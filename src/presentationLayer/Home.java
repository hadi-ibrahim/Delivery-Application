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
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	
	int xx,xy;
	private JPasswordField passwordField_2;
	private JPasswordField passwordField_3;

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
	
	
	// going to borrow code from a gist to move frame.
	

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
		
		Button button = new Button("Sign Up");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(241, 57, 83));
		button.setBounds(400, 505, 283, 36);
		contentPane.add(button);
		
		textField = new JTextField();
		textField.setBounds(400, 81, 283, 36);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblFirstname = new JLabel("First name");
		lblFirstname.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblFirstname.setBounds(400, 54, 114, 27);
		contentPane.add(lblFirstname);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblEmail.setBounds(400, 343, 54, 27);
		contentPane.add(lblEmail);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(400, 370, 283, 36);
		contentPane.add(textField_1);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(400, 417, 96, 27);
		lblPassword.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		contentPane.add(lblPassword);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(400, 128, 133, 27);
		lblLastName.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		contentPane.add(lblLastName);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(400, 442, 283, 36);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(400, 153, 283, 36);
		contentPane.add(passwordField_1);
		
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
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblAge.setBounds(400, 200, 96, 27);
		contentPane.add(lblAge);
		
		JLabel lblPhoneNumber_1 = new JLabel("Phone Number");
		lblPhoneNumber_1.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblPhoneNumber_1.setBounds(400, 271, 133, 27);
		contentPane.add(lblPhoneNumber_1);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(400, 225, 283, 36);
		contentPane.add(passwordField_2);
		
		passwordField_3 = new JPasswordField();
		passwordField_3.setBounds(400, 296, 283, 36);
		contentPane.add(passwordField_3);
	}
}