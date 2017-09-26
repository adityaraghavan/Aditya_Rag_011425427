import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DFS with a given root algorithm
 */
public class Main {

	// Graph structure
	Map <Processor, List<Processor> > graph;

	// Processors list
	Processor p0;
	Processor p1;
	Processor p2;
	Processor p3;
	Processor p4;
	Processor p5;

	/**
	 * Default Constructor
	 */
	public  Main(){

		init();
	}

	/**
	 * Entry point function
	 * @param args
	 */
	public static void main ( String args[]){

		Main m = new Main();

		// Starts the algorithm
		m.start();
	}

	/**
	 * Graph structure and processors initialization
	 */
	public void init(){

		graph = new HashMap<Processor, List<Processor>>();

		p0 = new Processor();
		p0.id = 0;

		p1 = new Processor();
		p1.id = 1;

		p2 = new Processor();
		p2.id = 2;

		p3 = new Processor();
		p3.id = 3;

		p4 = new Processor();
		p4.id = 4;

		p5 = new Processor();
		p5.id = 5;

		p0.unexplored.add(p1);
		p0.unexplored.add(p2);
		p0.unexplored.add(p3);
		graph.put(p0, p0.unexplored);

		p1.unexplored.add(p0);
		p1.unexplored.add(p2);
		p1.unexplored.add(p4);
		graph.put(p1, p1.unexplored);

		p2.unexplored.add(p0);
		p2.unexplored.add(p1);
		p2.unexplored.add(p5);
		graph.put(p2, p2.unexplored);

		p3.unexplored.add(p0);
		graph.put(p3, p3.unexplored);

		p4.unexplored.add(p1);
		p4.unexplored.add(p5);
		graph.put(p4, p4.unexplored);

		p5.unexplored.add(p2);
		p5.unexplored.add(p4);
		graph.put(p5, p5.unexplored);
	}

	/**
	 * Starts DFS algorithm
	 */
	public void start(){

		// Choose a processor as a Root
		p0.setParent(p0);

		// Begin DFS by sending a message to one of the neighbors
		Processor p = graph.get(p0).get(0);
		p0.unexplored.remove(p);
		p.sendMessgeToMyBuffer(Message.M, p0);
	}
}