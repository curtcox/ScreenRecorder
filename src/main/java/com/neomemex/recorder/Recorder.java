package com.neomemex.recorder;

/**
 * These methods may be invoked from any thread and should return immediately.
 */
public interface Recorder {
    void start();
    void stop();
}
