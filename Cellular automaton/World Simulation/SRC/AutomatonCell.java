import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

//cell class will represent a cell in the matrix.
public class AutomatonCell extends JPanel{

	//cell state properties
	private static final long serialVersionUID = 1L;
	private String type;
	private String WindDirection;
	private Color cellColor;
	private double temperature;
	private int pollutionLevel;
	private int wind;
	private int cloudsDensity;
	private int rainHalt=0;

	public final String[] DIRECTIONS = {"EAST", "WEST", "NORTH", "SOUTH"};
	
	//for graphic notations
	private JLabel tempLabel;	
	private JLabel windLabel;	
	private JLabel windDirectionLabel;	
	private JLabel pollutionLabel;	
	private JLabel cloudLabels;	
	
	
	//default constructor
	public AutomatonCell() {
		
		//properties of the state
		this.type = "Sea";
		this.cellColor = Color.blue;
		this.temperature = 15;
		this.pollutionLevel = 0;
		this.wind = 1;
		this.cloudsDensity = 10;
		this.WindDirection = DIRECTIONS[0];
		
		//creating and adding the lables to the cell panel
		this.tempLabel = new JLabel();
		this.windLabel = new JLabel();
		this.windDirectionLabel = new JLabel();
		this.pollutionLabel = new JLabel();
		this.cloudLabels = new JLabel();
		this.setLayout(new BorderLayout());
		this.add(this.tempLabel, BorderLayout.NORTH);
		this.add(this.windLabel, BorderLayout.CENTER);
		this.add(this.windDirectionLabel, BorderLayout.SOUTH);
		this.add(this.pollutionLabel, BorderLayout.EAST);
		this.add(this.cloudLabels, BorderLayout.WEST);
		
		}
	
	@Override
	public void paintComponent(Graphics g) 
	{
	        super.paintComponent(g);
	        
	        //set color
	        this.setBackground(cellColor);
			
	        //set temp label
			this.tempLabel.setText("•"+String.valueOf((double)((int)(this.temperature*10))/10));		//rounding for clear view
			this.tempLabel.setHorizontalAlignment(JLabel.CENTER);
			this.tempLabel.setFont(new Font("Verdana", Font.BOLD, 8));
			if(this.temperature>0)
				this.tempLabel.setForeground(Color.red.darker());
			else
				{
				if(this.type.equals("Sea"))
					 this.tempLabel.setForeground(Color.white);
				else this.tempLabel.setForeground(Color.BLUE.brighter());
				}

			//set wind label
			this.windLabel.setText(String.valueOf(this.wind));
			this.windLabel.setHorizontalAlignment(JLabel.CENTER);
			this.windLabel.setFont(new Font("Verdana", Font.BOLD, 9));
			this.windLabel.setForeground(new Color(135,206,250));	//light blue
			
			//set wind direction sign
			switch(WindDirection) {
			  case "EAST":
				  this.windDirectionLabel.setText(">");
				  break;
			  case "WEST":
				  this.windDirectionLabel.setText("<");
				  break;
			  case "NORTH":
				  this.windDirectionLabel.setText("^");
				  break;
			  case "SOUTH":
				  this.windDirectionLabel.setText("v");
				  break;
			  default:
			}
			this.windDirectionLabel.setHorizontalAlignment(JLabel.CENTER);
			this.windDirectionLabel.setFont(new Font("Verdana", Font.BOLD, 8));
			this.windDirectionLabel.setForeground(new Color(135,206,250));	//light blue
			
			
			//set Pollution label
			this.pollutionLabel.setText("%"+String.valueOf(this.pollutionLevel));
			this.pollutionLabel.setFont(new Font("Verdana", Font.BOLD, 7));
			this.pollutionLabel.setForeground(new Color(255,140,0));	//orange

			//set Clouds label
			if(cloudsDensity>0 )
			{
				this.cloudLabels.setText("%"+String.valueOf(this.cloudsDensity));
				this.cloudLabels.setFont(new Font("Verdana", Font.PLAIN, 7));
				this.cloudLabels.setForeground(Color.black);	//light blue
			}
			
			//if its raining:
			if(this.rainHalt>0)
			{
				int pointWidth = this.getWidth()/10;
				int pointHeight = this.getHeight()/10;
				
				for(int x=2 ; x<this.getWidth()-2; x+=(pointWidth*2))
					for(int y=(this.getHeight()/2 +pointWidth*2); y<this.getHeight()-2; y+=(pointWidth*2))
						{
						g.setColor(Color.blue.darker());
						g.fillOval(x, y, pointWidth, pointHeight);
						}
				
				g.setColor(Color.black);
				
				this.rainHalt--;
			}
	}
	
	//set cell propetries due to type
	public void setPropetrtiesByType(String initlizationType) {
		
		switch(initlizationType) {
		  case "Land":
			    this.type = "Land";
			  	this.cellColor = new Color(139,69,19); //maroon color
        		this.temperature = 25;
			  break;
		  case "City":
			  this.type = "City";
			  this.cellColor = new Color(112,128,144); //silver color
			  this.temperature = 30;
			  break;
		  case "Forest":
			  this.type = "Forest";
			  this.cellColor = Color.green.darker();
			  this.temperature = 20;
			  break;
		  case "Ice":
			  this.type = "Ice";
			  this.cellColor = Color.white;
			  this.temperature = -15;
			  this.cloudsDensity=0;
			  this.pollutionLevel=0;
			  break;
		  case "Sea":
			  this.type = "Sea";
			  this.cellColor = Color.blue;
			  this.temperature = 15;
			  break;
			  
		  default:
		}
		
		this.repaint();
	
	}
	
	//appander tools
	public void appandTemp(double d)   {
		this.temperature += d;		
		if(this.temperature<-30)
			this.temperature = -30;
		if(this.temperature>75) 
			this.temperature = 75;
	}
	public void appandClouds(int amount) {		
		this.cloudsDensity += amount;
		if(this.cloudsDensity<0)
			this.cloudsDensity = 0;
		if(this.cloudsDensity>100) 
			this.cloudsDensity = 100;
	}
	public void appandPollution(int amount)   {		
		this.pollutionLevel += amount;	
		if(this.pollutionLevel<0)
			this.pollutionLevel = 0;	
		if(this.pollutionLevel>100) 
			this.pollutionLevel = 100;	
		}
	public void appandWind(int amount)   {		
		this.wind += amount;	
		if(this.wind<1)
			this.wind = 1;	
		if(this.wind>25) 
			this.wind = 25;	
		}
	
	//getters
	public String getCellType() {		return this.type;	}
	public String getCellWindDirection() {		return this.WindDirection;	}
	public Color getCellColor() {		return this.cellColor;	}
	public double getCellTemperature() {		return this.temperature;	}
	public int getPollutionLevel() {		return this.pollutionLevel;	}
	public int getCellWind() {		return this.wind;	}
	public int getCellCloudsDensity() {		return this.cloudsDensity;	}
	public int getRain() {		return this.rainHalt;	}

	//setters
	public void setRain(int amount) {		this.rainHalt=amount;	}
	public void setCellType(String newType) {		this.type = newType;	}
	public void setCellWindDirection(String dir) {		this.WindDirection=dir;	}
	public void setCellColor(Color newColor) {		this.cellColor = newColor;	}
	public void setCellTemperature(int newTemp) {	this.temperature = newTemp;	}	
	public void setPollutionLevel(int newLevel) {
		if(newLevel>=0 && newLevel<=100) 
		this.pollutionLevel = newLevel;	}
	public void setCellWind(int newWind) {
		if(newWind>=0 && newWind<=100) 
		this.wind = newWind;	}	
	public void setCellCloudsDensity(int den) {
		if(den>=0 && den<=100) 
		this.cloudsDensity = den;	}

}
