package com.neomemex.multicolumn;

import javax.swing.*;

public final class Application extends JFrame {

    final JEditorPane edit = new JEditorPane();

    public Application() {
        super("Multicolumn text example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        edit.setEditorKit(new MultiColumnEditorKit());
        edit.setEditorKit(new CustomLayoutEditorKit());

        this.getContentPane().add(edit);
        StringBuilder out = new StringBuilder();
        for (int i=0; i< 99; i++) {
            for (int j=0; j<10; j++) {
                out.append(" " + i + "," + j);
            }
            out.append("\n");
        }
        edit.setText(out.toString());
        this.setSize(800,800);
    }

    public static void main(String[] args) {
        Application m = new Application();
        m.setVisible(true);
    }

}



