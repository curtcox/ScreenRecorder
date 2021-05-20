package com.curtcox.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

final class ByteBufferCompressor {

    static ByteBuffer compress(ByteBuffer in) {
        try {
            return compress0(in);
        } catch (IOException e) {
            throw new IllegalStateException("Lolwut?!", e);
        }
    }

    private static ByteBuffer compress0(ByteBuffer in) throws IOException {
        int remaining = in.remaining();
        Deflater deflater = new Deflater(remaining > 42 ? 9 : 0);

        int size = remaining + 20;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(size);
        DeflaterOutputStream deflaterStream = new DeflaterOutputStream(bytes, deflater, 0x2000, false);
        WritableByteChannel wbc = Channels.newChannel(deflaterStream);
        wbc.write(in);
        deflaterStream.finish();
        deflaterStream.flush();
        deflaterStream.close();
        return ByteBuffer.wrap(bytes.toByteArray());
    }

}
