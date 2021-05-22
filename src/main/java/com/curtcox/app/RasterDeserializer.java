package com.curtcox.app;

import java.awt.image.*;

/**
 * After much frustration, copying code from Robot worked to reconstitute the pixel bytes from one BufferedImage
 * into another. This code was copied from Robot and refactored.
 */
final class RasterDeserializer {

    private static final int   redMask = 0x00FF0000;
    private static final int greenMask = 0x0000FF00;
    private static final int  blueMask = 0x000000FF;
    // Robot uses this:
    private static final DirectColorModel colorModel = new DirectColorModel(24, redMask, greenMask, blueMask);

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
        return new BufferedImage(colorModel, raster(colorModel,image), false, null);
    }

}
