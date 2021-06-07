package com.neomemex.tray;

import com.neomemex.recorder.Recorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Tray {

    final Recorder recorder;
    final PopupMenu popup = new PopupMenu();
    final TrayIcon blackCircle = new TrayIcon(circleImage(Color.BLACK));
    final TrayIcon   redCircle = new TrayIcon(circleImage(Color.RED));
    final MenuItem  about = new MenuItem("About");
    final MenuItem   exit = new MenuItem("Exit");
    final SystemTray tray = SystemTray.getSystemTray();

    public Tray(Recorder recorder) {
        this.recorder = recorder;
    }

    static BufferedImage circleImage(Color color) {
        int diameter = 10;
        BufferedImage image = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(0,0,diameter,diameter);
        g2d.dispose();
        return image;
    }

    void install() throws AWTException {
        addMenuItems();
        blackCircle.setPopupMenu(popup);
        tray.add(blackCircle);
        addListeners();
    }

    void addListeners() {
        addBlackCircleListener();
        addAboutListener();
        addExitListener();
    }

    void addBlackCircleListener() {
        blackCircle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                recorder.start();
            }
        });
    }

    void addAboutListener() {
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Use this to record the screen");
            }
        });
    }

    void addExitListener() {
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(redCircle);
                System.exit(0);
            }
        });
    }

    void addMenuItems() throws AWTException {
        popup.add(about);
        popup.addSeparator();
        popup.addSeparator();
        popup.add(exit);
    }

}