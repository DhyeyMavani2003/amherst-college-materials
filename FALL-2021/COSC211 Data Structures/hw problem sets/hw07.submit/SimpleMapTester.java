public class SimpleMapTester {
    public static final int SIZE = 10;
    static SimpleMap<Integer, Integer> map = new HashSimpleMap<Integer, Integer>();

    public static void main(String[] args) {
	System.out.println("Testing put method...");
	if (testPut()) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}

	System.out.println("Testing get method...");
	if (testGet()) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}
	
	System.out.println("Testing contains method...");
	if (testContains()) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}

	System.out.println("Testing get remove...");
	if (testRemove()) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}
    }

    static boolean testPut() {
	System.out.print("   putting pairs ");
	
	for (int i = 0; i < SIZE; ++i) {
	    System.out.print("(" + i + ", 0) ");
	    
	    if (map.put(i, 0) != null) {
		System.out.println(" non-null value returned");
		return false;
	    }
	}

	System.out.print("\n   putting pairs ");
	
	for (int i = 0; i < SIZE; ++i) {
	    System.out.print("(" + i + ", " + i + ") ");
	    
	    if (map.put(i, i) != 0) {
		System.out.println(" incorrect value returned");
		return false;
	    }
	}
	
	System.out.println("");
	
	return true;
    }

    static boolean testGet() {
	System.out.print("   getting values ");
	
	for (int i = 0; i < SIZE; ++i) {
	    System.out.print(i + " ");
	    
	    if (map.get(i) != i) {
		System.out.println(" incorrect value returned");
		return false;
	    }
	}

	System.out.println("");
	
	return true;
    }

    static boolean testContains() {
	System.out.print("   testing containment of keys ");
	
	for (int i = 0; i < 2 * SIZE; ++i) {
	    System.out.print(i + " ");
	    
	    if (i < SIZE && !map.contains(i)) {
		System.out.println( " value not found, but should have been");
		return false;
	    }

	    if (i >= SIZE && map.contains(i)) {
		System.out.println( " value found, but should not have been");
		return false;		
	    }
	}

	System.out.println("");
	
	return true;
    }


    static boolean testRemove() {
	System.out.print("   removing ");
	
	for (int i = 0; i < 2 * SIZE; ++i) {
	    System.out.print(i + " ");

	    Integer val = map.remove(i);
	    
	    if (i < SIZE && !val.equals(i)) {
		System.out.println( " incorrect value removed\n");
		return false;
	    }

	    if (i >= SIZE && val != null) {
		System.out.println( " returned value, but key not present");
		return false;		
	    }

	    val = map.remove(i);

	    if (val != null) {
		System.out.println(" removed key still has associated value");
	    }
	}

	System.out.println("");
	
	return true;
    }

    
	
}
