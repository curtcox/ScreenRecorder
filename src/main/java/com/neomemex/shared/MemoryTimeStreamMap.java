package com.neomemex.shared;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class MemoryTimeStreamMap implements TimeStreamMap {

    private TimeRange range;
    private final Map<Time,ByteArrayOutputStream> map = new HashMap<>();

    @Override
    public TimeRange range() {
        return range;
    }

    public OutputStream output(final Time time) {
        ByteArrayOutputStream out = new ByteArrayOutputStream() {
            @Override public void close() throws IOException {
                super.close();
                System.out.println("closed");
                add(this,time);
            }
        };
        return out;
    }

    private void add(ByteArrayOutputStream stream,Time time) {
        range = range==null ? TimeRange.of(time,time) : range.plus(time);
        map.put(time,stream);
    }

    public InputStream input(Time time) {
        return new ByteArrayInputStream(closestStream(time).toByteArray());
    }

    private ByteArrayOutputStream closestStream(Time time) {
        return map.get(closestTime(time));
    }

    private Time closestTime(Time time) {
        Time best = null;
        for (Time t : map.keySet()) {
            if (best==null || time.diff(t) < time.diff(best)) {
                best = t;
            }
        }
        return best;
    }

}