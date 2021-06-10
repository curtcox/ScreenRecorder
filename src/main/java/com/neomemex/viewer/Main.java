package com.neomemex.viewer;

import com.neomemex.recorder.Recorder;
import com.neomemex.recorder.ScreenRecorder;
import com.neomemex.shared.Screen;
import com.neomemex.tray.Tray;

import java.awt.*;
import java.util.concurrent.*;

final class Main {

    static final SimpleImageRetriever retriever = new SimpleImageRetriever();
    static final ExecutorService       executor = Executors.newSingleThreadExecutor();
    static final SimpleImageRequestor requestor = new SimpleImageRequestor(retriever,executor);
    static final ScreenLogViewer         viewer = new ScreenLogViewer(requestor, Screen.width(),Screen.height());
    static final Recorder              recorder = ScreenRecorder.startWaitingToRecord();

    static void main0() {
        requestor.start(viewer);
        Tray.install(recorder,viewer);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() { main0(); }
        });
    }
}
