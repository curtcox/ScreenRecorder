package com.neomemex.fixed;

import com.neomemex.ocr.OCR;

import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://web.archive.org/web/20060110035515/http://java.sun.com/products/jfc/tsc/articles/text/element_interface/
 */
final class FixedElement implements Element {

    private final FixedDocument document;
    private final FixedElement parent;
    private FixedElement[] children = new FixedElement[0];
    private final int start;
    private final int end;
    private final int elementCount;

    static final int BEFORE_START = 0;
    static final int LEAF = -1;

    private FixedElement(FixedDocument document, FixedElement parent, int start, int end, int elementCount) {
        this.document = document;
        this.parent = parent;
        this.start = start;
        this.end = end;
        this.elementCount = elementCount;
    }

    public static FixedElement of(FixedDocument fixedDocument) {
        OCR.Word[] words = fixedDocument.words;
        if (words.length<1) {
            throw new IllegalArgumentException("Root element must have more than 0 words.");
        }
        FixedElement root = new FixedElement(fixedDocument,null,0,computeEnd(fixedDocument),words.length);
        root.children = computeChildren(fixedDocument,root);
        return root;
    }

    public static FixedElement leaf(FixedDocument document, FixedElement parent, int start, int end) {
        if (parent==null) {
            throw new IllegalArgumentException();
        }
        return new FixedElement(document,parent,start,end,0);
    }

    private static FixedElement[] computeChildren(FixedDocument fixedDocument,FixedElement parent) {
        int end = -1;
        OCR.Word[] words = fixedDocument.words;
        List<FixedElement> children = new ArrayList<>();
        for (OCR.Word word : words) {
            int start = end + 1;
            end = start + word.text.length() - 1;
            children.add(leaf(fixedDocument,parent,start,end));
        }
        return children.toArray(new FixedElement[0]);
    }

    private static int computeEnd(FixedDocument fixedDocument) {
        int end = -1;
        OCR.Word[] words = fixedDocument.words;
        for (OCR.Word word : words) {
            int start = end + 1;
            end = start + word.text.length() - 1;
        }
        return end;
    }

    @Override public boolean isLeaf() { return parent!=null; }
    @Override public Document getDocument() { return document; }
    @Override public int getElementIndex(int offset) {
        if (isLeaf()) return LEAF;
        if (offset<start) return BEFORE_START;
        if (offset>end) return elementCount - 1;
        for (int i=0; i<children.length; i++) {
            if (children[i].contains(offset)) {
                return i;
            }
        }
        throw new RuntimeException(offset + " not in " + this);
    }

    private boolean contains(int offset) {
        return offset>=start && offset<=end;
    }

    @Override public Element getElement(int index) { return children[index]; }
    @Override public int getEndOffset()    { return end; }
    @Override public int getStartOffset()  { return start; }
    @Override public int getElementCount() { return elementCount; }
    @Override public Element getParentElement() { return parent; }

    private static RuntimeException no() { throw new RuntimeException(); }
    private static void print(String s) { System.out.println(s); }

    @Override
    public String toString() {
        return isLeaf()
                ? "{ " + start + "," + end + " }"
                : "{ " + start + "," + end + " " + Arrays.asList(children) + " }";
    }

    // Methods below here are in the interface, but we don't even pretend to do them
    @Override public String getName() { throw no(); }
    @Override public AttributeSet getAttributes() { throw no(); }
}
