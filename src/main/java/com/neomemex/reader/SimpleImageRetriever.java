package com.neomemex.reader;

import com.neomemex.shared.Highlight;
import com.neomemex.shared.Image;
import com.neomemex.shared.Time;
import com.neomemex.store.TimeStreamMap;
import com.neomemex.viewer.Viewer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

    private Time[] allTimes() {
        Set<Time> all = new HashSet<>();
        for (Map<Time,Image> map : cache.values()) {
            all.addAll(map.keySet());
        }
        return all.toArray(new Time[0]);
    }

    @Override
    public Viewer.Response request(Viewer.Request request) {
        return new Viewer.Response(image(request.time),highlight(request.time),allTimes());
    }

    private Image image(Time time) {
        return closest(imageMap(streams.nearest(time)),time);
    }

    private Highlight highlight(Time time) {
        return Highlight.none;
    }

    private Map<Time,Image> imageMap(Time time) {
        if (!cache.containsKey(time)) {
            cache.put(time, ImageSequenceReader.allImagesFrom(streams.input(time)));
        }
        return cache.get(time);
    }

    private Image closest(Map<Time,Image> images,Time target) {
        return images.get(Time.closestTimeIn(target,images.keySet()));
    }

}
