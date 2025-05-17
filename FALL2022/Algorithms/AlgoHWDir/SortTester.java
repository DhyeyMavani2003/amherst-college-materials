import java.util.Arrays;

public class SortTester {
    
    public static void main(String[] args) {

	System.out.println("size, quickSort");

	for (int n = 100_000; n <= 1_000_000; n += 100_000){

	    System.out.println(n + ", " + runInsertionSortTest(n, 1));
	}

    }

    // Test the running times of insertion on random arrays of a givne
    // size. Several (numTests) arrays are generated and sorted, and
    // the average running time of Sorting.insertionSort is returned.
    public static long runInsertionSortTest(int size, int numTests) {
	long totalTime = 0;
	long start, stop;

	for (int i = 0; i < numTests; i++) {
	    int[] a = Sorting.init(size);
	    Sorting.shuffle(a);

	    start = System.nanoTime();
	    Sorting.quickSort(a);
	    stop = System.nanoTime();

	    if (!Sorting.isSorted(a))
		System.out.println("Failed sorting task!");

	    totalTime += (stop - start);
	}

	return totalTime / numTests;
    }    

}
