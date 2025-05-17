public class RandomWalkers {
    public static void main(String[] args) {
        double[] myArray = new double[Integer.parseInt(args[1])];
        for (int j = 0; j < Integer.parseInt(args[1]); j++) {
            int x = 0;
            int y = 0;
            for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                double rand = Math.random();
                if (rand < 0.25) x += 1;
                else if (rand < 0.5) x -= 1;
                else if (rand < 0.75) y += 1;
                else y -= 1;
            }
            myArray[j] = Math.pow(x, 2) + Math.pow(y, 2);
        }
        double total = 0;
        for (int t = 0; t < myArray.length; t++) {
            total += myArray[t];
        }
        double meanValue = total / (double) myArray.length;
        System.out.println("mean squared distance = " + meanValue);
    }
}
