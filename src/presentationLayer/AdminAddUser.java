package presentationLayer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import DTO.Role;
import DTO.User;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;
import businessLogicLayer.UserManager;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JPasswordField;

public class AdminAddUser extends JPanel {

	
	private JPanel mainPanel;
	UserManager userManager = new UserManager();
	
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
	private JLabel notification;
	private JTextField firstNameTxt;
	private JTextField lastNameTxt;
	private JTextField ageTxt;
	private JTextField phoneNumberTxt;
	private JTextField emailTxt;
	private JPasswordField passwordTxt;
	private JLabel phoneNumberLbl;
	private JLabel emailLbl;
	private JLabel passwordLbl;
	/**
	 * Create the panel.
	 */
	public AdminAddUser(JPanel mainPanel, AdminManageUsers displayPanel) {
		super();
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon backIcon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 30, tertiaryPink);
		setBackground(Color.WHITE);
		this.mainPanel = mainPanel;
		setBounds(100, 100, 780, 670);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		
		JButton addUserBtn = new JButton("Add User");
		addUserBtn.setForeground(Color.WHITE);
		addUserBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		addUserBtn.setBackground(new Color(241, 57, 83));
		addUserBtn.setBounds(220, 525, 150, 40);
		addUserBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				addUserBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				addUserBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			@Override
			public void mousePressed(MouseEvent e ) {
				if(!InputManager.verifyPositiveInteger(ageTxt.getText())) {
					notification.setForeground(tomato);
					notification.setText( "Price must be a positive value.");
				}
				else {
					userManager.create(firstNameTxt.getText(), lastNameTxt.getText(), Integer.parseInt(ageTxt.getText()),
							emailTxt.getText(), String.valueOf(passwordTxt.getPassword()), Role.CUSTOMER, phoneNumberTxt.getText());
					firstNameTxt.setText("");
					lastNameTxt.setText("");
					ageTxt.setText("");
					emailTxt.setText("");
					passwordTxt.setText("");
					phoneNumberTxt.setText("");

					displayPanel.RefreshUsersTable();
					notification.setText("User added successfully!");
					notification.setForeground(emerald);
					}
				}
		});
		add(addUserBtn);
		
		JLabel userIconLbl = new JLabel("");
		userIconLbl.setHorizontalAlignment(SwingConstants.CENTER);
		userIconLbl.setBounds(530, 160, 225, 225);
		userIconLbl.setIcon(new ImageIcon(CustomerDashboard.class.getResource("images/user.png")));

		add(userIconLbl);
		
		notification = new JLabel("");
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setBackground(Color.WHITE);
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		notification.setBounds(50, 600, 500, 50);
		add(notification);
		
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
			@Override
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("users");
			}
		});
		add(backArrow);
		
		firstNameTxt = new JTextField();
		firstNameTxt.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		firstNameTxt.setColumns(10);
		firstNameTxt.setBounds(150, 100, 300, 40);
		add(firstNameTxt);
		
		lastNameTxt = new JTextField();
		lastNameTxt.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lastNameTxt.setBounds(150, 170, 300, 40);
		add(lastNameTxt);
		
		ageTxt = new JTextField();
		ageTxt.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		ageTxt.setBounds(150, 240, 300, 40);
		add(ageTxt);
		
		phoneNumberTxt = new JTextField();
		phoneNumberTxt.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		phoneNumberTxt.setBounds(150, 310, 300, 40);
		add(phoneNumberTxt);
		
		emailTxt = new JTextField();
		emailTxt.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		emailTxt.setColumns(10);
		emailTxt.setBounds(150, 380, 300, 40);
		add(emailTxt);
		
		passwordTxt = new JPasswordField();
		passwordTxt.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		passwordTxt.setBounds(150, 450, 300, 40);
		add(passwordTxt);
		
		JLabel firstNameLbl = new JLabel("First Name");
		firstNameLbl.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		firstNameLbl.setBounds(40, 100, 100, 40);
		add(firstNameLbl);
		
		JLabel lastNameLbl = new JLabel("Last Name");
		lastNameLbl.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lastNameLbl.setBounds(40, 170, 100, 40);
		add(lastNameLbl);
		
		JLabel ageLbl = new JLabel("Age");
		ageLbl.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		ageLbl.setBounds(40, 240, 100, 40);
		add(ageLbl);
		
		phoneNumberLbl = new JLabel("Phone Number");
		phoneNumberLbl.setHorizontalAlignment(SwingConstants.LEFT);
		phoneNumberLbl.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		phoneNumberLbl.setBounds(40, 310, 100, 40);
		add(phoneNumberLbl);
		
		emailLbl = new JLabel("Email");
		emailLbl.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		emailLbl.setBounds(40, 380, 100, 40);
		add(emailLbl);
		
		passwordLbl = new JLabel("Password");
		passwordLbl.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		passwordLbl.setBounds(40, 450, 100, 40);
		add(passwordLbl);
	}
	
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}
}
