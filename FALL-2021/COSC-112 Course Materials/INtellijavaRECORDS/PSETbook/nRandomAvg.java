public class nRandomAvg {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        double sum = 0;
        for (int i = 0; i < n; i++) {
            double x = Math.random();
            System.out.println(x);
            sum += x;
        }
        System.out.println("Average is " + (sum / (double) n));
    }
}
