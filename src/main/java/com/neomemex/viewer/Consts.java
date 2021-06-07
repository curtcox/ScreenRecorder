package com.neomemex.viewer;

final class Consts {

    static final byte ZERO = 0;

    static final int IHDR_SIG = 0x49484452;
    static final int acTL_SIG = 0x6163544c;
    static final int IDAT_SIG = 0x49444154;
    static final int fdAT_SIG = 0x66644154;
    static final int fcTL_SIG = 0x6663544c;
    static final int IEND_SIG = 0x49454e44;

    static final int CHUNK_DELTA =
            4 //chunk len
                    + 4 //header
                    + 4;//CRC

    static final int IHDR_DATA_LEN = 13;
    static final int IHDR_TOTAL_LEN = IHDR_DATA_LEN + CHUNK_DELTA;

    static final int fcTL_DATA_LEN = 26;
    static final int fcTL_TOTAL_LEN = fcTL_DATA_LEN + CHUNK_DELTA;

    //http://www.w3.org/TR/PNG/#5PNG-file-signature
    private static final byte[] PNG_SIG = new byte[] {(byte) 0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a};
    private static final byte[] IEND_ARR = new byte[] {
            0,	  0,   0,   0,
            'I', 'E', 'N', 'D',
            (byte) 0xae, 0x42,
            0x60, (byte) 0x82 //ae4260820
    };

    private static final byte[] acTL_ARR = new byte[] {
            0,	  0,   0,   8,
            'a', 'c', 'T', 'L',
            0,   0,   0,   0,
            0,   0,   0,   0,
            0,   0,   0,   0
    };

    static byte[] getacTLArr() {
        return acTL_ARR.clone();
    }
    static byte[] getIENDArr() {
        return IEND_ARR.clone();
    }

    static byte[] getPNGSIGArr() {
        return PNG_SIG.clone();
    }

    static final int bands = 3;

}
