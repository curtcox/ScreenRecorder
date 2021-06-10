package com.neomemex.recorder;

import com.neomemex.shared.Screen;
import com.neomemex.shared.Sleep;
import com.neomemex.shared.Time;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

final class ScreenMinuteRecorder {

    final AtomicBoolean recording;
    final ImageSequenceWriter writer;
    final Time endTime;
    final int sleepTime;

    ScreenMinuteRecorder(AtomicBoolean recording, ImageSequenceWriter writer, Time endTime, int sleepTime) {
        this.recording = recording;
        this.writer = writer;
        this.endTime = endTime;
        this.sleepTime = sleepTime;
    }

    void writeScreenshots() throws IOException {
        while (endTime.inTheFuture()) {
            if (recording.get()) {
                writer.writeImage(Screen.shot());
            }
            Sleep.millis(sleepTime);
        }
        writer.close();
    }

}