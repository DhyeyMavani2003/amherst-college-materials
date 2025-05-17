public class CSVSortTester {
    
    public static void main(String[] args) {
        CSVWriter csv = new CSVWriter("sortingTimes.csv");
        
        /*
        for (int n = 5; n <= 100; n += 5){
            System.out.println(n + ", " + runInsertionSortTest(n, 100));
        }
        */
    
        csv.addEntry("n");
        csv.addEntry("limit");
        csv.addEntry("QuickSort Time (in ns)");
        csv.addEntry("MergeSort Time (in ns)");
        csv.addEntry("Modified MergeSort Time (in ns)");
        csv.addEntry("Modified QuickSort Time (in ns)");
        csv.addEntry("Time improvement b/w MergeSort and ModMergeSort (in ns)");
        csv.addEntry("Time improvement b/w QuickSort and ModQuickSort (in ns)");
        csv.endLine();
    
        for (int n = 1000000; n <= 1000000; n += 1000000) {
            for (int lim = 30; lim <= 150; lim += 1) {
                System.out.println(lim);
                csv.addEntry(n);
                csv.addEntry(lim);
                long quickSortTime = runQuickSortTest(n,10);
                long mergeSortTime = runMergeSortTest(n,10);
                long modMergeSortTime = runModMergeSortTest(n,10, lim);
                long modQuickSortTime = runModQuickSortTest(n,10, lim);
                csv.addEntry(quickSortTime);
                csv.addEntry(mergeSortTime);
                csv.addEntry(modMergeSortTime);
                csv.addEntry(modQuickSortTime);
                csv.addEntry(mergeSortTime - modMergeSortTime);
                csv.addEntry(quickSortTime - modQuickSortTime);
                csv.endLine();
            }
            
        }
        csv.close();
    
        }
    

        // Test the running times of insertion on random arrays of a givne
        // size. Several (numTests) arrays are generated and sorted, and
        // the average running time of Sorting.insertionSort is returned.
        /*
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
        */
        public static long runQuickSortTest(int size, int numTests) {
            long totalTime = 0;
            long start, stop;
        
            for (int i = 0; i < numTests; i++) {
                int[] a = Sorting.init(size);
                Sorting.shuffle(a);
        
                start = System.nanoTime();
                Sorting.quickSort(a);
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

        public static long runModMergeSortTest(int size, int numTests, int lim) {
            long totalTime = 0;
            long start, stop;
        
            for (int i = 0; i < numTests; i++) {
                int[] a = Sorting.init(size);
                Sorting.shuffle(a);
        
                start = System.nanoTime();
                Sorting.mergeSortMod(a, lim);
                stop = System.nanoTime();
        
                totalTime += (stop - start);
            }
        
            return totalTime / numTests;
        }

        public static long runModQuickSortTest(int size, int numTests, int lim) {
            long totalTime = 0;
            long start, stop;
        
            for (int i = 0; i < numTests; i++) {
                int[] a = Sorting.init(size);
                Sorting.shuffle(a);
        
                start = System.nanoTime();
                Sorting.modQuickSort(a, lim);
                stop = System.nanoTime();
        
                totalTime += (stop - start);
            }
        
            return totalTime / numTests;
        }
}
