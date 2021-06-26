package com.neomemex.tray;

import com.neomemex.ocr.OCR;
import com.neomemex.recorder.Recorder;
import com.neomemex.shared.Highlight;
import com.neomemex.shared.Time;
import com.neomemex.viewer.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TrayTestDemo {

    static class FakeRecorder implements Recorder {
        @Override public void start() { System.out.println("Started Recording"); }
        @Override public void stop()  { System.out.println("Stopped Recording"); }
    }

    static class FakeDisplay implements Viewer.Display {
        @Override public void show() { JOptionPane.showMessageDialog(null, "Fake display"); }
        @Override public void setImage(BufferedImage image) {}
        @Override public void setHighlight(Highlight highlight) {}
        @Override public void setTime(Time t,Time[] times) {}
        @Override public void setWords(OCR.Word[] words) {}
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Tray.install(new FakeRecorder(),new FakeDisplay());
            }
        });
    }

}
