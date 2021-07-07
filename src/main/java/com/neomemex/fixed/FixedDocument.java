package com.neomemex.fixed;

import com.neomemex.ocr.OCR;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;

final class FixedDocument implements Document {

    final OCR.Word[] words;
    final int length;
    final String text;

    private FixedDocument(OCR.Word[] words) {
        this.words = words;
        length = computeLength(words);
        text = computeText(words);
    }

    private static int computeLength(OCR.Word[] words) {
        int out = 0;
        for (OCR.Word word : words) {
            out = out + word.text.length();
        }
        return out;
    }

    private static String computeText(OCR.Word[] words) {
        StringBuilder out = new StringBuilder();
        for (OCR.Word word : words) {
            out.append(word.text);
        }
        return out.toString();
    }

    static FixedDocument of(OCR.Word[] words) {
        FixedDocument document = new FixedDocument(words);
        return document;
    }

    @Override public int getLength() { return length; }

    @Override
    public Position createPosition(final int offs)  {
        return new Position() {
            @Override public int getOffset() { return offs; }
        };
    }

    @Override
    public String getText(int offset, int length) {
        return text.substring(offset,length);
    }


    // Methods that we implement to the extent that we don't blow up
    @Override public Element getDefaultRootElement() { return new FixedElement(); }

    @Override
    public void addDocumentListener(DocumentListener listener) {
        print("addDocumentListener");
    }

    @Override
    public Object getProperty(Object key) {
        print("getProperty " + key);
        return null;
    }

    @Override
    public void putProperty(Object key, Object value) {
        print("putProperty"+key+value);
    }

    private RuntimeException no() { throw new RuntimeException(); }
    private void print(String s) { System.out.println(s); }

    // Methods below here are in the interface, but we don't even pretend to do them
    @Override public Element[] getRootElements() { throw no(); }
    @Override public void render(Runnable r) { throw no(); }
    @Override public void removeDocumentListener(DocumentListener listener) { throw no(); }
    @Override public void addUndoableEditListener(UndoableEditListener listener) { throw no(); }
    @Override public void removeUndoableEditListener(UndoableEditListener listener) { throw no(); }
    @Override public void remove(int offs, int len)  { throw no(); }
    @Override public void insertString(int offset, String str, AttributeSet a)  { throw no(); }
    @Override public void getText(int offset, int length, Segment txt) { throw no(); }
    @Override public Position getStartPosition() { throw no(); }
    @Override public Position getEndPosition() { throw no(); }

}