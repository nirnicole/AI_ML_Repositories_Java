import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//READ ME:
//M.v.c design - This is the VIEW file.
//here you will find all the graphics.
//
//->	for model go to MODEL file (Automaton.jave)
//->	for control flow go to CONTROL file (WorldSimulation.jave)
//
//description:
//this class responsible for displaying the automaton in its current state.
//GUI of the Automata
//jframe grid layout generator.
//
public class AutomatonGUI extends JFrame {

	//components
	private static final long serialVersionUID = 1L;
	private AutomatonCell[][] gridPanels;
	public Automaton data;
	
	private String initlizationType = "Land";
	private JPanel westSideButtons = new JPanel();			//will contain all jbuttons
	private JPanel containerEastSideButtons = new JPanel();			//will contain all jbuttons
	private JPanel eastSideButtons = new JPanel();			//will contain all jbuttons
	private JPanel simulationBoard = new JPanel();	// will contain all polynom related jbuttons

	private JButton cmdSetSea = new JButton("Set Sea");
	private JButton cmdSetLand = new JButton("Set Land");
	private JButton cmdSetCity = new JButton("Set City");
	private JButton cmdSetIce = new JButton("Set Ice");
	private JButton cmdSetForest = new JButton("Set Forest");
	
	private JButton cmdSetRun = new JButton("Simulation On");		//RUN OR PAUSE
	private JButton cmdSetRain = new JButton("Rain On");
	private JButton cmdLog = new JButton("Logger Off");
	private JButton cmdSpeedUp = new JButton("Speed>>");
	private JButton cmdSpeedDown = new JButton("Speed<<");
	
	private JPanel speedPanel = new JPanel();
	private JPanel speedDataPanel = new JPanel();
	private JPanel speedCmdPanel = new JPanel();
	private JLabel speedLabel = new JLabel("Frame Rate:");
	private JLabel speedLabelCount = new JLabel();
	private JLabel buildersLabel = new JLabel();

	private JPanel landsPanel = new JPanel();
	private JPanel populationPanel = new JPanel();
	private JPanel forestsPanel = new JPanel();
	private JPanel icebergsPanel = new JPanel();
	private JPanel seasPanel = new JPanel();
	
	private JLabel categLabel = new JLabel("Inventory ");
	private JLabel landsLabel = new JLabel("Lands:");
	private JLabel populationLabel = new JLabel("Cities:");
	private JLabel forestsLabel = new JLabel("Forests:");
	private JLabel icebergsLabel = new JLabel("Icebergs:");
	private JLabel seasLabel = new JLabel("Seas:");
	private JLabel landsLabelCount = new JLabel("0");
	private JLabel populationLabelCount = new JLabel("0");
	private JLabel forestsLabelCount = new JLabel("0");
	private JLabel icebergsLabelCount = new JLabel("0");
	private JLabel seasLabelCount = new JLabel("0");
	

	//constructor
	public AutomatonGUI(Automaton automatonMatrix, int size) {
		super("World Simulation - Cell Automaton");
		
		//GENRAL INITILIZAUTION
		this.data = automatonMatrix;
		this.gridPanels = new AutomatonCell[automatonMatrix.lines][automatonMatrix.columns];
		this.setLayout(new BorderLayout());
		
		
		//EAST SIDE PANEL
		//
		containerEastSideButtons.setLayout(new GridLayout(2,1));
		containerEastSideButtons.add(eastSideButtons);
		eastSideButtons.setLayout(new GridLayout(6,2,20,20));
		eastSideButtons.add(categLabel);
		eastSideButtons.add(new JPanel());
		eastSideButtons.add(landsLabel);
		eastSideButtons.add(landsPanel);
		eastSideButtons.add(populationLabel);
		eastSideButtons.add(populationPanel);
		eastSideButtons.add(forestsLabel);
		eastSideButtons.add(forestsPanel);
		eastSideButtons.add(icebergsLabel);
		eastSideButtons.add(icebergsPanel);
		eastSideButtons.add(seasLabel);
		eastSideButtons.add(seasPanel);
		containerEastSideButtons.add(speedPanel);
		//speed panel styling
		speedPanel.setLayout(new BorderLayout());
		speedDataPanel.setLayout(new BorderLayout());
		speedCmdPanel.setLayout(new GridLayout(1,2,1,20));
		speedDataPanel.setBackground(Color.gray.brighter());
		Box box = Box.createVerticalBox();
		speedLabelCount.setText("Daily pace");
		speedLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		box.add(speedLabel);
		speedLabelCount.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		box.add(speedLabelCount);
		speedPanel.setBackground(Color.gray.brighter());
		speedDataPanel.add( box , BorderLayout.SOUTH);
		speedPanel.add( speedDataPanel , BorderLayout.CENTER);
		speedCmdPanel.add(cmdSpeedDown);
		speedCmdPanel.add(cmdSpeedUp);
		speedPanel.add(speedCmdPanel, BorderLayout.SOUTH);
		//example panels
		landsPanel.setLayout(new GridBagLayout());
		populationPanel.setLayout(new GridBagLayout());
		forestsPanel.setLayout(new GridBagLayout());
		icebergsPanel.setLayout(new GridBagLayout());
		seasPanel.setLayout(new GridBagLayout());
		landsPanel.add(landsLabelCount);
		populationPanel.add(populationLabelCount);
		forestsPanel.add(forestsLabelCount);
		icebergsPanel.add(icebergsLabelCount);
		seasPanel.add(seasLabelCount);
		landsPanel.setBackground(new Color(139,69,19));
		populationPanel.setBackground(new Color(112,128,144));
		forestsPanel.setBackground(Color.green.darker());
		icebergsPanel.setBackground(Color.white);
		seasPanel.setBackground(Color.blue);
		seasLabelCount.setForeground(Color.white);
		categLabel.setBackground(Color.gray.brighter());
		//END OF EAST PANEL
		
		
		//CENTER PANEL - BOARD
		//
		this.simulationBoard.setLayout(new GridLayout(automatonMatrix.lines,automatonMatrix.columns,1,1));
		for(int line=0; line<automatonMatrix.lines; line++)
			for(int column=0; column < automatonMatrix.columns;column++)
				{
				this.gridPanels[line][column] = data.getAutomatonCell(line,column);				
				this.gridPanels[line][column].addMouseListener(new MouseListenerCMD());
				this.simulationBoard.add(this.gridPanels[line][column]);
				}
		//END OF CENTER PANEL
		
		
		//WEST SIDE PANEL
		//
		westSideButtons.setLayout(new GridLayout(9,1,1,1));
		westSideButtons.add(cmdSetRain);
		buildersLabel.setText(" builders:");
		buildersLabel.setVerticalAlignment(JLabel.BOTTOM);
		westSideButtons.add(buildersLabel);
		westSideButtons.add(cmdSetSea);
		westSideButtons.add(cmdSetLand);
		westSideButtons.add(cmdSetCity);
		westSideButtons.add(cmdSetIce);
		westSideButtons.add(cmdSetForest);
		westSideButtons.add(cmdLog);
		westSideButtons.add(cmdSetRun);
		cmdSetter(cmdLog ,data.keepLogTrack ,"Logging","Logger Off"); 
		cmdSetter(cmdSetRain ,data.showRain ,"Show Rain", "Rain Off"); 
		cmdSetter(cmdSetRun ,data.live ,"Running", "Pause"); 
		//END OF WEST SIDE PANEL
		
		
		//set fonts
		categLabel.setFont(new Font("Serif",Font.BOLD, 24));
		landsLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		landsLabelCount.setFont(new Font("Verdana", Font.BOLD, 16));
		populationLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		populationLabelCount.setFont(new Font("Verdana", Font.BOLD, 16));
		forestsLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		forestsLabelCount.setFont(new Font("Verdana", Font.BOLD, 16));
		icebergsLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		icebergsLabelCount.setFont(new Font("Verdana", Font.BOLD, 16));
		seasLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		seasLabelCount.setFont(new Font("Verdana", Font.BOLD, 16));
		buildersLabel.setFont(new Font("Serif", Font.BOLD, 14));
		speedLabel.setFont(new Font("Serif", Font.BOLD, 18));
		speedLabelCount.setFont(new Font("Serif", Font.ITALIC, 16));
		cmdSetRun.setFont(new Font("Courier", Font.TRUETYPE_FONT, 16));

		//connecting the buttons to actions
		ButtonListenerCMD listener = new ButtonListenerCMD();
		cmdSpeedUp.addActionListener(listener);
		cmdSpeedDown.addActionListener(listener);
		cmdSetRain.addActionListener(listener);
		cmdSetSea.addActionListener(listener);
		cmdSetLand.addActionListener(listener);
		cmdSetCity.addActionListener(listener);
		cmdSetIce.addActionListener(listener);
		cmdSetForest.addActionListener(listener);
		cmdLog.addActionListener(listener);
		cmdSetRun.addActionListener(listener);
		
		//add the panels to the frame:
		this.add(simulationBoard,BorderLayout.CENTER);
		this.add(westSideButtons,BorderLayout.WEST);
		this.add(containerEastSideButtons,BorderLayout.EAST);
		this.setSize(size+size/3,size);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	public void repaintCountPanel()
	{
		int[] dataArr =this.data.logData;
		landsLabelCount.setText(""+dataArr[0]);
		populationLabelCount.setText(""+dataArr[1]);
		forestsLabelCount.setText(""+dataArr[2]);
		icebergsLabelCount.setText(""+dataArr[3]);
		seasLabelCount.setText(""+dataArr[4]);
		
		this.containerEastSideButtons.repaint();
	}
	//listners
	//

	//buttons
	public class ButtonListenerCMD implements ActionListener { 
		  public void  actionPerformed(ActionEvent e) {
			  
			  int amount;
			  //speed control up
			  if(e.getSource() == cmdSpeedUp) {
				  if(!speedLabelCount.getText().equals("Paused"))
					  switch(data.framesRate) {
						  case 356:
							  data.framesRate= 30;
							  speedLabelCount.setText("Monthly pace");
							  break;
						  case 30:
							  data.framesRate= 12;
							  speedLabelCount.setText("Yearly pace");
							  break;
						
						  default:
							  data.framesRate= 12;
							  speedLabelCount.setText("Yearly pace");
							  break;
					  }
			  }
			//speed control down
			  if(e.getSource() == cmdSpeedDown) {
				  if(!speedLabelCount.getText().equals("Paused"))
					  {
					  switch(data.framesRate) {
						  case 12:
							  data.framesRate= 30;
							  speedLabelCount.setText("Monthly pace");
							  break;
						  case 30:
							  data.framesRate= 356;
							  speedLabelCount.setText("Daily pace");
							  break;
						
						  default:
							  data.framesRate= 356;
							  speedLabelCount.setText("Daily pace");
							  break;
				  }
					  }
			  }

			  //builder setters
			  if(e.getSource() == cmdSetSea) {		  initlizationType = "Sea";				  }
			  if(e.getSource() == cmdSetLand) {		  initlizationType = "Land";			  }
			  if(e.getSource() == cmdSetCity) {		  initlizationType = "City";			  }
			  if(e.getSource() == cmdSetIce)  {		  initlizationType = "Ice";				  }
			  if(e.getSource() == cmdSetForest) {	  initlizationType = "Forest";			  }
			  
			  //controllers
			  if(e.getSource() == cmdLog) {		data.keepLogTrack = cmdSetter(cmdLog ,!data.keepLogTrack ,"Logging","Logger Off"); }
			  if(e.getSource() == cmdSetRain) {			data.showRain = cmdSetter(cmdSetRain ,!data.showRain ,"Show Rain", "Rain Off"); }
			  if(e.getSource() == cmdSetRun) {			data.live = cmdSetter(cmdSetRun ,!data.live ,"Running", "Pause"); }

		  repaint();
		  }
	}

	
	//mouse
	private class MouseListenerCMD implements MouseListener { 

			MouseListenerCMD() {}
		    public void mousePressed(MouseEvent e) 		    { 		    } 
		    public void mouseReleased(MouseEvent e) 		    { 		    } 
		    public void mouseExited(MouseEvent e) 		    { 		    } 
		    public void mouseEntered(MouseEvent e) 		    {		    } 
	
		    //initilizing cell types by clicking on them
		    public void mouseClicked(MouseEvent e) 
		    {    	
	            Object source = e.getSource();
	            
	            //not allowing initilize running simulation - pause first
	            if(!data.live)
			    	if(source instanceof AutomatonCell){
		            		AutomatonCell cellPressed = (AutomatonCell) source;
		            		cellPressed.setPropetrtiesByType(initlizationType);
							}
		    }   	
	            		 	
		    
	 }//end of listners
	
	//boolean button setter: green is ON and red is OFF
	public boolean cmdSetter(JButton b, boolean setTo, String on, String off) {
		
		  if(setTo)
		  {
		  b.setText(on); 
		  b.setForeground(Color.black);
		  b.setBackground(Color.green.darker().darker());
		  }
	  else 
	  {	  
		  b.setText(off);
		  b.setBackground(Color.red.darker().darker());
		  b.setForeground(Color.white.darker());
	  }
		  
		  return setTo;
	}
}


