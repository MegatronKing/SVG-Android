package com.github.megatronking.svg.applet.io;

import java.io.IOException;
import java.io.InputStream;

public class StringInputStream extends InputStream {

    private final String data;

    private int index;

    public StringInputStream(String data) {
        this.data = data;
    }

    @Override
    public int read() throws IOException {
        if (data == null || index > data.length() - 1) {
            return -1;
        }
        char charByIndex = data.charAt(index);
        index++;
        return charByIndex;
    }
}
