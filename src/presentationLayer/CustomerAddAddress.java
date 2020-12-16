
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

import DTO.Address;
import DTO.Category;
import DTO.Item;
import DTO.Location;
import DTO.Warehouse;
import Helpers.SessionHelper;
import businessLogicLayer.AddressManager;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;
import businessLogicLayer.WarehouseManager;
import businessLogicLayer.LocationManager.LocationManager;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public class CustomerAddAddress extends JPanel {

	
	private JPanel mainPanel;
	private AddressManager addressManager = new AddressManager();
	private LocationManager addressPicker = new LocationManager();
	
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
	private JTextField txtCity;
	private JTextField txtLongitude;
	private JLabel resultLbl;
	private JTextField txtLatitude;
	private JLabel lblVerified;
	private JTextField txtStreet;
	private JTextField txtBuilding;
	private JTextField textFloor;
	private JTextField txtFloor;
	/**
	 * Create the panel.
	 */
	public CustomerAddAddress(JPanel mainPanel, CustomerManageAddress displayPanel) {
		super();
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon backIcon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 30, tertiaryPink);
		Icon locationIcon = IconFontSwing.buildIcon(FontAwesome.MAP_MARKER, 60, tertiaryPink);
		Icon completedIcon = IconFontSwing.buildIcon(FontAwesome.CHECK_CIRCLE, 60, emerald);


		setBackground(Color.WHITE);
		this.mainPanel = mainPanel;
		setBounds(100, 100, 780, 670);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblCity.setHorizontalAlignment(SwingConstants.LEFT);
		lblCity.setBounds(100, 150, 100, 40);
		this.add(lblCity);
		
		JButton addWarehouseBtn = new JButton("Add");
		addWarehouseBtn.setForeground(Color.WHITE);
		addWarehouseBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		addWarehouseBtn.setBackground(new Color(241, 57, 83));
		addWarehouseBtn.setBounds(275, 500, 150, 40);
		addWarehouseBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				addWarehouseBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				addWarehouseBtn.setBackground(secondaryPink);
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

				else {
					Address address = createAddressFromFields();
					addressManager.create(address);
					txtLongitude.setText("");
					txtCity.setText("");
					txtLatitude.setText("");
					txtStreet.setText("");
					txtBuilding.setText("");
					txtFloor.setText("");
					displayPanel.RefreshAddressTable();
					resultLbl.setText("Address saved successfully!");
					resultLbl.setForeground(emerald);
					lblVerified.setVisible(false);
					}
				}
		});
		add(addWarehouseBtn);
		
		txtCity = new JTextField();
		txtCity.setColumns(10);
		txtCity.setBounds(200, 150, 300, 40);
		add(txtCity);
		
		txtLongitude = new JTextField();
		txtLongitude.setEditable(false);
		txtLongitude.setDisabledTextColor(pastelPink);
		txtLongitude.setColumns(10);
		txtLongitude.setBounds(470, 58, 300, 40);
		txtLongitude.setVisible(false);

		add(txtLongitude);
		
		JLabel basketIconLbl = new JLabel("");
		basketIconLbl.setHorizontalAlignment(SwingConstants.CENTER);
		basketIconLbl.setBounds(540, 180, 225, 225);
		basketIconLbl.setIcon(new ImageIcon(CustomerAddAddress.class.getResource("/presentationLayer/images/address.png")));

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
		txtLatitude.setBounds(470, 109, 300, 40);
		txtLatitude.setVisible(false);
		add(txtLatitude);
		
		JLabel lblLocationSelect = new JLabel("Choose address Location:");
		lblLocationSelect.setHorizontalAlignment(SwingConstants.LEFT);
		lblLocationSelect.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblLocationSelect.setBounds(100, 350, 200, 40);
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
				lblVerified.setVisible(true);
				
			}
		});
		lblChooseLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseLocation.setBounds(321, 340, 60, 60);
		add(lblChooseLocation);
		
		JLabel backArrow = new JLabel(backIcon);
		backArrow.setBounds(670, 20, 40, 40);
		backArrow.addMouseListener(new MouseAdapter() {
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
				switchMainPanel("addresses");
			}
		});
		add(backArrow);
		
		lblVerified = new JLabel(completedIcon);
		lblVerified.setHorizontalAlignment(SwingConstants.CENTER);
		lblVerified.setBounds(400, 340, 60, 60);
		lblVerified.setVisible(false);
		add(lblVerified);
		
		txtStreet = new JTextField();
		txtStreet.setColumns(10);
		txtStreet.setBounds(200, 200, 300, 40);
		add(txtStreet);
		
		JLabel lblStreet = new JLabel("Street");
		lblStreet.setHorizontalAlignment(SwingConstants.LEFT);
		lblStreet.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblStreet.setBounds(100, 201, 100, 40);
		add(lblStreet);
		
		txtBuilding = new JTextField();
		txtBuilding.setColumns(10);
		txtBuilding.setBounds(200, 250, 300, 40);
		add(txtBuilding);
		
		JLabel lblBuilding = new JLabel("Building");
		lblBuilding.setHorizontalAlignment(SwingConstants.LEFT);
		lblBuilding.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblBuilding.setBounds(100, 252, 100, 40);
		add(lblBuilding);
		
		
		JLabel lblFloor = new JLabel("Floor");
		lblFloor.setHorizontalAlignment(SwingConstants.LEFT);
		lblFloor.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblFloor.setBounds(100, 300, 100, 40);
		add(lblFloor);
		
		txtFloor = new JTextField();
		txtFloor.setColumns(10);
		txtFloor.setBounds(200, 300, 300, 40);
		add(txtFloor);
	}
	
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}
	
	private Address createAddressFromFields() {
		Address address = new Address();
		address.setCity(txtCity.getText());
		address.setLocation(new Location(Double.valueOf(txtLongitude.getText()),Double.valueOf(txtLatitude.getText())));
		address.setStreet(txtStreet.getText());
		address.setBuilding(txtBuilding.getText());
		address.setFloor(txtFloor.getText());
		address.setIdUser(SessionHelper.isLoggedIn.getId());
		System.out.println(address.getIdUser());
		return address;
	}
}
