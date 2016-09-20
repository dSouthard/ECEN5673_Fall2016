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

- RaftImplementation.java: Java class containing setup and main function to run application.

- ECEN5673DianaSouthardHW2.pdf: Final report describing implemenation and current status of the data structure

***

#### Current Status

Nothing has been done yet, Raft is still being researched
