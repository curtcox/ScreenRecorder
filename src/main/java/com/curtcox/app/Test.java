package com.curtcox.app;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

final class Test {

    // After much frustration, copying code from Robot worked...
    static BufferedImage image(int[] pixels, int width, int height) {
        DataBufferInt buffer = new DataBufferInt(pixels, pixels.length);
        DirectColorModel screenCapCM = new DirectColorModel(24,
                /* red mask */ 0x00FF0000,
                /* green mask */ 0x0000FF00,
                /* blue mask */ 0x000000FF);
        int[] bandmasks = new int[3];

        bandmasks[0] = screenCapCM.getRedMask();
        bandmasks[1] = screenCapCM.getGreenMask();
        bandmasks[2] = screenCapCM.getBlueMask();

        WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, bandmasks, null);

        return new BufferedImage(screenCapCM, raster, false, null);
    }

    private static void show(String title, BufferedImage image) {
        JFrame frame = new JFrame(title);
        frame.add(new JLabel(new ImageIcon(image)));
        frame.setSize(900,800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static void print(Object o) {
        System.out.println(o);
    }

    static void main0() {
        print(Screen.width() + "*" + Screen.height() + " = " + Screen.width()*Screen.height());
        BufferedImage original = Screen.shot();
        show("original",original);

        RasterSerializer serializer = new RasterSerializer(original);
        BufferedImage copy = image(serializer.data(),Screen.width(),Screen.height());
        show("copy", copy);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                main0();
            }
        });
    }

}
