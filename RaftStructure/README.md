# Raft Distributed Structure

*Description*: Download an implemenation of Raft from the Internet. Using Raft, implement a distributed, fault-tolerant queue data structure, _FTQueue_, that exports the following operations to the clients:

- int _create\_Queue_(int label); // Create a new queue of integers, associates this queue with label and returns queue id (int)
- int _get\_qid_(int label); // returns queue id of the queue associated with label
- void _push_(int queue\_id, int item); // enters item in the queue
- int _pop_(int queue\_id); // removes an item from the queue and returns it
- int _top_(int queue\_id); // returns the value of the first element in the queue
- int _qsize_(int queue\_id); // returns the number of items in the queue

_FTQueue_ must be able to tolerate up to two server crash failures.

***

#### Description

There will be one client and a cluster of servers.

*Client*: Client will be program responsible for issuing the above commands to the server cluster. The client only communicates with
the current leader in the cluster. While running, the client should await input from the user to determine which command to issue.

*Server*: The cluster of servers will initially be leaderless. According to the timeout scheme, one node will timeout first, become a candidate, and trigger an election. Once a candidate receives a majority vote and becomes the leader, the client needs to be informed.
- Startup:
  1. All nodes are created as followers.
  2. All nodes have separate times with random, separate heartbeat timeout between 150 and 300 ms
  3. Once a followers reaches its heartbeat timeout, it becomes a candidate, issues vote requests to all other nodes, and votes for itself

- Elections:
  1. Each timed-out follower becomes a candidate, increments the current term number, votes for himself, and issues a vote request from the rest of the nodes.
  2. Each follower who received a vote request checks that the candidate is as up-to-date in committed entries, and if so votes for that candidate.
  3. Each node can vote for at most only 1 candidate.
  4. If a candidate reaches an election time-out occurs without a leader being chosen, the term in incremented and the election restarts.

- Normal Operations [_Log Replications_]:
  1. Leader sends out a regular heartbeat message to all other nodes. Each time a follower receives a heartbeat node, it resets its heartbeat timeout.
  2. For each command:
    1. Leader recevied command from client.
    2. Leader adds command to his log, uncommitted.
    3. Leader forwards request to all followers in next AppendEntry message.
    4. Once leader has received confirmation from a majority of the followers, it commits the entry in its log and informs the followers that the entry is committed.

####Psuedocode
  1. Establish the server cluster as group of followers.
  2. Establish servers' timeouts.
  3. Establish the client to the cluster.
  4. Wait for commands from input.
  5. Return results from commands.
  6. Implement exit command.

***

#### Files

- AtomixRaft/src/main/diana/ecen5673java/FTQueue.java: Java class containing the FTQueue object, interfacing an Atomix DistributedQueue object.
- AtomixRaft/src/main/diana/ecen5673java/FTQueueClient.java: Java class containing the client interface for the FTQueue object.
- AtomixRaft/src/main/diana/ecen5673java/FTQueueServer.java: Java class containing the server interface for the FTQueue object.

***

#### Current Status

To start up the cluster, the FTQueueServer class must be run. In order to survive 2 server failures:

``` 
The ideal number of replicas should be calculated as 2f + 1 where f is the number of failures to tolerate. 
``` 

Therefore, 5 servers are needed at a minimum.

This project was built upon the [Atomix](http://atomix.io/atomix/) Raft implementation, which creates a cluster of [AtomixReplicas](http://atomix.io/atomix/docs/clustering/) which can be accessed through [AtomixClients](http://atomix.io/atomix/docs/getting-started/#creating-a-client). To create a distributed queue, an Atmoix [DistributedQueue](http://atomix.io/atomix/docs/collections/#distributedqueue) was interfaced through the FTQueue structure. After creating the client, the user is instructed as to which commands can be accepted in creating and modifying an FTQueue object. Servers can query the same FTQueue object to ensure that all servers see the same information after pushes/pops. Server crashes are simulated by instructing any server to [leave the cluster group](http://atomix.io/atomix/docs/groups/#leaving-the-group). 

The submitted files were constructed and run on Eclipse Neon.

**Servers**

To start up the cluster, the FTQueueServer can either take in the desired hostnames/ports, or run through default names by specifying which machine you desire to run. For 5 servers, 5 different terminal windows must be run with the following commands:
```
java FTQueueServer 1  # Corresponds with # java FTQueueServer localhost:5000 localhost:5001 localhost:5002 localhost:5003 localhost:5004
java FTQueueServer 2  # Corresponds with # java FTQueueServer localhost:5001 localhost:5000 localhost:5002 localhost:5003 localhost:5004
java FTQueueServer 3  # Corresponds with # java FTQueueServer localhost:5002 localhost:5000 localhost:5001 localhost:5003 localhost:5004
java FTQueueServer 4  # Corresponds with # java FTQueueServer localhost:5003 localhost:5000 localhost:5001 localhost:5002 localhost:5004
java FTQueueServer 5  # Corresponds with # java FTQueueServer localhost:5004 localhost:5000 localhost:5001 localhost:5002 localhost:5003
```
By default, server logs are saved in corresponding log files log1-log5.

Once an AtomixReplica is created, it will attempt to join the cluster of the other host addresses in the cluster, with one replica being elected as the leader. The output of the terminal will show which replica is elected leader, the current term number, and messages whenever a member leaves/joins the group. The servers can receive commands to exit/rejoin a group (simulating server crashes), query the current status of a specified queue by queue label, or to exit the program.

**Client**

The FTQueueClient can be started before/after the server cluster has been started. It can be supplied with a list of the cluster addresses, or use the default values, as seen below:
```
java FTQueueClient # Corresponds with # java FTQueueClient localhost:5000 localhost:5001 localhost:5002 localhost:5003 localhost:5004
```
The program then prompts the user to input commands, first creating a queue and then manipulating it.

**Issues**

Currently, is the server cluster is running and the client is restarted, the client will not think that the distributed queues exist and will require the user to recreate FTQueue objects with the same labels. Calling the create command using an exisiting label at this point will not create a new queue, but will reconnect with the exisiting cluster's distributed resource.
