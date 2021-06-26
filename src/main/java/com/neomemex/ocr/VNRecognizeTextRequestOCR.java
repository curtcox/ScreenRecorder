package com.neomemex.ocr;

import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * A potential way of doing OCR is via wrapping VNRecognizeTextRequest
 * JNA is probably faster. ProcessBuilder is probably easier.
 * See https://github.com/curtcox/macOCR
 */
public final class VNRecognizeTextRequestOCR implements OCR {
    @Override public String text(InputStream input) {
        return null;
    }
    @Override public Word[] words(InputStream input) { return null; }
    @Override public Word[] words(BufferedImage image) { return null; }
}
