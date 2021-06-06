package com.curtcox.app;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.image.BufferedImage;

// EDT only
final class ScreenLogViewer implements Viewer.Display {

    int lastTimePartChanged = 0;
    final JFrame      frame = new JFrame("Viewer");
    final JSlider     years = slider(0);
    final JSlider      days = slider(1);
    final JSlider     hours = slider(2);
    final JSlider   minutes = slider(3);
    final JSlider   seconds = slider(4);
    final JTextField search = new JTextField();
    final JLabel imageLabel = new JLabel();
    final JLabel  timeLabel = new JLabel();
    final Listener listener = new Listener();
    final Viewer.Requestor requestor;
    final TimeCalculator calculator = new TimeCalculator();

    // On change, update the request
    private class Listener implements ChangeListener, DocumentListener {
        @Override public void stateChanged(ChangeEvent e)    {
            TimePartSlider slider = (TimePartSlider) e.getSource();
            slider.setToolTipText(Time.now().toString());
            lastTimePartChanged = slider.index;
            updateRequest();
        }
        @Override public void insertUpdate(DocumentEvent e)  { updateRequest(); }
        @Override public void removeUpdate(DocumentEvent e)  { updateRequest(); }
        @Override public void changedUpdate(DocumentEvent e) { updateRequest(); }
    }

    ScreenLogViewer(Viewer.Requestor requestor, int width, int height) {
        this.requestor = requestor;
        layout();
        setSize(width,height);
        addListeners();
        exitOnClose();
    }

    private static TimePartSlider slider(int index) { return new TimePartSlider(index); }
    private static final class TimePartSlider extends JSlider {
        final int index;
        TimePartSlider(int index) {
            this.index = index;
            setMinimum(0);
            setMaximum(10000);
        }
    }

    void setSize(int width,int height) {
        frame.setSize(width,height);
        frame.setMinimumSize(new Dimension(width/2,height/2));
    }

    void exitOnClose() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void layout() {
        years.setOrientation(JSlider.VERTICAL);
        days.setOrientation(JSlider.VERTICAL);
        frame.setLayout(new BorderLayout());
        frame.add(imageLabel,BorderLayout.CENTER);
        frame.add(search,BorderLayout.NORTH);
        JPanel west = new JPanel(new GridLayout(2,0));
        west.add(years);
        west.add(days);
        frame.add(west,BorderLayout.WEST);
        JPanel bottomSliders = new JPanel(new GridLayout(0,3));
        bottomSliders.add(hours);
        bottomSliders.add(minutes);
        bottomSliders.add(seconds);
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(timeLabel,BorderLayout.WEST);
        bottom.add(bottomSliders,BorderLayout.CENTER);
        frame.add(bottom,BorderLayout.SOUTH);
        timeLabel.setPreferredSize(new Dimension(130,20));
        timeLabel.setFont(new Font( "Monospaced", Font.PLAIN, 12 ));
    }

    void addListeners() {
        years.addChangeListener(listener);
        days.addChangeListener(listener);
        hours.addChangeListener(listener);
        minutes.addChangeListener(listener);
        seconds.addChangeListener(listener);
        search.getDocument().addDocumentListener(listener);
    }

    public void setImage(BufferedImage image) {
        imageLabel.setIcon(new ImageIcon(image.getScaledInstance(image.getWidth(),image.getHeight(),16)));
    }

    void updateRequest() {
        Time time = fromSliders();
        updateTimeLabel(time);
        requestor.request(new Viewer.Request(search.getText(),time));
    }

    private Time fromSliders() {
        double[] parts = new double[] {value(years),value(days),value(hours),value(minutes),value(seconds)};
        return calculator.timeFrom(parts,lastTimePartChanged);
    }

    private void updateTimeLabel(Time time) {
        timeLabel.setText(" " + time.toString());
    }

    private static double value(JSlider slider) {
        return ((double) slider.getValue()) / ((double) slider.getMaximum());
    }

    void show() {
        frame.setVisible(true);
    }

}
