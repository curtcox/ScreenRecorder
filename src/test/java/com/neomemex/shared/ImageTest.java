package com.neomemex.shared;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class ImageTest {

    Time t = new Time(0);

    @Test
    public void default_constructor_is_ARGB() {
        Image image = new Image(t,new int[0],0,0);
        assertEquals(Image.Color.ARGB, image.color);
    }

    @Test
    public void default_constructor_is_full() {
        Image image = new Image(t,new int[0],0,0);
        assertEquals(Image.Type.full, image.type);
    }

    @Test
    public void returns_pixels_from_constructor() {
        int[] pixels = new int[10];
        Image image = new Image(t,pixels,0,0);
        assertEquals(pixels.length, image.pixels().length);
        assertEquals(list(pixels), list(image.pixels()) );
    }

    @Test
    public void returns_width_from_constructor() {
        int width = hashCode();
        Image image = new Image(t,new int[0],width,0);
        assertEquals(width,image.width);
    }

    @Test
    public void returns_height_from_constructor() {
        int height = hashCode();
        Image image = new Image(t,new int[0],0,height);
        assertEquals(height, image.height);
    }

    @Test
    public void equal_images() {
        equal(new Image(t,new int[0],0,0),new Image(t,new int[0],0,0));
        equal(new Image(t,new int[10],10,10),new Image(t,new int[10],10,10));
        equal(new Image(t,new int[1],0,0),new Image(t,new int[1],0,0));
        equal(new Image(t,new int[1],2,0),new Image(t,new int[1],2,0));
        equal(new Image(t,new int[1],2,3),new Image(t,new int[1],2,3));
        equal(new Image(t,new int[0],0,0),new Image(t,Image.Color.ARGB, Image.Type.full,new int[0],0,0));
    }

    @Test
    public void unequal_images() {
        unequal(new Image(new Time(0),new int[0],0,0),new Image(t,new int[0],0,0));
        unequal(new Image(t,new int[0],0,0),new Image(t,new int[1],0,0));
        unequal(new Image(t,new int[0],0,0),new Image(t,new int[0],1,0));
        unequal(new Image(t,new int[0],0,0),new Image(t,new int[0],0,1));
        unequal(new Image(t,new int[0],0,0),new Image(t,Image.Color.RGB, Image.Type.full,new int[0],0,0));
        unequal(new Image(t,new int[0],0,0),new Image(t,Image.Color.ARGB, Image.Type.delta,new int[0],0,0));
    }

    @Test
    public void unequal_images_but_might_have_same_hash_code() {
        justNotEqual(new Image(t,new int[]{0},10,10),new Image(t,new int[]{1},10,10));
        justNotEqual(new Image(t,new int[]{1,2,3,4},10,10),new Image(t,new int[]{1,2,33,4},10,10));
    }

    void equal(Image a, Image b) {
        assertEquals(a,b);
        assertEquals(b,a);
        assertEquals(a.hashCode(),b.hashCode());
    }

    void justNotEqual(Image a, Image b) {
        assertNotEquals(a,b);
        assertNotEquals(b,a);
    }

    void unequal(Image a, Image b) {
        justNotEqual(a,b);
        assertNotEquals(a.hashCode(),b.hashCode());
    }

    static List<Integer> list(int[] array) {
        List<Integer> out = new ArrayList<>();
        for (int i: array) {
            out.add(i);
        }
        return out;
    }

    @Test
    public void rgb_is_smaller_than_argb() {
        Image image = new Image(t,ints(100),100,100);
        assertEquals(Image.Color.ARGB,image.color);
        assertEquals(100,image.size);
        Image rgb = image.rgb();
        assertEquals(Image.Color.RGB,rgb.color);
        assertEquals(75,rgb.size);
    }

    @Test
    public void rgb_is_idempotent() {
        Image image = new Image(t,ints(100),100,100);
        Image rgb1 = image.rgb();
        assertEquals(rgb1,rgb1.rgb());
        assertSame(rgb1,rgb1.rgb());
    }

    @Test
    public void argb_is_idempotent() {
        Image image = new Image(t,ints(100),100,100);
        Image argb1 = image.argb();
        assertEquals(argb1,argb1.argb());
        assertSame(argb1,argb1.argb());
    }

    @Test
    public void argb_undoes_rgb() {
        Image image = new Image(t,ints(100),100,100);
        Image argb = image.argb();
        Image rgb = argb.rgb();
        assertEquals(argb,rgb.argb());
    }

    @Test
    public void rgb_undoes_argb() {
        Image image = new Image(t,ints(100),100,100);
        Image rgb = image.rgb();
        Image argb = rgb.argb();
        assertEquals(rgb,argb.rgb());
    }

    @Test
    public void xor_zeros_itself() {
        Image image = new Image(t,ints(100),100,100);
        Image xor = image.xor(image);
        int[] pixels = xor.pixels();
        for (int i=0; i<pixels.length; i++) {
            assertEquals(0,pixels[i]);
        }
    }

    static int[] ints(int size) {
        int[] a = new int[size];
        for (int i=0; i<size; i++) {
            a[i] = i;
        }
        return a;
    }
}
