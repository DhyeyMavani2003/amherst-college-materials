import java.util.Random;

/**
 * Sorting.java provides implementations of different sorting
 * algorithms for sorting arrays of ints, as well as some utilities
 * for creating/shuffling arrays.
 *
 * All sorting methods are destructive in the sense that the modify the original array. Also, all methods are static. Example usage:
 *
 *  <code>
 *  int[] a = Sorting.init(100); // make an array w/ values 0, 1,...,99
 *  Sorting.shuffle(a);          // randomly shuffle the values of a
 *  Sorting.insertionSort(a);    // sort a using insertion sort
 *  </code>
 *
 * @author Will Rosenbaum
 */

public class Sorting {

    // swap two elements in an array
    private static void swap(int[] a, int i, int j) {
	int x = a[j];
	a[j] = a[i];
	a[i] = x;
    }

    // implementation of the insertion sort algorithm
    public static void insertionSort(int[] a) {
	for (int i = 1; i < a.length; ++i) {
	    int j = i;
	    while (j > 0 && a[j] < a[j-1]) {
		swap(a, j-1, j);
		j--;
	    }
	}
    }

    // implementation of the selection sort algorithm
    public static void selectionSort(int[] a) {
	for (int i = 0; i < a.length-1; ++i) {
	    int min = i;
	    for (int j = i+1; j < a.length; ++j) {
		if (a[j] < a[min]) {
		    min = j;
		}
	    }
	    swap(a, i, min);
	}
    }

    // implementation of the bubble sort algorithm
    public static void bubbleSort(int[] a) {
	for (int i = 0; i < a.length - 1; ++i) {
	    for (int j = 0; j < a.length - 1 - i; ++j) {
		if (a[j] > a[j+1]) {
		    swap(a, j, j+1);
		}
	    }
	}
    }

    // implementation of the merge sort algorithm (calls recursive
    // helper method)
    public static void mergeSort(int[] a) {
	mergeSort(a, 0, a.length);
    }

    // use merge sort to recursively sort the values of a between
    // indices i (inclusive) and k (exclusive)
    private static void mergeSort(int[] a, int i, int k) {
	if (k - i == 1) {
	    return;
	}

	int j = i + (k - i) / 2;

	mergeSort(a, i, j);
	mergeSort(a, j, k);
	merge(a, i, j, k);
    }

    // merge the two sub-arrays of a between indices i and j and
    // indices j and k
    // if indices i to j and j to k were previously
    // sorted, after this operation indices i through k will be sorted
    private static void merge(int[] a, int i, int j, int k) {
	int[] merged = new int[k - i];
	
	int ind = 0;
	int left = i;
	int right = j;

	while (left < j && right < k) {
	    if (a[left] <= a[right]) {
		merged[ind] = a[left];
		left++;
	    } else {
		merged[ind] = a[right];
		right++;
	    }
	    ind++;
	}

	while (left < j) {
	    merged[ind] = a[left];
	    left++;
	    ind++;	    
	}

	while (right < k) {
	    merged[ind] = a[right];
	    right++;
	    ind++;
	}

	for (ind = i; ind < k; ind++) {
	    a[ind] = merged[ind - i];
	}
    }

    // shuffle the values of an array a
    public static void shuffle(int[] a) {
	Random r = new Random();

	for (int i = 1; i < a.length; ++i) {
	    int j = r.nextInt(i+1);
	    swap(a, j, i);
	}
    }

    // make a copy of array a
    public static int[] copyOf(int[] a) {
	int[] b = new int[a.length];
	for (int i = 0; i < a.length; ++i) {
	    b[i] = a[i];
	}

	return b;
    }

    // create an array of size n storing values 0, 1,...,n-1
    public static int[] init(int n) {
	int[] a = new int[n];
	for (int i = 0; i < n; ++i) {
	    a[i] = i;
	}

	return a;
    }
}
