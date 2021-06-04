package com.curtcox.app;

final class TimeCalculator {

    private final Time start;
    private final Time end;
    private final long duration;

    TimeCalculator(Time start, Time end) {
        this.start = start;
        this.end = end;
        duration = end.t - start.t;
    }

    TimeCalculator() {
        this(Time.now(),Time.now());
    }

    Time timeFrom(double years, double days, double hours, double minutes, double seconds) {
        long t = 0;
        return new Time(t);
    }
}
