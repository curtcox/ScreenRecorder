package com.neomemex.stream;

import java.io.OutputStream;

public final class NullOutputStream extends OutputStream {
    @Override
    public void write(int b) {}
}
