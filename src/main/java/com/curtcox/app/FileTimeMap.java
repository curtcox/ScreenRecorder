package com.curtcox.app;

import java.io.File;

final class FileTimeMap {

    File base = new File(".screenshots");

    File file(Time time) {
        return new File(base.getPath() + "/" + "/"  + "/" + "");
    }
//    ScreenMinuteRecorder(File fileName) throws IOException {
//        this(SimpleImageSequenceWriter.to(fileName),Time.endOfThisMinute(),500);
//    }


}
