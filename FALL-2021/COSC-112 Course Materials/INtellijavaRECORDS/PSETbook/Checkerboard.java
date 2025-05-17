public class Checkerboard {

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        for (int col = 0; col < n; col++) {
            for (int row = 0; row < n; row++) {
                if ((row + col) % 2 == 0) {
                    System.out.print("*");
                } else {
                    System.out.print("0");
                }
            }
            System.out.println();
        }
    }
}
