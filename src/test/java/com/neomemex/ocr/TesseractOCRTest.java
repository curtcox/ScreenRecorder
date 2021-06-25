package com.neomemex.ocr;

import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TesseractOCRTest {

    OCR ocr = new TesseractOCR();

    @Test
    public void samples_exist() {
        assertNotNull(input("/browserSample1.png"));
        assertNotNull(input("/terminalSample1.png"));
    }

    @Test
    public void browserSample1_text() {
        String text = ocr.text(input("/browserSample1.png"));

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
    public void browserSample1_words() {
        OCR.Word[] words = ocr.words(input("/browserSample1.png"));
        assertContains(words,new OCR.Word(123,521,128,19,"tesseract"));
        assertContains(words,new OCR.Word(474,339,58,21,"OCR"));
        assertContains(words,new OCR.Word(1618,700,87,27,"engine"));
    }

    @Test
    public void terminalSample1_text() {
        String text = ocr.text(input("/terminalSample1.png"));

        assertContains(text,"109x28");
        assertContains(text,"git gui &");
        assertContains(text,"Error: Cask 'araxis-merge' definition is invalid: invalid 'depends_on macos' value: unknown or unsupported");
        assertContains(text,"Upgrading 11 outdated packages");
        assertContains(text,"ScreenRecorder");
    }

    void assertContains(String full,String part) {
        assertTrue(full,full.contains(part));
    }
    void assertContains(OCR.Word[] words, OCR.Word word) {
        List<OCR.Word> list = Arrays.asList(words);
        assertTrue(list.toString(),list.contains(word));
    }

    InputStream input(String name) {
        return TesseractOCRTest.class.getResourceAsStream(name);
    }
}
