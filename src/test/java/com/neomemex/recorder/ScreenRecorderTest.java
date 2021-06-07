package com.neomemex.recorder;

import com.neomemex.recorder.ScreenRecorder;
import com.neomemex.shared.FileTimeMap;
import com.neomemex.shared.Time;

public class ScreenRecorderTest {

    public static void main(String[] args) throws Exception {
        FileTimeMap map = new FileTimeMap();
        new ScreenRecorder(map, Time.endOfThisMinute(),500)
                .record();
    }

}
