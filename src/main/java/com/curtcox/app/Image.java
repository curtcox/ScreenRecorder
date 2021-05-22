package com.curtcox.app;

import java.awt.image.DirectColorModel;
import java.util.Arrays;

final class Image {

    final DirectColorModel colorModel;

    //    private static DirectColorModel colorModel() {
//        return new DirectColorModel(24,
//                /* red mask */ 0x00FF0000,
//                /* green mask */ 0x0000FF00,
//                /* blue mask */ 0x000000FF);
//    }

    private final int[] pixels;

    final int width;

    final int height;

    Image(DirectColorModel colorModel, int[] pixels, int width, int height) {
        this.colorModel = colorModel;
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    int[] pixels() {
        return Arrays.copyOf(pixels,pixels.length);
    }
}
