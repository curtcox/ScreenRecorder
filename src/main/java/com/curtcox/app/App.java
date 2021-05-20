package com.curtcox.app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class App {

    final File fileName;
    final Robot robot;
    final Dimension screenSize;

    App(File fileName,Robot robot,Dimension screenSize) {
        this.fileName = fileName;
        this.robot = robot;
        this.screenSize = screenSize;
    }

    public static void main(String[] args) throws Exception {
        File fileName = new File("screenshot.png");

        new App(fileName,new Robot(),screenSize()).writeScreenshots();
    }

    private PngSeqWriter newWriter(int count) throws IOException {
        int bands = screenshot().getRaster().getNumBands();
        Encoder encoder = new Encoder(screenSize.width,screenSize.height,bands);
        return new PngSeqWriter(fileName, encoder, count);
    }

    private void writeImage(PngSeqWriter writer) throws IOException {
        writer.writeImage(screenshot());
    }

    private BufferedImage screenshot() {
        return robot.createScreenCapture(new Rectangle(screenSize));
    }

    private static Dimension screenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    private void writeScreenshots() throws IOException {
        int max = 10;
        PngSeqWriter writer = newWriter(max);
        for (int i = 0; i < max; i++) {
            writeImage(writer);
            System.out.println(i);
            sleep();
        }
        writer.close();
    }

    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println("???");
        }
    }
}