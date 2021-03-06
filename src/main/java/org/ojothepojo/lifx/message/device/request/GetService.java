package org.ojothepojo.lifx.message.device.request;


import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;

public class GetService extends Message {

    public GetService() {
        super((short) 36, (short) 2, "00:00:00:00:00:00");
    }

    public GetService(byte[] bytes) {
        super(bytes);
    }

    @Override
    public ByteBuffer payloadToBytes() {
        return ByteBuffer.allocate(0);
    }

    @Override
    public void parsePayload(ByteBuffer bytes) {
        // No payload for this message
    }

    @Override
    public String toString() {
        return "GetService" + super.toString()+ "--payload()";
    }
}
