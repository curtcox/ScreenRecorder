package com.curtcox.app;

import java.nio.ByteBuffer;

final class Encoder {

    private final int width;
    private final int height;
    private final int bytesPerPixel;
    private final int bytesPerLine;
    private static final byte INDEX = 0;

    public Encoder(int width, int height, int bytesPerPixel) {
        this.width = width;
        this.height = height;
        this.bytesPerPixel = bytesPerPixel;
        bytesPerLine = width * bytesPerPixel;
    }

    void encode(ByteBuffer in, ByteBuffer out) {
        checkSize(in, out);
        for (int y = 0; y < height; y++) {
            int yoffset = y * bytesPerLine;
            encodeRow(in, yoffset, out, yoffset + y);
        }
    }

    private void encodeRow(ByteBuffer in, int srcOffset, ByteBuffer out,int destOffset) {
        out.put(destOffset++, INDEX);
        out.position(destOffset);
        out.put(oneLineOfBytes(in,srcOffset));
    }

    private ByteBuffer oneLineOfBytes(ByteBuffer in, int srcOffset) {
        ByteBuffer tmp = in.duplicate();
        tmp.position(srcOffset).limit(srcOffset + bytesPerLine);
        return tmp;
    }

    private void checkSize(ByteBuffer in, ByteBuffer out) {
        if (out.capacity() != (width * bytesPerPixel + 1) * height) {
            String message = "Invalid output buffer capacity: capacity != (width*bpp+1)*height, " +
                    out.capacity() + "!=" + (width * bytesPerPixel + 1) * height;
            throw new IllegalArgumentException(message);
        }
        if (in.remaining() != width * height * bytesPerPixel) {
            String message = "Invalid input buffer capacity: capacity != width*bpp*height, "
                    + in.capacity() + "!=" + width * bytesPerPixel * height;
            throw new IllegalArgumentException(message);
        }
    }

}
