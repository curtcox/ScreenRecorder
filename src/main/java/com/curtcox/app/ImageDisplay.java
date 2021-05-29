package com.curtcox.app;

import java.awt.image.BufferedImage;

interface ImageDisplay {
    /**
     * Invokers must be on the EDT.
     */
    void setImage(BufferedImage image);
}
