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


import DTO.IDTO;
import DTO.Item;
import DTO.User;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;
import businessLogicLayer.UserManager;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.SwingConstants;

public class AdminManageUsers extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTable tblUsers;
	private DefaultTableModel model;
	private UserManager userManager = new UserManager();
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
	private JLabel restoreIcon;
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
			        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        f.getContentPane().add(new AdminManageUsers(new JPanel()));
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
	public AdminManageUsers(JPanel mainPanel) {
		super();
		this.mainPanel = mainPanel;
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon plusIcon = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE	, 30, tertiaryPink);
		Icon minusIcon = IconFontSwing.buildIcon(FontAwesome.MINUS_CIRCLE, 30, tertiaryPink);
		Icon refreshIcon = IconFontSwing.buildIcon(FontAwesome.REFRESH, 30, tertiaryPink);

		
		JPanel addUsersPanel = new AdminAddUser (mainPanel, this);
		mainPanel.add(addUsersPanel,"addUsers");
		
		AdminRestoreUser restoreUsersPanel = new AdminRestoreUser(mainPanel, this);
		mainPanel.add(restoreUsersPanel, "restoreUsers");
		
		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(50, 70, 680, 500);
		scrollPane.setBackground(Color.WHITE);
		this.add(scrollPane);

		tblUsers= new PinkTable();
		scrollPane.setViewportView(tblUsers);

		RefreshUsersTable();
		
		addIcon = new JLabel(plusIcon);
		addIcon.setBounds(70, 20, 40, 40);
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
				switchMainPanel("addUsers");
				notification.setText("");
			}

		});
		add(addIcon);
		
		deleteIcon = new JLabel(minusIcon);
		deleteIcon.setBounds(120, 20, 40, 40);
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
				int row = tblUsers.getSelectedRow();
				if (row >= 0) {
					String id = tblUsers.getModel().getValueAt(row, column).toString();
					userManager.delete(Integer.parseInt(id));
					RefreshUsersTable();
					restoreUsersPanel.RefreshUsersTable();
				}
				else {
					notification.setText("Select a user to delete");
					notification.setForeground(tomato);
				}
			}

		});
		add(deleteIcon);
		
		restoreIcon = new JLabel(refreshIcon);
		restoreIcon.setBounds(170, 20, 40, 40);
		restoreIcon.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("restoreUsers");
				notification.setText("");
			}
		});
		add(restoreIcon);
		
		notification = new JLabel("");
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		notification.setBounds(90, 600, 600, 50);
		add(notification);

	}

	public void RefreshUsersTable() {
		boolean isEditable[] = { false, false, false, false, false, false, false, false };
		model = new DefaultTableModel(new Object[] { "id", "firstname", "lastname", "age", "email","phone", "role", "isDeleted","password" }, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};
		ArrayList<IDTO> activeUsers = userManager.getAllActiveUsers();
		if(activeUsers.isEmpty()) {
			model.addRow(new Object[] {"","","","","","","","",""});
		}
		for (IDTO dto : userManager.getAllActiveUsers()) {
			User user = (User) dto;
			model.addRow(new Object[] { user.getId(), user.getFirstname(), user.getLastname(), user.getAge(),
					user.getEmail(), user.getPhone(), user.getRole(), user.getIsDeleted(), user.getPassword() });
		}
		
		this.tblUsers.setModel(model);
		this.tblUsers.getColumnModel().getColumn(0).setWidth(0);
		this.tblUsers.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblUsers.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblUsers.getColumnModel().getColumn(7).setWidth(0);
		this.tblUsers.getColumnModel().getColumn(7).setMinWidth(0);
		this.tblUsers.getColumnModel().getColumn(7).setMaxWidth(0);
		this.tblUsers.getColumnModel().getColumn(8).setWidth(0);
		this.tblUsers.getColumnModel().getColumn(8).setMinWidth(0);
		this.tblUsers.getColumnModel().getColumn(8).setMaxWidth(0);
	}
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}

}
