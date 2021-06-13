package com.neomemex.tray;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A menu item that knows what it does.
abstract class ActionItem extends MenuItem {

    public ActionItem(String text) {
        super(text);
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                perform();
            }
        });
    }

    abstract void perform();
}
