package com.neomemex.viewer;

import com.neomemex.ocr.OCR;
import com.neomemex.shared.Highlight;
import com.neomemex.shared.Image;
import com.neomemex.shared.Time;

import java.awt.image.BufferedImage;

// This interface is a namespace for 3 intertwined interfaces.
public interface Viewer {

    interface Display {
        void show();
        void setTime(Time time,Time[] times);
        void setWords(OCR.Word[] words);
        void setHighlight(Highlight highlight);
        void setImage(BufferedImage image); // UI -- Invokers must always be on the EDT.
    }

    interface Retriever {
        Response request(Request request); // IO -- Implementors must never be on the EDT.
    }

    interface Requestor {
        // Invokers will be on the EDT, so implementors should return immediately without blocking.
        void request(Request request);
    }

    final class Request {

        public final String text; public final Time time;

        public Request(String text, Time time) {
            this.text = text;
            this.time = time;
        }

        @Override public String toString() {
            return "Request{" + text + ":" + time + "}";
        }
    }

    final class Response {

        final Image image; final Highlight highlight; final Time[] times;

        public Response(Image image,Highlight highlight, Time[] times) {
            this.image = image;
            this.highlight = highlight;
            this.times = times;
        }
    }

}
