package com.curtcox.app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

final class BufferedImageSerializer {

    private final Dimension dim;
    private final Encoder encoder;
    private final WritableRaster raster;
    private final int numBands;

    BufferedImageSerializer(BufferedImage image, Encoder encoder) {
        this.dim     = dim(image);
        this.encoder = encoder;
        raster       = image.getRaster();
        numBands     = raster.getNumBands();
    }

    private static Dimension dim(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }


    ByteBuffer getPixelBytes() {
        final int[] dataElements = dataElements();
        final int         length = Array.getLength(dataElements);
        ByteBuffer        buffer = writeToBuffer(dataElements,numBands,length);
        return encoded(buffer,numBands,length);
    }

    int[] dataElements() {
        return (int[]) raster.getDataElements(0, 0, dim.width, dim.height, null);
    }

    private ByteBuffer writeToBuffer(int[] dataElements, int numBands, int length) {
        ByteBuffer out = ByteBuffer.allocate(length * numBands + 1);

        if (numBands == 4) {
            writeDataElements(dataElements,length,out.asIntBuffer());
        } else {
            writeDataElements(dataElements,length,out);
        }

        out.position(0);
        out.limit(out.limit() - 1);
        return out;
    }

    private void writeDataElements(int[] dataElements, int length, IntBuffer intBuffer) {
        for (int i = 0; i < length; i++) {
            final int e = dataElements[i];
            final int a = (e & 0xff000000) >>> 24;
            intBuffer.put(e << 8 | a);
        }
    }

    private void writeDataElements(int[] dataElements, int length,ByteBuffer out) {
        int index = 0;
        for (int i = 0; i < length; i++) {
            final int e = dataElements[i];
            out.putInt(index, e << 8);
            index += 3;
        }
    }

    private ByteBuffer encoded(ByteBuffer tmp,int numBands,int length) {
        ByteBuffer result = ByteBuffer.allocate(length * numBands + dim.height);
        encoder.encode(tmp, result);
        result.position(0);
        return result;
    }

}
