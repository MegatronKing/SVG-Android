package com.android.svg.support.utils;

import java.util.HashMap;

/**
 * The helper handles color values used in parsing vector xml.
 *
 * @author Megatron King
 * @since 2016/8/31 21:48
 */

public class Color {

    public static final int BLACK       = 0xFF000000;
    public static final int DKGRAY      = 0xFF444444;
    public static final int GRAY        = 0xFF888888;
    public static final int LTGRAY      = 0xFFCCCCCC;
    public static final int WHITE       = 0xFFFFFFFF;
    public static final int RED         = 0xFFFF0000;
    public static final int GREEN       = 0xFF00FF00;
    public static final int BLUE        = 0xFF0000FF;
    public static final int YELLOW      = 0xFFFFFF00;
    public static final int CYAN        = 0xFF00FFFF;
    public static final int MAGENTA     = 0xFFFF00FF;
    public static final int TRANSPARENT = 0;

    private static final int DEFAULT_COLOR = TRANSPARENT;

    private static final String PREFIX = "#";

    private static final String REFERENCE_SYSTEM = "@android:color/";
    private static final String REFERENCE_APP = "@color/";

    public static HashMap<String, Integer> appColorMaps = new HashMap<>();
    public static HashMap<String, Integer> systemColorMaps = new HashMap<>();

    // define system colors used in the vector xml ( @android:color/xxx ).
    static {
        systemColorMaps.put("white", WHITE);
        systemColorMaps.put("black", BLACK);
        systemColorMaps.put("dkgray", DKGRAY);
        systemColorMaps.put("gray", GRAY);
        systemColorMaps.put("ltgray", LTGRAY);
        systemColorMaps.put("red", RED);
        systemColorMaps.put("green", GREEN);
        systemColorMaps.put("blue", BLUE);
        systemColorMaps.put("yellow", YELLOW);
        systemColorMaps.put("cyan", CYAN);
        systemColorMaps.put("magenta", MAGENTA);
        systemColorMaps.put("transparent", TRANSPARENT);
        systemColorMaps.put("shadow", 0xCC222222);
    }

    public static int convert(String color) {
        return convert(color, DEFAULT_COLOR);
    }

    public static int convert(String color, int defaultColor) {
        if (color == null) {
            return defaultColor;
        }
        if (color.startsWith(PREFIX)) {
            return parseColor(color, defaultColor);
        }
        if (color.startsWith(REFERENCE_SYSTEM)) {
            return referenceSystem(color);
        }
        if (color.startsWith(REFERENCE_APP)) {
            return referenceApp(color);
        }
        return defaultColor;
    }

    private static int referenceSystem(String color) {
        String name = color.substring(REFERENCE_SYSTEM.length());
        return systemColorMaps.containsKey(name) ? systemColorMaps.get(name) : DEFAULT_COLOR;
    }

    private static int referenceApp(String color) {
        String name = color.substring(REFERENCE_APP.length());
        return appColorMaps.containsKey(name) ? appColorMaps.get(name) : DEFAULT_COLOR;
    }

    private static int parseColor(String colorString, int defaultColor) {
        long color = Long.parseLong(colorString.substring(1), 16);
        if (colorString.length() == 7) {
            // Set the alpha value
            color |= 0x00000000ff000000;
        } else if (colorString.length() != 9) {
            return defaultColor;
        }
        return (int)color;
    }
}
