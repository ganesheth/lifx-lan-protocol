package org.ojothepojo.lifx.message.light.request;


import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;

public class GetColor extends Message {

    public GetColor() {
        super((short) 36, (short) 101, "00:00:00:00:00:00");
    }

    public GetColor(String targetMacAddress) {
        super((short) 36, (short) 101, targetMacAddress);
    }

    public GetColor(byte[] bytes) {
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
        return "GetColor" + super.toString() + "--payload()";
    }
}
