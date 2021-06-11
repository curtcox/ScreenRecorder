package com.neomemex.recorder;

import com.neomemex.shared.Sleep;

public class ScreenRecorderTest {

    public static void main(String[] args) {
        Recorder recorder = ScreenRecorder.startWaitingToRecord();
        recorder.start();
        Sleep.second(60);
        recorder.stop();
        System.exit(0);
    }

}
