package com.curtcox.app;

import java.io.File;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public final class ScreenRecording {

    final File fileName;
    final BufferedImageWriter writer;

    ScreenRecording(File fileName) {
        this.fileName = fileName;
//        writer = new SimpleBufferedImageWriter(new MeteredOutputStream(new NullOutputStream())); // 600 Megs per minute
//        writer = new SimpleBufferedImageWriter(new DeflaterOutputStream(new MeteredOutputStream(new NullOutputStream()))); // 15 mpm
        // buffer size and compression appear to have relatively little impact (10 - 13 mpm) no matter what
        writer = new SimpleBufferedImageWriter(
                new DeflaterOutputStream(
                        new MeteredOutputStream(new NullOutputStream()),
                        new Deflater(Deflater.BEST_COMPRESSION), 5120
                ));
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