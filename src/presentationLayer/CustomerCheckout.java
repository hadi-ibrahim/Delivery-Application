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

import DTO.Address;
import DTO.IDTO;
import DTO.Item;
import DTO.Location;
import DTO.Order;
import DTO.OrderStatus;
import DTO.OrderedWarehouseItem;
import DTO.Warehouse;
import DTO.WarehouseItem;
import Helpers.SessionHelper;
import Repositories.RepoItem;
import businessLogicLayer.AddressManager;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;
import businessLogicLayer.OrderManager;
import businessLogicLayer.StockManager;
import businessLogicLayer.WarehouseManager;
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

public class CustomerCheckout extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTable tblItems;
	private DefaultTableModel model;

	private Order order;	
	private ItemManager itemManager = new ItemManager();
	private StockManager stockManager = new StockManager();
	private OrderManager orderManager = new OrderManager();
	private AddressManager addressManager= new AddressManager();
	private WarehouseManager warehouseManager= new WarehouseManager();

	
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
	private JLabel notification;
	private JLabel checkoutTxt;
	private JLabel totalLbl;
	private double sum;
	
	private JTable tblAddresses;
	/**
	 * Create the frame.
	 */
	public CustomerCheckout(JPanel mainPanel,Order order, CustomerTrackOrders customerTrackOrders) {
		super();
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon plusIcon = IconFontSwing.buildIcon(FontAwesome.CART_PLUS	, 30, tertiaryPink);
		Icon backIcon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 30, tertiaryPink);
		
		order.setIdCustomer(SessionHelper.isLoggedIn.getId());
		
		this.mainPanel = mainPanel;
		this.order= order;
		
		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JScrollPane itemsPane = new JScrollPane();
		itemsPane.setViewportBorder(null);
		itemsPane.setBounds(50, 100, 300, 200);
		itemsPane.setBackground(Color.WHITE);
		this.add(itemsPane);

		tblItems= new PinkTable();
		itemsPane.setViewportView(tblItems);
		
		totalLbl = new JLabel("");
		totalLbl.setHorizontalAlignment(SwingConstants.CENTER);
		totalLbl.setBounds(500, 450, 150, 40);
		add(totalLbl);

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
				switchMainPanel("warehouseStock");
				notification.setText("");
			}
		});
		add(backArrow);
		
		notification = new JLabel("");
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setBounds(50, 610, 680, 50);
		add(notification);
		
		checkoutTxt = new JLabel("Checkout");
		checkoutTxt.setHorizontalAlignment(SwingConstants.CENTER);
		checkoutTxt.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		checkoutTxt.setForeground(Color.BLACK);
		checkoutTxt.setBounds(70, 20, 650, 40);
		add(checkoutTxt);
		
		JButton checkoutBtn = new JButton("Checkout");
		checkoutBtn.setForeground(Color.WHITE);
		checkoutBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		checkoutBtn.setBackground(new Color(241, 57, 83));
		checkoutBtn.setBounds(510, 561, 150, 40);
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
			        int row = tblAddresses.getSelectedRow();
			        if (row >= 0) {
			            String longitude = tblAddresses.getModel().getValueAt(row, 3).toString();
			            String latitude = tblAddresses.getModel().getValueAt(row, 2).toString();
			            order.setLocationDestination(new Location(Double.parseDouble(longitude),Double.parseDouble(latitude)));
			            order.setTotalAmount(sum);
			            orderManager.placeOrder(order);
			            notification.setText("Order placed successfully");
			            notification.setForeground(emerald);
			            customerTrackOrders.RefreshOrdersTable();
			        }
			        else {
			        	notification.setText("Choose address to complete order.");
			        	notification.setForeground(tomato);
			        }
				}
		});
		add(checkoutBtn);
		
		JScrollPane addressPane = new JScrollPane();
		addressPane.setViewportBorder(null);
		addressPane.setBackground(Color.WHITE);
		addressPane.setBounds(50, 400, 300, 200);
		add(addressPane);
		
		tblAddresses = new PinkTable();
		addressPane.setViewportView(tblAddresses);
		RefreshAddressTable();
		JLabel itemsLbl = new JLabel("Items");
		itemsLbl.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		itemsLbl.setHorizontalAlignment(SwingConstants.CENTER);
		itemsLbl.setBounds(120, 50, 150, 40);
		add(itemsLbl);
		
		JLabel totalTxt = new JLabel("Total:");
		totalTxt.setHorizontalAlignment(SwingConstants.CENTER);
		totalTxt.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		totalTxt.setBounds(400, 450, 90, 40);
		add(totalTxt);
		


	}

	public void RefreshItemTable() {
		boolean isEditable[] = { false, false, false,false};
		model = new DefaultTableModel(new Object[] { "id","items", "quantity" , "price"}, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};
		
		
		if(order.getOrderedItems().isEmpty()) {
			model.addRow(new Object[] {"","", "", ""  });
		}
		sum = 0;
		for (IDTO dto : order.getOrderedItems()) {
			OrderedWarehouseItem orderedWarehouseItem = (OrderedWarehouseItem) dto;
			WarehouseItem warehouseItem = stockManager.get(orderedWarehouseItem.getIdWarehouseItem());
			Item item = itemManager.get(warehouseItem.getIdItem());
			sum+=item.getPrice() *orderedWarehouseItem.getQuantity();
			model.addRow(new Object[] { orderedWarehouseItem.getId(), item.getDescription() +": " + item.getPrice() + "$",  orderedWarehouseItem.getQuantity(),item.getPrice() });
		}
		
		totalLbl.setText(sum + "$");
		
		this.tblItems.setModel(model);
		this.tblItems.getColumnModel().getColumn(0).setWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblItems.getColumnModel().getColumn(3).setWidth(0);
		this.tblItems.getColumnModel().getColumn(3).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(3).setMaxWidth(0);


		this.tblItems.getColumnModel().getColumn(2).setMaxWidth(70);
	}
	
	public void RefreshAddressTable() {
		boolean isEditable[] = { false, false, false, false};
		model = new DefaultTableModel(new Object[] { "id", "address", "latitude", " longitude" }, 0) {


			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};

		ArrayList<Address> addresses=  addressManager.getAllByUser(SessionHelper.isLoggedIn);
		if(addresses.isEmpty()) {
			model.addRow(new Object[] {"", "", "", ""});
		}
		for (IDTO dto : addresses) {
			Address address = (Address) dto;
			model.addRow(new Object[] { address.getId(), address.toString(),
					address.getLocation().getLatitude(),address.getLocation().getLongitude()});
		}
		
		this.tblAddresses.setModel(model);
		this.tblAddresses.getColumnModel().getColumn(0).setWidth(0);
		this.tblAddresses.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblAddresses.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblAddresses.getColumnModel().getColumn(2).setWidth(0);
		this.tblAddresses.getColumnModel().getColumn(2).setMinWidth(0);
		this.tblAddresses.getColumnModel().getColumn(2).setMaxWidth(0);
		this.tblAddresses.getColumnModel().getColumn(3).setWidth(0);
		this.tblAddresses.getColumnModel().getColumn(3).setMinWidth(0);
		this.tblAddresses.getColumnModel().getColumn(3).setMaxWidth(0);

	}
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		if(name!= "warehouseStock")
			orderManager.restoreItemsFromShoppingCart(order);
		cards.show(mainPanel, name);
	}
}
