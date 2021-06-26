package com.neomemex.viewer;

import com.neomemex.ocr.OCR;
import com.neomemex.shared.Highlight;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {

    private OCR.Word[] words;
    private BufferedImage image;

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
