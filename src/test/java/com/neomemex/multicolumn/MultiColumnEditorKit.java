package com.neomemex.multicolumn;

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

final class MultiColumnEditorKit extends StyledEditorKit {

    final ViewFactory defaultFactory=new MultiColumnFactory();

    public ViewFactory getViewFactory() {
        return defaultFactory;
    }
}