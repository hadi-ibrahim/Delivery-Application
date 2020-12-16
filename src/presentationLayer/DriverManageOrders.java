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



import DTO.DriverStatus;
import DTO.IDTO;
import DTO.Item;
import DTO.Order;
import DTO.OrderStatus;
import DTO.OrderedWarehouseItem;
import DTO.RouteCheckpoint;
import DTO.User;
import DTO.Warehouse;
import DTO.WarehouseItem;
import Helpers.SessionHelper;
import Repositories.RepoItem;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;
import businessLogicLayer.OrderManager;
import businessLogicLayer.StockManager;
import businessLogicLayer.UserManager;
import businessLogicLayer.WarehouseManager;
import businessLogicLayer.LocationManager.LocationManager;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DriverManageOrders extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private DefaultTableModel model;

	private Color watermelon = new Color(254, 127, 156);
	private Color lemonade = new Color(253, 185, 200);
	private Color pastelPink = new Color(255, 209, 220);
	private Color secondaryPink = new Color(241, 57, 83);
	private Color tertiaryPink = new Color(255, 0, 51);
	private Color whiteShade = new Color(240, 248, 255);
	private Color tomato = new Color(255, 99, 71);
	private Color emerald = new Color(80, 220, 100);

	private Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
	private Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);
	private JLabel notification;
	private JLabel txtCustName;
	private JLabel txtPhoneNumber;

	private Order order = new Order();
	private boolean isActive = false;
	private StockManager stockManager = new StockManager();
	private OrderManager orderManager = new OrderManager();
	private UserManager userManager = new UserManager();
	private WarehouseManager warehouseManager = new WarehouseManager();
	private LocationManager locationManager = new LocationManager();

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public DriverManageOrders() {
		super();
		IconFontSwing.register(FontAwesome.getIconFont());
		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		notification = new JLabel("");
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setBounds(50, 610, 680, 50);
		add(notification);
		
		JLabel lblCustName = new JLabel("New label");
		lblCustName.setBounds(114, 149, 42, 13);
		add(lblCustName);

		JLabel lblPhoneNumber = new JLabel("New label");
		lblPhoneNumber.setBounds(114, 200, 42, 13);
		add(lblPhoneNumber);

		txtCustName = new JLabel("New label");
		txtCustName.setBounds(264, 149, 42, 13);
		add(txtCustName);

		txtPhoneNumber = new JLabel("New label");
		txtPhoneNumber.setBounds(264, 200, 42, 13);
		add(txtPhoneNumber);
		
		JButton pickupBtn = new JButton("New button");
		pickupBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (isActive) {
					OrderedWarehouseItem orderedItem = order.getOrderedItems().get(0);
					WarehouseItem warehouseItem = stockManager.get(orderedItem.getIdWarehouseItem());
					Warehouse warehouse = warehouseManager.get(warehouseItem.getIdWarehouse());
					SessionHelper.isLoggedIn.setLocation(warehouse.getLocation());
					RouteCheckpoint routeCheckpoint = new RouteCheckpoint();
					routeCheckpoint.setIdOrder(order.getId());
					routeCheckpoint.setIsDeleted(0);
					SessionHelper.isLoggedIn.setLocation(warehouse.getLocation());
					routeCheckpoint.setLocation(SessionHelper.isLoggedIn.getLocation());
					order.setOrderStatus(OrderStatus.DELIVERING);
					orderManager.addRouteCheckpoint(routeCheckpoint);
					orderManager.update(order);
					userManager.update(SessionHelper.isLoggedIn);

				}
			}
		});
		pickupBtn.setBounds(95, 399, 83, 21);
		add(pickupBtn);

		JButton finishBtn = new JButton("New button");
		finishBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (isActive) {
					if(order.getStatus()==OrderStatus.DELIVERING) {
					SessionHelper.isLoggedIn.setLocation(order.getLocationDestination());
					RouteCheckpoint routeCheckpoint = new RouteCheckpoint();
					routeCheckpoint.setIdOrder(order.getId());
					routeCheckpoint.setIsDeleted(0);
					SessionHelper.isLoggedIn.setDriverStatus(DriverStatus.AVAILABLE);
					SessionHelper.isLoggedIn.setLocation(order.getLocationDestination());
					routeCheckpoint.setLocation(SessionHelper.isLoggedIn.getLocation());
					order.setOrderStatus(OrderStatus.COMPLETED);
					orderManager.addRouteCheckpoint(routeCheckpoint);
					orderManager.finish(order);
					userManager.update(SessionHelper.isLoggedIn);
					RefreshManageOrderPane();
					notification.setText("");
				}
				}else {
					notification.setText("You cannot complete the order before confirming items pickup.");
				}
			}
		});
		finishBtn.setBounds(264, 399, 83, 21);

		add(finishBtn);

		JButton updateBtn = new JButton("New button");
		updateBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (isActive) {
					OrderedWarehouseItem orderedItem = order.getOrderedItems().get(0);
					WarehouseItem warehouseItem = stockManager.get(orderedItem.getIdWarehouseItem());
					Warehouse warehouse = warehouseManager.get(warehouseItem.getIdWarehouse());
					SessionHelper.isLoggedIn.setLocation(
							locationManager.updateDriverLocation(SessionHelper.isLoggedIn, order, warehouse));
					RouteCheckpoint routeCheckpoint = new RouteCheckpoint();
					routeCheckpoint.setIdOrder(order.getId());
					routeCheckpoint.setIsDeleted(0);
					routeCheckpoint.setLocation(SessionHelper.isLoggedIn.getLocation());
					orderManager.addRouteCheckpoint(routeCheckpoint);
					orderManager.update(order);
					userManager.update(SessionHelper.isLoggedIn);
				}
			}
		});
		updateBtn.setBounds(429, 399, 83, 21);
		add(updateBtn);


		RefreshManageOrderPane();

	}

	public void RefreshManageOrderPane() {

		if (SessionHelper.isLoggedIn.getDriverStatus() != DriverStatus.BUSY) {
			notification.setText("You did not accept an order yet");
		} else {
			notification.setText("");
			isActive = true;
			order = (Order) orderManager.getActive(SessionHelper.isLoggedIn);
			ArrayList<IDTO> orderedItems = orderManager.getOrderOrderedItems(order);
			ArrayList<OrderedWarehouseItem> temp = new ArrayList<OrderedWarehouseItem>();
			for(IDTO dto : orderedItems) {
				OrderedWarehouseItem orderedItem = (OrderedWarehouseItem) dto;
				temp.add(orderedItem);
			}
			order.setOrderedItems(temp);
			User customer = userManager.get(order.getIdCustomer());
			txtCustName.setText(customer.getFirstname() + " " + customer.getLastname());
			txtPhoneNumber.setText(customer.getPhone());
		}

	}
}