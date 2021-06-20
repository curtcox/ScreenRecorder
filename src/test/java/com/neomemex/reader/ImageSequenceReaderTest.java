package com.neomemex.reader;

import com.neomemex.recorder.RasterSerializer;
import com.neomemex.recorder.SimpleImageSequenceWriter;
import com.neomemex.shared.Image;
import com.neomemex.shared.Screen;
import com.neomemex.shared.Time;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.SortedMap;

import static org.junit.Assert.assertEquals;

public class ImageSequenceReaderTest {

    @Test
    public void can_read_uncompressed_image_back_from_bytes() throws IOException {
        BufferedImage original = Screen.shot();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimpleImageSequenceWriter writer = new SimpleImageSequenceWriter(out);
        Image image = RasterSerializer.serialize(original, Time.now());
        writer.writeImage(image);
        writer.close();

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ImageSequenceReader reader = new ImageSequenceReader(in);
        Image copy = reader.read();

        assertEquals(image,copy);
    }

    @Test
    public void can_read_2_of_the_same_image_back_from_bytes() throws IOException {
        BufferedImage original = Screen.shot();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimpleImageSequenceWriter writer = new SimpleImageSequenceWriter(out);
        Image image = RasterSerializer.serialize(original,Time.now());
        writer.writeImage(image);
        writer.writeImage(image);
        writer.close();

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ImageSequenceReader reader = new ImageSequenceReader(in);
        Image copy1 = reader.read();
        Image copy2 = reader.read();

        assertEquals(image,copy1);
        assertEquals(image,copy2);
    }

    @Test
    public void can_read_2_different_images_back_from_bytes() throws IOException {
        BufferedImage original = Screen.shot();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimpleImageSequenceWriter writer = new SimpleImageSequenceWriter(out);
        Image first = RasterSerializer.serialize(original,Time.now());
        writer.writeImage(first);
        Image second = RasterSerializer.serialize(original,Time.now());
        writer.writeImage(second);
        writer.close();

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ImageSequenceReader reader = new ImageSequenceReader(in);
        Image copy1 = reader.read();
        Image copy2 = reader.read();

        assertEquals(first,copy1);
        assertEquals(second,copy2);
    }

    @Test
    public void can_read_2_of_the_same_image_back_from_compressed() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimpleImageSequenceWriter writer = SimpleImageSequenceWriter.to(out);
        Image first = RasterSerializer.serialize(Screen.shot(),Time.now());
        writer.writeImage(first);
        System.out.println("wrote " + out.size());
        Image second = RasterSerializer.serialize(Screen.shot(),Time.now());
        writer.writeImage(second);
        System.out.println("wrote " + out.size());
        writer.close();

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        SortedMap<Time,Image> images = ImageSequenceReader.allImagesFrom(in);

        assertEquals(2,images.size());
        assertEquals(first,images.get(first.time));
        assertEquals(second,images.get(second.time));
    }

    @Test
    public void can_read_compressed_image_back_from_bytes() throws IOException {
        BufferedImage original = Screen.shot();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimpleImageSequenceWriter writer = SimpleImageSequenceWriter.to(out);
        Image image = RasterSerializer.serialize(original,Time.now());
        writer.writeImage(image);
        writer.close();

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ImageSequenceReader reader = ImageSequenceReader.from(in);
        Image copy = reader.read();

        assertEquals(image,copy);
    }

    @Test
    public void can_read_2_compressed_images_back_from_bytes() throws IOException {
        BufferedImage original = Screen.shot();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimpleImageSequenceWriter writer = SimpleImageSequenceWriter.to(out);
        Image first = RasterSerializer.serialize(original,Time.now());
        writer.writeImage(first);
        Image second = RasterSerializer.serialize(original,Time.now());
        writer.writeImage(second);
        writer.close();

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ImageSequenceReader reader = ImageSequenceReader.from(in);
        Image copy1 = reader.read();
        Image copy2 = reader.read();

        assertEquals(first,copy1);
        assertEquals(second,copy2);
    }

}
