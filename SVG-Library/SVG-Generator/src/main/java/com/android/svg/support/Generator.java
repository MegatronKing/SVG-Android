package com.android.svg.support;

import com.android.svg.support.svgnew.Svg2Vector;

import java.io.File;

public class Generator {

    public static void main(String[] args) {
        // Deprecated
        // Use the gradle plugin in your projects!

        String log1 = Svg2Vector.parseSvgToXml(new File("D:\\git_tc\\SVG-Test\\svg_a\\ic_arrow.svg"), new File("D:\\git_tc\\SVG-Android\\SVG-Sample\\app\\src\\main\\res_vector\\drawable\\test1.xml"), -1, -1);
        System.out.println("log " + log1);

//        try {
//            String log2 = com.android.svg.support.svg.Svg2Vector.parseSvgToXml(new File("D:\\git_tc\\SVG-Test\\svg_a\\ic_arrow.svg"), new FileOutputStream(new File("D:\\git_tc\\SVG-Android\\SVG-Sample\\app\\src\\main\\res_vector\\drawable\\test2.xml")), 24, 24);
//            System.out.println("log " + log2);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }

}
