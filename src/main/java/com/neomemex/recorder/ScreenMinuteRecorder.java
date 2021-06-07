package com.neomemex.recorder;

import com.neomemex.shared.Screen;
import com.neomemex.shared.Sleep;
import com.neomemex.shared.Time;

import java.io.IOException;

final class ScreenMinuteRecorder {

    final ImageSequenceWriter writer;
    final Time endTime;
    final int sleepTime;

    ScreenMinuteRecorder(ImageSequenceWriter writer, Time endTime, int sleepTime) {
        this.writer = writer;
        this.endTime = endTime;
        this.sleepTime = sleepTime;
    }

    void writeScreenshots() throws IOException {
        while (endTime.inTheFuture()) {
            writer.writeImage(Screen.shot());
            Sleep.millis(sleepTime);
        }
        writer.close();
    }

}