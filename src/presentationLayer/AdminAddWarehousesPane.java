
package presentationLayer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import DTO.Category;
import DTO.Item;
import DTO.Location;
import DTO.Warehouse;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;
import businessLogicLayer.WarehouseManager;
import businessLogicLayer.LocationManager.AddressPicker;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public class AdminAddWarehousesPane extends JPanel {

	
	private JPanel mainPanel;
	WarehouseManager warehouseManager = new WarehouseManager();
	private AddressPicker addressPicker = new AddressPicker();
	
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -7017182885009918297L;
	private JTextField txtName;
	private JTextField txtLongitude;
	private JLabel resultLbl;
	private JTextField txtLatitude;
	/**
	 * Create the panel.
	 */
	public AdminAddWarehousesPane(JPanel mainPanel, AdminManageWarehousesPane displayPanel) {
		super();
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon locationIcon = IconFontSwing.buildIcon(FontAwesome.MAP_MARKER, 60, tertiaryPink);

		setBackground(Color.WHITE);
		this.mainPanel = mainPanel;
		setBounds(100, 100, 780, 670);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		
		JLabel lblLatitude = new JLabel("Latitude");
		lblLatitude.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblLatitude.setHorizontalAlignment(SwingConstants.LEFT);
		lblLatitude.setBounds(100, 300, 100, 40);
		this.add(lblLatitude);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setBounds(100, 100, 100, 40);
		this.add(lblName);
		
		JLabel lblLongitude = new JLabel("Longitude");
		lblLongitude.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblLongitude.setHorizontalAlignment(SwingConstants.LEFT);
		lblLongitude.setBounds(100, 400, 100, 40);
		this.add(lblLongitude);
		
		JButton addItemBtn = new JButton("Add Item");
		addItemBtn.setForeground(Color.WHITE);
		addItemBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		addItemBtn.setBackground(new Color(241, 57, 83));
		addItemBtn.setBounds(150, 510, 150, 40);
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
				if(!InputManager.verifyLongitude(txtLongitude.getText())) {
					resultLbl.setForeground(tomato);
					resultLbl.setText( "Invalid location");
				}
				else if(!InputManager.verifyLatitude(txtLatitude.getText())) {
					resultLbl.setForeground(tomato);
					resultLbl.setText( "Invalid location");
				}
				else if (txtName.getText().isBlank()) {
					resultLbl.setForeground(tomato);
					resultLbl.setText( "Invalid warehouse name");
				}
				else {
					Warehouse warehouse = createWarehouseFromFields();
//					warehouseManager.create(warehouse);
					txtLongitude.setText("");
					txtName.setText("");
					txtLatitude.setText("");
					displayPanel.RefreshItemTable();
					resultLbl.setText("Warhouse added successfully!");
					resultLbl.setForeground(emerald);
					}
				}
		});
		add(addItemBtn);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(200, 100, 300, 40);
		add(txtName);
		
		JButton backBtn = new JButton("Back");
		backBtn.setForeground(Color.WHITE);
		backBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		backBtn.setBackground(new Color(241, 57, 83));
		backBtn.setBounds(470, 510, 150, 40);
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
		@Override
		public void mousePressed(MouseEvent e ) {
			switchMainPanel("warehouses");
		}
		});
		add(backBtn);
		
		txtLongitude = new JTextField();
		txtLongitude.setEditable(false);
		txtLongitude.setDisabledTextColor(pastelPink);
		txtLongitude.setColumns(10);
		txtLongitude.setBounds(200, 400, 300, 40);
		add(txtLongitude);
		
		JLabel basketIconLbl = new JLabel("");
		basketIconLbl.setHorizontalAlignment(SwingConstants.CENTER);
		basketIconLbl.setBounds(540, 180, 225, 225);
		basketIconLbl.setIcon(new ImageIcon(AdminAddWarehousesPane.class.getResource("/presentationLayer/images/warehouse.png")));

		add(basketIconLbl);
		
		resultLbl = new JLabel("");
		resultLbl.setHorizontalAlignment(SwingConstants.CENTER);
		resultLbl.setBackground(Color.WHITE);
		resultLbl.setFont(new Font("Javanese Text", Font.PLAIN, 22));
		resultLbl.setBounds(185, 600, 400, 50);
		add(resultLbl);
		
		txtLatitude = new JTextField();
		txtLatitude.setEditable(false);
		txtLatitude.setDisabledTextColor(pastelPink);
		txtLatitude.setColumns(10);
		txtLatitude.setBounds(200, 300, 300, 40);
		add(txtLatitude);
		
		JLabel lblLocationSelect = new JLabel("Choose Warehouse Location:");
		lblLocationSelect.setHorizontalAlignment(SwingConstants.LEFT);
		lblLocationSelect.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblLocationSelect.setBounds(100, 200, 200, 40);
		add(lblLocationSelect);
		
		JLabel lblChooseLocation = new JLabel(locationIcon);
		lblChooseLocation.addMouseListener(new MouseAdapter() {	
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(arrow);

			}
			@Override
			public void mousePressed(MouseEvent e) {
				Location location =addressPicker.pickAddress();
				txtLatitude.setText("" + location.getLatitude());
				txtLongitude.setText("" + location.getLongitude());
			}
		});
		lblChooseLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseLocation.setBounds(310, 190, 60, 60);
		add(lblChooseLocation);
	}
	
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}
	
//	private double strToDouble(String str) {
//		DecimalFormat formatter = new DecimalFormat("#000.00000000");
//		Double dble = new Double(str.valueOf(str));
//		System.out.println(formatter.format(dble));
//	}
	private Warehouse createWarehouseFromFields() {
		Warehouse warehouse = new Warehouse(txtName.getText(),Double.valueOf(txtLongitude.getText()),Double.valueOf(txtLatitude.getText()));
		System.out.println(warehouse.getLongitude());
		return warehouse;
	}
}
