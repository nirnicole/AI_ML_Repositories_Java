import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//READ ME:
//M.v.c design - This is the MODEL file.
//here you will find all the modeling functions.
//you can also see here the TRANSITION-SET of states, the rule-set!
//AutomatonCell.java is a single state representation of the automaton - see it before you check this file please.
//
//->	for graphic go to VIEW file (AutomatonGUI.jave)
//->	for control flow go to CONTROL file (WorldSimulation.jave)
//
//description:
//this class responsible for calculating the next generation.
//a automaton cells data matrix, will generate data for calculating different simulations.
//
public class Automaton {

		private AutomatonCell[][] AutomatonCellMatrix;
		public int lines=10;
		public int columns=10;
		public boolean live = false;	//THIS FLAG IS IMPORTENT - IT REPRESENTS RUNNING / PAUSING SIMULATION.
		public int framesRate = 365;	//THIS FLAG IS IMPORTENT - IT REPRESENTS TIME FRAME RATE IN MILISECONDS.
		public int framesCount = 1;
		public boolean showRain = true;
		public boolean keepLogTrack = false;
		public int[] logData = new int[5];
		private boolean summer =true;
		public double minTempAvg = 100;
		public double maxTempAvg = -100;
		public double minPollAvg = 100;
		public double maxPollAvg = 0;
		public double minComAvg = 100;
		public double maxComAvg = 0;
		
		
		//default constructor with 10^10 cells.
		public Automaton() {
			this.AutomatonCellMatrix = new AutomatonCell[lines][columns];
			
			//initialize the cells
			for(int line = 0 ; line<this.AutomatonCellMatrix.length ; line++ )
				for(int column = 0 ; column<this.AutomatonCellMatrix[line].length ; column++ )
					AutomatonCellMatrix[line][column] = new AutomatonCell();
		}
		
		//Costume constructor.
		public Automaton(int lines, int columns) {
			this.AutomatonCellMatrix = new AutomatonCell[lines][columns];
			this.lines = lines;
			this.columns=columns;
			
			//initialize the cells
			for(int line = 0 ; line<this.AutomatonCellMatrix.length ; line++ )
				for(int column = 0 ; column<this.AutomatonCellMatrix[line].length ; column++ ) 
					AutomatonCellMatrix[line][column] = new AutomatonCell();
		}
		
//---------------------------------------------------------------------------------------------------
//												Transitions: 	(set of rules)											
//---------------------------------------------------------------------------------------------------
		

		//create next generation due to transition set
		//
		public void nextGenaration() {
			
			for(int line = 0 ; line<this.AutomatonCellMatrix.length ; line++ )
				for(int column = 0 ; column<this.AutomatonCellMatrix[line].length ; column++ )
				{
					
					//rule 1:
					//if pollution rate is up to 60% it will cause temp increas by 1.
				    if(this.AutomatonCellMatrix[line][column].getPollutionLevel() > 60)
			    		{
		    			this.AutomatonCellMatrix[line][column].appandTemp(0.1);
		    			this.AutomatonCellMatrix[line][column].appandPollution(-2);;
			    		}
				   				      
					//rule 2:
				    //cities couses pollution in a 1% rate per time unit.
				    if(this.AutomatonCellMatrix[line][column].getCellType().equals("City") )
						this.AutomatonCellMatrix[line][column].appandPollution(+1);

					
					//rule 3:
					//if an iceberg goes up to 1 degree it melts and becomes an ocean.
					 if(this.AutomatonCellMatrix[line][column].getCellType().equals("Ice") )
						 if(this.AutomatonCellMatrix[line][column].getCellTemperature()>0 )
							 {
							 this.AutomatonCellMatrix[line][column].setPropetrtiesByType("Sea");
							 this.AutomatonCellMatrix[line][column].setCellTemperature(1);
							 }
					 
					//rule 4:
					//if a sea goes up to 35 degree, for every 2 degrees he creates 1% cloud comdity because hes vaiporizing.
					//also if a sea goes up to 60 degree it will become land.
					 if(this.AutomatonCellMatrix[line][column].getCellType().equals("Sea") )
					 {
						 if(this.AutomatonCellMatrix[line][column].getCellTemperature()>35 )
								 this.AutomatonCellMatrix[line][column].appandClouds((int) ((this.AutomatonCellMatrix[line][column].getCellTemperature()-35)/2  +1));
							 
						 if(this.AutomatonCellMatrix[line][column].getCellTemperature()>60 )
						 	 this.AutomatonCellMatrix[line][column].setPropetrtiesByType("Land");
					 }
					 
					//rule 5:
					//------ rain!
				 	//if cloud comdity gets to 100% its raining and temp drop by 0.8 degrees and the pollution is clearing.
					 if(this.AutomatonCellMatrix[line][column].getCellCloudsDensity()>=100 )
						 {
						 this.AutomatonCellMatrix[line][column].setCellCloudsDensity(5);
						 if(this.AutomatonCellMatrix[line][column].getCellTemperature()>5 )
							 this.AutomatonCellMatrix[line][column].appandTemp(-0.8);
						 this.AutomatonCellMatrix[line][column].setPollutionLevel( 0);
						 this.AutomatonCellMatrix[line][column].appandWind(-1);

						 if(showRain)
							 this.AutomatonCellMatrix[line][column].setRain(20);			//set rain for 25 future frames so it will be visibl, thats a graphic perpose function.
						 }
						 
					//rule 6:
					//if a city goes up to 50 degree it burns down and becomes an land.
					 if(this.AutomatonCellMatrix[line][column].getCellType().equals("City") )
						 if(this.AutomatonCellMatrix[line][column].getCellTemperature()>50 )
							 {
							 this.AutomatonCellMatrix[line][column].setPropetrtiesByType("Land");
							 this.AutomatonCellMatrix[line][column].setCellTemperature(45);
							 }

					//rule 7:
					//if a Forest goes up to 50 degree it burns down and becomes an land.
					 if(this.AutomatonCellMatrix[line][column].getCellType().equals("Forest") )
						 if(this.AutomatonCellMatrix[line][column].getCellTemperature()>50 )
							 {
							 this.AutomatonCellMatrix[line][column].setPropetrtiesByType("Land");
							 this.AutomatonCellMatrix[line][column].setCellTemperature(45);
							 }

					//rule 8:
					//if a Sea goes down to -1 degree it froze and becomes an iceberg.
					 if(this.AutomatonCellMatrix[line][column].getCellType().equals("Sea") )
						 if(this.AutomatonCellMatrix[line][column].getCellTemperature() < 0 )
							 {
							 this.AutomatonCellMatrix[line][column].setPropetrtiesByType("Ice");
							 this.AutomatonCellMatrix[line][column].setCellTemperature(-1);;							 
							 }
				
					

					//rule 9:
					//sea creats comidity
					 if(this.AutomatonCellMatrix[line][column].getCellType().equals("Sea") &&  this.AutomatonCellMatrix[line][column].getCellTemperature()>10 )
						 this.AutomatonCellMatrix[line][column].appandClouds(+5);;

					/*	 	
					//rule 13:  ??
					//sea creats comidity
					 if((this.AutomatonCellMatrix[line][column].getCellTemperature()>25) && (this.AutomatonCellMatrix[line][column].getCellWind()/10  >  0) )
						 this.AutomatonCellMatrix[line][column].appandTemp(-1);
						*/
						 
			    	//rule 10:
			    	//neighbors rules:
					scanNeighbors(line,column);
				
					 
			    	//rule 11:
			    	//season change! shifting winds
					if(this.framesCount%(365/2)==0)
						{
						if(line==this.lines-1 && column==this.columns-1)
							this.summer = !this.summer;
						
						if(this.summer)
							{
							if(this.AutomatonCellMatrix[line][column].getCellWindDirection()==this.AutomatonCellMatrix[line][column].DIRECTIONS[0]) //east
								this.AutomatonCellMatrix[line][column].setCellWindDirection(this.AutomatonCellMatrix[line][column].DIRECTIONS[1]);	//west
							}
						else
							{
							if(this.AutomatonCellMatrix[line][column].getCellWindDirection()==this.AutomatonCellMatrix[line][column].DIRECTIONS[1]) //east
								this.AutomatonCellMatrix[line][column].setCellWindDirection(this.AutomatonCellMatrix[line][column].DIRECTIONS[0]);	//west
							}
						}
				
					
				}//if theirs an order waiting to update status, reverse the current status and initialize the update status.
			
		//update frame founter
		this.framesCount++;
		for(int i=0; i<this.logData.length;i++)
			this.logData[i] = getCellPopCount()[i];
		
		}//end of rules
		
		
		//extantion of rule 10 - neighbor associated rules
		//
		private void scanNeighbors(int line, int column) {
			
			for(int i = line-1; i<=line+1; i++ )
				for(int j = column-1; j<=column+1; j++ )
				{	//matrix boundaries.
					if(i>=0 && j>=0 && i<this.AutomatonCellMatrix.length && j<this.AutomatonCellMatrix[i].length)
						//not the cell itself!
						if(i!=line || j!=column)
							{
					    	//rule 10.1(complex one):
					    	//"wind shifts" - if the wind from your neighbor is shifting to your cell than:
					    	if(
				    			(i==line-1 && j==column ) && this.AutomatonCellMatrix[i][j].getCellWindDirection().equals("SOUTH")
				    			||
				    			(i==line && j==column-1 ) && this.AutomatonCellMatrix[i][j].getCellWindDirection().equals("EAST")
				    			||
				    			(i==line && j==column+1 ) && this.AutomatonCellMatrix[i][j].getCellWindDirection().equals("WEST")
				    			||
				    			(i==line+1 && j==column ) && this.AutomatonCellMatrix[i][j].getCellWindDirection().equals("NORTH")
				    		   )
					    		{
					    		
					    			int amount = this.AutomatonCellMatrix[i][j].getCellWind()/4 + 1;
					    		
						    		//10.1.1	clouds moving to you
				    				if(this.AutomatonCellMatrix[i][j].getCellCloudsDensity()>35)
					    	 			{
					    				this.AutomatonCellMatrix[line][column].appandClouds(1+amount);
					    				this.AutomatonCellMatrix[i][j].appandClouds(-amount);
					    	 			}
						    		
						    		//10.1.2	forests spread the oxijen to clean near cells effectivly with the wind
						    		if(this.AutomatonCellMatrix[i][j].getCellType().equals("Forest"))
						    	 			this.AutomatonCellMatrix[line][column].appandPollution(-2*amount);
						    		
						    		//10.1.3	if its winds from a forest, and to a cell that is a fertile land(with enought clouds and rain) it can cous you to chage from a land to a forest.
						    		if(this.AutomatonCellMatrix[i][j].getCellType().equals("Forest")    		 &&
						    			this.AutomatonCellMatrix[line][column].getCellType().equals("Land")	     &&
						    			this.AutomatonCellMatrix[line][column].getCellCloudsDensity()>=30	     &&
								    	this.AutomatonCellMatrix[line][column].getCellTemperature()<=50
						    			)
						    				this.AutomatonCellMatrix[line][column].setPropetrtiesByType("Forest");
						    		
						    		//10.1.4	pollution moving to you
						    		if(this.AutomatonCellMatrix[i][j].getPollutionLevel()>40)
						    	 			{
						    				this.AutomatonCellMatrix[i][j].appandPollution(-amount/2);
						    				this.AutomatonCellMatrix[line][column].appandPollution(amount);
						    	 			}
									
						    		//rule 10.1.5:
									//wind changes direction if a neighbor is strong on wind level
									 if(this.AutomatonCellMatrix[line][column].getCellWindDirection() != this.AutomatonCellMatrix[i][j].getCellWindDirection())
										 if(this.AutomatonCellMatrix[line][column].getCellWind() <= (this.AutomatonCellMatrix[i][j].getCellWind()+5))
											 {
											 this.AutomatonCellMatrix[line][column].setCellWindDirection(this.AutomatonCellMatrix[i][j].getCellWindDirection());
											 this.AutomatonCellMatrix[line][column].setCellWind(this.AutomatonCellMatrix[i][j].getCellWind()/2+1);;
											 }
								 
									//rule 10.1.6:
									//wind changes levels
									if(this.AutomatonCellMatrix[line][column].getCellWind() < (this.AutomatonCellMatrix[i][j].getCellWind()))
										{
										if(this.AutomatonCellMatrix[line][column].getCellWindDirection() == this.AutomatonCellMatrix[i][j].getCellWindDirection())
										 	 this.AutomatonCellMatrix[line][column].appandWind(+1);
										else
											 {
											 this.AutomatonCellMatrix[line][column].appandWind(-1);
											 this.AutomatonCellMatrix[i][j].appandWind(-1);
											 }
										 }
									
						    		
					    		}//end of rule 10.1
					    	
				    		//10.2	cities wish to built in proper neighboor cells
					    	if(this.AutomatonCellMatrix[line][column].getCellType().equals("City")  &&
					    			(this.AutomatonCellMatrix[i][j].getCellType().equals("Land") || this.AutomatonCellMatrix[i][j].getCellType().equals("Forest")))
					    		if(
					    				(	this.AutomatonCellMatrix[i][j].getCellTemperature()<35 && this.AutomatonCellMatrix[i][j].getPollutionLevel() < 50 ) &&
					    				(	this.AutomatonCellMatrix[i][j].getCellWind()<10 && this.AutomatonCellMatrix[i][j].getCellCloudsDensity()>45 )
					    				)
					    				{
					    				this.AutomatonCellMatrix[i][j].setPropetrtiesByType("City");
					    				break;
					    				}
				    				
				    		
						}////end of inner loop
				}//end of loop
		}//end of neighboor rules.
		
		
		
//->end of transitoin set

//---------------------------------------------------------------------------------------------------
//														Mainframe Utils:											
//---------------------------------------------------------------------------------------------------
		
			

		
		//initialization of the automata 
		//
		public void initilizeAutomata(int lands,int cities,int icebergs,int forests, int MaxRatio) {
			
			if(lands>lines || lands > columns || lands<0) lands=1;
			if(cities>lines || cities > columns || cities<0) cities=1;
			if(icebergs>lines || icebergs > columns || icebergs<0) icebergs=1;
			if(forests>lines || forests > columns || forests<0) forests=1;
			
			for(int index=0; index<lands; index++) {		this.updateAnArea("Land", lands, MaxRatio);			}
			for(int index=0; index<cities; index++) {		this.updateAnArea("City", cities, MaxRatio);		}
			for(int index=0; index<icebergs; index++) {		this.updateAnArea("Ice", icebergs, MaxRatio);		}
			for(int index=0; index<forests; index++) {		this.updateAnArea("Forest", forests, MaxRatio);		}
			for(int index=0; index<forests; index++) {		this.updateAnArea("Sea", icebergs, MaxRatio);		}
		}
		
		//aiding function for initialization
		private void updateAnArea(String type, int count, int MaxRatio) {
			int randomLine = ThreadLocalRandom.current().nextInt(0, this.AutomatonCellMatrix.length + 1);
			int randomColumn = ThreadLocalRandom.current().nextInt(0, this.AutomatonCellMatrix[0].length + 1);
			int randomSize;

			switch(type) {
			  case "Land":
				  MaxRatio/=3 ;
			    break;
			  case "Sea":
				  MaxRatio/=2 ;
			    break;
			  case "Forest":
				  MaxRatio/=2;
			    break;
			  case "Ice":
			    break;
			  case "City":
				  MaxRatio/=2;
			    break;
			  default:
			}
			
			MaxRatio+=1;
			randomSize = ThreadLocalRandom.current().nextInt(1, this.AutomatonCellMatrix.length/MaxRatio > 1? this.AutomatonCellMatrix.length/MaxRatio : 2 );
			
			for(int i = randomLine-randomSize; i<=randomLine+randomSize; i++ )
				for(int j = randomColumn-randomSize; j<=randomColumn+randomSize; j++ )
					//matrix boundaries.
					if(i>=0 && j>=0 && i<this.AutomatonCellMatrix.length && j<this.AutomatonCellMatrix[i].length)
						{
						this.AutomatonCellMatrix[i][j].setPropetrtiesByType(type);		//default propertoes by type
						int windIndex = ThreadLocalRandom.current().nextInt(0,this.AutomatonCellMatrix[i][j].DIRECTIONS.length);
						String newDir = this.AutomatonCellMatrix[i][j].DIRECTIONS[windIndex];
						this.AutomatonCellMatrix[i][j].setCellWindDirection(newDir);	//initilize random wind direction
						 if(probabilityToss(1))											//initilize random wind level
							 this.AutomatonCellMatrix[i][j].appandWind(+windIndex*4);
						 else
							 this.AutomatonCellMatrix[i][j].appandWind(-windIndex*4);
						}
					
		}
		
		//probability function
		//creates probability using coin tosess -> the probability is: 1/(2^tosses)  == such as 0.5, 0.25 , 0.125 etc..
		public boolean probabilityToss(int tosses) {
			
			// create random object
			Random randomno = new Random();

			// get next next boolean value 
			boolean probabilityIndicator;
		    
			//true tracker
			int track=0;
			
			for(int i=0 ; i<tosses; i++)
			{
				probabilityIndicator = randomno.nextBoolean();
				if(probabilityIndicator)
					track++;
			}
			
			if(tosses==track)
				 return true;
			else return false;
		}
		
		//derive data to a costume string for logger
		public String deriveData() {
			String logData = "\n";
			
			//{Land,City,Forest,Ice,Sea}
			int[] popArr = this.getCellPopCount();
			
			logData+= "Time units past: " + this.framesCount + "\n\n";
			logData+= "Lands: " + popArr[0] + "\n";
			logData+= "Population: " + popArr[1] + "\n";
			logData+= "Forests: " + popArr[2] + "\n";
			logData+= "Icebergs: " + popArr[3] + "\n";
			logData+= "Seas: " + popArr[4] + "\n\n";

			logData+= "Avarage cell Temperature: " + this.getAvgCellTemp() + "\n";
			logData+= "Minimum Avargae Temperature: " + this.minTempAvg + "\n";
			logData+= "Maximum Avargae Temperature: " + this.maxTempAvg + "\n\n";

			logData+= "Avarge cell Pollution levels: " + this.getAvgCellPoll() + "\n";
			logData+= "Minimum Avargae Pollution: " + this.minPollAvg + "\n";
			logData+= "Maximum Avargae Pollution: " + this.maxPollAvg + "\n\n";
			
			logData+= "Avarge cell comidity levels: " + this.getAvgCellCom() + "\n";
			logData+= "Minimum Avargae comidity: " + this.minComAvg + "\n";
			logData+= "Maximum Avargae comidity: " + this.maxComAvg + "\n\n";



			logData+= "\nEnd of log.\n";

			return logData;
		}
		
		public int[] getCellPopCount() {
			
			//{Land,City,Forest,Ice,Sea}
			int[] popArr = new int[5];
			
			for(int line=0; line<this.lines; line++)
				for(int column=0; column < this.columns;column++)
				{
					switch(this.AutomatonCellMatrix[line][column].getCellType()) {
					  case "Land":
						  popArr[0]++;
						  break;
					  case "City":
						  popArr[1]++;
						  break;
					  case "Forest":
						  popArr[2]++;
						  break;
					  case "Ice":
						  popArr[3]++;
						  break;
					  case "Sea":
						  popArr[4]++;
						  break;
						  
					  default:
					}
				}
			
			return popArr;
		}
		
		public double getAvgCellTemp() {
			
			double sum = 0, result=0;
			
			for(int line=0; line<this.lines; line++)
				for(int column=0; column < this.columns;column++)
					sum+=this.AutomatonCellMatrix[line][column].getCellTemperature();
			
			result = (double) Math.round(sum/(this.lines*this.columns) * 100) / 100; 

			if(result>this.maxTempAvg)
				this.maxTempAvg = result;
			if(result<this.minTempAvg)
				this.minTempAvg = result;
			
			return result;
		}
		
		
		public double getAvgCellPoll() {
			
			double sum = 0, result=0;
			
			for(int line=0; line<this.lines; line++)
				for(int column=0; column < this.columns;column++)
					sum+=this.AutomatonCellMatrix[line][column].getPollutionLevel();
			
			result = (double) Math.round(sum/(this.lines*this.columns) * 100) / 100; 

			if(result>this.maxPollAvg)
				this.maxPollAvg = result;
			if(result<this.minPollAvg)
				this.minPollAvg = result;
			
			return result;
		}
		
		public double getAvgCellCom() {
			
			double sum = 0, result=0;
			
			for(int line=0; line<this.lines; line++)
				for(int column=0; column < this.columns;column++)
					sum+=this.AutomatonCellMatrix[line][column].getCellCloudsDensity();
			
			result = (double) Math.round(sum/(this.lines*this.columns) * 100) / 100; 

			if(result>this.maxComAvg)
				this.maxComAvg = result;
			if(result<this.minComAvg)
				this.minComAvg = result;
			
			return result;
		}
		
		//getters
		public String getCellType(int line, int column) {			return this.AutomatonCellMatrix[line][column].getCellType();		}
		public Color getCellColor(int line, int column) {			return this.AutomatonCellMatrix[line][column].getCellColor();		}
		public double getCellTemperature(int line, int column) {			return this.AutomatonCellMatrix[line][column].getCellTemperature();		}
		public int getCellCloudsDensity(int line, int column) {			return this.AutomatonCellMatrix[line][column].getCellCloudsDensity();		}
		public int getCellWindLevels(int line, int column) {			return this.AutomatonCellMatrix[line][column].getCellWind();		}
		public String getCellWindDirection(int line, int column) {			return this.AutomatonCellMatrix[line][column].getCellWindDirection();		}
		public int getPollutionLevel(int line, int column) {			return this.AutomatonCellMatrix[line][column].getPollutionLevel();		}
		public AutomatonCell getAutomatonCell(int line, int column) {	return this.AutomatonCellMatrix[line][column];			}
		
		//setters
		public void setCellStatus(int line, int column, String newType) {			this.AutomatonCellMatrix[line][column].setCellType(newType);		}
		public void setCellColor(int line, int column, Color c) {			this.AutomatonCellMatrix[line][column].setCellColor(c);		}
		public void setAutomatonCell(int line, int column,AutomatonCell ac) {	this.AutomatonCellMatrix[line][column] = ac;			}

}
