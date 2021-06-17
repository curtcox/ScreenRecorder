package com.neomemex.viewer;

import com.neomemex.recorder.*;
import com.neomemex.shared.*;
import com.neomemex.tray.Tray;

import java.awt.*;
import java.util.concurrent.*;

final class Main {

    static final TimeStreamMap            store = new FileTimeStreamMap();
    static final Viewer.Retriever     retriever = SimpleImageRetriever.of(store);
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
