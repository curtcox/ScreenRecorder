package com.neomemex.store;

import java.io.IOException;

public class RuntimeIOException extends RuntimeException {
    public RuntimeIOException(IOException cause) {
        super(cause);
    }
}
