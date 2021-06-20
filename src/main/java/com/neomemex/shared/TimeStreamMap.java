package com.neomemex.shared;

import java.io.InputStream;
import java.io.OutputStream;

public interface TimeStreamMap {

    Time nearest(Time time);

    OutputStream output(Time time);

    InputStream input(Time time);

}
