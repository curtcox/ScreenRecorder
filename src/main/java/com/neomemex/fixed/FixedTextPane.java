package com.neomemex.fixed;

import com.neomemex.ocr.OCR;
import com.neomemex.shared.Screen;

import javax.swing.*;

/**
 * For selecting and copying the existing text identified in screenshots.
 */
public final class FixedTextPane
    extends JEditorPane
{

    final int width;
    final int height;
    final OCR.Word[] words;

    private FixedTextPane(int width, int height, OCR.Word[] words) {
        this.width = width;
        this.height = height;
        this.words = words;
    }

    public static FixedTextPane of(int width, int height, OCR.Word[] words) {
        FixedTextPane pane = new FixedTextPane(width,height,words);
        pane.setEditorKit(new FixedEditorKit(pane,Screen.width(),Screen.height(),words));
        pane.setEditable(false);
        return pane;
    }
}
