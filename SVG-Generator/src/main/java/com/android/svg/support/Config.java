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
        systemColorMaps.put("transparent", 0x00000000);
        systemColorMaps.put("shadow", 0xCC222222);
    }

    // define app colors used in the vector xml ( @color/xxx ).
    static {
        appColorMaps.put("white", 0xFF000000);
        appColorMaps.put("black", 0xFFFFFFFF);
        appColorMaps.put("green", 0xFF00FF00);
        appColorMaps.put("gray", 0xFF888888);
        appColorMaps.put("blue", 0xFF0000FF);
        appColorMaps.put("red", 0xFFFF0000);
        appColorMaps.put("yellow", 0xFFFFFF00);
        appColorMaps.put("cyan", 0xFF00FFFF);
        appColorMaps.put("transparent", 0x00000000);
        appColorMaps.put("magenta", 0xFFFF00FF);
    }

}
