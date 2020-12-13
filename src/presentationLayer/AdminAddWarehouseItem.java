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
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.SwingConstants;

public class AdminAddWarehouseItem extends JPanel {

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
	private Color tomato = new Color(255, 99, 71);
	private Color emerald  = new Color(80, 220, 100);
	
	private Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
	private Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);
	private JLabel addIcon;
	private JLabel notification;
	private JLabel backArrow;
	private JLabel title;

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
	public AdminAddWarehouseItem (JPanel mainPanel, AdminManageStockPane stockPane) {
		super();
		this.mainPanel = mainPanel;
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon plusIcon = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE	, 30, tertiaryPink);
		Icon backIcon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 30, tertiaryPink);

		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(50, 70, 680, 500);
		scrollPane.setBackground(Color.WHITE);
		this.add(scrollPane);

		tblItems= new PinkTable();
		scrollPane.setViewportView(tblItems);

		RefreshItemTable();
		
		addIcon = new JLabel(plusIcon);
		addIcon.setBounds(70, 20, 40, 40);
		addIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(arrow);

			}
			@Override
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("addItems");
				notification.setText("");
			}

		});
		add(addIcon);
		
		notification = new JLabel("");
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		notification.setBounds(90, 600, 600, 50);
		add(notification);
		
		backArrow = new JLabel(backIcon);
		backArrow.setBounds(670, 20, 40, 40);
		backArrow.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("manageStock");
	            notification.setText("");
			}
		});
		add(backArrow);
		
		title = new JLabel("Add Items to warehouse");
		title.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(watermelon);
		title.setBounds(120, 20, 570, 40);
		add(title);

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
