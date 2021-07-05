package com.neomemex.table;

import java.awt.*;
import javax.swing.text.*;

final class TableView extends BoxView {

    TableView(Element elem) {
        super(elem, View.Y_AXIS);
    }

    @Override public float getMinimumSpan(int axis) { return getPreferredSpan(axis); }
    @Override public float getMaximumSpan(int axis) { return getPreferredSpan(axis); }
    @Override public float getAlignment(int axis) { return 0; }

    protected void paintChild(Graphics g, Rectangle alloc, int index) {
        super.paintChild(g, alloc, index);
        g.setColor(Color.BLUE);
        g.drawLine(alloc.x, alloc.y, alloc.x + alloc.width, alloc.y);
        int lastY = alloc.y + alloc.height;
        if (index == getViewCount() - 1) {
            lastY--;
        }
        g.drawLine(alloc.x, lastY, alloc.x + alloc.width, lastY);
    }
}
