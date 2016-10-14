package com.android.svg.support.writer;

import java.io.BufferedWriter;
import java.io.IOException;

public interface IBufferWriter {

    void write(BufferedWriter bw) throws IOException;
}
