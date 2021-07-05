package com.neomemex.viewer;

import com.neomemex.ocr.OCR;
import com.neomemex.shared.Highlight;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * For displaying previously captured images.
 * In addition to the image, the panel has a notion of what words are within the image and where they are.
 * My current effort is to enable copy and paste of those words. Two potential ways of doing this both seem hard.
 * - directly adding the required text selection and copying support to the component
 * - forcing a JTextComponent with text to lay it out in a way that matches the given words
 */
final class ImagePanel extends JPanel {

    private OCR.Word[] words;
    private BufferedImage image;

    private ImagePanel() {}

    static ImagePanel of() {
        ImagePanel panel = new ImagePanel();
        panel.setActionMappings();
        panel.setInputMappings();
        return panel;
    }

    private void setActionMappings() {
        ActionMap map = getActionMap();
        map.put(TransferHandler.getCutAction().getValue(Action.NAME), TransferHandler.getCutAction());
        map.put(TransferHandler.getCopyAction().getValue(Action.NAME), TransferHandler.getCopyAction());
        map.put(TransferHandler.getPasteAction().getValue(Action.NAME), TransferHandler.getPasteAction());
    }

    private void setInputMappings() {
        InputMap imap = getInputMap();
        imap.put(KeyStroke.getKeyStroke("ctrl X"), TransferHandler.getCutAction().getValue(Action.NAME));
        imap.put(KeyStroke.getKeyStroke("ctrl C"), TransferHandler.getCopyAction().getValue(Action.NAME));
        imap.put(KeyStroke.getKeyStroke("ctrl V"), TransferHandler.getPasteAction().getValue(Action.NAME));
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setWords(OCR.Word[] words) {
        this.words = words;
    }

    public void setHighlight(Highlight highlight) {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintImage((Graphics2D) g);
        paintWordBoxes(g);
    }

    private void paintImage(Graphics2D g) {
        if (image!=null) {
            g.drawImage(image,null,null);
        }
    }

    private void paintWordBoxes(Graphics g) {
        g.setColor(Color.RED);
        for (OCR.Word word : words) {
            paintWordBox(g,word);
        }
    }

    private void paintWordBox(Graphics g,OCR.Word word) {
        g.drawRect(word.left,word.top,word.width,word.height);
    }
}
