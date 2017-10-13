import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * Performs all the processor related tasks
 *
 * @author Sample
 * @version 1.0
 */


public class Processor implements Observer {

	Integer id;
    List<Buffer> inChannels = null;
    List<Processor> neighbours = null;
    /**
     * List of output channels
     * //TODO: Homework: Use appropriate list implementation and replace null assignment with that
     *
     */
    List<Buffer> outChannels = null;

    /**
     * This is a map that will record the state of each incoming channel and all the messages
     * that have been received by this channel since the arrival of marker and receipt of duplicate marker
     * //TODO: Homework: Use appropriate Map implementation.
     */
    Map<Buffer, List<Message>> channelState = null;

    /**
     * This map can be used to keep track of markers received on a channel. When a marker arrives at a channel
     * put it in this map. If a marker arrives again then this map will have an entry already present from before.
     * Before  doing a put in this map first do a get and check if it is not null. ( to find out if an entry exists
     * or not). If the entry does not exist then do a put. If an entry already exists then increment the integer value
     * and do a put again.
     */
    Map<Buffer, Integer> channelMarkerCount = null;
    Map<Buffer, Integer> channelMarkerStart = null;
    Map<Buffer, Integer> channelMarkerEnd = null;


    /**
     * @param id of the processor
     */
    public Processor(int id, List<Buffer> inChannels, List<Buffer> outChannels) {
     
    	this.id = id;
    	this.inChannels = inChannels;
        this.outChannels = outChannels;
        this.neighbours = new ArrayList<>();
        //TODO: Homework make this processor as the observer for each of its inChannel
        //Hint [loop through each channel and add Observer (this) . Feel free to use java8 style streams if it makes
        // it look cleaner]
        Iterator<Buffer> itr = this.inChannels.iterator();

//        channelState = new HashMap<Buffer, List<Message>>();
        channelMarkerCount = new HashMap<Buffer, Integer>();
		channelMarkerStart = new HashMap<Buffer, Integer>();
		channelMarkerEnd = new HashMap<Buffer, Integer>();

		while(itr.hasNext())
    	{
    		Buffer bf = itr.next();
    		bf.addObserver(this);
    		channelMarkerCount.put(bf, 0);
    		channelMarkerStart.put(bf, -1);
    		channelMarkerEnd.put(bf, -1);
    	}
    }

    public void initiateSnapShot() {
    	
    	System.out.println("SnapShot initiated by Processor " +this.id);
    	//Record Current State
    	recordMyCurrentState();
    	//Send Marker Messages
    	
    	Iterator<Buffer> itr = this.outChannels.iterator();
    	while(itr.hasNext())
    	{
    		Buffer bf = itr.next();
    		for(int i=0; i< this.neighbours.size();i++)
    		{
    			if(bf.getLabel().contains(this.neighbours.get(i).id +""))
    			{
            		Message m = new Message(MessageType.MARKER);
            		m.setFrom(this);
            		neighbours.get(i).sendMessgeTo(m, bf);        			
 //           		this.channelMarkerStart.put(bf,-2);	
    			}
    		}
    	}
    	
        //Start recording on each of the input channels
    	itr = this.inChannels.iterator();
    	while(itr.hasNext())
    	{
        	recordChannel(itr.next());
    	}
    }

    /**
     * This is a dummy implementation which will record current state
     * of this processor
     */
    public void recordMyCurrentState() {
    	System.out.println("Recording current state of Processor - "+ this.id);
        System.out.println("Recording my registers...");
        System.out.println("Recording my program counters...");
        System.out.println("Recording my local variables...");
    }

    /**
     * THis method marks the channel as empty
     * @param channel
     */
    public void recordChannelAsEmpty(Buffer channel) {

//        channelState.put(channel, Collections.emptyList());
    	channelMarkerStart.put(channel, -1);
    }

    /**
     * You should send a message to this recording so that recording is stopped
     * //TODO: Homework: Move this method recordChannel(..) out of this class. Start this method in a
     *                  separate thread. This thread will start when the marker arrives and it will stop
     *                  when a duplicate marker is received. This means that each processor will have the
     *                  capability to start and stop this channel recorder thread. The processor manages the
     *                  record Channel thread. Processor will have the ability to stop the thread.
     *                  Feel free to use any java concurrency  and thread classes to implement this method
     *
     *
     * @param channel The input channel which has to be monitered
     */

    public void recordChannel(Buffer channel) {
        //Here print the value stored in the inChannels to stdout or file
        //TODO:Homework: Channel will have messages from before a marker has arrived. Record messages only after a
        //               marker has arrived.
        //               [hint: Use the getTotalMessageCount () method to get the messages received so far.
 //       int lastIdx = channel.getTotalMessageCount();
//       List<Message> recordedMessagesSinceMarker = new ArrayList<>();
            //TODO: Homework: Record messages
            // [Hint: Get the array that is storing the messages from the channel. Remember that the Buffer class
            // has a member     private List<Message> messages;  which stores all the messages received.
            // When a marker has arrived sample this List of messages and copy only those messages that
            // are received since the marker arrived. Copy these messages into recordedMessagesSinceMarker
            // and put it in the channelState map.
            //
            // ]

//        channelState.put(channel, recordedMessagesSinceMarker);
        channelMarkerStart.put(channel, channel.getTotalMessageCount());
    }

    /**
     * Overloaded method, called with single argument
     * This method will add a message to this processors buffer.
     * Other processors will invoke this method to send a message to this Processor
     *
     * @param message Message to be sent
     */
    public void sendMessgeTo(Message message, Buffer channel) {
    	System.out.println("Message\t-\t"+message.messageType+"\tfrom\t"+message.getFrom().id+"\tto\t"+this.id );
    	channel.saveMessage(message);
    }

    /**
     *
     * @param fromChannel channel where marker has arrived
     * @return true if this is the first marker false otherwise
     */
    public boolean isFirstMarker(Buffer fromChannel) {

    	if(channelMarkerCount.get(fromChannel) > 1)
    	{
    		return false;
    	}
    	return true;
    }

    /**
     * Gets called when a Processor receives a message in its buffer
     * Processes the message received in the buffer
     */
    public void update(Observable observable, Object arg) {
    	
    	Buffer fromChannel = (Buffer) observable;
    	int messageIndex = (int) arg;
        Message message = fromChannel.getMessage(messageIndex);       
        Processor sender = message.getFrom();
        
        if (message.getMessageType().equals(MessageType.MARKER)) {        

        	channelMarkerCount.put(fromChannel, channelMarkerCount.get(fromChannel)+1);
        	if (isFirstMarker(fromChannel)) {
        		
        		channelMarkerStart.put(fromChannel, messageIndex);
                recordChannelAsEmpty(fromChannel);
                this.recordMyCurrentState();
                //From the other incoming Channels (excluding the fromChannel which has sent the marker
                // startrecording messages
                //TODO: homework: Trigger the recorder thread from this processor so that it starts recording for each channel
                Iterator<Buffer> itr = this.inChannels.iterator();
            	while(itr.hasNext())
            	{
            		Buffer bf = itr.next();
            		if(!bf.getLabel().contains(sender.id+""))
            		{
            			recordChannelAsEmpty(bf);
            		}
            	}   
                // Exclude the "Channel from which marker has arrived.
            } 
        	else {
                
            	//fromChannel.//Means it isDuplicateMarkerMessage.
                //TODO: Homework Stop the recorder thread.
                channelMarkerEnd.put(fromChannel, messageIndex);  
                if(!channelMarkerEnd.containsValue(-1))
                {
                	System.out.println("Message recorded by Processor - " + this.id);
                	Set<Buffer> bfSet = channelMarkerStart.keySet();
                	Iterator<Buffer> itr = bfSet.iterator();
                	while(itr.hasNext())
                	{
                		Buffer bf = itr.next();
                		System.out.println("On Channel - " + bf.label);
                		for(int i=channelMarkerStart.get(bf); i>=0 && i< channelMarkerEnd.get(bf); i++)
                		{
                			System.out.println(bf.getMessage(i));
                		}
                	}
                }
        	
            
            //TODO: Homework Send marker messages to each of the out channels
        	Iterator<Buffer> itr = this.outChannels.iterator();
        	while(itr.hasNext())
        	{
        		Buffer bf = itr.next();
        		if(this.channelMarkerStart.get(bf)==-2)
        			break;
        		for(int i=0; i< this.neighbours.size();i++)
        		{
        			if(neighbours.get(i).id != sender.id)
        			{
                		Message m = new Message(MessageType.MARKER);
                		m.setFrom(this);
                		neighbours.get(i).sendMessgeTo(m, itr.next());        			
//                		this.channelMarkerStart.put(bf,-2);	
        			}
        		}
        	}
        	}
        }
        else
        {
            if (message.getMessageType().equals(MessageType.ALGORITHM)) {
                System.out.println("Processing Algorithm message...." +  this.id);
             }  //There is no other type
        }
    }
}
