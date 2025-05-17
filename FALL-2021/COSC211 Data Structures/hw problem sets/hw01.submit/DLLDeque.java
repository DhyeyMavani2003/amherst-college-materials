

public class DLLDeque<E> implements SimpleDeque<E> {

    private Node<E> head = null; 
    private Node<E> tail = null; 
    private int size = 0;


    @Override
    public int size(){
        return this.size;
    }

    @Override
    public boolean isEmpty(){ 
        return (head == null); //if head is null there is nothing in our deque 
    }

    @Override
    public void addFirst(E x) {
        
        Node<E> nd = new Node<E>(x);

        if (isEmpty()) {
            head = nd; //if the list is empty the head and the tail will be the same when we insert our first element
            tail = nd; 

        }

        else {
            head.prev = nd; //head will now be second in our list so its previous will reference the node we are adding
            nd.next = head; //our new node's next will point to our old head 
            head = nd; //set our node to be added as the head of the DL list
        }

        size++; //increment size

    }

    @Override
    public E removeFirst(){
        
        E toReturn = head.value; //store the head's current value to be returned

        if (isEmpty()) {
            throw new IndexOutOfBoundsException(); //cannot remove from an empty list
        }

        else if (size == 1){ //set new head to be what's currently second (if there is something) and set 
            head = null;
            tail = null;
        }

        else {
            head = head.next;
            head.prev = null;
        }

        size--; //decrement size since we are removing from our list

        return toReturn;

    }

    @Override
    public E peekFirst(){
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        else {
            return head.value;
        }
        
    }

    @Override
    public void addLast(E x) {
       
        Node<E> nd = new Node<E>(x); //create the node with our value to be added

        if (isEmpty()) { //if the list was previously empty both head and tail will be our new node
            head = nd;
            tail = nd;
        }

        else {
            tail.next = nd;
            nd.prev = tail;
            tail = nd;
        }

        size++;

    }

    @Override
    public E removeLast(){
         
        E toReturn = tail.value; //store the head's current value to be returned

        if (isEmpty()) {
            throw new IndexOutOfBoundsException(); //cannot remove from an empty list
        }

        else if (size == 1){ //if we have only 1 element in the list head and tail should be null after its removal 
            head = null;
            tail = null;
        }

        else {
            tail = tail.prev;
            tail.next = null;
        }

        size--; //decrement size since we are removing from our list

        return toReturn;

    }

    @Override
    public E peekLast(){
        if (isEmpty()) {
            throw new IndexOutOfBoundsException(); //cannot peek from an empty list
        }

        else {
            return tail.value;
        }

    }


    private class Node<E> {
        E value;
        Node<E> prev = null;
        Node<E> next = null;

        public Node (E val) {
            this.value = val;

        }
    }

}