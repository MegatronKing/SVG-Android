/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.svg.support.svg;

import com.android.SdkConstants;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Parse a VectorDrawble's XML file, and generate an internal tree representation,
 * which can be used for drawing / previewing.
 */
class VdParser {
    private static Logger logger = Logger.getLogger(VdParser.class.getSimpleName());
    private static final String PATH_SHIFT_X = "shift-x";
    private static final String PATH_SHIFT_Y = "shift-y";
    private static final String SHAPE_VECTOR = "vector";
    private static final String SHAPE_PATH = "path";
    private static final String SHAPE_GROUP = "group";
    private static final String PATH_ID = "android:name";
    private static final String PATH_DESCRIPTION = "android:pathData";
    private static final String PATH_FILL = "android:fillColor";
    private static final String PATH_FILL_OPACTIY = "android:fillAlpha";
    private static final String PATH_STROKE = "android:strokeColor";
    private static final String PATH_STROKE_OPACTIY = "android:strokeAlpha";
    private static final String PATH_STROKE_WIDTH = "android:strokeWidth";
    private static final String PATH_ROTATION = "android:rotation";
    private static final String PATH_ROTATION_X = "android:pivotX";
    private static final String PATH_ROTATION_Y = "android:pivotY";
    private static final String PATH_TRIM_START = "android:trimPathStart";
    private static final String PATH_TRIM_END = "android:trimPathEnd";
    private static final String PATH_TRIM_OFFSET = "android:trimPathOffset";
    private static final String PATH_STROKE_LINECAP = "android:strokeLinecap";
    private static final String PATH_STROKE_LINEJOIN = "android:strokeLinejoin";
    private static final String PATH_STROKE_MITERLIMIT = "android:strokeMiterlimit";
    private static final String PATH_CLIP = "android:clipToPath";
    private static final String LINECAP_BUTT = "butt";
    private static final String LINECAP_ROUND = "round";
    private static final String LINECAP_SQUARE = "square";
    private static final String LINEJOIN_MITER = "miter";
    private static final String LINEJOIN_ROUND = "round";
    private static final String LINEJOIN_BEVEL = "bevel";

    interface ElemParser {
        void parse(VdTree path, Attributes attributes);
    }

    ElemParser mParseSize = new ElemParser() {
        @Override
        public void parse(VdTree tree, Attributes attributes) {
            parseSize(tree, attributes);
        }
    };
    ElemParser mParsePath = new ElemParser() {
        @Override
        public void parse(VdTree tree, Attributes attributes) {
            VdPath p = parsePathAttributes(attributes);
            tree.add(p);
        }
    };
    ElemParser mParseGroup = new ElemParser() {
        @Override
        public void parse(VdTree tree, Attributes attributes) {
            VdGroup g = parseGroupAttributes(attributes);
            tree.add(g);
        }
    };
    HashMap<String, ElemParser> tagSwitch = new HashMap<String, ElemParser>();

    {
        tagSwitch.put(SHAPE_VECTOR, mParseSize);
        tagSwitch.put(SHAPE_PATH, mParsePath);
        tagSwitch.put(SHAPE_GROUP, mParseGroup);
        // TODO: add <g> tag and start to build the tree.
    }

    // Note that the incoming file is the VectorDrawable's XML file, not the SVG.
    // TODO: Use Document to parse and make sure no big performance difference.
    public VdTree parse(InputStream is, StringBuilder vdErrorLog) {
        try {
            final VdTree tree = new VdTree();
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            xr.setContentHandler(new ContentHandler() {
                String space = " ";

                @Override
                public void setDocumentLocator(Locator locator) {
                }

                @Override
                public void startDocument() throws SAXException {
                }

                @Override
                public void endDocument() throws SAXException {
                }

                @Override
                public void startPrefixMapping(String s, String s2) throws SAXException {
                }

                @Override
                public void endPrefixMapping(String s) throws SAXException {
                }

                @Override
                public void startElement(String s, String s2, String s3, Attributes attributes)
                        throws SAXException {
                    String name = s3;
                    if (tagSwitch.containsKey(name)) {
                        tagSwitch.get(name).parse(tree, attributes);
                    }
                    space += " ";
                }

                @Override
                public void endElement(String s, String s2, String s3) throws SAXException {
                    space = space.substring(1);
                }

                @Override
                public void characters(char[] chars, int i, int i2) throws SAXException {
                }

                @Override
                public void ignorableWhitespace(char[] chars, int i, int i2) throws SAXException {
                }

                @Override
                public void processingInstruction(String s, String s2) throws SAXException {
                }

                @Override
                public void skippedEntity(String s) throws SAXException {
                }
            });
            xr.parse(new InputSource(is));
            tree.parseFinish();
            return tree;
        } catch (Exception e) {
            vdErrorLog.append("Exception while parsing XML file:\n" + e.getMessage());
            return null;
        }
    }

    public VdParser() {
    }

    private static int nextStart(String s, int end) {
        char c;
        while (end < s.length()) {
            c = s.charAt(end);
            // Note that 'e' or 'E' are not valid path commands, but could be
            // used for floating point numbers' scientific notation.
            // Therefore, when searching for next command, we should ignore 'e'
            // and 'E'.
            if ((((c - 'A') * (c - 'Z') <= 0) || ((c - 'a') * (c - 'z') <= 0))
                    && c != 'e' && c != 'E') {
                return end;
            }
            end++;
        }
        return end;
    }

    public static VdPath.Node[] parsePath(String value) {
        int start = 0;
        int end = 1;
        ArrayList<VdPath.Node> list = new ArrayList<VdPath.Node>();
        while (end < value.length()) {
            end = nextStart(value, end);
            String s = value.substring(start, end);
            float[] val = getFloats(s);
            addNode(list, s.charAt(0), val);
            start = end;
            end++;
        }
        if ((end - start) == 1 && start < value.length()) {
            addNode(list, value.charAt(start), new float[0]);
        }
        return list.toArray(new VdPath.Node[list.size()]);
    }

    private static class ExtractFloatResult {
        // We need to return the position of the next separator and whether the
        // next float starts with a '-' or a '.'.
        int mEndPosition;
        boolean mEndWithNegOrDot;
    }

    /**
     * Copies elements from {@code original} into a new array, from indexes start (inclusive) to
     * end (exclusive). The original order of elements is preserved.
     * If {@code end} is greater than {@code original.length}, the result is padded
     * with the value {@code 0.0f}.
     *
     * @param original the original array
     * @param start    the start index, inclusive
     * @param end      the end index, exclusive
     * @return the new array
     * @throws ArrayIndexOutOfBoundsException if {@code start < 0 || start > original.length}
     * @throws IllegalArgumentException       if {@code start > end}
     * @throws NullPointerException           if {@code original == null}
     */
    private static float[] copyOfRange(float[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        int copyLength = Math.min(resultLength, originalLength - start);
        float[] result = new float[resultLength];
        System.arraycopy(original, start, result, 0, copyLength);
        return result;
    }

    /**
     * Calculate the position of the next comma or space or negative sign
     *
     * @param s      the string to search
     * @param start  the position to start searching
     * @param result the result of the extraction, including the position of the
     *               the starting position of next number, whether it is ending with a '-'.
     */
    private static void extract(String s, int start, ExtractFloatResult result) {
        // Now looking for ' ', ',', '.' or '-' from the start.
        int currentIndex = start;
        boolean foundSeparator = false;
        result.mEndWithNegOrDot = false;
        boolean secondDot = false;
        boolean isExponential = false;
        for (; currentIndex < s.length(); currentIndex++) {
            boolean isPrevExponential = isExponential;
            isExponential = false;
            char currentChar = s.charAt(currentIndex);
            switch (currentChar) {
                case ' ':
                case ',':
                    foundSeparator = true;
                    break;
                case '-':
                    // The negative sign following a 'e' or 'E' is not a separator.
                    if (currentIndex != start && !isPrevExponential) {
                        foundSeparator = true;
                        result.mEndWithNegOrDot = true;
                    }
                    break;
                case '.':
                    if (!secondDot) {
                        secondDot = true;
                    } else {
                        // This is the second dot, and it is considered as a separator.
                        foundSeparator = true;
                        result.mEndWithNegOrDot = true;
                    }
                    break;
                case 'e':
                case 'E':
                    isExponential = true;
                    break;
            }
            if (foundSeparator) {
                break;
            }
        }
        // When there is nothing found, then we put the end position to the end
        // of the string.
        result.mEndPosition = currentIndex;
    }

    /**
     * parse the floats in the string this is an optimized version of parseFloat(s.split(",|\\s"));
     *
     * @param s the string containing a command and list of floats
     * @return array of floats
     */
    private static float[] getFloats(String s) {
        if (s.charAt(0) == 'z' || s.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] results = new float[s.length()];
            int count = 0;
            int startPosition = 1;
            int endPosition = 0;
            ExtractFloatResult result = new ExtractFloatResult();
            int totalLength = s.length();
            // The startPosition should always be the first character of the
            // current number, and endPosition is the character after the current
            // number.
            while (startPosition < totalLength) {
                extract(s, startPosition, result);
                endPosition = result.mEndPosition;
                if (startPosition < endPosition) {
                    results[count++] = Float.parseFloat(
                            s.substring(startPosition, endPosition));
                }
                if (result.mEndWithNegOrDot) {
                    // Keep the '-' or '.' sign with next number.
                    startPosition = endPosition;
                } else {
                    startPosition = endPosition + 1;
                }
            }
            return copyOfRange(results, 0, count);
        } catch (NumberFormatException e) {
            throw new RuntimeException("error in parsing \"" + s + "\"", e);
        }
    }

    // End of copy from PathParser.java
    ////////////////////////////////////////////////////////////////
    private static void addNode(ArrayList<VdPath.Node> list, char cmd, float[] val) {
        list.add(new VdPath.Node(cmd, val));
    }

    public VdTree parse(URL r, StringBuilder vdErrorLog) throws Exception {
        return parse(r.openStream(), vdErrorLog);
    }

    private void parseSize(VdTree vdTree, Attributes attributes) {
        Pattern pattern = Pattern.compile("^\\s*(\\d+(\\.\\d+)*)\\s*([a-zA-Z]+)\\s*$");
        HashMap<String, Integer> m = new HashMap<String, Integer>();
        m.put(SdkConstants.UNIT_PX, 1);
        m.put(SdkConstants.UNIT_DIP, 1);
        m.put(SdkConstants.UNIT_DP, 1);
        m.put(SdkConstants.UNIT_SP, 1);
        m.put(SdkConstants.UNIT_PT, 1);
        m.put(SdkConstants.UNIT_IN, 1);
        m.put(SdkConstants.UNIT_MM, 1);
        int len = attributes.getLength();
        for (int i = 0; i < len; i++) {
            String name = attributes.getQName(i);
            String value = attributes.getValue(i);
            Matcher matcher = pattern.matcher(value);
            float size = 0;
            if (matcher.matches()) {
                float v = Float.parseFloat(matcher.group(1));
                String unit = matcher.group(3).toLowerCase(Locale.getDefault());
                size = v;
            }
            // -- Extract dimension units.
            if ("android:width".equals(name)) {
                vdTree.mBaseWidth = size;
            } else if ("android:height".equals(name)) {
                vdTree.mBaseHeight = size;
            } else if ("android:viewportWidth".equals(name)) {
                vdTree.mPortWidth = Float.parseFloat(value);
            } else if ("android:viewportHeight".equals(name)) {
                vdTree.mPortHeight = Float.parseFloat(value);
            } else if ("android:alpha".equals(name)) {
                vdTree.mRootAlpha = Float.parseFloat(value);
            } else {
                continue;
            }
        }
    }

    private VdPath parsePathAttributes(Attributes attributes) {
        int len = attributes.getLength();
        VdPath vgPath = new VdPath();
        for (int i = 0; i < len; i++) {
            String name = attributes.getQName(i);
            String value = attributes.getValue(i);
            logger.log(Level.FINE, "name " + name + "value " + value);
            setNameValue(vgPath, name, value);
        }
        return vgPath;
    }

    private VdGroup parseGroupAttributes(Attributes attributes) {
        int len = attributes.getLength();
        VdGroup vgGroup = new VdGroup();
        for (int i = 0; i < len; i++) {
            String name = attributes.getQName(i);
            String value = attributes.getValue(i);
            logger.log(Level.FINE, "name " + name + "value " + value);
        }
        return vgGroup;
    }

    public void setNameValue(VdPath vgPath, String name, String value) {
        if (PATH_DESCRIPTION.equals(name)) {
            vgPath.mNode = parsePath(value);
        } else if (PATH_ID.equals(name)) {
            vgPath.mName = value;
        } else if (PATH_FILL.equals(name)) {
            vgPath.mFillColor = calculateColor(value);
            if (!Float.isNaN(vgPath.mFillOpacity)) {
                vgPath.mFillColor &= 0x00FFFFFF;
                vgPath.mFillColor |= ((int) (0xFF * vgPath.mFillOpacity)) << 24;
            }
        } else if (PATH_STROKE.equals(name)) {
            vgPath.mStrokeColor = calculateColor(value);
            if (!Float.isNaN(vgPath.mStrokeOpacity)) {
                vgPath.mStrokeColor &= 0x00FFFFFF;
                vgPath.mStrokeColor |= ((int) (0xFF * vgPath.mStrokeOpacity)) << 24;
            }
        } else if (PATH_FILL_OPACTIY.equals(name)) {
            vgPath.mFillOpacity = Float.parseFloat(value);
            vgPath.mFillColor &= 0x00FFFFFF;
            vgPath.mFillColor |= ((int) (0xFF * vgPath.mFillOpacity)) << 24;
        } else if (PATH_STROKE_OPACTIY.equals(name)) {
            vgPath.mStrokeOpacity = Float.parseFloat(value);
            vgPath.mStrokeColor &= 0x00FFFFFF;
            vgPath.mStrokeColor |= ((int) (0xFF * vgPath.mStrokeOpacity)) << 24;
        } else if (PATH_STROKE_WIDTH.equals(name)) {
            vgPath.mStrokeWidth = Float.parseFloat(value);
        } else if (PATH_ROTATION.equals(name)) {
            vgPath.mRotate = Float.parseFloat(value);
        } else if (PATH_SHIFT_X.equals(name)) {
            vgPath.mShiftX = Float.parseFloat(value);
        } else if (PATH_SHIFT_Y.equals(name)) {
            vgPath.mShiftY = Float.parseFloat(value);
        } else if (PATH_ROTATION_Y.equals(name)) {
            vgPath.mRotateY = Float.parseFloat(value);
        } else if (PATH_ROTATION_X.equals(name)) {
            vgPath.mRotateX = Float.parseFloat(value);
        } else if (PATH_CLIP.equals(name)) {
            vgPath.mClip = Boolean.parseBoolean(value);
        } else if (PATH_TRIM_START.equals(name)) {
            vgPath.mTrimPathStart = Float.parseFloat(value);
        } else if (PATH_TRIM_END.equals(name)) {
            vgPath.mTrimPathEnd = Float.parseFloat(value);
        } else if (PATH_TRIM_OFFSET.equals(name)) {
            vgPath.mTrimPathOffset = Float.parseFloat(value);
        } else if (PATH_STROKE_LINECAP.equals(name)) {
            if (LINECAP_BUTT.equals(value)) {
                vgPath.mStrokeLineCap = 0;
            } else if (LINECAP_ROUND.equals(value)) {
                vgPath.mStrokeLineCap = 1;
            } else if (LINECAP_SQUARE.equals(value)) {
                vgPath.mStrokeLineCap = 2;
            }
        } else if (PATH_STROKE_LINEJOIN.equals(name)) {
            if (LINEJOIN_MITER.equals(value)) {
                vgPath.mStrokeLineJoin = 0;
            } else if (LINEJOIN_ROUND.equals(value)) {
                vgPath.mStrokeLineJoin = 1;
            } else if (LINEJOIN_BEVEL.equals(value)) {
                vgPath.mStrokeLineJoin = 2;
            }
        } else if (PATH_STROKE_MITERLIMIT.equals(name)) {
            vgPath.mStrokeMiterlimit = Float.parseFloat(value);
        } else {
            logger.log(Level.FINE, ">>>>>> DID NOT UNDERSTAND ! \"" + name + "\" <<<<");
        }
    }

    private int calculateColor(String value) {
        int len = value.length();
        int ret;
        int k = 0;
        switch (len) {
            case 7: // #RRGGBB
                ret = (int) Long.parseLong(value.substring(1), 16);
                ret |= 0xFF000000;
                break;
            case 9: // #AARRGGBB
                ret = (int) Long.parseLong(value.substring(1), 16);
                break;
            case 4: // #RGB
                ret = (int) Long.parseLong(value.substring(1), 16);
                k |= ((ret >> 8) & 0xF) * 0x110000;
                k |= ((ret >> 4) & 0xF) * 0x1100;
                k |= ((ret) & 0xF) * 0x11;
                ret = k | 0xFF000000;
                break;
            case 5: // #ARGB
                ret = (int) Long.parseLong(value.substring(1), 16);
                k |= ((ret >> 16) & 0xF) * 0x11000000;
                k |= ((ret >> 8) & 0xF) * 0x110000;
                k |= ((ret >> 4) & 0xF) * 0x1100;
                k |= ((ret) & 0xF) * 0x11;
                break;
            default:
                return 0xFF000000;
        }
        logger.log(Level.FINE, "color = " + value + " = " + Integer.toHexString(ret));
        return ret;
    }
}