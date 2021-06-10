package com.neomemex.viewer;

import com.neomemex.shared.Time;
import com.neomemex.shared.TimeCalculator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.image.BufferedImage;

// EDT only
final class ScreenLogViewer implements Viewer.Display {

    private TimePartSlider lastTimePartChanged;
    final JFrame      frame = new JFrame("Viewer");
    final TimePartSlider     years = slider(0);
    final TimePartSlider      days = slider(1);
    final TimePartSlider     hours = slider(2);
    final TimePartSlider   minutes = slider(3);
    final TimePartSlider   seconds = slider(4);
    final JTextField search = new JTextField();
    final JLabel imageLabel = new JLabel();
    final JLabel  timeLabel = newTimeLabel();
    final Listener listener = new Listener();
    final Viewer.Requestor requestor;
    final TimeCalculator calculator = new TimeCalculator();
    private static final int MAX = 10000;

    // On change, update the request
    private class Listener implements ChangeListener, DocumentListener {
        @Override public void stateChanged(ChangeEvent e)    {
            if (lastTimePartChanged==null) {
                userInitiated(e);
            }
        }
        void userInitiated(ChangeEvent e)    {
            try {
                lastTimePartChanged = (TimePartSlider) e.getSource();
                updateRequest(!lastTimePartChanged.getValueIsAdjusting());
            } finally {
                lastTimePartChanged = null;
            }
        }
        @Override public void insertUpdate(DocumentEvent e)  { fullUpdate(); }
        @Override public void removeUpdate(DocumentEvent e)  { fullUpdate(); }
        @Override public void changedUpdate(DocumentEvent e) { fullUpdate(); }
        void fullUpdate() { updateRequest(true); }
    }

    ScreenLogViewer(Viewer.Requestor requestor, int width, int height) {
        this.requestor = requestor;
        layout();
        setSize(width,height);
        addListeners();
    }

    private static TimePartSlider slider(int index) { return new TimePartSlider(index); }
    private static final class TimePartSlider extends JSlider {
        final int index;
        TimePartSlider(int index) {
            this.index = index;
            setMinimum(0);
            setMaximum(MAX);
        }
    }

    private static JLabel newTimeLabel() {
        JLabel label = new JLabel("----/--- --:--:--");
        label.setPreferredSize(new Dimension(130,20));
        label.setFont(new Font( "Monospaced", Font.PLAIN, 12 ));
        return label;
    }

    void setSize(int width,int height) {
        frame.setSize(width,height);
        frame.setMinimumSize(new Dimension(width/2,height/2));
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

    void updateRequest(boolean full) {
        Time time = fromSliders();
        updateTime(time);
        if (full) {
            requestor.request(new Viewer.Request(search.getText(),time));
        }
    }

    private Time fromSliders() {
        double[] parts = new double[] {value(years),value(days),value(hours),value(minutes),value(seconds)};
        return calculator.timeFrom(parts,lastTimePartChanged.index);
    }

    private void updateTime(Time time) {
        timeLabel.setText(" " + time.toString());
        set(years,calculator.year(time));
        set(days,calculator.day(time));
        set(hours,calculator.hour(time));
        set(minutes,calculator.minute(time));
        set(seconds,calculator.second(time));
    }

    private static double value(JSlider slider) {
        return ((double) slider.getValue()) / ((double) MAX);
    }

    private void set(TimePartSlider slider,double value) {
        if (slider!=lastTimePartChanged) {
            slider.setValue((int) (MAX * value));
        }
    }

    public void show() {
        frame.setVisible(true);
    }

}
