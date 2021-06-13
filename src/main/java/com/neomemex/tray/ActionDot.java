package com.neomemex.tray;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

abstract class ActionDot extends TrayIcon {

    ActionDot(Color color) {
        super(circleImage(color));
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                perform();
            }
        });
    }

    abstract void perform();

    static BufferedImage circleImage(Color color) {
        int diameter = 10;
        BufferedImage image = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(0,0,diameter,diameter);
        g2d.dispose();
        return image;
    }

}
