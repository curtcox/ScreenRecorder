package com.neomemex.reader;

import com.neomemex.shared.Convert;
import com.neomemex.shared.Image;
import com.neomemex.shared.RuntimeIOException;
import com.neomemex.shared.Time;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.InflaterInputStream;

final class ImageSequenceReader {

    private Image last;
    final DataInputStream data;

    ImageSequenceReader(InputStream input) {
        data = new DataInputStream(input);
    }

    static ImageSequenceReader from(InputStream input) {
        return new ImageSequenceReader(new InflaterInputStream(input));
    }

    static SortedMap<Time,Image> allImagesFrom(InputStream input) {
        return from(input).readAllImages();
    }

    Image read() {
        try {
            last = read0();
            return last;
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    private boolean moreImages() {
        try {
            System.out.println(data.available() + " available");
            return data.available() > 0;
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
            return last.xor(delta).full().time(delta.time);
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
        Time time = new Time(data.readLong());
        int   size = data.readInt();
        int  width = data.readShort();
        int height = data.readShort();
        byte[] bytes = new byte[size * 4];
        data.readFully(bytes);
        Image rgb = new Image(time,
                Image.Color.RGB,Image.Type.full,
                Convert.toInts(bytes),width,height);
        return rgb.argb();
    }

    private Image readDelta() throws IOException {
        int   diff = data.readShort();
        System.out.println("read diff = " + diff);
        Time  time = new Time(last.time.t + diff);
        int   size = data.readInt();
        int  width = data.readShort();
        int height = data.readShort();
        byte[] bytes = new byte[size * 4];
        data.readFully(bytes);
        Image rgb = new Image(time,
                Image.Color.RGB, Image.Type.delta,
                Convert.toInts(bytes),width,height);
        System.out.println("read rgb = " + rgb);
        return rgb.argb();
    }

    private SortedMap<Time,Image> readAllImages() {
        SortedMap<Time,Image> images = new TreeMap<>();
        try {
            for (;;) {
                Image image = read();
                System.out.println("ISR read " + image);
                images.put(image.time, image);
            }
        } catch (RuntimeIOException e) {
            e.printStackTrace();
        }
        System.out.println("Read images " + images);
        return images;
    }

}
