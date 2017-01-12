package com.github.megatronking.svg.generator.svg.model;

import com.github.megatronking.svg.generator.svg.utils.StyleUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * The style SVG element allows style sheets to be embedded directly within SVG content.
 *
 * @author Megatron King
 * @since 2017/1/4 10:26
 */

public class Style extends SvgNode {

    public String cssStyle;

    @Override
    public void toPath() {
        // nothing to do
    }

    @Override
    public void applyStyles(Map<String, String> inheritStyles, Map<String, Map<String, String>> defineStyles) {
        // Nothing to do
    }

    public Map<String, Map<String, String>> toStyle() {
        Map<String, Map<String, String>> styleMapsWithClass = new HashMap<>();
        StyleUtils.fill2Map(cssStyle, styleMapsWithClass);
        return styleMapsWithClass;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
