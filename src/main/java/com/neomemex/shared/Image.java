package com.neomemex.shared;

import java.util.*;

public final class Image {

    public enum Color { ARGB, RGB }
    public enum Type { full, delta }

    private final int[] pixels;
    public final int size;
    public final int width;
    public final int height;
    public final Color color;
    public final Type type;

    public Image(int[] pixels, int width, int height) {
        this(Color.ARGB,Type.full,pixels,width,height);
    }

    public Image(Color color, Type type, int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.color = color;
        this.type = type;
        size = pixels.length;
    }

    public int[] pixels() { return Arrays.copyOf(pixels,pixels.length); }
    public byte[] bytes() { return Convert.toBytes(pixels); }
    public Image full()   { return type==Type.full ? this : new Image(color,Type.full,pixels,width,height);}
    public Image rgb()    {
        return color==Color.RGB ? this : new Image(Color.RGB,type,rgb(bytes()),width,height);
    }
    public Image argb()    {
        return color==Color.ARGB ? this : new Image(Color.ARGB,type,argb(bytes()),width,height);
    }
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

    private static int[] argb(byte[] in) {
        int offset = 0;
        byte[] out = new byte[in.length * 4/3];
        for (int i=0; i<in.length; i++) {
            if (i%3==0) {
                out[offset] = 0;
                offset++;
            }
            out[offset] = in[i];
            offset++;
        }
        return Convert.toInts(out);
    }

    // This logic trims away zeros at the top and bottom.
    // To actually be used it needs a corresponding undo operation.
    // Thus, the future might be a Image.Type of patch that stores 4 side-based offsets.
//    public Image trim() {
//        int firstNonZero = 0;
//        int[] in = pixels;
//        for (int i=firstNonZero; i<in.length; i++) {
//            if (in[i]!=0) {
//                firstNonZero = i;
//                break;
//            }
//        }
//        int lastNonZero = in.length - 1;
//        for (int i=lastNonZero; i>0; i--) {
//            if (in[i]!=0) {
//                lastNonZero = i;
//                break;
//            }
//        }
//        int size = lastNonZero - firstNonZero;
//        int[] out = new int[size];
//        for (int i=0; i<size; i++) {
//            out[i] = in[firstNonZero + i];
//        }
//        return new Image(color,type,out,width,height);
//    }

    private static int[] xor(int[] a, int[] b) {
        int[] c = new int[a.length];
        for (int i=0; i<a.length; i++) {
            c[i] = a[i] ^ b[i];
        }
        return c;
    }

    @Override
    public int hashCode() {
        return typeNumber() + (pixels.length << 2)+ (width << 12) + (height << 22);
    }

    private int typeNumber() {
        return (color == Color.ARGB ? 0 : 1) + (type==Type.full ? 0 : 2);
    }

    @Override
    public boolean equals(Object o) {
        Image that = (Image) o;
        return color == that.color &&
                type == that.type &&
                width == that.width &&
                height == that.height &&
                Arrays.equals(pixels,that.pixels);
    }

    @Override
    public String toString() {
        return color + " " + type + " " + width + "x" + height + " " + size;
    }
}
