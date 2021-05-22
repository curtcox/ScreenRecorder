package com.curtcox.app;

import java.io.File;
import java.io.IOException;

public final class App {

    final File fileName;

    App(File fileName) {
        this.fileName = fileName;
    }

    private PngSeqWriter newWriter(int count) throws IOException {
        FilterNone filterNone = new FilterNone(Screen.size().width,Screen.size().height);
        return new PngSeqWriter(fileName, filterNone, count);
    }

    private void writeImage(PngSeqWriter writer) throws IOException {
        writer.writeImage(Screen.shot());
    }

    private void writeScreenshots() throws IOException {
        int max = 10;
        PngSeqWriter writer = newWriter(max);
        for (int i = 0; i < max; i++) {
            writeImage(writer);
            print(i);
            sleep();
        }
        writer.close();
    }

    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            print("???");
        }
    }

    private static void print(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) throws Exception {
        File fileName = new File("screenshot.png");
        new App(fileName).writeScreenshots();
    }

}