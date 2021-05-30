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

    final JFrame frame = new JFrame("Viewer");
    final JSlider days = new JSlider();
    final JSlider minutes = new JSlider();
    final JSlider seconds = new JSlider();
    final JTextField search = new JTextField();
    final JLabel imageLabel = new JLabel();
    final Listener listener = new Listener();
    final Viewer.Requestor requestor;

    // On change, update the request
    private class Listener implements ChangeListener, DocumentListener {
        @Override public void stateChanged(ChangeEvent e)    { updateRequest(); }
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

    void setSize(int width,int height) {
        frame.setSize(width,height);
        frame.setMinimumSize(new Dimension(width/2,height/2));
    }

    void exitOnClose() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void layout() {
        days.setOrientation(JSlider.VERTICAL);
        frame.setLayout(new BorderLayout());
        frame.add(imageLabel,BorderLayout.CENTER);
        frame.add(search,BorderLayout.NORTH);
        frame.add(days,BorderLayout.WEST);
        JPanel south = new JPanel(new GridLayout(0,2));
        south.add(minutes);
        south.add(seconds);
        frame.add(south,BorderLayout.SOUTH);
    }

    void addListeners() {
        days.addChangeListener(listener);
        minutes.addChangeListener(listener);
        seconds.addChangeListener(listener);
        search.getDocument().addDocumentListener(listener);
    }

    public void setImage(BufferedImage image) {
        imageLabel.setIcon(new ImageIcon(image.getScaledInstance(image.getWidth(),image.getHeight(),16)));
    }

    void updateRequest() {
        requestor.request(new Viewer.Request(search.getText(),days.getValue(),minutes.getValue(),seconds.getValue()));
    }

    void show() {
        frame.setVisible(true);
    }

}
