//1.3.21 good one is modified version of Binary
public class Kary {
    public static void main(String[] args) {
        //here i is the number in decimal form
        long i = Long.parseLong(args[0]);
        //here k is the number of base system
        int k = Integer.parseInt(args[1]);

        int v = 1;

        while (v <= i / k) {
            v = k * v;
        }
        // Cast out powers of k in decreasing order.
        if (n < v) {
            System.out.print("0");
        } else {
            int digit = (int) (n / v);
            if (digit > 9) {
                char chardigit = (char) ('a' + (digit - 10));
                System.out.print(charDigit);
            } else
                System.out.print(digit);
            n -= v * digit;
        }
        v = v / k;
    }
    System.out.println();
}
        /*if ((k >= 2) && (k <= 16)) {
            int power = 1;
            while (power <= i / k) {
                power *= k;
            }
            if (k < 10) {
                for (int j = 1; j <= 10; j++) {
                    while (power > j) {
                        if (i < power) {
                            System.out.print(j - 1);
                        } else {
                            i -= power;
                        }
                        power /= k;
                    }
                }
            } else if (k == 11) {
                System.out.print("Thinkinng in progress");
            }
            System.out.println();*/
     else System.out.println("Error, base numbering out of bounds.");
             }
             }


