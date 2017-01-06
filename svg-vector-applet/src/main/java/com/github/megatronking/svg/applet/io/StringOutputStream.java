package com.github.megatronking.svg.applet.io;

import java.io.IOException;
import java.io.OutputStream;

public class StringOutputStream extends OutputStream {

    private StringBuffer dataBuffer = new StringBuffer();

    @Override
    public void write(int b) throws IOException {
        dataBuffer.append((char)b);
    }

    public String getString() {
        return dataBuffer.toString();
    }

}
