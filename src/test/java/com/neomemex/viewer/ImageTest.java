package com.neomemex.viewer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class ImageTest {

    @Test
    public void default_constructor_is_RGB() {
        Image image = new Image(new int[0],0,0);
        assertEquals(Image.Color.ARGB, image.color);
    }

    @Test
    public void default_constructor_is_full() {
        Image image = new Image(new int[0],0,0);
        assertEquals(Image.Type.full, image.type);
    }

    @Test
    public void returns_pixels_from_constructor() {
        int[] pixels = new int[10];
        Image image = new Image(pixels,0,0);
        assertEquals(pixels.length, image.pixels().length);
        assertEquals(list(pixels), list(image.pixels()) );
    }

    @Test
    public void returns_width_from_constructor() {
        int width = hashCode();
        Image image = new Image(new int[0],width,0);
        assertEquals(width,image.width);
    }

    @Test
    public void returns_height_from_constructor() {
        int height = hashCode();
        Image image = new Image(new int[0],0,height);
        assertEquals(height, image.height);
    }

    static List<Integer> list(int[] array) {
        List<Integer> out = new ArrayList<>();
        for (int i: array) {
            out.add(i);
        }
        return out;
    }
}
