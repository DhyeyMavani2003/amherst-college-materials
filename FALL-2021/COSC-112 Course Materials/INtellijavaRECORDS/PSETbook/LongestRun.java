public class LongestRun {
    public static void main(String[] args) {
        if (StdIn.isEmpty()) {
            StdOut.println("no longest consecutive run");
            return;
        }
        int prev = StdIn.readInt();
        int count = 1;
        int best = prev;
        int bestCount = count;
        while (!StdIn.isEmpty()) {
            int current = StdIn.readInt();
            if (current == prev) count++;
            else {
                prev = current;
                count = 1;
            }
            if (count > bestCount) {
                bestCount = count;
                best = current;
            }
        }
        StdOut.println("Longest run: " + bestCount + " consecutive " + best + "s");
    }
}
