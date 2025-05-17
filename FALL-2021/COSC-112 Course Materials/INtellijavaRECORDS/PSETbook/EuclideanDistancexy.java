public class EuclideanDistancexy {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        double a = Math.sqrt(x * x + y * y);
        System.out.println("Euclidean Distance between (x,y) and (0,0) is " + a);
    }
}
