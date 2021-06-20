package com.neomemex.store;

import com.neomemex.shared.Pad;
import com.neomemex.shared.Time;

import java.io.File;

final class FileTimeMap {

    final File base = new File(".screenshots");

    File file(Time time) {
        return new File(base.getPath() +
                "/" + time.year() +
                "/" + Pad.last(3,time.day()) +
                "/" + Pad.last(2,time.hour()) +
                "/" + Pad.last(2,time.minute()) + ".slog");
    }


}
