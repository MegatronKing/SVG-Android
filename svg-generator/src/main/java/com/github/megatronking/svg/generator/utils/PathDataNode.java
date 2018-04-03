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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Each PathDataNode represents one command in the "d" attribute of the svg file.
 * An array of PathDataNode can represent the whole "d" attribute.
 *
 * @author Megatron King
 * @since 2016/9/2 15:34
 */

public class PathDataNode {

    private static final char INIT_TYPE = ' ';

    /*package*/
    public char type;
    public float[] params;

    private static final Map<Character, Integer> commandStepMap =
            new HashMap<>();

    static {
        commandStepMap.put('z', 2);
        commandStepMap.put('Z', 2);
        commandStepMap.put('m', 2);
        commandStepMap.put('M', 2);
        commandStepMap.put('l', 2);
        commandStepMap.put('L', 2);
        commandStepMap.put('t', 2);
        commandStepMap.put('T', 2);
        commandStepMap.put('h', 1);
        commandStepMap.put('H', 1);
        commandStepMap.put('v', 1);
        commandStepMap.put('V', 1);
        commandStepMap.put('c', 6);
        commandStepMap.put('C', 6);
        commandStepMap.put('s', 4);
        commandStepMap.put('S', 4);
        commandStepMap.put('q', 4);
        commandStepMap.put('Q', 4);
        commandStepMap.put('a', 7);
        commandStepMap.put('A', 7);
    }

    private PathDataNode(char type, float[] params) {
        this.type = type;
        this.params = params;
    }

    public void transform(AffineTransform transform, Point currentPoint,
                          Point currentSegmentStartPoint, char previousType) {
        // These has to be pre-transformed value. In another word, the same as it is
        // in the pathData.
        float currentX = currentPoint.x;
        float currentY = currentPoint.y;
        float currentSegmentStartX = currentSegmentStartPoint.x;
        float currentSegmentStartY = currentSegmentStartPoint.y;

        int length = params.length;
        int step = commandStepMap.get(type);
        float[] tempParams = new float[2 * length];

        switch (type) {
            case 'z':
            case 'Z':
                currentX = currentSegmentStartX;
                currentY = currentSegmentStartY;
                break;
            case 'M':
            case 'L':
            case 'T':
            case 'C':
            case 'S':
            case 'Q':
                currentX = params[length - 2];
                currentY = params[length - 1];
                if (type == 'M') {
                    currentSegmentStartX = currentX;
                    currentSegmentStartY = currentY;
                }
                transform.transform(params, 0, params, 0, length / 2);
                break;
            case 'm':
                // We also need to workaround a bug in API 21 that 'm' after 'z'
                // is not picking up the relative value correctly.
                if (previousType == 'z' || previousType == 'Z') {
                    type = 'M';
                    params[0] += currentSegmentStartX;
                    params[1] += currentSegmentStartY;
                    currentSegmentStartX = params[0];
                    currentSegmentStartY = params[1];
                    for (int i = 1; i < length / step; i++) {
                        params[i * step] += params[(i - 1) * step];
                        params[i * step + 1] += params[(i - 1) * step + 1];
                    }
                    currentX = params[length - 2];
                    currentY = params[length - 1];

                    transform.transform(params, 0, params, 0, length / 2);
                } else {
                    // We need to handle the initial 'm' similar to 'M' for first pair.
                    // Then all the following numbers are handled as 'l'
                    int startIndex = 0;
                    if (previousType == INIT_TYPE) {
                        int paramsLenInitialM = 2;
                        currentX = params[paramsLenInitialM - 2];
                        currentY = params[paramsLenInitialM - 1];
                        currentSegmentStartX = currentX;
                        currentSegmentStartY = currentY;

                        transform.transform(params, 0, params, 0, paramsLenInitialM / 2);
                        startIndex = 1;
                    }
                    for (int i = startIndex; i < length / step; i++) {
                        int indexX = i * step + (step - 2);
                        int indexY = i * step + (step - 1);
                        currentX += params[indexX];
                        currentY += params[indexY];
                    }

                    if (!isTranslationOnly(transform)) {
                        deltaTransform(transform, params, 2 * startIndex,
                                length - 2 * startIndex);
                    }
                }

                break;
            case 'l':
            case 't':
            case 'c':
            case 's':
            case 'q':
                for (int i = 0; i < length / step; i ++) {
                    int indexX = i * step + (step - 2);
                    int indexY = i * step + (step - 1);
                    currentX += params[indexX];
                    currentY += params[indexY];
                }
                if (!isTranslationOnly(transform)) {
                    deltaTransform(transform, params, 0, length);
                }
                break;
            case 'h':
                for (int i = 0; i < length; i ++) {
                    // tempParams may not be used, but I would rather merge the code here.
                    tempParams[i * 2] = params[i];
                    currentX += params[i];
                    tempParams[i * 2 + 1] = 0;
                }
                if (!isTranslationOnly(transform)) {
                    type = 'l';
                    deltaTransform(transform, tempParams, 0, 2 * length);
                    params = tempParams;
                }
                break;
            case 'H':
                type = 'L';
                for (int i = 0; i < length; i ++) {
                    tempParams[i * 2] = params[i];
                    tempParams[i * 2 + 1] = currentY;
                    currentX = params[i];
                }
                transform.transform(tempParams, 0, tempParams, 0, length /*points*/);
                params = tempParams;
                break;
            case 'v':
                for (int i = 0; i < length; i++) {
                    // tempParams may not be used, but I would rather merge the code here.
                    tempParams[i * 2] = 0;
                    tempParams[i * 2 + 1] = params[i];
                    currentY += params[i];
                }

                if (!isTranslationOnly(transform)) {
                    type = 'l';
                    deltaTransform(transform, tempParams, 0, 2 * length);
                    params = tempParams;
                }
                break;
            case 'V':
                type = 'L';
                for (int i = 0; i < length; i ++) {
                    tempParams[i * 2] = currentX;
                    tempParams[i * 2 + 1] = params[i];
                    currentY = params[i];
                }
                transform.transform(tempParams, 0, tempParams, 0, length /*points*/);
                params = tempParams;
                break;
            case 'a':
                for (int i = 0; i < length / step; i ++) {
                    // (0:rx 1:ry 2:x-axis-rotation 3:large-arc-flag 4:sweep-flag 5:x 6:y)
                    // [0, 1, 2]
                    if (!isTranslationOnly(transform)) {
                        EllipseSolver ellipseSolver = new EllipseSolver(transform,
                                currentX, currentY,
                                params[i * step], params[i * step + 1], params[i * step + 2],
                                params[i * step + 3], params[i * step + 4],
                                params[i * step + 5], params[i * step + 6]);
                        params[i * step] = ellipseSolver.getMajorAxis();
                        params[i * step + 1] = ellipseSolver.getMinorAxis();
                        params[i * step + 2] = ellipseSolver.getRotationDegree();
                        if (ellipseSolver.getDirectionChanged()) {
                            params[i * step + 4] = 1 - params[i * step + 4];
                        }
                    } else {
                        // No need to change the value of rx , ry, rotation, and flags.
                    }
                    // [5, 6]
                    currentX = params[i * step + 5];
                    currentY = params[i * step + 6];

                    transform.transform(params, i * step + 5, params, i * step + 5, 1 /*1 point only*/);
                }
                break;
            case 'A':
                for (int i = 0; i < length / step; i ++) {
                    float oldCurrentX = currentX;
                    float oldCurrentY = currentY;

                    currentX += params[i * step + 5];
                    currentY += params[i * step + 6];
                    if (!isTranslationOnly(transform)) {
                        EllipseSolver ellipseSolver = new EllipseSolver(transform,
                                oldCurrentX, oldCurrentY,
                                params[i * step], params[i * step + 1], params[i * step + 2],
                                params[i * step + 3], params[i * step + 4],
                                oldCurrentX + params[i * step + 5],
                                oldCurrentY + params[i * step + 6]);
                        // (0:rx 1:ry 2:x-axis-rotation 3:large-arc-flag 4:sweep-flag 5:x 6:y)
                        // [5, 6]
                        deltaTransform(transform, params, i * step + 5, 2);
                        // [0, 1, 2]
                        params[i * step] = ellipseSolver.getMajorAxis();
                        params[i * step + 1] = ellipseSolver.getMinorAxis();
                        params[i * step + 2] = ellipseSolver.getRotationDegree();
                        if (ellipseSolver.getDirectionChanged()) {
                            params[i * step + 4] = 1 - params[i * step + 4];
                        }
                    }

                }
                break;
        }
        currentPoint.set(currentX, currentY);
        currentSegmentStartPoint.set(currentSegmentStartX, currentSegmentStartY);
    }

    private boolean isTranslationOnly(AffineTransform totalTransform) {
        int type = totalTransform.getType();
        return type == AffineTransform.TYPE_IDENTITY
                || type == AffineTransform.TYPE_TRANSLATION;
    }

    /**
     * Convert the <code>tempParams</code> into a double array, then apply the
     * delta transform and convert it back to float array.
     * @param offset in number of floats, not points.
     * @param paramsLen in number of floats, not points.
     */
    private void deltaTransform(AffineTransform totalTransform, float[] tempParams,
                                       int offset, int paramsLen) {
        float[] doubleArray = new float[paramsLen];
        for (int i = 0; i < paramsLen; i++) {
            doubleArray[i] = tempParams[i + offset];
        }
        totalTransform.deltaTransform(doubleArray, 0, doubleArray, 0, paramsLen / 2);
        for (int i = 0; i < paramsLen; i++) {
            tempParams[i + offset] = doubleArray[i];
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
        Point currentPoint = new Point();
        Point currentSegmentStartPoint = new Point();
        char previousType = INIT_TYPE;
        AffineTransform transform = new AffineTransform(a, b, c, d, e, f);
        for (PathDataNode node : nodes) {
            node.transform(transform, currentPoint, currentSegmentStartPoint, previousType);
            previousType= node.type;
        }
    }

    public static String nodeListToString(PathDataNode[] nodes) {
        StringBuilder s = new StringBuilder();
        for (PathDataNode node : nodes) {
            s.append(node.type);
            int len = node.params.length;
            for (int i = 0; i < len; i++) {
                if (i > 0) {
                    s.append(((i & 1) == 1) ? "," : " ");
                }
                s.append(FloatUtils.format2String(node.params[i]));
            }
        }
        return s.toString();
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
            int endPosition;

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

    private static class EllipseSolver {

        // Final results:
        private float mMajorAxis = 0;

        private float mMinorAxis = 0;

        private float mRotationDegree = 0;

        private boolean mDirectionChanged;

        // Intermediate results:
        private Point mMajorAxisPoint;

        private Point mMiddlePoint;

        private Point mMinorAxisPoint;

        private Point mDstMajorAxisPoint;

        private Point mDstMiddlePoint;

        private Point mDstMinorAxisPoint;

        /**
         * Rotate the Point2D by radians
         *
         * @return the rotated Point2D
         */
        private Point rotatePoint2D(Point inPoint, float radians) {
            Point result = new Point();
            float cosine = (float) Math.cos(radians);
            float sine = (float) Math.sin(radians);
            result.x = inPoint.x * cosine - inPoint.y * sine;
            result.y = inPoint.x * sine + inPoint.y * cosine;
            return result;
        }

        /**
         * Construct the solver with all necessary parameters, and all the output value will be
         * ready after this constructor is called.
         *
         * Note that all the x y values are in absolute coordinates, such that we can apply the
         * transform directly.
         */
        private EllipseSolver(AffineTransform totalTransform,
                      float currentX, float currentY, float rx, float ry, float xAxisRotation,
                      float largeArcFlag, float sweepFlag, float destX, float destY) {
            boolean largeArc = largeArcFlag != 0;
            boolean sweep = sweepFlag != 0;

            // Compute the cx and cy first.
            Point originalCenter = computeOriginalCenter(currentX, currentY, rx, ry,
                    xAxisRotation, largeArc, sweep, destX, destY);

            // Compute 3 points from original ellipse
            computeControlPoints(rx, ry, xAxisRotation, originalCenter.x, originalCenter.y);

            // Transform 3 points and center point into destination.
            mDstMiddlePoint = totalTransform.transform(mMiddlePoint, null);
            mDstMajorAxisPoint = totalTransform.transform(mMajorAxisPoint, null);
            mDstMinorAxisPoint = totalTransform.transform(mMinorAxisPoint, null);
            Point dstCenter = totalTransform.transform(originalCenter, null);
            float dstCenterX = dstCenter.getX();
            float dstCenterY = dstCenter.getY();

            // Compute the relative 3 points:
            float relativeDstMiddleX = mDstMiddlePoint.x - dstCenterX;
            float relativeDstMiddleY = mDstMiddlePoint.y - dstCenterY;
            float relativeDstMajorAxisPointX = mDstMajorAxisPoint.x - dstCenterX;
            float relativeDstMajorAxisPointY = mDstMajorAxisPoint.y - dstCenterY;
            float relativeDstMinorAxisPointX = mDstMinorAxisPoint.x - dstCenterX;
            float relativeDstMinorAxisPointY = mDstMinorAxisPoint.y - dstCenterY;

            // Check if the direction has change!
            mDirectionChanged = computeDirectionChange(mMiddlePoint, mMajorAxisPoint,
                    mMinorAxisPoint,
                    mDstMiddlePoint, mDstMajorAxisPoint, mDstMinorAxisPoint);

            // From 3 dest points, recompute the a, b and theta.
            computeABThetaFromControlPoints(relativeDstMiddleX, relativeDstMiddleY,
                    relativeDstMajorAxisPointX,
                    relativeDstMajorAxisPointY, relativeDstMinorAxisPointX,
                    relativeDstMinorAxisPointY);
        }

        /**
         * After a random transformation, the controls points may change its direction, left handed <->
         * right handed. In this case, we better flip the flag for the ArcTo command.
         *
         * Here, we use the cross product to figure out the direction of the 3 control points for the
         * src and dst ellipse.
         */
        private boolean computeDirectionChange(final Point middlePoint,
                                               final Point majorAxisPoint, final Point minorAxisPoint,
                                               final Point dstMiddlePoint, final Point dstMajorAxisPoint,
                                               final Point dstMinorAxisPoint) {
            // Compute both Cross Product, then compare the sign.
            float srcCrossProduct = getCrossProduct(middlePoint, majorAxisPoint, minorAxisPoint);
            float dstCrossProduct = getCrossProduct(dstMiddlePoint, dstMajorAxisPoint,
                    dstMinorAxisPoint);
            return srcCrossProduct * dstCrossProduct < 0;
        }

        private float getCrossProduct(final Point middlePoint,
                                      final Point majorAxisPoint, final Point minorAxisPoint) {
            float majorMinusMiddleX = majorAxisPoint.x - middlePoint.x;
            float majorMinusMiddleY = majorAxisPoint.y - middlePoint.y;

            float minorMinusMiddleX = minorAxisPoint.x - middlePoint.x;
            float minorMinusMiddleY = minorAxisPoint.y - middlePoint.y;

            return (majorMinusMiddleX * minorMinusMiddleY) - (majorMinusMiddleY
                    * minorMinusMiddleX);
        }

        private void computeABThetaFromControlPoints(float relMiddleX, float relMiddleY,
                                                        float relativeMajorAxisPointX, float relativeMajorAxisPointY,
                                                        float relativeMinorAxisPointX, float relativeMinorAxisPointY) {
            float m11 = relMiddleX * relMiddleX;
            float m12 = relMiddleX * relMiddleY;
            float m13 = relMiddleY * relMiddleY;

            float m21 = relativeMajorAxisPointX * relativeMajorAxisPointX;
            float m22 = relativeMajorAxisPointX * relativeMajorAxisPointY;
            float m23 = relativeMajorAxisPointY * relativeMajorAxisPointY;

            float m31 = relativeMinorAxisPointX * relativeMinorAxisPointX;
            float m32 = relativeMinorAxisPointX * relativeMinorAxisPointY;
            float m33 = relativeMinorAxisPointY * relativeMinorAxisPointY;

            float det = -(m13 * m22 * m31 - m12 * m23 * m31 - m13 * m21 * m32
                    + m11 * m23 * m32 + m12 * m21 * m33 - m11 * m22 * m33);

            if (det == 0) {
                return;
            }
            float A = (-m13 * m22 + m12 * m23 + m13 * m32 - m23 * m32 - m12 * m33 + m22 * m33)
                    / det;
            float B = (m13 * m21 - m11 * m23 - m13 * m31 + m23 * m31 + m11 * m33 - m21 * m33) / det;
            float C = (m12 * m21 - m11 * m22 - m12 * m31 + m22 * m31 + m11 * m32 - m21 * m32)
                    / (-det);

            // Now we know A = cos(t)^2 / a^2 + sin(t)^2 / b^2
            // B = -2 cos(t) sin(t) (1/a^2 - 1/b^2)
            // C = sin(t)^2 / a^2 + cos(t)^2 / b^2

            // Solve it , we got
            // 2*t = arctan ( B / (A - C);
            if ((A - C) == 0) {
                // We know that a == b now.
                mMinorAxis = (float) Math.hypot(relativeMajorAxisPointX, relativeMajorAxisPointY);
                mMajorAxis = mMinorAxis;
                mRotationDegree = 0;
                return;
            }
            float doubleThetaInRadians = (float) Math.atan(B / (A - C));
            float thetaInRadians = doubleThetaInRadians / 2;
            if (Math.sin(doubleThetaInRadians) == 0) {
                mMinorAxis = (float) Math.sqrt(1 / C);
                mMajorAxis = (float) Math.sqrt(1 / A);
                mRotationDegree = 0;
                // This is a valid answer, so return false;
                return;
            }
            float bSqInv = (A + C + B / (float) Math.sin(doubleThetaInRadians)) / 2;
            float aSqInv = A + C - bSqInv;

            if (bSqInv == 0 || aSqInv == 0) {
                return;
            }
            mMinorAxis = (float) Math.sqrt(1 / bSqInv);
            mMajorAxis = (float) Math.sqrt(1 / aSqInv);

            mRotationDegree = (float) Math.toDegrees(Math.PI / 2 + thetaInRadians);
        }

        private void computeControlPoints(float a, float b, float rot, float cx, float cy) {
            mMajorAxisPoint = new Point(a, 0f);
            mMinorAxisPoint = new Point(0f, b);

            mMajorAxisPoint = rotatePoint2D(mMajorAxisPoint, rot);
            mMinorAxisPoint = rotatePoint2D(mMinorAxisPoint, rot);

            mMajorAxisPoint.x += cx;
            mMajorAxisPoint.y += cy;

            mMinorAxisPoint.x += cx;
            mMinorAxisPoint.y += cy;

            float middleDegree = 45; // This can be anything b/t 0 to 90.
            float middleRadians = (float) Math.toRadians(middleDegree);
            float middleR = a * b / (float) Math.hypot(b * (float) Math.cos(middleRadians),
                    a * (float) Math.sin(middleRadians));

            mMiddlePoint = new Point(middleR * (float) Math.cos(middleRadians),
                    middleR * (float) Math.sin(middleRadians));
            mMiddlePoint = rotatePoint2D(mMiddlePoint, rot);
            mMiddlePoint.x += cx;
            mMiddlePoint.y += cy;
        }

        private Point computeOriginalCenter(float x1, float y1, float rx, float ry,
                                                    float phi, boolean largeArc, boolean sweep, float x2, float y2) {
            Point result = new Point();
            float cosPhi = (float) Math.cos(phi);
            float sinPhi = (float) Math.sin(phi);
            float xDelta = (x1 - x2) / 2;
            float yDelta = (y1 - y2) / 2;
            float tempX1 = cosPhi * xDelta + sinPhi * yDelta;
            float tempY1 = -sinPhi * xDelta + cosPhi * yDelta;

            float rxSq = rx * rx;
            float rySq = ry * ry;
            float tempX1Sq = tempX1 * tempX1;
            float tempY1Sq = tempY1 * tempY1;

            float tempCenterFactor = rxSq * rySq - rxSq * tempY1Sq - rySq * tempX1Sq;
            tempCenterFactor /= rxSq * tempY1Sq + rySq * tempX1Sq;
            if (tempCenterFactor < 0) {
                tempCenterFactor = 0;
            }
            tempCenterFactor = (float) Math.sqrt(tempCenterFactor);
            if (largeArc == sweep) {
                tempCenterFactor = -tempCenterFactor;
            }
            float tempCx = tempCenterFactor * rx * tempY1 / ry;
            float tempCy = -tempCenterFactor * ry * tempX1 / rx;

            float xCenter = (x1 + x2) / 2;
            float yCenter = (y1 + y2) / 2;

            float cx = cosPhi * tempCx - sinPhi * tempCy + xCenter;
            float cy = sinPhi * tempCx + cosPhi * tempCy + yCenter;

            result.x = cx;
            result.y = cy;
            return result;
        }

        private float getMajorAxis() {
            return mMajorAxis;
        }

        private float getMinorAxis() {
            return mMinorAxis;
        }

        private float getRotationDegree() {
            return mRotationDegree;
        }

        private boolean getDirectionChanged() {
            return mDirectionChanged;
        }

    }

}
