import java.util.Arrays;
import java.util.Random;

public class SimpleUSetTester {

    private static int MAX = 20;
    private static Random rand = new Random(0);

    public static void main(String[] args) {
	
	// replace with your SimpleUSet implementation
	//SimpleUSet<Integer> set = new ArraySimpleUSet<Integer>();
	SimpleUSet<Integer> set = new HashUSet<Integer>();
	
	
	System.out.println("Constructing array of elements...");

	boolean[] inSet = getInSet();
	int[] elts = getElementArray(inSet);
	int[] order = getElementOrder();

	System.out.println("    Elements = " + Arrays.toString(elts));

	System.out.println("Testing add method...");
	if (testAdd(set, inSet, order)) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}

	System.out.println("Testing remove method...");
	if (testRemove(set, inSet, order)) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}
	
	System.out.println("Testing find method...");
	if (testFind(set, inSet, order)) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}
    }

    private static boolean[] getInSet () {
	boolean[] inSet = new boolean[MAX];
	for (int i = 0; i < inSet.length; ++i) {
	    inSet[i] = (rand.nextInt(2) == 0);
	}

	return inSet;
    }

    private static int[] getElementArray (boolean[] inSet) {
	int size = 0;
	for (int i = 0; i < inSet.length; ++i) {
	    if (inSet[i]) {
		++size;
	    }
	}

	int[] elts = new int[size];

	int curIndex = 0;

	for (int i = 0; i < inSet.length; ++i) {
	    if (inSet[i]) {
		elts[curIndex] = i;
		++curIndex;
	    }
	}

	return elts;
    }

    private static int[] getElementOrder() {
	int[] order = new int[MAX];
	
	for(int i = 0; i < MAX; ++i) {
	    order[i] = i;
	}

	for (int i = 1; i < MAX; ++i) {
	    int j = rand.nextInt(i+1);
	    int val = order[j];
	    order[j] = order[i];
	    order[i] = val;
	}

	return order;
	     
    }

    private static boolean testAdd (SimpleUSet<Integer> set, boolean[] inSet, int[] order) {
	int size = 0;

	// try adding elements to the set
	System.out.print("   adding ");
	for (int i : order) {
	    if (inSet[i]) {
		
		System.out.print(i + " ");
		
		if (!set.add(i)) {
		    System.out.println("\n   add(" + i + ") failed.");
		    return false;
		}
		
		++size;
	    }
	}

	if (set.size() != size) {
	    System.out.println("\n   size() method returned incorrect value");
	    return false;
	}

	for (int i = 0; i < inSet.length; ++i) {
	    System.out.print(i + " ");
	    
	    boolean added = set.add(i);
	    if (added && inSet[i]) {
		System.out.println("\n   added " + i + " which was already in set");
		return false;
	    }

	    if (!added && !inSet[i]) {
		System.out.println("\n   failed to add " + i + " (not previously in set)");
		return false;
	    }
	}

	System.out.println("");
	
	return true;
    }

    private static boolean testRemove (SimpleUSet<Integer> set, boolean[] inSet, int[] order) {
	int size = inSet.length;

	System.out.print("   removing ");
	
	for (int i : order) {
	    if (!inSet[i]) {

		System.out.print(i + " ");
		
		if (set.remove(i) == null) {
		    System.out.println("\n   failed to remove " + i);
		    return false;
		}
		--size;
	    }
	}

	if (size != set.size()) {
	    System.out.println("\n   size() method fails after removal");
	    return false;
	}

	System.out.println("");
	
	return true;
    }

    private static boolean testFind (SimpleUSet<Integer> set, boolean[] inSet, int[] order) {
	System.out.print("   finding ");
	for (int i : order) {
	    System.out.print(i + " ");
	    
	    Integer elt = set.find(i);

	    if (inSet[i] && (elt == null || !elt.equals(i))) {
		System.out.println("\n   failed to find " + i);
		return false;
	    }

	    if (!inSet[i] && (elt != null && elt.equals(i))) {
		System.out.println("\n   found " + i + " but should not have");
		return false;
	    }
	}

	System.out.println("");
	
	return true;
    }
    
}
