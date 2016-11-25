package com.android.svg.support.writer.impl;

import com.android.svg.support.svg.model.G;
import com.android.svg.support.svg.model.Svg;
import com.android.svg.support.svg.model.SvgNode;
import com.android.svg.support.writer.IBufferWriter;

import java.io.BufferedWriter;
import java.io.IOException;

public class Svg2VectorTemplateWriter implements IBufferWriter {

    private static final String HEAD_INDENT = "    ";

    private Svg mSvg;

    public Svg2VectorTemplateWriter(Svg svg) {
        this.mSvg = svg;
    }

    @Override
    public void write(BufferedWriter bw) throws IOException {
        bw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        bw.newLine();
        bw.write("<vector xmlns:android=\"http://schemas.android.com/apk/res/android\"");
        bw.newLine();
        bw.write(HEAD_INDENT + "android:width=\"" + mSvg.w + "dp\"");
        bw.newLine();
        bw.write(HEAD_INDENT + "android:height=\"" + mSvg.h + "dp\"");
        bw.newLine();
        bw.write(HEAD_INDENT + "android:viewportHeight=\"" + mSvg.viewBox[3] + "\"");
        bw.newLine();
        bw.write(HEAD_INDENT + "android:viewportWidth=\"" + mSvg.viewBox[2] + "\">");
        bw.newLine();
        for (SvgNode svgNode : mSvg.children) {
            writeSvgNodes(bw, svgNode , 1);
        }
        bw.write("</vector>");
        // The end.
        bw.flush();
        bw.close();
    }

    private void writeSvgNodes(BufferedWriter bw, SvgNode svgNode, int indentCount) throws IOException {
        if (!svgNode.isValid()) {
            return;
        }
        if (svgNode instanceof G) {
            for (SvgNode group : ((G)svgNode).children) {
                writeSvgNodes(bw, group, indentCount);
            }
        } else {
            String indent = "";
            for (int i = 0; i < indentCount; i++) {
                indent += HEAD_INDENT;
            }
            bw.write(indent);
            bw.write(svgNode.convert2VectorXml(indent));
            bw.newLine();
        }
    }
}
