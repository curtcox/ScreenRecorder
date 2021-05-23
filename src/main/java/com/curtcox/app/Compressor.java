package com.curtcox.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

final class Compressor {

    private final ByteArrayOutputStream bytes;
    private final DeflaterOutputStream deflaterStream;
    private final WritableByteChannel wbc;

    Compressor(Deflater deflater, int size) {
        bytes = new ByteArrayOutputStream(size);
        deflaterStream = new DeflaterOutputStream(bytes, deflater, 0x2000, false);
        wbc = Channels.newChannel(deflaterStream);
    }

    static ByteBuffer compress(ByteBuffer in) {
        try {
            int remaining = in.remaining();
            Deflater deflater = new Deflater(remaining > 42 ? 9 : 0);
            int size = remaining + 20;
            Compressor compressor = new Compressor(deflater,size);
            compressor.deflate(in);
            compressor.close();
            return compressor.toByteBuffer();
        } catch (IOException e) {
            throw new IllegalStateException("Lolwut?!", e);
        }
    }

    static byte[] compress(byte[] input) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DeflaterOutputStream stream = new DeflaterOutputStream(out,new Deflater(Deflater.BEST_COMPRESSION), 5120);
        for (byte b: input) {
            stream.write(b);
        }
        stream.close();
        out.close();
        return out.toByteArray();
    }

    void deflate(ByteBuffer in) throws IOException {
        wbc.write(in);
    }

    private void close() throws IOException {
        deflaterStream.finish();
        deflaterStream.flush();
        deflaterStream.close();
    }

    private ByteBuffer toByteBuffer() throws IOException {
        return ByteBuffer.wrap(bytes.toByteArray());
    }

}
