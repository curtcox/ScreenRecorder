package com.neomemex.store;

import com.neomemex.shared.Time;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class MemoryTimeStreamMap implements TimeStreamMap {

    private final Map<Time,ByteArrayOutputStream> map = new HashMap<>();

    @Override
    public Time nearest(Time time) {
        return Time.closestTimeIn(time,map.keySet());
    }

    public OutputStream output(final Time time) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream() {
            @Override public void close() throws IOException {
                super.close();
                System.out.println("closed with " + size());
                add(this,time);
            }
        };
        return out;
    }

    private void add(ByteArrayOutputStream stream,Time time) {
        map.put(time,stream);
    }

    public InputStream input(Time time) {
        return new ByteArrayInputStream(closestStream(time).toByteArray());
    }

    private ByteArrayOutputStream closestStream(Time time) { return map.get(nearest(time)); }

}