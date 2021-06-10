package com.neomemex.tray;

import com.neomemex.recorder.Recorder;
import com.neomemex.viewer.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TrayTest {

    static class FakeRecorder implements Recorder {
        @Override public void start() { System.out.println("Started Recording"); }
        @Override public void stop()  { System.out.println("Stopped Recording"); }
    }

    static class FakeDisplay implements Viewer.Display {
        @Override public void show() { JOptionPane.showMessageDialog(null, "Fake display"); }
        @Override public void setImage(BufferedImage image) {}
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Tray.install(new FakeRecorder(),new FakeDisplay());
            }
        });
    }

}
