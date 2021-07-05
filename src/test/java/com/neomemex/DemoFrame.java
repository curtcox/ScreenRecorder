package com.neomemex;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public final class DemoFrame {

    public final JFrame frame;

    public DemoFrame(String title) {
        frame = new JFrame(title);
        frame.setSize(900,800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void show() {
        frame.setVisible(true);
    }

    public static DemoFrame title(final String title) throws ExecutionException, InterruptedException {
        FutureTask<DemoFrame> future = new FutureTask<>(new Callable<DemoFrame>() {
            @Override
            public DemoFrame call() {
                DemoFrame frame = new DemoFrame(title);
                return frame;
            }
        });
        EventQueue.invokeLater(future);
        return future.get();
    }

}
