package com.neomemex.shared;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public final class Convert {

    public static byte[] toBytes(int[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(data);
        return byteBuffer.array();
    }

    public static int[] toInts(byte[] data) {
        IntBuffer intBuf = ByteBuffer.wrap(data)
                        .asIntBuffer();
        int[] array = new int[intBuf.remaining()];
        intBuf.get(array);
        return array;
    }

}
