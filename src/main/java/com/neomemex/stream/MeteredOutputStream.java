package com.neomemex.stream;

import java.io.IOException;
import java.io.OutputStream;

public final class MeteredOutputStream extends OutputStream {

    private int count;
    private final OutputStream out;
    private final long started = System.currentTimeMillis();

    public MeteredOutputStream(OutputStream out) {
        this.out = out;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
        reportIfNeeded();
        count++;
    }

    private void reportIfNeeded() {
        if (count % 100000 == 0) {
            report();
        }
    }

    private void report() {
        long duration = duration();
        double megs = ((double) count) / (1024 * 1024);
        double minutes = ((double) duration) / (1000 * 60);
        String megPerMinute = minutes == 0.0 ? "?" : (megs / minutes) + "";
        print(count + "/" + duration + " -> " + megPerMinute);
    }

    private long duration() {
        return System.currentTimeMillis() - started;
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }

    @Override
    public void close() throws IOException {
        report();
        out.close();
    }

    private static void print(Object o) {
        System.out.println(o);
    }

}
