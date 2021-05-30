package com.curtcox.app;

final class Time {

    final long t;

    private static int MILLIS_PER_MINUTE = 60 * 1000;

    Time(long t) {
        this.t = t;
    }

    static Time endOfLastMinute() {
        return new Time(now().totalMinutesSince0() * MILLIS_PER_MINUTE - 1);
    }

    static Time endOfThisMinute() {
        return new Time((now().totalMinutesSince0() + 1) * MILLIS_PER_MINUTE - 1);
    }

    private long totalMinutesSince0() {
        return (t / MILLIS_PER_MINUTE);
    }

    int minute() {
        return (int) (totalMinutesSince0() % 60);
    }

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
