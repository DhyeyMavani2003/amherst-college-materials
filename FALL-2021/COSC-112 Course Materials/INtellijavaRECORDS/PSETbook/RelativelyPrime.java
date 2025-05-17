public class RelativelyPrime {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                int x = row;
                int y = col;
                while (x % y != 0) {
                    if (x < y) {
                        //method for swapping x and y
                        int tmp = x;
                        x = y;
                        y = tmp;
                    }
                    x = x % y;
                }
                if (y == 1)
                    System.out.print("*");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
}
