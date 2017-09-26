-----------
Homework 3
-----------
This program takes a graph as an input and builds a DFS spanning tree.

Input
-----

- 6 Processors with Id and unexplored list is populated.
- Created a Graph using Map <Processor, List<Processor> >

p0 = [p1, p2, p3]
p1 = [p0, p2, p4]
p2 = [p1, p0, p5]
p3 = [p0]
p4 = [p1, p5]
p5 = [p2, p4]

- Here Root is p0 and message M is sent to first processor in the list of unexplored.

Output
------

- Source and target Processors along with the type of message (<M>, <ALREADY>, <PARENT>) are printed in the console.
- Map(Processors and its children) which represents the spanning tree.
p0 = [p1, p3]
p1 = [p4]
p2 = []
p3 = []
p4 = [p5]
p5 = [p2]

--------------Output Displayed--------------

Sender	-	0	Receiver	-	1	Message	-	M
Sender	-	1	Receiver	-	4	Message	-	M
Sender	-	4	Receiver	-	5	Message	-	M
Sender	-	5	Receiver	-	2	Message	-	M
Sender	-	2	Receiver	-	1	Message	-	M
Sender	-	1	Receiver	-	2	Message	-	ALREADY
Sender	-	2	Receiver	-	0	Message	-	M
Sender	-	0	Receiver	-	2	Message	-	ALREADY
Sender	-	2	Receiver	-	5	Message	-	PARENT
Sender	-	5	Receiver	-	4	Message	-	PARENT
Sender	-	4	Receiver	-	1	Message	-	PARENT
Sender	-	1	Receiver	-	2	Message	-	M
Sender	-	2	Receiver	-	1	Message	-	ALREADY
Sender	-	1	Receiver	-	0	Message	-	PARENT
Sender	-	0	Receiver	-	3	Message	-	M
Sender	-	3	Receiver	-	0	Message	-	PARENT
Sender	-	0	Receiver	-	2	Message	-	M
Sender	-	2	Receiver	-	0	Message	-	ALREADY
Terminate!