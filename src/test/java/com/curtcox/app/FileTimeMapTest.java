package com.curtcox.app;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class FileTimeMapTest {

    final FileTimeMap map = new FileTimeMap();

    @Test
    public void full_path_contains_expected_elements() {
        File file = map.file(new Time(0));
        String full = file.getAbsolutePath();
        assertTrue(full,full.contains(".screenshots"));
        assertTrue(full,full.contains("1970"));
        assertTrue(full,full.contains("001"));
        assertTrue(full,full.contains("0001.slog"));
        assertTrue(full,full.endsWith(".screenshots/1970/001/0001.slog"));
    }
}
