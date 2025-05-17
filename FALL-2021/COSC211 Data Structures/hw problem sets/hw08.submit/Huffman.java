// importing java libraries
import java.io.*;
import java.util.*;

// defining Huffman class as implementation of PrefixCode
public class Huffman implements PrefixCode {
    // defining overallRoot of Huffman tree
    HuffmanNode overallRoot = null;

    // defining a map with character as key and codeword as value
    Map<Character, String> map = new HashMap<Character, String>();

    // defining a map with codeword as key and ascii code of character as value
    Map<String, Integer> map2 = new HashMap<String, Integer>();

    // defining original size
    int originalSize = 0;

    // defining compressed size
    int compressedSize = 0;

    // defining the HuffmanNode class using comparable interface
    public class HuffmanNode implements Comparable<HuffmanNode> {
        // defining what the HuffmanNode should store
        public int character;
        public int frequency;			
        public HuffmanNode left;		
        public HuffmanNode right;
        public String codeword;		
        
        // defining helpful methods to call and set HuffmanNode with character
        public HuffmanNode(int character) {
            this(character, 0);
        }
     
        // defining helpful methods to call and set HuffmanNode with character and frequency
        public HuffmanNode(int character, int frequency) {
            this(character, frequency, null, null);
        }
        
        // defining helpful methods to call and set HuffmanNode with character, frequency, left and right
        public HuffmanNode(int character, int frequency, HuffmanNode left, HuffmanNode right) {
            this.character = character;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }
        
        // defining helpful methods to check if the given node is a leaf
        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }
        
        // defining helpful methods to compare frequency
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }
    }

    // defining the generateCode method
    @Override
    public void generateCode(InputStream in) {
        // using the try-catch statement to capture the exception
        try {
            // defining CHAR_MAX
            int CHAR_MAX = 256;

            // initializing an array of CHAR_MAX size
            int[] count = new int[CHAR_MAX];

            // getting a char's ascii code from the input stream
            int ch = in.read();
            
            // while we have a real character we update the frequency of occurrence in count array
            while (ch != -1) {
                count[ch]++;
                ch = in.read();
            }

            // calculate the original size by adding the frequencies 
            for (int i = 0; i <count.length; i++) {
                originalSize += count[i];
            }

            // defining a nodeQueue as a priorityQueue with HuffmanNodes
            Queue<HuffmanNode> nodeQueue = new PriorityQueue<HuffmanNode>();

            // if there is a element occurence, then add that HuffmanNode to the nodeQueue
            for (int i = 0; i < count.length; i++) {
                if (count[i] > 0) 
                    nodeQueue.add(new HuffmanNode(i, count[i]));
            }

            // while the size of nodeQueue is greater than one, build a tree
            while (nodeQueue.size() > 1) {
                // getting nodes from the nodeQueue
                HuffmanNode node1 = nodeQueue.remove();
                HuffmanNode node2 = nodeQueue.remove();

                // calculate the frequency of the internal node
                int frequencySum = node1.frequency + node2.frequency;

                // making a new node with frequency of frequencySum
                HuffmanNode newRoot = new HuffmanNode(-1, frequencySum, node1, node2);
                
                // adding the new node in the nodeQueue
                nodeQueue.add(newRoot);
            }
            // setting the overall root to the new overall root extracted from the modified priority queue
            overallRoot = nodeQueue.remove();
            
            // defining tempcodeword
            String tempcodeword = "";

            // defining temporary node nd
            HuffmanNode nd = overallRoot;

            // calling recursiveMapPut to create map and map2
            recursiveMapPut(tempcodeword, nd);
            
        } catch (Exception e) {
            // handle exception
        }
    }

    // defining helper recursiveMapPut method
    public void recursiveMapPut(String tempcodeword, HuffmanNode nd) {
        // setting the node's codeword to be the current tempcodeword
        nd.codeword = tempcodeword;

        // if we can go left, then go left and recursively call this method with updated tempcodeword and nd's left node
        if (nd.left != null) { 
            recursiveMapPut(tempcodeword + "0", nd.left);
        }
        
        // if we can go right, then go right and recursively call this method with updated tempcodeword and nd's right node
        if (nd.right != null) {
            recursiveMapPut(tempcodeword + "1", nd.right);
        }

        // the nd is a leaf
        if (nd.isLeaf()) {

            // put the respective values according to the types of key and value pairs in the map and map2
            map.put((char) nd.character, nd.codeword);
            map2.put(nd.codeword, nd.character);

            // increment the compressedSize accordingly to keep tack of the compressed size
            compressedSize += nd.codeword.length() * nd.frequency;
        }
    }

    // defining getCodeword method
    @Override
    public String getCodeword(char ch) {
        // if map has the key storing ch, then return the corresponding value which is the codeword
        if (map.containsKey(ch)) {
            return map.get(ch);
        }

        // return an empty string if not found ch
        return "";
    }

    // defining the getChar method
    @Override
    public int getChar(String codeWord) {
        // if map2 has the key storing codeWord, then return the corresponding value which is the ascii associated to a character
        if (map2.containsKey(codeWord)) {
            return map2.get(codeWord);
        }

        // return -1 if not found codeWord
        return -1;
    }

    // defining the encode method
    @Override
    public String encode(String str) {
        // creating an array of chars in a string str
        char[] arr = str.toCharArray();

        // defining an empty encoded string
        String encoded = "";

        // adding and concatenating the associated codewords to generate the encoded version of the given string str
        for (int i = 0; i < arr.length; i++) {
            encoded += map.get(arr[i]);
        }
        
        // return the encoded string
        return encoded;
    }

    // defining the decode method
    @Override
    public String decode(String str) {
        /**********************************
         using the sliding window paradigm 
        ***********************************/ 

        // initializing the start and end variables along with the decoded string
        int start = 0;
        int end = 1;
        String decoded = "";

        // iterating until start is less than the string's length
        while (start < str.length()) {

            // taking a substring of the str to check if we have it mapped to some character in our map
            String temp = str.substring(start, end);

            // if map contains that substring as a codeword for some character,
            if (map2.containsKey(temp)) {

                // get the char associated
                char cur = (char) getChar(temp);

                // concatenate it with the decoded string
                decoded += cur;

                // set the new start to our old end
                start = end;
            }

            // increment the end
            end++;
        }
        
        // return the decoded string
        return decoded;

        /////////////////// ALTERNATIVE 1 //////////////////////
        /*System.out.println("The input string is " + str);
        char[] arr = str.toCharArray();
        System.out.print("{");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+",");
        }
        System.out.println("}");
        String decoded = "";
        System.out.println("The initial decoded string is " + decoded);
        HuffmanNode nd = overallRoot;
        System.out.println("The overall root node is storing " + nd.frequency);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '0') {
                
                if (nd.left != null) {
                    System.out.println("We moved left");
                    nd = nd.left;
                }
            
                if (nd.left == null && nd.right == null) {
                    System.out.println("At the leaf node");
                    decoded += (char) nd.character;
                    System.out.println("New decoded string is " + decoded);
                    nd = overallRoot;
                }
            }
            if (arr[i] == '1') {

                if (nd.right != null) {
                    System.out.println("We moved right");
                    nd = nd.right;
                }

                if (nd.right == null && nd.left == null) {
                    System.out.println("At the leaf node");
                    decoded += (char) nd.character;
                    System.out.println("New decoded string is " + decoded);
                    nd = overallRoot;
                }
            }
        }
        return decoded;*/
        
        ///////////////////// ALTERNATIVE 2 //////////////////////

        /*int start = 0;
        int end = 0;
        String decoded = "";
        while (start < str.length()) {
            end = start + 1;
            while (end <= str.length()) {
                String temp = str.substring(start, end);
                if (map2.containsKey(temp)) {
                    char cur = (char) getChar(temp);
                    decoded += cur;
                    break;
                }
                end++;
            }
            start = end;
        }
        return decoded;*/

    }

    // defining the originalSize method which just returns original size
    @Override
    public int originalSize() {
        return originalSize;
    }

    // defining the compressedSize method which just returns the compressed size
    @Override
    public int compressedSize() {
        return compressedSize/8;
    }
}
