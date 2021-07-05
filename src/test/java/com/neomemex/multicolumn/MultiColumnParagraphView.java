package com.neomemex.multicolumn;

import java.awt.*;
import javax.swing.text.*;

final class MultiColumnParagraphView extends ParagraphView {

    int verticalStartOffset;
    int columnCount;
    int columnNumber;
    int restHeight;

    int[] majorOffsets;
    int[] majorSpans;

    int[] minorOffsets;
    int[] minorSpans;

    int columnWidth=100;
    int columnHeight=100;
    Point[] starts;

    MultiColumnParagraphView(Element elem) {
        super(elem);
    }

    @Override
    protected void layout(int width, int height) {
        super.layout(columnWidth,0);
    }

    @Override
    protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
        super.layoutMajorAxis(targetSpan,axis,offsets,spans);
        majorOffsets=offsets;
        majorSpans=spans;
        performMultiColumnLayout();
        offsets=majorOffsets;
    }

    @Override
    protected void layoutMinorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
        super.layoutMinorAxis(targetSpan,axis,offsets,spans);
        minorOffsets=offsets;
        minorSpans=spans;
        performMultiColumnLayout();
        offsets=minorOffsets;
    }

    void performMultiColumnLayout() {
        if (majorOffsets==null || minorOffsets==null || minorOffsets.length!=majorOffsets.length) {
            return;
        }
        int childCount=majorOffsets.length;
        int vo=verticalStartOffset;
        int cc=1;
        starts=new Point[childCount];
        for (int i=0; i<childCount; i++) {
            starts[i]=new Point();
            if (vo+majorSpans[i]>columnHeight) {
                cc++;
                vo=0;
            }
            starts[i].y=vo;
            starts[i].x=(columnNumber+cc-1)*columnWidth;
            majorOffsets[i]=vo;
            vo+=majorSpans[i];
            restHeight=columnHeight-vo;
            minorOffsets[i]=(cc-1)*columnWidth;
        }
        if (columnCount!=cc) {
            columnCount = cc;
            preferenceChanged(getView(0),true,true);
        }
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
        int ind=getViewIndexAtPoint((int)x,(int)y,a.getBounds());
        View v=getViewAtPoint((int)x,(int)y,a.getBounds());
        Shape childAlloc=getChildAllocation(ind,a);
        return v.viewToModel(x,y,childAlloc,bias);
    }

    private int getViewIndexAtPoint(int x, int y, Rectangle alloc) {
        if (starts!=null) {
            for (int i=starts.length-1; i>0; i--) {
                if ((starts[i].x<x && starts[i].y<y)
                        || (starts[i].x+columnWidth<x)){
                    return i;
                }
            }
        }
        return 0;
    }

    @Override
    protected View getViewAtPoint(int x, int y, Rectangle alloc) {
        if (starts!=null) {
            for (int i=starts.length-1; i>0; i--) {
                if ((starts[i].x<x && starts[i].y<y)
                        || (starts[i].x+columnWidth<x)){
                    return getView(i);
                }
            }
        }
        return getView(0);
    }

    @Override
    public Shape getChildAllocation(int index, Shape a) {
        Rectangle r=super.getChildAllocation(index,a).getBounds();
        r.x=starts[index].x+3;
        r.y=starts[index].y+3;
        return r;
    }
}
