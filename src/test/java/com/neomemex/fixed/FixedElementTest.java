package com.neomemex.fixed;

import com.neomemex.ocr.OCR;
import org.junit.Test;

import javax.swing.text.Element;

import static org.junit.Assert.*;

/**
 * https://web.archive.org/web/20060110035515/http://java.sun.com/products/jfc/tsc/articles/text/element_interface/
 */

public class FixedElementTest {

    static OCR.Word word(String text) {
        return new OCR.Word(0,0,0,0,text);
    }

    OCR.Word word = word("bird");
    FixedElement parent = FixedElement.of(FixedDocument.of(new OCR.Word[]{word}));

    @Test
    public void can_create_root() {
        assertNotNull(FixedElement.of(FixedDocument.of(new OCR.Word[]{word})));
    }

    @Test
    public void root_with_0_words() {
        try {
            FixedElement.of(FixedDocument.of(new OCR.Word[0]));
        } catch (IllegalArgumentException e) {
            assertEquals("Root element must have more than 0 words.",e.getMessage());
        }
    }

    @Test
    public void root_with_1_word() {
        Element root = FixedElement.of(FixedDocument.of(new OCR.Word[]{
                word("text")
        }));
        assertFalse(root.isLeaf());
        equals(root,1,root.getElementCount());
        equals(root,0,root.getStartOffset());
        equals(root,3,root.getEndOffset());
        equals(root,root.getStartOffset(),root.getElement(0).getStartOffset());
        equals(root,root.getEndOffset(),root.getElement(0).getEndOffset());
    }

    private void equals(Element e,int expected,int actual) {
        assertEquals(e.toString(),expected,actual);
    }

    @Test
    public void root_with_2_words() {
        Element root = FixedElement.of(FixedDocument.of(new OCR.Word[]{
                word("one"),
                word("two")
        }));
        assertFalse(root.isLeaf());
        equals(root,2,root.getElementCount());
        equals(root,0,root.getStartOffset());
        equals(root,0,root.getElement(0).getStartOffset());
        equals(root, 5,root.getEndOffset());
        equals(root,root.getStartOffset(),root.getElement(0).getStartOffset());
        equals(root,2,root.getElement(0).getEndOffset());
        equals(root,3,root.getElement(1).getStartOffset());
        equals(root,root.getEndOffset(),root.getElement(1).getEndOffset());
    }

    @Test
    public void can_create_leaf() {
        assertNotNull(FixedElement.leaf(null, parent,0,0));
    }

    @Test
    public void leaf_isLeaf_returns_true_when_parent_is_not_null() {
        FixedElement element = FixedElement.leaf(null, parent,0,5);
        assertTrue(element.isLeaf());
        equals(element,0,element.getElementCount());
        equals(element,0,element.getStartOffset());
        equals(element,5,element.getEndOffset());
        assertSame(parent,element.getParentElement());
    }

    @Test
    public void getDocument_returns_given_document() {
        FixedDocument document = FixedDocument.of(new OCR.Word[0]);
        Element element = FixedElement.leaf(document, parent,0,0);
        assertSame(document,element.getDocument());
    }

//    @Test
//    public void getElement_with_index_0_returns_element_with_startOffset() {
//        FixedElement map = FixedElement.leaf(null, parent,0,0);
//        Element paragraph = map.getElement(0);
//        assertEquals(0,paragraph.getStartOffset());
//    }

//    @Test
//    public void getElement_with_index_0_returns_element_with_endOffset() {
//        FixedElement map = FixedElement.leaf(null, parent,0,0);
//        Element paragraph = map.getElement(0);
//        assertEquals(0,paragraph.getEndOffset());
//    }

    @Test
    public void getElementIndex_returns_neg_1_if_the_element_is_a_leaf() {
        Element leaf = FixedElement.leaf(null, parent,0,0);
        assertTrue(leaf.isLeaf());
        equals(leaf,-1,leaf.getElementIndex(0));
    }

    @Test
    public void root_getElementIndex_returns_0_if_the_location_is_less_than_the_start_offset() {
        Element root = FixedElement.of(FixedDocument.of(new OCR.Word[]{
                word("text")
        }));
        assertFalse(root.isLeaf());
        equals(root,0,root.getElementIndex(-1));
        equals(root,0,root.getElementIndex(-2));
    }

    @Test
    public void root_with_1_getElementIndex_returns_getElementCount_minus_1_if_the_location_is_greater_than_the_end_offset() {
        Element root = FixedElement.of(FixedDocument.of(new OCR.Word[]{
                word("text")
        }));
        assertFalse(root.isLeaf());
        equals(root,0,root.getElementIndex(5));
        equals(root,0,root.getElementIndex(10));
    }

    @Test
    public void root_with_1_getElementIndex_returns_best_match() {
        Element root = FixedElement.of(FixedDocument.of(new OCR.Word[]{
                word("fi")
        }));
        assertFalse(root.isLeaf());
        equals(root,0,root.getElementIndex(-1));
        equals(root,0,root.getElementIndex(0));
        equals(root,0,root.getElementIndex(1));
        equals(root,0,root.getElementIndex(2));
    }

    @Test
    public void root_with_2_getElementIndex_returns_getElementCount_minus_1_if_the_location_is_greater_than_the_end_offset() {
        Element root = FixedElement.of(FixedDocument.of(new OCR.Word[]{
                word("one"),
                word("two")
        }));
        assertFalse(root.isLeaf());
        equals(root,2,root.getElementCount());
        equals(root,1,root.getElementIndex(10));
        equals(root,1,root.getElementIndex(8));
        equals(root,1,root.getElementIndex(7));
        equals(root,1,root.getElementIndex(6));
        equals(root,1,root.getElementIndex(5));
    }

    @Test
    public void root_with_2_getElementIndex_returns_best_match() {
        Element root = FixedElement.of(FixedDocument.of(new OCR.Word[]{
                word("fi"),
                word("fi")
        }));
        assertFalse(root.isLeaf());
        equals(root,2,root.getElementCount());
        equals(root,0,root.getElementIndex(-1));
        equals(root,0,root.getElementIndex(0));
        equals(root,0,root.getElementIndex(1));
        equals(root,1,root.getElementIndex(2));
        equals(root,1,root.getElementIndex(3));
        equals(root,1,root.getElementIndex(4));
    }

    //public int getElementIndex(int offset);
//    Gets the child element index closest to the given offset.
//    The offset is specified relative to the beginning of the document.
//    Returns -1 if the Element is a leaf,
//    otherwise returns the index of the Element that best represents the given location.
//    Returns 0 if the location is less than the start offset.
//    Returns getElementCount() - 1 if the location is greater than or equal to the end offset.
//            Params:
//    offset – the specified offset >= 0
//    Returns:
//    the element index >= 0


    /**
     * Fetches the child element at the given index.
     *
     * @param index the specified index &gt;= 0
     * @return the child element
     */
    //public Element getElement(int index);

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
