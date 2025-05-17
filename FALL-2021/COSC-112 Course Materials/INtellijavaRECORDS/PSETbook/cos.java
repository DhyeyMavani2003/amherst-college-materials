public class cos {

    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);

        // convert x to an angle between -2 PI and 2 PI
        x = x % (2 * Math.PI);

        // compute the Taylor series approximation
        double term = 1.0;      // ith term = x^i / i!
        double sum = 0.0; // sum of first i terms in taylor series
        for (int n = 1; sum != sum + term; n++) {
            sum += term;
            term *= x * x / ((2 * n - 1) * 2 * n);
            term = -1.0 * term;
        }
        System.out.println(sum);
        System.out.println(Math.cos(x));
    }
