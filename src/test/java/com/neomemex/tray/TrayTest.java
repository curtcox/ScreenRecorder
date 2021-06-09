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
            System.out.println("Stopped Recording");
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Tray.install(new FakeRecorder());
            }
        });
    }

}
