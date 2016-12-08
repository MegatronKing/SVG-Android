package com.github.megatronking.svg.generator.svg.utils;

import com.github.megatronking.svg.generator.utils.Matrix;
import com.github.megatronking.svg.generator.utils.SCU;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransformUtils {

    private static final String VALUE_REGEX = "\\([([1-9]/d*/./d*|0/./d*[1-9]/d*|0?/.0+|0)\\s]+\\)";

    public static float[] formatTransform(String transform) {
        Matrix matrix = new Matrix();
        float translateX = 0f;
        float translateY = 0f;
        float scaleX = 1.0f;
        float scaleY = 1.0f;
        float pivotX = 0f;
        float pivotY = 0f;
        float rotation = 0f;
        String translateValue = regexFirstResult(transform, "translate" + VALUE_REGEX);
        if (translateValue != null) {
            translateValue = translateValue.replace("translate","").replace("(","").replace(")","").trim();
            if (translateValue.contains(" ")) {
                int index = translateValue.indexOf(" ");
                translateX = SCU.parseFloat(translateValue.substring(0, index).trim(), 0f);
                translateY = SCU.parseFloat(translateValue.substring(index).trim(), 0f);
            } else {
                translateX = SCU.parseFloat(translateValue, 0f);
            }
        }
        String scaleValue = regexFirstResult(transform, "scale" + VALUE_REGEX);
        if (scaleValue != null) {
            scaleValue = scaleValue.replace("scale","").replace("(","").replace(")","").trim();
            if (scaleValue.contains(" ")) {
                int index = scaleValue.indexOf(" ");
                scaleX = SCU.parseFloat(scaleValue.substring(0, index).trim(), 1.0f);
                scaleY = SCU.parseFloat(scaleValue.substring(index).trim(), 1.0f);
            } else {
                scaleX = SCU.parseFloat(scaleValue, 1.0f);
            }
        }
        String rotateValue = regexFirstResult(transform, "rotate" + VALUE_REGEX);
        if (rotateValue != null) {
            rotateValue = rotateValue.replace("rotate","").replace("(","").replace(")","").trim();
            if (rotateValue.contains(" ")) {
                int firstIndex = rotateValue.indexOf(" ");
                int lastIndex = rotateValue.lastIndexOf(" ");
                if (firstIndex != lastIndex) {
                    rotation = SCU.parseFloat(rotateValue.substring(0, firstIndex).trim(), 0f);
                    pivotX = SCU.parseFloat(rotateValue.substring(firstIndex, lastIndex).trim(), 0f);
                    pivotY = SCU.parseFloat(rotateValue.substring(lastIndex).trim(), 0f);
                }
            } else {
                rotation = SCU.parseFloat(rotateValue, 0f);
            }
            matrix.postTranslate(-pivotX, -pivotY);
            matrix.postScale(scaleX, scaleY);
            matrix.postRotate(rotation, 0, 0);
            matrix.postTranslate(translateX + pivotX, translateY + pivotY);
        }
        float[] matrixValue = new float[9];
        matrix.getValues(matrixValue);
        return new float[] {matrixValue[0], matrixValue[3], matrixValue[1], matrixValue[4], matrixValue[2], matrixValue[5]};
    }

    private static String regexFirstResult(String s, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}
