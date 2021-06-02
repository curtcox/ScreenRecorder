package com.curtcox.app;

final class SimpleImageRetriever implements Viewer.Retriever {
    @Override
    public Viewer.Response request(Viewer.Request request) {
        return new Viewer.Response(new RasterSerializer(Screen.shot()).image());
    }
}