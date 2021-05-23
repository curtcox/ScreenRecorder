package com.curtcox.app;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

final class Convert {

    static byte[] toBytes(int[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(data);
        return byteBuffer.array();
    }
}
