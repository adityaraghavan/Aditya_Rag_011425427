-----------
Homework 5b
-----------
This program takes an execution plan for processes, updates the Vector Clocks and gets the maximal cut.

Input
-----

- 3 processes with id.
- Execution plan given
- Cut value at each process

Output
------
****Maximal Consistent Cut is VectorClock [vc=[3, 5]]
 
--------------Output Displayed--------------

Starting execution for p0
Starting execution for p1

---Message sent by p0 to p1 Message [messageType=SEND, vc=VectorClock [vc=[1, 0]]]
---VC for p0 is:VectorClock [vc=[1, 0]]

---Compute by p1 Message [messageType=COMPUTATION, vc=null]
---VC for p1 is:VectorClock [vc=[0, 1]]

---Compute by p1 Message [messageType=COMPUTATION, vc=null]
---VC for p1 is:VectorClock [vc=[0, 2]]

---Compute by p1 Message [messageType=COMPUTATION, vc=null]
---VC for p1 is:VectorClock [vc=[0, 3]]

---Compute by p0 Message [messageType=COMPUTATION, vc=null]
---VC for p0 is:VectorClock [vc=[2, 0]]

---Message received by p1 from p0 Message [messageType=RECEIVE, vc=VectorClock [vc=[0, 3]]]
---VC for p1 is:VectorClock [vc=[1, 4]]

---Compute by p1 Message [messageType=COMPUTATION, vc=null]
---VC for p1 is:VectorClock [vc=[1, 5]]

---Compute by p0 Message [messageType=COMPUTATION, vc=null]
---VC for p0 is:VectorClock [vc=[3, 0]]

Receive initiated: p1 is waiting for p0

---Message sent by p0 to p1 Message [messageType=SEND, vc=VectorClock [vc=[4, 0]]]
---VC for p0 is:VectorClock [vc=[4, 0]]

---Message received by p1 from p0 Message [messageType=RECEIVE, vc=VectorClock [vc=[1, 5]]]
---VC for p1 is:VectorClock [vc=[4, 6]]

*****Completed execution for p1. Vector Clock at p1 is:VectorClock [vc=[4, 6]]

*****Completed execution for p0. Vector Clock at p0 is:VectorClock [vc=[4, 0]]
Enter cut value at p0: 
3
Enter cut value at p1: 
6

****Maximal Consistent Cut is VectorClock [vc=[3, 5]]