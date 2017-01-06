package com.github.megatronking.svg.generator.utils;

import java.util.ArrayList;

/**
 * Each PathDataNode represents one command in the "d" attribute of the svg file.
 * An array of PathDataNode can represent the whole "d" attribute.
 *
 * @author Megatron King
 * @since 2016/9/2 15:34
 */

public class PathDataNode {

    /*package*/
    public char type;
    public float[] params;

    private PathDataNode(char type, float[] params) {
        this.type = type;
        this.params = params;
    }

    public void transform(float a,
                          float b,
                          float c,
                          float d,
                          float e,
                          float f,
                          float[] pre) {
        int incr;
        float[] tempParams;
        float[] origParams;
        switch (type) {
            case 'z':
            case 'Z':
                return;
            case 'M':
            case 'L':
            case 'T':
                incr = 2;
                pre[0] = params[params.length - 2];
                pre[1] = params[params.length - 1];
                for (int i = 0; i < params.length; i += incr) {
                    matrix(a, b, c, d, e, f, i, i + 1);
                }
                break;
            case 'm':
            case 'l':
            case 't':
                incr = 2;
                pre[0] += params[params.length - 2];
                pre[1] += params[params.length - 1];
                for (int i = 0; i < params.length; i += incr) {
                    matrix(a, b, c, d, 0, 0, i, i + 1);
                }
                break;
            case 'h':
                type = 'l';
                pre[0] += params[params.length - 1];
                tempParams = new float[params.length * 2];
                origParams = params;
                params = tempParams;
                for (int i = 0; i < params.length; i += 2) {
                    params[i] = origParams[i / 2];
                    params[i + 1] = 0;
                    matrix(a, b, c, d, 0, 0, i, i + 1);
                }
                break;
            case 'H':
                type = 'L';
                pre[0] = params[params.length - 1];
                tempParams = new float[params.length * 2];
                origParams = params;
                params = tempParams;
                for (int i = 0; i < params.length; i += 2) {
                    params[i] = origParams[i / 2];
                    params[i + 1] = pre[1];
                    matrix(a, b, c, d, e, f, i, i + 1);
                }
                break;
            case 'v':
                pre[1] += params[params.length - 1];
                type = 'l';
                tempParams = new float[params.length * 2];
                origParams = params;
                params = tempParams;
                for (int i = 0; i < params.length; i += 2) {
                    params[i] = 0;
                    params[i + 1] = origParams[i / 2];
                    matrix(a, b, c, d, 0, 0, i, i + 1);
                }
                break;
            case 'V':
                type = 'L';
                pre[1] = params[params.length - 1];
                tempParams = new float[params.length * 2];
                origParams = params;
                params = tempParams;
                for (int i = 0; i < params.length; i += 2) {
                    params[i] = pre[0];
                    params[i + 1] = origParams[i / 2];
                    matrix(a, b, c, d, e, f, i, i + 1);
                }
                break;
            case 'C':
            case 'S':
            case 'Q':
                pre[0] = params[params.length - 2];
                pre[1] = params[params.length - 1];
                for (int i = 0; i < params.length; i += 2) {
                    matrix(a, b, c, d, e, f, i, i + 1);
                }
                break;
            case 's':
            case 'q':
            case 'c':
                pre[0] += params[params.length - 2];
                pre[1] += params[params.length - 1];
                for (int i = 0; i < params.length; i += 2) {
                    matrix(a, b, c, d, 0, 0, i, i + 1);
                }
                break;
            case 'a':
                incr = 7;
                pre[0] += params[params.length - 2];
                pre[1] += params[params.length - 1];
                for (int i = 0; i < params.length; i += incr) {
                    matrix(a, b, c, d, 0, 0, i, i + 1);
                    double ang = Math.toRadians(params[i + 2]);
                    params[i + 2] = (float) Math.toDegrees(ang + Math.atan2(b, d));
                    matrix(a, b, c, d, 0, 0, i + 5, i + 6);
                }
                break;
            case 'A':
                incr = 7;
                pre[0] = params[params.length - 2];
                pre[1] = params[params.length - 1];
                for (int i = 0; i < params.length; i += incr) {
                    matrix(a, b, c, d, e, f, i, i + 1);
                    double ang = Math.toRadians(params[i + 2]);
                    params[i + 2] = (float) Math.toDegrees(ang + Math.atan2(b, d));
                    matrix(a, b, c, d, e, f, i + 5, i + 6);
                }
                break;
        }
    }

    private void matrix(float a,
                float b,
                float c,
                float d,
                float e,
                float f,
                int offx,
                int offy) {
        float inx = (offx < 0) ? 1 : params[offx];
        float iny = (offy < 0) ? 1 : params[offy];
        float x = inx * a + iny * c + e;
        float y = inx * b + iny * d + f;
        if (offx >= 0) {
            params[offx] = x;
        }
        if (offy >= 0) {
            params[offy] = y;
        }
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
     * @param pathData The string representing a path, the same as "d" string in svg file.
     * @return an array of the PathDataNode.
     */
    public static PathDataNode[] createNodesFromPathData(String pathData) {
        if (pathData == null) {
            return null;
        }
        int start = 0;
        int end = 1;

        ArrayList<PathDataNode> list = new ArrayList<PathDataNode>();
        while (end < pathData.length()) {
            end = nextStart(pathData, end);
            String s = pathData.substring(start, end).trim();
            if (s.length() > 0) {
                float[] val = getFloats(s);
                addNode(list, s.charAt(0), val);
            }

            start = end;
            end++;
        }
        if ((end - start) == 1 && start < pathData.length()) {
            addNode(list, pathData.charAt(start), new float[0]);
        }
        return list.toArray(new PathDataNode[list.size()]);
    }

    public static void transform(float a,
                                 float b,
                                 float c,
                                 float d,
                                 float e,
                                 float f,
                                 PathDataNode[] nodes) {
        float[] pre = new float[2];
        for (PathDataNode node : nodes) {
            node.transform(a, b, c, d, e, f, pre);
        }
    }

    public static String nodeListToString(PathDataNode[] nodes) {
        String s = "";
        for (PathDataNode node : nodes) {
            s += node.type;
            int len = node.params.length;
            for (int i = 0; i < len; i++) {
                if (i > 0) {
                    s += ((i & 1) == 1) ? "," : " ";
                }
                s += FloatUtils.format2String(node.params[i]);
            }
        }
        return s;
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

    private static void addNode(ArrayList<PathDataNode> list, char cmd, float[] val) {
        list.add(new PathDataNode(cmd, val));
    }

    /**
     * Parse the floats in the string.
     * This is an optimized version of parseFloat(s.split(",|\\s"));
     *
     * @param s the string containing a command and list of floats
     * @return array of floats
     */
    private static float[] getFloats(String s) {
        if (s.charAt(0) == 'z' | s.charAt(0) == 'Z') {
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

    public static boolean hasRelMoveAfterClose(PathDataNode[] nodes) {
        char preType = ' ';
        for (PathDataNode n : nodes) {
            if ((preType == 'z' || preType == 'Z') && n.type == 'm') {
                return true;
            }
            preType = n.type;
        }
        return false;
    }

    private static class ExtractFloatResult {
        // We need to return the position of the next separator and whether the
        // next float starts with a '-' or a '.'.
        int mEndPosition;
        boolean mEndWithNegOrDot;
    }
}
