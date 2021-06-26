package com.neomemex.png;

import com.neomemex.shared.Screen;
import org.junit.Test;

import java.io.FileOutputStream;

import static org.junit.Assert.*;

public class PngImageEncoderTest {
    @Test
    public void screen_shots_take_a_reasonable_amount_of_space() {
        byte[] bytes = PngSequenceWriter.bytes(Screen.shot());
        String message = bytes.length + " bytes";
        assertTrue(message,bytes.length > 1000000);
        assertTrue(message,bytes.length < 10000000);
    }

    public static void main(String[] args) throws Exception {
        FileOutputStream out = new FileOutputStream("screenshot.png");
        out.write(PngSequenceWriter.bytes(Screen.shot()));
        out.close();
    }
}
