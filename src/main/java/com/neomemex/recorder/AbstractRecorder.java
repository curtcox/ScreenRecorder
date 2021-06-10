package com.neomemex.recorder;

import java.util.concurrent.atomic.AtomicBoolean;

abstract class AbstractRecorder implements Recorder {

    final AtomicBoolean recording = new AtomicBoolean();

    @Override public void start() { recording.set(true); }
    @Override public void stop()  { recording.set(false); }

}
