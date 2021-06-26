package com.neomemex.viewer;

import com.neomemex.ocr.OCR;
import com.neomemex.ocr.TesseractOCR;
import com.neomemex.shared.Screen;
import com.neomemex.shared.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class ScreenLogViewerDemo {

    static void main0() {
        BufferedImage image = Screen.shot();
        double scale = 0.75;
        int width = (int) (image.getWidth() * scale);
        int height = (int) (image.getHeight() * scale);
        final OCR ocr = TesseractOCR.instance;
        final ArrayList<Time> times = new ArrayList<>();

        // Breaks the threading rules, in addition to being fake
        class FakeImageRequestor implements Viewer.Requestor {
            Viewer.Display display;
            @Override public void request(Viewer.Request request) {
                System.out.println(request);
                Time time = request.time;
                times.add(time);
                BufferedImage bufferedImage = Screen.shot();
                display.setImage(bufferedImage);
                display.setWords(ocr.words(bufferedImage));
                display.setTime(time,times.toArray(new Time[times.size()]));
            }
        }

        final FakeImageRequestor searcher = new FakeImageRequestor();
        final ScreenLogViewer viewer = new ScreenLogViewer(searcher,width,height);
        searcher.display = viewer;
        viewer.setImage(Screen.shot());
        viewer.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        viewer.show();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() { main0(); }
        });
    }


}
