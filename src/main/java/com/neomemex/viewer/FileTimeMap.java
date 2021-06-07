package com.neomemex.viewer;

import java.io.File;

final class FileTimeMap {

    File base = new File(".screenshots");

    File file(Time time) {
        return new File(base.getPath() +
                "/" + time.year() +
                "/" + Pad.last(3,time.day() + 1) +
                "/" + Pad.last(4,time.minute() + 1) + ".slog");
    }


}
