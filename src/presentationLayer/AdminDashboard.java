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
import java.awt.Dimension;
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
import java.awt.CardLayout;

public class AdminDashboard extends JFrame {

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
	private Color pastelPink = new Color(255, 209, 220);
	private Color secondaryPink = new Color(241, 57, 83);
	private Color tertiaryPink = new Color(255, 0 ,51);
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
					CustomerDashboard frame = new CustomerDashboard();
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
	public AdminDashboard() {
		this.setLocationByPlatform(true);
		this.setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		IconFontSwing.register(FontAwesome.getIconFont());
		IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
		
		Icon usersIcon = IconFontSwing.buildIcon(FontAwesome.USER_PLUS, 30, whiteShade);
		Icon itemsIcon = IconFontSwing.buildIcon(FontAwesome.CART_PLUS, 30, whiteShade);
		Icon ordersIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.RECEIPT, 30, whiteShade);
		Icon warehouseIcon = IconFontSwing.buildIcon(FontAwesome.BUILDING, 30, whiteShade);
		Icon logoutIcon = IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 30, whiteShade);
		Icon roleIcon = IconFontSwing.buildIcon(FontAwesome.WRENCH, 30, whiteShade);
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
		mainPanel.setLayout(new CardLayout(0, 0));
		
		AdminManageItemsPane adminManageItemsPane = new AdminManageItemsPane(mainPanel);
		mainPanel.add(adminManageItemsPane, "items");
		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		mainPanel.add(panel, "1");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.PINK);
		mainPanel.add(panel_1, "2");

		switchMainPanel("items");
		
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
		
		activePaneLbl = new JLabel("Order Items ");
		activePaneLbl.setHorizontalAlignment(SwingConstants.CENTER);
		activePaneLbl.setForeground(new Color(240, 248, 255));
		activePaneLbl.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		activePaneLbl.setBounds(55, 0, 675, 50);
		topBar.add(activePaneLbl);
		
		sidePanel = new JPanel();
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
						
						JPanel manageItemsPanel = new JPanel();
						manageItemsPanel.addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {
								switchMainPanel("items");								 
							}
						});		
						manageItemsPanel.setBounds(0, 250, 300, 70);
						sidePanel.add(manageItemsPanel);
						manageItemsPanel.setLayout(null);
						
						JLabel ItemsIconLbl = new JLabel(itemsIcon);
						ItemsIconLbl.setBounds(50, 10, 50, 50);
						manageItemsPanel.add(ItemsIconLbl);
						
						JLabel ManageItemsLbl = new JLabel("Manage Items ");
						ManageItemsLbl.setBounds(100, 10, 150, 50);
						manageItemsPanel.add(ManageItemsLbl);
						ManageItemsLbl.setForeground(whiteShade);
						ManageItemsLbl.setHorizontalAlignment(SwingConstants.CENTER);
						ManageItemsLbl.setFont(new Font("Javanese Text", Font.PLAIN, 16));
						setActivePanel(manageItemsPanel);
						addPanelEffects(manageItemsPanel);
						
						JPanel manageWarehousePanel = new JPanel();
						manageWarehousePanel.addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {
								switchMainPanel("warehouse");
							}
						});		
						addPanelEffects(manageWarehousePanel);
						manageWarehousePanel.setLayout(null);
						manageWarehousePanel.setBackground(Color.PINK);
						manageWarehousePanel.setBounds(0, 320, 300, 70);
						sidePanel.add(manageWarehousePanel);
						
						JLabel ManageWarehouseIconLbl = new JLabel(warehouseIcon);
						ManageWarehouseIconLbl.setBounds(50, 10, 50, 50);
						manageWarehousePanel.add(ManageWarehouseIconLbl);
						
						JLabel manageWarehouseLbl = new JLabel("Manage Warehouses");
						manageWarehouseLbl.setBounds(100, 10, 150, 50);
						manageWarehousePanel.add(manageWarehouseLbl);
						manageWarehouseLbl.setForeground(whiteShade);
						manageWarehouseLbl.setHorizontalAlignment(SwingConstants.CENTER);
						manageWarehouseLbl.setFont(new Font("Javanese Text", Font.PLAIN, 16));
						
						JPanel ManageUsersPanel = new JPanel();
						ManageUsersPanel.addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {
								switchMainPanel("users");
							}
						});		
						addPanelEffects(ManageUsersPanel);
						ManageUsersPanel.setLayout(null);
						ManageUsersPanel.setBackground(Color.PINK);
						ManageUsersPanel.setBounds(0, 390, 300, 70);
						sidePanel.add(ManageUsersPanel);
						
						JLabel ManageUsersIcon = new JLabel(usersIcon);
						ManageUsersIcon.setBounds(50, 10, 50, 50);
						ManageUsersPanel.add(ManageUsersIcon);
						
						JLabel ManageUsersLbl = new JLabel("ManageUsers");
						ManageUsersLbl.setBounds(100, 10, 150, 50);
						ManageUsersPanel.add(ManageUsersLbl);
						ManageUsersLbl.setForeground(whiteShade);
						ManageUsersLbl.setHorizontalAlignment(SwingConstants.CENTER);
						ManageUsersLbl.setFont(new Font("Javanese Text", Font.PLAIN, 16));
						
						JPanel viewOrdersPanel = new JPanel();
						viewOrdersPanel.addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {
								switchMainPanel("Orders");
							}
						});		
						addPanelEffects(viewOrdersPanel);
						viewOrdersPanel.setLayout(null);
						viewOrdersPanel.setBackground(Color.PINK);
						viewOrdersPanel.setBounds(0, 460, 300, 70);
						sidePanel.add(viewOrdersPanel);
						
						JLabel viewOrdersIconLbl = new JLabel(ordersIcon);
						viewOrdersIconLbl.setBounds(50, 10, 50, 50);
						viewOrdersPanel.add(viewOrdersIconLbl);
						
						JLabel viewOrderLbl = new JLabel("View Orders");
						viewOrderLbl.setBounds(100, 10, 150, 50);
						viewOrdersPanel.add(viewOrderLbl);
						viewOrderLbl.setHorizontalAlignment(SwingConstants.CENTER);
						viewOrderLbl.setForeground(whiteShade);
						viewOrderLbl.setFont(new Font("Javanese Text", Font.PLAIN, 16));
						viewOrderLbl.setHorizontalAlignment(SwingConstants.CENTER);
						viewOrderLbl.setForeground(whiteShade);
						viewOrderLbl.setFont(new Font("Javanese Text", Font.PLAIN, 16));
						
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
						separator.setBounds(50, 560, 200, 17);
						sidePanel.add(separator);
						
						JSeparator separator_1 = new JSeparator();
						separator_1.setBounds(50, 220, 200, 17);
						sidePanel.add(separator_1);
						separator_1.setForeground(whiteShade);
						
						JPanel userInfoPanel = new JPanel();
						userInfoPanel.setBounds(40, 90, 260, 70);
						userInfoPanel.setBackground(Color.PINK);
						sidePanel.add(userInfoPanel);
						userInfoPanel.setLayout(null);
						
						JLabel usernameLbl = new JLabel("Welcome, " + SessionHelper.isLoggedIn.getFirstname());
						usernameLbl.setBounds(60, 10, 120, 50);
						userInfoPanel.add(usernameLbl);
						setLblFont(usernameLbl);
						
						roleIconLbl = new JLabel(roleIcon);
						roleIconLbl.setBounds(0, 10, 40, 40);
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
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}
	

}




