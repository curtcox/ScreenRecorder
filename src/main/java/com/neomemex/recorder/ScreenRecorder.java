package com.neomemex.recorder;

import com.neomemex.shared.FileTimeMap;
import com.neomemex.shared.Time;

import java.io.File;
import java.io.IOException;

final class ScreenRecorder {

    final FileTimeMap map;
    final Time endTime;
    final int sleepTime;

    ScreenRecorder(FileTimeMap map, Time endTime, int sleepTime) {
        this.map = map;
        this.endTime = endTime;
        this.sleepTime = sleepTime;
    }

    void record() throws IOException {
        while (endTime.inTheFuture()) {
            minuteRecorder().writeScreenshots();
        }
    }

    ScreenMinuteRecorder minuteRecorder() throws IOException {
        Time now = Time.now();
        File file = map.file(now);
        return new ScreenMinuteRecorder(SimpleImageSequenceWriter.to(file),now.endOfThisMinute(),sleepTime);
    }
}
