package com.neomemex.viewer;

import com.neomemex.reader.ImageSequenceReader;
import com.neomemex.shared.Image;
import com.neomemex.shared.Time;
import com.neomemex.shared.TimeStreamMap;

final class SimpleImageRetriever implements Viewer.Retriever {

    final TimeStreamMap map;

    private SimpleImageRetriever(TimeStreamMap map) {
        this.map = map;
    }

    static Viewer.Retriever of(TimeStreamMap map) {
        return new SimpleImageRetriever(map);
    }

    @Override
    public Viewer.Response request(Viewer.Request request) {
        return new Viewer.Response(image(request.time));
    }

    private Image image(Time time) {
        ImageSequenceReader reader = ImageSequenceReader.from(map.input(time));
        Image last = null;
        for (Image image : reader) {
            if (image.time.after(time)) {
                if (last==null) {
                    return image;
                }
                return time.diff(image.time) < time.diff(last.time) ? image : last;
            } else {
                last = image;
            }
        }
        return last;
    }
}
