package com.neomemex.ocr;

import java.io.InputStream;

/**
 * We might eventually create a custom engine.
 */
public interface OCR {
    String process(InputStream input);
}
