package com.android.svg.support.writer.impl;

import com.android.svg.support.writer.IBufferWriter;

import java.io.BufferedWriter;
import java.io.IOException;

public class SVGFakeXmlTemplateWriter implements IBufferWriter {

    @Override
    public void write(BufferedWriter bw) throws IOException {
        bw.write("<!-- AUTO-GENERATED FILE.  DO NOT MODIFY. -->");
        bw.newLine();
        bw.write("<shape xmlns:android=\"http://schemas.android.com/apk/res/android\" />");
    }
}
