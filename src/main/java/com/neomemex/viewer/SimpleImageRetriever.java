package com.neomemex.viewer;

final class SimpleImageRetriever implements Viewer.Retriever {
    @Override
    public Viewer.Response request(Viewer.Request request) {
        System.out.println(request);
        return new Viewer.Response(new RasterSerializer(Screen.shot()).image());
    }
}
