package com.neomemex.viewer;

public class ScreenRecorderTest {

    public static void main(String[] args) throws Exception {
        FileTimeMap map = new FileTimeMap();
        new ScreenRecorder(map,Time.endOfThisMinute(),500)
                .record();
    }

}
