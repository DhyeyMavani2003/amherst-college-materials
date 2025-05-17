public class ThreeSort {
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int c = Integer.parseInt(args[2]);
        int x3 = Math.max(Math.max(a, b), c);
        int x1 = Math.min(Math.min(a, b), c);
        int x2 = a + b + c - x1 - x3;

        System.out.println("The Ascending order is  x1, x2, x3: " + x1 + ", " + x2 + ", " + x3);

    }
}
