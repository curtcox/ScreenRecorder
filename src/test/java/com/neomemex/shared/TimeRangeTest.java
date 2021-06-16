package com.neomemex.shared;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeRangeTest {

    @Test
    public void contains_is_true_when_times_are_in_order() {
        assertTrue(TimeRange.of(new Time(0),new Time(2)).contains(new Time(1)));
        assertTrue(TimeRange.of(new Time(0),new Time(100)).contains(new Time(99)));
    }

    @Test
    public void contains_is_true_when_times_are_out_of_order() {
        assertTrue(TimeRange.of(new Time(2),new Time(0)).contains(new Time(1)));
        assertTrue(TimeRange.of(new Time(100),new Time(0)).contains(new Time(99)));
    }

    @Test
    public void contains_is_false() {
        assertFalse(TimeRange.of(new Time(0),new Time(2)).contains(new Time(3)));
        assertFalse(TimeRange.of(new Time(1),new Time(2)).contains(new Time(0)));
    }

    @Test
    public void plus_returns_same_range_when_time_is_in_range() {
        TimeRange range = TimeRange.of(new Time(0),new Time(2));
        assertSame(range,range.plus(new Time(0)));
        assertSame(range,range.plus(new Time(1)));
        assertSame(range,range.plus(new Time(2)));
    }

    @Test
    public void plus_returns_new_range_ending_with_new_time() {
        TimeRange range = TimeRange.of(new Time(0),new Time(2)).plus(new Time(10));
        assertTrue(range.contains(new Time(9)));
        assertFalse(range.contains(new Time(11)));
    }

    @Test
    public void plus_returns_new_range_starting_with_new_time() {
        TimeRange range = TimeRange.of(new Time(100),new Time(102)).plus(new Time(50));
        assertTrue(range.contains(new Time(51)));
        assertFalse(range.contains(new Time(49)));
    }

}
