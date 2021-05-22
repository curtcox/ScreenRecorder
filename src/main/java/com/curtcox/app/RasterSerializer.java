package com.curtcox.app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.util.Arrays;

final class RasterSerializer {

    private final int numBands = Consts.bands;
    private final int[] dataElements;

    RasterSerializer(BufferedImage image) {
        final WritableRaster raster = image.getRaster();
        if (raster.getNumBands() != numBands) {
            throw new IllegalArgumentException("Sorry, Dave.");
        }
        dataElements = dataElements(raster,dim(image));
    }

    private static void print(Object o) {
        System.out.println(o);
    }

    private static Dimension dim(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }
    
    ByteBuffer asByteBuffer() { return allButLast(threeOf4(byteBuffer(3))); }
    int[]              data() { return Arrays.copyOf(dataElements,dataElements.length); }

    private static int[] dataElements(WritableRaster raster,Dimension dim) {
        return (int[]) raster.getDataElements(0, 0, dim.width, dim.height, null);
    }

    private ByteBuffer allButLast(ByteBuffer out) {
        out.position(0);
        out.limit(out.limit() - 1);
        return out;
    }

    private ByteBuffer byteBuffer(int colors) {
        return ByteBuffer.allocate(dataElements.length * colors + 1);
    }

    private ByteBuffer threeOf4(ByteBuffer out) {
        int index = 0;
        for (int i = 0; i < dataElements.length; i++) {
            out.putInt(index, dataElements[i] << 8);
            index += 3;
        }
        return out;
    }

    private ByteBuffer firstOf4(ByteBuffer out) {
        int index = 0;
        for (int i = 0; i < dataElements.length; i++) {
            out.putInt(index, dataElements[i] << 8);
            index += 1;
        }
        return out;
    }

}