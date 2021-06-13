package com.neomemex.shared;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class MemoryTimeStreamMap implements TimeStreamMap {

    final Map<Time,ByteArrayOutputStream> map = new HashMap<>();

    public OutputStream output(Time time) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        map.put(time,out);
        return out;
    }

    public InputStream input(Time time) {
        ByteArrayOutputStream out = map.get(time);
        return new ByteArrayInputStream(out.toByteArray());
    }

}