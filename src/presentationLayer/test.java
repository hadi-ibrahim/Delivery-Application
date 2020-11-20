package presentationLayer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helpers.SessionHelper;
import Repositories.RepoUser;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class test extends JFrame {

	private JPanel contentPane;
	private RepoUser repoUser = new RepoUser();
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
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
	public test() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SessionHelper.signOut();
				dispose();
			}
		});
		btnNewButton.setBounds(259, 168, 83, 21);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setText(SessionHelper.isLoggedIn.getFirstname());
		
		textField.setBounds(153, 60, 76, 19);
		contentPane.add(textField);
		textField.setColumns(10);
	}
}
