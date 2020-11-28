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
import DTO.Item;
import DTO.User;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class adminManageItems extends JFrame {

	private JPanel contentPane;
	private JTable tblItems;
	private DefaultTableModel model;
	private ItemManager itemManager = new ItemManager();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminManageItems frame = new adminManageItems();
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
	public adminManageItems() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(8, 45, 420, 43);
		contentPane.add(scrollPane);

		tblItems = new JTable();
		scrollPane.setViewportView(tblItems);

		JButton btnItemAdd = new JButton("Add Item");
		btnItemAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminAddItem addItem = new adminAddItem();
				addItem.setVisible(true);
				addItem.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosed(java.awt.event.WindowEvent evt) {
						setVisible(true);
						RefreshItemTable();
					}
				});
				setVisible(false);
			}
		});
		btnItemAdd.setBounds(41, 138, 83, 21);
		contentPane.add(btnItemAdd);

		JButton btnItemDelete = new JButton("Delete Item");
		btnItemDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = tblItems.getSelectedRow();
				if (row >= 0) {
					String id = tblItems.getModel().getValueAt(row, column).toString();
					itemManager.delete(Integer.parseInt(id));
					RefreshItemTable();
				}
			}
		});
		btnItemDelete.setBounds(180, 138, 83, 21);
		contentPane.add(btnItemDelete);

		JButton btnItemRestore = new JButton("Restore Item");
		btnItemRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminRestoreItems restoreItems = new adminRestoreItems();
				restoreItems.setVisible(true);
				restoreItems.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosed(java.awt.event.WindowEvent evt) {
						setVisible(true);
						RefreshItemTable();
					}
				});
				setVisible(false);
			}
		});
		btnItemRestore.setBounds(305, 138, 89, 21);
		contentPane.add(btnItemRestore);

		RefreshItemTable();
	}

	public void RefreshItemTable() {
		boolean isEditable[] = { false, true, true, true, false };
		model = new DefaultTableModel(new Object[] { "id", "category", "price", "description", "isDeleted" }, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};

		for (IDTO dto : itemManager.getAllActiveItems()) {
			Item item = (Item) dto;
			model.addRow(new Object[] { item.getId(), item.getCategory(), item.getPrice(), item.getDescription(),
					item.getIsDeleted() });
		}
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int column = 0;
				int row = tblItems.getSelectedRow();
				if (row >= 0) {
					if (!InputManager.verifyPositiveDouble(tblItems.getModel().getValueAt(row, 2).toString())) {
						RefreshItemTable();
						JOptionPane.showMessageDialog(null, "Price must be a positive double.");
					} else if (!InputManager.verifyCategory(tblItems.getModel().getValueAt(row, 1).toString())) {
						RefreshItemTable();
						JOptionPane.showMessageDialog(null, "Category must be either WEAPON, GROCERY or FOOD.");
					} else {

						String id = tblItems.getModel().getValueAt(row, column).toString();
						Item item = itemManager.get(Integer.parseInt(id));
						item.setCategory(tblItems.getModel().getValueAt(row, 1).toString());
						item.setPrice(Double.parseDouble(tblItems.getModel().getValueAt(row, 2).toString()));
						item.setDescription(tblItems.getModel().getValueAt(row, 3).toString());
						item.setIsDeleted(Integer.parseInt(tblItems.getModel().getValueAt(row, 4).toString()));
						itemManager.update(item);
						RefreshItemTable();
					}
				}
			}
		});
		this.tblItems.setModel(model);
		this.tblItems.getColumnModel().getColumn(0).setWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setMaxWidth(0);

	}
}
