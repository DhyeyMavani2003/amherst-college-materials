**__1. Using your BinarySearchTree implementation, run the provided RandomBSTBuilder program. This program builds a binary search tree by inserting random values into the tree and records the tree’s height in a file tree-heights.csv. Generate a plot of this output. How quickly does the height of a random BST seem to grow with its size? Is this growth what you expected? Why or why not?**

Answer

We can clearly see from the graph of height versus set size that the height grows logarithmically with the set size. This was expected as we need h(n) = log(n). This makes the height grow really slowly when we get to higher and higher set sizes. The graph is submitted as a separate file titled "height vs. size.png".




**__2. In Assignment 02, you wrote a program that counted the number of distinct words in a text file using a SimpleUSet implementation. Since words are stored as Strings—which implement the Comparable interface—the words can be stored in a SimpleSSet as well. Modify your word-reading program from Assignment 02 to compare the performance of your BinarySearchTree implementation to the provided ArraySimpleSSet implementation of SimpleSSet. Note that ArraySimpleSSet stores the words in an array, and uses binary search for all finding operations. Which implementation, BinarySearchTree or ArraySimpleSSet is faster for reading the words from a few provided texts? Is the difference significant? Why might you expect one or the other implementation to be faster?**

Answer

According to the different graphs, visualizations which you can see in the table/graph file attached, I can say that the BinarySearchTree.java is significantly faster than the ArraySimpleSSet.java except for the EnglishWords.txt file. My expectation aligns with the observation, because the binary search tree when implemented with nodes should be faster than the array implementation where we are looping around with higher time complexity. The BinarySearchTree is significantly slower than the ArraySimpleSSet in case of EnglishWords.txt because in that case the BinaryTree essentially converts into a linked list as every word in that file is different. Hence, just in the case of EnglishWords.txt, ArraySimpleSSet will be faster.
