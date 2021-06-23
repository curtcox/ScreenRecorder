package com.neomemex.reader;

import com.neomemex.shared.Image;
import com.neomemex.shared.Time;
import com.neomemex.store.TimeStreamMap;
import com.neomemex.viewer.Viewer;

import java.util.Map;
import java.util.TreeMap;

public final class SimpleImageRetriever implements Viewer.Retriever {

    final Map<Time,Map<Time,Image>> cache = new TreeMap<>();
    final TimeStreamMap streams;

    private SimpleImageRetriever(TimeStreamMap timeStreamMap) {
        this.streams = timeStreamMap;
    }

    public static Viewer.Retriever of(TimeStreamMap map) {
        return new SimpleImageRetriever(map);
    }

    @Override
    public Viewer.Response request(Viewer.Request request) {
        return new Viewer.Response(image(request.time));
    }

    private Image image(Time time) {
        return closest(imageMap(streams.nearest(time)),time);
    }

    private Map<Time,Image> imageMap(Time time) {
        if (cache.containsKey(time)) {
            return cache.get(time);
        } else {
            Map<Time,Image> images = ImageSequenceReader.allImagesFrom(streams.input(time));
            cache.put(time,images);
            return images;
        }
    }

    private Image closest(Map<Time,Image> images,Time target) {
        return images.get(Time.closestTimeIn(target,images.keySet()));
    }

}
