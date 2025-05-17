public class nHellos {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);


        for (int i = 1; i <= n; i++) {
            int r10 = i % 10;
            int r100 = i % 100;
            System.out.print(i);
            if (r100 >= 11 && r100 <= 20) {
                System.out.print("th");
            } else if (r10 == 1) System.out.print("st");
            else if (r10 == 2) System.out.print("nd");
            else if (r10 == 3) System.out.print("rd");

            else System.out.print("th");

            System.out.println(" Shree Krishna Sharanam Mamah.");
        }

    }
}
