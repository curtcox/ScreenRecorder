package com.curtcox.app;

import java.io.File;
import java.io.IOException;

public final class AnimatedPngFromScreenRecording {

    final File fileName;
    final FilterNone filterNone = new FilterNone(Screen.width(),Screen.height());

    AnimatedPngFromScreenRecording(File fileName) {
        this.fileName = fileName;
    }

    private void writeScreenshots(int max) throws IOException {
        PngSequenceWriter writer = new PngSequenceWriter(fileName, filterNone, max);
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
        new AnimatedPngFromScreenRecording(new File("screenshot.png"))
                .writeScreenshots(5);
    }

}