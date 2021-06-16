package com.neomemex.recorder;

import com.neomemex.shared.Time;

import java.awt.image.BufferedImage;
import java.io.Closeable;

public interface ImageSequenceWriter extends Closeable {
    void writeImage(BufferedImage img, Time time);
}
