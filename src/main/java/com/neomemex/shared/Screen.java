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

    /**
     * The returned screenshot is OK, but blurry on my dev Mac.
     * It appears to vary based on OS and JRE.
     * Using the MultiResolutionImage API introduced in Java 9 fared no better on my machine.
     * Possible futures:
     * a) The screencapture command is nice and crisp. Perhaps it can be sent to standard out via the clipboard.
     * b) Native robot peers
     * c) JNA
     * https://bugs.openjdk.java.net/browse/JDK-8207386
     */
    public static BufferedImage shot() {
        return robot.createScreenCapture(new Rectangle(size()));
    }

    static Dimension size() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static int width() {  return size().width; }
    public static int height() { return size().height; }
}
