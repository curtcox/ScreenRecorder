package com.neomemex.viewer;

final class TimeCalculator {

    private final Time start;
    private final long duration;

    TimeCalculator(Time start, Time end) {
        this.start = start;
        duration = duration(start,end);
    }

    static long duration(Time start, Time end) { return end.t - start.t; }

    TimeCalculator() {
        this(new Time(2000,0,0,0),new Time(2040,0,0,0));
    }

    static final int   YEAR = 0;
    static final int    DAY = 1;
    static final int   HOUR = 2;
    static final int MINUTE = 3;
    static final int SECOND = 4;
    Time timeFrom(double[] parts,int focus) {
        long t = (long) (start.t + (parts[YEAR] * duration));
        Time time = new Time(t);
        if (focus==YEAR)   return time;
        if (focus==DAY)    return timeFocusDay(time,parts[DAY]);
        if (focus==HOUR)   return timeFocusHour(time,parts[HOUR]);
        if (focus==MINUTE) return timeFocusMinute(time,parts[MINUTE]);
        if (focus==SECOND) return timeFocusSecond(time,parts[SECOND]);
        throw new IllegalArgumentException();
    }

    private Time timeFocusDay(Time time,double day) {
        return new Time(time.year(), outOf(day, 365), time.hour(),time.minute());
    }

    private Time timeFocusHour(Time time,double hour) {
        return new Time(time.year(), time.day(), outOf(hour,24),time.minute());
    }

    private Time timeFocusMinute(Time time,double minute) {
        return new Time(time.year(), time.day(), time.hour(), outOf(minute,60));
    }

    private Time timeFocusSecond(Time time,double second) {
        return new Time(time.year(), time.day(), time.hour(),time.minute(),outOf(second,60),0);
    }

    double   year(Time time) { return (double) duration(start,time) / (double) duration; }
    double    day(Time time) { return fraction(time.day(),365); }
    double   hour(Time time) { return fraction(time.hour(), 24); }
    double minute(Time time) { return fraction(time.minute(),60); }
    double second(Time time) { return fraction(time.second(),60); }

    private static int outOf(double x, int max) { return (int) (x * max); }
    private static double fraction(int x, int max) { return (double) x / (double) max; }
}
