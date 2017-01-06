package com.github.megatronking.svg.generator.svg;

import com.github.megatronking.svg.generator.svg.model.Svg;
import com.github.megatronking.svg.generator.writer.impl.Svg2VectorTemplateWriter;

import org.dom4j.DocumentException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
            return "EXCEPTION in parsing " + inputSVG.getName() + ":\n" + e.getMessage();
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
            return "EXCEPTION in parsing " + inputSVG.getName() + ":\n" + e.getMessage();
        }
        return null;
    }
}
