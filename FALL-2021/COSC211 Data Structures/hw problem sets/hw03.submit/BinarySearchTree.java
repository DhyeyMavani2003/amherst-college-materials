public class BinarySearchTree<E extends Comparable<E>> implements SimpleSSet<E> {
    
    public int size = 0; // initializing size

    public Node<E> root = null; // defining root

    // defining a node class including leftNode and rNode as left and right nodes respectively 
    public class Node<E> {
        E value;
        Node<E> leftNode = null;
        Node<E> rNode = null;
        
        public Node(E val) { this.value = val;}
    }

    // defining size method
    @SuppressWarnings("unchecked")
    public int size(){
        return this.size;
    }
    
    // defining isEmpty() method 
    @SuppressWarnings("unchecked")
    public boolean isEmpty(){
        return root == null;
    }

    // defining find method
    @SuppressWarnings("unchecked")
    public E find(E x){
        // we can't find anything from an empty set
        if (isEmpty()){
            return null;
        }
        
        // defining curNode and setting it to root
        Node curNode = root;

        // iterate over the tree until we reach end
        while (curNode != null){
            // defining y to temporarily store value
            E y = (E) curNode.value;
            // if y less than x, go right 
            if (y.compareTo(x) < 0){
                curNode = curNode.rNode;
            } 
            // if y greater than x, go left
            if (y.compareTo(x) > 0){
                curNode = curNode.leftNode;
            } 
            // if y equals x, we found it!!
            else if(y.compareTo(x)==0){
                return y;  
            } 
        }
        // cannot find the input x in the set
        return null;

    }

    // defining findMin() method
    @SuppressWarnings("unchecked")
    public E findMin(){
        // defining and initializing min to current value at root
        E min = (E) root.value;

        // iterating through the tree to get to the leftmost node
        while (root.leftNode != null){
            min = (E) root.leftNode.value; // updating min to the next left child
            root = root.leftNode; // updating the root to the left child
        }
        // found and return the global min
        return min;
    }

    // defining findSuccessorE() method
    @SuppressWarnings("unchecked")
    public E findSuccessorE(Node root){
        // defining and initializing min to current value at root
        E min = (E) root.value;
        // making root to be it's right child
        root = root.rNode;
        
        // iterating through the tree to get to the leftmost node
        while (root.leftNode != null){
            min = (E) root.leftNode.value; // updating min to the next left child
            root = root.leftNode; // updating the root to the left child
        }
        // making root null
        root = null;
        // found and return min
        return min;
    }

    // defining findMax() method
    @SuppressWarnings("unchecked")
    public E findMax(){
        // defining and initializing max to current value at root
        E max = (E) root.value;
        // iterating through the tree to get to the rightmost node
        while (root.rNode != null){
            max = root.rNode.value; // updating max to the next right child
            root = root.rNode; // updating the root to the right child
        }
        // found and return max
        return max;
    }

    // defining height() method
    @SuppressWarnings("unchecked")
    public int height(){
        // height of an empty BST is -1
        if (size == 0){
            return -1;
        }
        // return the heightRecursive function if BST has atleast one element
        return heightRecursive(root, -1);
        
    }

    // defining heightRecursive() method
    @SuppressWarnings("unchecked")
    public int heightRecursive(Node curNode, int depth){
        // reached to end via recursion and return depth
        if (curNode == null){
            return depth;
        }
        else{ // height is the max of height of two subtrees with childs as roots
            return Math.max(heightRecursive(curNode.leftNode, depth+1), heightRecursive(curNode.rNode, depth+1));
        }
    }

    // defining add method
    @SuppressWarnings("unchecked")
    public boolean add(E x){
        // if the BST is empty initially then make a sigular new node and increment the size
        if (isEmpty()){
            root = new Node(x);
            size++;
            return true;
        }

        Node curNode = root; // defining curNode and initializing it to root
        Node pNode = root; // defining pNode and initializing it to root

        // iterating through the tree until we reach the end of the BST
        while (curNode != null){
            // defining y to temporarily store value
            E y = (E) curNode.value;
            // initializing parent to current
            pNode = curNode;
            // if current value is less than x, go right
            if (y.compareTo(x) < 0){
                curNode = curNode.rNode;
            } 
            // if current value is more than x, go left
            if (y.compareTo(x) > 0){
                curNode = curNode.leftNode;
            } 
            // if current value equals x, return false 
            //because we don't need to add as the element is already there in ser
            else if(y.compareTo(x)==0){
                return false;  
            } 
        }
        // storing value of parent node to temporary variable a
        E a = (E) pNode.value;
        // if a is less than x, make a new node as right child of parent
        if (a.compareTo(x) < 0){
            pNode.rNode = new Node(x);
        }
        // if a is greater than x, make a new node as left child of parent
        if (a.compareTo(x) > 0){
            pNode.leftNode = new Node(x);
        }
        size++; // increment size as we added a new element 
        return true;

    }

    // defining remove method
    @SuppressWarnings("unchecked")
    public E remove(E x){
        // if we find the desired element
        if (find(x) != null){
            root = removeRecursive(root, x); // call removerecursive method to update the root
            size--; // decrement size
            return root.value; // return the removed value
        }
        return null; // not found the desired element, so cannot remove and return null
    }

    // defining removeRecursive method
    @SuppressWarnings("unchecked")
    public Node removeRecursive(Node root, E x){
        // if root is null then return root
        if (root == null)  return root; 
        // temporarily storing the value at root in variable y
        E y = (E) root.value;
        // if x is less than y, go left
        if (x.compareTo(y) < 0){
            root.leftNode = removeRecursive(root.leftNode, x); 
        }
        // if x is greater than y, go right
        else if (x.compareTo(y) > 0){
            root.rNode = removeRecursive(root.rNode, x); 
        }
        else{ 
            // if we don't have left child, return right child
            if (root.leftNode == null) 
                return root.rNode; 
            // if we don't have right child, return left child
            else if (root.rNode == null) 
                return root.leftNode; 
   
            // if node has two children 
            // get inorder successor (min value in the right subtree) 
            y = findSuccessorE(root);
            // delete the inorder successor
            root.value = y;
        }
        return root; 
    }
}