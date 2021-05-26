package com.curtcox.app;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

final class SimpleImageSequenceWriter implements ImageSequenceWriter {

    private Image last;
    private final OutputStream out;

    SimpleImageSequenceWriter(OutputStream out) {
        this.out = out;
    }

    @Override
    public void writeImage(BufferedImage img) throws IOException {
        RasterSerializer serializer = new RasterSerializer(img);
        Image current = serializer.image();
        byte[] bytes = diff(last,current);
        //print(bytes.length + " bytes -> " + Compressor.compress(bytes).length);
        out.write(bytes,0,bytes.length);
        last = current;
    }

    private byte[] diff(Image a, Image b) {
        return a == null ? b.bytes() : a.xor(b).rgb().trim().bytes();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }

    private static void print(Object o) {
        System.out.println(o);
    }
}
