package com.neomemex.recorder;

import com.neomemex.shared.Image;

import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public final class SimpleImageSequenceWriter implements ImageSequenceWriter {

    private Image last;
    private final DataOutputStream data;

    public SimpleImageSequenceWriter(OutputStream out) {
        data = new DataOutputStream(out);
    }

    public static SimpleImageSequenceWriter to(OutputStream out) {
        return new SimpleImageSequenceWriter(
                new DeflaterOutputStream(out, new Deflater(Deflater.BEST_COMPRESSION), 5120));
    }


    @Override
    public void writeImage(BufferedImage img) {
        writeImage(RasterSerializer.serialize(img));
    }

    public void writeImage(Image raw) {
        Image image = raw.rgb();
        try {
            writeImage0(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        last = image;
    }

    private void writeImage0(Image image) throws IOException {
        writeType();
//        if (isFull()) {
            writeFull(image);
//        } else {
//            writeDelta(image);
//        }
    }

    private boolean isFull() { return last == null; }

    private void writeType() throws IOException {
        data.writeByte(isFull() ? 0 : 1);
    }

    private void writeFull(Image image) throws IOException {
        byte[] bytes = image.bytes();
        data.writeInt(image.size);
        data.writeShort(image.width);
        data.writeShort(image.height);
        data.write(bytes,0,bytes.length);
    }

//    private void writeDelta(Image image) throws IOException {
//        byte[] bytes = diff(last,image);
//        data.write(bytes,0,bytes.length);
//    }


//    private static byte[] diff(Image a, Image b) {
//        return a.xor(b).rgb().trim().bytes();
//    }

    @Override
    public void close() throws IOException {
        data.close();
    }

}
