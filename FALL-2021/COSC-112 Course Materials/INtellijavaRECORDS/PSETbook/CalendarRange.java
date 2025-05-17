public class CalendarRange {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int d = Integer.parseInt(args[1]);
        boolean a = ((d == 20) && ((m == 3) || (m == 4) || (m == 5) || (m == 6)));
        System.out.println(a);
    }
}
