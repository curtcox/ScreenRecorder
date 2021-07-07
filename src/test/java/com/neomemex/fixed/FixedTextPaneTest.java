package com.neomemex.fixed;

import com.neomemex.ocr.OCR;
import org.junit.Test;

import javax.swing.text.Utilities;

import static org.junit.Assert.*;

public class FixedTextPaneTest {

    @Test
    public void can_create() {
        assertNotNull(FixedTextPane.of(0,0,new OCR.Word[0]));
    }

    @Test
    public void getParagraphElement_does_not_throw_an_exception() {
        FixedTextPane pane = FixedTextPane.of(0,0,new OCR.Word[0]);
        Utilities.getParagraphElement(pane,0);
    }
}
