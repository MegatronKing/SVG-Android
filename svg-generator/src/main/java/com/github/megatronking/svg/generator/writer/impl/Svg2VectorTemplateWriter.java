/*
 * Copyright (C) 2017, Megatron King
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.megatronking.svg.generator.writer.impl;

import com.github.megatronking.svg.generator.svg.model.Svg;
import com.github.megatronking.svg.generator.svg.model.SvgGroupNode;
import com.github.megatronking.svg.generator.svg.model.SvgNode;
import com.github.megatronking.svg.generator.utils.FloatUtils;
import com.github.megatronking.svg.generator.writer.IBufferWriter;

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
        bw.write(HEAD_INDENT + "android:width=\"" + FloatUtils.format2String(mSvg.w) + "dp\"");
        bw.newLine();
        bw.write(HEAD_INDENT + "android:height=\"" + FloatUtils.format2String(mSvg.h) + "dp\"");
        bw.newLine();
        bw.write(HEAD_INDENT + "android:viewportHeight=\"" + FloatUtils.format2String(mSvg.viewBox[3]) + "\"");
        bw.newLine();
        bw.write(HEAD_INDENT + "android:viewportWidth=\"" + FloatUtils.format2String(mSvg.viewBox[2]) + "\">");
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
        if (svgNode instanceof SvgGroupNode) {
            for (SvgNode group : ((SvgGroupNode)svgNode).children) {
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
