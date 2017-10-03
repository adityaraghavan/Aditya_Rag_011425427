import java.util.Observable;

/**
 * Buffer specific to each processor for receiving messages
 */
public class Buffer extends Observable {
	
	// Message sent via the buffer
    private Message message;
    private Processor sourceProcessor;
    
    public Processor getSourceProcessor() {
		return sourceProcessor;
	}

	public void setSourceProcessor(Processor sourceProcessor) {
		this.sourceProcessor = sourceProcessor;
	}

	/**
     * Default constructor
     */
    public Buffer(){
    }
    
    /**
     * Parameterized constructor
     * @param message
     */
    public Buffer(Message message) {
        this.message = message;
    }

    /**
     * Fetching message received over buffer
     * @return
     */
    public Message  getMessage() {
        return message;
    }

    /**
     * Sending message over buffer
     * @param message
     * @param sourceProcessor
     */
    public void setMessage(Message message) {
        this.message = message;
        setChanged();
        notifyObservers();
    }
}

