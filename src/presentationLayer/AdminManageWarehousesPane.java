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
import DTO.Warehouse;
import businessLogicLayer.InputManager;
import businessLogicLayer.WarehouseManager;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminManageWarehousesPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTable tblWarehouses;
	private DefaultTableModel model;
	private WarehouseManager warehouseManager = new WarehouseManager();
	private Color watermelon = new Color(254,127,156);
	private Color lemonade = new Color(253,185,200);
	private Color pastelPink = new Color(255, 209, 220);
	private Color secondaryPink = new Color(241, 57, 83);
	private Color tertiaryPink = new Color(255, 0 ,51);
	private Color whiteShade = new Color(240, 248, 255);
	
	private Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
	private Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);
	
	public JButton addWarehousesBtn;
	public JButton deleteItemBtn;
	public JButton restoreWarehousesBtn;

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
			        f.getContentPane().add(new AdminManageWarehousesPane(new JPanel()));
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
	public AdminManageWarehousesPane(JPanel mainPanel) {
		super();
		this.mainPanel = mainPanel;
		
		JPanel addItemsPanel = new AdminAddWarehousesPane (mainPanel, this);
		mainPanel.add(addItemsPanel,"addWarehouses");
		
		AdminRestoreWarehousesPane restoreWarehousesPanel = new AdminRestoreWarehousesPane(mainPanel, this);
		mainPanel.add(restoreWarehousesPanel, "restoreWarehouses");
		
		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(50, 50, 680, 450);
		scrollPane.setBackground(Color.WHITE);
		this.add(scrollPane);

		tblWarehouses= new PinkTable();
		scrollPane.setViewportView(tblWarehouses);

		RefreshItemTable();
		
		addWarehousesBtn = new JButton("Add");
		addWarehousesBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				addWarehousesBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				addWarehousesBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			@Override
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("addWarehouses");
			}

		});
		
		addWarehousesBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		addWarehousesBtn.setForeground(Color.WHITE);
		addWarehousesBtn.setBackground(secondaryPink);
		addWarehousesBtn.setBounds(100, 550, 150, 40);
		this.add(addWarehousesBtn);
		
		deleteItemBtn = new JButton("Delete");
		deleteItemBtn.setForeground(Color.WHITE);
		deleteItemBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		deleteItemBtn.setBackground(new Color(241, 57, 83));
		deleteItemBtn.setBounds(315, 550, 150, 40);
		deleteItemBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				deleteItemBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				deleteItemBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			@Override
			public void mousePressed(MouseEvent e ) {
				
				int column = 0;
				int row = tblWarehouses.getSelectedRow();
				if (row >= 0) {
					String id = tblWarehouses.getModel().getValueAt(row, column).toString();
					warehouseManager.delete(Integer.parseInt(id));
					RefreshItemTable();
					restoreWarehousesPanel.RefreshItemTable();
				}
			}

		});
		this.add(deleteItemBtn);
		
		restoreWarehousesBtn = new JButton("Restore");
		restoreWarehousesBtn.setForeground(Color.WHITE);
		restoreWarehousesBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		restoreWarehousesBtn.setBackground(new Color(241, 57, 83));
		restoreWarehousesBtn.setBounds(530, 550, 150, 40);
		restoreWarehousesBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				restoreWarehousesBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				restoreWarehousesBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("restoreWarehouses");
				}
		});
		this.add(restoreWarehousesBtn);

	}

	public void RefreshItemTable() {
		boolean isEditable[] = { false, true, true, true, false };
		model = new DefaultTableModel(new Object[] { "id", "name", "latitude", "longitude", "isDeleted" }, 0) {


			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};

		ArrayList<IDTO > activeWarehouses=  warehouseManager.getAllActiveWarehouses();
		if(activeWarehouses.isEmpty()) {
			model.addRow(new Object[] {"", "", "", "", ""});
		}
		for (IDTO dto : activeWarehouses) {
			Warehouse warehouse = (Warehouse) dto;
			model.addRow(new Object[] { warehouse.getId(), warehouse.getName(),warehouse.getLatitude(),warehouse.getLongitude(), warehouse.getIsDeleted()});
		}
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				
				int column = 0;
				int row = tblWarehouses.getSelectedRow();
				if (row >= 0) {
					
					if (!InputManager.verifyLongitude(tblWarehouses.getModel().getValueAt(row, 3).toString())) {
						RefreshItemTable();
						JOptionPane.showMessageDialog(null, "Error: latitude invalid");
					} else if (!InputManager.verifyLongitude(tblWarehouses.getModel().getValueAt(row, 2).toString())) {
						RefreshItemTable();
						JOptionPane.showMessageDialog(null, "Error: invalid longitude");
					} else {
						String id = tblWarehouses.getModel().getValueAt(row, column).toString();
						Warehouse warehouse = warehouseManager.get(Integer.parseInt(id));
						warehouse.setName(tblWarehouses.getModel().getValueAt(row, 1).toString());
						warehouse.setLatitude(Double.parseDouble(tblWarehouses.getModel().getValueAt(row, 2).toString()));
						warehouse.setLongitude(Double.parseDouble(tblWarehouses.getModel().getValueAt(row, 3).toString()));
						warehouse.setIsDeleted(Integer.parseInt(tblWarehouses.getModel().getValueAt(row, 4).toString()));
						warehouseManager.update(warehouse);
						RefreshItemTable();
					}
				}
			}
		});
		this.tblWarehouses.setModel(model);
		this.tblWarehouses.getColumnModel().getColumn(0).setWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(4).setWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(4).setMinWidth(0);
		this.tblWarehouses.getColumnModel().getColumn(4).setMaxWidth(0);
		

	}
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}

}
