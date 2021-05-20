package com.curtcox.app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

final class BufferedImageSerializer {

    private final Dimension dim;
    private final Encoder encoder;
    private final RasterSerializer raster;

    BufferedImageSerializer(BufferedImage image, Encoder encoder) {
        this.dim     = dim(image);
        this.encoder = encoder;
        raster       = new RasterSerializer(image);
    }

    private static Dimension dim(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    ByteBuffer encode() {
        ByteBuffer pixelBytes = raster.getPixelBytes();
        ByteBuffer result = ByteBuffer.allocate(bufferSize());
        encoder.encode(pixelBytes, result);
        result.position(0);
        return result;
    }

    private int bufferSize() {
        return (dim.width * Consts.bands + 1) * dim.height ;
    }
}
