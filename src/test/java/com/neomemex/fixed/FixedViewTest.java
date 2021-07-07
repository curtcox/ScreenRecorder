package com.neomemex.fixed;

import com.neomemex.ocr.OCR;
import org.junit.Test;

import javax.swing.text.Position;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FixedViewTest {

    @Test
    public void can_create() {
        assertNotNull(new FixedView(null,0,0,new OCR.Word[0]));
    }

    @Test
    public void view_to_model_when_it_is_within_only_word() {
        FixedView view = new FixedView(null,0,0,new OCR.Word[]{
                new OCR.Word(0,0,10,10,"boo")
        });
        Position.Bias[] biasReturn = new Position.Bias[1];

        int location = view.viewToModel(5,5,null,biasReturn);

        assertEquals(0,location);
        assertEquals(Position.Bias.Forward,biasReturn[0]);
    }

    @Test
    public void view_to_model_when_it_is_within_first_of_two_words() {
        FixedView view = new FixedView(null,0,0,new OCR.Word[]{
                new OCR.Word(0,0,10,10,"here"),
                new OCR.Word(20,20,10,10,"there")
        });
        Position.Bias[] biasReturn = new Position.Bias[1];

        int location = view.viewToModel(5,5,null,biasReturn);

        assertEquals(0,location);
        assertEquals(Position.Bias.Forward,biasReturn[0]);
    }

    @Test
    public void view_to_model_when_it_is_within_second_of_two_words() {
        FixedView view = new FixedView(null,0,0,new OCR.Word[]{
                new OCR.Word(0,0,10,10,"here"),
                new OCR.Word(20,20,10,10,"there")
        });
        Position.Bias[] biasReturn = new Position.Bias[1];

        int location = view.viewToModel(25,25,null,biasReturn);

        assertEquals(4,location);
        assertEquals(Position.Bias.Forward,biasReturn[0]);
    }

}
