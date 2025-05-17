// =============================================================================
// IMPORTS

import java.util.*;
// =============================================================================


// =============================================================================
/**
 * @file   ParityDataLinkLayer.java
 * @author Scott F. Kaplan (sfkaplan@cs.amherst.edu)
 * @date   February 2020
 *
 * A data link layer that uses start/stop tags and byte packing to frame the
 * data, and that performs error management with a parity bit.  It employs no
 * flow control; damaged frames are dropped.
 */
public class PADataLinkLayer extends DataLinkLayer {
// =============================================================================


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
		(currentByte == escapeTag) || (currentByte == ackTag)) {

		framingData.add(escapeTag);
	    }
	    // Add the data byte itself.
	    framingData.add(currentByte);
	}

	// Add the parity byte.
	framingData.add(parity);
	
	// End with a stop tag.
	framingData.add(stopTag);

	return framingData;
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
	    } else if(current == ackTag){
				t = (long)(0);
				return null;
		}else if (current == stopTag) {
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
	    System.out.println("ParityDataLinkLayer.processFrame(): Got whole frame!");
	}
        
	// The final byte inside the frame is the parity.  Compare it to a
	// recalculation.
	byte receivedParity   = extractedBytes.remove(extractedBytes.size() - 1);
	byte calculatedParity = calculateParity(extractedBytes);
	if (receivedParity != calculatedParity) {
	    System.out.printf("ParityDataLinkLayer.processFrame():\tDamaged frame\n");
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
		prevSentID = (prevSentID+1) % 2;
		if(nextSentID == 0){
			nextSentID = 1;
		}else{
			nextSentID = 0;
		}
		copy = frame;
		t = System.currentTimeMillis();
	}
        // COMPLETE ME WITH FLOW CONTROL
	// finishFrameSend ()
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
        // COMPLETE ME WITH FLOW CONTROL
        // Deliver frame to the client.
		createAck();
        byte[] deliverable = new byte[frame.size()];
        for (int i = 0; i < deliverable.length; i += 1) {
            deliverable[i] = frame.remove();
        }
        client.receive(deliverable);
		if(nextSentID != prevSentID){
		if (nextReceiveID == 0){
			nextReceiveID = 1;
		}else{
			nextReceiveID = 0;
			}
		}
		transmit(ack);
	}
		// finishFrameReceive ()
    // =========================================================================



    // =========================================================================
    /**
     * Determine whether a timeout should occur and be processed.  This method
     * is called regularly in the event loop, and should check whether too much
     * time has passed since some kind of response is expected.
     */
    protected void checkTimeout () {
		if(t > 100000000){
			//transmit(copy);
		}
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
	private void createAck(){
		//Creates an ack frame with a sequence of 1s and escape tags
		if(ack.isEmpty()){
		ack.add(startTag);
		ack.add(ackTag);
		ack.add(ackTag);
		ack.add(ackTag);
		ack.add(ackTag);
		ack.add(ackTag);
		ack.add(ackTag);
		ack.add(ackTag);
		ack.add(ackTag);
		ack.add(stopTag);
		System.out.println("feio");}
	}
	// =========================================================================
	protected Queue<Byte> sendNextFrame () {
		if (sendBuffer.isEmpty()) {
			return null;
		}

		// Extract a frame-worth of data from the sending buffer.
		int frameSize = ((sendBuffer.size() < MAX_FRAME_SIZE)
				? sendBuffer.size()
				: MAX_FRAME_SIZE);
		if (nextSentID != nextReceiveID){
		Queue<Byte> data = new LinkedList<Byte>();
		for (int j = 0; j < frameSize; j += 1) {
			data.add(sendBuffer.remove());
		}
			Queue<Byte> framedData = createFrame(data);
			transmit(framedData);
			return framedData;
		}
		// Create a frame from the data and transmit it.
		return null;
	}


	// =========================================================================
	// go ()
	// =========================================================================

    // DATA MEMBERS

	/** The start tag. */
    private final byte startTag  = (byte)'{';

    /** The stop tag. */
    private final byte stopTag   = (byte)'}';

    /** The escape tag. */
    private final byte escapeTag = (byte)'\\';

	/** The acknowledgement tag. */
	private final byte ackTag = (byte)'|';

	/** acknowledgment frame */
	private Queue<Byte> ack = new LinkedList<Byte>();

	/** waiting for ack */
	private int nextSentID = 1;

	/** Timeout */
	private int prevSentID = 0;

	/** AckSent marker */
	private int nextReceiveID = 0;

	/** Copy */
	public Queue<Byte> copy = new LinkedList<Byte>();

	/** Timer */
	private Long t = (long)(0);
    // =========================================================================

// =============================================================================
} // class ParityDataLinkLayer
// =============================================================================

