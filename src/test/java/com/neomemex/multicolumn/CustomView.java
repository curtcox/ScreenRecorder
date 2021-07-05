package com.neomemex.multicolumn;

import javax.swing.text.Element;
import javax.swing.text.GlyphView;
import java.awt.*;

final class CustomView extends GlyphView {

    final Element elem;

    /**
     * Constructs a new view wrapped on an element.
     *
     * @param elem the element
     */
    public CustomView(Element elem) {
        super(elem);
        this.elem = elem;
    }

    @Override
    public void paint(Graphics g, Shape a) {
        System.out.println("paint " + elem + " " + a);
        super.paint(g,a);
    }
}