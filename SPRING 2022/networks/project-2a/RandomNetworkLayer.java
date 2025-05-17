// =============================================================================
// IMPORTS

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
// My added import.
import java.util.Set;
// =============================================================================



// =============================================================================
/**
 * @file RandomNetworkLayer.java
 * @author Scott F. Kaplan (sfkaplan@cs.amherst.edu) and Dhyey Mavani (dmavani25@amherst.edu)
 * @date May 2022
 *
 *       A network layer that perform routing via random link selection.
 */
public class RandomNetworkLayer extends NetworkLayer {
// =============================================================================



    // =========================================================================
    // PUBLIC METHODS
    // =========================================================================



    // =========================================================================
    /**
     * Default constructor. Set up the random number generator.
     */
    public RandomNetworkLayer() {

        random = new Random();

    } // RandomNetworkLayer ()
    // =========================================================================



    // =========================================================================
    /**
     * Create a single packet containing the given data, with header that marks
     * the source and destination hosts.
     *
     * @param destination The address to which this packet is sent.
     * @param data        The data to send.
     * @return the sequence of bytes that comprises the packet.
     */
    protected byte[] createPacket(int destination, byte[] data) {
        
        /*** Initialization ***/
        int inputDataLength   = data.length;
        int lenTotal = inputDataLength + bytesPerHeader;
        int sourceAdd = this.address;
        int destAdd = destination;
        /**********************/

        /****Populating Arrays*/
        byte[] returnPacket = new byte[lenTotal];

        byte[] lengthByteArray = intToBytes(inputDataLength);
        copyInto(returnPacket, lengthOffset, lengthByteArray);

        byte[] sourceAddByteArray = intToBytes(sourceAdd);
        copyInto(returnPacket, sourceOffset, sourceAddByteArray);

        byte[] destinationAddressByteArray = intToBytes(destAdd);
        copyInto(returnPacket, destinationOffset, destinationAddressByteArray);

        copyInto(returnPacket, bytesPerHeader, data);
        /*********************/

        return returnPacket;

    } // createPacket ()
    // =========================================================================



    // =========================================================================
    /**
     * Randomly choose the link through which to send a packet given its
     * destination.
     *
     * @param destination The address to which this packet is being sent.
     */
    protected DataLinkLayer route(int destination) {

        int numDLLs = this.dataLinkLayers.size();
        if (numDLLs <= 0) return null;

        int randIdx = random.nextInt(numDLLs);

        Iterator<DataLinkLayer> DLLIterator =
            this.dataLinkLayers.values().iterator();
        for (int i = 0; i < randIdx; i++) {
            DLLIterator.next();
        }
        DataLinkLayer result = DLLIterator.next();
        if (result == null) {
            return null;
        }

        return result;

    } // route ()
    // =========================================================================



    // =========================================================================
    /**
     * Examine a buffer to see if it's data can be extracted as a packet; if so,
     * do it, and return the packet whole.
     *
     * @param buffer The receive-buffer to be examined.
     * @return the packet extracted packet if a whole one is present in the
     *         buffer; <code>null</code> otherwise.
     */
    protected byte[] extractPacket(Queue<Byte> buffer) {

        if (buffer.size() <= bytesPerHeader) {
            return null;
        }
        
        Iterator<Byte> buffIter   = buffer.iterator();
        byte[]         buffLen = new byte[Integer.BYTES];
        int            lenPacket;
        for (int i = 0; i < Integer.BYTES; i++) {
            buffLen[i] = buffIter.next();
        }
        lenPacket = bytesToInt(buffLen);

        if (lenPacket > buffer.size() - bytesPerHeader) return null;

        
        int lenTotal = lenPacket + bytesPerHeader;
        byte[] returnPacket = new byte[lenTotal];
        for (int i = 0; i < lenTotal; i++) {
            returnPacket[i] = buffer.remove();
        }

        return returnPacket;

    } // extractPacket ()
    // =========================================================================



    // =========================================================================
    /**
     * Given a received packet, process it. If the destination for the packet
     * is this host, then deliver its data to the client layer. If the
     * destination is another host, route and send the packet.
     *
     * @param packet The received packet to process.
     * @see createPacket
     */
    protected void processPacket(byte[] packet) {

        int    length;
        byte[] buffLen             = new byte[Integer.BYTES];
        int    sourceAdd;
        byte[] sourceAddBuffer      = new byte[Integer.BYTES];
        int    destAdd;
        byte[] destinationAddressBuffer = new byte[Integer.BYTES];
        copyFrom(buffLen, packet, lengthOffset);
        copyFrom(sourceAddBuffer, packet, sourceOffset);
        copyFrom(destinationAddressBuffer, packet, destinationOffset);
        length    = bytesToInt(buffLen);
        sourceAdd = bytesToInt(sourceAddBuffer);
        destAdd   = bytesToInt(destinationAddressBuffer);

        if (destAdd == this.address) {

            byte[] data = new byte[length];
            copyFrom(data, packet, bytesPerHeader);
            this.client.receive(data);
            return;

        }

        DataLinkLayer chosenLink = route(destAdd);
        chosenLink.send(packet);

    } // processPacket ()
    // =========================================================================



    // =========================================================================
    // INSTANCE DATA MEMBERS
    private Random random;
    // =========================================================================



    // =========================================================================
    // CLASS DATA MEMBERS

    /** The offset into the header for the length. */
    public static final int lengthOffset = 0;

    /** The offset into the header for the source address. */
    public static final int sourceOffset = lengthOffset + Integer.BYTES;

    /** The offset into the header for the destination address. */
    public static final int destinationOffset = sourceOffset + Integer.BYTES;

    /** How many total bytes per header. */
    public static final int bytesPerHeader = destinationOffset + Integer.BYTES;

    /** Whether to emit debugging information. */
    public static final boolean debug = false;
    // =========================================================================



// =============================================================================
} // class RandomNetworkLayer
// =============================================================================
