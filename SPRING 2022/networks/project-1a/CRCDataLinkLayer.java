// =============================================================================
// IMPORTS
import java.lang.Math;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
// =============================================================================

//import com.oracle.jrockit.jfr.EventDefinition;

// =============================================================================
/**
 * @file   CRCDataLinkLayer.java
 * @author Dhyey Mavani (dmavani25@amherst.edu)
 * @date   March 2022
 */
public class CRCDataLinkLayer extends DataLinkLayer {
// =============================================================================

    
    // =========================================================================
    /**
     * Embed a raw sequence of bytes into a framed sequence.
     *
     * @param  data The raw sequence of bytes to be framed.
     * @return A complete frame.
     */
    protected byte[] createFrame (byte[] data) {

		Queue<Byte> framingData = new LinkedList<Byte>();
		
		int curByteIndex = 0;

		//while there is still some raw data
		while (curByteIndex < data.length) 
		{
            ArrayList<Byte> rawFrame = new ArrayList<>();
  
			//add a start tag to framingData
			framingData.add(startTag);
			
			//look into the raw data 8 times
			for(int j = 0; j < 8 && curByteIndex < data.length; j++)
			{
					// If metadata, precede with escape tag
					byte curByte = data[curByteIndex];
				
					if ((curByte == startTag) ||
					(curByte == stopTag) ||
                    (curByte == escapeTag)){

						//add an escape tag before the special raw byte
						framingData.add(escapeTag);
					}

					// Add the data byte itself.
                    framingData.add(curByte);
                    rawFrame.add(curByte);
					
					curByteIndex++;
            }
            
            ArrayList<Integer> dataBits = nextFrameBits(rawFrame);
            int checkSum = remainder(dataBits);
            // Add the checkSum
            framingData.add((byte) checkSum);
          
			// End with a stop tag
			framingData.add(stopTag);
		}

		// Convert to the desired byte array
		byte[] framedData = new byte[framingData.size()];
		Iterator<Byte>  i = framingData.iterator();
		int             j = 0;
		while (i.hasNext()) {
			framedData[j++] = i.next();
		}
		
		return framedData;
	
    } // createFrame ()
    // =========================================================================


    
    // =========================================================================
    /**
     * Determine whether the received, buffered data constitutes a complete
     * frame.  If so, then remove the framing metadata and return the original
     * data.  Note that any data preceding an escaped start tag is assumed to be
     * part of a damaged frame, and is thus discarded.
     *
     * @return If the buffer contains a complete frame, the extracted, original
     * data; <code>null</code> otherwise.
     */
    protected byte[] processFrame () {

		// Search for a start tag.  Discard anything prior to it.
		boolean        startTagFound = false;
		Iterator<Byte>             i = byteBuffer.iterator();
		while (!startTagFound && i.hasNext()) {
			byte cur = i.next();
			if (cur != startTag) {
				i.remove();
			} else {
				startTagFound = true;
			}
		}

		// If there is no start tag, then there is no frame.
		if (!startTagFound) {
			return null;
		}
		
		// Try to extract data while waiting for an unescaped stop tag.
		Queue<Byte> extractedBytes = new LinkedList<Byte>();
        boolean       stopTagFound = false;

        //tells us whether checkSum is satisfied
		while (!stopTagFound && i.hasNext()) {

			// Grab the next byte.  If it is...
			//   (a) An escape tag: Skip over it and grab what follows as
			//                      literal data.
			//   (b) A stop tag:    Remove all processed bytes from the buffer
			//   (c) A start tag:   All that precedes is damaged, so remove it
            //                      from the buffer and restart extraction.
			//   (d) Otherwise:     Take it as literal data. And assume it's a checkSum (temp)
			byte cur = i.next();
			
			if (cur == escapeTag) {
				if (i.hasNext()) {
					cur = i.next();
					extractedBytes.add(cur);
				} else {
					// An escape was the last byte available, so this is not a
					// complete frame.
					return null;
				}
			} else if (cur == stopTag) {

				cleanBufferUpTo(i); //including the parity byte
				stopTagFound = true;
				

			} else if (cur == startTag) {

				cleanBufferUpTo(i);
                extractedBytes = new LinkedList<Byte>();

                System.out.println("******************");
                System.out.println("Frames Are Damaged");
                System.out.println("******************");
				
			} else {
                extractedBytes.add(cur);
			}

		}

		

		// If there is no stop tag, then the frame is incomplete.
		if (!stopTagFound) {
			return null;
		}

        

        ArrayList<Integer> receivedBits_with_checksum = nextFrameBits(extractedBytes);
        
        int remainder = remainder(receivedBits_with_checksum);

        extractLastQueueElt(extractedBytes);
		
		// Convert to the desired byte array.
		if (debug) {
			System.out.println("CRCDataLinkLayer.processFrame(): Got whole frame!");
		}

		byte[] extractedData = new byte[extractedBytes.size()];
		int                j = 0;
		i = extractedBytes.iterator();
		while (i.hasNext()) {
			extractedData[j] = i.next();
				if (debug) {
				System.out.printf("CRCDataLinkLayer.processFrame():\tbyte[%d] = %c\n",
						j,
						extractedData[j]);
				}
			j += 1;
        }
        
        if (remainder != 0)
        {
            System.out.println("");
			System.out.println("Error occured");
			System.out.println("***** CRC Failure *****");
			for (int k = 0; k < extractedData.length; k++)
			{
				System.out.print((char) extractedData[k]);
			}
			System.out.println("");
			System.out.println("***************************");
			System.out.println("");

			return null;
        }

        return extractedData;


    } // processFrame ()
    // ===============================================================

    private static void extractLastQueueElt(Queue<Byte> data) 
    {
        ArrayList<Byte> temp = new ArrayList<>();
        while (!data.isEmpty())
        {
            temp.add(data.remove());
        }
        
        temp.remove(temp.size() - 1);
        while (!temp.isEmpty())
        {
            data.add(temp.remove(0));
        }
    }

    // ===============================================================
    private void cleanBufferUpTo (Iterator<Byte> end) {

		Iterator<Byte> i = byteBuffer.iterator();
		while (i.hasNext() && i != end) {
			i.next();
			i.remove();
		}

    }
    // ===============================================================


    /***************************************
     ************ MY METHODS ***************
     ***************************************/

    // mthod checking the divisibility
    public boolean isDivisible(int value, int gen_length){
        return ((value & (1<<gen_length)) != 0);
    }

    //Returns int but just do (byte)
    public static int injectNextBit(int receptor, int donor){
        return ((receptor<<1)|donor);
    }
        
    public int remainder(ArrayList<Integer> dataBits) {
        
        int result = 0;
        while (!dataBits.isEmpty()) {
            int nextBit = dataBits.remove(0);
            
            result = injectNextBit(result, nextBit);

            if (isDivisible(result, 8)) {
                result = result ^ generator;
            }
        }
        return result;
    }
    

    /**
     * @input the next frame to be considered
     * @return its raw data in a sequence of bits 
     * @specialfunction The zeros are APPENDED also...
     */
    private ArrayList<Integer> nextFrameBits(ArrayList<Byte> data)
    {
        //this will store added bits
        ArrayList<Integer> result = new ArrayList<>();

        for(int j = 0; j < data.size(); j++)
		{
                //stores the cur byte coonsidered
                byte curByte = data.get(j);

                //converts the byte into a binary string
                String binaryString = String.format("%8s", Integer.toBinaryString(curByte & 0xFF)).replace(' ', '0');

                //converting the binary string into a char array
                char[] dataBits = binaryString.toCharArray();

                //goes through the binary string and adds bits to the LinkedList
                for (int charIndex = 0; charIndex < dataBits.length; charIndex++)
                {
                    result.add(Character.getNumericValue(dataBits[charIndex]));
                }
        }

        // adding appended zeros
        for (int i = 0; i < 8; i++)
        {
            result.add(0);
        }
        return result;
    }

    private ArrayList<Integer> nextFrameBits(Queue<Byte> data)
    {
        //this will store added bits
        ArrayList<Integer> result = new ArrayList<>();
        Queue<Byte> holdOnQueue = new LinkedList<>();

        while (data.size() != 0)
		{
                //stores the cur byte considered
                byte curByte = data.remove();
                holdOnQueue.add(curByte);
                
                //converts the byte into a binary string
                String binaryString = String.format("%8s", Integer.toBinaryString(curByte & 0xFF)).replace(' ', '0');

                //converting the binary string into a char array
                char[] dataBits = binaryString.toCharArray();

                //goes through the binary string and adds bits to the LinkedList
                for (int charIndex = 0; charIndex < dataBits.length; charIndex++)
                {
                    result.add(Character.getNumericValue(dataBits[charIndex]));
                }
        }
        //give back the whole queue
        while (!holdOnQueue.isEmpty())
        {
            data.add(holdOnQueue.remove());
        }

        return result;
    }
    // ===============================================================
    // DATA MEMBERS
    // ===============================================================

    // generator
    private final int generator = 0b111010101;

    // ===============================================================
    // The start tag, stop tag, and the escape tag.
    private final byte startTag  = (byte)'{';
    private final byte stopTag   = (byte)'}';
    private final byte escapeTag = (byte)'\\';
    
    // ===============================================================

// ===================================================================
} // class CRCDataLinkLayer
// ===================================================================