package com.github.megatronking.svg.applet.graphics;

import com.github.megatronking.svg.generator.utils.PathDataNode;
import com.google.common.collect.ImmutableMap;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class PathUtils {

    private static final char INIT_TYPE = ' ';

    private static final ImmutableMap<Character, Integer> commandStepMap =
            ImmutableMap.<Character, Integer>builder()
                    .put('z', 2)
                    .put('Z', 2)
                    .put('m', 2)
                    .put('M', 2)
                    .put('l', 2)
                    .put('L', 2)
                    .put('t', 2)
                    .put('T', 2)
                    .put('h', 1)
                    .put('H', 1)
                    .put('v', 1)
                    .put('V', 1)
                    .put('c', 6)
                    .put('C', 6)
                    .put('s', 4)
                    .put('S', 4)
                    .put('q', 4)
                    .put('Q', 4)
                    .put('a', 7)
                    .put('A', 7)
                    .build();


    public static void transform(AffineTransform totalTransform, PathDataNode[] nodes) {
        Point2D.Float currentPoint = new Point2D.Float();
        Point2D.Float currentSegmentStartPoint = new Point2D.Float();
        char previousType = INIT_TYPE;
        for (PathDataNode node : nodes) {
            transform(node, totalTransform, currentPoint, currentSegmentStartPoint, previousType);
            previousType= node.type;
        }
    }

    private static void transform(PathDataNode node, AffineTransform totalTransform, Point2D.Float currentPoint,
                           Point2D.Float currentSegmentStartPoint, char previousType) {
        // For Horizontal / Vertical lines, we have to convert to LineTo with 2 parameters
        // And for arcTo, we also need to isolate the parameters for transformation.
        // Therefore a looping will be necessary for such commands.
        //
        // Note that if the matrix is translation only, then we can save many computations.

        int paramsLen = node.params.length;
        float[] tempParams = new float[2 * paramsLen];
        // These has to be pre-transformed value. In another word, the same as it is
        // in the pathData.
        float currentX = currentPoint.x;
        float currentY = currentPoint.y;
        float currentSegmentStartX = currentSegmentStartPoint.x;
        float currentSegmentStartY = currentSegmentStartPoint.y;

        int step = commandStepMap.get(node.type);
        switch (node.type) {
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
                currentX = node.params[paramsLen - 2];
                currentY = node.params[paramsLen - 1];
                if (node.type == 'M') {
                    currentSegmentStartX = currentX;
                    currentSegmentStartY = currentY;
                }

                totalTransform.transform(node.params, 0, node.params, 0, paramsLen / 2);
                break;
            case 'm':
                // We also need to workaround a bug in API 21 that 'm' after 'z'
                // is not picking up the relative value correctly.
                if (previousType == 'z' || previousType == 'Z') {
                    node.type = 'M';
                    node.params[0] += currentSegmentStartX;
                    node.params[1] += currentSegmentStartY;
                    currentSegmentStartX = node.params[0];
                    currentSegmentStartY = node.params[1];
                    for (int i = 1; i < paramsLen / step; i++) {
                        node.params[i * step] += node.params[(i - 1) * step];
                        node.params[i * step + 1] += node.params[(i - 1) * step + 1];
                    }
                    currentX = node.params[paramsLen - 2];
                    currentY = node.params[paramsLen - 1];

                    totalTransform.transform(node.params, 0, node.params, 0, paramsLen / 2);
                } else {

                    // We need to handle the initial 'm' similar to 'M' for first pair.
                    // Then all the following numbers are handled as 'l'
                    int startIndex = 0;
                    if (previousType == INIT_TYPE) {
                        int paramsLenInitialM = 2;
                        currentX = node.params[paramsLenInitialM - 2];
                        currentY = node.params[paramsLenInitialM - 1];
                        currentSegmentStartX = currentX;
                        currentSegmentStartY = currentY;

                        totalTransform.transform(node.params, 0, node.params, 0, paramsLenInitialM / 2);
                        startIndex = 1;
                    }
                    for (int i = startIndex; i < paramsLen / step; i++) {
                        int indexX = i * step + (step - 2);
                        int indexY = i * step + (step - 1);
                        currentX += node.params[indexX];
                        currentY += node.params[indexY];
                    }

                    if (!isTranslationOnly(totalTransform)) {
                        deltaTransform(totalTransform, node.params, 2 * startIndex,
                                paramsLen - 2 * startIndex);
                    }
                }

                break;
            case 'l':
            case 't':
            case 'c':
            case 's':
            case 'q':
                for (int i = 0; i < paramsLen / step; i ++) {
                    int indexX = i * step + (step - 2);
                    int indexY = i * step + (step - 1);
                    currentX += node.params[indexX];
                    currentY += node.params[indexY];
                }
                if (!isTranslationOnly(totalTransform)) {
                    deltaTransform(totalTransform, node.params, 0, paramsLen);
                }
                break;
            case 'H':
                node.type = 'L';
                for (int i = 0; i < paramsLen; i ++) {
                    tempParams[i * 2] = node.params[i];
                    tempParams[i * 2 + 1] = currentY;
                    currentX = node.params[i];
                }
                totalTransform.transform(tempParams, 0, tempParams, 0, paramsLen /*points*/);
                node.params = tempParams;
                break;
            case 'V':
                node.type = 'L';
                for (int i = 0; i < paramsLen; i ++) {
                    tempParams[i * 2] = currentX;
                    tempParams[i * 2 + 1] = node.params[i];
                    currentY = node.params[i];
                }
                totalTransform.transform(tempParams, 0, tempParams, 0, paramsLen /*points*/);
                node.params = tempParams;
                break;
            case 'h':
                for (int i = 0; i < paramsLen; i ++) {
                    // tempParams may not be used, but I would rather merge the code here.
                    tempParams[i * 2] = node.params[i];
                    currentX += node.params[i];
                    tempParams[i * 2 + 1] = 0;
                }
                if (!isTranslationOnly(totalTransform)) {
                    node.type = 'l';
                    deltaTransform(totalTransform, tempParams, 0, 2 * paramsLen);
                    node.params = tempParams;
                }
                break;
            case 'v':
                for (int i = 0; i < paramsLen; i++) {
                    // tempParams may not be used, but I would rather merge the code here.
                    tempParams[i * 2] = 0;
                    tempParams[i * 2 + 1] = node.params[i];
                    currentY += node.params[i];
                }

                if (!isTranslationOnly(totalTransform)) {
                    node.type = 'l';
                    deltaTransform(totalTransform, tempParams, 0, 2 * paramsLen);
                    node.params = tempParams;
                }
                break;
            case 'A':
                for (int i = 0; i < paramsLen / step; i ++) {
                    // (0:rx 1:ry 2:x-axis-rotation 3:large-arc-flag 4:sweep-flag 5:x 6:y)
                    // [0, 1, 2]
                    if (!isTranslationOnly(totalTransform)) {
                        EllipseSolver ellipseSolver = new EllipseSolver(totalTransform,
                                currentX, currentY,
                                node.params[i * step], node.params[i * step + 1], node.params[i * step + 2],
                                node.params[i * step + 3], node.params[i * step + 4],
                                node.params[i * step + 5], node.params[i * step + 6]);
                        node.params[i * step] = ellipseSolver.getMajorAxis();
                        node.params[i * step + 1] = ellipseSolver.getMinorAxis();
                        node.params[i * step + 2] = ellipseSolver.getRotationDegree();
                        if (ellipseSolver.getDirectionChanged()) {
                            node.params[i * step + 4] = 1 - node.params[i * step + 4];
                        }
                    }
                    // [5, 6]
                    currentX = node.params[i * step + 5];
                    currentY = node.params[i * step + 6];

                    totalTransform.transform(node.params, i * step + 5, node.params, i * step + 5, 1 /*1 point only*/);
                }
                break;
            case 'a':
                for (int i = 0; i < paramsLen / step; i ++) {
                    float oldCurrentX = currentX;
                    float oldCurrentY = currentY;

                    currentX += node.params[i * step + 5];
                    currentY += node.params[i * step + 6];
                    if (!isTranslationOnly(totalTransform)) {
                        EllipseSolver ellipseSolver = new EllipseSolver(totalTransform,
                                oldCurrentX, oldCurrentY,
                                node.params[i * step], node.params[i * step + 1], node.params[i * step + 2],
                                node.params[i * step + 3], node.params[i * step + 4],
                                oldCurrentX + node.params[i * step + 5],
                                oldCurrentY + node.params[i * step + 6]);
                        // (0:rx 1:ry 2:x-axis-rotation 3:large-arc-flag 4:sweep-flag 5:x 6:y)
                        // [5, 6]
                        deltaTransform(totalTransform, node.params, i * step + 5, 2);
                        // [0, 1, 2]
                        node.params[i * step] = ellipseSolver.getMajorAxis();
                        node.params[i * step + 1] = ellipseSolver.getMinorAxis();
                        node.params[i * step + 2] = ellipseSolver.getRotationDegree();
                        if (ellipseSolver.getDirectionChanged()) {
                            node.params[i * step + 4] = 1 - node.params[i * step + 4];
                        }
                    }

                }
                break;
            default:
                throw new IllegalArgumentException("Type is not right!!!");
        }
        currentPoint.setLocation(currentX, currentY);
        currentSegmentStartPoint.setLocation(currentSegmentStartX, currentSegmentStartY);
    }

    private static boolean isTranslationOnly(AffineTransform totalTransform) {
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
    private static void deltaTransform(AffineTransform totalTransform, float[] tempParams,
                                       int offset, int paramsLen) {
        double[] doubleArray = new double[paramsLen];
        for (int i = 0; i < paramsLen; i++)
        {
            doubleArray[i] = (double) tempParams[i + offset];
        }

        totalTransform.deltaTransform(doubleArray, 0, doubleArray, 0, paramsLen / 2);

        for (int i = 0; i < paramsLen; i++)
        {
            tempParams[i + offset] = (float) doubleArray[i];
        }
    }

}
