package com.android.svg.support.utils;

import static com.android.svg.support.Config.appColorMaps;
import static com.android.svg.support.Config.systemColorMaps;

/**
 * The helper handles color values used in parsing vector xml.
 *
 * @author Megatron King
 * @since 2016/8/31 21:48
 */

public class Color {

    private static final int DEFAULT_COLOR = 0x00000000;

    private static final String PREFIX = "#";

    private static final String REFERENCE_SYSTEM = "@android:color/";
    private static final String REFERENCE_APP = "@color/";

    public static int convert(String color) {
        if (color == null) {
            return DEFAULT_COLOR;
        }
        if (color.startsWith(PREFIX)) {
            return parseColor(color, DEFAULT_COLOR);
        }
        if (color.startsWith(REFERENCE_SYSTEM)) {
            return referenceSystem(color);
        }
        if (color.startsWith(REFERENCE_APP)) {
            return referenceApp(color);
        }
        return DEFAULT_COLOR;
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
