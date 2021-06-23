package com.neomemex.viewer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

final class TimePartSlider extends JPanel {

    final int index;
    final JSlider slider = new JSlider();
    static final int MAX = 10000;

    TimePartSlider(int index) {
        super(new GridLayout(1,1));
        this.index = index;
        slider.setMinimum(0);
        slider.setMaximum(MAX);
        add(slider);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics scratch = (g == null) ? null : g.create();
        try {
            scratch.setColor(Color.GREEN);
            mark(g,getValue());
        }
        finally {
            scratch.dispose();
        }
    }

    private void mark(Graphics g,double at) {
        if (slider.getOrientation()==JSlider.HORIZONTAL) {
            int width = getWidth();
            int x = (int) (width * 0.91 * at + width * 0.05);
            g.drawLine(x,0,x,getHeight());
        } else {
            int height = getHeight();
            int y = (int) (height * 0.96 - height * 0.92 * at);
            g.drawLine(0,y,getWidth(),y);
        }
    }

    void setValue(double value) {
        slider.setValue((int) (MAX * value));
    }

    double getValue() {
        return ((double) slider.getValue()) / ((double) MAX);
    }

    boolean getValueIsAdjusting()                   { return slider.getValueIsAdjusting(); }
    void setOrientation(int orientation)            { slider.setOrientation(orientation); }
    void addChangeListener(final ChangeListener listener) {
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                listener.stateChanged(new ChangeEvent(TimePartSlider.this));
            }
        });
    }
}
