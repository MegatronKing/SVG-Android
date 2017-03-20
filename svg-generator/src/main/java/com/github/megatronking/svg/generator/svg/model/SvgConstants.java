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
package com.github.megatronking.svg.generator.svg.model;

/**
 * In this class, defines all the element names and attribute names of a svg xml file.
 *
 * @author Megatron King
 * @since 2016/11/22 18:15
 */

public class SvgConstants {

    // svg tags
    public static final String TAG_SVG = "svg";
    public static final String TAG_RECT = "rect";
    public static final String TAG_CIRCLE = "circle";
    public static final String TAG_ELLIPSE = "ellipse";
    public static final String TAG_LINE = "line";
    public static final String TAG_POLYGON = "polygon";
    public static final String TAG_POLYLINE = "polyline";
    public static final String TAG_GROUP = "g";
    public static final String TAG_PATH = "path";
    public static final String TAG_DEFS = "defs";
    public static final String TAG_STYLE = "style";
    public static final String TAG_USE = "use";
    public static final String TAG_SYMBOL = "symbol";

    // svg attributes
    public static final String ATTR_WIDTH = "width";
    public static final String ATTR_HEIGHT = "height";
    public static final String ATTR_VIEW_BOX1 = "viewBox";
    public static final String ATTR_VIEW_BOX2 = "viewbox";
    public static final String ATTR_VIEW_PORT1 = "viewPort";
    public static final String ATTR_VIEW_PORT2 = "viewport";

    // common attributes
    public static final String ATTR_ID = "id";
    public static final String ATTR_CLASS = "class";
    public static final String ATTR_TRANSFORM = "transform";
    public static final String ATTR_STYLE = "style";
    public static final String ATTR_DISPLAY = "display";
    public static final String ATTR_STROKE = "stroke";
    public static final String ATTR_STROKE_WIDTH = "stroke-width";
    public static final String ATTR_STROKE_OPACITY = "stroke-opacity";
    public static final String ATTR_STROKE_LINEJOINE = "stroke-linejoin";
    public static final String ATTR_STROKE_LINECAP = "stroke-linecap";
    public static final String ATTR_STROKE_MITERLIMIT = "stroke-miterlimit";
    public static final String ATTR_FILL_OPACITY = "fill-opacity";
    public static final String ATTR_FILL_RULE = "fill-rule";

    // rect attributes
    public static final String ATTR_X = "x";
    public static final String ATTR_Y = "y";
    // circle attributes
    public static final String ATTR_CX = "cx";
    public static final String ATTR_CY = "cy";
    public static final String ATTR_R = "r";
    // ellipse attributes
    public static final String ATTR_RX = "rx";
    public static final String ATTR_RY = "ry";
    // line attributes
    public static final String ATTR_X1 = "x1";
    public static final String ATTR_Y1 = "y1";
    public static final String ATTR_X2 = "x2";
    public static final String ATTR_Y2 = "y2";
    // polygon attributes
    public static final String ATTR_POINTS = "points";
    // group attributes
    public static final String ATTR_FILL = "fill";
    // path attributes
    public static final String ATTR_D = "d";
    // use attributes
    public static final String ATTR_HREF = "href";
}
