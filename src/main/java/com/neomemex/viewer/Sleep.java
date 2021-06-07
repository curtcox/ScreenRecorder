package com.neomemex.viewer;

final class Sleep {

    static void second() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            print("???");
        }
    }

    static void millis(int m) {
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
