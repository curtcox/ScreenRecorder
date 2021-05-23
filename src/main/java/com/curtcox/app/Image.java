package com.curtcox.app;

import java.util.Arrays;

final class Image {

    enum Color {
        ARGB, red, green, blue
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

    Image xor(Image that) {
        return new Image(color,Type.delta,xor(pixels,that.pixels),width,height);
    }

    private static int[] xor(int[] a, int[] b) {
        int[] c = new int[a.length];
        for (int i=0; i<a.length; i++) {
            c[i] = a[i] ^ b[i];
        }
        return c;
    }
}
