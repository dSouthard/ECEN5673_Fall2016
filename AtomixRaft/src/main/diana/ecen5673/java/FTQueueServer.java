/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package main.diana.ecen5673.java;

import io.atomix.AtomixReplica;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;
import io.atomix.group.DistributedGroup;
import io.atomix.group.LocalMember;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Distributed queue.
 */
public class FTQueueServer {

	/**
	 * Starts the Servers and Distributed Queue.
	 */
	public static void main(String[] args) throws Exception {
		// Change which case number is being run
		int caseNum = 1;

		if (args.length == 1){
			caseNum = Integer.valueOf(args[0]);
		}
		// Create list of server addresses, either from command line input or default
		List<String> memberList = new ArrayList<>();
		if (args.length == 0 || args.length == 1 ){
			switch (caseNum){
			case 1:
				memberList = Arrays.asList("localhost:5000", "localhost:5001", "localhost:5002", "localhost:5003", "localhost:5004");
				break;
			case 2:
				memberList = Arrays.asList("localhost:5001", "localhost:5000",  "localhost:5002", "localhost:5003", "localhost:5004");
				break;
			case 3:
				memberList = Arrays.asList("localhost:5002","localhost:5000", "localhost:5001", "localhost:5003", "localhost:5004");
				break;
			case 4:
				memberList = Arrays.asList("localhost:5003", "localhost:5000", "localhost:5001", "localhost:5002", "localhost:5004");
				break;
			case 5:
				memberList = Arrays.asList("localhost:5004", "localhost:5000", "localhost:5001", "localhost:5002", "localhost:5003");
				break;
			}
			// Print message showing default servers' info
			System.out.println("Using default server address hosts:ports: \n" + memberList.toString());
		}

		else {
			// Build a list of all member addresses from command line to connect.
			memberList = Arrays.asList(args);
			// Print message showing default servers' info
			System.out.println("Using inputted server address hosts:ports: \n" + memberList.toString());
		}

		// Build a list of all member addresses to which to connect.
		List<Address> cluster = new ArrayList<>();
		// Create array of addresses
		for (String item : memberList) {
			String[] parts = item.split(":");
			cluster.add(new Address(parts[0], Integer.valueOf(parts[1])));
		}

		/* Create a stateful Atomix replica. The replica communicates with other replicas in the cluster
		 * to replicate state changes.
		 * 
		 * Replicas serve as hybrid AtomicClient and server to allow a server to be embedded in an application. From the perspective
		 * of state, replicas behave like servers in that they maintain a replicated sate machine for Resources and fully participate
		 * in the underlying consensus algorithm. From the perspective of resources, replicas behave like AtomixClients in that they
		 * may themselves create and modify distributed resources.
		 * 
		 * To create a replica, use the builder factory. Each replica must initially be configured with a replica Address and a list
		 * of addresses for other members of the core cluster. Note that the list of member addresses does not have to include the local
		 * replica nor does it have to include all replicas in the cluster. As long as the replica can reach one live member of the 
		 * cluster, it can join.
		 */

		AtomixReplica atomix = AtomixReplica.builder(cluster.get(0))
				.withTransport(new NettyTransport())
				.withStorage(Storage.builder()
						.withStorageLevel(StorageLevel.DISK) // use DISK storage level for strongest level of consistency
						.withDirectory(new File("logs" + String.valueOf(caseNum)))
						.withMinorCompactionInterval(Duration.ofSeconds(30))
						.withMajorCompactionInterval(Duration.ofMinutes(1))
						.withMaxSegmentSize(1024 * 1024 * 8)
						.withMaxEntriesPerSegment(1024 * 8)
						.build())
				.build();

		/*
		 * Replica Lifecycle: When the replica is started, it will attempt to contact members in the configured startup Address list. If
		 * any of the members are already in an active state, the replica will request to join the cluster. During the process of joining
		 * the cluster, the replica will notify the current cluster leader of its existence. If the leader already knows about the joining
		 * replica, the replica will immediately join and become a full voting member. If the joining replica is not yet known to the rest
		 * of the cluster, it will join the cluster in a passive state in which it receives replicated state from other replicas in the cluster
		 * but does not participate in elections or other quorum-based aspects of the underlying consensus algorithm. Once the joining
		 * replica is caught up with the rest of the cluster, the leader will promote it to a full voting member. 
		 */
		System.out.println("Created a new replica.");

		// Make the cluster
		atomix.bootstrap(cluster).join();
		System.out.println("Replica has boostrapped the cluster.");

		// Create a leader election resource.
		DistributedGroup group = atomix.getGroup("group").get();

		/* servers transition between various states throughout their lifetimes. States are representing by the CopycatServer.State
		 * enum and each state represents a server's role in communicating with the other servers in the cluster and with the clients
		 * connected to them.
		 * 
		 * To listen for changes to a server's state, register a state change listener via CopycatServer's onStateChange method:
		 * Each time the server transitions state, all registered state change listeners will be called. When a server state listener 
		 * callback is registered, a Listener object will be returned. The Listener can be used to unregistered the state change callback
		 * by calling the close() method.
		 * 
		 * Used when testing FTQueue persistence in the face of 2 server crashes.
		 */
		//		Resource.State state = group.state();
		group.onStateChange(state -> {
			switch (state){
			case CONNECTED:
				// The resource is healthy
				System.out.println("This server's state is now CONNECTED!");
				break;
			case SUSPENDED:
				// the resource is unhealthy and operations may be unsafe
				System.out.println("This server's state is now SUSPENDED!");
				break;
			case CLOSED:
				// The resource has been closed and pending operations have failed
				System.out.println("This server's state is now CLOSED!");
				break;
			}
		});	

		/* 
		 * Setup listener to notify when a member joins or leaves the group
		 */
		group.onJoin(joinMember -> {
			System.out.println(joinMember.id() + " joined the group!");
		});

		group.onLeave(leaveMember -> {
			System.out.println(leaveMember.id() + " left the group!");
		});

		// Join the group
		LocalMember member = group.join().get();
		System.out.println("This server's memberID is " + member.id());

		// Register a callback to be called when the local member is elected the leader.
		group.election().onElection(term -> {
			if (term.leader().equals(member)) {
				System.out.println("THIS SERVER IS ELECTED LEADER!!!!!"); 
			}
			System.out.println("Now starting Term #" + term.term());
		});

		// Await commands from client
		System.out.println("Awaiting commands from client");

		System.out.println("Type [crash] to temporarily shutdown this server. Once crashed, type [join] to rejoin the cluster. \n"
				+ "Type [exit] to permentently close this server.\n"
				+ "Type [queue] to check the current status of the FTQueue as seen from this machine.");
		Scanner scanner = new Scanner(System.in);
		String input = null;
		boolean crashed = false;

		// Block while the replica is open.
		for (;;) {
			input = scanner.next().toLowerCase();
			switch(input){
			case "crash":
				member.leave().join();
				crashed = true;
				System.out.println("This server has left the group!");
				break;
			case "join":
				if (crashed){
					group.join().join();
				}
				else {
					System.out.println("Already a part of the group!");
				}
				break;
			case "queue":
				String label = null;
				System.out.println("Please enter in queue label: ");
				label = scanner.next();
				FTQueue queue = new FTQueue(atomix, label);
				System.out.println("Queue " + label + " has the following id: " + queue.getQueueID());
				int size = queue.queueSize();
				System.out.println("Queue size is " + size);
				if (size > 0) System.out.println("Top value from the queue is: " + queue.top());
				break;
			case "exit":
				scanner.close();
				member.leave().join();
				System.out.println("Exiting program.");
				System.exit(0);
			}
		}	
	} // End of main
}
