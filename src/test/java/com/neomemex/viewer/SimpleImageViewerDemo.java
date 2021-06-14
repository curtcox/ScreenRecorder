package com.neomemex.viewer;

import com.neomemex.recorder.Recorder;
import com.neomemex.recorder.ScreenRecorder;
import com.neomemex.shared.*;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleImageViewerDemo {

    static final TimeStreamMap            store = new MemoryTimeStreamMap();
    static final SimpleImageRetriever retriever = new SimpleImageRetriever(store);
    static final ExecutorService       executor = Executors.newSingleThreadExecutor();
    static final SimpleImageRequestor requestor = new SimpleImageRequestor(retriever,executor);
    static final ScreenLogViewer         viewer = new ScreenLogViewer(requestor, Screen.width(),Screen.height());
    static final Recorder              recorder = ScreenRecorder.startWaitingToRecord(store);

    public static void main(String[] args) {
        recorder.start();
        Sleep.second(5);
        recorder.stop();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                requestor.start(viewer);
                viewer.show();
            }
        });
    }

}
