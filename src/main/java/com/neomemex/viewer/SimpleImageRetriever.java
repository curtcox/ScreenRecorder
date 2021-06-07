package com.neomemex.viewer;

import com.neomemex.recorder.RasterSerializer;
import com.neomemex.shared.Screen;

final class SimpleImageRetriever implements Viewer.Retriever {
    @Override
    public Viewer.Response request(Viewer.Request request) {
        System.out.println(request);
        return new Viewer.Response(new RasterSerializer(Screen.shot()).image());
    }
}
