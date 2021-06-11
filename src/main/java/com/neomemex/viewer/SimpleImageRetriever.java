package com.neomemex.viewer;

import com.neomemex.reader.ImageSequenceReader;
import com.neomemex.shared.Image;
import com.neomemex.shared.Time;
import com.neomemex.shared.TimeStreamMap;


final class SimpleImageRetriever implements Viewer.Retriever {

    final TimeStreamMap map = new TimeStreamMap();

    @Override
    public Viewer.Response request(Viewer.Request request) {
        return new Viewer.Response(image(request.time));
    }

    private Image image(Time time) {
        ImageSequenceReader reader = new ImageSequenceReader(map.input(time));
        return null;
    }
}
