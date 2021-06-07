package com.neomemex.viewer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

final class SimpleImageSequenceWriter implements ImageSequenceWriter {

    private Image last;
    private final OutputStream out;

    SimpleImageSequenceWriter(OutputStream out) {
        this.out = out;
    }

    static ImageSequenceWriter to(File fileName) throws IOException {
        return new SimpleImageSequenceWriter(
                new DeflaterOutputStream(
                        new FileOutputStream(fileName),
                        new Deflater(Deflater.BEST_COMPRESSION), 5120
                ));
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
