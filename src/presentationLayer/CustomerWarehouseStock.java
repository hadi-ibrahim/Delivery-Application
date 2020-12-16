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
import DTO.Order;
import DTO.OrderedWarehouseItem;
import DTO.Warehouse;
import DTO.WarehouseItem;
import Helpers.SessionHelper;
import Repositories.RepoItem;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;
import businessLogicLayer.OrderManager;
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
	public static Order order;
	private Warehouse warehouse;
	
	private ItemManager itemManager = new ItemManager();
	private StockManager stockManager = new StockManager();
	private OrderManager orderManager = new OrderManager();
	
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
	 * Create the frame.
	 */
	public CustomerWarehouseStock(JPanel mainPanel,Warehouse warehouse, CustomerTrackOrders customerTrackOrders) {
		super();
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon plusIcon = IconFontSwing.buildIcon(FontAwesome.CART_PLUS	, 30, tertiaryPink);
		Icon backIcon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 30, tertiaryPink);
		order = new Order();
		CustomerWarehouseStock.order.setIdCustomer(SessionHelper.isLoggedIn.getId());
		
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
				 int column = 0;
			        int row = tblItems.getSelectedRow();
			        if (row >= 0) {
			            String id = tblItems.getModel().getValueAt(row, column).toString();
			            Item item = itemManager.get(stockManager.get(Integer.parseInt(id)).getIdItem());
			            String quantity = tblItems.getModel().getValueAt(row, 5).toString();

			            if(InputManager.verifyPositiveInteger(quantity)) {
			            	if(Integer.parseInt(quantity) > 0 ) {
					            RefreshItemTable();
					            String availableQuantity = tblItems.getModel().getValueAt(row, 4).toString();
			            		if (Integer.parseInt(quantity) <= Integer.parseInt(availableQuantity)) {
			            			OrderedWarehouseItem orderedItem = new OrderedWarehouseItem();
			            			orderedItem.setIdWarehouseItem(Integer.parseInt(tblItems.getModel().getValueAt(row, 0).toString()));
			            			orderedItem.setPricePerUnit(item.getPrice());
			            			orderedItem.setQuantity(Integer.parseInt(quantity));
			            			
			            			orderManager.addItemToShoppingCart(orderedItem, order);
			            			notification.setText("Item added to shopping cart");
			            			notification.setForeground(emerald);
			            		}
			            		else notification.setText("Quantity not available");
			            	}
			            	else notification.setText("Please enter a quantity");
			            }
			            else {
			            	notification.setText("Please enter a valid quantity");
			            }
			     
			            RefreshItemTable();
			        }
			        else {
			        	notification.setText("Select an Item to add to shopping cart");
			        	notification.setForeground(tomato);
			        }
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
		
		JButton checkoutBtn = new JButton("Checkout");
		checkoutBtn.setForeground(Color.WHITE);
		checkoutBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		checkoutBtn.setBackground(new Color(241, 57, 83));
		checkoutBtn.setBounds(310, 540, 150, 40);
		checkoutBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				checkoutBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				checkoutBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			@Override
			public void mousePressed(MouseEvent e ) {
					JPanel checkoutPanel = new CustomerCheckout(mainPanel, order,customerTrackOrders);
					mainPanel.add(checkoutPanel,"checkout");
					switchMainPanel("checkout");
					notification.setText("");
				}
		});
		add(checkoutBtn);

	}

	public void RefreshItemTable() {
		boolean isEditable[] = { false, false, false, false, false, true};
		model = new DefaultTableModel(new Object[] { "id", "category", "price", "description", "quantityAvailable", "quantity" }, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};
		
		ArrayList<IDTO> warehouseItems = stockManager.getAllActiveItemsInWarehouse(warehouse);
		if(warehouseItems.isEmpty()) {
			model.addRow(new Object[] {"","","","","","" });
		}
		for (IDTO dto : warehouseItems) {
			WarehouseItem warehouseItem = (WarehouseItem) dto;
			Item item = itemManager.get(warehouseItem.getIdItem());
			model.addRow(new Object[] { warehouseItem.getId(), item.getCategory(), item.getPrice(), item.getDescription(),warehouseItem.getQuantity(), "0" });
		}
		
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
		if(name!= "checkout")
			orderManager.restoreItemsFromShoppingCart(order);
		cards.show(mainPanel, name);
	}
}
