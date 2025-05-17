public class GeneralisedQuadratic {
    public static void main(String[] args) {
        double a = Double.parseDouble(args[0]);
        double b = Double.parseDouble(args[1]);
        double c = Double.parseDouble(args[2]);
        double discriminant = (b * b - 4.0 * a * c);
        if (a == 0.0) {
            if (b == 0.0) {
                if (c == 0.0) {
                    System.out.println("All x are solutions.");
                } else {
                    System.out.println("No solution.");
                }
            } else {
                System.out.println("This is a linear equation. x is " + (-c / b));
            }
        } else {
            if (discriminant < 0.0)
                System.out.println(" No real roots possible. As discriminant is negative.");
            else if (discriminant == 0.0)
                System.out.println("coincident roots. x is " + (-b / (2.0 * a)));
            else
                System.out.println("Two real roots possible.");
            System.out.println(" First root is " + ((-b + Math.sqrt(discriminant)) / (2.0 * a)));
            System.out.println(" Second root is " + ((-b - Math.sqrt(discriminant)) / (2.0 * a)));
        }
    }
}
