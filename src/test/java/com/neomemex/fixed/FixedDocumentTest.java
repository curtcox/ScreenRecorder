package com.neomemex.fixed;

import com.neomemex.ocr.OCR;
import org.junit.Test;

import javax.swing.text.BadLocationException;
import javax.swing.text.Segment;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class FixedDocumentTest {

    static OCR.Word word(String text) {
        return new OCR.Word(0,0,0,0,text);
    }

    @Test
    public void can_create() {
        assertNotNull(FixedDocument.of(new OCR.Word[]{word("")}));
    }

    @Test
    public void getDefaultRootElement() {
        FixedDocument document = FixedDocument.of(new OCR.Word[]{word("")});
        assertNotNull(document.getDefaultRootElement());
    }

    @Test
    public void getText_fetches_contained_text() {
        FixedDocument document = FixedDocument.of(new OCR.Word[]{word("")});
        int offset =0;
        int length = 100;
        Segment segment = new Segment();
        document.getText(offset,length,segment);
        fail();
    }

//    Exception in thread "AWT-EventQueue-0" java.lang.RuntimeException
//    at com.neomemex.fixed.FixedDocument.no(FixedDocument.java:76)
//    at com.neomemex.fixed.FixedDocument.getText(FixedDocument.java:87)
//    at java.desktop/javax.swing.text.Utilities.getWordStart(Utilities.java:904)
//    at java.desktop/javax.swing.text.DefaultEditorKit$BeginWordAction.actionPerformed(DefaultEditorKit.java:1779)
//    at java.desktop/javax.swing.text.DefaultEditorKit$SelectWordAction.actionPerformed(DefaultEditorKit.java:2199)
//    at java.desktop/javax.swing.text.DefaultCaret.selectWord(DefaultCaret.java:397)
//    at java.desktop/javax.swing.text.DefaultCaret.mouseClicked(DefaultCaret.java:427)
//    at java.desktop/java.awt.AWTEventMulticaster.mouseClicked(AWTEventMulticaster.java:278)
    
    /**
     * Fetches the text contained within the given portion
     * of the document.
     * <p>
     * If the partialReturn property on the txt parameter is false, the
     * data returned in the Segment will be the entire length requested and
     * may or may not be a copy depending upon how the data was stored.
     * If the partialReturn property is true, only the amount of text that
     * can be returned without creating a copy is returned.  Using partial
     * returns will give better performance for situations where large
     * parts of the document are being scanned.  The following is an example
     * of using the partial return to access the entire document:
     *
     * <pre><code>
     *
     * &nbsp; int nleft = doc.getDocumentLength();
     * &nbsp; Segment text = new Segment();
     * &nbsp; int offs = 0;
     * &nbsp; text.setPartialReturn(true);
     * &nbsp; while (nleft &gt; 0) {
     * &nbsp;     doc.getText(offs, nleft, text);
     * &nbsp;     // do someting with text
     * &nbsp;     nleft -= text.count;
     * &nbsp;     offs += text.count;
     * &nbsp; }
     *
     * </code></pre>
     *
     * @param offset  the offset into the document representing the desired
     *   start of the text &gt;= 0
     * @param length  the length of the desired string &gt;= 0
     * @param txt the Segment object to return the text in
     *
     * @exception BadLocationException  Some portion of the given range
     *   was not a valid part of the document.  The location in the exception
     *   is the first bad position encountered.
     */
    //public void getText(int offset, int length, Segment txt) throws BadLocationException;
}
