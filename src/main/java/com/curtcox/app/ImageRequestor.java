package com.curtcox.app;

/**
 * Invokers will be on the EDT, so implementors should return immediately without blocking.
 */
interface ImageRequestor {
    void request(String text, int days,int minutes, int seconds);
}
