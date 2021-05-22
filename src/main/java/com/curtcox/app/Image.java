package com.curtcox.app;

import java.util.Arrays;

final class Image {

    enum Color {
        RGB, red, green, blue
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
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        color = Color.RGB;
        type = Type.full;
    }

    int[] pixels() { return Arrays.copyOf(pixels,pixels.length); }

}
