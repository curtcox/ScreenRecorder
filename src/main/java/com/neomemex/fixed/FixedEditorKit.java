package com.neomemex.fixed;

import com.neomemex.ocr.OCR;

import javax.swing.text.*;

final class FixedEditorKit extends DefaultEditorKit {

    final int width;
    final int height;
    final OCR.Word[] words;

    FixedEditorKit(int width, int height,OCR.Word[] words) {
        this.width = width;
        this.height = height;
        this.words = words;
    }

    public ViewFactory getViewFactory() {
        return new ViewFactory() {
            @Override public View create(Element elem) {
                return new FixedView(elem,width,height,words);
            }
        };
    }

    public Document createDefaultDocument() {
        return FixedDocument.of(words);
    }
}
