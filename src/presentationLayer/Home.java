package presentationLayer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DTO.Role;
import DTO.User;
import Helpers.SessionHelper;
import businessLogicLayer.Registration;

import java.awt.Color;
import java.awt.Cursor;
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
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame {

	private JLabel lblSignUp = new JLabel("Sign Up");
	private JLabel lblSignIn = new JLabel("Sign In");
	private JPanel contentPane;
	private JTextField firstNameField;
	private JTextField emailFieldSignUp;
	private JPasswordField passwordFieldSignUp;
	private JTextField emailFieldSignIn;
	private JPasswordField passwordFieldSignIn;
	private JTextField lastNameField;
	private JPanel signUpPanel = new JPanel();
	private JPanel signInPanel = new JPanel();
	private Registration registration = new Registration();

	private JTextField ageField;
	private JTextField phoneNumberField;
	private boolean signUpFrameActive =true;
	
	private Color secondaryPink = new Color(241, 57, 83);
	private Color tertiaryPink = new Color(255, 0 ,51);
	private Color whiteShade = new Color(240, 248, 255);

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
		setBounds(100, 100, 764, 632);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.addMouseMotionListener(new MouseMotionListener() {
            int lastX, lastY;

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen(), y = e.getYOnScreen();

                setLocation(getLocationOnScreen().x + x - lastX, getLocationOnScreen().y + y - lastY);
                lastX = x;
                lastY = y;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                lastX = e.getXOnScreen();
                lastY = e.getYOnScreen();
            }
        });
		


		lblSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSignUp.setForeground(secondaryPink);
				setCursorAsPointer();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!signUpFrameActive)
					lblSignUp.setForeground(Color.BLACK);
				else lblSignUp.setForeground(tertiaryPink);
				setCursorAsNormal();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblSignUp.setForeground(tertiaryPink);
				signUpFrameActive = true;
				lblSignIn.setForeground(Color.BLACK);
				togglePanels();
			}
		});
		lblSignIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSignIn.setForeground(secondaryPink);
				setCursorAsPointer();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(signUpFrameActive)
					lblSignIn.setForeground(Color.BLACK);
				else lblSignIn.setForeground(tertiaryPink);
				setCursorAsNormal();


			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblSignIn.setForeground(tertiaryPink);
				signUpFrameActive = false;
				lblSignUp.setForeground(Color.BLACK);
				togglePanels();
			}
		});
		
		JLabel lbl_close = new JLabel("\u00D7");
		lbl_close.setBounds(730, 0, 37, 27);
		contentPane.add(lbl_close);
		lbl_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {		
				disposeFrame();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_close.setForeground(tertiaryPink);
				setCursorAsPointer();

			}
			@Override
			public void mouseExited(MouseEvent e) {
				lbl_close.setForeground(secondaryPink);
				setCursorAsNormal();

			}
		});
		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_close.setForeground(secondaryPink);
		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSignIn.setBounds(465, 22, 64, 38);
		lblSignIn.setFont(new Font("Gabriola", Font.PLAIN, 22));
		contentPane.add(lblSignIn);
		lblSignUp.setForeground(tertiaryPink);
		lblSignUp.setBounds(585, 22, 64, 38);
		lblSignUp.setFont(new Font("Gabriola", Font.PLAIN, 22));
		contentPane.add(lblSignUp);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.PINK);
		panel.setBounds(0, 0, 365, 650);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTitle = new JLabel("Delivery App");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Brush Script MT", Font.ITALIC, 42));
		lblTitle.setForeground(whiteShade);
		lblTitle.setBounds(70, 365, 224, 52);
		panel.add(lblTitle);
		
		JLabel lblSlogan = new JLabel("We put the \"good\"");
		lblSlogan.setHorizontalAlignment(SwingConstants.CENTER);
		lblSlogan.setForeground(whiteShade);
		lblSlogan.setFont(new Font("Gabriola", Font.PLAIN, 26));
		lblSlogan.setBounds(86, 461, 185, 53);
		panel.add(lblSlogan);
		
		JLabel label = new JLabel("");
		
		label.setBounds(20, 54, 345, 344);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(Home.class.getResource("/presentationLayer/images/food-restaurant.png")));
		panel.add(label);
		
		JLabel lblInGoodsDelivery = new JLabel("in goods delivery");
		lblInGoodsDelivery.setHorizontalAlignment(SwingConstants.CENTER);
		lblInGoodsDelivery.setForeground(whiteShade);
		lblInGoodsDelivery.setFont(new Font("Gabriola", Font.PLAIN, 26));
		lblInGoodsDelivery.setBounds(87, 494, 185, 53);
		panel.add(lblInGoodsDelivery);
		signInPanel.setBackground(Color.WHITE);
		signInPanel.setLayout(null);
		
		JLabel lblEmailSignIn = new JLabel("Email");
		lblEmailSignIn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblEmailSignIn.setBounds(51, 131, 114, 27);
		signInPanel.add(lblEmailSignIn);
		
		emailFieldSignIn = new JTextField();
		emailFieldSignIn.setColumns(10);
		emailFieldSignIn.setBounds(51, 169, 283, 36);
		signInPanel.add(emailFieldSignIn);
		
		JLabel lblPasswordSignIn = new JLabel("Password");
		lblPasswordSignIn.setBounds(51, 251, 67, 27);
		lblPasswordSignIn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		signInPanel.add(lblPasswordSignIn);
		
		passwordFieldSignIn = new JPasswordField();
		passwordFieldSignIn.setBounds(51, 289, 283, 36);
		signInPanel.add(passwordFieldSignIn);
		
		signInPanel.setBounds(365, 0, 383, 600);
		contentPane.add(signInPanel);
		signInPanel.setVisible(false);
		
		signUpPanel.setBackground(Color.WHITE);
		signUpPanel.setBounds(365, 0, 383, 600);
		contentPane.add(signUpPanel);
		signUpPanel.setLayout(null);
		
		Button signUpBtn = new Button("Sign Up");
		signUpBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				signUpBtn.setBackground(tertiaryPink);
				setCursorAsPointer();

			}
			@Override
			public void mouseExited(MouseEvent e) {
				signUpBtn.setBackground(secondaryPink);
				setCursorAsNormal();

			}
			@Override
			public void mousePressed(MouseEvent e) {
				signUp();
			}
		});
		
		signUpBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		signUpBtn.setForeground(Color.WHITE);
		signUpBtn.setBackground(secondaryPink);
		signUpBtn.setBounds(51, 554, 283, 36);
		signUpPanel.add(signUpBtn);
		
		Button signInBtn = new Button("Sign In");
		signInBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				signInBtn.setBackground(tertiaryPink);
				setCursorAsPointer();

			}
			@Override
			public void mouseExited(MouseEvent e) {
				signInBtn.setBackground(secondaryPink);
				setCursorAsNormal();

			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(signIn()) {
					if(SessionHelper.isLoggedIn.getRole() == Role.CUSTOMER) {
						CustomerDashboard frame = new CustomerDashboard();
						frame.setVisible(true);
						 frame.addWindowListener(new java.awt.event.WindowAdapter() {
					            @Override
					            public void windowClosed(java.awt.event.WindowEvent evt) {
					                setVisible(true);
					                SessionHelper.signOut();
					            }
					        });
					}
					else if (SessionHelper.isLoggedIn.getRole() == Role.DRIVER) {
						driverDashboard frame = new driverDashboard();
						frame.setVisible(true);
						 frame.addWindowListener(new java.awt.event.WindowAdapter() {
					            @Override
					            public void windowClosed(java.awt.event.WindowEvent evt) {
					                setVisible(true);
					                SessionHelper.signOut();
					            }
					        });
					}
					else if (SessionHelper.isLoggedIn.getRole()== Role.ADMIN) {
						adminDashboard frame = new adminDashboard();
						frame.setVisible(true);
						 frame.addWindowListener(new java.awt.event.WindowAdapter() {
					            @Override
					            public void windowClosed(java.awt.event.WindowEvent evt) {
					                setVisible(true);
					                SessionHelper.signOut();
					            }
					        });
					}
					setVisible(false);
				}
			}
		});
		
		signInBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		signInBtn.setForeground(Color.WHITE);
		signInBtn.setBackground(secondaryPink);
		signInBtn.setBounds(51, 400, 283, 36);
		signInPanel.add(signInBtn);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(51, 130, 283, 36);
		signUpPanel.add(firstNameField);
		firstNameField.setColumns(10);
		
		JLabel lblFirstname = new JLabel("First name");
		lblFirstname.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblFirstname.setBounds(51, 103, 114, 27);
		signUpPanel.add(lblFirstname);
		
		
		JLabel lblEmailSignUp = new JLabel("Email");
		lblEmailSignUp.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblEmailSignUp.setBounds(51, 392, 54, 27);
		signUpPanel.add(lblEmailSignUp);
		
		emailFieldSignUp = new JTextField();
		emailFieldSignUp.setColumns(10);
		emailFieldSignUp.setBounds(51, 419, 283, 36);
		signUpPanel.add(emailFieldSignUp);
		
		JLabel lblPasswordSignUp = new JLabel("Password");
		lblPasswordSignUp.setBounds(51, 466, 96, 27);
		lblPasswordSignUp.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		signUpPanel.add(lblPasswordSignUp);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(51, 177, 133, 27);
		lblLastName.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		signUpPanel.add(lblLastName);
		
		passwordFieldSignUp = new JPasswordField();
		passwordFieldSignUp.setBounds(51, 491, 283, 36);
		signUpPanel.add(passwordFieldSignUp);
		
		lastNameField = new JTextField();
		lastNameField.setBounds(51, 202, 283, 36);
		signUpPanel.add(lastNameField);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblAge.setBounds(51, 249, 96, 27);
		signUpPanel.add(lblAge);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		lblPhoneNumber.setBounds(51, 320, 133, 27);
		signUpPanel.add(lblPhoneNumber);
		
		ageField = new JTextField();
		ageField.setBounds(51, 274, 283, 36);
		signUpPanel.add(ageField);
		
		phoneNumberField = new JTextField();
		phoneNumberField.setBounds(51, 345, 283, 36);
		signUpPanel.add(phoneNumberField);

	}
	
	private void togglePanels( ) {
		signUpPanel.setVisible(signUpFrameActive);
		signInPanel.setVisible(!signUpFrameActive);
	}
	
	private void setCursorAsPointer() {
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        this.setCursor(cursor);
	}
	private void setCursorAsNormal() {
        Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
        this.setCursor(cursor);
	}
	
	@SuppressWarnings("deprecation")
	private void signUp() {
		User user = new User ();
		try {
		user.setFirstname(this.firstNameField.getText());
		user.setLastname(lastNameField.getText());
		user.setAge(Integer.parseInt(ageField.getText()));
		user.setPhone(this.phoneNumberField.getText());
		user.setEmail(this.emailFieldSignUp.getText());
		user.setPassword(this.passwordFieldSignUp.getText());
		user.setRole(Role.CUSTOMER);
		user= registration.signUp(user);
		if (user!= null) {
			System.out.println("signup successful! ");
		}
		else System.out.println("unable to signup");
		} catch (Exception e ) {
			System.out.println("unable to signup. make sure of values");
		}
	}
	@SuppressWarnings("deprecation")
	private boolean signIn() {
		User user ;
		String password, email;
		try {
		email= this.emailFieldSignIn.getText();
		password= this.passwordFieldSignIn.getText();
		user= registration.signIn(email, password);
		if (user!= null) {
			System.out.println("sign in successful! ");
			return true;
		}
		else System.out.println("unable to sign in");
		} catch (Exception e ) {
			System.out.println("unable to sign in. make sure of values");
		}
		return false;
	}
	
	private void disposeFrame() {
		this.dispose();
	}
}