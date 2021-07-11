package com.neomemex.fixed;

import com.neomemex.ocr.OCR;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;
import java.awt.*;
import java.util.Arrays;

final class FixedView extends View
    implements CaretListener
{

    final int width;
    final int height;
    final OCR.Word[] words;
    final int[] starts;
    private CaretEvent caretEvent;

    FixedView(Element elem, int width, int height, OCR.Word[] words) {
        super(elem);
        this.width = width;
        this.height = height;
        this.words = words;
        starts = starts(words);
    }

    private static int[] starts(OCR.Word[] words) {
        int[] a = new int[words.length];
        int start = 0;
        for (int i=0; i< words.length; i++) {
            OCR.Word word = words[i];
            a[i] = start;
            start = start + word.text.length();
        }
        return a;
    }

    @Override
    public float getPreferredSpan(int axis) {
        return axis==X_AXIS ? width : height;
    }

    @Override
    public void paint(Graphics g, Shape allocation) {
        for (int i=0; i<words.length; i++) {
            OCR.Word word = words[i];
            if (isSelected(i)) {
                paintSelectedWord(g,word);
            } else {
                paintUnselectedWord(g,word);
            }
        }
    }

    private boolean isSelected(int i) {
        if (caretEvent==null) {
            return false;
        }
        int start = starts[i];
        return isBetween(caretEvent.getMark(),start,caretEvent.getDot());
    }

    private static boolean isBetween(int e1, int m, int e2) {
        return (e1 <= m && m <= e2) || (e2 >= m && m >= e2);
    }

    private void paintUnselectedWord(Graphics g, OCR.Word word) {
        int left = word.left;
        int top = word.top;
        int bottom = word.top + word.height;
        int w = word.width;
        int h = word.height;
        g.setColor(Color.WHITE);
        g.fillRect(left,top,w,h);
        g.setColor(Color.RED);
        g.drawString(word.text,left,bottom);
    }

    private void paintSelectedWord(Graphics g, OCR.Word word) {
        int left = word.left;
        int top = word.top;
        int bottom = word.top + word.height;
        int w = word.width;
        int h = word.height;
        g.setColor(Color.BLUE);
        g.fillRect(left,top,w,h);
        g.setColor(Color.RED);
        g.drawString(word.text,left,bottom);
    }


    /**
     * Provides a mapping, for a given character,
     * from the document model coordinate space
     * to the view coordinate space.
     *
     * @param pos the position of the desired character (&gt;=0)
     * @param a the area of the view, which encompasses the requested character
     * @param b the bias toward the previous character or the
     *  next character represented by the offset, in case the
     *  position is a boundary of two views; <code>b</code> will have one
     *  of these values:
     * <ul>
     * <li> <code>Position.Bias.Forward</code>
     * <li> <code>Position.Bias.Backward</code>
     * </ul>
     * @return the bounding box, in view coordinate space,
     *          of the character at the specified position
     * @exception BadLocationException  if the specified position does
     *   not represent a valid location in the associated document
     * @exception IllegalArgumentException if <code>b</code> is not one of the
     *          legal <code>Position.Bias</code> values listed above
     * @see View#viewToModel
     */
    @Override
    public Shape modelToView(int pos, Shape a, Position.Bias b) {
        System.out.println("modelToView " + pos + " " + a + " " + b);
        return a;
    }

    public Shape modelToView(int p0, Position.Bias b0, int p1, Position.Bias b1, Shape a)  {
        System.out.println("modelToView " + p0 + " " + b0 + " " + p1 + b1 + a);
        return a;
    }

    /**
     * Provides a mapping from the view coordinate space to the logical coordinate space of the model.
     * The biasReturn argument will be filled in to indicate that the point given is closer to the next character in
     * the model or the previous character in the model.
     * Params:
     * x – the X coordinate >= 0
     * y – the Y coordinate >= 0
     * a – the allocated region in which to render
     * biasReturn – the returned bias
     * Returns:
     * the location within the model that best represents the given point in the view >= 0.
     * The biasReturn argument will be filled in to indicate that the point given is closer to the next character
     * in the model or the previous character in the model.
     * @param x
     * @param y
     * @param a
     * @param biasReturn
     * @return
     */
    @Override
    public int viewToModel(float x, float y, Shape a, Position.Bias[] biasReturn) {
        int location = viewToModel0(x,y,a,biasReturn);
        System.out.println("viewToModel " + x + " " + y + " " + Arrays.asList(biasReturn)+ "->" + location);
        return location;
    }

    private int viewToModel0(float x, float y, Shape a, Position.Bias[] biasReturn) {
        biasReturn[0] = Position.Bias.Forward;
        for (int i=0; i< words.length; i++) {
            OCR.Word word = words[i];
            if (pointIsInWord(x,y,word)) {
                return starts[i];
            }
        }
        return 0;
    }

    private boolean pointIsInWord(float x, float y, OCR.Word word) {
        return  x > word.left && x < word.left + word.width &&
                y > word.top  && y < word.top  + word.height;
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        System.out.println("caretUpdate " + e);
        this.caretEvent = e;
    }
}
