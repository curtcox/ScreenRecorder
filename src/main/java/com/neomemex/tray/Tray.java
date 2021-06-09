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

    Tray(Recorder recorder) {
        this.recorder = recorder;
    }

    static void install(Recorder recorder) {
        new Tray(recorder).install();
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

    void install() {
        addMenuItems();
        blackCircle.setPopupMenu(popup);
        add(blackCircle);
        addListeners();
    }

    void add(TrayIcon icon) {
        try {
            tray.add(icon);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    void addListeners() {
        addBlackCircleListener();
        addRedCircleListener();
        addAboutListener();
        addExitListener();
    }

    private void switchToRedButton() {
        tray.remove(blackCircle);
        add(redCircle);
    }

    private void switchToBlackButton() {
        tray.remove(redCircle);
        add(blackCircle);
    }

    void addBlackCircleListener() {
        blackCircle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchToRedButton();
                recorder.start();
            }
        });
    }

    void addRedCircleListener() {
        redCircle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchToBlackButton();
                recorder.stop();
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

    void addMenuItems() {
        popup.add(about);
        popup.addSeparator();
        popup.addSeparator();
        popup.add(exit);
    }

}