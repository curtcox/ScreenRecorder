package com.curtcox.app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;

final class RasterSerializer {

    private final Dimension dim;
    private final DirectColorModel colorModel;
    private final int numBands = Consts.bands;
    private final int[] dataElements;

    RasterSerializer(BufferedImage image) {
        final WritableRaster raster = image.getRaster();
        if (raster.getNumBands() != numBands) {
            throw new IllegalArgumentException("Sorry, Dave.");
        }
        dim = dim(image);
        colorModel = (DirectColorModel) image.getColorModel();
        dataElements = dataElements(raster,dim);
    }

    private static Dimension dim(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }
    
    ByteBuffer asByteBuffer() { return allButLast(threeOf4(byteBuffer(3))); }
    Image             image() { return new Image(colorModel,dataElements,dim.width, dim.height); }

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
