Final: Question 8

Suppose you have a three node replica set. Node 1 is the primary. Node 2 is a secondary, Node 3 is a secondary running with a delay of 10 seconds. All writes to the database are issued with w=majority and j=1 (by which we mean that the getLastError call has those values set). 

A write operation (could be insert or update) is initiated from your application at time=0. At time=5 seconds, the primary, Node 1, goes down for an hour and another node is elected primary. 

Will there be a rollback of data when Node 1 comes back up? Choose the best answer.


Yes, always

No, never

Maybe, it depends on whether Node 3 has processed the write.

Maybe, it depends only on whether Node 2 has processed the write.
