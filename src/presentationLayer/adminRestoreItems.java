package presentationLayer;

import java.awt.BorderLayout;
import businessLogicLayer.ItemManager;
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
import businessLogicLayer.InputManager;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class adminRestoreItems extends JFrame {

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
					adminRestoreItems frame = new adminRestoreItems();
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
	public adminRestoreItems() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(8, 46, 420, 63);
		contentPane.add(scrollPane);
		
		tblItems = new JTable();
		scrollPane.setViewportView(tblItems);
		
		JButton btnRestoreItem = new JButton("Restore");
		btnRestoreItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int column = 0;
			        int row = tblItems.getSelectedRow();
			        if (row >= 0) {
			            String id = tblItems.getModel().getValueAt(row, column).toString();
			            itemManager.restore(Integer.parseInt(id));
			            RefreshItemTable();
			        }
			}
		});
		btnRestoreItem.setBounds(52, 193, 83, 21);
		contentPane.add(btnRestoreItem);
		
		JButton btnCancel = new JButton("Go back");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(228, 193, 83, 21);
		contentPane.add(btnCancel);
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

		for (IDTO dto : itemManager.getAllDisabledItems()) {
			Item item = (Item) dto;
			model.addRow(new Object[] { item.getId(), item.getCategory(), item.getPrice(), item.getDescription(),
					item.getIsDeleted() });
		}
		this.tblItems.setModel(model);
		this.tblItems.getColumnModel().getColumn(0).setWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setMaxWidth(0);

	}

}
