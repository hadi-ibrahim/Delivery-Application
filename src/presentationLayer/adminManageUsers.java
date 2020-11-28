package presentationLayer;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import DTO.IDTO;
import DTO.User;
import businessLogicLayer.InputManager;
import businessLogicLayer.UserManager;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class adminManageUsers extends JFrame {

	private JPanel contentPane;
	private JTable tblUsers;
	private DefaultTableModel model;
	private UserManager userManager = new UserManager();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminManageUsers frame = new adminManageUsers();
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
	public adminManageUsers() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 769, 528);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 54, 691, 138);
		contentPane.add(scrollPane);
		
		tblUsers = new JTable();
		scrollPane.setViewportView(tblUsers);
		
		JButton btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int column = 0;
			        int row = tblUsers.getSelectedRow();
			        if (row >= 0) {
			            String id = tblUsers.getModel().getValueAt(row, column).toString();
			            userManager.delete(Integer.parseInt(id));
			            RefreshUserTable();
			        }
			}
		});
		btnDeleteUser.setBounds(114, 249, 114, 34);
		contentPane.add(btnDeleteUser);
		
		JButton btnRestoreUser = new JButton("Restore User");
		btnRestoreUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminRestoreUsers frame = new adminRestoreUsers();
				frame.setVisible(true);
				 frame.addWindowListener(new java.awt.event.WindowAdapter() {
			            @Override
			            public void windowClosed(java.awt.event.WindowEvent evt) {
			                setVisible(true);
			                RefreshUserTable();
			            }
			        });
				 setVisible(false);
			}
		});
		btnRestoreUser.setBounds(347, 249, 114, 34);
		contentPane.add(btnRestoreUser);
		
		RefreshUserTable();
	}

	public void RefreshUserTable() {
		boolean isEditable[] = { false, true, true, true, true, true, true, false };
		model = new DefaultTableModel(new Object[] { "id", "firstname", "lastname", "age", "email","phone", "role", "isDeleted","password" }, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};

		for (IDTO dto : userManager.getAllActiveUsers()) {
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
						RefreshUserTable();
						JOptionPane.showMessageDialog(null, "Age must be a positive integer");
					}
					else if(!InputManager.verifyRole(tblUsers.getModel().getValueAt(row, 6).toString())){
						RefreshUserTable();
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
						RefreshUserTable();
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
}
