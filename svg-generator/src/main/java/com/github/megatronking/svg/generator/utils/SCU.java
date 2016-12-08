package com.github.megatronking.svg.generator.utils;

/**
 * Some string convert methods.
 *
 * @author Megatron King
 * @since 2016/8/31 21:48
 */
public class SCU {

    public static int parseInt(String string, int defaultValue) {
        int result = defaultValue;

        if (isEmpty(string)) {
            return result;
        }

        try {
            result = Integer.parseInt(string);
        } catch (Exception e) {
            // parse error
        }
        return result;
    }

    public static int parseInt(String string) {
        try {
            if (isEmpty(string)) {
                return 0;
            }
            Double d1 = Double.parseDouble(string);
            return d1.intValue();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean parseBoolean(String string, boolean defaultValue) {
        boolean result = defaultValue;

        if (isEmpty(string)) {
            return result;
        }

        try {
            result = Boolean.parseBoolean(string);
        } catch (Exception e) {
            // parse error
        }
        return result;

    }

    public static long parseLong(String string, long defaultValue) {
        long result = defaultValue;

        if (isEmpty(string)) {
            return result;
        }

        try {
            result = Long.parseLong(string);
        } catch (Exception e) {
            // parse error
        }
        return result;
    }

    public static float parseFloat(String string, float defaultValue) {
        float result = defaultValue;

        if (isEmpty(string)) {
            return result;
        }

        try {
            result = Float.parseFloat(string);
        } catch (Exception e) {
            // parse error
        }
        return result;
    }

    public static double parseDouble(String string, double defaultValue) {
        double result = defaultValue;

        if (isEmpty(string)) {
            return result;
        }

        try {
            result = Double.parseDouble(string);
        } catch (Exception e) {
            // parse error
        }
        return result;
    }

    public static byte parseByte(String string, byte defaultValue) {
        byte result = defaultValue;

        if (isEmpty(string)) {
            return result;
        }

        try {
            result = Byte.parseByte(string);
        } catch (Exception e) {
            // parse error
        }
        return result;
    }

    private static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }
}
