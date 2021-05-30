package com.curtcox.app;

final class Time {

    final long t;

    private static long MILLIS_PER_MINUTE = 60 * 1000;
    private static long MILLIS_PER_DAY    = MILLIS_PER_MINUTE * 60 * 24;
    private static long MILLIS_PER_YEAR   = MILLIS_PER_DAY    * 365;

    Time(long t) {
        this.t = t;
    }

    static Time endOfLastMinute() {
        return new Time(now().totalMinutesSince0() * MILLIS_PER_MINUTE - 1);
    }

    static Time endOfThisMinute() {
        return new Time((now().totalMinutesSince0() + 1) * MILLIS_PER_MINUTE - 1);
    }

    private long totalMinutesSince0() { return t / MILLIS_PER_MINUTE; }
    private long totalDaysSince0()    { return t / MILLIS_PER_DAY;    }
    private long totalYearsSince0()   { return t / MILLIS_PER_YEAR;   }

    int minute() { return t < 0 ? (int) (60  + totalMinutesSince0() % 60)  : (int) (totalMinutesSince0() % 60); }
    int day()    { return t < 0 ? (int) (366  + totalDaysSince0() % 365)   : (int) (totalDaysSince0() % 365); }
    int year()   { return t < 0 ? (int) (1969 + totalYearsSince0())        : (int) (1970 + totalYearsSince0()); }

    static Time now() {
        return new Time(System.currentTimeMillis());
    }

    boolean inThePast() {
        return t < System.currentTimeMillis();
    }

    boolean inTheFuture() {
        return t > System.currentTimeMillis();
    }


}
