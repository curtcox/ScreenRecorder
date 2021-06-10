package com.neomemex.shared;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class FileTimeMapTest {

    final FileTimeMap map = new FileTimeMap();

    private String path(Time time) {
        File file = map.file(time);
        return file.getAbsolutePath();
    }

    @Test
    public void full_path_contains_expected_elements_for_time_0() {
        String full = path(new Time(0));
        assertTrue(full,full.contains(".screenshots"));
        assertTrue(full,full.contains("/1970/"));
        assertTrue(full,full.contains("/000/"));
        assertTrue(full,full.contains("/00/"));
        assertTrue(full,full.contains("/00.slog"));
        assertTrue(full,full.endsWith(".screenshots/1970/000/00/00.slog"));
    }

    @Test
    public void full_path_contains_expected_elements_for_jan_1_2000() {
        endWith(new Time(2000,0,0,0),"2000/000/00/00");
    }

    @Test
    public void full_path_contains_expected_elements_for_dec_31_1999() {
        endWith(new Time(1999,364,23,59),"1999/364/23/59");
    }

    private void endWith(Time time,String text) {
        String path = path(time);
        assertTrue(path,path.endsWith(".screenshots/" + text + ".slog"));
    }
}
