package presentationLayer;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


import DTO.IDTO;
import DTO.Item;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminManageItemsPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTable tblItems;
	private DefaultTableModel model;
	private ItemManager itemManager = new ItemManager();
	private Color watermelon = new Color(254,127,156);
	private Color lemonade = new Color(253,185,200);
	private Color pastelPink = new Color(255, 209, 220);
	private Color secondaryPink = new Color(241, 57, 83);
	private Color tertiaryPink = new Color(255, 0 ,51);
	private Color whiteShade = new Color(240, 248, 255);
	
	private Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
	private Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);
	
	public JButton addItemBtn;
	public JButton deleteItemBtn;
	public JButton restoreItemsBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			        JFrame f = new JFrame();
			        f.setUndecorated(true);
			        f.setSize( 780, 670);
			        f.setTitle("Sometimes Red, Sometimes Blue");
			        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        f.getContentPane().add(new AdminManageItemsPane(new JPanel()));
			        f.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminManageItemsPane(JPanel mainPanel) {
		super();
		this.mainPanel = mainPanel;
		
		JPanel addItemsPanel = new AdminAddItemsPane (mainPanel, this);
		mainPanel.add(addItemsPanel,"addItems");
		
		AdminRestoreItemsPane restoreItemsPanel = new AdminRestoreItemsPane(mainPanel, this);
		mainPanel.add(restoreItemsPanel, "restoreItems");
		
		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(50, 50, 680, 450);
		scrollPane.setBackground(Color.WHITE);
		this.add(scrollPane);

		tblItems= new PinkTable();
		scrollPane.setViewportView(tblItems);

		RefreshItemTable();
		
		addItemBtn = new JButton("Add");
		addItemBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				addItemBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				addItemBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			@Override
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("addItems");
			}

		});
		
		addItemBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		addItemBtn.setForeground(Color.WHITE);
		addItemBtn.setBackground(secondaryPink);
		addItemBtn.setBounds(100, 550, 150, 40);
		this.add(addItemBtn);
		
		deleteItemBtn = new JButton("Delete");
		deleteItemBtn.setForeground(Color.WHITE);
		deleteItemBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		deleteItemBtn.setBackground(new Color(241, 57, 83));
		deleteItemBtn.setBounds(315, 550, 150, 40);
		deleteItemBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				deleteItemBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				deleteItemBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			@Override
			public void mousePressed(MouseEvent e ) {
				
				int column = 0;
				int row = tblItems.getSelectedRow();
				if (row >= 0) {
					String id = tblItems.getModel().getValueAt(row, column).toString();
					itemManager.delete(Integer.parseInt(id));
					RefreshItemTable();
					restoreItemsPanel.RefreshItemTable();
				}
			}

		});
		this.add(deleteItemBtn);
		
		restoreItemsBtn = new JButton("Restore");
		restoreItemsBtn.setForeground(Color.WHITE);
		restoreItemsBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		restoreItemsBtn.setBackground(new Color(241, 57, 83));
		restoreItemsBtn.setBounds(530, 550, 150, 40);
		restoreItemsBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				restoreItemsBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				restoreItemsBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("restoreItems");
				}
		});
		this.add(restoreItemsBtn);

	}

	public void RefreshItemTable() {
		boolean isEditable[] = { false, true, true, true, false };
		model = new DefaultTableModel(new Object[] { "id", "category", "price", "description", "isDeleted" }, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};
		ArrayList<IDTO> activeItems = itemManager.getAllActiveItems();
		if(activeItems.isEmpty()) {
			model.addRow(new Object[] {"","","","",""});
		}
		for (IDTO dto : activeItems) {
			Item item = (Item) dto;
			model.addRow(new Object[] { item.getId(), item.getCategory(), item.getPrice(), item.getDescription(),
					item.getIsDeleted() });
		}
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int column = 0;
				int row = tblItems.getSelectedRow();
				if (row >= 0) {
					if (!InputManager.verifyPositiveDouble(tblItems.getModel().getValueAt(row, 2).toString())) {
						RefreshItemTable();
						JOptionPane.showMessageDialog(null, "Price must be a positive double.");
					} else if (!InputManager.verifyCategory(tblItems.getModel().getValueAt(row, 1).toString())) {
						RefreshItemTable();
						JOptionPane.showMessageDialog(null, "Category must be either WEAPON, GROCERY or FOOD.");
					} else {

						String id = tblItems.getModel().getValueAt(row, column).toString();
						Item item = itemManager.get(Integer.parseInt(id));
						item.setCategory(tblItems.getModel().getValueAt(row, 1).toString());
						item.setPrice(Double.parseDouble(tblItems.getModel().getValueAt(row, 2).toString()));
						item.setDescription(tblItems.getModel().getValueAt(row, 3).toString());
						item.setIsDeleted(Integer.parseInt(tblItems.getModel().getValueAt(row, 4).toString()));
						itemManager.update(item);
						RefreshItemTable();
					}
				}
			}
		});
		this.tblItems.setModel(model);
		this.tblItems.getColumnModel().getColumn(0).setWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setMaxWidth(0);
		

	}
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}

}
