package com.neomemex.recorder;

import com.neomemex.shared.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public final class SimpleImageSequenceWriter implements ImageSequenceWriter {

    private Image last;
    private final OutputStream out;

    public SimpleImageSequenceWriter(OutputStream out) {
        this.out = out;
    }

    static ImageSequenceWriter to(OutputStream out) {
        return new SimpleImageSequenceWriter(
                new DeflaterOutputStream(out, new Deflater(Deflater.BEST_COMPRESSION), 5120));
    }


    @Override
    public void writeImage(BufferedImage img) throws IOException {
        writeImage(RasterSerializer.serialize(img));
    }

    public void writeImage(Image image) throws IOException {
        byte[] bytes = diff(last,image);
        //print(bytes.length + " bytes -> " + Compressor.compress(bytes).length);
        out.write(bytes,0,bytes.length);
        last = image;
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
