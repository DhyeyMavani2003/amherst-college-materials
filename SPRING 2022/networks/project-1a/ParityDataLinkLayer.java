// =============================================================================
// IMPORTS

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
// =============================================================================


// =============================================================================
/**
 * @file   ParityDataLinkLayer.java
 * @author Dhyey D. Mavani (dmavani25@amherst.edu)
 * @date   February 2022
 */
public class ParityDataLinkLayer extends DataLinkLayer {
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
        // while there is still some raw data to parse
        while (curByteIndex < data.length) {
            // variable storing the number of 1 bits
            int numOneBits = 0;

            // add a startTag
            framingData.add(startTag);

            // look into the raw data 8 consecutive times
            for (int j = 0; j < 8 && curByteIndex < data.length; j++) {
                
                // If the current data byte is a metadata tag itself, then precede
                // it with an escape tag. Note: consider parity bytes as metadata
                byte curByte = data[curByteIndex];

                if ((curByte == startTag) ||
                (curByte == stopTag) ||
                (curByte == escapeTag) ||
                (curByte == evenParityTag) ||
                (curByte == oddParityTag)) {

                    framingData.add(escapeTag);
                }

                // Add the data byte itself.
                framingData.add(curByte);
                numOneBits += Integer.bitCount((int) curByte);

                // if I took the raw data
                curByteIndex++;
            }

            // add the correct parity byte
            // if we get even number of bits, else odd number of bits
            if (numOneBits % 2 == 0) {
                framingData.add(evenParityTag);
            } else {
                framingData.add(oddParityTag);
            }

            framingData.add(stopTag);

            
        }
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
        // the 1 bit counter 
		int numOneBits = 0;
        // Discard anything prior to the first startTag
        boolean        startTagFound = false;
        Iterator<Byte>             i = byteBuffer.iterator();
        while (!startTagFound && i.hasNext()) {
            byte current = i.next();
            if (current != startTag) {
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
        boolean parityMatches = false; // boolean for parity matching purposes
        while (!stopTagFound && i.hasNext()) {

            // Grab the next byte.  If it is...
            //   (a) An escape tag: Skip over it and grab what follows as
            //                      literal data.
            //   (b) A stop tag:    Remove all processed bytes from the buffer and
            //                      end extraction.
            //   (c) A start tag:   All that precedes is damaged, so remove it
            //                      from the buffer and restart extraction.
            //   (d) Otherwise:     Take it as literal data.
            byte current = i.next();
            if (current == escapeTag) {
                if (i.hasNext()) {
                    current = i.next();
                    extractedBytes.add(current);
                    //count the number of 1 bits in raw byte
                    numOneBits += Integer.bitCount((int)current);
                } else {
                    // An escape was the last byte available, so this is not a
                    // complete frame.
                    return null;
                }
            } else if (current == evenParityTag || current == oddParityTag){

				//if we get even num of 1-bits
				if ( (numOneBits % 2 == 0) && (current == evenParityTag) )
				{
					parityMatches = true;
				}
				// if we get odd num of 1-bits
				else if ( (numOneBits % 2 == 1) && (current == oddParityTag) )
				{
					parityMatches = true;
				}

				continue;

			} else if (current == stopTag) {
                cleanBufferUpTo(i);
                stopTagFound = true;
            } else if (current == startTag) {
                
                cleanBufferUpTo(i);
                extractedBytes = new LinkedList<Byte>();
            } else {
                extractedBytes.add(current);
                numOneBits += Integer.bitCount((int) current);
            }

        }

        // frame is incomplete if no stop tag
        if (!stopTagFound) {
            return null;
        }

        // Convert to the desired byte array.
        if (debug) {
            System.out.println("ParityDataLinkLayer.processFrame(): Got whole frame!");
        }
        byte[] extractedData = new byte[extractedBytes.size()];
        int                j = 0;
        i = extractedBytes.iterator();
        while (i.hasNext()) {
            extractedData[j] = i.next();
            if (debug) {
            System.out.printf("ParityDataLinkLayer.processFrame():\tbyte[%d] = %c\n",
                    j,
                    extractedData[j]);
            }
            j += 1;
        }

        if (stopTagFound == true && parityMatches == false)
		{
			System.out.println("Corrupted Frame Error occured");
			
			for (int k = 0; k < extractedData.length; k++)
			{
				System.out.print((char) extractedData[k]);
			}

			return null;
		}

        return extractedData;

    } // processFrame ()
    // ===============================================================



    // ===============================================================
    private void cleanBufferUpTo (Iterator<Byte> end) {

        Iterator<Byte> i = byteBuffer.iterator();
        while (i.hasNext() && i != end) {
            i.next();
            i.remove();
        }

    }
    // ===============================================================



    // ===============================================================
    // DATA MEMBERS
    // ===============================================================



    // ===============================================================
    // The start tag, stop tag, and the escape tag.
    private final byte startTag  = (byte)'{';
    private final byte stopTag   = (byte)'}';
    private final byte escapeTag = (byte)'\\';

    //The parity bytes
    private final byte oddParityTag  = 00000001;
	private final byte evenParityTag = 00000000;
    // ===============================================================



// ===================================================================
} // class ParityDataLinkLayer
// ===================================================================
