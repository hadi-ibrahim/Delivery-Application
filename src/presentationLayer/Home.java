package presentationLayer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Button;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame {

	private JPanel contentPane;
	private JTextField firstNameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField lastNameField;
	
	int xx,xy;
	private JPasswordField ageField;
	private JPasswordField phoneNumberField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
		

	/**
	 * Create the frame.
	 */
	public Home() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 741, 606);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.PINK);
		panel.setBounds(0, 0, 365, 638);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTitle = new JLabel("Delivery App");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Brush Script MT", Font.ITALIC, 32));
		lblTitle.setForeground(new Color(240, 248, 255));
		lblTitle.setBounds(89, 366, 178, 52);
		panel.add(lblTitle);
		
		JLabel label = new JLabel("");
		
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				 xx = e.getX();
			     xy = e.getY();
			}
		});
		label.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				
				int x = arg0.getXOnScreen();
	            int y = arg0.getYOnScreen();
	            Home.this.setLocation(x - xx, y - xy);  
			}
		});
		label.setBounds(10, 55, 345, 344);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(Home.class.getResource("/presentationLayer/images/food-restaurant.png")));
		panel.add(label);
		
		JLabel lblSlogan = new JLabel("Never have a bad meal");
		lblSlogan.setHorizontalAlignment(SwingConstants.CENTER);
		lblSlogan.setForeground(new Color(240, 248, 255));
		lblSlogan.setFont(new Font("Gabriola", Font.PLAIN, 22));
		lblSlogan.setBounds(89, 434, 185, 38);
		panel.add(lblSlogan);
		
		JLabel lbl_close = new JLabel("X");
		lbl_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				System.exit(0);
			}
		});
		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_close.setForeground(new Color(241, 57, 83));
		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_close.setBounds(703, 0, 37, 27);
		contentPane.add(lbl_close);
		
		JPanel signUpPanel = new JPanel();
		signUpPanel.setBackground(Color.WHITE);
		signUpPanel.setBounds(365, 0, 362, 567);
		contentPane.add(signUpPanel);
		signUpPanel.setLayout(null);
		
		Button signUpBtn = new Button("Sign Up");
		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		signUpBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		signUpBtn.setForeground(Color.WHITE);
		signUpBtn.setBackground(new Color(241, 57, 83));
		signUpBtn.setBounds(35, 505, 283, 36);
		signUpPanel.add(signUpBtn);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(35, 81, 283, 36);
		signUpPanel.add(firstNameField);
		firstNameField.setColumns(10);
		
		JLabel lblFirstname = new JLabel("First name");
		lblFirstname.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblFirstname.setBounds(35, 54, 114, 27);
		signUpPanel.add(lblFirstname);
		
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblEmail.setBounds(35, 343, 54, 27);
		signUpPanel.add(lblEmail);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(35, 370, 283, 36);
		signUpPanel.add(emailField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(35, 417, 96, 27);
		lblPassword.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		signUpPanel.add(lblPassword);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(35, 128, 133, 27);
		lblLastName.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		signUpPanel.add(lblLastName);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(35, 442, 283, 36);
		signUpPanel.add(passwordField);
		
		lastNameField = new JPasswordField();
		lastNameField.setBounds(35, 153, 283, 36);
		signUpPanel.add(lastNameField);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblAge.setBounds(35, 200, 96, 27);
		signUpPanel.add(lblAge);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblPhoneNumber.setBounds(35, 271, 133, 27);
		signUpPanel.add(lblPhoneNumber);
		
		ageField = new JPasswordField();
		ageField.setBounds(35, 225, 283, 36);
		signUpPanel.add(ageField);
		
		phoneNumberField = new JPasswordField();
		phoneNumberField.setBounds(35, 296, 283, 36);
		signUpPanel.add(phoneNumberField);

		
		

	}
}