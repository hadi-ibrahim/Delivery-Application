package presentationLayer;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import DTO.IDTO;
import DTO.Item;
import DTO.User;
import DTO.Warehouse;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;
import businessLogicLayer.UserManager;
import businessLogicLayer.WarehouseManager;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class AdminRestoreUser extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 214066076771725348L;
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
	
	public JButton restoreItemBtn;
	private JLabel backArrow;
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
			        f.getContentPane().add(new AdminManageItemsPane(new JPanel()));
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
	public AdminRestoreUser(JPanel mainPanel, AdminManageUsers usersPane) {
		super();
		this.mainPanel = mainPanel;
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon backIcon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 30, tertiaryPink);
		
		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(50, 70, 680, 470);
		scrollPane.setBackground(Color.WHITE);
		this.add(scrollPane);

		tblUsers= new PinkTable();
		scrollPane.setViewportView(tblUsers);

		RefreshUsersTable();
		
		restoreItemBtn = new JButton("Restore");
		restoreItemBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				restoreItemBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				restoreItemBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			@Override
			public void mousePressed(MouseEvent e ) {
				 int column = 0;
			        int row = tblUsers.getSelectedRow();
			        if (row >= 0) {
			            String id = tblUsers.getModel().getValueAt(row, column).toString();
			            userManager.restore(Integer.parseInt(id));
			            RefreshUsersTable();
			            usersPane.RefreshUsersTable();
			            notification.setText("");
			        }
			        else {
			        	notification.setText("Select a User to restore");
			        	notification.setForeground(tomato);
			        }
			     }

		});
		
		restoreItemBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		restoreItemBtn.setForeground(Color.WHITE);
		restoreItemBtn.setBackground(secondaryPink);
		restoreItemBtn.setBounds(310, 560, 150, 40);
		this.add(restoreItemBtn);
		
		backArrow = new JLabel(backIcon);
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
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("users");
	            notification.setText("");
			}
		});
		add(backArrow);
		
		notification = new JLabel("");
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setBounds(50, 610, 680, 40);
		add(notification);

	}

	public void RefreshUsersTable() {
		boolean isEditable[] = { false, true, true, true, true, true, true, false };
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
		ArrayList<IDTO> activeUsers = userManager.getAllDisabledUsers();
		if(activeUsers.isEmpty()) {
			model.addRow(new Object[] {"","","","","","","","",""});
		}
		for (IDTO dto : userManager.getAllDisabledUsers()) {
			User user = (User) dto;
			model.addRow(new Object[] { user.getId(), user.getFirstname(), user.getLastname(), user.getAge(),
					user.getEmail(), user.getPhone(), user.getRole(), user.getIsDeleted(), user.getPassword() });
		}
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int column = 0;
				int row = tblUsers.getSelectedRow();
				if (row >= 0) {
					if (!InputManager.verifyPositiveInteger(tblUsers.getModel().getValueAt(row, 3).toString())) {
						RefreshUsersTable();
						JOptionPane.showMessageDialog(null, "Age must be a positive integer");
					}
					else if(!InputManager.verifyRole(tblUsers.getModel().getValueAt(row, 6).toString())){
						RefreshUsersTable();
						JOptionPane.showMessageDialog(null, "Role must be either ADMIN, DRIVER or CUSTOMER.");
					}
					else {
					
						String id = tblUsers.getModel().getValueAt(row, column).toString();
						User user = userManager.get(Integer.parseInt(id));
						user.setFirstname(tblUsers.getModel().getValueAt(row, 1).toString());
						user.setLastname(tblUsers.getModel().getValueAt(row, 2).toString());
						user.setAge(Integer.parseInt(tblUsers.getModel().getValueAt(row, 3).toString()));
						user.setEmail(tblUsers.getModel().getValueAt(row, 4).toString());
						user.setPhone(tblUsers.getModel().getValueAt(row, 5).toString());
						user.setRole(tblUsers.getModel().getValueAt(row, 6).toString());
						user.setIsDeleted(Integer.parseInt(tblUsers.getModel().getValueAt(row, 7).toString()));
						user.setPassword(tblUsers.getModel().getValueAt(row, 8).toString());
						userManager.update(user);
						RefreshUsersTable();
					}
				}
			}
		});
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
