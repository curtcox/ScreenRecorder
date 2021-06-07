package com.neomemex.viewer;

// Immutable instant
final class Time {

    final long t;
    private final long timeThisYear;
    private final int index;

    private static long MILLIS_PER_SECOND     = 1000;
    private static long MILLIS_PER_MINUTE     = MILLIS_PER_SECOND * 60;
    private static long MILLIS_PER_HOUR       = MILLIS_PER_MINUTE * 60;
    private static long MILLIS_PER_DAY        = MILLIS_PER_HOUR   * 24;
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

    Time(int year, int day, int hour, int minute) {
        this(year,day,hour,minute,0,0);
    }

    Time(int year, int day, int hour, int minute, int second, int milliseconds) {
        index = year - 1970;
        timeThisYear = day * MILLIS_PER_DAY + hour * MILLIS_PER_HOUR + minute * MILLIS_PER_MINUTE +
            second * MILLIS_PER_SECOND + milliseconds;

        t = startOfYear[index] + timeThisYear;
    }

    Time(long t) {
        this.t = t;
        index = indexFor(t);
        timeThisYear = t - startOfYear[index];
    }

    private final long totalSecondsSince0() { return t / MILLIS_PER_SECOND; };
    private final long totalMinutesSince0() { return t / MILLIS_PER_MINUTE; };
    private final long totalHoursSince0()   { return t / MILLIS_PER_HOUR; };

    static Time endOfLastMinute() { return new Time(now().totalMinutesSince0() * MILLIS_PER_MINUTE - 1); }
    static Time endOfThisMinute() { return new Time((now().totalMinutesSince0() + 1) * MILLIS_PER_MINUTE - 1); }

    int second() { return (int) totalSecondsSince0() % 60; }
    int minute() { return (int) totalMinutesSince0() % 60; }
    int hour()   { return (int) totalHoursSince0() % 24; }
    int day()    { return (int) (timeThisYear / MILLIS_PER_DAY); }
    int year()   { return index + 1970; }

    static Time now() { return new Time(System.currentTimeMillis()); }

    boolean inThePast()   { return t < System.currentTimeMillis(); }
    boolean inTheFuture() { return t > System.currentTimeMillis(); }

    @Override public String toString() {
        return year() + "/" + pad3(day()) + " " + pad(hour()) + ":" + pad(minute()) + ":" + pad(second());
    }

    private static String pad(int count)  { return Pad.last(2,count); }
    private static String pad3(int count) { return Pad.last(3,count); }

}
