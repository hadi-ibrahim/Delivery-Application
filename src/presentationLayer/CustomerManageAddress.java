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
import DTO.Warehouse;
import Helpers.SessionHelper;
import businessLogicLayer.AddressManager;
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

public class CustomerManageAddress extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private CustomerManageAddress self= this;
	private JTable tblAddresses;
	private DefaultTableModel model;
	private AddressManager addressManager = new AddressManager();
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
	private JLabel deleteIcon;
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
			        f.getContentPane().add(new CustomerManageAddress(new JPanel()));
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
	public CustomerManageAddress(JPanel mainPanel) {
		super();
		this.mainPanel = mainPanel;
		
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon plusIcon = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE	, 30, tertiaryPink);
		Icon minusIcon = IconFontSwing.buildIcon(FontAwesome.MINUS_CIRCLE, 30, tertiaryPink);

		JPanel addAddressPanel = new CustomerAddAddress(mainPanel, this);
		mainPanel.add(addAddressPanel,"addAddress");
		

		
		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(50, 70, 680, 450);
		scrollPane.setBackground(Color.WHITE);
		this.add(scrollPane);

		tblAddresses= new PinkTable();
		scrollPane.setViewportView(tblAddresses);

		RefreshAddressTable();
		
		addIcon = new JLabel(plusIcon);
		addIcon.setBounds(70, 11, 40, 40);
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
				switchMainPanel("addAddress");
				notification.setText("");
			}

		});
		add(addIcon);
		
		deleteIcon = new JLabel(minusIcon);
		deleteIcon.setBounds(120, 11, 40, 40);
		deleteIcon.addMouseListener(new MouseAdapter() {
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
				int row = tblAddresses.getSelectedRow();
				if (row >= 0) {
					String id = tblAddresses.getModel().getValueAt(row, column).toString();
//					warehouseManager.delete(Integer.parseInt(id));
					
					
					
					
					
					
					
					
					
					RefreshAddressTable();
				}
				else {
					notification.setText("Select a warehouse to delete");
					notification.setForeground(tomato);
				}
			}

		});
		add(deleteIcon);
		
		notification = new JLabel("");
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setBounds(50, 620, 680, 40);
		add(notification);

	}

	public void RefreshAddressTable() {
		boolean isEditable[] = { false, true, true, true, false, false};
		model = new DefaultTableModel(new Object[] { "id", "street","city", "building", "latitude", "longitude" }, 0) {


			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};

		ArrayList<IDTO > activeWarehouses=  addressManager.getAllByUser(SessionHelper.isLoggedIn);
		if(activeWarehouses.isEmpty()) {
			model.addRow(new Object[] {"", "", "", "", "", "" });
		}
		for (IDTO dto : activeWarehouses) {
			Address address = (Address) dto;
			model.addRow(new Object[] { address.getId(), address.getStreet(),address.getCity(), address.getBuilding(),
					address.getLocation().getLatitude(),address.getLocation().getLongitude()});
		}
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				
				int column = 0;
				int row = tblAddresses.getSelectedRow();
				if (row >= 0) {
					
					if (!InputManager.verifyLongitude(tblAddresses.getModel().getValueAt(row, 5).toString())) {
						RefreshAddressTable();
						JOptionPane.showMessageDialog(null, "Error: latitude invalid");
					} else if (!InputManager.verifyLongitude(tblAddresses.getModel().getValueAt(row, 4).toString())) {
						RefreshAddressTable();
						JOptionPane.showMessageDialog(null, "Error: invalid longitude");
					} else {
						String id = tblAddresses.getModel().getValueAt(row, column).toString();
						Warehouse warehouse = addressManager.get(Integer.parseInt(id));
						warehouse.setName(tblAddresses.getModel().getValueAt(row, 1).toString());
						warehouse.setLatitude(Double.parseDouble(tblAddresses.getModel().getValueAt(row, 2).toString()));
						warehouse.setLongitude(Double.parseDouble(tblAddresses.getModel().getValueAt(row, 3).toString()));
						warehouse.setIsDeleted(Integer.parseInt(tblAddresses.getModel().getValueAt(row, 4).toString()));
						addressManager.update(warehouse);
						RefreshAddressTable();
					}
				}
			}
		});
		this.tblAddresses.setModel(model);
		this.tblAddresses.getColumnModel().getColumn(0).setWidth(0);
		this.tblAddresses.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblAddresses.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblAddresses.getColumnModel().getColumn(4).setWidth(0);
		this.tblAddresses.getColumnModel().getColumn(4).setMinWidth(0);
		this.tblAddresses.getColumnModel().getColumn(4).setMaxWidth(0);
		this.tblAddresses.getColumnModel().getColumn(5).setWidth(0);
		this.tblAddresses.getColumnModel().getColumn(5).setMinWidth(0);
		this.tblAddresses.getColumnModel().getColumn(5).setMaxWidth(0);

		

	}
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}
}
