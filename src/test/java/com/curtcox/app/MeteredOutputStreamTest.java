package com.curtcox.app;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MeteredOutputStreamTest {

    @Test
    public void passes_bytes_to_inner_stream() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MeteredOutputStream stream = new MeteredOutputStream(out);

        byte b = (byte) hashCode();
        stream.write(b);

        byte[] written = out.toByteArray();
        assertEquals(1,written.length);
    }

    @Test
    public void reports_number_bytes_written() throws IOException {
        MeteredOutputStream stream = new MeteredOutputStream(new ByteArrayOutputStream());
        assertEquals(0,stream.getCount());
        stream.write(4);
        assertEquals(1,stream.getCount());
    }
}
