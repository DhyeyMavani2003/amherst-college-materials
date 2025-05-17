public class ColorConversion {
    public static void main(String[] args) {
        int r = Integer.parseInt(args[0]);
        int g = Integer.parseInt(args[1]);
        int b = Integer.parseInt(args[2]);
        int w = Math.max(r / 255, Math.max(g / 255, b / 255));
        int c = (w - (r / 255)) / w;
        int m = (w - (g / 255)) / w;
        int y = (w - (b / 255)) / w;
        int k = 1 - w;
        System.out.println("C is " + c);
        System.out.println("M is " + m);
        System.out.println("Y is " + y);
        System.out.println("K is " + k);

    }
}
