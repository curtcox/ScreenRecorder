package com.neomemex.ocr;

import java.io.InputStream;

/**
 * A potential way of doing OCR is via wrapping VNRecognizeTextRequest
 * JNA is probably faster. ProcessBuilder is probably easier.
 * See https://github.com/curtcox/macOCR
 */
public final class VNRecognizeTextRequestOCR implements OCR {
    public String process(InputStream input) {
        return null;
    }
}
