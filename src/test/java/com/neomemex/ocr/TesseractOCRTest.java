package com.neomemex.ocr;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class TesseractOCRTest {

    OCR ocr = new TesseractOCR();

    @Test
    public void samples_exist() {
        assertNotNull(input("/browserSample1.png"));
        assertNotNull(input("/terminalSample1.png"));
    }

    @Test
    public void browserSample1() {
        String text = ocr.process(input("/browserSample1.png"));

        assertContains(text,"OCR");
        assertContains(text,"github.com/tesseract-ocr/tesseract/blob/master/doc/tesseract.1.asc");
        assertContains(text,"tesseract - command-line OCR engine");
        assertContains(text,"tesseract(1) is a commercial quality OCR engine originally developed at HP between 1985 and 1995.");
        assertContains(text,"It was open-sourced by HP and UNLV in 2005,");
        assertContains(text,"The name of the input file. This can either be an image file or a text file.");
        assertContains(text,"If FILE is stdin or — then the standard input is used.");
        assertContains(text,"The basename of the output file (to which the appropriate extension will be appended).");
        assertContains(text,"If OUTPUTBASE is stdout or - then the standard output is used.");
    }

    @Test
    public void terminalSample1() {
        String text = ocr.process(input("/terminalSample1.png"));

        assertContains(text,"109x28");
        assertContains(text,"git gui &");
        assertContains(text,"Error: Cask 'araxis-merge' definition is invalid: invalid 'depends_on macos' value: unknown or unsupported");
        assertContains(text,"Upgrading 11 outdated packages");
        assertContains(text,"ScreenRecorder");
    }

    void assertContains(String full,String part) {
        assertTrue(full,full.contains(part));
    }

    InputStream input(String name) {
        return TesseractOCRTest.class.getResourceAsStream(name);
    }
}
