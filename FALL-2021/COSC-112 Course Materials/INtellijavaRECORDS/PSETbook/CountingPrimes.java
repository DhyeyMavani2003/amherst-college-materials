/*    1.3.36 Counting primes. Write a program PrimeCounter that takes an integer command-line argument n
and finds the number of primes less than or equal to n. Use it to print out the number of primes less
than or equal to 10 million. Note: If you are not careful, your program may not finish in a reasonable
amount of time! */
public class CountingPrimes {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int counter = 0;
        for (int i = 2; i <= N; i++) {
            boolean isPrime = true;
            for (int j = 2; j <= i / j; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime)
                counter++;
        }
        System.out.println(counter);
    }
}
