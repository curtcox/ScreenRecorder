package com.neomemex.table;

import java.util.*;

import javax.swing.text.*;
import static javax.swing.text.DefaultStyledDocument.ElementSpec.*;

final class TableDocument extends DefaultStyledDocument {

    static final String ELEMENT_NAME_TABLE = "table";
    static final String ELEMENT_NAME_ROW = "row";
    static final String ELEMENT_NAME_CELL = "cell";
    static final String PARAM_CELL_WIDTH = "cell-width";

    void insertTable(int offset, int rowCount, int[] colWidths) throws BadLocationException {
        List<ElementSpec> tableSpecs = elementSpecsForTable(rowCount,colWidths);
        insert(offset, tableSpecs.toArray(new ElementSpec[tableSpecs.size()]));
        System.out.println(getLength() + " insert count = " + tableSpecs.size());
    }

    static private List<ElementSpec> elementSpecsForTable(int rowCount, int[] colWidths) {
        ArrayList<ElementSpec> specs = new ArrayList<>();
        specs.add(closeParagraphTag());
        specs.add(startTableTag());
        specs.addAll(elementSpecsForRows(rowCount, colWidths));
        specs.add(endTableTag());
        specs.add(openParagraphTag());
        return specs;
    }

    static private List<ElementSpec> elementSpecsForRows(int rowCount, int[] colWidths) {
        ArrayList<ElementSpec> specs = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            specs.add(spec(rowAttrs(), StartTagType));
            specs.addAll(elementSpecsForRow(colWidths));
            specs.add(spec(rowAttrs(), EndTagType));
        }
        return specs;
    }

    static private List<ElementSpec> elementSpecsForRow(int[] colWidths) {
        ArrayList<ElementSpec> specs = new ArrayList<>();
        for (int colWidth : colWidths) {
            specs.addAll(elementSpecsForCell(colWidth));
        }
        return specs;
    }

    static private List<ElementSpec> elementSpecsForCell(int colWidth) {
        return Arrays.asList(
                spec(cellAttrs(colWidth), StartTagType),
                spec(emptyAttrs(), StartTagType),
                new ElementSpec(emptyAttrs(), ContentType, "\n".toCharArray(), 0, 1),
                spec(emptyAttrs(), EndTagType),
                spec(cellAttrs(colWidth), EndTagType)
        );
    }

    static ElementSpec spec(AttributeSet a, short type) { return new ElementSpec(a,type); }
    static private ElementSpec closeParagraphTag() { return spec(new SimpleAttributeSet(), EndTagType); }
    static private ElementSpec startTableTag()     { return spec(tableAttrs(), StartTagType); }
    static private ElementSpec endTableTag()       { return spec(tableAttrs(), EndTagType); }
    static private ElementSpec openParagraphTag()  { return spec(new SimpleAttributeSet(), StartTagType); }

    static private SimpleAttributeSet emptyAttrs() {
        return new SimpleAttributeSet();
    }

    static private SimpleAttributeSet tableAttrs() {
        SimpleAttributeSet set = emptyAttrs();
        set.addAttribute(ElementNameAttribute, ELEMENT_NAME_TABLE);
        return set;
    }

    static private SimpleAttributeSet rowAttrs() {
        SimpleAttributeSet set = emptyAttrs();
        set.addAttribute(ElementNameAttribute, ELEMENT_NAME_ROW);
        return set;
    }

    static private SimpleAttributeSet cellAttrs(int colWidth) {
        SimpleAttributeSet set = emptyAttrs();
        set.addAttribute(ElementNameAttribute, ELEMENT_NAME_CELL);
        set.addAttribute(PARAM_CELL_WIDTH, colWidth);
        return set;
    }

}