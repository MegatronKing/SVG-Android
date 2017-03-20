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
package com.github.megatronking.svg.generator.svg;

import com.github.megatronking.svg.generator.svg.model.Svg;
import com.github.megatronking.svg.generator.writer.impl.Svg2VectorTemplateWriter;

import org.dom4j.DocumentException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Converts SVG to VectorDrawable's XML.
 *
 * @author Megatron King
 * @since 2016/11/22 16:42
 */
public class Svg2Vector {

    /**
     * Convert a SVG file into VectorDrawable's XML content, if no error is found.
     *
     * @param inputSVG  The input SVG file.
     * @param outputVector The converted VectorDrawable's content. This can be
     *                  empty if there is any error found during parsing.
     * @param width The width of VectorDrawable's XML, such as 'android:width=18dip'.
     * @param height The height of VectorDrawable's XML, such as 'android:height=18dip'.
     * @return The error messages, which contain things like all the tags
     * VectorDrawable don't support or exception message.
     */
    public static String parseSvgToXml(File inputSVG, File outputVector, int width, int height) {
        // Check input params.
        if (inputSVG == null || outputVector == null) {
            return "Invalid input params!";
        }
        if (!inputSVG.exists() || inputSVG.length() == 0 || inputSVG.isDirectory()) {
            return "Invalid svg file: " + inputSVG.getName();
        }

        SvgSAXReader reader = new SvgSAXReader();
        try {
            Svg svg = reader.read(inputSVG);
            if (width > 0) {
                svg.w = width;
            }
            if (height > 0) {
                svg.h = height;
            }
            Svg2VectorTemplateWriter writer = new Svg2VectorTemplateWriter(svg);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputVector));
            writer.write(bufferedWriter);
        } catch (IOException | DocumentException e) {
            return "Exception in parsing " + inputSVG.getName() + ":\n" + e.getMessage();
        }
        return null;
    }

    public static String parseSvgToXml(File inputSVG, OutputStream outputStream, int width, int height) {
        // Check input params.
        if (inputSVG == null || outputStream == null) {
            return "Invalid input params!";
        }
        if (!inputSVG.exists() || inputSVG.length() == 0 || inputSVG.isDirectory()) {
            return "Invalid svg file: " + inputSVG.getName();
        }

        SvgSAXReader reader = new SvgSAXReader();
        try {
            Svg svg = reader.read(inputSVG);
            if (width > 0) {
                svg.w = width;
            }
            if (height > 0) {
                svg.h = height;
            }
            Svg2VectorTemplateWriter writer = new Svg2VectorTemplateWriter(svg);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(bufferedWriter);
        } catch (IOException | DocumentException e) {
            return "Exception in parsing " + inputSVG.getName() + ":\n" + e.getMessage();
        }
        return null;
    }

    public static String parseSvgToXml(InputStream inputStream, OutputStream outputStream, int width, int height) {
        // Check input params.
        if (inputStream == null || outputStream == null) {
            return "Invalid input params!";
        }

        SvgSAXReader reader = new SvgSAXReader();
        try {
            Svg svg = reader.read(inputStream);
            if (width > 0) {
                svg.w = width;
            }
            if (height > 0) {
                svg.h = height;
            }
            Svg2VectorTemplateWriter writer = new Svg2VectorTemplateWriter(svg);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(bufferedWriter);
        } catch (IOException | DocumentException e) {
            return "Exception when parsing :\n" + e.getMessage();
        }
        return null;
    }

}
