package com.curtcox.app;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;

final class ScreenLogViewer {

    final JFrame frame = new JFrame("Viewer");
    final JSlider slider = new JSlider();
    final JLabel imageLabel = new JLabel();
    final int width;
    final int height;

    ScreenLogViewer(BufferedImage image) {
        double scale = 0.75;
        width = (int) (image.getWidth() * scale);
        height = (int) (image.getHeight() * scale);
        layout();
        setImage(image);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setImageIndex(slider.getValue());
            }
        });
    }

    void layout() {
        frame.setLayout(new BorderLayout());
        frame.add(imageLabel,BorderLayout.CENTER);
        frame.add(slider,BorderLayout.SOUTH);
        frame.setSize(width,height);
        frame.setMinimumSize(new Dimension(width/2,height/2));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void setImage(BufferedImage image) {
        imageLabel.setIcon(new ImageIcon(image.getScaledInstance(width,height,16)));
    }

    void setImageIndex(int index) {
        System.out.println(index);
    }

    void show() {
        frame.setVisible(true);
    }

    static void main0() {
        BufferedImage original = Screen.shot();
        ScreenLogViewer viewer = new ScreenLogViewer(original);
        viewer.show();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() { main0(); }
        });
    }

}
