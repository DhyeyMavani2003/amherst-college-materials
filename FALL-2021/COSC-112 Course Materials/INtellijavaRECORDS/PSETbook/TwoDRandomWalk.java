/* 1.3.37 2D random walk. A two-dimensional random walk simulates the behavior of a particle moving in
a grid of points. At each step, the random walker moves north, south, east, or west with probability
equal to 1/4, independent of previous moves. Write a program RandomWalker that takes an integer
command-line argument n and estimates how long it will take a random walker to hit the boundary of a
2n-by-2n square centered at the starting point. */
public class TwoDRandomWalk {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int x = 0, y = 0; // initial position of the walker
        int sumMoves = 0;
        while (Math.abs(x) < N && Math.abs(y) < N) {
            double move = Math.random();
            if (move < 0.25) {
                x++;
            } else if (move < 0.5) {
                x--;
            } else if (move < 0.75) {
                y++;
            } else
                y--;
            sumMoves++;
        }
        System.out.println(sumMoves);
    }
}

