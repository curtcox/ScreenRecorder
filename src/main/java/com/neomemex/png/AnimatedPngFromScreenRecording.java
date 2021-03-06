package com.neomemex.png;

import com.neomemex.shared.Screen;
import com.neomemex.shared.Sleep;
import com.neomemex.shared.Time;

import java.io.File;
import java.io.IOException;

final class AnimatedPngFromScreenRecording {

    final File fileName;
    final FilterNone filterNone = new FilterNone(Screen.width(),Screen.height());

    AnimatedPngFromScreenRecording(File fileName) {
        this.fileName = fileName;
    }

    private void writeScreenshots(int max) throws IOException {
        PngSequenceWriter writer = PngSequenceWriter.of(fileName, filterNone, max);
        for (int i = 0; i < max; i++) {
            writer.writeImage(Screen.shot(), Time.now());
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