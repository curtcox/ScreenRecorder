package com.curtcox.app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
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
        ByteBuffer out = ByteBuffer.allocate(dataElements.length * numBands + 1);
        writeDataElements(dataElements,out);
        out.position(0);
        out.limit(out.limit() - 1);
        return out;
    }

    private void writeDataElements(int[] dataElements,ByteBuffer out) {
        int index = 0;
        for (int i = 0; i < dataElements.length; i++) {
            out.putInt(index, dataElements[i] << 8);
            index += 3;
        }
    }

}
