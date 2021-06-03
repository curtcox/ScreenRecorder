package com.curtcox.app;

import java.io.File;

final class FileTimeMap {

    File base = new File(".screenshots");

    File file(Time time) {
        return new File(base.getPath() +
                "/" + time.year() +
                "/" + last(3,time.day() + 1) +
                "/" + last(4,time.minute() + 1) + ".slog");
    }

    private static String last(int count,int number) {
        String text = "0000" + number;
        return text.substring(text.length() - count);
    }

}
