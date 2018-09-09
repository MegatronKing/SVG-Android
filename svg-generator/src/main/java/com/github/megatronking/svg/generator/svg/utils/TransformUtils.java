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
package com.github.megatronking.svg.generator.svg.utils;

import com.github.megatronking.svg.generator.utils.Matrix;
import com.github.megatronking.svg.generator.utils.SCU;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransformUtils {

    private static final String VALUE_REGEX = "\\([([1-9/-]/d*/./d*|0/./d*[1-9/-]/d*|0?/.0+|0|,)\\s]+\\)";

    public static float[] preConcat(float[] matrix1, float[] matrix2) {
        if (matrix1 == null) {
            matrix1 = new float[] {1, 0, 0, 1, 0, 0};
        }
        if (matrix2 == null) {
            matrix2 = new float[] {1, 0, 0, 1, 0, 0};
        }
        Matrix matrixTemp1 = new Matrix();
        matrixTemp1.setValues(new float[] {matrix1[0], matrix1[2], matrix1[4], matrix1[1], matrix1[3], matrix1[5], 0, 0, 1});
        Matrix matrixTemp2 = new Matrix();
        matrixTemp2.setValues(new float[] {matrix2[0], matrix2[2], matrix2[4], matrix2[1], matrix2[3], matrix2[5], 0, 0, 1});
        matrixTemp2.preConcat(matrixTemp1);
        float[] valueTemp = new float[9];
        matrixTemp2.getValues(valueTemp);
        return new float[] {valueTemp[0], valueTemp[3], valueTemp[1], valueTemp[4], valueTemp[2], valueTemp[5]};
    }

    public static float[] formatTransform(String transform) {
        Map<Integer, String> transformMaps = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer k1, Integer k2) {
                return k1.compareTo(k2);
            }
        });
        regexMatch(transform, "translate" + VALUE_REGEX, transformMaps);
        regexMatch(transform, "scale" + VALUE_REGEX, transformMaps);
        regexMatch(transform, "rotate" + VALUE_REGEX, transformMaps);
        Matrix resultMatrix = new Matrix();
        for (String value : transformMaps.values()) {
            Matrix matrix = new Matrix();
            if (value.startsWith("translate")) {
                doTranslate(matrix, value);
            }
            if (value.startsWith("scale")) {
                doScale(matrix, value);
            }
            if (value.startsWith("rotate")) {
                doRotation(matrix, value);
            }
            resultMatrix.preConcat(matrix);
        }
        float[] matrixValue = new float[9];
        resultMatrix.getValues(matrixValue);
        return new float[] {matrixValue[0], matrixValue[3], matrixValue[1], matrixValue[4], matrixValue[2], matrixValue[5]};
    }

    private static void regexMatch(String s, String regex, Map<Integer, String> transformMaps) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            transformMaps.put(matcher.start(), matcher.group().trim());
        }
    }

    private static void doTranslate(Matrix matrix, String translateValue) {
        float translateX;
        float translateY;
        translateValue = translateValue.replace("translate", "").replace("(", "").replace(")", "").trim();
        if (translateValue.contains(",")) {
            String[] arrays = translateValue.split(",");
            translateX = SCU.parseFloat(arrays[0].trim(), 0f);
            translateY = SCU.parseFloat(arrays[1].trim(), 0f);
        } else if (translateValue.contains(" ")) {
            int index = translateValue.indexOf(" ");
            translateX = SCU.parseFloat(translateValue.substring(0, index).trim(), 0f);
            translateY = SCU.parseFloat(translateValue.substring(index).trim(), 0f);
        } else {
            translateX = SCU.parseFloat(translateValue, 0f);
            translateY = 0f;
        }
        matrix.postTranslate(translateX, translateY);
    }

    private static void doScale(Matrix matrix, String scaleValue) {
        float scaleX;
        float scaleY;
        scaleValue = scaleValue.replace("scale", "").replace("(", "").replace(")", "").trim();
        if (scaleValue.contains(",")) {
            String[] arrays = scaleValue.split(",");
            scaleX = SCU.parseFloat(arrays[0].trim(), 1.0f);
            scaleY = SCU.parseFloat(arrays[1].trim(), 1.0f);
        } else if (scaleValue.contains(" ")) {
            int index = scaleValue.indexOf(" ");
            scaleX = SCU.parseFloat(scaleValue.substring(0, index).trim(), 1.0f);
            scaleY = SCU.parseFloat(scaleValue.substring(index).trim(), 1.0f);
        } else {
            scaleX = SCU.parseFloat(scaleValue, 1.0f);
            scaleY = 1.0f;
        }
        matrix.postScale(scaleX, scaleY);
    }

    private static void doRotation(Matrix matrix, String rotateValue) {
        float pivotX = 0f;
        float pivotY = 0f;
        float rotation = 0f;
        rotateValue = rotateValue.replace("rotate", "").replace("(", "").replace(")", "").trim();
        if (rotateValue.contains(",")) {
            String[] arrays = rotateValue.split(",");
            if (arrays.length == 2) {
                rotation = SCU.parseFloat(arrays[0].trim(), 0f);
                pivotX = SCU.parseFloat(arrays[1].trim(), 0f);
            }
            if (arrays.length == 3) {
                rotation = SCU.parseFloat(arrays[0].trim(), 0f);
                pivotX = SCU.parseFloat(arrays[1].trim(), 0f);
                pivotY = SCU.parseFloat(arrays[2].trim(), 0f);
            }
        } else if (rotateValue.contains(" ")) {
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
        matrix.postRotate(rotation, pivotX, pivotY);
    }
}
