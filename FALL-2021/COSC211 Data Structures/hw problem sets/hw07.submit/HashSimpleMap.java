import java.util.Random;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashSimpleMap<K, V> implements SimpleMap<K,  V>{
    public HashUSet<Pair<K,V>> hashUSet = new HashUSet<Pair<K,V>>();

    // defining put method
    @Override
    public V put(K key, V value) {
        // defining a new pair to add
        Pair<K,V> pair = new Pair<K,V>(key, value);
        
        // finding the pair in hashUSet
        Pair<K,V> p = hashUSet.find(pair);

        // if found the pair
        if (p != null) {
            // remove the initial pair
            hashUSet.remove(p);

            // add my new pair
            hashUSet.add(pair);

            // return the old value of the pair
            return p.getValue();
        }

        // just add the pair if not found the pair
        hashUSet.add(pair);
        
        return null;     
    }

    // defining the get method
    @Override
    public V get(K key) {
        // defining the new pair
        Pair<K,V> pair = new Pair<K,V>(key,null);

        // if we find the pair then return the value of that pair
        if (hashUSet.find(pair) != null) {
            return (hashUSet.find(pair)).getValue();
        }

        return null;
    }

    // defining the size method
    @Override
    public int size() {
        return hashUSet.size();
    }

    // defining the isEmpty() method
    @Override
    public boolean isEmpty() {
        return hashUSet.isEmpty();
    }

    // defining the contains method
    @Override
    public boolean contains(K key){
        // defining a new pair with given key
        Pair<K,V> pair = new Pair<K,V>(key,null);

        // if we find the pair return true
        if (hashUSet.find(pair) != null) {
            return true;
        }

        return false;
    }

    // defining the remove method
    @Override
    public V remove(K key){
        // defining a new pair with given key
        Pair<K,V> pair = new Pair<K,V>(key,null);

        // if we find the pair, delete it and return it's value
        if (hashUSet.find(pair) != null) {
            Pair<K,V> p = hashUSet.remove(pair);
            return p.getValue();
        }

        return null;
    }

    // defining the pair class
    protected class Pair<K, V> {
        final K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        // defining the equals method to override the default method
        @Override
        public boolean equals(Object obj) {
            // if the object is instance of pair 
            // then check if they have equal keys
            if (obj instanceof Pair) {
                Pair<K,V> p = (Pair<K,V>) obj;
                return key.equals(p.getKey());
            }
            return false;
        }

        // overriding the default method as we are finding hashCode for keys 
        // in the case of the pair class
        @Override
        public int hashCode() {
            return ((key == null) ? 0 : key.hashCode());
        }
    }
}



















/*import java.util.Random;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashSimpleMap<K, V> implements SimpleMap<K, V> {

    public static final int DEFAULT_LOG_CAPACITY = 40;
    protected int logCapacity = DEFAULT_LOG_CAPACITY; // value d from lecture
    protected int capacity = 1 << logCapacity;        // n = 2^d
    protected int size = 0;
    protected Object[] hashMap;                         // array of heads of linked lists

    // final = can't be changed after initial assignment!
    protected final int z;

    public HashSimpleMap() {
	    // set capacity to 2^logCapacity
	    int capacity = 1 << logCapacity;
	
	    hashMap = new Object[capacity];

	    // add a sentinel node to each list in the table
	    for (int i = 0; i < capacity; ++i) {
	        hashMap[i] = new Pair<K, V>(null,null);
	    }

	    // fix a random odd integer
	    Random r = new Random();	
	    z = (r.nextInt() << 1) + 1;
    }

    protected class Pair <K, V> {
        protected K key;
        protected V value;
        
        public Pair(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    LinkedSimpleList<Pair<K,V>>[] hashMap = new LinkedSimpleList[2]; //Array of LinkedLists to store Key-Value Pairs.
    int size = 0; //At the beginning, the size is 0

    public HashSimpleMap(){
        Random r = new Random();	
        z = (r.nextInt() << 1) + 1;
    }

    public void resize(){
        LinkedSimpleList< Pair< K, V > >[] oldHashMap = hashMap; //We need to save the existing data to re-insert later
        hashMap = new LinkedSimpleList[size * 2]; //Resizing the array
        size = 0; //Resetting size because put() adds to size already

        for (int i = 0; i < oldHashMap.length; i++) { //Looping through existing keys
            if(oldHashMap[i] == null) continue;
            for(Pair< K, V > pair : oldHashMap[i]){
                put(pair.key, pair.value); //Using the build put() method to re-insert into the bigger array
            }
        }
    }

    public int getIndex(K key){
        return ((z * key.hashCode()) >>> 32 - logCapacity);
    }

    public int size(){
        return size; //Returns the size variable, bascially the size of the list
    }

    public void put(K key, V value){ //K and V because it's the user's specified type
        if(size >= hashMap.length){
            resize();
        }

        int index = getIndex(key); //Getting the index to insert the pair into the array

        if(hashMap[index] == null){ //If the spot is null, that means there's no existing keys at that cell
            hashMap[index] = new LinkedSimpleList<>(); //Create the LinkedList
            hashMap[index].add(new Pair(key, value)); //Add the entry to the LinkedList at that cell
            size++; //Increment the size
            return;
        }
        else{ //Maybe a hash collision or a same key so we need to replace the existing value
            for(Pair< K, V > pair : hashMap[index]){ //Loop through entries to see if there is a existing key that is the same
                if((pair.key).equals(key)){ //Replaces the value corresponding to that key
                    pair.value = value;
                    return;
                }
            }
        //Does not replace, simply adds it to the end
            hashMap[index].add(new Pair<>(key, value));
            size++;
            return;
        }
    }

    public V get(K key){
        int index = getIndex(key); //Gets the index of where the key could POTENTIALLY be

        if(hashMap[index] == null) return null; //If that position doesn't have anything, then the key isn't in the hashmap

        for(Pair< K, V > pair : hashMap[index]){ //Looping through keys at that index
            if((pair.key).equals(key)){ //If that is the key, then return its value
                return pair.value;
            }
        }
        //Key is not in the hashmap, return null
        return null;
    }

    //Below is the containsKey() method. This is basically the same idea, but instead of returning the value, we will return a boolean.
    //I won't add comments because I think it's pretty self-explanatory
    public boolean contains(K key){
        if(key == null) return false;

        int index = getIndex(key);

        if(hashMap[index] == null){
            return false;
        }

        for(Pair< K, V > pair : hashMap[index]){
            if((pair.key).equals(key)){
                return true;
            }
        }
        return false;
    }

    public V remove(K key){

        //For this first part, you can just call the containsKey() method written earlier, but I decided to do this just for clarity
        if(key == null) return null; //If key is null, no need to delete a null key

        int index = getIndex(key); //Getting the index of where that Key could POTENTIALLY be

        if(hashMap[index] == null) return null; //If that position doesn't contain the key, then the key is not in the hashmap



        Pair< K, V > toRemove = null; //Creating the Entry that will hold the Entry to remove

        for (Pair< K, V > pair : hashMap[index]){ //Looping through the Entries at that index
            if((pair.key).equals(key)){ //If it is the key, then set toRemove to that Entry and break because all keys are unique
                toRemove = pair;
                break;
            }
        }

        if(toRemove == null) return null; //If toRemove is null, that means we didn't find the key so it's not in the hashmap

        hashMap[index].remove(toRemove); //Removing that entry from the LinkedList at that index
        size--; //Subtracts size
        return toRemove.value;
    }
}*/


////////////////////////////////////////////////////////////////////////////////////////////
/*public class HashSimpleMap<K, V> implements SimpleMap<K, V>{
    public static final int DEFAULT_LOG_CAPACITY = 40;
    
    protected int logCapacity = DEFAULT_LOG_CAPACITY; // value d from lecture
    protected int capacity = 1 << logCapacity;        // n = 2^d
    protected int size = 0;
    protected Object[] map;                         // array of heads of linked lists

    // final = can't be changed after initial assignment!
    protected final int z;

    public HashSimpleMap() {
	    // set capacity to 2^logCapacity
	    int capacity = 1 << logCapacity;
	
	    map = new Object[capacity];

	    // add a sentinel node to each list in the table
	    for (int i = 0; i < capacity; ++i) {
	        map[i] = new Pair<K, V>(null,null);
	    }

	    // fix a random odd integer
	    Random r = new Random();	
	    z = (r.nextInt() << 1) + 1;
    }

    protected class Pair <K, V> {
        protected K key;
        protected V value;
        
        public Pair(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    protected int getIndex(K key) {
	    // get the first logCapacity bits of z * x.hashCode()
	    return ((z * key.hashCode()) >>> 32 - logCapacity);
    }

    @Override
    public int size() {
	    return this.size;
    }

    @Override
    public boolean isEmpty() {
	    return this.size == 0;
    }

    @Override
    public V get(K key) {

        int index = getIndex(key);

        if (map[index] == null) {
            return null;
        }

        for(Pair< K, V > pair : map[index]){ //Looping through keys at that index
            if((pair.key).equals(key)){ //If that is the key, then return its value
                return pair.value;
            }
        }
        //Key is not in the hashmap, return null
        return null;
    }

    @Override
    public V put(K key, V value) {

    }

    @Override
    public V remove(K key) {

    }

    @Override
    public boolean contains(K key) {

    }
}*/