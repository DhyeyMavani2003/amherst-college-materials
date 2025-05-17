public class FactorsModified {

    public static void main(String[] args) {

        // command-line argument
        long n = Long.parseLong(args[0]);

        System.out.print("The prime factors of " + n + " are: ");

        // for each potential factor
        for (long factor = 2; factor * factor <= n; factor++) {

            // if factor is a factor of n, repeatedly divide it out
            while (n % factor == 0) {
                System.out.print(factor + " ");
                long reminder;
                do {
                    reminder = n % factor;
                    if (reminder == 0) n = n / factor;
                } while (reminder == 0 && n > 1);
            }
        }

        // if biggest factor occurs only once, n > 1
        if (n > 1) System.out.println(n);
        else System.out.println();
    }
}

