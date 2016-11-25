package com.android.svg.support.svgnew.utils;

import java.util.HashMap;
import java.util.Map;

public class StyleUtils {

    public static Map<String, String> convertStyleString2Map(String style) {
        if (style == null || style.length() == 0) {
            return null;
        }
        Map<String, String> styleMaps = new HashMap<>();
        String[] styleParts = style.split(";");
        for (String stylePart : styleParts) {
            String[] nameValue = stylePart.split(":");
            if (nameValue.length == 2) {
                styleMaps.put(nameValue[0].trim(), nameValue[1].trim());
            }
        }
        return styleMaps;
    }

}
