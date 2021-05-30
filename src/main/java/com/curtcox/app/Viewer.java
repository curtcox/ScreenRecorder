package com.curtcox.app;

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

        final String text; final int days; final int minutes; final int seconds;

        public Request(String text, int days, int minutes, int seconds) {
            this.text = text;
            this.days = days;
            this.minutes = minutes;
            this.seconds = seconds;
        }
    }

    final class Response {

        final Image image;

        public Response(Image image) {
            this.image = image;
        }
    }

}
