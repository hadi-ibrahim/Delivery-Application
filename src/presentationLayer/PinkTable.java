package presentationLayer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class PinkTable extends JTable {
	  private static final Color ALTERNATE_ONE = Color.YELLOW;
	  private static final Color ALTERNATE_TWO = Color.GRAY;

	  private Color alternateColor = ALTERNATE_ONE;
	  private Color whiteColor = Color.WHITE;
	  
		private Color watermelon = new Color(254,127,156);
		private Color lemonade = new Color(253,185,200);
		private Color pastelPink = new Color(255, 209, 220);
		private Color secondaryPink = new Color(241, 57, 83);
		private Color tertiaryPink = new Color(255, 0 ,51);
		private Color whiteShade = new Color(240, 248, 255);

	  @Override
	  public Component prepareRenderer(TableCellRenderer renderer, int row, int col){	
			  Component comp = super.prepareRenderer(renderer, row, col);
			  //even index, selected or not selected
			  if (row % 2 == 0 && !isCellSelected(row, col)) {
				  comp.setBackground(pastelPink);
			  } 
			  else if(isCellSelected(row, col))
				  comp.setBackground(watermelon);
			  else {
				  comp.setBackground(Color.WHITE);
			  }
			  
				this.setFillsViewportHeight(true);
				JTableHeader header = this.getTableHeader();
				header.setBackground(lemonade);
			  return comp;
		  	}	  
}