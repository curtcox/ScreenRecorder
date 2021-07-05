package com.neomemex.table;

import java.awt.*;
import javax.swing.text.*;

final class RowView extends BoxView {

    RowView(Element elem) {
        super(elem, View.X_AXIS);
    }

    @Override public float getPreferredSpan(int axis) { return super.getPreferredSpan(axis); }
    @Override public float   getMinimumSpan(int axis) { return getPreferredSpan(axis); }
    @Override public float   getMaximumSpan(int axis) { return getPreferredSpan(axis); }
    @Override public float     getAlignment(int axis) { return 0; }

    @Override
    protected void paintChild(Graphics g, Rectangle alloc, int index) {
        super.paintChild(g, alloc, index);
        g.setColor(Color.RED);
        int h = (int) getPreferredSpan(View.Y_AXIS) - 1;
        g.drawLine(alloc.x, alloc.y, alloc.x, alloc.y + h);
        g.drawLine(alloc.x + alloc.width, alloc.y, alloc.x + alloc.width, alloc.y + h);
    }
}