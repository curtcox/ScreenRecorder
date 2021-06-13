package com.neomemex.tray;

import com.neomemex.recorder.Recorder;
import com.neomemex.viewer.Viewer;

import javax.swing.*;
import java.awt.*;

public final class Tray {

    final Recorder recorder;
    final Viewer.Display display;
    final PopupMenu popup = new PopupMenu();
    final TrayIcon blackCircle = new ActionDot(Color.BLACK)   { @Override void perform() { onBlackDot();   } };
    final TrayIcon   redCircle = new ActionDot(Color.RED)     { @Override void perform() { onRedDot();     } };
    final MenuItem    about = new ActionItem("About")    { @Override void perform() { showAbout();    } };
    final MenuItem settings = new ActionItem("Settings") { @Override void perform() { showSettings(); } };
    final MenuItem   viewer = new ActionItem("Viewer")   { @Override void perform() { showViewer();   } };
    final MenuItem     exit = new ActionItem("Exit")     { @Override void perform() { exit();         } };
    final SystemTray   tray = SystemTray.getSystemTray();

    Tray(Recorder recorder, Viewer.Display display) {
        this.recorder = recorder;
        this.display = display;
    }

    public static void install(Recorder recorder, Viewer.Display display) {
        new Tray(recorder,display).install();
    }

    void install() {
        addMenuItems();
        blackCircle.setPopupMenu(popup);
        add(blackCircle);
    }

    void add(TrayIcon icon) {
        try {
            tray.add(icon);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private void switchToRedButton() {
        tray.remove(blackCircle);
        add(redCircle);
    }

    private void switchToBlackButton() {
        tray.remove(redCircle);
        add(blackCircle);
    }

    private void onBlackDot() {
        switchToRedButton();
        recorder.start();
    }

    private void onRedDot() {
        switchToBlackButton();
        recorder.stop();
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(null, "Use this to record the screen");
    }

    private void showSettings() {
        JOptionPane.showMessageDialog(null, "Future settings");
    }

    private void showViewer() {
        display.show();
    }

    private void exit() {
        tray.remove(redCircle);
        System.exit(0);
    }

    void addMenuItems() {
        popup.add(about);
        popup.addSeparator();
        popup.add(settings);
        popup.addSeparator();
        popup.add(viewer);
        popup.addSeparator();
        popup.add(exit);
    }

}