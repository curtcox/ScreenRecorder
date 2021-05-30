package com.curtcox.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeTest {

    static final long A_MINUTE = 60 * 1000;

    @Test
    public void now_is_currentTimeMillis() {
        assertTrue(Time.now().t <= System.currentTimeMillis());
        assertTrue(System.currentTimeMillis() <= Time.now().t);
    }

    @Test
    public void endOfLastMinute_must_be_before_now() {
        assertTrue(Time.endOfLastMinute().inThePast());
    }

    @Test
    public void endOfLastMinute_must_not_be_more_than_a_minute_before_now() {
        assertFalse(Time.now().t - Time.endOfLastMinute().t  > A_MINUTE);
    }

    @Test
    public void endOfLastMinute_must_not_have_the_same_minute_as_now() {
        assertNotEquals(Time.endOfLastMinute().minute(),Time.now().minute());
    }

    @Test
    public void endOfLastMinute_must_have_a_different_minute_than_its_sucessor() {
        Time t = Time.endOfLastMinute();
        assertNotEquals(t.minute(),new Time(t.t + 1).minute());
    }

    @Test
    public void endOfThisMinute_must_not_be_before_now() {
        assertFalse(Time.endOfThisMinute().inThePast());
    }

    @Test
    public void endOfThisMinute_must_not_be_more_than_a_minute_from_now() {
        assertFalse(Time.endOfThisMinute().t - Time.now().t > A_MINUTE);
    }

    @Test
    public void endOfThisMinute_must_have_the_same_minute_as_now() {
        assertEquals(Time.endOfThisMinute().minute(),Time.now().minute());
    }

    @Test
    public void endOfThisMinute_must_have_a_different_minute_than_its_sucessor() {
        Time t = Time.endOfThisMinute();
        assertNotEquals(t.minute(),new Time(t.t + 1).minute());
    }

    @Test
    public void endOfThisMinute_must_be_a_minute_after_endOfLastMinute() {
        assertEquals(Time.endOfThisMinute().t - Time.endOfLastMinute().t,A_MINUTE);
    }

    @Test
    public void last_milli_is_inThePast() {
        assertTrue(new Time(Time.now().t  - 1).inThePast());
    }

    @Test
    public void just_after_now_is_not_inThePast() {
        assertFalse(new Time(Time.now().t  + 100).inThePast());
    }

    @Test
    public void next_milli_is_inTheFuture() {
        assertTrue(new Time(Time.now().t  + 100).inTheFuture());
    }

    @Test
    public void just_before_now_is_not_inTheFuture() {
        assertFalse(new Time(Time.now().t  - 1).inTheFuture());
    }

}
