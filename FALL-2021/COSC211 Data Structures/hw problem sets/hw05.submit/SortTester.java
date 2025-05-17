import java.util.Arrays;

public class SortTester {
    
    public static void main(String[] args) {
	CSVWriter csv = new CSVWriter("largeSortingTimes.csv");
	
	/*
	for (int n = 5; n <= 100; n += 5){
	    System.out.println(n + ", " + runInsertionSortTest(n, 100));
	}
	*/

	csv.addEntry("n");
	csv.addEntry("InsertionSort Time (in ns)");
	csv.addEntry("SelectionSort Time (in ns)");
	csv.addEntry("BubbleSort Time (in ns)");
	csv.addEntry("MergeSort Time (in ns)");

	for (int n = 5000; n <= 100000; n += 5000) {
		System.out.println(n);
		csv.addEntry(n);
		csv.addEntry(runInsertionSortTest(n, 10));
		csv.addEntry(runSelectionSortTest(n, 10));
		csv.addEntry(runBubbleSortTest(n, 10));
		csv.addEntry(runMergeSortTest(n, 10));
		csv.endLine();
	}
	csv.close();

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
	    Sorting.insertionSort(a);
	    stop = System.nanoTime();

	    totalTime += (stop - start);
	}

	return totalTime / numTests;
    }
	
	public static long runSelectionSortTest(int size, int numTests) {
		long totalTime = 0;
		long start, stop;
	
		for (int i = 0; i < numTests; i++) {
			int[] a = Sorting.init(size);
			Sorting.shuffle(a);
	
			start = System.nanoTime();
			Sorting.selectionSort(a);
			stop = System.nanoTime();
	
			totalTime += (stop - start);
		}
	
		return totalTime / numTests;
	}

	public static long runBubbleSortTest(int size, int numTests) {
		long totalTime = 0;
		long start, stop;
	
		for (int i = 0; i < numTests; i++) {
			int[] a = Sorting.init(size);
			Sorting.shuffle(a);
	
			start = System.nanoTime();
			Sorting.bubbleSort(a);
			stop = System.nanoTime();
	
			totalTime += (stop - start);
		}
	
		return totalTime / numTests;
	}

	public static long runMergeSortTest(int size, int numTests) {
		long totalTime = 0;
		long start, stop;
	
		for (int i = 0; i < numTests; i++) {
			int[] a = Sorting.init(size);
			Sorting.shuffle(a);
	
			start = System.nanoTime();
			Sorting.mergeSort(a);
			stop = System.nanoTime();
	
			totalTime += (stop - start);
		}
	
		return totalTime / numTests;
	}

}
