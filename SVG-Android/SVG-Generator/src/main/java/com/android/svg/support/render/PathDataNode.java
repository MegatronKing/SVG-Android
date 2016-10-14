package com.android.svg.support.render;

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

    private PathDataNode(PathDataNode n) {
        type = n.type;
        params = copyOfRange(n.params, 0, n.params.length);
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

    private static class ExtractFloatResult {
        // We need to return the position of the next separator and whether the
        // next float starts with a '-' or a '.'.
        int mEndPosition;
        boolean mEndWithNegOrDot;
    }
}
