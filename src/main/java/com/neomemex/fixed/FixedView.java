package com.neomemex.fixed;

import com.neomemex.ocr.OCR;

import javax.swing.text.*;
import java.awt.*;

final class FixedView extends View {

    final int width;
    final int height;
    final OCR.Word[] words;

    FixedView(Element elem, int width, int height, OCR.Word[] words) {
        super(elem);
        this.width = width;
        this.height = height;
        this.words = words;
    }

    @Override
    public float getPreferredSpan(int axis) {
        return axis==X_AXIS ? width : height;
    }

    @Override
    public void paint(Graphics g, Shape allocation) {
        for (OCR.Word word : words) {
            paintWord(g,word);
        }
    }

    private void paintWord(Graphics g, OCR.Word word) {
        int left = word.left;
        int top = word.top;
        int bottom = word.top + word.height;
        int w = word.width;
        int h = word.height;
        g.setColor(Color.BLUE);
        g.drawRect(left,top,w,h);
        g.setColor(Color.RED);
        g.drawString(word.text,left,bottom);
    }

    @Override
    public Shape modelToView(int pos, Shape a, Position.Bias b) {
        System.out.println("modelToView");
        return a;
    }

    @Override
    public int viewToModel(float x, float y, Shape a, Position.Bias[] biasReturn) {
        System.out.println("modelToView");
        return 0;
    }

}
