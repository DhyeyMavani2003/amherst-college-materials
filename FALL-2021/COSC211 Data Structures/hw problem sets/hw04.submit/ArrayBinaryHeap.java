public class ArrayBinaryHeap<E extends Comparable<E>> {    
    // setting a high default capacity
    public static final int DEFAULT_SIZE = 10000000;
    
    // defining an array object called contents
    private Object[] contents;

    // initializinf size to 0
    private int size = 0;

    // defining ArrayBinaryHeap as making a new array of given size
    public ArrayBinaryHeap() {
	contents = new Object[DEFAULT_SIZE];
    }

    // defining size method
    public int size() {
	return this.size;
    }

    // defining isEmpty() method
    public boolean isEmpty() {
	return (size == 0);
    }

    // defining min method
    public E min() {
        // storing the current value in cur
        E cur = (E) contents[0];

        // initialising counter i to 0
        int i = 0;

        // looping around the array
        while (i < size) {
            // storing the current moving value in cur_i as we progress into the array
            E cur_i = (E) contents[i];

            // if cur_i < cur, then set cur to cur_i
            if (cur_i.compareTo(cur) < 0) {
                cur = cur_i;
            }

            // incrementing the counter
            i++;
        }

        // returning the global minimum of the array
        return cur;
    }

    // defining insert method
    public void insert(E x) {
        // adding the element x to end of array
        contents[size] = x;

        // incrementing size
        size++;

        // bubbling up the rest elements to adjust for the new addition. 
        // Refer bubble_up method for more information
        bubble_up(size-1);
    }

    // defining removeMin method
    public E removeMin() {
        if (size == 0) {
            return null;
        }
        // setting cur to the last element
        E cur = (E) contents[size - 1];

        // decrementing size
        size--;

        // tricklig down the rest elements to adjust for the new removal. 
        // Refer to trickle_down method for more information
        trickle_down(size - 1);

        // return cur
        return cur;
    }

    // defining left method
    public int left(int i) {
        // returning (2i + 1)
        return (2 * i + 1);
    }

    // defining right method
    public int right(int i) {
        // returning 2(i+1)
        return (2 * (i + 1));
    }

    // defining parent method
    public int parent(int i) {
        // returning (i-1)/2
        return (i - 1)/2;
    }

    // defining bubble_up method
    public void bubble_up(int i) {
        // defining p index as parent of i
        int p = parent(i);

        // storing the value of i th element in the temporary variable value_i
        E value_i = (E) contents[i];

        // storing the value of p th element in the temporary variable value_p
        E value_p = (E) contents[p];

        // looping through if i is positive (thus valid!) and value_i < value_p
        while ((i > 0) && (value_i.compareTo(value_p) < 0)) {
            // algorithm of swapping the values
            // defining and setting temp to be value_i
            E temp = value_i;

            // setting value_i to be value_p
            value_i = value_p;

            // setting value_p to be equal to temp
            value_p = temp;

            // swapping the indexes
            // setting index i to p
            i = p;

            // setting p to be parent of i
            p = parent(i);
        }
    }

    // defining the trickle_down method
    public void trickle_down(int i) {
        // looping while we have a valid index
        while (i >= 0) {
            // defining value_i and setting it to value of element at index i
            E value_i = (E) contents[i];

            // defining and setting j as -1
            int j = -1;
            
            // defining and setting r to be right of i
            int r = right(i);

            // defining value_r and setting it to value of element at index r
            E value_r = (E) contents[r];

            // defining and setting l to be left of i
            int l = left(i);

            // defining value_l and setting it to the value of element at index l
            E value_l = (E) contents[l];
            
            // if r is valid index and value at r is less than the value at i
            if ((r < size) && ((value_r).compareTo(value_i) < 0)) {
                // if value at l is less than the value at r
                if ((value_l).compareTo(value_r) < 0) {
                    // setting j as l
                    j = l;
                } // otherwise if value at r is greater than equal to the value at i
                else {
                    // setting j as r
                    j = r;
                }
            } // otherwise if value at r is greater than or equal to the value at i
            else {
                // if l is valid index and value at l is less than the value at i
                if ((l < size) && ((value_l).compareTo(value_i) < 0)) {
                    // setting j to l
                    j = l;
                }
            }
            
            // if j is  a valid index
            if (j >= 0) {
                // algorithm of swapping the values
                // defining and setting temp to the value at i
                E temp = (E) contents[i];

                // setting value at i to be value at j
                contents[i] = contents[j];

                // setting the value at j to be temp
                contents[j] = temp;
            }
            // setting i to j
            i = j;
        }
    }
}
