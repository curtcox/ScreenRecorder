package com.curtcox.app;

import java.awt.*;
import java.awt.image.BufferedImage;

final class Screen {

    static final Robot robot = newRobot();

    private static Robot newRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    static BufferedImage shot() {
        return robot.createScreenCapture(new Rectangle(size()));
    }

    static Dimension size() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    static int width() {  return size().width; }
    static int height() { return size().height; }
}
