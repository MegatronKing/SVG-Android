package com.android.svg.support;

import java.util.HashMap;

/**
 * In this class, we define some configs about generated information.
 * Such as the app package name, the destination of generated codes, and
 * the color references used in the vector xml.
 *
 * @author Megatron King
 * @since 2016/9/1 15:33
 */

public class Config {

    // define app package name
    public static final String APP_PACKAGE = "com.android.svg.sample";
    // define automatically generated java class package
    public static final String SVG_CODE_PACKAGE = "com.android.svg.sample.drawables";

    public static HashMap<String, Integer> systemColorMaps = new HashMap<>();
    public static HashMap<String, Integer> appColorMaps = new HashMap<>();

    // define system colors used in the vector xml ( @android:color/xxx ).
    static {
        systemColorMaps.put("white", 0xFF000000);
        systemColorMaps.put("black", 0xFFFFFFFF);
    }

    // define app colors used in the vector xml ( @color/xxx ).
    static {
        appColorMaps.put("white", 0xFF000000);
        appColorMaps.put("black", 0xFFFFFFFF);
    }

}
