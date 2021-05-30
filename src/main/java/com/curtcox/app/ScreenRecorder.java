package com.curtcox.app;

import java.io.File;
import java.io.IOException;

final class ScreenRecorder {

    final ImageSequenceWriter writer;
    final Time endTime;
    final int sleepTime;

    ScreenRecorder(ImageSequenceWriter writer, Time endTime, int sleepTime) {
        this.writer = writer;
        this.endTime = endTime;
        this.sleepTime = sleepTime;
    }

    ScreenRecorder(File fileName) throws IOException {
        this(SimpleImageSequenceWriter.to(fileName),Time.endOfThisMinute(),500);
    }

    void writeScreenshots() throws IOException {
        while (endTime.inTheFuture()) {
            writer.writeImage(Screen.shot());
            Sleep.millis(sleepTime);
        }
        writer.close();
    }

}