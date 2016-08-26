# UDP Client/Server Program

*Description*: Write a simple UDP Client/Server program, where the client sends a UDP message to the server and the server sends a reply to the client. Record the local clock times of when each message is sent and received. Record these timing by first running the client on Machine A and the server on Machine B, and then running the client on Machine B and the server on Machine A for the following scenarios:

- Machines A and B are on the _same machine_.
- Machines A and B are on _different machines_ within the _same local network_.
- Machines A and B are on _different machines_ within the _same wireless network_.
- Machines A and B are on _different machines_ at _different geographic locations_.

***

#### Files

- Server.ipynb: Jupyter Notebook containing Python code for the server
- Client.ipynb: Jupyter Notebook containing Python code for the client
- timingResults.txt: file containing recording time for messages being sent/received

***

#### Questions
**Question 1**: For each scenario, repeat the experiment 5 times by running it aroudn the same time for each scenario. Report all the timings and compute the pairwise latencies (A to B and B to A) along with average and standard deviation.


**Question 2**: Provide an analysis of your results in terms of why there is a variation in latencies, which ones you expect to be more accurate, etc.


**Question 3**: Compute the offset (o<sub>i</sub>) and delay (d<sub>i</sub>) for each of the 5 time measurements for each scenario using the NTP formulat. For each scenario, explain which ones of the five measurements you think provides the best accuracy.

***

#### Pseudocode
1. Determine whether machine is running as client or server (commandline argument).
2. Determine which scenario is being run from list above (commandline argument).
3. Set up server IP address and port.
4. Create UDP socket.
5. Bind the server.
6. Have client send message, recording time message was sent.
7. Wait for server to receive message, record time recieved, and send reply, recording time reply was send.
8. Wait for client to receive reply, record time reply was received.
9. Repeat at least 5 times.
10. Write timing results to resultFile, calculating latencies and statistical measurements for each time measurement. Generate resulting plots.

***
