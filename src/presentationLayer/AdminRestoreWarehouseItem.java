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
import DTO.WarehouseItem;
import businessLogicLayer.ItemManager;
import businessLogicLayer.StockManager;
import businessLogicLayer.WarehouseManager;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class AdminRestoreWarehouseItem extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 214066076771725348L;
	private JPanel mainPanel;
	private JTable tblItems;
	private DefaultTableModel model;
	private ItemManager itemManager = new ItemManager();
	private StockManager stockManager = new StockManager();
	private Color watermelon = new Color(254,127,156);
	private Color lemonade = new Color(253,185,200);
	private Color pastelPink = new Color(255, 209, 220);
	private Color secondaryPink = new Color(241, 57, 83);
	private Color tertiaryPink = new Color(255, 0 ,51);
	private Color whiteShade = new Color(240, 248, 255);
	private Color tomato = new Color(255, 99, 71);
	private Color emerald  = new Color(80, 220, 100);
	
	private Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
	private Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);
	
	public JButton restoreItemBtn;
	private JLabel backArrow;
	private JLabel notification;

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
			        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        f.getContentPane().add(new AdminManageItems(new JPanel()));
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
	public AdminRestoreWarehouseItem(JPanel mainPanel, AdminManageStock stockPane) {
		super();
		this.mainPanel = mainPanel;
		IconFontSwing.register(FontAwesome.getIconFont());
		Icon backIcon = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 30, tertiaryPink);
		
		this.setBounds(100, 100, 780, 670);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(50, 70, 680, 470);
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
			            stockManager.restore(Integer.parseInt(id));
			            RefreshItemTable();
			            stockPane.RefreshItemTable();
			            notification.setText("");
			        }
			        else {
			        	notification.setText("Select a Warehouse Item to restore");
			        	notification.setForeground(tomato);
			        }
			     }

		});
		
		restoreItemBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		restoreItemBtn.setForeground(Color.WHITE);
		restoreItemBtn.setBackground(secondaryPink);
		restoreItemBtn.setBounds(310, 560, 150, 40);
		this.add(restoreItemBtn);
		
		backArrow = new JLabel(backIcon);
		backArrow.setBounds(670, 20, 40, 40);
		backArrow.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(arrow);

			}
			public void mousePressed(MouseEvent e ) {
				switchMainPanel("manageStock");
	            notification.setText("");
			}
		});
		add(backArrow);
		
		notification = new JLabel("");
		notification.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setBounds(50, 610, 680, 40);
		add(notification);

	}

	public void RefreshItemTable() {
		boolean isEditable[] = { false, false, false, false, false, false };
		model = new DefaultTableModel(new Object[] { "id", "category", "price", "description", "quantity", "isDeleted" }, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEditable[column];
			}
		};
		
		ArrayList<IDTO> warehouseItems = stockManager.getAllDisabledWarehouseItems();
		if(warehouseItems.isEmpty()) {
			model.addRow(new Object[] {"","","","","",""});
		}
		for (IDTO dto : warehouseItems) {
			WarehouseItem warehouseItem = (WarehouseItem) dto;
			Item item = itemManager.get(warehouseItem.getIdItem());
			model.addRow(new Object[] { warehouseItem.getId(), item.getCategory(), item.getPrice(), item.getDescription(),warehouseItem.getQuantity(),
					warehouseItem.getIsDeleted() });
		}
		
		this.tblItems.setModel(model);
		this.tblItems.getColumnModel().getColumn(0).setWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(0).setMaxWidth(0);
		this.tblItems.getColumnModel().getColumn(5).setWidth(0);
		this.tblItems.getColumnModel().getColumn(5).setMinWidth(0);
		this.tblItems.getColumnModel().getColumn(5).setMaxWidth(0);	

	}
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}
}
