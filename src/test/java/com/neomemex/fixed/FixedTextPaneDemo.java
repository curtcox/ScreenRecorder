package com.neomemex.fixed;

import com.neomemex.ocr.OCR;
import com.neomemex.shared.Screen;

import javax.swing.*;
import javax.swing.text.BadLocationException;

final class FixedTextPaneDemo extends JFrame {

    FixedTextPaneDemo(OCR.Word[] words) {
        super("FixedTextPaneDemo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = Screen.width()/2; int height = Screen.height()/2;
        final FixedTextPane pane = FixedTextPane.of(width,height,words);
        add(pane);
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws BadLocationException {
        new FixedTextPaneDemo(new OCR.Word[] {
                new OCR.Word(0,0,100,100,"(0,0)"),
                new OCR.Word(100,100,100,100,"(100,100)"),
                new OCR.Word(200,200,100,100,"(200,200)"),
                new OCR.Word(400,400,100,100,"(400,400)"),
        }).setVisible(true);
    }

}
