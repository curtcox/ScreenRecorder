package com.neomemex.multicolumn;

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

final class CustomLayoutEditorKit extends StyledEditorKit {

    final ViewFactory defaultFactory=new CustomLayoutViewFactory();

    public ViewFactory getViewFactory() {
        return defaultFactory;
    }
}