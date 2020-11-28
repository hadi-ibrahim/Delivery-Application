package presentationLayer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class adminRestoreUsers extends JFrame {

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
					adminRestoreUsers frame = new adminRestoreUsers();
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
	public adminRestoreUsers() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(8, 56, 420, 92);
		contentPane.add(scrollPane);
		
		tblUsers = new JTable();
		scrollPane.setViewportView(tblUsers);
		RefreshUserTable();
		
		JButton btnUserRestore = new JButton("Restore User");
		btnUserRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int column = 0;
			        int row = tblUsers.getSelectedRow();
			        if (row >= 0) {
			            String id = tblUsers.getModel().getValueAt(row, column).toString();
			            userManager.restore(Integer.parseInt(id));
			            RefreshUserTable();
			        }
			}
		});
		btnUserRestore.setBounds(154, 178, 113, 31);
		contentPane.add(btnUserRestore);
	}
	
	public void RefreshUserTable() {
		boolean isEditable[] = { false, false, false, false, false, false ,false , false };
		model = new DefaultTableModel(new Object[] { "id", "firstname", "lastname", "age", "email", "phone", "role", "isDeleted" }, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};

		for (IDTO dto : userManager.getAllDisabledUsers()) {
			User user = (User) dto;
			model.addRow(new Object[] { user.getId(), user.getFirstname(), user.getLastname(), user.getAge(),user.getEmail(),user.getPhone(),user.getRole(),user.getIsDeleted() });
		}
		this.tblUsers.setModel(model);
		this.tblUsers.getColumnModel().getColumn(0).setWidth(0);
		this.tblUsers.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblUsers.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblUsers.getColumnModel().getColumn(7).setWidth(0);
		this.tblUsers.getColumnModel().getColumn(7).setMinWidth(0);
		this.tblUsers.getColumnModel().getColumn(7).setMaxWidth(0);

	}
}
