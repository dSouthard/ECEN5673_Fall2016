package main.diana.ecen5673.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import io.atomix.AtomixClient;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;

public class FTQueueClient {

	private static Scanner scanner = new Scanner(System.in);
	private static List<Address> cluster = new ArrayList<>();

	/**
	 * Starts the Servers and Distributed Queue.
	 */
	public static void main(String[] args) throws Exception {
		// Create list of server addresses, either from command line input or default
		List<String> memberList = new ArrayList<>();
		if (args.length == 0){
			memberList = Arrays.asList("localhost:5000", "localhost:5001", "localhost:5002", "localhost:5003", "localhost:5004");
			// Print message showing default servers' info
			System.out.println("Using default server address hosts:ports: \n" + memberList.toString());
		}

		else {
			// Build a list of all member addresses from command line to connect.
			memberList = Arrays.asList(args);
			// Print message showing default servers' info
			System.out.println("Using inputted server address hosts:ports: \n" + memberList.toString());
		}

		// Build a list of all member addresses to which to connect	
		// Create array of addresses
		for (String item : memberList) {
			String[] parts = item.split(":");
			cluster.add(new Address(parts[0], Integer.valueOf(parts[1])));
		}

		// Create a client
		AtomixClient client = AtomixClient.builder()
				.withTransport(new NettyTransport())
				.build();

		client.connect(cluster).join();
		System.out.println("Client connected!");

		takeCommands(client);
	} // End of main

	private static void takeCommands(AtomixClient client) {
		/* Create a new hashmap to hold created queues
		 * Use LinkedHashMap for performance increases in iteration and accessing
		 */
		Map<String, FTQueue> queueList = new LinkedHashMap<>();

		System.out.println("Client is ready to accept commands. Choose from the following:\n"
				+ "\t create <label>: creates a new queue with the desired label and associates it with an integer id. \n"
				+ "\t getID <label>: returns the queue ID of the queue with the desired label. \n"
				+ "\t push <label>: pushes an item onto the queue with the desired label. \n"
				+ "\t pop <label>: removed an item from the queue with the desired label and returns it. \n"
				+ "\t top <label>: returns the value of the first element in the queue with the desired label. \n"
				+ "\t size <label>: returns the number of items in the queue with the desired label. \n"
				+ "\t help: reprint this help statement. \n"
				+ "\t exit: quits the program.");

		String command = null, label = null;

		while (true){
			System.out.println("Ready for next command: ");
			command = scanner.next().toLowerCase();

			// Check if any queue has been setup yet
			if (queueList.size() == 0 && !command.equals("create")){
				System.out.println("Please set up a queue first, using the 'create' command.");
				continue;
			}

			// Perform requested command.
			switch(command){
			case "create":
				System.out.println("Please enter in queue label: ");
				label = scanner.next();
				if (!queueList.containsKey(label)){
					// FTQueue with that label not yet created, create an FTQueue and add to the LinkedHashMap
					FTQueue queue = new FTQueue(client, label);
					System.out.println("Queue " + label + " has been created.");
					queueList.put(label, queue);
					System.out.println("List size is now: " + queueList.size());
				}
				else {
					// FTQueue with that label has already been created
					System.out.println("Queue " + label + " has already been created.");
				}
				break; // End of create command
			case "getid":
				label = checkLabel(queueList);
				if (label != null){
					// DistributedQueue with that label has already been created
					System.out.println("Queue " + label + " has the following id: " + queueList.get(label).getQueueID());
				}
				break; // end of getId command
			case "push":
				label = checkLabel(queueList);
				if (label != null){
					// DistributedQueue with that label has already been created
					System.out.println("Please enter in desired value [integer]: ");
					try {
						// Try to push value onto the queue
						int value = scanner.nextInt();
						queueList.get(label).push(value);
						System.out.println("Value pushed onto the queue is: " + value);
					} catch (Exception e){
						System.err.println("Caught Exception " + e.getMessage());
					}
				}
				break; // end of push command
			case "pop":
				label = checkLabel(queueList);
				if (label != null){
					// Retrieve value from queue, if possible
					System.out.println("Popped value from the queue is: " + queueList.get(label).pop());
				}
				break; // end of pop command
			case "top":
				label = checkLabel(queueList);
				if (label != null){
					// Retrieve the top value from the queue
					System.out.println("Top value from the queue is: " + queueList.get(label).top());
				}
				break; // end of top command
			case "size":
				label = checkLabel(queueList);
				// Retrieve queue size
				if (label != null) {
					int size = queueList.get(label).queueSize();
					System.out.println("Queue size is " + size);
				}
				break; // end of size command
			case "help":
				System.out.println("Client is ready to accept commands. Choose from the following:\n"
						+ "\t create: creates a new queue and associates it with an integer id. \n"
						+ "\t getID: returns the queue ID of the queue associated with the label. \n"
						+ "\t push: pushes an item onto the queue. \n"
						+ "\t pop: removed an item from the queue and returns it. \n"
						+ "\t top: returns the value of the first element in the queue. \n"
						+ "\t size: returns the number of items in the queue. \n"
						+ "\t help: reprint this help statement. \n"
						+ "\t exit: quits the program.");
				break; // end of help command
			case "exit":
				System.out.println("Now exiting.");
				scanner.close();
				System.exit(0);
				break; // end of exit command
			}
		}
	}

	private static String checkLabel(Map<String, FTQueue> queueList){
		String label = null;
		System.out.println("Please enter in queue label: ");
		label = scanner.next();
		if (!queueList.containsKey(label)){
			// FTQueue with that label not yet created
			System.out.println("Queue with label " + label + " has NOT yet been created.");
			return null;
		}
		else {
			return label;
		}
	}
}
