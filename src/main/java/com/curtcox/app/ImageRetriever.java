package com.curtcox.app;

/**
 * Implementors must never be on the EDT.
 */
interface ImageRetriever {
    Image setTime(long time);
    Image setText(String text);
}
