package com.neomemex.shared;

public final class Sleep {

    public static void second(int count) {
        try {
            Thread.sleep(count * 1000);
        } catch (InterruptedException e) {
            print("???");
        }
    }

    public static void millis(int m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            print("???");
        }
    }

    private static void print(Object o) {
        System.out.println(o);
    }

}
