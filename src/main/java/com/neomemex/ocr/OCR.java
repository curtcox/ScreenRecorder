package com.neomemex.ocr;

import java.io.InputStream;

/**
 * We might eventually create a custom engine.
 */
public interface OCR {
    final class Word {
        public final int left; public final int top;
        public final int width; public final int height;
        public final String	text;
        Word(int left, int top, int width, int height, String text) {
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
            this.text = text;
        }
        @Override
        public int hashCode() {
            return left ^ top ^ width ^ height ^ text.hashCode();
        }
        @Override
        public boolean equals(Object o) {
            Word that = (Word) o;
            return left == that.left && top == that.top &&
                    width == that.width && height == that.height &&
                    text.equals(that.text);
        }
        @Override
        public String toString() {
            return text + "@" + left + "," + top + " " + width + "x" + height;
        }
    }

    String text(InputStream input);
    Word[] words(InputStream input);

}
