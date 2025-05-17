/******************************************************************************
 *  Compilation:  javac Euler.java
 *  Execution:    java Euler n
 *
 * 1.3.46 Euler’s sum-of-powers conjecture. In 1769 Leonhard Euler formulated a generalized version of
 * Fermat’s Last Theorem, conjecturing that at least n nth powers are needed to obtain a sum that is itself
 * an nth power, for n > 2. Write a program to disprove Euler’s conjecture (which stood until 1967), using
 * a quintuply nested loop to find four positive integers whose 5th power sums to the 5th power of another
 * positive integer. That is, find a, b, c, d, and e such that a5 + b5 + c5 + d5 = e5. Use the long data type.
 *
 *  Tests whether there are any five positive integers that satisfy
 *  a^5 + b^5 + c^5 + d^5 = e^5. In 1769 Euler conjectured that no such
 *  solutions exists, but his conjecture was disproved in 1966 using
 *  a method like the one below.
 *
 *  The program reads in an integer command-line argument n and prints
 *  all solutions with a <= b <= c <= d <= e <= n. Restricting attention
 *  to solutions of this form only eliminates duplicates and makes
 *  the program faster since we have many fewer possibilities to try
 *  (675,993,780 vs. 75,937,500,000).
 *
 *  For further efficiency, we use the break statement to avoid explicitly
 *  enumerating certain tuples (a, b, c, d, e), e.g., if a^5 + b^5
 *  is already greater than e^5, there is no need to fix specific
 *  values of c and d and compute a^5 + b^5 + c^5 + d^5.
 *
 *  % java Euler 100
 *
 *  % java Euler 150
 *   27^5 + 84^5 + 110^5 + 133^5 = 144^5
 *
 *  % java Euler 500
 *  27^5 + 84^5 + 110^5 + 133^5 = 144^5
 *  54^5 + 168^5 + 220^5 + 266^5 = 288^5
 *  81^5 + 252^5 + 330^5 + 399^5 = 432^5
 *
 *
 ******************************************************************************/
// Exercise 1.3.44

/*public class Euler {
    public static void main(String[] args) {
        long max = 150;

        for (long a = 1; a < max; a++) {
            for (long b = a; b < max; b++) {
                for (long c = b; c < max; c++) {
                    for (long d = c; d < max; d++) {
                        for (long e = 1; e < max; e++) {
                            if (e * e * e * e * e == a * a * a * a * a + b * b * b * b * b + c * c * c * c * c + d * d * d * d * d) {
                                System.out.println("Proven! a, b, c, d: " + a + ", " + b + ", " + c + ", " + d + " and e: " + e);
                            }
                        }
                    }
                }
            }
            System.out.println(a); // proven for: a 27 b 84 c 110 d 133 and e 144
        }
    }
}*/

public class Euler {

    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);
        long a5, b5, c5, d5, e5;

        for (long e = 1; e <= n; e++) {
            e5 = e * e * e * e * e;

            // restrict search to a <= b <= c <= d <= e for efficiency
            for (long a = 1; a <= n; a++) {
                a5 = a * a * a * a * a;
                if (a5 + a5 + a5 + a5 > e5) break;

                for (long b = a; b <= n; b++) {
                    b5 = b * b * b * b * b;
                    if (a5 + b5 + b5 + b5 > e5) break;

                    for (long c = b; c <= n; c++) {
                        c5 = c * c * c * c * c;
                        if (a5 + b5 + c5 + c5 > e5) break;

                        for (long d = c; d <= n; d++) {
                            d5 = d * d * d * d * d;
                            if (a5 + b5 + c5 + d5 > e5) break;
                            if (a5 + b5 + c5 + d5 == e5)
                                System.out.println(a + "^5 + " + b + "^5 + " + c + "^5 + " + d + "^5 = " + e + "^5");
                        }
                    }
                }
            }
        }
    }
}


//Copyright © //2000–2017, Robert Sedgewick and Kevin Wayne.
//Last updated: Tue Apr 30 18:11:46 EDT 2019.
