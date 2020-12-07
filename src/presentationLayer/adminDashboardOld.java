package presentationLayer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class adminDashboardOld extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminDashboardOld frame = new adminDashboardOld();
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
	public adminDashboardOld() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnManageUsers = new JButton("Manage Users");
		btnManageUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminManageUsers mngUsers = new adminManageUsers();
				mngUsers.setVisible(true);
				 mngUsers.addWindowListener(new java.awt.event.WindowAdapter() {
			            @Override
			            public void windowClosed(java.awt.event.WindowEvent evt) {
			                setVisible(true);
			            }
			        });
				 setVisible(false);
			}
		});
		btnManageUsers.setBounds(157, 56, 124, 21);
		contentPane.add(btnManageUsers);
		
		JButton btnManageItems = new JButton("Manage Items");
		btnManageItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminManageItems mngItems = new adminManageItems();
				mngItems.setVisible(true);
				 mngItems.addWindowListener(new java.awt.event.WindowAdapter() {
			            @Override
			            public void windowClosed(java.awt.event.WindowEvent evt) {
			                setVisible(true);
			            }
			        });
				 setVisible(false);
			}
		});
		btnManageItems.setBounds(157, 106, 124, 21);
		contentPane.add(btnManageItems);
		
		JButton btnManageWarehouses = new JButton("Manage Warehouses");
		btnManageWarehouses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminManageWarehouses mngWarehouses = new adminManageWarehouses();
				mngWarehouses.setVisible(true);
				 mngWarehouses.addWindowListener(new java.awt.event.WindowAdapter() {
			            @Override
			            public void windowClosed(java.awt.event.WindowEvent evt) {
			                setVisible(true);
			            }
			        });
				 setVisible(false);
			}
		});
		btnManageWarehouses.setBounds(157, 156, 124, 21);
		contentPane.add(btnManageWarehouses);
	}
}
