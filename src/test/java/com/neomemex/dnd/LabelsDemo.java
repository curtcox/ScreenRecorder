package com.neomemex.dnd;

import com.neomemex.DemoFrame;
import javax.swing.*;
import java.awt.*;

final class LabelsDemo {
    static void setContents(final DemoFrame demo) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                demo.frame.add(new JPanel() {{
                    add(text("red"));
                    add(text("green"));
                    add(text("blue"));
                }});
                demo.show();
            }
        });
    }

    static JComponent text(String s) {
        return new JTextField(s) {{
            setEnabled(true);
            setEditable(false);
        }};
    }

    public static void main(String[] args) throws Exception {
        DemoFrame original = DemoFrame.title("original");
        setContents(original);
    }
}
