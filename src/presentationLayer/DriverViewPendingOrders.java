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
import Helpers.SessionHelper;
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

public class DriverViewPendingOrders extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTable tblOrders;
	private DefaultTableModel model;
	
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
	
	private DriverViewPendingOrders self = this;
	private Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
	private Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);
	private JLabel notification;
	private JLabel tooltip;
	private JButton acceptBtn;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public DriverViewPendingOrders(JPanel mainPanel,DriverManageOrders driverManageOrders) {
		super();
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon plusIcon = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE	, 30, tertiaryPink);
		Icon minusIcon = IconFontSwing.buildIcon(FontAwesome.MINUS_CIRCLE, 30, tertiaryPink);
		Icon refreshIcon = IconFontSwing.buildIcon(FontAwesome.REFRESH, 30, tertiaryPink);
		Icon backIcon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 30, tertiaryPink);
		
		this.mainPanel = mainPanel;
		
		
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
		
		acceptBtn = new JButton("Accept");
		acceptBtn.setForeground(Color.WHITE);
		acceptBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		acceptBtn.setBackground(new Color(241, 57, 83));
		acceptBtn.setBounds(310, 560, 150, 40);
		acceptBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				acceptBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				acceptBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				if(SessionHelper.isLoggedIn.getDriverStatus().toString()== "AVAILABLE") {
				
					int column = 0;
					int row = tblOrders.getSelectedRow();
					if (row >= 0) {
						String id = tblOrders.getModel().getValueAt(row, column).toString();
						Order order= orderManager.get(Integer.parseInt(id));
						orderManager.takeOrder(order, SessionHelper.isLoggedIn);
						
						notification.setText("Successfully accepted, be safe :)");
						notification.setForeground(emerald);
						RefreshOrdersTable();
						driverManageOrders.isActive=true;
						driverManageOrders.RefreshManageOrderPane();

					}
					else {
						notification.setText("Select order to accept");
						notification.setForeground(tomato);
					}
				}
				else {
					notification.setText("Cannot accept more than 1 order at a time");
					notification.setForeground(tomato);
				}	
				
			}
		});
		add(acceptBtn);

	}

	public void RefreshOrdersTable() {
		boolean isEditable[] = { false, false, false, false, false, false, false, false, false, false };
		model = new DefaultTableModel(new Object[] { "id", "idCustomer", "idDriver", "destinationLongitude", "destinationLatitude", "dateStart", "dateEnd",
				"orderStatus", "totalAmount", "isDeleted"}, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};
		ArrayList<IDTO> orders = orderManager.getAllPending();
		if(orders.isEmpty()) {
			model.addRow(new Object[] {"","","","","","","","","",""});
		}
		for (IDTO dto : orders) {
			Order order = (Order) dto;
			model.addRow(new Object[] { order.getId(), order.getIdCustomer(), order.getIdDriver(), order.getLocationDestination().getLongitude(),
					order.getLocationDestination().getLatitude(), order.getStartDate(), order.getEndDate(), order.getStatus(), order.getTotalAmount(),
					order.isDeleted()});
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
