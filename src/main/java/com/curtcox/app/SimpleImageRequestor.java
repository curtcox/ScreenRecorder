package com.curtcox.app;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.*;

final class SimpleImageRequestor implements Viewer.Requestor {

    private Viewer.Display display;
    private final Viewer.Retriever retriever;
    private final ExecutorService executor;
    private final LinkedList<Viewer.Request> requests = new LinkedList<>();

    SimpleImageRequestor(Viewer.Retriever retriever, ExecutorService executor) {
        this.retriever = retriever;
        this.executor = executor;
    }

    void start(Viewer.Display display) {
        this.display = display;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (!executor.isShutdown()) {
                    Viewer.Request request = latestRequest();
                    if (request==null) {
                        Sleep.millis(100);
                    } else {
                        process(request);
                    }
                }
            }
        });
    }

    private Viewer.Request latestRequest() {
        synchronized (requests) {
            if (requests.isEmpty()) {
                return null;
            } else {
                Viewer.Request request = requests.getLast();
                requests.clear();
                return request;
            }
        }
    }

    private void process(final Viewer.Request request) {
        final BufferedImage image = RasterDeserializer.image(retriever.request(request).image);
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                display.setImage(image);
            }
        });
    }

    @Override
    // Invokers will be on the EDT, so implementors should return immediately without blocking.
    public void request(final Viewer.Request request) {
        synchronized (requests) {
            requests.add(request);
        }
    }
}
