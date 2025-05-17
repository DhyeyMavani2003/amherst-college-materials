/*public class Integers1kto2k {
    public static void main(String[] args) {
        int i;
        for (i = 1000; i <= 2000; i++) {
            System.out.print(i + " ");
            int r5 = (i + 1) % 5;
            if (r5 == 0) {
                System.out.println();
            }
            System.out.println();
        }

    }
} */

public class FivePerLine {

    public static void main(String[] args) {

        // print integers from 1000 to 2000, 5 per line
        int START = 1000;
        int END = 2000;
        int PER_LINE = 5;
        for (int i = START; i <= END; i++) {
            System.out.print(i + " ");
            if ((i + 1) % PER_LINE == 0) System.out.println();
        }
        System.out.println();
    }
}
