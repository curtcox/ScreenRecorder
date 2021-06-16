package com.neomemex.viewer;

import com.neomemex.DemoFrame;
import com.neomemex.recorder.RasterSerializer;
import com.neomemex.shared.Screen;
import com.neomemex.shared.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

final class ImageCopierDemo {

    static void setContents(final DemoFrame demo, final BufferedImage image) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                demo.frame.add(new JLabel(new ImageIcon(image)));
                demo.show();
            }
        });
    }

    static BufferedImage copy(BufferedImage original) {
        return RasterDeserializer.image(RasterSerializer.serialize(original, Time.now()));
    }

    public static void main(String[] args) throws Exception {
        DemoFrame original = DemoFrame.title("original");
        DemoFrame copy     = DemoFrame.title("copy");
        BufferedImage shot = Screen.shot();
        setContents(original,shot);
        setContents(copy,copy(shot));
    }

}
