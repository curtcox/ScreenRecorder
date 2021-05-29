package com.curtcox.app;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenLogViewerTest {

    static void main0() {
        BufferedImage image = Screen.shot();
        double scale = 0.75;
        int width = (int) (image.getWidth() * scale);
        int height = (int) (image.getHeight() * scale);

        // Breaks the threading rules, in addition to being fake
        class FakeImageRequestor implements ImageRequestor {
            ImageDisplay display;
            @Override public void request(String text, int days, int minutes, int seconds) {
                display.setImage(Screen.shot());
            }
        }

        final FakeImageRequestor searcher = new FakeImageRequestor();
        final ScreenLogViewer viewer = new ScreenLogViewer(searcher,width,height);
        searcher.display = viewer;
        viewer.setImage(Screen.shot());
        viewer.show();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() { main0(); }
        });
    }

}
