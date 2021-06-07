package com.neomemex.viewer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeCalculatorTest {

    static Time year(int year) {
        return new Time(year,0,0,0);
    }

    @Test
    public void _2000_is_start_of_between_2000_to_2020() {
        TimeCalculator calculator = new TimeCalculator(year(2000),year(2020));

        Time time = calculator.timeFrom(new double[] {0.0,0.0,0.0,0.0,0.0},0);

        assertEquals(2000,time.year());
        assertEquals(0,time.day());
        assertEquals(0,time.hour());
        assertEquals(0,time.minute());
    }

    @Test
    public void _2040_is_end_of_2020_to_2040() {
        TimeCalculator calculator = new TimeCalculator(year(2020),year(2040));

        Time time = calculator.timeFrom(new double[] {1.0,0.0,0.0,0.0,0.0},0);

        assertEquals(2040,time.year());
        assertEquals(0,time.day());
        assertEquals(0,time.hour());
        assertEquals(0,time.minute());
    }

    @Test
    public void _2000_is_mid_way_between_1980_and_2020() {
        TimeCalculator calculator = new TimeCalculator(year(1980),year(2020));

        Time time = calculator.timeFrom(new double[] {0.5,0.0,0.0,0.0,0.0},0);

        assertEquals(2000,time.year());
        assertEquals(0,time.day());
        assertEquals(0,time.hour());
        assertEquals(0,time.minute());
    }

}
