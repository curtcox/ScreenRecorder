package com.neomemex.viewer;

final class Pad {

    static String last(int digitCount,int number) {
        String text = "0000" + number;
        return text.substring(text.length() - digitCount);
    }

}
