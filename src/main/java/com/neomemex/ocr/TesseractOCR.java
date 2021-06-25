package com.neomemex.ocr;

import com.neomemex.store.RuntimeIOException;
import com.neomemex.stream.NullOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

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

    public String text(InputStream input) {
        try {
            return text0(input);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Word[] words(InputStream input) {
        try {
            return words0(input);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String text0(InputStream input) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("tesseract", "-", "-");
        return process(processBuilder.start(),input);
    }

    private String process(Process process, InputStream input) throws IOException, InterruptedException {
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

    private Word[] words0(InputStream input) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("tesseract", "-", "-", "tsv");
        String output = process(processBuilder.start(),input);
        return words(lines(output));
    }

    private String[] lines(String text) {
        return text.split("\n");
    }

    private Word[] words(String[] lines) {
        List<Word> out = new ArrayList<>();
        for (String line : lines) {
            if (validWord(line)){
                out.add(word(line));
            }
        }
        return out.toArray(new Word[out.size()]);
    }

    private boolean validWord(String line) {
        return !header(line) && line.split("\t").length == 12;
    }

    private boolean header(String line) {
        return line.startsWith("level");
    }

    //5	1	3	1	1	4	474	339	58	21	96	OCR
    private Word word(String line) {
        String[] parts = line.split("\t");
        System.out.println(Arrays.asList(parts));
        int left = parseInt(parts[6]);
        int top = parseInt(parts[7]);
        int width = parseInt(parts[8]);
        int height = parseInt(parts[9]);
        String text = parts[11];
        return new Word(left,top,width,height,text);
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
