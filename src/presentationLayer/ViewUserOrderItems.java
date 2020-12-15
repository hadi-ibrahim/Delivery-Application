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

public class ViewUserOrderItems extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTable tblOrders;
	private DefaultTableModel model;
	
	private Order order;
	private OrderManager orderManager = new OrderManager();
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
	
	private ViewUserOrderItems self = this;
	private Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
	private Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);
	private JLabel notification;
	private JLabel tooltip;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ViewUserOrderItems(JPanel mainPanel, Order order ) {
		super();
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon plusIcon = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE	, 30, tertiaryPink);
		Icon minusIcon = IconFontSwing.buildIcon(FontAwesome.MINUS_CIRCLE, 30, tertiaryPink);
		Icon refreshIcon = IconFontSwing.buildIcon(FontAwesome.REFRESH, 30, tertiaryPink);
		Icon backIcon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 30, tertiaryPink);
		
		this.mainPanel = mainPanel;
		this.order = order;
		
		
		
		
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

		RefreshOrderedItemsTable();
		
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
				switchMainPanel("viewUserOrders");
				notification.setText("");
			}
		});
		add(backArrow);
		
		notification = new JLabel("");
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setBounds(50, 610, 680, 50);
		add(notification);
		
		tooltip = new JLabel("Items");
		tooltip.setHorizontalAlignment(SwingConstants.CENTER);
		tooltip.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		tooltip.setForeground(Color.BLACK);
		tooltip.setBounds(50, 20, 680, 40);
		add(tooltip);

	}

	public void RefreshOrderedItemsTable() {
		boolean isEditable[] = { false, false, false, false, false, false, false};
		model = new DefaultTableModel(new Object[] { "id", "idWarehouseItem", "idOrder", "quantity", "price per unit", "description" }, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};
		
		ArrayList<IDTO> orderedWarehouseItem = orderManager.getOrderOrderedItems(order);
		if(orderedWarehouseItem.isEmpty()) {
			model.addRow(new Object[] {"","","","","","",""});
		}
		for (IDTO dto : orderedWarehouseItem) {
			OrderedWarehouseItem orderedItem = (OrderedWarehouseItem) dto;
			model.addRow(new Object[] { orderedItem.getId(), orderedItem.getIdWarehouseItem(), orderedItem.getIdOrder(), orderedItem.getQuantity(), orderedItem.getPricePerUnit(),
					itemManager.get(stockManager.get(orderedItem.getIdWarehouseItem()).getIdItem()).getDescription()});			
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

	}
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}
}
