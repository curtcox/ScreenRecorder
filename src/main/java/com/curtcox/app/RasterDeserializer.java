package com.curtcox.app;

import java.awt.image.*;

/**
 * After much frustration, copying code from Robot worked to reconstitute the pixel bytes from one BufferedImage
 * into another. This code was copied from Robot and refactored.
 */
final class RasterDeserializer {

    private static int[] bandmasks(DirectColorModel colorModel) {
        return new int[] { colorModel.getRedMask(), colorModel.getGreenMask(), colorModel.getBlueMask() };
    }

    private static WritableRaster raster(DirectColorModel colorModel,Image image) {
        return raster(colorModel,image.pixels(), image.width, image.height);
    }

    private static WritableRaster raster(DirectColorModel colorModel,int[] pixels, int width, int height) {
        return Raster.createPackedRaster(new DataBufferInt(pixels, pixels.length), width, height, width, bandmasks(colorModel), null);
    }

    static BufferedImage image(Image image) {
        DirectColorModel colorModel = image.colorModel;
        return new BufferedImage(colorModel, raster(colorModel,image), false, null);
    }

}
