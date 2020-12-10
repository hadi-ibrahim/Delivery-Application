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

import javax.swing.table.DefaultTableModel;

import DTO.IDTO;
import DTO.Item;
import DTO.Warehouse;
import businessLogicLayer.ItemManager;
import businessLogicLayer.WarehouseManager;

public class AdminRestoreWarehousesPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 214066076771725348L;
	private JPanel mainPanel;
	private JTable tblItems;
	private DefaultTableModel model;
	private ItemManager itemManager = new ItemManager();
	private WarehouseManager warehouseManager = new WarehouseManager();
	private Color watermelon = new Color(254,127,156);
	private Color lemonade = new Color(253,185,200);
	private Color pastelPink = new Color(255, 209, 220);
	private Color secondaryPink = new Color(241, 57, 83);
	private Color tertiaryPink = new Color(255, 0 ,51);
	private Color whiteShade = new Color(240, 248, 255);
	
	private Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
	private Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);
	
	public JButton restoreItemBtn;
	public JButton backBtn;

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
			        f.setTitle("Sometimes Red, Sometimes Blue");
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
	public AdminRestoreWarehousesPane(JPanel mainPanel, AdminManageWarehousesPane warehousesPane) {
		super();
		this.mainPanel = mainPanel;
		
		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(50, 50, 680, 450);
		scrollPane.setBackground(Color.WHITE);
		this.add(scrollPane);

		tblItems= new PinkTable();
		scrollPane.setViewportView(tblItems);

		RefreshItemTable();
		
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
			        int row = tblItems.getSelectedRow();
			        if (row >= 0) {
			            String id = tblItems.getModel().getValueAt(row, column).toString();
			            warehouseManager.restore(Integer.parseInt(id));
			            RefreshItemTable();
			            warehousesPane.RefreshItemTable();
			        }
			     }

		});
		
		restoreItemBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		restoreItemBtn.setForeground(Color.WHITE);
		restoreItemBtn.setBackground(secondaryPink);
		restoreItemBtn.setBounds(140, 550, 150, 40);
		this.add(restoreItemBtn);
		
		backBtn = new JButton("Back");
		backBtn.setForeground(Color.WHITE);
		backBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		backBtn.setBackground(new Color(241, 57, 83));
		backBtn.setBounds(480, 550, 150, 40);
		backBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				backBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				backBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("warehouses");
			}
		});
		this.add(backBtn);

	}

	public void RefreshItemTable() {
		boolean isEditable[] = { false, true, true, true, false };
		model = new DefaultTableModel(new Object[] { "id", "name", "latitude", "longitude", "isDeleted" }, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5871568570449186724L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};

		ArrayList<IDTO> disabledWarehouses = warehouseManager.getAllDisabledWarehouses();
		if (disabledWarehouses.isEmpty()) {
			model.addRow(new Object[] {"","","","",""});
		}
		else {
			for (IDTO dto : disabledWarehouses ) {
				Warehouse warehouse = (Warehouse) dto;
				model.addRow(new Object[] { warehouse.getId(), warehouse.getName(), warehouse.getLatitude(), warehouse.getLongitude(),
						warehouse.getIsDeleted() });
			}
		}
		this.tblItems.setModel(model);
		this.tblItems.getColumnModel().getColumn(0).setWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(4).setMaxWidth(0);

	}
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}

}
