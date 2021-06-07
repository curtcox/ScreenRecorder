package com.neomemex.recorder;

import java.nio.ByteBuffer;

/**
 * See https://www.w3.org/TR/PNG/#9Filters
 * In the future, we could pick the best filter under the covers based on something like
 * minimum sum of absolute differences. That could result in smaller PNGs if done right.
 * Caution is needed, however, since the library this comes from went to a lot more work for worse results.
 *
 * Currently, we only implement the "None" filter.
 * See https://www.oreilly.com/library/view/png-the-definitive/9781565925427/18_chapter-09.html
 */
interface Filter {
    void encode(ByteBuffer in, ByteBuffer out);
}
