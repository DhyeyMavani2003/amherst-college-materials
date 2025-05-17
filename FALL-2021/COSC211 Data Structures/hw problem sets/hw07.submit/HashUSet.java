import java.util.Random;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashUSet<E> implements SimpleUSet<E> {
    public static final int DEFAULT_LOG_CAPACITY = 4;
    
    protected int logCapacity = DEFAULT_LOG_CAPACITY; // value d from lecture
    protected int capacity = 1 << logCapacity;        // n = 2^d
    protected int size = 0;
    protected Object[] table;                         // array of heads of linked lists

    // final = can't be changed after initial assignment!
    protected final int z;

    public HashUSet() {
	    // set capacity to 2^logCapacity
	    int capacity = 1 << logCapacity;
	
	    table = new Object[capacity];

	    // add a sentinel node to each list in the table
	    for (int i = 0; i < capacity; ++i) {
	        table[i] = new Node<E>(null);
	    }

	    // fix a random odd integer
	    Random r = new Random();	
	    z = (r.nextInt() << 1) + 1;
    }

    // defining node
    protected class Node<E> {
	    protected Node<E> next = null;
	    protected E value;

	    public Node(E value) {
	        this.value = value;
	    }
    }

    protected int getIndex(E x) {
	    // get the first logCapacity bits of z * x.hashCode()
	    return ((z * x.hashCode()) >>> 32 - logCapacity);
    }
    
    // defining size method
    @Override
    public int size() {
	    return this.size;
    }

    // defining isEmpty method
    @Override
    public boolean isEmpty() {
	    return this.size == 0;
    }

    // defining add method
    @Override
    @SuppressWarnings("unchecked")
    public boolean add(E x) {

        // increase the capacity if needed
        if (size + 1 > table.length) {
            increaseCapacity();
        }

        // store and get hashed index of x
        int index = getIndex(x);

        // defining current node and initializing it
        Node<E> current = (Node<E>) table[index];
        
        // iterate over until current's next becomes null in next step
        while (current.next != null) {
            if ((x).equals((current.next).value)) {
                return false;
            }
            current = current.next;
        }

        // making a new node storing x
        Node<E> entry = new Node<E>(x);

        // adding new node and incrementing the size
        current.next = entry;
        

        size++;
        return true;

    }

    // defining remove method
    @Override
    @SuppressWarnings("unchecked")
    public E remove(E x) {

        // getting the index
        int index = getIndex(x);
        // initializing the current node
        Node<E> current = (Node<E>) table[index];
        

        // iterating over
        while (current.next != null) {
            // if we find the element, remove and update the pointers
            if ((x).equals((current.next).value)) {
                E temp = (current.next).value;
                current.next = current.next.next;
                size--;
                return temp;
            }
            current = current.next;
        }

        // returning null as we didn't find the element to remove
        return null;
    }

    // defining the find method
    @Override
    @SuppressWarnings("unchecked")
    public E find(E x) {
        // getting index
        int index = getIndex(x);
        
        // setting and initializing the current node
        Node<E> current = (Node<E>) table[index];
        
        // iterate over
        while (current.next != null) {
            
            // if we find the ement then return it
            if ((x).equals((current.next).value)) {
                return (current.next).value;
            }
            // updating current pointer node
            current = current.next;
        }

        // returning null because the requested element is not found
        return null;

    }

    // defining increaseCapacity method
    @SuppressWarnings("unchecked")
    protected void increaseCapacity() {	
    	logCapacity += 1;
    	capacity = capacity << 1;

    	// store the old hash table
    	Object[] oldTable = table;

    	// make a new new has table and initialize it
    	table = new Object[capacity];
	    
    	for (int i = 0; i < table.length; ++i) {
    	    table[i] = new Node<E>(null);
    	}

    	// reset the size to 0 since it will get incremented when we
    	// add elements to the new table
    	size = 0;

    	// iterate over lists in oldTable and add elements to new table
    	for (int i = 0; i < oldTable.length; ++i) {
    	    Node<E> nd = ((Node<E>)oldTable[i]).next;
    	    while (nd != null) {
    		    this.add(nd.value);
    		    nd = nd.next;
    	    }
    	}
    }
}
