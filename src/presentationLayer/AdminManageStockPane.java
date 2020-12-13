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
import DTO.Warehouse;
import DTO.WarehouseItem;
import Repositories.RepoItem;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;
import businessLogicLayer.StockManager;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;

public class AdminManageStockPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTable tblItems;
	private DefaultTableModel model;
	
	private Warehouse warehouse;
	private ItemManager itemManager = new ItemManager();
	private StockManager stockManager = new StockManager();
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
	public JButton backBtn;

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
	public AdminManageStockPane(JPanel mainPanel, AdminManageWarehousesPane adminManageWarehousePane, Warehouse warehouse) {
		super();
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon backIcon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 30, tertiaryPink);
		
		this.mainPanel = mainPanel;
		this.warehouse = warehouse;
		
		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(50, 70, 680, 450);
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
		addItemBtn.setBounds(100, 570, 150, 40);
		this.add(addItemBtn);
		
		deleteItemBtn = new JButton("Delete");
		deleteItemBtn.setForeground(Color.WHITE);
		deleteItemBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		deleteItemBtn.setBackground(new Color(241, 57, 83));
		deleteItemBtn.setBounds(315, 570, 150, 40);
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
					stockManager.delete(Integer.parseInt(id));
					RefreshItemTable();
				}
			}

		});
		this.add(deleteItemBtn);
		
		backBtn = new JButton("Back");
		backBtn.setForeground(Color.WHITE);
		backBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		backBtn.setBackground(new Color(241, 57, 83));
		backBtn.setBounds(530, 570, 150, 40);
		backBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				backBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				backBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("restoreItems");
				}
		});
		this.add(backBtn);
		
		JLabel backArrow = new JLabel(backIcon);
		backArrow.setBounds(680, 20, 40, 40);
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
				switchMainPanel("warehouses");
			}
		});
		add(backArrow);

	}

	public void RefreshItemTable() {
		boolean isEditable[] = { false, false, false, false, false, false };
		model = new DefaultTableModel(new Object[] { "id", "category", "price", "description", "quantity", "isDeleted" }, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};
		
		ArrayList<IDTO> warehouseItems = stockManager.getAllItemsInWarehouse(warehouse);
		if(warehouseItems.isEmpty()) {
			model.addRow(new Object[] {"","","","","",""});
		}
		for (IDTO dto : warehouseItems) {
			WarehouseItem warehouseItem = (WarehouseItem) dto;
			Item item = itemManager.get(warehouseItem.getIdItem());
			model.addRow(new Object[] { warehouseItem.getId(), item.getCategory(), item.getPrice(), item.getDescription(),warehouseItem.getQuantity(),
					warehouseItem.getIsDeleted() });
		}
		
		this.tblItems.setModel(model);
		this.tblItems.getColumnModel().getColumn(0).setWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblItems.getColumnModel().getColumn(5).setWidth(0);
		this.tblItems.getColumnModel().getColumn(5).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(5).setMaxWidth(0);	

	}
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}
}
