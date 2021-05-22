package com.curtcox.app;

import java.awt.image.*;

/**
 * After much frustration, copying code from Robot worked to reconstitute the pixel bytes from one BufferedImage
 * into another. This code was copied from Robot and refactored.
 */
final class RasterDeserializer {

    private static DirectColorModel colorModel() {
        return new DirectColorModel(24,
                /* red mask */ 0x00FF0000,
                /* green mask */ 0x0000FF00,
                /* blue mask */ 0x000000FF);
    }

    private static int[] bandmasks(DirectColorModel colorModel) {
        int[] bandmasks = new int[3];
        bandmasks[0] = colorModel.getRedMask();
        bandmasks[1] = colorModel.getGreenMask();
        bandmasks[2] = colorModel.getBlueMask();
        return bandmasks;
    }

    private static WritableRaster raster(DirectColorModel colorModel,Image image) {
        return raster(colorModel,image.pixels(), image.width, image.height);
    }

    private static WritableRaster raster(DirectColorModel colorModel,int[] pixels, int width, int height) {
        return Raster.createPackedRaster(new DataBufferInt(pixels, pixels.length), width, height, width, bandmasks(colorModel), null);
    }

    static BufferedImage image(Image image) {
        DirectColorModel colorModel = colorModel();
        return new BufferedImage(colorModel, raster(colorModel,image), false, null);
    }

}
