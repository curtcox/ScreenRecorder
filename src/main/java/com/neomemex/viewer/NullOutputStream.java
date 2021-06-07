package com.neomemex.viewer;

import java.io.OutputStream;

final class NullOutputStream extends OutputStream {
    @Override
    public void write(int b) {}
}
