package com.neomemex.viewer;

import com.neomemex.ocr.OCR;
import com.neomemex.shared.Highlight;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel
{
    OCR.Word[] words;
    final JLabel label = new JLabel();

    ImagePanel() {
        super(new GridLayout(1,1));
        add(label);
    }

    public void setImage(BufferedImage image) {
        label.setIcon(new ImageIcon(image.getScaledInstance(image.getWidth(),image.getHeight(),16)));
    }

    public void setWords(OCR.Word[] words) {
        this.words = words;
        System.out.println(words.length + " words");
        this.repaint();
    }

    public void setHighlight(Highlight highlight) {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintWordBoxes(g);
//        paintBorder(g);
//        paintComponent(g);
//        paintChildren(g);
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
