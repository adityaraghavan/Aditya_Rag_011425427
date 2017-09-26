import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Contains processor details and functions
 */
public class Processor implements Observer {

	// Processor id
	Integer id;
	// Parent processor to this processor
	Processor parentProcessor;
	// Processor buffer to receive messages
	Buffer messageBuffer;
	// List of unexplored neighbours
	List<Processor> unexplored;
	// List of child processors
	List<Processor> children;

	/**
	 * Default constructor initializes processor details
	 */
	public Processor() {

		id = Integer.MIN_VALUE;
		messageBuffer = new Buffer();
		children = new ArrayList<>();
		unexplored = new ArrayList<>();
		// Processor observers its buffer to check messages received
		messageBuffer.addObserver(this);
	}

	/**
	 * Removes processor from list of unexplored neighbors
	 * @param p
	 */
	private void removeFromUnexplored(Processor p) {
		this.unexplored.remove(p);
	}

	/**
	 * Adds a processor to this message buffer
	 * Used by other processors to send a message to this processor
	 * @param message
	 * @param sourceProcessor
	 */
	public void sendMessgeToMyBuffer(Message message, Processor sourceProcessor) {
		System.out.println("Sender\t-\t" + sourceProcessor.id + "\tReceiver\t-\t" + this.id + "\tMessage\t-\t" + message.name());
		messageBuffer.setMessage(message, sourceProcessor);
	}

	/**
	 * Called when a message is sent to this processor using its buffer
	 */
	public void update(Observable observable, Object arg) {

		Processor sourceProcessor = (Processor) arg;

		// Check type of message received
		switch(this.messageBuffer.getMessage()){

		case M: 
			if(null == this.parentProcessor){
				// Set source processor as parent to this processor
				this.parentProcessor = sourceProcessor;
				// Remove source processor from list of unexplored neighbors
				removeFromUnexplored(sourceProcessor);
				// Continue with DFS
				explore();
			}
			else{
				// Reply if message already received
				sourceProcessor.sendMessgeToMyBuffer(Message.ALREADY, this);
				// Remove source processor from list of unexplored neighbors
				removeFromUnexplored(sourceProcessor);
			}
			break;

		case ALREADY: 
			// Continue with DFS
			explore();
			break;

		case PARENT: 
			// Set source processor as child of this processor
			sourceProcessor.children.add(this);
			// Continue with DFS
			explore();
			break;
		}
	}

	/**
	 * Exploring the graph further using DFS
	 */
	private void explore() {

		Processor p;
		if(!this.unexplored.isEmpty())
		{
			p = this.unexplored.get(this.unexplored.size()-1);
			this.unexplored.remove(p);
			p.sendMessgeToMyBuffer(Message.M, this);
		}
		else{

			if(this.parentProcessor.id != this.id){
				this.parentProcessor.sendMessgeToMyBuffer(Message.PARENT, this);
			}
			// Stop the algorithm
			System.out.println("Terminate!");
			System.exit(0);
		}
	}

	/**
	 * Set parent processor for this processor
	 * @param p
	 */
	public void setParent(Processor p) {
		this.parentProcessor = p;
	}
}