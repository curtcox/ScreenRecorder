package com.neomemex.multicolumn;

import javax.swing.text.*;
import static javax.swing.text.AbstractDocument.*;
import static javax.swing.text.StyleConstants.*;

final class CustomLayoutViewFactory implements ViewFactory {
    public View create(Element elem) {
        String kind = elem.getName();
        if (ContentElementName.equals(kind))   { return new CustomView(elem); }
        if (ParagraphElementName.equals(kind)) { return new CustomView(elem); }
        if (SectionElementName.equals(kind))   { return new CustomView(elem); }
        if (ComponentElementName.equals(kind)) { return new ComponentView(elem); }
        if (IconElementName.equals(kind))      { return new IconView(elem); }
        return new CustomView(elem);
    }
}