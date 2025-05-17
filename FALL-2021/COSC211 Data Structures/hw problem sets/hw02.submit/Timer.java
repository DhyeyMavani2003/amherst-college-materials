/**
 * This program demonstrates the usage of the RunTimer and CSVWriter
 * classes in order to test the running time of methods. In
 * particular, it will demonstrate how quickly your computer can count
 * to various values.
 */

public class Timer {


    public static long LinkedSimpleUSetTimer(String file){
        LinkedSimpleUSet<String> testLSUS = new LinkedSimpleUSet<String>();
        RunTimer rt = new RunTimer();
        WordReader wr = new WordReader(file);
        String word = wr.nextWord();
        rt.start();
        while (word != null){
            testLSUS.add(word);
            word = wr.nextWord();
        }
        rt.stop();
        return rt.getElapsedMillis();
    }

    public static long MTFSimpleUSetTimer(String file){
        MTFSimpleUSet<String> testMTFSUS = new MTFSimpleUSet<String>();
        RunTimer rt = new RunTimer();
        WordReader wr = new WordReader(file);
        String word = wr.nextWord();
        rt.start();
        while (word != null){
            testMTFSUS.add(word);
            word = wr.nextWord();
        }
        rt.stop();
        return rt.getElapsedMillis();
    }

    public static void main(String[] args) {
	// make a CSVWriter instance that will create a file called
	// "read-times.csv"
	CSVWriter csv = new CSVWriter("read-times.csv");

	// write first row of csv (column headings)
	csv.addEntry("file name");
	csv.addEntry("LinkedSimpleUSet-time (ms)");
    csv.addEntry("MTFSimpleUSet-time (ms)");
	csv.endLine();

    String[] files = {"CompleteShakespeare.txt", "DavidCopperfield.txt", "Dubliners.txt", "EnglishWords.txt", "Frankenstein.txt", "GreatExpectations.txt", "GreatGatsby.txt", "Iliad.txt", "Metamorphoses.txt", "MobyDick.txt", "PortraitOfTheArtist.txt", "Uslysses.txt", "WutheringHeights.txt"};

    for (String name : files){
	    csv.addEntry(name);
        csv.addEntry(LinkedSimpleUSetTimer(name));

        csv.addEntry(MTFSimpleUSetTimer(name));
        csv.endLine();
    }

    csv.close();
	}
}
