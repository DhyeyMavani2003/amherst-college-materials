public class kthroot {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        double EPSILON = 1e-15;
        double t = (double) n;
        double c = (double) n;
        while (Math.abs(t - c / Math.pow(t, (k - 1))) > EPSILON * t) {
            t = (t - t / k + c / (k * Math.pow(t, (k - 1))));
        }
        System.out.println(t);
    }
}

//Create an program thaat can estimate the value by the Newtons method.
