public class UniformRandomNumbers {
    public static void main(String[] args) {
        double x1 = Math.random();
        double x2 = Math.random();
        double x3 = Math.random();
        double x4 = Math.random();
        double x5 = Math.random();
        System.out.println(x1 + ", " + x2 + ", " + x3 + ", " + x4 + ", " + x5);
        double xavg = (x1 + x2 + x3 + x4 + x5) / 5;
        double xmin = Math.min(Math.min(Math.min(Math.min(x1, x2), x3), x4), x5);
        double xmax = Math.max(Math.max(Math.max(Math.max(x1, x2), x3), x4), x5);
        System.out.println("The maximum value (x_max) is " + xmax);
        System.out.println("The minimum value (x_min) is " + xmin);
        System.out.println("The average value (x_avg) is " + xavg);

    }
}
