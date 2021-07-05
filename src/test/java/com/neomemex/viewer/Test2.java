package com.neomemex.viewer;

import java.awt.*;
import java.awt.font.*;
import javax.swing.*;


public class Test2 extends JPanel {
    public Test2() {
        setBackground(Color.white);
    }

    public void paint(Graphics g) {
        Graphics2D g2D;
        g2D = (Graphics2D) g;

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        FontRenderContext frc = g2D.getFontRenderContext();
        //new javax.swing.plaf.basic.BasicTextUI()
        Font font1 = new Font("Courier", Font.BOLD, 24);
        String str1 = "Java";
        TextLayout tl = new TextLayout(str1, font1, frc);
        g2D.setColor(Color.gray);
        tl.draw(g2D, 50, 150);
    }

    public static void main(String s[]) {
        JFrame frame1 = new JFrame("2D Text");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame1.getContentPane().add("Center", new Test2());
        frame1.pack();
        frame1.setSize(new Dimension(500, 300));
        frame1.setVisible(true);
    }
}