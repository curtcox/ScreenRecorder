package com.neomemex.shared;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TimeTest {

    static final long A_MINUTE = 60 * 1000;
    static final long A_DAY    = A_MINUTE * 24 * 60;
    static final long A_YEAR   = A_DAY    * 365;

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

    @Test
    public void time_0_is_midnight_day_1_1970() {
        Time t = new Time(0);
        assertEquals(1970,t.year());
        assertEquals(0,t.day());
        assertEquals(0,t.minute());
    }

    @Test
    public void time_0_plus_a_minute_1970() {
        Time t = new Time(A_MINUTE);
        assertEquals(1970,t.year());
        assertEquals(0,t.day());
        assertEquals(1,t.minute());
    }

    @Test
    public void time_0_plus_a_day_1970() {
        Time t = new Time(A_DAY);
        assertEquals(1970,t.year());
        assertEquals(1,t.day());
        assertEquals(0,t.minute());
    }

    @Test
    public void time_0_plus_364_days_is_1970() {
        Time t = new Time(364 * A_DAY);
        assertEquals(1970,t.year());
        assertEquals(364,t.day());
        assertEquals(0,t.minute());
    }

    @Test
    public void time_0_plus_1_year_is_1971() {
        Time t = new Time(A_YEAR);
        assertEquals(1971,t.year());
        assertEquals(0,t.day());
        assertEquals(0,t.minute());
    }

    @Test
    public void time_0_plus_2_years_is_1972() {
        Time t = new Time(2 * A_YEAR);
        assertEquals(1972,t.year());
        assertEquals(0,t.day());
        assertEquals(0,t.minute());
    }

    @Test
    public void time_0_plus_3_years_is_still_1972() {
        Time t = new Time(3 * A_YEAR);
        assertEquals(1972,t.year());
        assertEquals(365,t.day());
        assertEquals(0,t.minute());
    }

    @Test
    public void time_0_plus_3_years_and_a_day_is_1973() {
        Time t = new Time(3 * A_YEAR + A_DAY);
        assertEquals(1973,t.year());
        assertEquals(0,t.day());
        assertEquals(0,t.minute());
    }

    @Test
    public void time_0_plus_a_day_and_a_minute_1970() {
        Time t = new Time(A_DAY + A_MINUTE);
        assertEquals(1970,t.year());
        assertEquals(1,t.day());
        assertEquals(1,t.minute());
    }

    @Test
    public void time_0_plus_a_year_2_days_and_3_minutes_1971() {
        Time t = new Time(A_YEAR + 2 * A_DAY + 3 * A_MINUTE);
        assertEquals(1971,t.year());
        assertEquals(2,t.day());
        assertEquals(3,t.minute());
    }

    @Test
    public void time_0_can_be_constructed_via_year_day_minute() {
        Time t = new Time(1970,0,0,0);
        assertEquals(1970,t.year());
        assertEquals(0,t.day());
        assertEquals(0,t.hour());
        assertEquals(0,t.minute());
    }

    @Test
    public void dec_31_1999_can_be_constructed_via_year_day_minute() {
        Time t = new Time(1999,364,23,59);
        assertEquals(1999,t.year());
        assertEquals(364,t.day());
        assertEquals(23,t.hour());
        assertEquals(59,t.minute());
    }

    @Test
    public void before() {
        assertTrue(new Time(0).before(new Time(1)));
        assertFalse(new Time(1).before(new Time(0)));
    }

    @Test
    public void after() {
        assertTrue(new Time(1).after(new Time(0)));
        assertFalse(new Time(0).after(new Time(1)));
    }

    @Test
    public void equal_times() {
        equal(new Time(0),new Time(0));
        equal(new Time(1),new Time(1));
    }

    @Test
    public void unequal_times() {
        unequal(new Time(0),new Time(1));
        unequal(new Time(1),new Time(2));
    }

    @Test
    public void closest() {
        Set<Time> times = new HashSet<>();
        times.addAll(Arrays.asList(new Time[] {new Time(1000), new Time(2000), new Time(3000)}));
        assertEquals(new Time(1000),Time.closestTimeIn(new Time(10),times));
        assertEquals(new Time(1000),Time.closestTimeIn(new Time(1010),times));
        assertEquals(new Time(1000),Time.closestTimeIn(new Time(1499),times));
        assertEquals(new Time(2000),Time.closestTimeIn(new Time(1501),times));
        assertEquals(new Time(2000),Time.closestTimeIn(new Time(2499),times));
        assertEquals(new Time(3000),Time.closestTimeIn(new Time(2501),times));
        assertEquals(new Time(3000),Time.closestTimeIn(new Time(3499),times));
        assertEquals(new Time(3000),Time.closestTimeIn(new Time(9999999),times));
    }

    void equal(Time a, Time b) {
        assertEquals(a,b);
        assertEquals(b,a);
        assertEquals(a.hashCode(),b.hashCode());
    }

    void unequal(Time a, Time b) {
        assertNotEquals(a,b);
        assertNotEquals(b,a);
        assertNotEquals(a.hashCode(),b.hashCode());
    }

}
