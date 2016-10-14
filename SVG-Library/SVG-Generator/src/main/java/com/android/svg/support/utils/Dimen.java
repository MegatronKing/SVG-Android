package com.android.svg.support.utils;

/**
 * The helper handles dimen values used in parsing vector xml.
 *
 * @author Megatron King
 * @since 2016/8/31 21:48
 */

public class Dimen {

    private static final float DEFAULT_VALUE = 0.0f;

    private static final String UNIT1 = "dp";
    private static final String UNIT2 = "dip";
    private static final String UNIT3 = "px";

    public static float convert(String value) {
        if (value == null) {
            return DEFAULT_VALUE;
        }
        if (value.endsWith(UNIT1)) {
            return SCU.parseFloat(value.substring(0, value.indexOf(UNIT1)), DEFAULT_VALUE);
        }
        if (value.endsWith(UNIT2)) {
            return SCU.parseFloat(value.substring(0, value.indexOf(UNIT2)), DEFAULT_VALUE);
        }
        if (value.endsWith(UNIT3)) {
            return SCU.parseFloat(value.substring(0, value.indexOf(UNIT3)), DEFAULT_VALUE);
        }
        return DEFAULT_VALUE;
    }

    public static boolean isDip(String value) {
        return value != null && (value.endsWith(UNIT1) || value.endsWith(UNIT2));
    }

    public static boolean isPx(String value) {
        return value != null && value.endsWith(UNIT3);
    }
}
