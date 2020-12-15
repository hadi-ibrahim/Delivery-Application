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
import businessLogicLayer.InputManager;
import businessLogicLayer.WarehouseManager;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Icon;

public class CustomerOrderItems extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private CustomerOrderItems self= this;
	private JTable tblWarehouses;
	private DefaultTableModel model;
	private WarehouseManager warehouseManager = new WarehouseManager();
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
	public JButton viewItems;
	private JLabel notification;

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
			        f.getContentPane().add(new CustomerOrderItems(new JPanel()));
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
	public CustomerOrderItems(JPanel mainPanel) {
		super();
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

		tblWarehouses= new PinkTable();
		scrollPane.setViewportView(tblWarehouses);

		RefreshItemTable();
		
		viewItems = new JButton("View Items");
		viewItems.setForeground(Color.WHITE);
		viewItems.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		viewItems.setBackground(new Color(241, 57, 83));
		viewItems.setBounds(310, 560, 150, 40);
		viewItems.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				viewItems.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				viewItems.setBackground(secondaryPink);
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				int column = 0;
				int row = tblWarehouses.getSelectedRow();
				if (row >= 0) {
					String id = tblWarehouses.getModel().getValueAt(row, column).toString();
					Warehouse warehouse= warehouseManager.get(Integer.parseInt(id));
					JPanel userCheckWarehouseStock = new CustomerWarehouseStock(mainPanel, warehouse);
					mainPanel.add(userCheckWarehouseStock,"warehouseStock");
					switchMainPanel("warehouseStock");
					notification.setText("");
				}
				else {
					notification.setText("Select a warehouse view stock");
					notification.setForeground(tomato);
				}
			}
		});
		add(viewItems);
		
		notification = new JLabel("");
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setBounds(50, 620, 680, 40);
		add(notification);

	}

	public void RefreshItemTable() {
		boolean isEditable[] = { false, false,false,false, false };
		model = new DefaultTableModel(new Object[] { "id", "Warehouses", "latitude", "longitude", "isDeleted" }, 0) {


			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};

		ArrayList<IDTO > activeWarehouses=  warehouseManager.getAllActiveWarehouses();
		if(activeWarehouses.isEmpty()) {
			model.addRow(new Object[] {"", "", "", "", ""});
		}
		for (IDTO dto : activeWarehouses) {
			Warehouse warehouse = (Warehouse) dto;
			model.addRow(new Object[] { warehouse.getId(), warehouse.getName(),warehouse.getLatitude(),warehouse.getLongitude(), warehouse.getIsDeleted()});
		}
		
		this.tblWarehouses.setModel(model);
		this.tblWarehouses.getColumnModel().getColumn(0).setWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(2).setWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(2).setMinWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(2).setMaxWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(3).setWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(3).setMinWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(3).setMaxWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(4).setWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(4).setMinWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(4).setMaxWidth(0);
		

	}
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}
}
