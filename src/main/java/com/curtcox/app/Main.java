package com.curtcox.app;

import java.awt.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

final class Main {

    static final SimpleImageRetriever retriever = new SimpleImageRetriever();
    static final Executor              executor = Executors.newSingleThreadExecutor();
    static final SimpleImageRequestor requestor = new SimpleImageRequestor(retriever,executor);
    static final ScreenLogViewer         viewer = new ScreenLogViewer(requestor,Screen.width(),Screen.height());

    static void main0() {
        requestor.display = viewer;
        viewer.show();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() { main0(); }
        });
    }
}
