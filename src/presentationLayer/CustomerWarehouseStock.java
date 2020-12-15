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
import javax.swing.SwingConstants;

public class CustomerWarehouseStock extends JPanel {

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
	private Color tomato = new Color(255, 99, 71);
	private Color emerald  = new Color(80, 220, 100);
	
	private Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
	private Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);
	private JLabel addIcon;
	private JLabel notification;
	private JLabel warehouseName;

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
			        f.getContentPane().add(new AdminManageItems(new JPanel()));
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
	public CustomerWarehouseStock(JPanel mainPanel, AdminManageWarehouses adminManageWarehousePane, Warehouse warehouse) {
		super();
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon plusIcon = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE	, 30, tertiaryPink);
		Icon minusIcon = IconFontSwing.buildIcon(FontAwesome.MINUS_CIRCLE, 30, tertiaryPink);
		Icon refreshIcon = IconFontSwing.buildIcon(FontAwesome.REFRESH, 30, tertiaryPink);
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
				switchMainPanel("orderItems");
				notification.setText("");
			}
		});
		add(backArrow);
		
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
				switchMainPanel("addWarehouseItem");
				notification.setText("");
			}

		});
		add(addIcon);
		
		notification = new JLabel("");
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setBounds(50, 610, 680, 50);
		add(notification);
		
		warehouseName = new JLabel(warehouse.getName());
		warehouseName.setHorizontalAlignment(SwingConstants.CENTER);
		warehouseName.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		warehouseName.setForeground(Color.BLACK);
		warehouseName.setBounds(70, 20, 650, 40);
		add(warehouseName);

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
