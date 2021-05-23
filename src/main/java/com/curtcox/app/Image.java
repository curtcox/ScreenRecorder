package com.curtcox.app;

import java.util.Arrays;

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

    int[] pixels()        { return Arrays.copyOf(pixels,pixels.length); }
    byte[] bytes()        { return Convert.toBytes(pixels); }
    Image xor(Image that) { return new Image(color,Type.delta,xor(pixels,that.pixels),width,height); }
    Image banded()        { return new Image(Color.banded,type,banded(bytes()),width,height); }
    Image rgb()           { return new Image(Color.RGB,type,rgb(bytes()),width,height); }

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
        for (int i=0; i<in.length; i=i+1) {
            if (i%4!=0) {
                out[offset] = in[i];
                offset++;
            }
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
}
