package com.neomemex.multicolumn;

import javax.swing.text.*;

final class MultiColumnFactory implements ViewFactory {
    public View create(Element elem) {
        String kind = elem.getName();
        if (kind == null) { return new LabelView(elem); }
        if (kind.equals(AbstractDocument.ContentElementName))   { return new LabelView(elem); }
        if (kind.equals(AbstractDocument.ParagraphElementName)) { return new MultiColumnParagraphView(elem); }
        if (kind.equals(AbstractDocument.SectionElementName))   { return new MultiColumnView(elem, View.Y_AXIS); }
        if (kind.equals(StyleConstants.ComponentElementName))   { return new ComponentView(elem); }
        if (kind.equals(StyleConstants.IconElementName))        { return new IconView(elem); }

        // default to text display
        throw new IllegalArgumentException("kind = " + kind);
    }
}