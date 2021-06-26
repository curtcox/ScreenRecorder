package com.neomemex.png;

import java.io.IOException;
import java.nio.ByteBuffer;

import java.nio.channels.WritableByteChannel;
import java.util.Arrays;

final class WritableByteBufferChannel implements WritableByteChannel {

    private boolean open = true;
    private final ByteBuffer buffer;

    WritableByteBufferChannel(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    WritableByteBufferChannel(int capacity) {
        this(ByteBuffer.allocate(capacity));
    }

    @Override public int write(ByteBuffer fromBuffer) throws IOException {
        int count = fromBuffer.remaining();
        buffer.put(fromBuffer);
        return count;
    }
    @Override public void close() throws IOException { open = false; }
    @Override public boolean isOpen() { return open; }

    byte[] asBytes() { return Arrays.copyOfRange(buffer.array(),0,buffer.position()); }


}