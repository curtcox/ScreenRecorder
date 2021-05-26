package com.curtcox.app;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

final class Image {

    enum Color {
        ARGB, RGB, banded
    }

    enum Type {
        full, delta
    }

    private final int[] pixels;

    final int width;

    final int height;

    final Color color;

    final Type type;

    Image(int[] pixels, int width, int height) {
        this(Color.ARGB,Type.full,pixels,width,height);
    }

    Image(Color color, Type type, int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.color = color;
        this.type = type;
    }

    int[] pixels() { return Arrays.copyOf(pixels,pixels.length); }
    byte[] bytes() { return Convert.toBytes(pixels); }
    Image banded() { return new Image(Color.banded,type,banded(bytes()),width,height); }
    Image rgb()    { return new Image(Color.RGB,type,rgb(bytes()),width,height); }
    Image sub()    { return new Image(color,Type.delta,sub(bytes()),width,height); }
    Image xor(Image that)   { return new Image(color,Type.delta,xor(pixels,that.pixels),width,height); }
    Image minus(Image that) { return new Image(color,Type.delta,minus(bytes(),that.bytes()),width,height); }


    private static int[] banded(byte[] in) {
        int offset = 0;
        byte[] out = new byte[in.length * 3/4];
        offset = band(in,out,offset,1);
        offset = band(in,out,offset,2);
                 band(in,out,offset,3);
        return Convert.toInts(out);
    }

    private static int band(byte[] in, byte[] out, int start, int n) {
        int offset = start;
        for (int i=n; i<in.length; i=i+4) {
            out[offset] = in[i];
            offset++;
        }
        return offset;
    }

    private static int[] rgb(byte[] in) {
        int offset = 0;
        byte[] out = new byte[in.length * 3/4];
        for (int i=0; i<in.length; i++) {
            if (i%4!=0) {
                out[offset] = in[i];
                offset++;
            }
        }
        return Convert.toInts(out);
    }

    Image trim() {
        int firstNonZero = 0;
        int[] in = pixels;
        for (int i=firstNonZero; i<in.length; i++) {
            if (in[i]!=0) {
                firstNonZero = i;
                break;
            }
        }
        int lastNonZero = in.length - 1;
        for (int i=lastNonZero; i>0; i--) {
            if (in[i]!=0) {
                lastNonZero = i;
                break;
            }
        }
        int size = lastNonZero - firstNonZero;
        int[] out = new int[size];
        for (int i=0; i<size; i++) {
            out[i] = in[firstNonZero + i];
        }
        print((double) size / (double) in.length);
        countZeros(out);
        return new Image(color,type,out,width,height);
    }

    private static void countZeros(int[] in) {
        int count = 0;
        int total = 0;
        for (int i=0; i<in.length; i++) {
            if (in[i]==0) {
                count++;
                total++;
            } else {
                //if (count>0)
                //  print("ended at " + count);
                count = 0;
            }
        }
        print(total + "/" + in.length + " " + ((double)total/(double) in.length) + " zero " + (in.length - total) + " non zero");
    }

    private static void countZeros(byte[] in) {
        int count = 0;
        int total = 0;
        for (int i=0; i<in.length; i++) {
            if (in[i]==0) {
                count++;
                total++;
            } else {
                //if (count>0)
                //  print("ended at " + count);
                count = 0;
            }
        }
        print(total + "/" + in.length + " " + ((double)total/(double) in.length) + " zero " + (in.length - total) + " non zero");
    }

    Image palette() {
        try {
            return palette0();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Image palette0() throws IOException {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i=0; i<pixels.length; i++) {
            int key = pixels[i];
            if (!map.containsKey(key)) {
                map.put(key,map.size());
            }
        }
        if (map.size()>256) {
            return this;
        }
        print("palette " + map.size());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(out);
        data.write(map.size());
        for (Integer key: map.keySet()) {
            data.writeInt(key);
        }
        for (int i=0; i<pixels.length; i++) {
            int key = pixels[i];
            out.write(map.get(key));
        }
        data.close();
        return new Image(Color.RGB,type,Convert.toInts(out.toByteArray()),width,height);
    }

    Image paletteSize() {
        Set<Integer> set = new HashSet<>();
        for (int i=0; i<pixels.length; i++) {
            set.add(pixels[i]);
        }
        print("palette " + set.size());
        return this;
    }

    private static int[] sub(byte[] in) {
        int offset = 0;
        byte[] out = new byte[in.length];
        for (int i=1; i<in.length; i=i+1) {
            out[offset] = (byte) (in[i] - in[i-1]);
            offset++;
        }
        return Convert.toInts(out);
    }

    private static int[] xor(int[] a, int[] b) {
        int[] c = new int[a.length];
        for (int i=0; i<a.length; i++) {
            c[i] = a[i] ^ b[i];
        }
        return c;
    }

    private static int[] minus(byte[] a, byte[] b) {
        byte[] c = new byte[a.length];
        for (int i=0; i<a.length; i++) {
            c[i] = (byte) (a[i] - b[i]);
        }
        return Convert.toInts(c);
    }

    private static void print(Object o) {
        System.out.println(o);
    }
}
