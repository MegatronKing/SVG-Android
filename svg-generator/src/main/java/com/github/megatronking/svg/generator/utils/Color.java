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
package com.github.megatronking.svg.generator.utils;

import java.lang.reflect.Field;
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
    public static final int SHADOW     = 0xCC222222;
    public static final int TRANSPARENT = 0;

    private static final int DEFAULT_COLOR = TRANSPARENT;

    private static final String PREFIX = "#";

    private static final String REFERENCE_SYSTEM = "@android:color/";
    private static final String REFERENCE_APP = "@color/";
    private static final String REFERENCE_RGB = "rgb";

    public static HashMap<String, Integer> appColorMaps = new HashMap<>();
    public static HashMap<String, Integer> systemColorMaps = new HashMap<>();
    public static HashMap<String, Integer> keywordColorMaps = new HashMap<>();

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
        systemColorMaps.put("shadow", SHADOW);
        systemColorMaps.put("none", TRANSPARENT);
    }

    // Define keyword colors used in the svg xml.
    // See https://www.w3.org/TR/SVG11/types.html#ColorKeywords
    static {
        try {
            Field[] fields = KeywordColors.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                keywordColorMaps.put(field.getName(), (Integer) field.get(null));
            }
        } catch (Exception e) {
            // ignore
        }
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
        if (color.startsWith(REFERENCE_RGB)) {
            return convertRGBToInteger(color);
        }
        if (keywordColorMaps.containsKey(color)) {
            return keywordColorMaps.get(color);
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
        } else if (colorString.length() == 4) {
            String newColorString = "0xff";
            for (int i = 0; i < colorString.length() - 1; i++) {
                newColorString += colorString.charAt(i);
                newColorString += colorString.charAt(i);
            }
            color = Long.parseLong(newColorString, 16);
        } else if (colorString.length() != 9) {
            return defaultColor;
        }
        return (int)color;
    }

    private static int convertRGBToInteger(String svgValue) {
        String result;
        String functionValue = svgValue.trim();
        functionValue = svgValue.substring(4, functionValue.length() - 1);
        // After we cut the "(", ")", we can deal with the numbers.
        String[] numbers = functionValue.split(",");
        if (numbers.length != 3) {
            return BLACK;
        }
        int[] color = new int[3];
        for (int i = 0; i < 3; i++) {
            String number = numbers[i];
            number = number.trim();
            if (number.endsWith("%")) {
                float value = Float.parseFloat(number.substring(0, number.length() - 1));
                color[i] = clamp((int) (value * 255.0f / 100.0f), 0, 255);
            } else {
                int value = Integer.parseInt(number);
                color[i] = clamp(value, 0, 255);
            }
        }
        StringBuilder builder = new StringBuilder("FF");
        for (int i = 0; i < 3; i++) {
            builder.append(String.format("%02X", color[i]));
        }
        result = builder.toString();
        return Integer.parseUnsignedInt(result, 16);
    }

    private static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}
