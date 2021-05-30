package com.curtcox.app;

import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class ScreenMinuteRecorderTest {

    public static void main(String[] args) throws Exception {
        ImageSequenceWriter writer = new SimpleImageSequenceWriter(
                    new DeflaterOutputStream(
                            new MeteredOutputStream(new NullOutputStream()),
                            new Deflater(Deflater.BEST_COMPRESSION), 5120
                    ));

        new ScreenMinuteRecorder(writer,Time.endOfThisMinute(),500)
                .writeScreenshots();
    }

}
