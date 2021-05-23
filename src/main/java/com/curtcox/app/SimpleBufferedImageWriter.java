package com.curtcox.app;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

final class SimpleBufferedImageWriter implements BufferedImageWriter {

    final OutputStream out;

    SimpleBufferedImageWriter(OutputStream out) {
        this.out = out;
    }

    @Override
    public void writeImage(BufferedImage img) throws IOException {
        RasterSerializer serializer = new RasterSerializer(img);
        Image image = serializer.image();
        byte[] bytes = Convert.toBytes(image.pixels());
        out.write(bytes,0,bytes.length);
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
