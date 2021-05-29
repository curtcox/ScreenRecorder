package com.curtcox.app;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;

final class ScreenLogViewer {

    final JFrame frame = new JFrame("Viewer");
    final JSlider days = new JSlider();
    final JSlider minutes = new JSlider();
    final JSlider seconds = new JSlider();
    final JTextField search = new JTextField();
    final JLabel imageLabel = new JLabel();
    final int width;
    final int height;
    final TimeChangeListener listener = new TimeChangeListener();

    private class TimeChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            updateTime();
        }
    }

    ScreenLogViewer(BufferedImage image) {
        double scale = 0.75;
        width = (int) (image.getWidth() * scale);
        height = (int) (image.getHeight() * scale);
        layout();
        frame.setVisible(true);
        setImage(image);
        setSize();
        addListeners();
        exitOnClose();
    }

    void setSize() {
        frame.setSize(width,height);
        frame.setMinimumSize(new Dimension(width/2,height/2));
    }

    void exitOnClose() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void layout() {
        days.setOrientation(JSlider.VERTICAL);
        frame.setLayout(new BorderLayout());
        frame.add(imageLabel,BorderLayout.CENTER);
        frame.add(search,BorderLayout.NORTH);
        frame.add(days,BorderLayout.WEST);
        JPanel south = new JPanel(new GridLayout(0,2));
        south.add(minutes);
        south.add(seconds);
        frame.add(south,BorderLayout.SOUTH);
    }

    void addListeners() {
        days.addChangeListener(listener);
        minutes.addChangeListener(listener);
        seconds.addChangeListener(listener);
    }

    void setImage(BufferedImage image) {
        imageLabel.setIcon(new ImageIcon(image.getScaledInstance(width,height,16)));
    }

    void updateTime() {
        String message = days.getValue() + " " + minutes.getValue() + " " + seconds.getValue();
        System.out.println(message);
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
