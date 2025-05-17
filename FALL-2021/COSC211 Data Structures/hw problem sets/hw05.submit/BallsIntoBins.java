// importing the required Array and Random from java
import java.lang.reflect.Array;
import java.util.Random;

// defining the class BallsIntoBins
public class BallsIntoBins {
        
    // setting a high default capacity
    public static final int DEFAULT_SIZE = Integer.MAX_VALUE;

    // defining the firstCollision method
    public static int firstCollision (int n) {
        // defining an initializing a new aray of ints called contents
        int[] contents = new int[n];

        // defining random
        Random rand = new Random();

        // looping n times
        for (int i = 0; i < n; i++) {
            
            // generating random integer r
            int r = rand.nextInt(n);

            // incrementing contents at index r to imitate throwing a ball in rth bin
            contents[r]++;

            // if we have a collision, meaning two in one bin, return the number of trial which is i
            if (contents[r] > 1) { 
                return i;
            }
        }
        return n;
    }

    // defining firstCollisionTimer method
    public static long firstCollisionTimer(int n) {
        // defining run timer rt
        RunTimer rt = new RunTimer();

        // recording and returning the time for firstCollision method
        rt.start();
        firstCollision(n);
        rt.stop();
        return rt.getElapsedNanos();
    }

    // defining firstCollisionTimerStats method
    public static long[] firstCollisionTimerStats(int n) {
        // initializing integers cur, min and max
        long total = 0;
        long min = Integer.MAX_VALUE;
        long max = 0;

        // looping through the number of samplings (let's say 50)
        for (int i = 0; i < 50; i++) {
            // storing the currently generated value from firstCollisionTimer in a temporary variable
            long temp = firstCollisionTimer(n);

            // adding up iteratively to keep track of total
            total += temp;

            // replacing mininum with current value when min is greater than current value
            if (min > temp) {
                min = temp;
            }

            // replacing maximum with current value when max is greater than current value
            if (max < temp) {
                max = temp;
            }
        }

        // defining average
        long avg = total/50;

        // defining an array to store and return statistics requested
        long[] values = new long[] {min, max, avg};
        return values;
    }

    // defining maxOccupancyStats method
    public static long[] maxOccupancyStats(int n) {
        // initializing integers cur, min and max
        long total = 0;
        long min = Integer.MAX_VALUE;
        long max = 0;

        // looping through the number of samplings (let's say 50)
        for (int i = 0; i < 50; i++) {
            // storing the currently generated value from firstCollisionTimer in a temporary variable
            long temp = maxOccupancy(n);

            // adding up iteratively to keep track of total
            total += temp;

            // replacing mininum with current value when min is greater than current value
            if (min > temp) {
                min = temp;
            }

            // replacing maximum with current value when max is greater than current value
            if (max < temp) {
                max = temp;
            }
        }

        // defining average
        long avg = total/50;

        // defining an array to store and return statistics requested
        long[] values = new long[] {min, max, avg};
        return values;
    }

    // definig maxOccupancy method
    public static int maxOccupancy (int n) {
        // defining and initializing an array of required size n called contents
        int[] contents = new int[n];
        
        // genreating two random numbers
        Random rand = new Random();

        // looping through
        for (int i = 0; i < n; i++) {
            // implementing the "Power of Two Choices methodology"
            
            int r1 = rand.nextInt(n);
            int r2 = rand.nextInt(n);

            // if they are equal, increment any of them
            // in case of inequality, increment the smaller one
            if (contents[r1] == contents[r2]) {
                contents[r1]++;
            }
            else if (contents[r1] > contents[r2]) {
                contents[r2]++;
            }
            else {
                contents[r1]++;
            }
        }

        // defining and initializing cur to contents[0]
        int cur = contents[0];

        // looping through to find the maximum and return the maximum
        for (int j = 0; j < n; j++) {
            if (cur < contents[j]) {
                cur = contents[j];
            }
        }
        return cur;
    }

    // defining maxOccupancyWithoutPowerOfTwo method
    public static int maxOccupancyWithoutPowerOfTwo (int n) {

        // defining and initializing an array of required size n called contents
        int[] contents = new int[n];
        
        // genreating a random number
        Random rand = new Random();

        // looping through
        for (int i = 0; i < n; i++) {
            // NOT implementing the "Power of Two Choices methodology"
            
            int r = rand.nextInt(n);

            // incrementing the contents at index r
            contents[r]++;
        }

        // defining and initializing cur to contents[0]
        int cur = contents[0];

        // looping through to find the maximum and return the maximum
        for (int j = 0; j < n; j++) {
            if (cur < contents[j]) {
                cur = contents[j];
            }
        }
        return cur;
    }

    // writing nonEmptyOccurance method
    public static int nonEmptyOccurance (int n) {
        // defining contents
        int[] contents = new int[n];

        Random rand = new Random();

        // defining count of number of zeros
        int zeroCount = n;

        // looping through
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            // "Power of Two values method" REUSED
            // generating two random numbers
            int r1 = rand.nextInt(n);
            int r2 = rand.nextInt(n);

            // if two values are same, 
            //      if value being incremented is zero before, decrement zeroCount 
            // and increment value at that index
            if (contents[r1] == contents[r2]) {
                if (contents[r1] == 0){
                    zeroCount -= 1;
                }
                contents[r1]++;
            } // increment the smaller value and if value being incremented is zero before, decrement zeroCount 
            else if (contents[r1] > contents[r2]) {
                if (contents[r2] == 0) {
                    zeroCount -= 1;
                }
                contents[r2]++;
            } 
            else {
                if (contents[r1] == 0){
                    zeroCount -= 1;
                }
                contents[r1]++;
            }

            // if we have no more zeros than return i
            if (zeroCount == 0) {
                return i;
            }

        }
        return DEFAULT_SIZE;
    }

    // MAIN METHOD
    public static void main(String[] args) {

        // Writing a csv and respective entries as headings
        CSVWriter csv = new CSVWriter("BallsStatistics.csv");
        csv.addEntry("n");
        csv.addEntry("Trial number at Occurance of first Collision");
        csv.addEntry("Time for first Collision (in ns)");
        csv.addEntry("Maximum Occupancy after n trials");
        csv.addEntry("Max Occupancy after n trials W/O power of two ");
        csv.addEntry("Trial number for First non-Empty Occurance");
        csv.addEntry("stats for first collision [min]");
        csv.addEntry("stats for first collision [max]");
        csv.addEntry("stats for first collision [avg]");
        csv.addEntry("stats for max occupancy [min]");
        csv.addEntry("stats for max occupancy [max]");
        csv.addEntry("stats for max occupancy [avg]");
        csv.endLine();

        // entering values in the csv for differnt values of n
        for (int i = 300; i < 400; i += 5) {
            csv.addEntry(i);
            csv.addEntry(firstCollision(i));
            csv.addEntry(firstCollisionTimer(i));
            csv.addEntry(maxOccupancy(i));
            csv.addEntry(maxOccupancyWithoutPowerOfTwo(i));
            csv.addEntry(nonEmptyOccurance(i));
            long[] tempFirstCollision = firstCollisionTimerStats(i);
            csv.addEntry(tempFirstCollision[0]);
            csv.addEntry(tempFirstCollision[1]);
            csv.addEntry(tempFirstCollision[2]);
            long[] tempMaxOccupancy = maxOccupancyStats(i);
            csv.addEntry(tempMaxOccupancy[0]);
            csv.addEntry(tempMaxOccupancy[1]);
            csv.addEntry(tempMaxOccupancy[2]);
            csv.endLine();
        }

        // closing the csv
        csv.close();
    }
}

