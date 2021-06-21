package com.neomemex.ocr;

import com.neomemex.store.RuntimeIOException;
import com.neomemex.stream.NullOutputStream;

import java.io.*;

/**
 * A potential simple way of doing OCR is via tesseract.
 *
 * brew install tesseract
 *
 * then
 *
 * cat imagefile | tesseract - -
 *
 * See https://github.com/tesseract-ocr/tesseract/blob/master/doc/tesseract.1.asc
 */
public final class TesseractOCR implements OCR {

    public String process(InputStream input) {
        try {
            return process0(input);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String process0(InputStream input) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("tesseract", "-", "-");
        Process process = processBuilder.start();
        OutputStream in = process.getOutputStream();
        copy(input,in);
        in.close();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(process.getInputStream(),out);
        copy(process.getErrorStream(),new NullOutputStream());
        int exitCode = process.waitFor();
        System.out.println("Exited with " + exitCode);
        return new String(out.toByteArray());
    }

    // buffer size used for reading and writing
    private static final int BUFFER_SIZE = 8192;

    /**
     * Reads all bytes from an input stream and writes them to an output stream.
     */
    private static long copy(InputStream source, OutputStream sink) throws IOException {
        long nread = 0L;
        byte[] buf = new byte[BUFFER_SIZE];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
            nread += n;
        }
        return nread;
    }

}
