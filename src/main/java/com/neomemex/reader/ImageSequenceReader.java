package com.neomemex.reader;

import com.neomemex.shared.Convert;
import com.neomemex.shared.Image;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

public final class ImageSequenceReader {

    private Image last;
    final DataInputStream data;

    public ImageSequenceReader(InputStream input) {
        data = new DataInputStream(input);
    }

    public static ImageSequenceReader from(InputStream input) {
        return new ImageSequenceReader(new InflaterInputStream(input));
    }

    public Image read() {
        try {
            last = read0();
            return last;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Image read0() throws IOException {
        Image.Type type = readType();
        if (Image.Type.full==type) {
            return readFull();
        } else {
            Image delta = readDelta();
            return last.xor(delta).full();
        }
    }

    private Image.Type readType() throws IOException {
        int   kind = data.readByte();
        if (kind!=0 && kind!=1) {
            throw new IllegalArgumentException();
        }
        return kind == 0 ? Image.Type.full : Image.Type.delta;
    }

    private Image readFull() throws IOException {
        int   size = data.readInt();
        int  width = data.readShort();
        int height = data.readShort();
        byte[] bytes = new byte[size * 4];
        data.readFully(bytes);
        Image rgb = new Image(
                Image.Color.RGB,Image.Type.full,
                Convert.toInts(bytes),width,height);
        return rgb.argb();
    }

    private Image readDelta() throws IOException {
        int   size = data.readInt();
        int  width = data.readShort();
        int height = data.readShort();
        byte[] bytes = new byte[size * 4];
        data.readFully(bytes);
        Image rgb = new Image(
                Image.Color.RGB, Image.Type.delta,
                Convert.toInts(bytes),width,height);
        return rgb.argb();
    }

}
