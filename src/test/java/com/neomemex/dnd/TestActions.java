package com.neomemex.dnd;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.text.*;

public class TestActions {
    private String[] texts = {
            "Hello", "Goodbye", "What the f***?", "Heck if I know", "Peace out man!"
    };
    private JTextArea textArea = new JTextArea(10, 30);
    private Action[] textActions = { new DefaultEditorKit.CutAction(),
            new DefaultEditorKit.CopyAction(), new DefaultEditorKit.PasteAction(), };
    private JPanel mainPanel = new JPanel();
    private JMenuBar menubar = new JMenuBar();
    private JPopupMenu popup = new JPopupMenu();
    private PopupListener popupListener = new PopupListener();

    public TestActions() {
        JPanel btnPanel = new JPanel(new GridLayout(1, 0, 5, 5));
        JMenu menu = new JMenu("Edit");
        for (Action textAction : textActions) {
            btnPanel.add(new JButton(textAction));
            menu.add(new JMenuItem(textAction));
            popup.add(new JMenuItem(textAction));
        }
        menubar.add(menu);

        JPanel textFieldPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        for (String text: texts) {
            JTextField textField = new JTextField(text, 15);
            textField.addMouseListener(popupListener);
            textFieldPanel.add(textField);
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    ((JTextComponent)e.getSource()).selectAll();
                }
            });
        }
        textArea.addMouseListener(popupListener);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel textFieldPanelWrapper = new JPanel(new BorderLayout());
        textFieldPanelWrapper.add(textFieldPanel, BorderLayout.NORTH);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(new BorderLayout(5, 5));
        mainPanel.add(btnPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(textFieldPanelWrapper, BorderLayout.EAST);
    }

    public JComponent getMainPanel() {
        return mainPanel;
    }

    private JMenuBar getMenuBar() {
        return menubar;
    }

    private class PopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                        e.getX(), e.getY());
            }
        }
    }

    private static void createAndShowGui() {
        TestActions testActions = new TestActions();

        JFrame frame = new JFrame("Test Actions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(testActions.getMainPanel());
        frame.setJMenuBar(testActions.getMenuBar());
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }
}