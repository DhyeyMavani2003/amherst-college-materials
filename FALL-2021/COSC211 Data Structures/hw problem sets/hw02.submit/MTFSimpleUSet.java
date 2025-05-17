public class MTFSimpleUSet<E> implements SimpleUSet<E> {

    private int size = 0; // initializing size

    private Node<E> head = null; // initialising head

    //defining node
    private class Node<E> {
        E value; // initializing value
        Node<E> next = null; // initializing next

        public Node(E val) {//storing value
            this.value = val;
        }
    }

    //size method
    public int size() {
        return this.size;
    }

    //isEmpty method
    public boolean isEmpty() {
        return head == null; // returning the boolean which means the list is empty only if head is NULL
    }

    private Node<E> getNode(int i) {
        // check if i is a valid index
        if (i < 0 || i >= size) return null; // invalid index i

        Node<E> cur = head; //initializing cur to head

        // find the i-th successor of the head
        for (int j = 0; j < i; ++j) { 
            cur = cur.next; //search node by node
        }

        return cur;
    }

    // find method
    public E find(E x) {
        Node<E> nd = new Node<E>(x);//new node
        Node<E> cur = head; //initializing cur node
        for (int i = 0; i < size; i++) { // search in the list one by one
            E y = cur.value; // storing the current value in variable y
            if (x.equals(y)) { // if we found the semantically equal element
                //MTF Heuristic implemented here
                Node<E> pred = getNode(i-1); // defining the pred node
                pred.next = pred.next.next; // linking the pred node to the pred's next's next 
                cur.next = this.head; //making the cur linked to head
                this.head = cur; // making cur the new head
                return y;
            }
            cur = cur.next; //shifting our cur node to its next so as to mke progress in the search
        }
        return null; //if element not found
    }

    //add method
    public boolean add(E x) {
        Node<E> nd = new Node<E>(x);//new node
        Node<E> cur = head; //initialising cur node
        nd.value = x; //assigning the node nd a value x
        if (size == 0) {
            // if we are adding at index 0, the newly added item is
            // stored at the head
            nd.next = this.head;
            this.head = nd;
            size++;
            return true;
        } else {
            for (int i = 0; i < size; i++) { // traversing along the list
                E y = cur.value; // temporarily storing the cur value in variable y
                if (x.equals(y)) { // if the x is semantically equal to y
                    if (cur == head){ //if this is the first element
                        return false;
                    }
                    //MTF Heuristic implemented here
                    Node<E> pred = getNode(i-1); // defining pred node                   
                    pred.next = pred.next.next; // linking the pred node to the pred's next's next 
                    cur.next = this.head; //making the cur linked to head
                    this.head = cur; // making cur the new head
                    return false; 
                }
                cur = cur.next;// shift our cur node to the next in order to progress in the list
            }
            Node<E> pred = getNode(size - 1); // defining predecessor
            Node<E> succ = pred.next; // defining successor
            pred.next = nd; // linking pred to new node nd
            nd.next = succ; // linking new node nd to succ
            size++; // incrementing size
            return true;
        }
    }

    public E remove(E x) {
        Node<E> nd = new Node<E>(x);//new node
        Node<E> cur = head; // initialising cur node
        nd.value = x; // assigning the value x to nd node 
        if (size == 0) { // removing the only head value present
            E value = head.value; // storing head to return later after removal
            head = head.next;
            --size; // decrementing size
            return value; // returning removed value
        }
        else {
            for (int i = 0; i < size; i++) {
                E y = cur.value;// storing value in variable y
                if (x.equals(y)) { // if we found a semantically equal element
                    Node<E> pred = getNode(i - 1); // defining the predecessor in this case
                    E value = pred.next.value; // storing the value of element that is removed
                    pred.next = pred.next.next; // linking back the broken chain due to the removal of element in between
                    --size; // decrementing size
                    return value; // returning the element removed
                }
                cur = cur.next; // shifting cur node to next so as to progress with search
            }
        }
        return null;
    }
}