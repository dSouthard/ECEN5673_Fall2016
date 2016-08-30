# UDP Client/Server Program

*Description*: Write a simple UDP Client/Server program, where the client sends a UDP message to the server and the server sends a reply to the client. Record the local clock times of when each message is sent and received. Record these timing by first running the client on Machine A and the server on Machine B, and then running the client on Machine B and the server on Machine A for the following scenarios:

0. Machines A and B are on the _same machine_.
1. Machines A and B are on _different machines_ within the _same local network_.
2. Machines A and B are on _different machines_ within the _same wireless network_.
3. Machines A and B are on _different machines_ at _different geographic locations_.

***

#### Pseudocode

1. Determine which scenario is being run from list above (user input).
2. Determine whether machine is running as client or server (user input).
3. Set up server IP address and port.
4. Create UDP socket.
5. Bind the server.
6. Have client send message, recording time message was sent.
7. Wait for server to receive message, record time recieved, and send reply, recording time reply was send.
8. Wait for client to receive reply, record time reply was received.
9. Repeat at least 5 times.
10. Write timing results to resultFile, calculating latencies and statistical measurements for each time measurement. Generate resulting plots.

***

#### Files

- ClientServer.ipynb: Jupyter Notebook containing Python code for the client/server program
        
    * When running the program, 2 inputs are prompted from the user: choosing a number 0-3 to determine the scenario being
        tested, as listed above [default = 0], and choosing 0 or 1 to run the program as the client or server [default = 0]

- UDPClientServerOutput[X][Y].txt: files containing recording time for messages being sent/received
        
    * X: The number of the scenario being run, as listed above
        
    * Y: 0 = Client time, 1 = Server time

- ResultCalculations.ipynb: Jupyter Notebook containing the Python code for analyzing the program results

- ECEN5673DianaSouthardHW1.pdf: Final report answering questions listed below

***

#### Questions
**Question 1**: For each scenario, repeat the experiment 5 times by running it aroudn the same time for each scenario. Report all the timings and compute the pairwise latencies (A to B and B to A) along with average and standard deviation.


**Question 2**: Provide an analysis of your results in terms of why there is a variation in latencies, which ones you expect to be more accurate, etc.


**Question 3**: Compute the offset (o<sub>i</sub>) and delay (d<sub>i</sub>) for each of the 5 time measurements for each scenario using the NTP formulat. For each scenario, explain which ones of the five measurements you think provides the best accuracy.

***

