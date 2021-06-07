package com.neomemex.shared;

import java.util.*;

public final class Image {

    enum Color {
        ARGB, RGB
    }

    enum Type {
        full, delta
    }

    private final int[] pixels;

    public final int width;

    public final int height;

    public final Color color;

    public final Type type;

    public Image(int[] pixels, int width, int height) {
        this(Color.ARGB,Type.full,pixels,width,height);
    }

    Image(Color color, Type type, int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.color = color;
        this.type = type;
    }

    public int[] pixels() { return Arrays.copyOf(pixels,pixels.length); }
    public byte[] bytes() { return Convert.toBytes(pixels); }
    public Image rgb()    { return new Image(Color.RGB,type,rgb(bytes()),width,height); }
    public Image xor(Image that)   { return new Image(color,Type.delta,xor(pixels,that.pixels),width,height); }

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

    public Image trim() {
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
        return new Image(color,type,out,width,height);
    }

    private static int[] xor(int[] a, int[] b) {
        int[] c = new int[a.length];
        for (int i=0; i<a.length; i++) {
            c[i] = a[i] ^ b[i];
        }
        return c;
    }

    private static void print(Object o) {
        System.out.println(o);
    }
}
