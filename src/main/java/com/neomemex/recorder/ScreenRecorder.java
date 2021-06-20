package com.neomemex.recorder;

import com.neomemex.store.FileTimeStreamMap;
import com.neomemex.shared.Sleep;
import com.neomemex.shared.Time;
import com.neomemex.store.TimeStreamMap;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ScreenRecorder implements Recorder {

    final AtomicBoolean recording;
    final TimeStreamMap map;
    final ExecutorService executor;
    private static final int sleepTime = 10;

    ScreenRecorder(AtomicBoolean recording, TimeStreamMap map, ExecutorService executor) {
        this.recording = recording;
        this.map = map;
        this.executor = executor;
    }

    public static Recorder startWaitingToRecord(TimeStreamMap map) {
        return startWaitingToRecord(new AtomicBoolean(),map, Executors.newSingleThreadExecutor());
    }

    public static Recorder startWaitingToRecord() {
        return startWaitingToRecord(new FileTimeStreamMap());
    }

    static Recorder startWaitingToRecord(AtomicBoolean recording, TimeStreamMap map, ExecutorService executor) {
        ScreenRecorder recorder = new ScreenRecorder(recording,map,executor);
        recorder.startWaiting();
        return recorder;
    }

    private void startWaiting() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (!executor.isShutdown()) {
                    if (recording.get()) {
                        recordScreenshots();
                    } else {
                        Sleep.millis(sleepTime);
                    }
                }
            }
        });
    }

    private void recordScreenshots() {
        try {
            minuteRecorder().writeScreenshots();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ScreenMinuteRecorder minuteRecorder() throws IOException {
        Time now = Time.now();
        return new ScreenMinuteRecorder(
                recording,
                SimpleImageSequenceWriter.to(map.output(now)),
                now.endOfThisMinute(),
                sleepTime);
    }

    @Override public void start() { recording.set(true); }
    @Override public void stop()  { recording.set(false); }

}
