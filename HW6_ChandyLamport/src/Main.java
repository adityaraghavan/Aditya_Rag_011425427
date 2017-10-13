import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by tphadke on 9/27/17.
 */
public class Main {

	Algorithm algo;

	Processor processor1;
	Processor processor2;
	Processor processor3;
	
	List<Buffer> inChannelsP1;
	List<Buffer> outChannelsP1;
	Buffer channelP12;
	Buffer channelP13;

	List<Buffer> inChannelsP2;
	List<Buffer> outChannelsP2;
	Buffer channelP21;
	Buffer channelP23;

	List<Buffer> inChannelsP3;
	List<Buffer> outChannelsP3;
	Buffer channelP31;
	Buffer channelP32;

	public static void main(String args[]) {


		Main m = new Main();
		m.init();
		m.start();
	}

	public void init()
	{
		
		//Channels From Processor 1 to 2 and 3
		channelP12 = new Buffer("12");
		channelP13 = new Buffer("13");

		//Channels From Processor 2 to 1 and 3
		channelP21 = new Buffer("21");
		channelP23 = new Buffer("23");

		//Channels From Processor 3 to 1 and 2
		channelP31 = new Buffer("31");
		channelP32 = new Buffer("32");

		//Initializing Processor 1 with channels
		outChannelsP1 = new ArrayList<>();
		outChannelsP1.add(channelP12);
		outChannelsP1.add(channelP13);

		inChannelsP1 = new ArrayList<>();
		inChannelsP1.add(channelP21);
		inChannelsP1.add(channelP31);

		processor1 = new Processor(1, inChannelsP1, outChannelsP1); //Only observes in channels.
		
		//Initializing Processor 2 with channels
		outChannelsP2 = new ArrayList<>();
		outChannelsP2.add(channelP21);
		outChannelsP2.add(channelP23);

		inChannelsP2 = new ArrayList<>();
		inChannelsP2.add(channelP12);
		inChannelsP2.add(channelP32);
		processor2 = new Processor(2, inChannelsP2, outChannelsP2); //Only observes in channels.


		//Initializing Processor 3 with channels
		outChannelsP3 = new ArrayList<>();
		outChannelsP3.add(channelP31);
		outChannelsP2.add(channelP32);

		inChannelsP3 = new ArrayList<>();
		inChannelsP3.add(channelP13);
		inChannelsP3.add(channelP23);
		processor3 = new Processor(3, inChannelsP2, outChannelsP2); //Only observes in channels.

		List<Processor> neighbours = new ArrayList<Processor>();
		neighbours.add(processor2);
		neighbours.add(processor3);
		processor1.neighbours.addAll(neighbours);
		
		neighbours.clear();
		neighbours.add(processor1);
		neighbours.add(processor3);
		processor2.neighbours.addAll(neighbours);

		neighbours.clear();
		neighbours.add(processor1);
		neighbours.add(processor2);
		processor3.neighbours.addAll(neighbours);

		//Init Algo
		algo = new Algorithm(processor1, processor2, processor3);
	}
	
	public void start(){

		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.execute(new Runnable() {
			public void run() {
			     algo.executionPlanP1();
			}

		});
		executor.execute(new Runnable() {
			public void run() {
				algo.executionPlanP2();

			}

		});
		executor.execute(new Runnable() {
			public void run() {
				algo.executionPlanP3();
			}

		});

		executor.shutdown();
		try {

			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

		} catch (InterruptedException e) {
			
		}
	}
}