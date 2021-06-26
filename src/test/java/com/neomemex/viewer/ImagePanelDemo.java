package com.neomemex.viewer;

import com.neomemex.DemoFrame;
import com.neomemex.ocr.TesseractOCR;
import com.neomemex.shared.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanelDemo {
    static void setContents(final DemoFrame demo, final BufferedImage image) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ImagePanel panel = new ImagePanel();
                panel.setImage(image);
                panel.setWords(TesseractOCR.instance.words(image));
                demo.frame.add(panel);
                demo.show();
            }
        });
    }

    public static void main(String[] args) throws Exception {
        DemoFrame original = DemoFrame.title("original");
        BufferedImage shot = Screen.shot();
        setContents(original,shot);
    }
}

