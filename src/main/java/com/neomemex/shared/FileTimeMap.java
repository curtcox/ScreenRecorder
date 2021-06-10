package com.neomemex.shared;

import java.io.File;

public final class FileTimeMap {

    final File base = new File(".screenshots");

    public File file(Time time) {
        return new File(base.getPath() +
                "/" + time.year() +
                "/" + Pad.last(3,time.day()) +
                "/" + Pad.last(2,time.hour()) +
                "/" + Pad.last(2,time.minute()) + ".slog");
    }


}
