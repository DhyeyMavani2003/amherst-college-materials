public class MercatorProjection {
    public static void main(String[] args) {
        double lambda0 = Double.parseDouble(args[0]);
        double lambda = Double.parseDouble(args[1]);
        double phi = Double.parseDouble(args[2]);
        double x = lambda - lambda0;
        double y = 0.5 * Math.log((1 + Math.sin(phi)) / (1 - Math.cos(phi)));
        System.out.println("The mapping (x, y) is (" + x + ", " + y + ").");

    }
}
