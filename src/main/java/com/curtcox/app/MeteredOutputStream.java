package com.curtcox.app;

import java.io.IOException;
import java.io.OutputStream;

public final class MeteredOutputStream extends OutputStream {

    private int count;
    private final OutputStream out;

    public MeteredOutputStream(OutputStream out) {
        this.out = out;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
        count++;
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
