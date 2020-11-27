			this is an MVC design pattern:
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
