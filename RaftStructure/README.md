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

#### Pseudocode

1. 

***

#### Files

- FTQueue.ipynb: Jupyter Notebook containing Python code for the FTQueue data structure program

- ECEN5673DianaSouthardHW2.pdf: Final report describing implemenation and current status of the data structure

***

#### Current Status

Nothing has been done yet, Raft is still being researched