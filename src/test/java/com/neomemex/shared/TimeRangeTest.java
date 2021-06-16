package com.neomemex.shared;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeRangeTest {

    @Test
    public void contains_is_true() {
        assertTrue(TimeRange.of(new Time(0),new Time(2)).contains(new Time(1)));
        assertTrue(TimeRange.of(new Time(0),new Time(100)).contains(new Time(99)));
    }

    @Test
    public void contains_is_false() {
        assertFalse(TimeRange.of(new Time(0),new Time(2)).contains(new Time(3)));
        assertFalse(TimeRange.of(new Time(1),new Time(2)).contains(new Time(0)));
    }

}
