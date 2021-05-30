package com.curtcox.app;

import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class ScreenRecorderTest {

    public static void main(String[] args) throws Exception {
        ImageSequenceWriter writer = new SimpleImageSequenceWriter(
                    new DeflaterOutputStream(
                            new MeteredOutputStream(new NullOutputStream()),
                            new Deflater(Deflater.BEST_COMPRESSION), 5120
                    ));

        new ScreenRecorder(writer,Time.endOfThisMinute(),500)
                .writeScreenshots();
    }

}
