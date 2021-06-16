package com.neomemex.shared;

public final class TimeRange {

    public final Time start;
    public final Time end;

    private TimeRange(Time start, Time end) {
        this.start = start;
        this.end = end;
    }

    public static TimeRange of(Time t1, Time t2) {
        return t1.before(t2) ? new TimeRange(t1,t2) : new TimeRange(t2,t1);
    }

    public boolean contains(Time time) {
        return time.after(start) && time.before(end);
    }

    public TimeRange plus(Time time) {
        if (time.before(start)) {
            return of(time,end);
        }
        if (time.after(end)) {
            return of(start,time);
        }
        return this;
    }
}
