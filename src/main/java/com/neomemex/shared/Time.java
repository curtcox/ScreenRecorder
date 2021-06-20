package com.neomemex.shared;

import java.util.Set;

// Immutable instant
public final class Time implements Comparable<Time> {

    public final long t;
    private final long timeThisYear;
    private final int index;

    public static long MILLIS_PER_SECOND     = 1000;
    public static long MILLIS_PER_MINUTE     = MILLIS_PER_SECOND * 60;
    public static long MILLIS_PER_HOUR       = MILLIS_PER_MINUTE * 60;
    public static long MILLIS_PER_DAY        = MILLIS_PER_HOUR   * 24;
    public static long MILLIS_PER_YEAR       = MILLIS_PER_DAY    * 365;

    private static long[] startOfYear = new long[100];

    static {
        long start = 0;
        for (int i=0; i<startOfYear.length; i++) {
            startOfYear[i] = start;
            start += MILLIS_PER_YEAR;
            if (i % 4 == 2) {
                start += MILLIS_PER_DAY;
            }
        }
    }

    private static int indexFor(long t) {
        if (t<0) {
            throw new IllegalArgumentException(t + " < 0");
        }
        for (int i=0; i<startOfYear.length; i++) {
            if (t==startOfYear[i]) {
                return i;
            }
            if (t > startOfYear[i] && t < startOfYear[i + 1]) {
                return i;
            }
        }
        throw new IllegalArgumentException(t + " too big : expand table");
    }

    Time(int year, int day, int hour, int minute) {
        this(year,day,hour,minute,0,0);
    }

    Time(int year, int day, int hour, int minute, int second, int milliseconds) {
        index = year - 1970;
        timeThisYear =
            day * MILLIS_PER_DAY +
            hour * MILLIS_PER_HOUR +
            minute * MILLIS_PER_MINUTE +
            second * MILLIS_PER_SECOND +
            milliseconds;

        t = startOfYear[index] + timeThisYear;
    }

    public Time(long t) {
        this.t = t;
        index = indexFor(t);
        timeThisYear = t - startOfYear[index];
    }

    private final long totalSecondsSince0() { return t / MILLIS_PER_SECOND; };
    private final long totalMinutesSince0() { return t / MILLIS_PER_MINUTE; };
    private final long totalHoursSince0()   { return t / MILLIS_PER_HOUR; };

    public static Time endOfLastMinute() { return new Time(now().totalMinutesSince0() * MILLIS_PER_MINUTE - 1); }
    public static Time endOfThisMinute() { return new Time((now().totalMinutesSince0() + 1) * MILLIS_PER_MINUTE - 1); }

    public int second() { return (int) totalSecondsSince0() % 60; }
    public int minute() { return (int) totalMinutesSince0() % 60; }
    public int hour()   { return (int) totalHoursSince0() % 24; }
    public int day()    { return (int) (timeThisYear / MILLIS_PER_DAY); }
    public int year()   { return index + 1970; }

    public static Time now() { return new Time(t()); }

    public long diff(Time that)  { return Math.abs(t - that.t);}
    public boolean inThePast()   { return t < t(); }
    public boolean inTheFuture() { return t > t(); }
    private static long t()      { return System.currentTimeMillis(); }

    @Override public String toString() {
        return year() + "/" + pad3(day()) + " " + pad(hour()) + ":" + pad(minute()) + ":" + pad(second());
    }

    @Override public int hashCode()           { return (int) t; }
    @Override public boolean equals(Object o) { return t == ((Time) o).t; }

    private static String pad(int count)  { return Pad.last(2,count); }
    private static String pad3(int count) { return Pad.last(3,count); }

    public boolean after(Time that)  { return t > that.t; }
    public boolean before(Time that) { return t < that.t; }

    public static Time closestTimeIn(Time target, Set<Time> times) {
        Time best = null;
        for (Time t : times) {
            if (best==null || target.diff(t) < target.diff(best)) {
                best = t;
            }
        }
        return best;
    }

    @Override
    public int compareTo(Time that) {
        return Long.compare(t,that.t);
    }
}
