package presentationLayer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class SignupFrm extends JFrame {

	private JPanel contentPane;
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JTextField txtFirstname;
	private JTextField txtLastname;
	private JTextField txtAge;
	private JTextField txtPhone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignupFrm frame = new SignupFrm();
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
	public SignupFrm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Signup");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(0, 0, 436, 31);
		contentPane.add(lblNewLabel);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(216, 41, 76, 19);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(216, 70, 76, 19);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		txtFirstname = new JTextField();
		txtFirstname.setBounds(216, 99, 76, 19);
		contentPane.add(txtFirstname);
		txtFirstname.setColumns(10);
		
		txtLastname = new JTextField();
		txtLastname.setBounds(216, 128, 76, 19);
		contentPane.add(txtLastname);
		txtLastname.setColumns(10);
		
		txtAge = new JTextField();
		txtAge.setBounds(216, 157, 76, 19);
		contentPane.add(txtAge);
		txtAge.setColumns(10);
		
		txtPhone = new JTextField();
		txtPhone.setBounds(216, 186, 76, 19);
		contentPane.add(txtPhone);
		txtPhone.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(63, 41, 42, 13);
		contentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(63, 74, 42, 13);
		contentPane.add(lblPassword);
		
		JLabel lblFirstname = new JLabel("Firstnamel");
		lblFirstname.setBounds(63, 103, 42, 13);
		contentPane.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setBounds(63, 132, 42, 13);
		contentPane.add(lblLastname);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setBounds(63, 161, 42, 13);
		contentPane.add(lblAge);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(63, 190, 42, 13);
		contentPane.add(lblPhone);
	}
}
