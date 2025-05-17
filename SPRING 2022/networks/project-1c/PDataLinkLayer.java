// =============================================================================
// IMPORTS
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
// =============================================================================


// =============================================================================
/**
 * @file   PARDataLinkLayer.java
 * @author Dhyey Mavani
 * @date   March 2022
 *
 * A data link layer that uses start/stop tags and byte packing to frame the
 * data, and that performs error management with a parity bit. 
 * Additionally it involves flow control.
 */
public class PDataLinkLayer extends DataLinkLayer {
// =============================================================================

    /**
     * (SENDER)
     * Time the most recent frame was sent
     */
    public long recentSendTime;

    /**
     * (SENDER)
     * The frames that could be asked to resend in the checkTimeOut
     */
    private Queue<Byte> resendBuffer = new LinkedList<>();

    /**
     * (SENDER)
     * Whether to wait and not send anything
     */
    private boolean isReadyToSend = true;

    /**
     * isThisSender = if this DLL belongs to the sender
     * isThisFirstSend = if this is the first data send among all users
     */
    private boolean isThisSender = false;
    //private static boolean isThisFirstSend = true;

    /**
     * (RECEIVER)
     * These show frame with what number has to be sent next and received next
     */
    private byte numberingToSend = (byte) 0; // for sender (added before parity)
    private byte numberingToReceive = (byte) 0; // for receiver

    /**
     * (changes for RECEIVER)
     * Whether to send the data to receiver
     */
    private boolean sendToClient = true;

    // =========================================================================
    /**
     * Embed a raw sequence of bytes into a framed sequence.
     *
     * @param  data The raw sequence of bytes to be framed.
     * @return A complete frame.
     */
    protected Queue<Byte> createFrame (Queue<Byte> data) {

	    // Calculate the parity.
	    byte parity = calculateParity(data);
	
	    // Begin with the start tag.
	    Queue<Byte> framingData = new LinkedList<Byte>();
	    framingData.add(startTag);

	    // Add each byte of original data.
        for (byte currentByte : data) {

            // If the current data byte is itself a metadata tag, then precede
            // it with an escape tag.
            if ((currentByte == startTag) ||
            (currentByte == stopTag) ||
            (currentByte == escapeTag)){

                framingData.add(escapeTag);

            }

            // Add the data byte itself.
            framingData.add(currentByte);
            

	    }

        // Add the numbering of the frame
        // Note: this is done before adding parity
        framingData.add(this.numberingToSend);

        // reset the numbering to send
        this.numberingToSend = (this.numberingToSend == 0) ? (byte) 1 : (byte) 0;

	    // Add the parity byte.
	    framingData.add(parity);
    
	    // End with a stop tag.
        framingData.add(stopTag);
        //===============================
        /**
         * Debugging
         */
        //  if(this.isThisSender)
        //  {
        //      System.out.println();
        //      System.out.println("SENDER: " + framingData);
        //  }
        //  if(!this.isThisSender)
        //  {
        //      System.out.println();
        //      System.out.println("RECEIVER: " + framingData);
        //  }
        //==============================
    
        return framingData;
    } // createFrame ()
    // =========================================================================


    // =========================================================================
    /**
     * This method is called only if sendBuffer is not empty 
     * - this will happen first to the SENDER; 
     * that is how this method determines if client of this DataLinkLayer is a sender or receiver
     * ============================
     * Extract the next frame-worth of data from the sending buffer, frame it,
     * and then send it.
     * 
     * @return the frame of bytes transmitted.
     */
    protected Queue<Byte> sendNextFrame () {
        
        //this will only run once 
        //to determine if this is a sender

            this.isThisSender = true;
            //isThisFirstSend = false;


        //do not send if issues with the last frame
        if (!isReadyToSend)
        {
            return null;
        }

        return super.sendNextFrame();
    } // sendNextFrame ()
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
    protected Queue<Byte> processFrame () {

        // Search for a start tag.  Discard anything prior to it.
        boolean        startTagFound = false;
        Iterator<Byte>             i = receiveBuffer.iterator();
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
        int                       index = 1;
	    LinkedList<Byte> extractedBytes = new LinkedList<Byte>();
	    boolean            stopTagFound = false;
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
            index += 1;
            if (current == escapeTag) {
                if (i.hasNext()) {
                    current = i.next();
                            index += 1;
                    extractedBytes.add(current);
                } else {
                    // An escape was the last byte available, so this is not a
                    // complete frame.
                    return null;
                }
            } else if (current == stopTag) {
                cleanBufferUpTo(index);
                stopTagFound = true;
            } else if (current == startTag) {
                cleanBufferUpTo(index - 1);
                index = 1;
                extractedBytes = new LinkedList<Byte>();
            } else {
                extractedBytes.add(current);
            }

        }

        // If there is no stop tag, then the frame is incomplete.
        if (!stopTagFound) {
            return null;
        }

        if (debug) {
            System.out.println("PARDataLinkLayer.processFrame(): Got whole frame!");
        }
            
        // The final byte inside the frame is the parity.  
        // Compare it to a recalculation.
        byte receivedParity   = extractedBytes.remove(extractedBytes.size() - 1);
        
        //storing the received numbering
        //byte receivedNumbering = extractedBytes.remove(extractedBytes.size() - 1);

        byte calculatedParity = calculateParity(extractedBytes);
        if (receivedParity != calculatedParity) {
            System.out.printf("PARDataLinkLayer.processFrame():\tDamaged frame\n");
            
            return null;
        }

        return extractedBytes;

    } // processFrame ()
    // =========================================================================



    // =========================================================================
    /**
     * After sending a frame, do any bookkeeping (e.g., buffer the frame in case
     * a resend is required).
     *
     * @param frame The framed data that was transmitted.
     */ 
    protected void finishFrameSend (Queue<Byte> frame) {

        // COMPLETE ME WITH FLOW CONTROL

        // send the frame to resend buffer
        this.resendBuffer = frame;
        this.recentSendTime = System.currentTimeMillis();
        

        //do not send anything after this frame
        //until isReadyToSend becomes true (in finishFrameReceive)
        this.isReadyToSend = false;

    } // finishFrameSend ()
    // =========================================================================



    // =========================================================================
    /**
     * After receiving a frame, do any bookkeeping (e.g., deliver the frame to
     * the client, if appropriate) and responding (e.g., send an
     * acknowledgment).
     *
     * @param frame The frame of bytes received.
     */
    protected void finishFrameReceive (Queue<Byte> frame) {
        // compare frame numbers
        // if they match, deliver it to the client
        // then send an acknowledgment message to the sender.
        // if not, then do something, and do not deliver it to the client.

        // COMPLETE ME WITH FLOW CONTROL
        byte receivedNumbering = frame.remove();
        if (receivedNumbering != numberingToReceive)
        {
            this.sendToClient = false;
        }
        else 
        {
            this.numberingToReceive = (this.numberingToReceive == 0) ? (byte) 1 : (byte) 0;
            this.sendBuffer.add(this.ackMsg);
            if (frame.remove() == this.ackMsg)
            {
                //allow the next frames to be sent
                this.isReadyToSend = true;
                this.resendBuffer = null;
            }
        }
        /**
         * If this is a receiver
         */
            // this.sendBuffer.add(this.ackMsg);
        

        /**
         * If this is a sender
         */
            //if the received frame is an ack
            // if (frame.remove() == this.ackMsg)
            // {
            //     //allow the next frames to be sent
            //     this.isReadyToSend = true;
            //     this.resendBuffer = null;
            // }

        //if the processFrame's received numbering allows to send the data to client
        if (this.sendToClient)
        {
            // Deliver frame to the client.
            byte[] deliverable = new byte[frame.size()];
            for (int i = 0; i < deliverable.length; i += 1) {
                deliverable[i] = frame.remove();
            }

            client.receive(deliverable);
        }
        //allow to send frames to client for now
        this.sendToClient = true;
        
    } // finishFrameReceive ()
    // =========================================================================



    // =========================================================================
    /**
     * Determine whether a timeout should occur and be processed.  This method
     * is called regularly in the event loop, and should check whether too much
     * time has passed since some kind of response is expected.
     */
    protected void checkTimeout () {
        // if there is a timeout, resend the last frame.
        // update the fields accordingly. (ex: start a new timer).
        // wait for acknowledgment.


        // COMPLETE ME WITH FLOW CONTROL
        /**
         * If this is a sender
         */
        //if(this.resendBuffer != null)
        //{
            //the current time

            long currentTime = System.currentTimeMillis();

            //find if 1 second passed from the last sent

            if ( (currentTime - this.recentSendTime) >= 1000 )
            {
                //take the frame to be sent from buffer

                Queue<Byte> frameToSend = this.resendBuffer;

                //resend the frame to receiver
                //and keep record of time again
                //as finishFrameSend IS NOT CALLED

                System.out.println("RESENDING FROM SENDER" + frameToSend);
                transmit(frameToSend);
                this.recentSendTime = System.currentTimeMillis();
            }
        //}

    } // checkTimeout ()
    // =========================================================================



    // =========================================================================
    /**
     * For a sequence of bytes, determine its parity.
     *
     * @param data The sequence of bytes over which to calculate.
     * @return <code>1</code> if the parity is odd; <code>0</code> if the parity
     *         is even.
     */
    private byte calculateParity (Queue<Byte> data) {

        int parity = 0;
        for (byte b : data) {
            for (int j = 0; j < Byte.SIZE; j += 1) {
                if (((1 << j) & b) != 0) {
                    parity ^= 1;
                }
            }
        }

        return (byte)parity;
	
    } // calculateParity ()
    // =========================================================================
    


    // =========================================================================
    /**
     * Remove a leading number of elements from the receive buffer.
     *
     * @param index The index of the position up to which the bytes are to be
     *              removed.
     */
    private void cleanBufferUpTo (int index) {

        for (int i = 0; i < index; i += 1) {
            receiveBuffer.remove();
	    }

    } // cleanBufferUpTo ()
    // =========================================================================



    // =========================================================================
    // DATA MEMBERS

    /** The start tag. */
    private final byte startTag  = (byte)'{';

    /** The stop tag. */
    private final byte stopTag   = (byte)'}';

    /** The escape tag. */
    private final byte escapeTag = (byte)'\\';

    /** The ack message */
    private final byte ackMsg = (byte) '*';
    // =========================================================================



// =============================================================================
} // class PARDataLinkLayer
// =============================================================================

