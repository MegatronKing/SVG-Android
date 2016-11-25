package com.android.svg.support.svgnew;

import com.android.svg.support.svgnew.model.Svg;
import com.android.svg.support.writer.impl.Svg2VectorTemplateWriter;

import org.dom4j.DocumentException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException | DocumentException e) {
            return "EXCEPTION in parsing " + inputSVG.getName() + ":\n" + e.getMessage();
        }
        return null;
    }
}
