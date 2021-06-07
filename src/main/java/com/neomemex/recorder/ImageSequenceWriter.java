package com.neomemex.recorder;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;

interface ImageSequenceWriter extends Closeable
{
    void writeImage(BufferedImage img) throws IOException;
}
