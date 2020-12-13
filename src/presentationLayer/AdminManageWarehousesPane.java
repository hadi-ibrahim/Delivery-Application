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
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Icon;

public class AdminManageWarehousesPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private AdminManageWarehousesPane self= this;
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
	public JButton manageStockBtn;
	private JLabel addIcon;
	private JLabel deleteIcon;
	private JLabel restoreIcon;

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
		
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon plusIcon = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE	, 30, tertiaryPink);
		Icon minusIcon = IconFontSwing.buildIcon(FontAwesome.MINUS_CIRCLE, 30, tertiaryPink);
		Icon refreshIcon = IconFontSwing.buildIcon(FontAwesome.REFRESH, 30, tertiaryPink);

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
		scrollPane.setBounds(50, 70, 680, 500);
		scrollPane.setBackground(Color.WHITE);
		this.add(scrollPane);

		tblWarehouses= new PinkTable();
		scrollPane.setViewportView(tblWarehouses);

		RefreshItemTable();
		
		manageStockBtn = new JButton("Manage stock");
		manageStockBtn.setForeground(Color.WHITE);
		manageStockBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		manageStockBtn.setBackground(new Color(241, 57, 83));
		manageStockBtn.setBounds(300, 600, 150, 40);
		manageStockBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				manageStockBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				manageStockBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				int column = 0;
				int row = tblWarehouses.getSelectedRow();
				if (row >= 0) {
					String id = tblWarehouses.getModel().getValueAt(row, column).toString();
					Warehouse warehouse= warehouseManager.get(Integer.parseInt(id));
					JPanel manageStockPanel = new AdminManageStockPane(mainPanel, self, warehouse);
					mainPanel.add(manageStockPanel,"addStock");
					switchMainPanel("addStock");
				}			
			}
		});
		add(manageStockBtn);
		
		JLabel warehouseNotifier = new JLabel("");
		warehouseNotifier.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		warehouseNotifier.setHorizontalAlignment(SwingConstants.CENTER);
		warehouseNotifier.setBounds(315, 601, 365, 40);
		add(warehouseNotifier);
		
		addIcon = new JLabel(plusIcon);
		addIcon.setBounds(70, 11, 40, 40);
		addIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(arrow);

			}
			@Override
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("addWarehouses");
			}

		});
		add(addIcon);
		
		deleteIcon = new JLabel(minusIcon);
		deleteIcon.setBounds(120, 11, 40, 40);
		deleteIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
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
		add(deleteIcon);
		
		restoreIcon = new JLabel(refreshIcon);
		restoreIcon.setBounds(170, 11, 40, 40);
		restoreIcon.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("restoreWarehouses");
			}
		});
		add(restoreIcon);

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
