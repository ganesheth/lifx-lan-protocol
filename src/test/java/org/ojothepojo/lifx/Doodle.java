package org.ojothepojo.lifx;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class Doodle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Doodle.class);

    @Test
    public void byteStuff() {
        byte b1 = 127;
        LOGGER.debug("b1 is " + b1);
        LOGGER.debug("Byte.SIZE is " + Byte.SIZE);
        LOGGER.debug("Binary String: " + Integer.toBinaryString(b1));
        LOGGER.debug("Hex String: " + Integer.toHexString(b1));

        byte b2 = 0x77;
        LOGGER.debug("b2 is " + b2);

        // Create an empty ByteBuffer with a 10 byte capacity
        ByteBuffer bbuf = ByteBuffer.allocate(10);

        // Get the buffer's capacity
        int capacity = bbuf.capacity(); // 10

        // Use the absolute put().
        // This method does not affect the position.
        bbuf.put((byte)0xFF); // position=0
        bbuf.rewind();
        LOGGER.debug("Byte from buffer " + bbuf.get());



    }
}
