package com.neomemex.shared;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class Screen {

    static final Robot robot = newRobot();

    private static Robot newRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static BufferedImage shot() {
        return robot.createScreenCapture(new Rectangle(size()));
    }

    static Dimension size() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static int width() {  return size().width; }
    public static int height() { return size().height; }
}
