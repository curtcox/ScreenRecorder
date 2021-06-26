package com.neomemex.recorder;

import com.neomemex.shared.Image;
import com.neomemex.shared.Time;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;

public final class RasterSerializer {

    private final Dimension dim;
    private final int numBands = 3;
    private final int[] dataElements;

    private RasterSerializer(BufferedImage image) {
        final WritableRaster raster = image.getRaster();
        if (raster.getNumBands() != numBands) {
            throw new IllegalArgumentException("Sorry, Dave.");
        }
        dim = dim(image);
        dataElements = dataElements(raster,dim);
    }

    public static Image serialize(BufferedImage image,Time time) {
        return new RasterSerializer(image).image(time);
    }

    public static ByteBuffer asByteBuffer(BufferedImage image) {
        return new RasterSerializer(image).asByteBuffer();
    }

    private static Dimension dim(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }
    
    ByteBuffer asByteBuffer() { return allButLast(threeOf4(byteBuffer(3))); }
    Image image(Time time) { return new Image(time,dataElements,dim.width, dim.height); }

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

}
