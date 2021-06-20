package com.neomemex.shared;

public final class TimeCalculator {

    private final Time start;
    private final long duration;

    public TimeCalculator(Time start, Time end) {
        this.start = start;
        duration = duration(start,end);
    }

    static long duration(Time start, Time end) { return end.t - start.t; }

    public TimeCalculator() {
        this(new Time(2000,0,0,0),new Time(2040,0,0,0));
    }

    static final int   YEAR = 0;
    static final int    DAY = 1;
    static final int   HOUR = 2;
    static final int MINUTE = 3;
    static final int SECOND = 4;
    public Time timeFrom(Time time,double[] parts,int focus) {
        if (focus==YEAR)   return timeFocusYear(parts[YEAR]);
        if (focus==DAY)    return timeFocusDay(time,parts[DAY]);
        if (focus==HOUR)   return timeFocusHour(time,parts[HOUR]);
        if (focus==MINUTE) return timeFocusMinute(time,parts[MINUTE]);
        if (focus==SECOND) return timeFocusSecond(time,parts[SECOND]);
        throw new IllegalArgumentException();
    }

    private Time timeFocusYear(double year) {
        return new Time((long) (start.t + (year * duration)));
    }

    private Time timeFocusDay(Time time,double day) {
        Time start = new Time(time.year(),0,0,0,0,0);
        return new Time(start.t + (long)(day * Time.MILLIS_PER_YEAR));
    }

    private Time timeFocusHour(Time time,double hour) {
        Time start = new Time(time.year(),time.day(),0,0,0,0);
        return new Time(start.t + (long)(hour * Time.MILLIS_PER_DAY));
    }

    private Time timeFocusMinute(Time time,double minute) {
        Time start = new Time(time.year(),time.day(),time.hour(),0,0,0);
        return new Time(start.t + (long)(minute * Time.MILLIS_PER_HOUR));
    }

    private Time timeFocusSecond(Time time,double second) {
        Time start = new Time(time.year(),time.day(),time.hour(),time.minute(),0,0);
        return new Time(start.t + (long)(second * Time.MILLIS_PER_MINUTE));
    }

    public double   year(Time time) { return (double) duration(start,time) / (double) duration; }
    public double    day(Time time) { return fraction(time.day(),365); }
    public double   hour(Time time) { return fraction(time.hour(), 24); }
    public double minute(Time time) { return fraction(time.minute(),60); }
    public double second(Time time) { return fraction(time.second(),60); }

    private static double fraction(int x, int max) { return (double) x / (double) max; }
}
