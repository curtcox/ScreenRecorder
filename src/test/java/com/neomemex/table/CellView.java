package com.neomemex.table;

import javax.swing.text.*;

final class CellView extends BoxView {

    public CellView(Element elem) {
        super(elem, View.Y_AXIS);
        setInsets((short) 20, (short) 20, (short) 20, (short) 20);
    }

    @Override
    public float getPreferredSpan(int axis) {
        return (axis == View.X_AXIS) ? getCellWidth() : super.getPreferredSpan(axis);
    }

    @Override public float getMinimumSpan(int axis) { return getPreferredSpan(axis); }
    @Override public float getMaximumSpan(int axis) { return getPreferredSpan(axis); }
    @Override public float getAlignment(int axis)   { return 0; }

    private int getCellWidth() {
        Integer width = widthFromAttributes();
        return width == null ? 100 : width;
    }

    private Integer widthFromAttributes() {
        return (Integer) getAttributes().getAttribute(TableDocument.PARAM_CELL_WIDTH);
    }

}