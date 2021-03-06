package com.neomemex.recorder;

import com.neomemex.shared.Time;
import com.neomemex.stream.MeteredOutputStream;
import com.neomemex.stream.NullOutputStream;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class ScreenMinuteRecorderDemo {

    public static void main(String[] args) throws Exception {
        ImageSequenceWriter writer = new SimpleImageSequenceWriter(
                    new DeflaterOutputStream(
                            new MeteredOutputStream(new NullOutputStream()),
                            new Deflater(Deflater.BEST_COMPRESSION), 5120
                    ));

        new ScreenMinuteRecorder(new AtomicBoolean(true),writer, Time.endOfThisMinute(),500)
                .writeScreenshots();
    }

}
