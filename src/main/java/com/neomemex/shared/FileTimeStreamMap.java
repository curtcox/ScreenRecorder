package com.neomemex.shared;

import java.io.*;

public final class FileTimeStreamMap implements TimeStreamMap {

    final FileTimeMap map = new FileTimeMap();

    @Override
    public Time nearest(Time time) {
        throw new UnsupportedOperationException();
    }

    public OutputStream output(Time time) {
        try {
            return new FileOutputStream(ensure(map.file(time)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream input(Time time) {
        try {
            return new FileInputStream(ensure(map.file(time)));
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