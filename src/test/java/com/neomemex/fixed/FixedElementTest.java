package com.neomemex.fixed;

import org.junit.Test;

import static org.junit.Assert.*;

public class FixedElementTest {

    @Test
    public void can_create() {
        assertNotNull(new FixedElement());
    }

    @Test
    public void getElementIndex_() {
        fail();
    }

    @Test
    public void getElement_() {
        fail();
    }
    //    public static final Element getParagraphElement(JTextComponent c, int offs) {
//        Document doc = c.getDocument();
//        if (doc instanceof StyledDocument) {
//            return ((StyledDocument)doc).getParagraphElement(offs);
//        }
//        Element map = doc.getDefaultRootElement();
//        int index = map.getElementIndex(offs);
//        Element paragraph = map.getElement(index);
//        if ((offs >= paragraph.getStartOffset()) && (offs < paragraph.getEndOffset())) {
//            return paragraph;
//        }
//        return null;
//    }

}
