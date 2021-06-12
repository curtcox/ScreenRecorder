package com.neomemex.reader;

import com.neomemex.recorder.RasterSerializer;
import com.neomemex.recorder.SimpleImageSequenceWriter;
import com.neomemex.shared.Image;
import com.neomemex.shared.Screen;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ImageSequenceReaderTest {

    @Test
    public void can_read_image_back_from_file() throws IOException {
        BufferedImage original = Screen.shot();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimpleImageSequenceWriter writer = new SimpleImageSequenceWriter(out);
        Image image = RasterSerializer.serialize(original);
        writer.writeImage(image);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ImageSequenceReader reader = new ImageSequenceReader(in);
        Image copy = reader.read();

        assertEquals(original,copy);
    }
}
