package com.neomemex.fixed;

import com.neomemex.ocr.OCR;

import javax.swing.text.*;

final class FixedEditorKit extends DefaultEditorKit {

    final int width;
    final int height;
    final OCR.Word[] words;
    final FixedTextPane pane;

    FixedEditorKit(FixedTextPane pane,int width, int height,OCR.Word[] words) {
        this.pane = pane;
        this.width = width;
        this.height = height;
        this.words = words;
    }

    public ViewFactory getViewFactory() {
        return new ViewFactory() {
            @Override public View create(Element elem) {
                FixedView view = new FixedView(elem,width,height,words);
                pane.addCaretListener(view);
                return  view;
            }
        };
    }

    public Document createDefaultDocument() {
        return FixedDocument.of(words);
    }
}
