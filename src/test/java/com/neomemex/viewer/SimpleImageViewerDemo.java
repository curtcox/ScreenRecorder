package com.neomemex.viewer;

import com.neomemex.reader.SimpleImageRetriever;
import com.neomemex.recorder.Recorder;
import com.neomemex.recorder.ScreenRecorder;
import com.neomemex.shared.*;
import com.neomemex.store.MemoryTimeStreamMap;
import com.neomemex.store.TimeStreamMap;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleImageViewerDemo {

    static final TimeStreamMap            store = new MemoryTimeStreamMap();
    static final Viewer.Retriever     retriever = SimpleImageRetriever.of(store);
    static final ExecutorService       executor = Executors.newSingleThreadExecutor();
    static final SimpleImageRequestor requestor = new SimpleImageRequestor(retriever,executor);
    static final ScreenLogViewer         viewer = new ScreenLogViewer(requestor, Screen.width(),Screen.height());
    static final Recorder              recorder = ScreenRecorder.startWaitingToRecord(store);

    public static void main(String[] args) {
        System.out.println("Started recording");
        recorder.start();
        Sleep.second(55);
        recorder.stop();
        System.out.println("Stopped recording");
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                requestor.start(viewer);
                viewer.show();
            }
        });
    }

}
