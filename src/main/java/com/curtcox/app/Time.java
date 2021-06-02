package com.curtcox.app;

// Immutable instant
final class Time {

    final long t;
    private final long timeThisYear;
    private final long totalMinutesSince0;
    private final int index;

    private static long MILLIS_PER_MINUTE     = 60 * 1000;
    private static long MILLIS_PER_DAY        = MILLIS_PER_MINUTE * 60 * 24;
    private static long MILLIS_PER_YEAR       = MILLIS_PER_DAY    * 365;

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


    Time(long t) {
        this.t = t;
        index = indexFor(t);
        timeThisYear = t - startOfYear[index];
        totalMinutesSince0 = t / MILLIS_PER_MINUTE;
    }

    static Time endOfLastMinute() { return new Time(now().totalMinutesSince0 * MILLIS_PER_MINUTE - 1); }
    static Time endOfThisMinute() { return new Time((now().totalMinutesSince0 + 1) * MILLIS_PER_MINUTE - 1); }
    
    int minute() { return (int) totalMinutesSince0 % 60; }
    int day()    { return (int) (timeThisYear / MILLIS_PER_DAY); }
    int year()   { return index + 1970; }

    static Time now() { return new Time(System.currentTimeMillis()); }

    boolean inThePast()   { return t < System.currentTimeMillis(); }
    boolean inTheFuture() { return t > System.currentTimeMillis(); }

}
