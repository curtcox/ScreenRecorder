package com.curtcox.app;

import java.util.Arrays;

final class Image {

    private final int[] pixels;

    final int width;

    final int height;

    Image(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    int[] pixels() {
        return Arrays.copyOf(pixels,pixels.length);
    }
}
