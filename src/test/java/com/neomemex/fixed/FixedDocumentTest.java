package com.neomemex.fixed;

import com.neomemex.ocr.OCR;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class FixedDocumentTest {

    @Test
    public void can_create() {
        assertNotNull(FixedDocument.of(new OCR.Word[0]));
    }

    @Test
    public void getDefaultRootElement() {
        FixedDocument document = FixedDocument.of(new OCR.Word[0]);
        assertNotNull(document.getDefaultRootElement());
    }

}
