public class Kary {
    public static void main(String[] args) {
        // i is the number entered by user in base 10.
        long i = Long.parseLong(args[0]);
        // k is the baase number to which i should be converted by the program herein.
        int k = Integer.parseInt(args[1]);
        // Now max_pow_k is the largest power of k <= i.
        int max_pow_k = 1;

        while (max_pow_k <= i / k)
            max_pow_k = k * max_pow_k;

        long n = i;

        while (max_pow_k > 0) {
            // Cast out powers of k in decreasing order.
            if (n < max_pow_k) {
                System.out.print("0");
            } else {
                int digit = (int) (n / max_pow_k);
                if (digit > 9) {
                    char char_cum_Digit = (char) ('a' + (digit - 10));
                    System.out.print(char_cum_Digit);
                } else
                    System.out.print(digit);
                n -= max_pow_k * digit;
            }

            max_pow_k = max_pow_k / k;
        }

        System.out.println();
    }
}
