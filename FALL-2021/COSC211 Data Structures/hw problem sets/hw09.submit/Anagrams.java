// importing libraries
import java.util.Arrays;
import java.util.*;

// defining the class
public class Anagrams {
	public static void main(String[] args) {
		// making a new runtimer
		RunTimer rt = new RunTimer();
		rt.start(); // starts a run timer

		// UI welcome message
		System.out.println("Welcome to the anagram finder!\n");
		
		// usage UI guidance
		if (args.length != 1) {
			System.out.println("  usage: java Anagram some-text.txt\n");
			System.exit(0);
		}

		// UI reading give file message
		System.out.println("  reading file " + args[0] + "...");

		// making a new word reader
		WordReader wr = new WordReader(args[0]);

		// initialize the hash map
		HashMap<String, List<String> > map = new HashMap<>();

		// read the document word by word
		while (wr.nextWord() != null) {
			// catching the word
			String word = wr.nextWord();
			// coverting word to a character array
			char[] c = word.toCharArray();
			// sorting the array
			Arrays.sort(c);
			// making a new word
			String newWord = new String(c);
			
			// if map has this new word as key then there I should add our read word, else add and put in the map
			if (map.containsKey(newWord)) {
				map.get(newWord).add(word);
			} else {
				List<String> words = new ArrayList<>();
				words.add(word);
				map.put(newWord, words);
			}
		}

		// UI augment
		System.out.println("Found anagrams: ");

		// defining the count and max
		int count = 0;
		int max = 0;

		// for all the map's keys
		for (String s : map.keySet()) {
			// get the value list at given key
			List<String> values = map.get(s);
			// if the list has more than one element
			if (values.size() > 1) {
				// incrementing count
				count++;
				// if size is greater than max, then update max
				if (values.size() > max) {
					max = values.size();
				}
				// print values to console
				System.out.println(values);
			}
		}

		rt.stop(); // stops a run timer

		// UI and output to console
		System.out.println("There are " +count+ " sets of anagrams");
		System.out.println("It took " + rt.getElapsedMillis() + "ms");
		System.out.println("Words with most anagrams are ");

		// for each element in the map, print only those who are of max size
		for (String s : map.keySet()) {
			List<String> maxValues = map.get(s);
			if (maxValues.size() == max) {
				System.out.println(maxValues);
			}
		}
    }    
}
