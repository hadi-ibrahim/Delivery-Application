package presentationLayer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Helpers.SessionHelper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JSeparator;

public class DriverDashboard extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel activePanelRef = null;
	private JPanel sidePanel;
	private JPanel mainPanel;
	private JPanel topBar;
	
	private Color watermelon = new Color(254,127,156);
	private Color lemonade = new Color(253,185,200);
//	private Color pastelPink = new Color(255, 209, 220);
	private Color secondaryPink = new Color(241, 57, 83);
//	private Color tertiaryPink = new Color(255, 0 ,51);
	private Color whiteShade = new Color(240, 248, 255);
	
	private Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
	private Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);
	
	private JLabel roleIconLbl;
	private JLabel activePaneLbl;
	
	private boolean sideNavActive = true;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerDashboard frame = new CustomerDashboard(100,100);
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
	public DriverDashboard(int x , int y) {
		this.setLocationByPlatform(true);
		this.setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 1080, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		IconFontSwing.register(FontAwesome.getIconFont());
		IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
		
		Icon orderIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.RECEIPT, 30, whiteShade);
		Icon trackIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.LOCATION_ON, 30, whiteShade);
		Icon logoutIcon = IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 30, whiteShade);
		Icon roleIcon = IconFontSwing.buildIcon(FontAwesome.MOTORCYCLE, 30, whiteShade);
		Icon closeIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.CLOSE, 30, whiteShade);
		Icon hamburgerIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.MENU, 30, whiteShade);
		
		topBar = new JPanel();
		topBar.setBounds(300, 0, 780, 50);
		topBar.setBackground(secondaryPink);
		contentPane.add(topBar);
		topBar.setLayout(null);
		
		mainPanel = new JPanel();
		mainPanel.setBounds(300, 50, 780, 670);
		mainPanel.setBackground(Color.WHITE);
		contentPane.add(mainPanel);
		
		JLabel hamburgerLbl = new JLabel(hamburgerIcon);
		hamburgerLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				toggleSideNav();
			}
		});
		hamburgerLbl.setBounds(5, 5, 40, 40);
		topBar.add(hamburgerLbl);
		setHoverable(hamburgerLbl);

		JLabel CloseBtnLbl = new JLabel(closeIcon);
		CloseBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		CloseBtnLbl.setBounds(740, 5, 40, 40);
		topBar.add(CloseBtnLbl);
		setHoverable(CloseBtnLbl);
		
		activePaneLbl = new JLabel("Pending Orders");
		activePaneLbl.setHorizontalAlignment(SwingConstants.CENTER);
		activePaneLbl.setForeground(new Color(240, 248, 255));
		activePaneLbl.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		activePaneLbl.setBounds(55, 0, 675, 50);
		topBar.add(activePaneLbl);
		
		sidePanel = new JPanel();
		//		sidePanel.setBackground(Color.PINK);
				sidePanel.setBackground(Color.PINK);
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

				
						sidePanel.setBounds(0, 0, 300, 720);
						contentPane.add(sidePanel);
						sidePanel.setLayout(null);
						
						JLabel roadLbl = new JLabel("");
						roadLbl.setBounds(0, 579, 180, 130);
						roadLbl.setIcon(new ImageIcon(CustomerDashboard.class.getResource("images/road.png")));
						sidePanel.add(roadLbl);						
						JLabel lblTitle = new JLabel("Delivery App");
						lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
						lblTitle.setFont(new Font("Brush Script MT", Font.ITALIC, 42));
						lblTitle.setForeground(new Color(240, 248, 255));
						lblTitle.setBounds(25, 10, 250, 52);
						sidePanel.add(lblTitle);
						
						JPanel checkPendingOrdersPanel = new JPanel();
						checkPendingOrdersPanel.addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {
								// this.mainPanel = new ItemsPanel() ;
							}
						});		
						checkPendingOrdersPanel.setBounds(0, 320, 300, 70);
						sidePanel.add(checkPendingOrdersPanel);
						checkPendingOrdersPanel.setLayout(null);
						
						JLabel pendingOrdersIcon = new JLabel(orderIcon);
						pendingOrdersIcon.setBounds(40, 10, 50, 50);
						checkPendingOrdersPanel.add(pendingOrdersIcon);
						
						JLabel pendingOrdersLbl = new JLabel("Pending Orders");
						pendingOrdersLbl.setBounds(100, 10, 170, 50);
						checkPendingOrdersPanel.add(pendingOrdersLbl);
						pendingOrdersLbl.setForeground(whiteShade);
						pendingOrdersLbl.setHorizontalAlignment(SwingConstants.CENTER);
						pendingOrdersLbl.setFont(new Font("Javanese Text", Font.PLAIN, 16));
						setActivePanel(checkPendingOrdersPanel);
						addPanelEffects(checkPendingOrdersPanel);
						
						JPanel trackOrderPanel = new JPanel();
						trackOrderPanel.addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {
								// this.mainPanel = new ItemsPanel() ;
							}
						});		
						addPanelEffects(trackOrderPanel);
						trackOrderPanel.setLayout(null);
						trackOrderPanel.setBackground(Color.PINK);
						trackOrderPanel.setBounds(0, 390, 300, 70);
						sidePanel.add(trackOrderPanel);
						
						JLabel trackOrderIconLbl = new JLabel(trackIcon);
						trackOrderIconLbl.setBounds(40, 10, 50, 50);
						trackOrderPanel.add(trackOrderIconLbl);
						
						JLabel trackOrderLbl = new JLabel("Track Order");
						trackOrderLbl.setBounds(100, 10, 170, 50);
						trackOrderPanel.add(trackOrderLbl);
						trackOrderLbl.setForeground(whiteShade);
						trackOrderLbl.setHorizontalAlignment(SwingConstants.CENTER);
						trackOrderLbl.setFont(new Font("Javanese Text", Font.PLAIN, 16));
						
						JPanel logoutPanel = new JPanel();
						logoutPanel.setBounds(150, 620, 150, 70);
						logoutPanel.setBackground(Color.PINK);
						setHoverable(logoutPanel);
						logoutPanel.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseEntered(MouseEvent e) {
								logoutPanel.setBackground(lemonade);
							}
							@Override
							public void mouseExited(MouseEvent e) {
								logoutPanel.setBackground(Color.PINK);
							}
							@Override
							public void mousePressed(MouseEvent e) {
								logoutPanel.setBackground(watermelon);
								SessionHelper.signOut();
								disposeFrame();
							}
						});		
						sidePanel.add(logoutPanel);
						logoutPanel.setLayout(null);
						
						JLabel logoutIconLbl = new JLabel(logoutIcon);
						logoutIconLbl.setBounds(0, 10, 50, 50);
						logoutPanel.add(logoutIconLbl);
						
						JLabel logoutLbl = new JLabel("Logout");
						logoutLbl.setBounds(60, 10, 90, 50);
						logoutLbl.setForeground(whiteShade);
						logoutLbl.setFont(new Font("Javanese Text", Font.PLAIN, 16));
						logoutPanel.add(logoutLbl);
						
						JSeparator separator = new JSeparator();
						separator.setForeground(whiteShade);
						separator.setBounds(50, 490, 200, 17);
						sidePanel.add(separator);
						
						JSeparator separator_1 = new JSeparator();
						separator_1.setBounds(50, 290, 200, 17);
						sidePanel.add(separator_1);
						separator_1.setForeground(whiteShade);
						
						JPanel userInfoPanel = new JPanel();
						userInfoPanel.setBounds(0, 90, 300, 70);
						userInfoPanel.setBackground(Color.PINK);
						sidePanel.add(userInfoPanel);
						userInfoPanel.setLayout(null);
						
						JLabel usernameLbl = new JLabel("Welcome, " + SessionHelper.isLoggedIn.getFirstname());
						usernameLbl.setBounds(60, 10, 230, 50);
						userInfoPanel.add(usernameLbl);
						setLblFont(usernameLbl);
						
						roleIconLbl = new JLabel(roleIcon);
						roleIconLbl.setBounds(40, 10, 40, 40);
						userInfoPanel.add(roleIconLbl);
						
						
						
	}
	
	private void addPanelEffects(JPanel panel) {
		setHoverable(panel);
		addPanelHoverEvents(panel);
		addPanelActiveEvents(panel);
	}
	
	private void setHoverable(Component component) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(pointer);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(arrow);				
			}
		});
	}
	
	private void addPanelHoverEvents(JPanel panel) {
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setBackground(lemonade);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				
				if(isActivePanel(panel))
					panel.setBackground(watermelon);
				else panel.setBackground(Color.PINK);				
			}
		});
	}
	
	private void addPanelActiveEvents(JPanel panel) {
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				activePanelRef.setBackground(Color.PINK);
				setActivePanel(panel);
			}
		});
	}
	
	private void setActivePanel(JPanel panel) {
		this.activePanelRef = panel;
		panel.setBackground(watermelon);
		JLabel panelName = (JLabel) panel.getComponent(1);
		activePaneLbl.setText(panelName.getText());
	}
	
	private boolean isActivePanel (JPanel panel) {
		return panel.equals(activePanelRef);
	}
	
	private void setLblFont(JLabel lbl) {
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setForeground(whiteShade);
		lbl.setFont(new Font("Javanese Text", Font.PLAIN, 18));
	}
	
	private void toggleSideNav( ) {
		this.sideNavActive = ! this.sideNavActive;
		sidePanel.setVisible(sideNavActive);	
		recalculatePanelsWidth();
	}
	
	private void recalculatePanelsWidth() {
		if(this.sideNavActive) {
			this.setBounds(this.getX()-300, this.getY(), 1080, 720);
			topBar.setBounds(300, 0, 780, 50);
			mainPanel.setBounds(300, 50, 780, 670);
		}
		else {
			this.setBounds(this.getX() + 300, this.getY(), 780, 720);
			topBar.setBounds(0, 0, 780, 50);
			mainPanel.setBounds(0, 50, 780, 670);
		}
		
	}
	
	private void disposeFrame() {
		this.dispose();
	}
}




