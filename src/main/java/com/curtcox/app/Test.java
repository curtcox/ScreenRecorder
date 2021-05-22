package com.curtcox.app;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;

final class Test {

    static BufferedImage image(int[] data, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, DataBuffer.TYPE_INT);
        WritableRaster raster = bufferedImage.getRaster();
        raster.setDataElements(0,0,width,height,data);
        print("raster = " + raster);
        bufferedImage.setData(raster);
        print("bufferedImage = " + bufferedImage);
        return bufferedImage;
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
        show("copy", image(serializer.data(),Screen.width(),Screen.height()));
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
