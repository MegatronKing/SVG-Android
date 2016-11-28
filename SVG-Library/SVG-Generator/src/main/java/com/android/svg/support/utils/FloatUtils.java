package com.android.svg.support.utils;

public class FloatUtils {

    public static String format2String(float value) {
        // To avoid trailing zeros like 17.0, use this trick
        if (value == (long) value) {
            return String.valueOf((long) value);
        } else {
            return String.valueOf(value);
        }
    }

}
