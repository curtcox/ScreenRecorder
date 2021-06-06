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
        this(new Time(2000,0,0,0),new Time(2040,0,0,0));
    }

    Time timeFrom(double[] parts,int focus) {
        long t = (long) (start.t + (parts[0] * duration));
        return new Time(t);
    }
}
