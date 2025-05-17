//This program tells if the triangle is right angled or not.
public class RightTriangle {

    public static void main(String[] args) {

        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int c = Integer.parseInt(args[2]);

        boolean isRightTriangle = ((a > 0) && (b > 0) && (c > 0) && (a + b > c) && (b + c > a) && (c + a > b) && (((a * a + b * b) == c * c) || ((b * b + c * c) == a * a) || ((a * a + c * c) == b * b)));

        System.out.println(isRightTriangle);

    }

}
