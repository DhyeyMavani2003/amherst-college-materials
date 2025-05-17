public class Randomab {
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        double d = Math.random();
        int c = a + (int) (d * (b - a));
        System.out.println(c);
    }
}
