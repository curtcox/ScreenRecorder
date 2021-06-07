package com.neomemex.viewer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenLogViewerTest {

    static void main0() {
        BufferedImage image = Screen.shot();
        double scale = 0.75;
        int width = (int) (image.getWidth() * scale);
        int height = (int) (image.getHeight() * scale);

        // Breaks the threading rules, in addition to being fake
        class FakeImageRequestor implements Viewer.Requestor {
            Viewer.Display display;
            @Override public void request(Viewer.Request request) {
                System.out.println(request);
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
