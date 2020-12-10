package presentationLayer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import DTO.Category;
import DTO.Item;
import businessLogicLayer.InputManager;
import businessLogicLayer.ItemManager;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public class AdminAddItemsPane extends JPanel {

	
	private JPanel mainPanel;
	JComboBox<String> comboBoxCategory;
	ItemManager itemManager = new ItemManager();
	
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -7017182885009918297L;
	private JTextField txtPrice;
	private JTextField txtDescription;
	private JLabel resultLbl;
	/**
	 * Create the panel.
	 */
	public AdminAddItemsPane(JPanel mainPanel, AdminManageItemsPane displayPanel) {
		super();
		setBackground(Color.WHITE);
		this.mainPanel = mainPanel;
		setBounds(100, 100, 780, 670);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		
		comboBoxCategory = new JComboBox<String>();
		comboBoxCategory.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		comboBoxCategory.setBounds(200, 100, 300, 40);
		addCategoryTypes();
		this.add(comboBoxCategory);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblCategory.setHorizontalAlignment(SwingConstants.LEFT);
		lblCategory.setBounds(100, 100, 100, 40);
		this.add(lblCategory);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lblPrice.setBounds(100, 200, 100, 40);
		this.add(lblPrice);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Javanese Text", Font.PLAIN, 14));
		lblDescription.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescription.setBounds(100, 300, 100, 40);
		this.add(lblDescription);
		
		JButton addItemBtn = new JButton("Add Item");
		addItemBtn.setForeground(Color.WHITE);
		addItemBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		addItemBtn.setBackground(new Color(241, 57, 83));
		addItemBtn.setBounds(150, 466, 150, 40);
		addItemBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				addItemBtn.setBackground(tertiaryPink);
				setCursor(pointer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				addItemBtn.setBackground(secondaryPink);
				setCursor(arrow);

			}
			@Override
			public void mousePressed(MouseEvent e ) {
				if(!InputManager.verifyPositiveDouble(txtPrice.getText())) {
					resultLbl.setForeground(tomato);
					resultLbl.setText( "Price must be a positive value.");
				}
				else {
					itemManager.create(new Item(txtDescription.getText(),comboBoxCategory.getSelectedItem().toString(),Double.parseDouble(txtPrice.getText())));
					txtDescription.setText("");
					txtPrice.setText("");
					displayPanel.RefreshItemTable();
					resultLbl.setText("Item added successfully!");
					resultLbl.setForeground(emerald);
					}
				}
		});
		add(addItemBtn);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(200, 200, 300, 40);
		add(txtPrice);
		
		JButton backBtn = new JButton("Back");
		backBtn.setForeground(Color.WHITE);
		backBtn.setFont(new Font("Javanese Text", Font.PLAIN, 16));
		backBtn.setBackground(new Color(241, 57, 83));
		backBtn.setBounds(470, 466, 150, 40);
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
		@Override
		public void mousePressed(MouseEvent e ) {
			switchMainPanel("items");
		}
		});
		add(backBtn);
		
		txtDescription = new JTextField();
		txtDescription.setColumns(10);
		txtDescription.setBounds(200, 300, 300, 40);
		add(txtDescription);
		
		JLabel basketIconLbl = new JLabel("");
		basketIconLbl.setHorizontalAlignment(SwingConstants.CENTER);
		basketIconLbl.setBounds(540, 110, 225, 225);
		basketIconLbl.setIcon(new ImageIcon(CustomerDashboard.class.getResource("images/basket.png")));

		add(basketIconLbl);
		
		resultLbl = new JLabel("");
		resultLbl.setHorizontalAlignment(SwingConstants.CENTER);
		resultLbl.setBackground(Color.WHITE);
		resultLbl.setFont(new Font("Javanese Text", Font.PLAIN, 22));
		resultLbl.setBounds(185, 550, 400, 50);
		add(resultLbl);
	}
	
	public void addCategoryTypes() {
		for(Category C : Category.values()) {
			this.comboBoxCategory.addItem(C.toString());
		}
	}
	
	
	private void switchMainPanel(String name) {
		CardLayout cards =(CardLayout) mainPanel.getLayout();
		cards.show(mainPanel, name);
	}
}
