import java.util.concurrent.TimeUnit;


/*
 * READ ME:
 * 			this is an MVC design pattern:
 * 					M = Automaton.java
 * 					V = AutomatonGUI.java
 * 					C = WorldSimulation.java
 * 
 * this is CONTROL flow of the design.
 * 
 *	->	for model go to MODEL file (Automaton.jave)
 *	->	for graphics go to VIEW file (AutomatonGUI.jave)
 *
 * Basic 2-dm automata representing World Simulation - Cell Automaton.
 * 
 * A = (Z^2,S,N,D)
 * 
 * Z: 2 dimensions.
 * S: {Sea,Land,City,Forest,Iceberg}.
 * N: for cell(i,j): i from i-1 to i+1 ,  j from j-1 to j+1 .
 * D: 
 * 		rules: 
 * 			SECIFIED IN MODEL FILE - Automaton.java
 * 
 * specifications:
 *	Z - dimensions.
 *	S - finite set of the possible cell status.
 *	N - orederd subset called neighborhood.
 *	D - function called the transition rules of A.
 */

//main game simulation.
public class WorldSimulation {

		//create simulation
		public final int SIZE=1000;
		public static AutomatonGUI automatonGameGrid;
		public static Automaton automatonMatrix;
		public static Logger log;

		//default values
		public WorldSimulation() { new WorldSimulation(20, 20);	}
		
		//
		public WorldSimulation(int lines, int columns) {	new WorldSimulation(20, 20, 2, 2, 1, 2, 10);	}
		
		//constructed automaton
		public WorldSimulation(int lines, int columns, int lands,int cities,int icebergs,int forests, int MaxRatio) {
			
			//creating model design
			automatonMatrix = new Automaton(lines>1?lines:1,columns>1?columns:1);
			
			//optional, initilize Automata function gets (lands,cities,icebergs,forests,and maximum ratio of randomnes)
			automatonMatrix.initilizeAutomata(lands, cities, icebergs, forests, MaxRatio);
			
			//creating view design
			automatonGameGrid  = new AutomatonGUI(automatonMatrix,SIZE);
			automatonGameGrid.setVisible(true);
			
			//create a logger
			log = new Logger("WS-Log");

			//start simulation
			try {
				runGame();
			} catch (InterruptedException e) {	e.printStackTrace();	}

		}//end of constractor
		
		//this loop simulatin an endless run of the current automaton.
		//i left a timeslace of thread sleep when paused so other threads could take control of the proccesor.
		public static void runGame() throws InterruptedException {
			while(true) {
				while(automatonGameGrid.data.live)
					{
					//if logging is requierd create a logger and choose directory
					if(!log.flag && automatonGameGrid.data.keepLogTrack)
						{
						log.runLog();
						if(!log.flag) 
							automatonGameGrid.data.keepLogTrack=false;
						}

					//write data to log
					if(automatonGameGrid.data.keepLogTrack && log.flag &&  automatonGameGrid.data.framesCount%30==1)
						{
						if((automatonGameGrid.data.framesCount%365>=0) && (automatonGameGrid.data.framesCount%365<31))
							log.writeTo("\nYear " + (automatonGameGrid.data.framesCount/356 + 1) + ":\n\n" );
						log.writeTo(automatonGameGrid.data.deriveData());	//monthly report
						}
					
					//generate next automata generation
					automatonGameGrid.data.nextGenaration();
					
					//update cell count - optional
					automatonGameGrid.repaintCountPanel();
					
					//delay for clear view
					TimeUnit.MILLISECONDS.sleep(automatonGameGrid.data.framesRate);

					}

				//delay for allwing other threads take controll
				TimeUnit.MILLISECONDS.sleep(automatonGameGrid.data.framesRate/2);
			}
			
		}//end of runGame
		

}//end of class
