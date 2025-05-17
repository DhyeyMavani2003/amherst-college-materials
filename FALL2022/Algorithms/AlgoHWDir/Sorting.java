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

    public static void insertionSort(int[] a, int i, int j) {
	for (int x = i+1; x < j + 1; ++x) {
	    int y = x;
	    while (y > i && a[y] < a[y-1]) {
		swap(a, y-1, y);
		y--;
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

	int j = (i + k) / 2;

	mergeSort(a, i, j);
	mergeSort(a, j, k);
	merge(a, i, j, k);
    }
	
	// implementation of the merge sort algorithm (calls recursive
    // helper method)
    public static void mergeSortMod(int[] a, int limit) {
	int lim = limit;
	mergeSortMod(a, 0, a.length, lim);
	}

	// use merge sort to recursively sort the values of a between
	// indices i (inclusive) and k (exclusive)
	private static void mergeSortMod(int[] a, int i, int k, int lim) {
	if (k - i <= lim) {
		insertionSort(a, i, k-1);
		return;
	}

	int j = (i + k) / 2;

	mergeSortMod(a, i, j, lim);
	mergeSortMod(a, j, k, lim);
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

    public static void quickSort(int[] a) {
	quickSort(a, 0, a.length-1, new Random());
    }

	public static void modQuickSort(int[] a, int limit) {
		int lim = limit;
		modQuickSort(a, 0, a.length - 1, new Random(), lim);
	}

	private static void modQuickSort(int[] a, int i, int j, Random r, int limit) {
	// System.out.println("Sorting(" + i + ", " + j + ")");
	
	
	if (j - i <= limit) {
		insertionSort(a, i, j);
		return;
	}

	int p = i + r.nextInt(j-i);
	int m = split(a, i, j, p);
	modQuickSort(a, i, m-1, r, limit);
	modQuickSort(a, m+1, j, r, limit);	
	}

    private static void quickSort(int[] a, int i, int j, Random r) {
	// System.out.println("Sorting(" + i + ", " + j + ")");
	
	if (j <= i) {
	    return;
	}

	int p = i + r.nextInt(j-i);
	int m = split(a, i, j, p);
	quickSort(a, i, m-1, r);
      	quickSort(a, m+1, j, r);	
    }

    // Split the array between indices i and j according to the pivot
    // index p. After this operation, the index m is returned such
    // that all values at indices i..m-1 are <= pivot, a[m] = pivot,
    // and all values at indices m+1..j are > pivot
    public static int split(int[] a, int i, int j, int pIndex) {

	int pivot = a[pIndex];

	// move pivot to right-most index
	swap(a, pIndex, j);

	// the largest index of values <= pivot
	int small = i - 1;

	// values between small and cur are > pivot
	for (int cur = i; cur <= j; cur++) {
	    if (a[cur] <= pivot) {
		small++;
		swap(a,small,cur);
	    }
	}

	// return the index of the pivot
	return small;
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

    public static boolean isSorted(int[] a) {
	for (int i = 0; i < a.length - 1; i++) {
	    if (a[i] > a[i+1])
		return false;
	}

	return true;
    }
}
