import java.awt.Color;
import java.util.concurrent.TimeUnit;

/*
 * Basic 2-dm automata representing Conway's Game Of Life.
 * 
 * A = (Z^2,S,N,D)
 * 
 * Z: 2 dimensions.
 * S: {alive, dead}.
 * N: livingNeighbors, an int between 0-8 representing living cells of a cell zero.
 * D: 
 * 		rules: 
 * 			cell=alive	and	livingNeighbors<2	->	cell=dead.
 * 			cell=alive	and	livingNeighbors>3	->	cell=dead.
 *			cell=dead	and	livingNeighbors=3	->	cell=alive.
 *			else								->	cell=no change.
 * 
 * specifications:
 *	Z - dimensions.
 *	S - finite set of the possible cell status.
 *	N - orederd subset called neighborhood.
 *	D - function called the transition rule of A.
 */

//main game simulation.
public class LifeGame {

	public static void main(String[] args) throws InterruptedException {

		//create automata
		final int LINES = 100, COLUMNS = 100, DELAY=220, SIZE=800;
		LifeSitesGrid lifeGameGrid  = new LifeSitesGrid(LINES,COLUMNS,SIZE);
		LifeSitesMatrix lifeMatrix = new LifeSitesMatrix(LINES,COLUMNS);
		
		//initilize automata
		lifeMatrix.randomizeMatrix();
		lifeGameGrid.setVisible(true);
		
		
		while(true)
		{	//setting the grid cells color according to living cells on life sites matrix.
			lifeGameGrid.paintGrid(lifeMatrix, Color.DARK_GRAY, Color.WHITE);
			
			//implement automata rules
			lifeMatrix.checkSitseArea();
			
			//generate next automata generation
			lifeMatrix.nextGenaration();
			
			//delay for clear view
			TimeUnit.MILLISECONDS.sleep(DELAY);
		
		}//end of loop.
		
	}//end of main.

}
