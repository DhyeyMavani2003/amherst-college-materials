// =============================================================================
// IMPORTS

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
// =============================================================================



// =============================================================================
/**
 * @file PARDataLinkLayer.java
 * @author Scott F. Kaplan (sfkaplan@cs.amherst.edu) and Dhyey Mavani
 * @date April 2022
 */
public class PARDataLinkLayer extends DataLinkLayer {
// =============================================================================



  // ===========================================================================
  /**
   * Embed a raw sequence of bytes into a framed sequence.
   *
   * @param data The raw sequence of bytes to be framed.
   * @return A complete frame.
   */
  protected Queue<Byte> createFrame(Queue<Byte> data) {

    // Calculate the parity.
    byte parity = calculateParity(data);

    // Begin with the start tag.
    Queue<Byte> framingData = new LinkedList<Byte>();
    framingData.add(startTag);

    ////////// ADDED CODE /////////
    if (previousID == firstFrame) {
      framingData.add(secondFrame);
    } else {
      framingData.add(firstFrame);
    }
    /////////////////////////////////

    // Add each byte of original data.
    for (byte currentByte : data) {

      // If the current data byte is itself a metadata tag, then precede it with
      // an escape tag.
      if ((currentByte == startTag)     ||
          (currentByte == stopTag)      ||
          (currentByte == escapeTag)    ||
          (currentByte == ACK)) { // Escaping out the ack message too :)

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
  // ===========================================================================



  // ===========================================================================
  /**
   * Determine whether the received, buffered data constitutes a complete frame.
   * If so, then remove the framing metadata and return the original data. Note
   * that any data preceding an escaped start tag is assumed to be part of a
   * damaged frame, and is thus discarded.
   *
   * @return If the buffer contains a complete frame, the extracted, original
   *         data; <code>null</code> otherwise.
   */
  protected Queue<Byte> processFrame () {

    // Search for a start tag. Discard anything prior to it.
    boolean startTagFound = false;
    Iterator<Byte> i = receiveBuffer.iterator();
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
    int index = 1;
    LinkedList<Byte> extractedBytes = new LinkedList<Byte>();
    boolean stopTagFound = false;
    while (!stopTagFound && i.hasNext()) {

      // Grab the next byte. If it is...
      // (a) An escape tag: Skip over it and grab what follows as literal data.
      // (b) A stop tag: Remove all processed bytes from the buffer and end
      // extraction.
      // (c) A start tag: All that precedes is damaged, so remove it from the
      // buffer and restart extraction.
      // (d) Otherwise: Take it as literal data.

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
      System.out.println("PARDataLinkLayer.processFrame(): " +
        "Got whole frame!");
    }

    ////////// MY CODE FOR PROCESS FRAME STARTS HERE ///////////////

    // Checking if it is an ACK frame
    if (extractedBytes.size() == 1) {
      if (extractedBytes.get(0) == ACK) {
        return extractedBytes;
      }
      return null;
    }

    // Get the sequence number.
    byte sequenceNumber = extractedBytes.remove(0);

    ////////////////////// ERROR HANDLING ///////////////////////
    // If the sequence number has error
    if (sequenceNumber != firstFrame && sequenceNumber != secondFrame) {
      System.out.println("processFrame(): numbering messed up");
      return null;
    }

    // If the frame is empty
    if (extractedBytes.size() == 0) {
      System.out.println("processFrame(): frame is empty");
      return null;
    }
    /////////////////////////////////////////////////////////////

    // If the numbering is OK...
    if (sequenceNumber == currentExpID) {

      // parity check as provided by professor Kaplan
      byte receivedParity = extractedBytes.remove(extractedBytes.size() - 1);
      byte calculatedParity = calculateParity(extractedBytes);
      if (receivedParity != calculatedParity) {
        System.out.printf("ParityDataLinkLayer.processFrame():" + 
          "\tDamaged frame\n");
        return null;
      }

      // If we reach here then we know that parity check succeeded
      // Now just return an ACK...
      Queue<Byte> ACKFrame = createACKFrame();
      transmit(ACKFrame);

      return extractedBytes;
    } else {

      System.out.println("processFrame(): different number than expected");

      // Still send an ACK, but return `null`.
      Queue<Byte> ACKFrame = createACKFrame();
      transmit(ACKFrame);

      return null;
    }
    ///////////////////////////////////////////////////////////////////////

  } // processFrame ()
  // ===========================================================================



  // ===========================================================================
  /**
   * After sending a frame, do any bookkeeping (e.g., buffer the frame in case a
   * resend is required).
   *
   * @param frame The framed data that was transmitted.
   */
  protected void finishFrameSend(Queue<Byte> frame) {

    ////// MY CODE FOR FLOW CONTROL ///////
    // Start the timer.
    
    startTime = System.currentTimeMillis();

    // First buffer the frame.
    sentBufferFrame = frame;
    if (sentBufferFrame == null) {
      System.out.println("Null Buffer Dude!!!!");
    }

    // This indicates that we just finally finished sending the message we wanted to send.
    if (previousID == firstFrame) {
      previousID = secondFrame;
    } else {
      previousID = firstFrame;
    }

  } // finishFrameSend ()
  // ===========================================================================



  // ===========================================================================
  /**
   * After receiving a frame, do any bookkeeping (e.g., deliver the frame to the
   * client, if appropriate) and responding (e.g., send an acknowledgment).
   *
   * @param frame The frame of bytes received.
   */
  protected void finishFrameReceive(Queue<Byte> frame) {

    // The case when the sender is just receiving the acknowledgement
    if (frame.size() == 1 && frame.peek() == ACK) {

      // Move the second hand of the sliding window thingy to reflect the fact
      // that we received an ACK.
      if (nextID == firstFrame) {
        nextID = secondFrame;
      } else {
        nextID = firstFrame;
      }
      return;

    }

    // The receiver ready for the next frame.
    if (currentExpID == firstFrame) {
      currentExpID = secondFrame;
    } else {
      currentExpID = firstFrame;
    }

    // Deliver frame to the client.
    byte[] deliverable = new byte[frame.size()];
    for (int i = 0; i < deliverable.length; i += 1) {
        deliverable[i] = frame.remove();
    }

    client.receive(deliverable);

  } // finishFrameReceive ()
  // ===========================================================================



  // ===========================================================================
  /**
   * Determine whether a timeout should occur and be processed. This method is
   * called regularly in the event loop, and should check whether too much time
   * has passed since some kind of response is expected.
   */
  protected void checkTimeout() {

    if (previousID == nextID && 
    System.currentTimeMillis() - startTime > 100) {

      transmit(sentBufferFrame);
      startTime = System.currentTimeMillis();

    }

  } // checkTimeout ()
  // ===========================================================================



  // ===========================================================================
  /**
   * For a sequence of bytes, determine its parity.
   *
   * @param data The sequence of bytes over which to calculate.
   * @return <code>1</code> if the parity is odd; <code>0</code> if the parity
   *         is even.
   */
  // Provided by Professor Kaplan
  private byte calculateParity(Queue<Byte> data) {

    int parity = 0;
    for (byte b : data) {
      for (int j = 0; j < Byte.SIZE; j += 1) {
        if (((1 << j) & b) != 0) {
          parity ^= 1;
        }
      }
    }

    return (byte) parity;

  } // calculateParity ()
  // ===========================================================================



  // ===========================================================================
  /**
   * Remove a leading number of elements from the receive buffer.
   *
   * @param index The index of the position up to which the bytes are to be
   *              removed.
   */
  // Provided by Professor Kaplan
  private void cleanBufferUpTo(int index) {

    for (int i = 0; i < index; i += 1) {
      receiveBuffer.remove();
    }

  } // cleanBufferUpTo ()
  // ===========================================================================



  // ===========================================================================
  // Just creating an ACK Frame
  private Queue<Byte> createACKFrame ()
  {
    Queue<Byte> ACKFrame = new LinkedList<Byte>();
    ACKFrame.add(startTag);
    ACKFrame.add(ACK);
    ACKFrame.add(stopTag);
    
    return ACKFrame;

  } // createACKFrame ()
  // ===========================================================================

  // ===========================================================================
  @Override
  protected Queue<Byte> sendNextFrame ()
  {
    // If the sender is still waiting, just return null
    if (previousID == nextID) {
      return null;
    } else {
      //do everything as before...
      return super.sendNextFrame();
    }
  } // sendNextFrame ()
  // ===========================================================================



  // ===========================================================================
  // DATA MEMBERS

  private final byte startTag = (byte) '{';
  private final byte stopTag = (byte) '}';
  private final byte escapeTag = (byte) '\\';

  /* Possible sequence numbers for the frames */
  private final byte firstFrame  = (byte) 0;
  private final byte secondFrame = (byte) -1;
  private final byte ACK      = (byte) '*';

  /* Buffer for the sent frame */
  private Queue<Byte> sentBufferFrame = null;

  /* Sequence number for the current frame */
  private byte previousID = secondFrame;
  private byte nextID = firstFrame;
  
  /* Timer */
  private long startTime;

  /* Expectation of the receiver */
  private byte currentExpID = firstFrame;
  // ===========================================================================



// =============================================================================
} // class ParityDataLinkLayer
// =============================================================================
