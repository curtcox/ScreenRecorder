package com.neomemex.shared;

public final class Pad {

    public static String last(int digitCount,int number) {
        String text = "0000" + number;
        return text.substring(text.length() - digitCount);
    }

}
