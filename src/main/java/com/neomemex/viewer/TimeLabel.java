package com.neomemex.viewer;

import com.neomemex.shared.Time;

import javax.swing.*;
import java.awt.*;

final class TimeLabel extends JLabel {

    private Time time = new Time(0);

    TimeLabel() {
        super("----/--- --:--:--");
        setPreferredSize(new Dimension(130,20));
        setFont(new Font( "Monospaced", Font.PLAIN, 12 ));
    }

    Time getTime() {return time;}

    void setTime(Time time) {
        this.time = time;
        setText(time.toString());
    }
}
