package com.curtcox.app;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;

interface BufferedImageWriter extends Closeable
{
    public void writeImage(BufferedImage img) throws IOException;
}
