import java.awt.*;

public class Banner {

    public static void main(String[] args) {
        String s = args[0];

        // set the font
        Font font = new Font("Arial", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.enableDoubleBuffering();


        for (double i = 0.0; true; i += 0.01) {
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.text((i % 1.0), 0.5, s);
            StdDraw.text((i % 1.0) - 1.0, 0.5, s);
            StdDraw.text((i % 1.0) + 1.0, 0.5, s);
            StdDraw.pause(60);
            StdDraw.show();
        }

    }

}
