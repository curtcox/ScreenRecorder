package com.neomemex.viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

final class ImageCopier {

    private static void show(String title, BufferedImage image) {
        JFrame frame = new JFrame(title);
        frame.add(new JLabel(new ImageIcon(image)));
        frame.setSize(900,800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    static void main0() {
        BufferedImage original = Screen.shot();
        show("original",original);

        RasterSerializer serializer = new RasterSerializer(original);
        BufferedImage copy = RasterDeserializer.image(serializer.image());
        show("copy", copy);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() { main0(); }
        });
    }

}
