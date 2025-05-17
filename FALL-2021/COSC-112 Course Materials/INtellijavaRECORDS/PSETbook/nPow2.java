public class nPow2 {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int i = 1; i <= n; i++) {
            System.out.println((long) Math.pow(2, i));
        }
    }
}
