package com.curtcox.app;

import java.awt.*;
import java.util.concurrent.Executor;

final class SimpleImageRequestor implements Viewer.Requestor {

    Viewer.Display display;
    final Viewer.Retriever retriever;
    final Executor executor;

    SimpleImageRequestor(Viewer.Retriever retriever, Executor executor) {
        this.retriever = retriever;
        this.executor = executor;
    }

    @Override
    public void request(final Viewer.Request request) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final Viewer.Response response = retriever.request(request);
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        display.setImage(RasterDeserializer.image(response.image));
                    }
                });
            }
        });
    }
}
