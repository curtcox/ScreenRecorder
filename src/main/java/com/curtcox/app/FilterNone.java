package com.curtcox.app;

import java.nio.ByteBuffer;

/**
 * This implements the "None" filter, which amounts to prepending 0 on every row.
 * https://www.w3.org/TR/PNG/#9Filters
 */
final class FilterNone
    implements Filter
{

    private final int width;
    private final int height;
    private final int bytesPerLine;
    private static final int bytesPerPixel = Consts.bands;
    private static final byte FILTER_CODE = 0;

    FilterNone(int width, int height) {
        this.width = width;
        this.height = height;
        bytesPerLine = width * bytesPerPixel;
    }

    public void encode(ByteBuffer in, ByteBuffer out) {
        checkSize(in, out);
        for (int y = 0; y < height; y++) {
            int yoffset = y * bytesPerLine;
            encodeRow(in, yoffset, out, yoffset + y);
        }
    }

    private void encodeRow(ByteBuffer in, int srcOffset, ByteBuffer out,int destOffset) {
        out.put(destOffset++, FILTER_CODE);
        out.position(destOffset);
        out.put(oneLineOfBytes(in,srcOffset));
    }

    private ByteBuffer oneLineOfBytes(ByteBuffer in, int srcOffset) {
        ByteBuffer tmp = in.duplicate();
        tmp.position(srcOffset).limit(srcOffset + bytesPerLine);
        return tmp;
    }

    private void checkSize(ByteBuffer in, ByteBuffer out) {
        checkInputCapacity(in);
        checkOutputCapacity(out);
    }

    private void checkInputCapacity(ByteBuffer in) {
        if (in.remaining() != requiredInputCapacity()) {
            String message = "Invalid input buffer capacity: capacity != width*bytesPerPixel*height, "
                    + in.capacity() + "!=" + width * bytesPerPixel * height;
            throw new IllegalArgumentException(message);
        }
    }

    private void checkOutputCapacity(ByteBuffer out) {
        if (out.capacity() != requiredOutputCapacity()) {
            String message = "Invalid output buffer capacity: capacity != (width*bytesPerPixel+1)*height, " +
                    out.capacity() + "!=" + (width * bytesPerPixel + 1) * height;
            throw new IllegalArgumentException(message);
        }
    }

    int requiredInputCapacity()  { return width * height * bytesPerPixel; }
    int requiredOutputCapacity() { return (width * bytesPerPixel + 1) * height; }

}
