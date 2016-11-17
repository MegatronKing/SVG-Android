package com.android.svg.support;

import com.android.svg.support.svg.Svg2Vector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Generator {

    public static void main(String[] args) {
        // Deprecated
        // Use the gradle plugin in your projects!

        try {
            Svg2Vector.parseSvgToXml(new File("D:\\git_tc\\SVG-Test\\svg_a\\ic_arrow.svg"), new FileOutputStream("D:\\test2.xml"), 24, 24);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
        }
    }

}
