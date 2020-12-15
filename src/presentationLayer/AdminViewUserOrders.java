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
import DTO.RouteCheckpoint;
import DTO.User;
import DTO.Warehouse;
import DTO.WarehouseItem;
import Repositories.RepoItem;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;
import businessLogicLayer.OrderManager;
import businessLogicLayer.StockManager;
import businessLogicLayer.UserManager;
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

public class AdminViewUserOrders extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTable tblOrders;
	private DefaultTableModel model;
	
	private User user;
	private UserManager userManager = new UserManager();
	private OrderManager orderManager = new OrderManager();
	private LocationManager locationManager = new LocationManager();
	
	private Color watermelon = new Color(254,127,156);
	private Color lemonade = new Color(253,185,200);
	private Color pastelPink = new Color(255, 209, 220);
	private Color secondaryPink = new Color(241, 57, 83);
	private Color tertiaryPink = new Color(255, 0 ,51);
	private Color whiteShade = new Color(240, 248, 255);
	private Color tomato = new Color(255, 99, 71);
	private Color emerald  = new Color(80, 220, 100);
	
	private AdminViewUserOrders self = this;
	private Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
	private Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);
	private JLabel notification;
	private JLabel tooltip;
	private JButton viewItemsBtn;
	private JButton viewRouteBtn;

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
	public AdminViewUserOrders(JPanel mainPanel, User user ) {
		super();
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon plusIcon = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE	, 30, tertiaryPink);
		Icon minusIcon = IconFontSwing.buildIcon(FontAwesome.MINUS_CIRCLE, 30, tertiaryPink);
		Icon refreshIcon = IconFontSwing.buildIcon(FontAwesome.REFRESH, 30, tertiaryPink);
		Icon backIcon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 30, tertiaryPink);
		
		this.mainPanel = mainPanel;
		this.user = user;		
		
		
		
		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(50, 70, 680, 450);
		scrollPane.setBackground(Color.WHITE);
		this.add(scrollPane);

		tblOrders= new PinkTable();
		scrollPane.setViewportView(tblOrders);

		RefreshOrdersTable();
		
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
				switchMainPanel("orders");
				notification.setText("");
			}
		});
		add(backArrow);
		
		notification = new JLabel("");
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setBounds(50, 610, 680, 50);
		add(notification);
		
		tooltip = new JLabel("Select Order to view ordered Items or route taken");
		tooltip.setHorizontalAlignment(SwingConstants.CENTER);
		tooltip.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		tooltip.setForeground(Color.BLACK);
		tooltip.setBounds(50, 20, 680, 40);
		add(tooltip);
		
		viewItemsBtn = new JButton("View Items");
		viewItemsBtn.setForeground(Color.WHITE);
		viewItemsBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		viewItemsBtn.setBackground(new Color(241, 57, 83));
		viewItemsBtn.setBounds(160, 550, 150, 40);
		viewItemsBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				viewItemsBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				viewItemsBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				int column = 0;
				int row = tblOrders.getSelectedRow();
				if (row >= 0) {
					String id = tblOrders.getModel().getValueAt(row, column).toString();
					Order order= orderManager.get(Integer.parseInt(id));
					
					JPanel viewUserOrdersItems = new AdminViewUserOrderItems(mainPanel, order);
					mainPanel.add(viewUserOrdersItems,"viewUserOrdersItems");
					
					switchMainPanel("viewUserOrdersItems");
					notification.setText("");
				}
				else {
					notification.setText("Select a order to view items");
					notification.setForeground(tomato);
				}
			}
		});
		add(viewItemsBtn);
		
		viewRouteBtn = new JButton("View Route");
		viewRouteBtn.setForeground(Color.WHITE);
		viewRouteBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		viewRouteBtn.setBackground(new Color(241, 57, 83));
		viewRouteBtn.setBounds(440, 550, 150, 40);
		viewRouteBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				viewRouteBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				viewRouteBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				int column = 0;
				int row = tblOrders.getSelectedRow();
				if (row >= 0) {
					String id = tblOrders.getModel().getValueAt(row, column).toString();
					Order order= orderManager.get(Integer.parseInt(id));
					ArrayList<IDTO> cps = orderManager.getAllCheckpointsByOrder(order);
					new Thread(() -> {
						locationManager.displayRoute(cps);
					}).start();
					notification.setText("");
				}
				else {
					notification.setText("Select order to view route");
					notification.setForeground(tomato);
				}
			}
		});
		add(viewRouteBtn);

	}

	public void RefreshOrdersTable() {
		boolean isEditable[] = { false, false, false, false, false, false, false, false, false, false, false };
		model = new DefaultTableModel(new Object[] { "id", "idCustomer", "idDriver", "destinationLongitude", "destinationLatitude", "dateStart", "dateEnd",
				"orderStatus", "totalAmount", "isDeleted", "driverName"}, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};
		
		ArrayList<IDTO> orders = orderManager.getAllFinishedByUser(user.getId());
		if(orders.isEmpty()) {
			model.addRow(new Object[] {"","","","","","","","","","", ""});
		}
		for (IDTO dto : orders) {
			Order order = (Order) dto;
			User driver= userManager.get(order.getIdDriver());
			model.addRow(new Object[] { order.getId(), order.getIdCustomer(), order.getIdDriver(), order.getLocationDestination().getLongitude(),
					order.getLocationDestination().getLatitude(), order.getStartDate(), order.getEndDate(), order.getStatus(), order.getTotalAmount(),
					order.isDeleted(), driver.getFirstname() + " " + driver.getLastname()});
		}
		
		this.tblOrders.setModel(model);
		this.tblOrders.getColumnModel().getColumn(0).setWidth(0);
		this.tblOrders.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblOrders.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblOrders.getColumnModel().getColumn(1).setWidth(0);
		this.tblOrders.getColumnModel().getColumn(1).setMinWidth(0);
		this.tblOrders.getColumnModel().getColumn(1).setMaxWidth(0);
		this.tblOrders.getColumnModel().getColumn(2).setWidth(0);
		this.tblOrders.getColumnModel().getColumn(2).setMinWidth(0);
		this.tblOrders.getColumnModel().getColumn(2).setMaxWidth(0);
		this.tblOrders.getColumnModel().getColumn(3).setWidth(0);
		this.tblOrders.getColumnModel().getColumn(3).setMinWidth(0);
		this.tblOrders.getColumnModel().getColumn(3).setMaxWidth(0);
		this.tblOrders.getColumnModel().getColumn(4).setWidth(0);
		this.tblOrders.getColumnModel().getColumn(4).setMinWidth(0);
		this.tblOrders.getColumnModel().getColumn(4).setMaxWidth(0);
		this.tblOrders.getColumnModel().getColumn(9).setWidth(0);
		this.tblOrders.getColumnModel().getColumn(9).setMinWidth(0);
		this.tblOrders.getColumnModel().getColumn(9).setMaxWidth(0);	

	}
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}
}
