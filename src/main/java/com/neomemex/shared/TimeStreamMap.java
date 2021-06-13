package com.neomemex.shared;

import java.io.InputStream;
import java.io.OutputStream;

public interface TimeStreamMap {

    OutputStream output(Time time);

    InputStream input(Time time);

}
