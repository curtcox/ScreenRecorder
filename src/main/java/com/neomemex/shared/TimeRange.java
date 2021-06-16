package com.neomemex.shared;

public final class TimeRange {

    public final Time start;
    public final Time end;

    private TimeRange(Time start, Time end) {
        this.start = start;
        this.end = end;
    }

    public static TimeRange of(Time t1, Time t2) {
        return new TimeRange(t1,t2);
    }

    public boolean contains(Time time) {
        return time.after(start) && time.before(end);
    }
}
