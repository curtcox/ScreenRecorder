package com.neomemex.recorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

final class PngImageEncoder {

    private final Dimension dim;
    private final Filter filter;
    private final ByteBuffer pixelBytes;

    PngImageEncoder(BufferedImage image, Filter filter) {
        this.dim     = dim(image);
        this.filter  = filter;
        pixelBytes   = RasterSerializer.asByteBuffer(image);
    }

    private static Dimension dim(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    ByteBuffer encode() {
        ByteBuffer result = ByteBuffer.allocate(bufferSize());
        filter.encode(pixelBytes, result);
        result.position(0);
        return result;
    }

    private int bufferSize() {
        return (dim.width * Consts.bands + 1) * dim.height ;
    }
}
