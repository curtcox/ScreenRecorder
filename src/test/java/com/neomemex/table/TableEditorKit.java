package com.neomemex.table;

import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

final class TableEditorKit extends StyledEditorKit {

    ViewFactory defaultFactory = new TableViewFactory();

    public ViewFactory getViewFactory() {
        return defaultFactory;
    }

    public Document createDefaultDocument() {
        return new TableDocument();
    }
}
