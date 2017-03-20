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
        float tryFloat = SCU.parseFloat(value, -1.0f);
        if (tryFloat != -1.0f) {
            return tryFloat;
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
