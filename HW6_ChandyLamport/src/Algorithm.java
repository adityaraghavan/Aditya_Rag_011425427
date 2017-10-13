import java.util.Iterator;
import java.util.List;

/**
 * This is the simulation of a main algorithm that will run on processors P1, P2, P3
 * This could be a banking application, payroll application or any other distributed application
 */
public class Algorithm {

    /**
     * The processors which will participate in a distributed application
     */
    Processor processor1, processor2, processor3;

    public Algorithm(Processor processor1, Processor processor2, Processor processor3) {
    	
        //TODO: Homeork: Initialize processors so that they represent the topology of 3 processor system
    	this.processor1 = processor1;
    	this.processor2 = processor2;
    	this.processor3 = processor3;
    }


    /**
     * TODO: Homework: Implement send message from processor1 to different processors. Add a time gap betweeen two different
     *                send events. Add computation events between two diferent sends.
     *      [Hint: Create a loop that kills time, sleep , wait on somevalue etc..]
     *
     */
   public void executionPlanP1(){

	   	Message m = new Message(MessageType.ALGORITHM);
	   	m.setFrom(processor1);
        processor2.sendMessgeTo(m,processor1.outChannels.get(0));

        compute(processor1);
        compute(processor1);
        
        processor1.initiateSnapShot();
       	
       	m = new Message(MessageType.COMPUTATION);
	   	m.setFrom(processor1);
        processor3.sendMessgeTo(m,processor1.outChannels.get(1));
        
        compute(processor1);
        compute(processor1);
    
        m = new Message(MessageType.ALGORITHM);
	   	m.setFrom(processor1);
        processor2.sendMessgeTo(m,processor1.outChannels.get(0));
        
        compute(processor1);
        compute(processor1);
        compute(processor1);
    }

    // Write hard coded execution plan for processors
    public void executionPlanP2() {
    	
    	Message m = new Message(MessageType.ALGORITHM);
	   	m.setFrom(processor2);

	   	processor1.sendMessgeTo(m,processor2.outChannels.get(0));
        compute(processor2);
        compute(processor2);

        m = new Message(MessageType.COMPUTATION);
	   	m.setFrom(processor2);
       	processor3.sendMessgeTo(m,processor2.outChannels.get(1));

       	compute(processor2);
        compute(processor2);
        
        m = new Message(MessageType.ALGORITHM);
	   	m.setFrom(processor2);
        processor1.sendMessgeTo(m,processor2.outChannels.get(0));
        compute(processor2);
        compute(processor2);
        compute(processor2);
    }

    // Write hard coded execution plan for processors
    public void executionPlanP3() {

    	Message m = new Message(MessageType.ALGORITHM);
	   	m.setFrom(processor3);
    	processor2.sendMessgeTo(m,processor3.outChannels.get(0));

    	compute(processor3);
        compute(processor3);
       	
        m = new Message(MessageType.COMPUTATION);
	   	m.setFrom(processor3);
       	processor1.sendMessgeTo(m,processor3.outChannels.get(1));
        
       	compute(processor3);
        compute(processor3);
        
        m = new Message(MessageType.COMPUTATION);
	   	m.setFrom(processor3);
       	processor2.sendMessgeTo(m,processor3.outChannels.get(0));

       	compute(processor3);
        compute(processor3);
        compute(processor3);
    }

    /**
     * A dummy computation.
     * @param p
     */
    public void compute(Processor p) {
    
        System.out.println("Doing some computation on Processor" + p.id);
    }

    /**
     *
     * @param to processor to which message is sent
     * @param channel the incoming channel on the to processor that will receive this message
     */
    public void send(Processor to, Buffer channel) {
        to.sendMessgeTo(null, channel); // ALGORITHM
    }
}
