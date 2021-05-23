package com.curtcox.app;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

final class SimpleBufferedImageWriter implements BufferedImageWriter {

    private Image last;
    private final OutputStream out;

    SimpleBufferedImageWriter(OutputStream out) {
        this.out = out;
    }

    @Override
    public void writeImage(BufferedImage img) throws IOException {
        RasterSerializer serializer = new RasterSerializer(img);
        Image current = serializer.image();
        byte[] bytes = diff(last,current);
        out.write(bytes,0,bytes.length);
        last = current;
    }

    private byte[] diff(Image a, Image b) {
        return a == null ? b.bytes() : a.xor(b).bytes();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
