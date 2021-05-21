package com.curtcox.app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;

final class RasterSerializer {

    private final Dimension dim;
    private final WritableRaster raster;
    private final int numBands = Consts.bands;

    RasterSerializer(BufferedImage image) {
        this.dim     = dim(image);
        raster       = image.getRaster();
        if (raster.getNumBands() != numBands) {
            throw new IllegalArgumentException("Sorry, Dave.");
        }
    }

    private static Dimension dim(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }
    
    ByteBuffer getPixelBytes() {
        return writeToBuffer(dataElements());
    }

    private int[] dataElements() {
        return (int[]) raster.getDataElements(0, 0, dim.width, dim.height, null);
    }

    private ByteBuffer writeToBuffer(int[] dataElements) {
        final int length = Array.getLength(dataElements);
        ByteBuffer out = ByteBuffer.allocate(length * numBands + 1);
        writeDataElements(dataElements,length,out);
        out.position(0);
        out.limit(out.limit() - 1);
        return out;
    }

    private void writeDataElements(int[] dataElements, int length,ByteBuffer out) {
        int index = 0;
        for (int i = 0; i < length; i++) {
            final int e = dataElements[i];
            out.putInt(index, e << 8);
            index += 3;
        }
    }

}
