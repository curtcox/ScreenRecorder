package com.neomemex.table;

import javax.swing.text.*;
import static com.neomemex.table.TableDocument.*;

class TableViewFactory implements ViewFactory {

    public View create(Element elem) {
        System.out.println("elem =" + elem);
        String kind = elem.getName();
        if (kind.equals(SectionElementName))   { return new BoxView(elem, View.Y_AXIS); }
        if (kind.equals(ParagraphElementName)) { return new BoxView(elem, View.Y_AXIS); }
        if (kind.equals(ELEMENT_NAME_TABLE))   { return new TableView(elem); }
        if (kind.equals(ELEMENT_NAME_ROW))     { return new RowView(elem); }
        if (kind.equals(ELEMENT_NAME_CELL))    { return new CellView(elem); }
        if (kind.equals(ContentElementName))   { return new LabelView(elem); }
        throw new IllegalArgumentException();
    }
}
