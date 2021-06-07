package com.neomemex.viewer;

import java.awt.image.BufferedImage;

// This interface is a namespace for 3 intertwined interfaces.
interface Viewer {

    interface Display {
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

        final String text; final Time time;

        public Request(String text, Time time) {
            this.text = text;
            this.time = time;
        }

        @Override public String toString() {
            return "Request{" + text + ":" + time + "}";
        }
    }

    final class Response {

        final Image image;

        public Response(Image image) {
            this.image = image;
        }
    }

}
