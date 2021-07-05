package com.neomemex.multicolumn;

import java.awt.*;
import javax.swing.text.*;

final class MultiColumnView extends BoxView {

    //default width for columns
    int columnWidth=100;
    //default height (could be changed to be JEditorPane's height
    int columnHeight=100;

    //children (paragraphs) offsets and spans and horizontal/vertical sizes
    int majorTargetSpan;
    int[] majorOffsets;
    int[] majorSpans;

    int minorTargetSpan;
    int[] minorOffsets;
    int[] minorSpans;

    //starting positions of paragraph views
    Point[] starts;

    //cont of columns
    int columnCount=1;

    MultiColumnView(Element elem, int axis) {
        super(elem, axis);
    }

    @Override
    protected void layout(int width, int height) {
        columnHeight=height;
        super.layout(columnWidth,height);
    }

    @Override
    protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
        super.layoutMajorAxis(targetSpan,axis,offsets,spans);
        majorTargetSpan=targetSpan;
        majorOffsets=offsets;
        majorSpans=spans;
        performMultiColumnLayout();
        for (int i=0; i<offsets.length; i++) {
            spans[i]=columnHeight;
            offsets[i]=0;
        }
    }

    @Override
    protected void layoutMinorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
        super.layoutMinorAxis(targetSpan,axis,offsets,spans);
        minorTargetSpan=targetSpan;
        minorOffsets=offsets;
        minorSpans=spans;
        performMultiColumnLayout();
        for (int i=0; i<offsets.length; i++) {
            View v=getView(i);
            if (v instanceof MultiColumnParagraphView) {
                MultiColumnParagraphView par=(MultiColumnParagraphView)v;
                spans[i] = par.columnCount*columnWidth;
                offsets[i]=par.columnNumber*columnWidth;
            }
        }
    }

    private void performMultiColumnLayout() {
        if (majorOffsets==null || minorOffsets==null || minorOffsets.length!=majorOffsets.length) {
            return;
        }
        int childCount=majorOffsets.length;
        int verticalStartOffset=0;
        int columnNumber=0;
        starts=new Point[childCount];
        for (int i=0; i<childCount; i++) {
            View v=getView(i);
            starts[i]=new Point();
            if (v instanceof MultiColumnParagraphView) {
                MultiColumnParagraphView par=(MultiColumnParagraphView)v;
                par.verticalStartOffset=verticalStartOffset;
                par.columnWidth=columnWidth;
                par.columnHeight=columnHeight;
                par.columnNumber=columnNumber;
                par.performMultiColumnLayout();
                starts[i].y=verticalStartOffset;
                starts[i].x=columnNumber*columnWidth;
                verticalStartOffset=columnHeight-par.restHeight;
                columnNumber+=par.columnCount-1;
            }
        }
        columnCount = columnNumber + 1;
    }

    @Override
    public float getPreferredSpan(int axis) {
        if (axis==View.Y_AXIS) {
            return columnHeight;
        }
        else {
            return columnWidth*columnCount;
        }
    }

    @Override
    public float getMinimumSpan(int axis) {
        if (axis==View.Y_AXIS) {
            return columnHeight;
        }
        else {
            return columnWidth*columnCount;
        }
    }

    @Override
    public float getMaximumSpan(int axis) {
        if (axis==View.Y_AXIS) {
            return columnHeight;
        }
        else {
            return columnWidth*columnCount;
        }
    }

    @Override
    public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
        //define child container
        if (starts!=null) {
            for (int i=starts.length-1; i>0; i--) {
                if ((starts[i].x<x && starts[i].y<y)
                        || (starts[i].x+columnWidth<x)
                ){
                    return getView(i).viewToModel(x,y,a,bias);
                }
            }
        }
        return getView(0).viewToModel(x,y,a,bias);
    }

    @Override
    public void paint(Graphics g, Shape a) {
        super.paint(g,a);
        Rectangle r=a.getBounds();
        int shift=columnWidth;
        g.setColor(new Color(240,240,240));
        while (shift<r.width) {
            g.drawLine(r.x+shift,r.y,r.x+shift,r.y+r.height);
            shift+=columnWidth;
        }
    }
}