import java.util.Random;

//this class responsible for calculating the generation.
//a life sites data matrix, will generate data for calculating different situations.
public class LifeSitesMatrix {

		private LifeSite[][] LifeMatrix;
		
		//default constructor with 10^10 cells.
		public LifeSitesMatrix() {
			this.LifeMatrix = new LifeSite[10][10];
			
			for(int line = 0 ; line<this.LifeMatrix.length ; line++ )
				for(int column = 0 ; column<this.LifeMatrix[line].length ; column++ )// notice the end of this loop, the matrix can have lines not equal to columns and we deal that and other cases here.
					LifeMatrix[line][column] = new LifeSite();
		}
		
		//Costume constructor.
		public LifeSitesMatrix(int lines, int columns) {
			this.LifeMatrix = new LifeSite[lines][columns];
			
			for(int line = 0 ; line<this.LifeMatrix.length ; line++ )
				for(int column = 0 ; column<this.LifeMatrix[line].length ; column++ ) 
					LifeMatrix[line][column] = new LifeSite();
		}
		
		//initilize automata
		public void randomizeMatrix() {
			for(int lines=0; lines<this.LifeMatrix.length; lines++)
				for(int columns=0; columns < this.LifeMatrix[lines].length;columns++)
					this.LifeMatrix[lines][columns].setSiteStatus(getRandomBoolean());
		}
		
		//
		//updates next generation status for each cell for synchronic update of all the cells.
		public void checkSitseArea() {
			int livingNeighbors;
			
			for(int line = 0 ; line<this.LifeMatrix.length ; line++ )
				for(int column = 0 ; column<this.LifeMatrix[line].length ; column++ )
				{
					livingNeighbors = countLivingNeighbors(line,column);

					//rules of life implementation:
					//birth or death status.
					if( (!this.getCellStatus(line, column) && livingNeighbors==3) )
						this.setCellUpdateStatus(line, column, true);
					else if(this.getCellStatus(line, column) &&  (livingNeighbors<2 || livingNeighbors>3) )
						this.setCellUpdateStatus(line, column, true);
					// -->no need to change status for existing status.
				}
		}
		
		//rules function, not yet the implemntation only the basic preparations
		//check the rules for a cell
		private int countLivingNeighbors(int line, int column) {
			int counter = 0;
			
			for(int i = line-1; i<=line+1; i++ )
				for(int j = column-1; j<=column+1; j++ )
				{	//matrix boundaries.
					if(i>=0 && j>=0 && i<this.LifeMatrix.length && j<this.LifeMatrix[i].length)
						if(this.getCellStatus(i, j))
							if(i!=line || j!=column) //dont count yourself as a neighbor.
								counter++;
				}
			return counter;
		}
		
		//create next generation
		public void nextGenaration(boolean showBornDie) {
			
			for(int line = 0 ; line<this.LifeMatrix.length ; line++ )
				for(int column = 0 ; column<this.LifeMatrix[line].length ; column++ )
				{
					if(this.getCellUpdateStatus(line, column))
							{
						//if the cell is alive and needs to die, paint red!
						if(this.getCellStatus(line, column))		
							{
							this.setCellStatus(line,column, false);
							if(showBornDie)
								this.setCellColor(line, column, Color.red);
							else
								this.setCellColor(line, column, Color.WHITE);
							}

						else
							{
							//if the cell is dead and needs to live, paint blue! yey!
							this.setCellStatus(line, column, true);
							if(showBornDie)
								this.setCellColor(line, column, Color.blue);
							else
								this.setCellColor(line, column, Color.DARK_GRAY);
							}
							}
					else {
						//if cell needs no update, paint white if dead, dark grey if alive (:
						if(this.getCellStatus(line, column))		
							this.setCellColor(line, column, Color.DARK_GRAY);
						else
							this.setCellColor(line, column, Color.WHITE);
					}
					
					this.setCellUpdateStatus(line, column, false);
				}//if theirs an order waiting to update status, reverse the current status and initialize the update status.
		}

		public boolean getCellStatus(int line, int column) {
			return this.LifeMatrix[line][column].getSiteStatus();
		}
		
		public boolean getCellUpdateStatus(int line, int column) {
			return this.LifeMatrix[line][column].getUpdateStatus();
		}
		
		public Color getCellColor(int line, int column) {
			return this.LifeMatrix[line][column].getCellColor();
		}
		
		public void setCellStatus(int line, int column, boolean status) {
			this.LifeMatrix[line][column].setSiteStatus(status);
		}
		
		public void setCellUpdateStatus(int line, int column, boolean status) {
			this.LifeMatrix[line][column].setUpdateStatus(status);
		}
		
		public void setCellColor(int line, int column, Color c) {
			this.LifeMatrix[line][column].setCellColor(c);;
		}
		
		private boolean getRandomBoolean() {
		    Random random = new Random();
		    return random.nextBoolean();
		}
}
