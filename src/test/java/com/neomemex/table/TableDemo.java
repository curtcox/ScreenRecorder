package com.neomemex.table;

import javax.swing.*;
import javax.swing.text.*;

final class TableDemo extends JFrame {

    final JEditorPane edit = new JEditorPane();

    public TableDemo() throws BadLocationException {
        super("Tables in JEditorPane example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        edit.setEditorKit(new TableEditorKit());
        edit.setEditable(false);
        initTableDemo();

        this.getContentPane().add(new JScrollPane(edit));
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
    }

    private void initTableDemo() throws BadLocationException {
        TableDocument doc = (TableDocument) edit.getDocument();
        doc.insertTable(0, 2, new int[] {200, 100, 150});
        doc.insertTable(4, 2, new int[] {100, 50});

//        doc.insertString(10, "Paragraph after table.\nYou can set caret in table cell and start typing.", null);
//        doc.insertString(4, "Inner Table", null);
//        doc.insertString(3, "Cell with a nested table", null);
//        doc.insertString(0, "Table\nCell", null);
        doc.insertString(10, "10", null);
        doc.insertString(9, "9", null);
        doc.insertString(8, "8", null);
        doc.insertString(7, "7", null);
        doc.insertString(6, "6", null);
        doc.insertString(5, "5", null);
        doc.insertString(4, "4", null);
        doc.insertString(3, "3", null);
        doc.insertString(2, "2", null);
        doc.insertString(1, "1", null);
        doc.insertString(0, "0", null);
    }

    public static void main(String[] args) throws BadLocationException {
        new TableDemo().setVisible(true);
    }
}
