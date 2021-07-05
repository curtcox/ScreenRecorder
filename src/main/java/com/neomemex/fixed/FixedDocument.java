package com.neomemex.fixed;

import com.neomemex.ocr.OCR;

import javax.swing.text.*;

final class FixedDocument extends PlainDocument {

    final OCR.Word[] words;

    private FixedDocument(OCR.Word[] words){
        this.words = words;
    }

    static FixedDocument of(OCR.Word[] words) {
        FixedDocument document = new FixedDocument(words);
        document.insertWords();
        return document;
    }

    private void insertWords() {
        try {
            insertWords0();
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertWords0() throws BadLocationException {
        for (OCR.Word word : words) {
            insertString(0,word.text,null);
        }
    }
}