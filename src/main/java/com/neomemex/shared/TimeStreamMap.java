package com.neomemex.shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public final class TimeStreamMap {

    final FileTimeMap map = new FileTimeMap();

    public OutputStream output(Time time) {
        try {
            return new FileOutputStream(ensure(map.file(time)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static File ensure(File file) {
        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        return file;
    }
}