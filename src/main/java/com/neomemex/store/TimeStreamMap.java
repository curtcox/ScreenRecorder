package com.neomemex.store;

import com.neomemex.shared.Time;

import java.io.InputStream;
import java.io.OutputStream;

public interface TimeStreamMap {

    Time nearest(Time time);

    OutputStream output(Time time);

    InputStream input(Time time);

}
