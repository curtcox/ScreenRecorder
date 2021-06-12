package com.neomemex.recorder;

import java.awt.image.BufferedImage;
import java.io.Closeable;

public interface ImageSequenceWriter extends Closeable {
    void writeImage(BufferedImage img);
}
