package com.curtcox.app;

final class Time {

    final long t;

    private static long MILLIS_PER_MINUTE     = 60 * 1000;
    private static long MILLIS_PER_DAY        = MILLIS_PER_MINUTE * 60 * 24;
    private static long MILLIS_PER_YEAR       = MILLIS_PER_DAY    * 365;
    private static long MILLIS_PER_LEAPYEAR   = 4 * MILLIS_PER_YEAR + MILLIS_PER_DAY;
    private static long TWO_YEARS = 2 * MILLIS_PER_YEAR;

    Time(long t) {
        this.t = t;
    }

    static Time endOfLastMinute() {
        return new Time(now().totalMinutesSince0() * MILLIS_PER_MINUTE - 1);
    }

    static Time endOfThisMinute() {
        return new Time((now().totalMinutesSince0() + 1) * MILLIS_PER_MINUTE - 1);
    }

    private long totalMinutesSince0()   { return t / MILLIS_PER_MINUTE; }
    private long totalDaysSince0()      { return t / MILLIS_PER_DAY;    }
    private long totalYearsSince0()     { return t / MILLIS_PER_YEAR;   }
    private long totalLeapYearsSince0() { return (t  +  TWO_YEARS) / MILLIS_PER_LEAPYEAR;   }

    int minute() { return (int) mod(totalMinutesSince0(),60); }
    int day()    {
        long justThisYear = totalDaysSince0() - totalYearsSince0() * 365 - totalLeapYearsSince0();
        return (int) mod(justThisYear,daysInThisYear());
    }

    long mod(long x, long y) {
        long z = x % y;
        return z >= 0 ? z : y + z;
    }

    int year() {
        int base = t < 0 ? 1969 : 1970;
        return (int) (base + totalYearsSince0() - totalLeapYearsSince0());
    }

    long daysInThisYear() { return isLeapYear() ? 366 : 365; }
    boolean isLeapYear()  { return year() % 4 == 0; }

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
