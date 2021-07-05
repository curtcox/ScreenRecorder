package com.neomemex.viewer;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;


public class Test {

    static class CustomTextArea extends JTextArea {
        @Override
        public Rectangle modelToView(int pos) throws BadLocationException {
            System.out.println("pos = " + pos);
            return super.modelToView(pos);
        }
        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
        @Override
        public void paintChildren(Graphics g) {
            super.paintChildren(g);
        }
        @Override
        public void paintComponents(Graphics g) {
            super.paintComponents(g);
        }
        @Override
        public void paintBorder(Graphics g) {
            super.paintBorder(g);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                JTextArea textArea = new CustomTextArea();
                //new javax.swing.plaf.basic.BasicTextFieldUI();
                //new javax.swing.text.PlainView();
                textArea.setText(
                        "text area plus lots of stuff so this will wrap and wrap and other stuff and so on 1 2 3 4 5 6 \n" +
                        "text area plus lots of stuff so this will wrap and wrap and other stuff and so on 1 2 3 4 5 6 \n" +
                        "text area plus lots of stuff so this will wrap and wrap and other stuff and so on 1 2 3 4 5 6 \n" +
                        "text area plus lots of stuff so this will wrap and wrap and other stuff and so on 1 2 3 4 5 6 \n" +
                        "text area plus lots of stuff so this will wrap and wrap and other stuff and so on 1 2 3 4 5 6 \n"
                                + textArea.getUI());
                textArea.setEditable(false);
                frame.setLayout(new FlowLayout());
                JTextField textField = new JTextField();
                new JEditorPane();
                textField.setText("text field " + textField.getUI());
                frame.add(textField);
                frame.add(textArea);
                JTextField textField2 = new JTextField();
                JTextField textField3 = new JTextField();
                textField2.setText("transfer handler " + textField2.getTransferHandler());
                textField3.setText("view " + textField3);
                frame.add(textField2);
                frame.add(textField3);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    static RuntimeException no() {
        return new RuntimeException();
    }
}