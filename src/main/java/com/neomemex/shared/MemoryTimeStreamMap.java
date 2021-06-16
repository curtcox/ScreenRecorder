package com.neomemex.shared;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class MemoryTimeStreamMap implements TimeStreamMap {

    final Map<Time,ByteArrayOutputStream> map = new HashMap<>();

    @Override
    public TimeRange range() {
        return null;
    }

    public OutputStream output(final Time time) {
        ByteArrayOutputStream out = new ByteArrayOutputStream() {
            @Override public void close() throws IOException {
                super.close();
                System.out.println("closed");
                map.put(time,this);
            }
        };
        return out;
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