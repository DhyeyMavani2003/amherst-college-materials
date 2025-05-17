
// importing the random library
import java.util.Random;

public class SkipList<E> implements SimpleList<E> {
    
	// random number generator used to determine heights of new nodes
    private Random rand = new Random();

    // a reference to the sentinel node in the list, known as the head
    private Node<E> head = new Node<E>(null, 0);

    // the number of elements in the set
    private int size = 0;

	// defining the size method
    @Override
    public int size() {
		return this.size;
    }
	
	// defining the isEmpty method
    @Override
    public boolean isEmpty() {
		return this.size == 0;
    }

	// defining the pickHeight method the pick height with coin flip and height at random
    public int pickHeight() {
		int h = 0;
	    while (rand.nextBoolean()) {
			h++;
	    }
		return h;
    }

	// defining the Node<E> class
	public class Node<E> {
        // defining the value stored in node as x
		E x;

		// defining the next array of objects
        Object[] next;

		// defining the length array of integers
        int[] length;

		// defining the Node(ix,h) similar to professor's SimpleSSet implementation
        public Node(E ix, int h) {
            x = ix;
            next = new Object[h + 1];
            length = new int[h+1];
        }

		// defining the height() similar to professor's SimpleSSet implementation
        public int height() {
            return next.length - 1;
        }

		// defining the getNext method to get the next at height h
		@SuppressWarnings("unchecked")
		public Node<E> getNext(int h) {
			return (Node<E>) next[h];
		}

		// defining the setNext method to set node at height h
		public void setNext(int h, Node<E> nd) {
			next[h] = nd;
		}
    }

	// defining the findPred method to fing the predecessor
	public Node<E> findPred(int i) {
		// defining the node u as head
        Node<E> u = head;

		// defining the height of u as h
		int h = u.height();

		// setting r to h
        int r = h;

		// setting j to -1
        int j = -1;

		// while r is greater than equal to zero, then do...
        while (r >= 0) {
			// while next of u at height r is not null AND (j + u.length[r] < i), then do...
            while (u.next[r] != null && j + u.length[r] < i) {
				// increment j by u's length at height r
                j += u.length[r];

				// update u to u's next at height r
                u = u.getNext(r);
            }
			// decrementing r 
            r--;
        }

		// return the predecessor node for index i
        return u;
    }

	// defining the get method
	public E get(int i) {
		// if the index is out of bounds
        if (i < 0 || i > size-1) throw new IndexOutOfBoundsException();

		// return the value at the node
        return findPred(i).getNext(0).x;
    }

	// defining the set method
    public void set(int i, E x) {
		// if the index is out of bounds
        if (i < 0 || i > size-1) throw new IndexOutOfBoundsException();

		// defining the node u to the current node
        Node<E> u = findPred(i).getNext(0);

		// setting the value as x
        u.x = x;
    }

	// defining the add method
	public void add(int i, E x) {
		// defining u as head
		Node<E> u = head;

		// defining h as height of u
		int h = u.height();

		// if the index is out of bounds
        if (i < 0 || i > size) throw new IndexOutOfBoundsException();

		// create a new node w
        Node<E> w = new Node<E>(x, pickHeight());

		// if height of w is greater than sentinel, then update the sentinel's height
        if (w.height() > h) 
            h = w.height();

		// call addENode method
        addENode(i, w);
    }

	// defining an helper method called addENode
	public Node<E> addENode(int i, Node<E> w) {

		// defining u as head/sentinel
        Node<E> u = head;

		// defining h as height of node u
		int h = u.height();

		// defining k as height of node 
        int k = w.height();

		// defining r as h
        int r = h;

		// index of u
        int j = -1; 

		// while r is greater than equal to zero, then do...
        while (r >= 0) {
			// while next of u at height r is not null AND (j + u.length[r] < i), then do...
            while (u.next[r] != null && j+u.length[r] < i) {
				// increment j by u's length at height r
                j += u.length[r];

				// update u to u's next at height r
                u = u.getNext(r);
            }

			// accounts for new node in list 0
            u.length[r]++; 

			// if r is less than or equal to k 
            if (r <= k) {
				// setting next of w to next of u at height r
                w.next[r] = u.next[r];

				// setting next of u at height r to w
                u.next[r] = w;

				// setting length of w at height r to that of u at height r - (i-j)
                w.length[r] = u.length[r] - (i - j);

				// setting u's length at height r to (i-j)
                u.length[r] = i - j;
            }

			// decrementing r
            r--;
        }

		// incrementing size
        size++;

		// return node u
        return u;
    }

	// defining the remove method 
	public E remove(int i) {
		// if the index is out of the bounds
        if (i < 0 || i > size-1) throw new IndexOutOfBoundsException();

		// setting the x to null
        E x = null;

		// setting node u to head
        Node<E> u = head;

		// setting h to the height of node u
		int h = u.height();

		// setting r to h
        int r = h;

		// index of node u
        int j = -1; 

		// while r is greater than equal to zero, then do...
        while (r >= 0) {
			// while next of u at height r is not null AND (j + u.length[r] < i), then do...
            while (u.next[r] != null && j+u.length[r] < i) {
				// increment j by u's length at height r
				j += u.length[r];

				// update u to u's next at height r
				u = u.getNext(r);
            }

			// for the node we are removing
            u.length[r]--;  


			// if j + u's length at height r + 1 equals i AND u's next at height r is not null, then...
            if (j + u.length[r] + 1 == i && u.next[r] != null) {
                // setting x to value of u's next at height r's
				x = u.getNext(r).x;

				// incrementing the length of u to its next's length
                u.length[r] += u.getNext(r).length[r];

				// incrementing u's next at r to u's next's next
                u.next[r] = u.getNext(r).getNext(r);

				// if we are on either end...
                if (u == head && u.next[r] == null) {
					// decrementing h
					h--;
				}
            }
			// decrementing r
            r--;
        }
		// decrementing size
        size--;

		// return the removed value
        return x;
    }

}
