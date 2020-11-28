package presentationLayer;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import DTO.Category;
import DTO.Item;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class adminAddItem extends JFrame {

	private JPanel contentPane;
	private JTextField txtDescription;
	private JTextField txtPrice;
	JComboBox comboBoxCategory;
	ItemManager itemManager = new ItemManager();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminAddItem frame = new adminAddItem();
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
	public adminAddItem() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtDescription = new JTextField();
		txtDescription.setBounds(186, 151, 76, 19);
		contentPane.add(txtDescription);
		txtDescription.setColumns(10);
		
		txtPrice = new JTextField();
		txtPrice.setBounds(186, 93, 76, 19);
		contentPane.add(txtPrice);
		txtPrice.setColumns(10);
		
		comboBoxCategory = new JComboBox();
		comboBoxCategory.setBounds(186, 33, 76, 21);
		addCategoryTypes();
		contentPane.add(comboBoxCategory);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategory.setBounds(51, 37, 62, 13);
		contentPane.add(lblCategory);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setBounds(51, 96, 62, 13);
		contentPane.add(lblPrice);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescription.setBounds(51, 154, 62, 13);
		contentPane.add(lblDescription);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!InputManager.verifyPositiveDouble(txtPrice.getText()))
					JOptionPane.showMessageDialog(null, "Price must be double.");
				else itemManager.create(new Item(txtDescription.getText(),comboBoxCategory.getSelectedItem().toString(),Double.parseDouble(txtPrice.getText())));
			}
		});
		btnAddItem.setBounds(100, 232, 103, 21);
		contentPane.add(btnAddItem);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(236, 232, 103, 21);
		contentPane.add(btnCancel);
	}
	
	public void addCategoryTypes() {
		for(Category C : Category.values()) {
			this.comboBoxCategory.addItem(C.toString());
		}
	}
}
