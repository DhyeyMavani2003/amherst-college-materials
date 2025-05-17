public class CADeque<E> implements SimpleDeque<E> {

    private int head = -1; //start at nonsensical index 
    private int tail = -1;
    private int size = 0;
    private int capacity;
    private Object[] contents;
    
    public static final int DEFAULT_DEQUE_CAPACITY = 100; 

    public CADeque() { //connstructor for deque of default/undespecified size
        this(DEFAULT_DEQUE_CAPACITY);
    }

    public CADeque (int initialCapacity) { //constructor of deque with specified size
        this.capacity = initialCapacity;
        contents = new Object[this.capacity];
    }

    @Override
    public int size() { //implements size method, we can find size based off the difference between
        //the value of the head and the value of the tail of our deque within our array
        return this.size;
    }
    
    @Override
    public boolean isEmpty() { //if both head and tail have not had an updated index then we know our deque is empty
        return (this.head == -1 && this.tail == -1); 
    }

    @Override
    public void addFirst(E x) {
        
        if (this.size() == capacity){ //if we are at capacity we need to first increase the size of our array
            this.increaseCapacity();
        }
        
        if (isEmpty()) { //if we are adding to an empty array the head and tail should reference the same element
            head = 0; 
            tail = 0;
        }

        else if (head == 0){
            head = capacity - 1; //if head is the at the first index the new head will be the array's last index
        }

        else { //if head is at any other index we move it back one
            head--;
        }
        
        size++; //then we increment size and set the contents of our head to be our added value
        contents[head] = x;
    }


    @Override
    @SuppressWarnings("unchecked")
    public E removeFirst() {
        if (isEmpty()) { //cannot remove from an empty array
            throw new IndexOutOfBoundsException();
        }

        E toReturn = (E) contents[head]; //store the value we wish to return

        if (this.size() == 1){ //if we are removing the deque's only element we need to set head and tail as we had in our original empty deque
            head = -1;
            tail = -1;
    
        }

        else { //we move the head up one spot, if its current index is the last we have to set its new one to be the arrays first (i.e 0)
            if (head == capacity - 1) {
                head = 0;
            }

            else {
                head++;
            }
        }

        //decrement size and return our value
        size--;
        return toReturn;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peekFirst() { //can't peek from any empty deque
        if(isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        //returns value of element at the head
        else return (E) contents[head];
    }


    @Override

    public void addLast(E x) {
        //similar to addFirst except we switch direction of our shifting (tail shifts up instead of down with an added element)
        //everything else is the same replacing tail with head
        if (this.size == capacity) {
            increaseCapacity();
        }
        
        if (isEmpty()) {
            head = 0;
            tail = 0;
        }

        else if (tail == capacity - 1) {
            tail = 0;
        }

        else {
            tail++;
        }

        size++;
        contents[tail] = x;

    }


    @Override
    @SuppressWarnings("unchecked")
    public E removeLast() {
        //again this method is the mirror image of remove first with head and tail substituted and our shifting directions reversed
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        E toReturn = (E) contents[tail];

        if (head == tail) {
            head = -1;
            tail = -1;
        }

        else if (tail == 0) {
            tail = capacity -1;
        }

        else {
            tail--;
        }

        size--;
        return toReturn;
    
    }

    @Override
    @SuppressWarnings("unchecked")
    //same as peekFirst
    public E peekLast() {
        if(isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        else return (E) contents[tail];
      
    }

    //copied method from array simple list that allows us to increase capacity of our array when it is at capacity
    //altered it slightly to work with circular array by reindexing in our new array
    private void increaseCapacity() {
        // create a new array with larger capacity
        Object[] bigContents = new Object[2 * capacity];
    
        // copy contents to bigContents
        for (int i = 0; i < capacity; ++i) {
            bigContents[i] = contents[(head + i)%capacity];
            //index into CADeque = (head+index)%length of array
        }
    
        // set contents to refer to the new array
        contents = bigContents;
    
        // update this.capacity accordingly
        capacity = 2 * capacity;

        head = 0;
        tail = size-1; //since we reindex in our new array we need to redefine our head and tail
        }

}
