package com.neomemex.viewer;

import com.neomemex.ocr.OCR;
import com.neomemex.shared.Highlight;
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

    final JFrame             frame = new JFrame("Viewer");
    final TimePartSlider     years = slider(0);
    final TimePartSlider      days = slider(1);
    final TimePartSlider     hours = slider(2);
    final TimePartSlider   minutes = slider(3);
    final TimePartSlider   seconds = slider(4);
    final JTextField search = new JTextField();
    final ImagePanel imagePanel = ImagePanel.of();
    final TimeLabel targetTime = new TimeLabel();
    final TimeLabel actualTime = new TimeLabel();
    final Listener listener = new Listener();
    final Viewer.Requestor requestor;
    final TimeCalculator calculator = new TimeCalculator();
    private TimePartSlider lastTimePartChanged;
    private Time[] times = new Time[0];

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

    void setSize(int width,int height) {
        frame.setSize(width,height);
        frame.setMinimumSize(new Dimension(width/2,height/2));
    }

    void layout() {
        years.setOrientation(JSlider.VERTICAL);
        days.setOrientation(JSlider.VERTICAL);
        frame.setLayout(new BorderLayout());
        frame.add(imagePanel,BorderLayout.CENTER);
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
        bottom.add(targetTime,BorderLayout.WEST);
        bottom.add(actualTime,BorderLayout.EAST);
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

    @Override
    public void setImage(BufferedImage image) {
        imagePanel.setImage(image);
    }

    @Override
    public void setHighlight(Highlight highlight) {
        imagePanel.setHighlight(highlight);
    }

    @Override
    public void setTime(Time time,Time[] times) {
        this.times = times;
        actualTime.setTime(time);
    }

    @Override
    public void setWords(OCR.Word[] words) {
        imagePanel.setWords(words);
    }

    void updateRequest(boolean full) {
        Time time = fromSliders();
        targetTime.setTime(time);
        updateTime(time);
        if (full) {
            requestor.request(new Viewer.Request(search.getText(),time));
        }
    }

    private Time fromSliders() {
        double[] parts = new double[] {value(years),value(days),value(hours),value(minutes),value(seconds)};
        int focus = lastTimePartChanged == null ? 0 : lastTimePartChanged.index;
        return calculator.timeFrom(targetTime.getTime(),parts,focus);
    }

    private void updateTime(Time time) {
        setYears(time);
        setDays(time);
        setHours(time);
        setMinutes(time);
        setSeconds(time);
        years.setMarks(calculator.years(times));
        days.setMarks(calculator.days(time,times));
        hours.setMarks(calculator.hours(time,times));
        minutes.setMarks(calculator.minutes(time,times));
        seconds.setMarks(calculator.seconds(time,times));
    }

    private void   setYears(Time time) { set(years,calculator.year(time)); }
    private void    setDays(Time time) { set(days,calculator.day(time)); }
    private void   setHours(Time time) { set(hours,calculator.hour(time)); }
    private void setMinutes(Time time) { set(minutes,calculator.minute(time)); }
    private void setSeconds(Time time) { set(seconds,calculator.second(time)); }

    private static double value(TimePartSlider slider) {
        return (slider.getValue());
    }
    private void set(TimePartSlider slider,double value) {
        if (slider!=lastTimePartChanged) {
            slider.setValue(value);
        }
    }

    public void show() {
        frame.setVisible(true);
    }

}
