package com.neomemex.fixed;

import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;

final class FixedElement implements Element {

    @Override public int getElementIndex(int offset) { return 0; }
    @Override public Element getElement(int index) { return null; }

    private RuntimeException no() { throw new RuntimeException(); }
    private void print(String s) { System.out.println(s); }

    // Methods below here are in the interface, but we don't even pretend to do them
    @Override public Document getDocument() { throw no(); }
    @Override public Element getParentElement() { throw no(); }
    @Override public String getName() { throw no(); }
    @Override public AttributeSet getAttributes() { throw no(); }
    @Override public int getStartOffset() { throw no(); }
    @Override public int getEndOffset() { throw no(); }
    @Override public int getElementCount() { throw no(); }
    @Override public boolean isLeaf() { throw no(); }
}
