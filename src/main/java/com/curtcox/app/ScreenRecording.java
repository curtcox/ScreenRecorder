package com.curtcox.app;

import java.io.File;
import java.io.IOException;

public final class ScreenRecording {

    final File fileName;
    final BufferedImageWriter writer;

    ScreenRecording(File fileName) {
        this.fileName = fileName;
        writer = new SimpleBufferedImageWriter();
    }

    private void writeScreenshots(int max) throws IOException {
        for (int i = 0; i < max; i++) {
            writer.writeImage(Screen.shot());
            print(i);
            Sleep.millis(500);
        }
        writer.close();
    }

    private static void print(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) throws Exception {
        new ScreenRecording(new File("screenshot.png"))
                .writeScreenshots(5);
    }

}