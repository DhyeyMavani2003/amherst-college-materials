// Exercise 1.4.3

public class Deck {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        String[] suit = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String[] rank = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] deck = new String[suit.length * rank.length];
        for (int i = 0; i < suit.length; i++)
            for (int j = 0; j < rank.length; j++)
                deck[rank.length * i + j] = rank[j] + " of " + suit[i];
        int n = deck.length;
        for (int i = 0; i < n; i++) {
            int r = i + (int) (Math.random() * (n - i));
            String t = deck[i];
            deck[i] = deck[r];
            deck[r] = t;
        }
        boolean[] handed = new boolean[n];
        for (int k = 0; k < n; k++) {
            int hand = 0;
            while (hand < 5) {
                int index = (int) (n * Math.random());
                if (!handed[index]) {
                    System.out.println(deck[index]);
                    hand++;
                    handed[index] = true;
                }
            }
            System.out.println();
        }
    }
}
