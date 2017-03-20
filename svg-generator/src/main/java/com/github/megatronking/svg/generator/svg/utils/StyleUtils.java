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

import com.github.megatronking.svg.generator.svg.css.CSSParser;
import com.github.megatronking.svg.generator.svg.css.CSSParserCallback;
import com.github.megatronking.svg.generator.utils.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class StyleUtils {

    public static Map<String, String> convertStyleString2Map(String style) {
        if (TextUtils.isEmpty(style)) {
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

    public static void fill2Map(String cssStyle, Map<String, Map<String, String>> map) {
        if (TextUtils.isEmpty(cssStyle) || map == null) {
            return;
        }
        CSSParser cssParser = new CSSParser();
        try {
            cssParser.parse(cssStyle, new CSSParserCallbackImpl(map), false);
        } catch (Exception e) {
            // unexpected exception
        }
    }

    private static class CSSParserCallbackImpl implements CSSParserCallback {

        private Map<String, Map<String, String>> map;

        private Map<String, String> styleMap;

        private boolean isInRule;
        private String propertySave;

        private CSSParserCallbackImpl(Map<String, Map<String, String>> map) {
            this.map = map;
        }

        @Override
        public void handleImport(String importString) {
            // do not handle this
        }

        @Override
        public void handleSelector(String selector) {
            if (!TextUtils.isEmpty(selector) && !isInRule) {
                styleMap = new HashMap<>();
                map.put(selector, styleMap);
            } else {
                styleMap = null;
            }
            propertySave = null;
        }

        @Override
        public void startRule() {
            isInRule = true;
            propertySave = null;
        }

        @Override
        public void handleProperty(String property) {
            if (!TextUtils.isEmpty(property) && isInRule && styleMap != null && propertySave == null) {
                propertySave = property.trim();
            }
        }

        @Override
        public void handleValue(String value) {
            if (!TextUtils.isEmpty(value) && isInRule && styleMap != null && propertySave != null) {
                styleMap.put(propertySave, value.trim());
                propertySave = null;
            }
        }

        @Override
        public void endRule() {
            isInRule = false;
            styleMap = null;
            propertySave = null;
        }
    }

}
