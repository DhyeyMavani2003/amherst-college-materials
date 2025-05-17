import java.io.File;
import java.util.*;
public class DhyeyMavaniJava {
    
    public static void inputProcessor() {
        
        // ideally our input processor should read through the given file "input.txt"
        // line by line to have an array of list of obstacles coordinates
        // then we should keep reading and populate the array of list of commands
        // some of the pseudocode is provided below. I am not exactly sure how to do it yet.
        // But, given the time constraints, I think there are libraries of I/O and File in java which we can use


        File file = new File("input.txt");
        //Scanner sc = new Scanner(file);
        //System.out.println(sc.nextLine());
        // while (sc.hasNextLine()){
        //     System.out.println(sc.nextLine());
        // }
    }

    public static int roboTurtles(String[] list_commands, int[][] list_obstacles) {
        Set<String> set = new HashSet<>();
        for (int[] obstacle : list_obstacles) {
            set.add(obstacle[0] + " " + obstacle[1]);
        }
        int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int d = 0, x = 0, y = 0, distance = 0;
        for (String command : list_commands) {
            if (command == "L") {
                // turn left
                d = (d + 3) % 4;
            } else if (command == "R") {
                // turn right
                d = (d + 1) % 4;
            } else {
                // the case of move command "M 4" for example
                // move in the direction where we are currently pointed
                // until and unless we hit an obstacle
                int steps = command.charAt(2);
                while (steps-- > 0 && !set.contains((x + directions[d][0]) + " " + (y + directions[d][1]))) {
                    x += directions[d][0];
                    y += directions[d][1];
                }
            }
            // every time calculate the distance and update maxdistance if necessary
            distance = Math.max(distance, x * x + y * y);
        }
        return distance;
    }

    public static void main(String[] args) {
        String[] list_commands = new String[] {};
        int[][] list_obstacles = new int[][] {};
        inputProcessor(); // populates our above mentioned data structures
        
        // call to our method so that we can return the answer
        System.out.println(roboTurtles(list_commands, list_obstacles));
    }
}
