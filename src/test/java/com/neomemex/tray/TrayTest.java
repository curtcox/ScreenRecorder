package com.neomemex.tray;

import com.neomemex.recorder.Recorder;

import java.awt.*;

public class TrayTest {

    static class FakeRecorder implements Recorder {
        @Override
        public void start() {
            System.out.println("Started Recording");
        }

        @Override
        public void stop() {

        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Tray(new FakeRecorder()).install();
                } catch (AWTException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
