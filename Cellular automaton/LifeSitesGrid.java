import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;

//GUI of the Automata
//jframe grid layout generator.
public class LifeSitesGrid extends JFrame {

	private JPanel[][] gridPanels;	//i gave the class panels matrix character so we could later access each panel and change his color.
	
	//i chose to implement only a costume(lines,columns) grid with no default option.
	public LifeSitesGrid(int lines, int columns, int size) {
		super("Life Game");
		this.setLayout(new GridLayout(lines,columns,1,1));
		this.gridPanels = new JPanel[lines][columns];
		this.setSize(size,size);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		//adding the panels to the jframe grid layout.
		for(int line=0; line<lines; line++)
			for(int column=0; column < columns;column++)
				{
				this.gridPanels[line][column] = new JPanel();
				this.add(this.gridPanels[line][column]);
				}
	}

	public void paintGrid(LifeSitesMatrix lifeMatrix) {
		
		for(int line=0; line<this.gridPanels.length; line++)
			for(int column=0; column < this.gridPanels[line].length;column++)
				this.gridPanels[line][column].setBackground(lifeMatrix.getCellColor(line, column));
		this.repaint();
	}
	
}


