package main.diana.ecen5673.java;

import io.atomix.AtomixClient;
import io.atomix.AtomixReplica;
import io.atomix.collections.DistributedQueue;

public class FTQueue {

	private int queueID;
	private DistributedQueue<Object> queue;

	public FTQueue(AtomixClient client, String label) {
		// Create a distributed queue, accessible by the client
		queue = client.getQueue(label).join();

		// generate the queue ID, based on inputed label 
		queueID = label.hashCode();
		System.out.println("Queue created");
	}

	public FTQueue(AtomixReplica atomix, String label) {
		// Create a distributed queue, accessible by the server
		queue = atomix.getQueue(label).join();

		// generate the queue ID, based on inputed label 
		queueID = label.hashCode();
	}

	public int getQueueID() {
		return queueID;
	}

	public void push (int item) {
		// Add item to the queue
		queue.offer(item).join();
	}

	public Object pop(){
		// Removes an item from the queue and returns it
		return queue.poll().join();
	}

	public Object top() {
		// Returns the value of the first element in the queue
		return queue.peek().join();
	}

	public int queueSize() {
		// Returns the number of items in the queue
		return queue.size().join();
	}

}
