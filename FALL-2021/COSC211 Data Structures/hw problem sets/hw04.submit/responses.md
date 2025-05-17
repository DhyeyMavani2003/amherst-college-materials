**1. Write a program that compares the performance (i.e., empirical running time) of your heap-based priority queue and the provided AVLTree implementation. Be sure to test both the insert and removeMin methods. How do the running times scale with the size of the data structure for the two implementations? How do the running times for the two implementations compare to each other?**

Answer

The Times for insert and RemoveMin methods of AVL and Heap implementations are comparable and constant on an exponential scale, which suggests that they are O(logn) as expected. Time increases logarithmically with size. Also, a reason for this can be visualized in the balanced nature of both AVL and Heap Trees. If we test it near the small marginal sizes of 1-10, by visualizing the graph, we can conclude that AVL insert is faster than Heap insert. On the other hand, the removeMin methods are very comparable, but again by the same graph we can say that the Heap removeMin is a bit faster than the AVL removeMin. Please refer to the data generated in the times.csv and the multiple graphs generated thereof.



**2. Suppose you wanted to supplement your ArrayBinaryHeap with a method to search for a given element in the heap (like the find method for SimpleSSet). Without modifying the heap structure (and insert/remove methods), is it possible to search for a given element significantly faster than traversing the entire array? Why or why not?**

Answer

We cannot write a faster find method in the case of heap because the only condition in the case of heap is that the children should be greater than the parent. This leaves no notion of left and right sorting according to values, which leads to an state where the tree is balanced but we cannot guarantee if the largest/smallest is on either side. This randomness gives rise to uncertainity of direction, which leads us to have to search in the entire structure taking O(n) time.
