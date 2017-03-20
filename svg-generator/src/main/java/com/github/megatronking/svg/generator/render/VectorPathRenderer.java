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
package com.github.megatronking.svg.generator.render;

import com.github.megatronking.svg.generator.utils.PathDataNode;
import com.github.megatronking.svg.generator.vector.model.ClipPath;
import com.github.megatronking.svg.generator.vector.model.Path;
import com.github.megatronking.svg.generator.utils.Matrix;

public class VectorPathRenderer extends NotifyVectorRenderer<Path> {

    private boolean isFillPaintInited;
    private boolean isStrokePaintInited;

    @Override
    public void render(Path path) {
        super.render(path);
        // no fill color and stroke color, no content to draw
        if (path.fillColor == 0 && path.strokeColor == 0) {
            return;
        }
        // calculate matrixScale
        float[] matrixValues = new float[9];
        Matrix groupStackedMatrix = path.parentGroup.getMatrix();
        groupStackedMatrix.getValues(matrixValues);
        float matrixScale = getMatrixScale(groupStackedMatrix);
        if (matrixScale == 0) {
            return;
        }

        resetPaths();
        writeNewLine();

        initFinalPathMatrix(matrixValues);
        writeNewLine();

        drawPathData(path.pathData);
        writeNewLine();

        if (path instanceof ClipPath) {
            drawClipPath();
        } else {
            drawPath(path, matrixScale);
        }
    }

    @Override
    protected void notifyResult(String result) {
        super.notifyResult(HEAD_SPACE + HEAD_SPACE + result + "\n");
    }

    private void resetPaths() {
        notifyResult("mPath.reset();");
        notifyResult("mRenderPath.reset();");
    }

    private void initFinalPathMatrix(float[] matrixValues) {
        notifyResult("mFinalPathMatrix.setValues(new float[]{" + matrixValues[0] + "f, " +
                matrixValues[1] + "f, " + matrixValues[2] + "f, " + matrixValues[3] + "f, " +
                matrixValues[4] + "f, " + matrixValues[5] + "f, " + matrixValues[6] + "f, " +
                matrixValues[7] + "f, " + matrixValues[8] + "f});");
        notifyResult("mFinalPathMatrix.postScale(scaleX, scaleY);");
    }

    private float getMatrixScale(Matrix groupStackedMatrix) {
        // Given unit vectors A = (0, 1) and B = (1, 0).
        // After matrix mapping, we got A' and B'. Let theta = the angel b/t A' and B'.
        // Therefore, the final scale we want is min(|A'| * sin(theta), |B'| * sin(theta)),
        // which is (|A'| * |B'| * sin(theta)) / max (|A'|, |B'|);
        // If  max (|A'|, |B'|) = 0, that means either x or y has a scale of 0.
        //
        // For non-skew case, which is most of the cases, matrix scale is computing exactly the
        // scale on x and y axis, and take the minimal of these two.
        // For skew case, an unit square will mapped to a parallelogram. And this function will
        // return the minimal height of the 2 bases.
        float[] unitVectors = new float[]{0, 1, 1, 0};
        groupStackedMatrix.mapVectors(unitVectors);
        float scaleX = (float) Math.hypot(unitVectors[0], unitVectors[1]);
        float scaleY = (float) Math.hypot(unitVectors[2], unitVectors[3]);
        float crossProduct = cross(unitVectors[0], unitVectors[1], unitVectors[2],
                unitVectors[3]);
        float maxScale = Math.max(scaleX, scaleY);

        float matrixScale = 0;
        if (maxScale > 0) {
            matrixScale = Math.abs(crossProduct) / maxScale;
        }
        return matrixScale;
    }

    private float cross(float v1x, float v1y, float v2x, float v2y) {
        return v1x * v2y - v1y * v2x;
    }

    private void drawPathData(String pathData) {
        PathDataNode[] nodes = PathDataNode.createNodesFromPathData(pathData);
        float[] current = new float[6];
        char previousCommand = 'm';
        for (int i = 0; i < nodes.length; i++) {
            addCommand(current, previousCommand, nodes[i].type, nodes[i].params);
            previousCommand = nodes[i].type;
        }
    }

    private void addCommand(float[] current, char previousCmd, char cmd, float[] val) {
        int incr = 2;
        float currentX = current[0];
        float currentY = current[1];
        float ctrlPointX = current[2];
        float ctrlPointY = current[3];
        float currentSegmentStartX = current[4];
        float currentSegmentStartY = current[5];
        float reflectiveCtrlPointX;
        float reflectiveCtrlPointY;

        switch (cmd) {
            case 'z':
            case 'Z':
                notifyResult("mPath.close();");
                // Path is closed here, but we need to move the pen to the
                // closed position. So we cache the segment's starting position,
                // and restore it here.
                currentX = currentSegmentStartX;
                currentY = currentSegmentStartY;
                ctrlPointX = currentSegmentStartX;
                ctrlPointY = currentSegmentStartY;
                notifyResult("mPath.moveTo(" + currentX + "f, " + ctrlPointY + "f);");
                break;
            case 'm':
            case 'M':
            case 'l':
            case 'L':
            case 't':
            case 'T':
                incr = 2;
                break;
            case 'h':
            case 'H':
            case 'v':
            case 'V':
                incr = 1;
                break;
            case 'c':
            case 'C':
                incr = 6;
                break;
            case 's':
            case 'S':
            case 'q':
            case 'Q':
                incr = 4;
                break;
            case 'a':
            case 'A':
                incr = 7;
                break;
        }

        for (int k = 0; k < val.length; k += incr) {
            switch (cmd) {
                case 'm': // moveto - Start a new sub-path (relative)
                    currentX += val[k];
                    currentY += val[k + 1];
                    if (k > 0) {
                        // According to the spec, if a moveto is followed by multiple
                        // pairs of coordinates, the subsequent pairs are treated as
                        // implicit lineto commands.
                        notifyResult("mPath.rLineTo(" + val[k] + "f, " + val[k + 1] + "f);");
                    } else {
                        notifyResult("mPath.rMoveTo(" + val[k] + "f, " + val[k + 1] + "f);");
                        currentSegmentStartX = currentX;
                        currentSegmentStartY = currentY;
                    }
                    break;
                case 'M': // moveto - Start a new sub-path
                    currentX = val[k];
                    currentY = val[k + 1];
                    if (k > 0) {
                        // According to the spec, if a moveto is followed by multiple
                        // pairs of coordinates, the subsequent pairs are treated as
                        // implicit lineto commands.
                        notifyResult("mPath.lineTo(" + val[k] + "f, " + val[k + 1] + "f);");
                    } else {
                        notifyResult("mPath.moveTo(" + val[k] + "f, " + val[k + 1] + "f);");
                        currentSegmentStartX = currentX;
                        currentSegmentStartY = currentY;
                    }
                    break;
                case 'l': // lineto - Draw a line from the current point (relative)
                    notifyResult("mPath.rLineTo(" + val[k] + "f, " + val[k + 1] + "f);");
                    currentX += val[k];
                    currentY += val[k + 1];
                    break;
                case 'L': // lineto - Draw a line from the current point
                    notifyResult("mPath.lineTo(" + val[k] + "f, " + val[k + 1] + "f);");
                    currentX = val[k];
                    currentY = val[k + 1];
                    break;
                case 'h': // horizontal lineto - Draws a horizontal line (relative)
                    notifyResult("mPath.rLineTo(" + val[k] + "f, 0f);");
                    currentX += val[k];
                    break;
                case 'H': // horizontal lineto - Draws a horizontal line
                    notifyResult("mPath.lineTo(" + val[k] + "f, " + currentY + "f);");
                    currentX = val[k];
                    break;
                case 'v': // vertical lineto - Draws a vertical line from the current point (r)
                    notifyResult("mPath.rLineTo(0f, " + val[k] + "f);");
                    currentY += val[k];
                    break;
                case 'V': // vertical lineto - Draws a vertical line from the current point
                    notifyResult("mPath.lineTo(" + currentX + "f, " + val[k] + "f);");
                    currentY = val[k];
                    break;
                case 'c': // curveto - Draws a cubic Bézier curve (relative)
                    notifyResult("mPath.rCubicTo(" + val[k] + "f, " + val[k + 1] + "f, " + val[k + 2]
                            + "f, " + val[k + 3] + "f, " + val[k + 4] + "f, " + val[k + 5] + "f);");
                    ctrlPointX = currentX + val[k + 2];
                    ctrlPointY = currentY + val[k + 3];
                    currentX += val[k + 4];
                    currentY += val[k + 5];

                    break;
                case 'C': // curveto - Draws a cubic Bézier curve
                    notifyResult("mPath.cubicTo(" + val[k] + "f, " + val[k + 1] + "f, " + val[k + 2]
                            + "f, " + val[k + 3] + "f, " + val[k + 4] + "f, " + val[k + 5] + "f);");
                    currentX = val[k + 4];
                    currentY = val[k + 5];
                    ctrlPointX = val[k + 2];
                    ctrlPointY = val[k + 3];
                    break;
                case 's': // smooth curveto - Draws a cubic Bézier curve (reflective cp)
                    reflectiveCtrlPointX = 0;
                    reflectiveCtrlPointY = 0;
                    if (previousCmd == 'c' || previousCmd == 's'
                            || previousCmd == 'C' || previousCmd == 'S') {
                        reflectiveCtrlPointX = currentX - ctrlPointX;
                        reflectiveCtrlPointY = currentY - ctrlPointY;
                    }
                    notifyResult("mPath.rCubicTo(" + reflectiveCtrlPointX + "f, " + reflectiveCtrlPointY + "f, " + val[k]
                            + "f, " + val[k + 1] + "f, " + val[k + 2] + "f, " + val[k + 3] + "f);");
                    ctrlPointX = currentX + val[k];
                    ctrlPointY = currentY + val[k + 1];
                    currentX += val[k + 2];
                    currentY += val[k + 3];
                    break;
                case 'S': // shorthand/smooth curveto Draws a cubic Bézier curve(reflective cp)
                    reflectiveCtrlPointX = currentX;
                    reflectiveCtrlPointY = currentY;
                    if (previousCmd == 'c' || previousCmd == 's'
                            || previousCmd == 'C' || previousCmd == 'S') {
                        reflectiveCtrlPointX = 2 * currentX - ctrlPointX;
                        reflectiveCtrlPointY = 2 * currentY - ctrlPointY;
                    }
                    notifyResult("mPath.cubicTo(" + reflectiveCtrlPointX + "f, " + reflectiveCtrlPointY + "f, " + val[k]
                            + "f, " + val[k + 1] + "f, " + val[k + 2] + "f, " + val[k + 3] + "f);");
                    ctrlPointX = val[k];
                    ctrlPointY = val[k + 1];
                    currentX = val[k + 2];
                    currentY = val[k + 3];
                    break;
                case 'q': // Draws a quadratic Bézier (relative)
                    notifyResult("mPath.rQuadTo(" + val[k] + "f, " + val[k + 1] + "f, " + val[k + 2] + "f, " + val[k + 3] + "f);");
                    ctrlPointX = currentX + val[k];
                    ctrlPointY = currentY + val[k + 1];
                    currentX += val[k + 2];
                    currentY += val[k + 3];
                    break;
                case 'Q': // Draws a quadratic Bézier
                    notifyResult("mPath.quadTo(" + val[k] + "f, " + val[k + 1] + "f, " + val[k + 2] + "f, " + val[k + 3] + "f);");
                    ctrlPointX = val[k];
                    ctrlPointY = val[k + 1];
                    currentX = val[k + 2];
                    currentY = val[k + 3];
                    break;
                case 't': // Draws a quadratic Bézier curve(reflective control point)(relative)
                    reflectiveCtrlPointX = 0;
                    reflectiveCtrlPointY = 0;
                    if (previousCmd == 'q' || previousCmd == 't'
                            || previousCmd == 'Q' || previousCmd == 'T') {
                        reflectiveCtrlPointX = currentX - ctrlPointX;
                        reflectiveCtrlPointY = currentY - ctrlPointY;
                    }
                    notifyResult("mPath.rQuadTo(" + reflectiveCtrlPointX + "f, " + reflectiveCtrlPointY + "f, " + val[k] + "f, " + val[k + 1] + "f);");
                    ctrlPointX = currentX + reflectiveCtrlPointX;
                    ctrlPointY = currentY + reflectiveCtrlPointY;
                    currentX += val[k];
                    currentY += val[k + 1];
                    break;
                case 'T': // Draws a quadratic Bézier curve (reflective control point)
                    reflectiveCtrlPointX = currentX;
                    reflectiveCtrlPointY = currentY;
                    if (previousCmd == 'q' || previousCmd == 't'
                            || previousCmd == 'Q' || previousCmd == 'T') {
                        reflectiveCtrlPointX = 2 * currentX - ctrlPointX;
                        reflectiveCtrlPointY = 2 * currentY - ctrlPointY;
                    }
                    notifyResult("mPath.quadTo(" + reflectiveCtrlPointX + "f, " + reflectiveCtrlPointY + "f, " + val[k] + "f, " + val[k + 1] + "f);");
                    ctrlPointX = reflectiveCtrlPointX;
                    ctrlPointY = reflectiveCtrlPointY;
                    currentX = val[k];
                    currentY = val[k + 1];
                    break;
                case 'a': // Draws an elliptical arc
                    // (rx ry x-axis-rotation large-arc-flag sweep-flag x y)
                    drawArc(currentX,
                            currentY,
                            val[k + 5] + currentX,
                            val[k + 6] + currentY,
                            val[k],
                            val[k + 1],
                            val[k + 2],
                            val[k + 3] != 0,
                            val[k + 4] != 0);
                    currentX += val[k + 5];
                    currentY += val[k + 6];
                    ctrlPointX = currentX;
                    ctrlPointY = currentY;
                    break;
                case 'A': // Draws an elliptical arc
                    drawArc(currentX,
                            currentY,
                            val[k + 5],
                            val[k + 6],
                            val[k],
                            val[k + 1],
                            val[k + 2],
                            val[k + 3] != 0,
                            val[k + 4] != 0);
                    currentX = val[k + 5];
                    currentY = val[k + 6];
                    ctrlPointX = currentX;
                    ctrlPointY = currentY;
                    break;
            }
            previousCmd = cmd;
        }
        current[0] = currentX;
        current[1] = currentY;
        current[2] = ctrlPointX;
        current[3] = ctrlPointY;
        current[4] = currentSegmentStartX;
        current[5] = currentSegmentStartY;
    }

    private void drawArc(float x0,
                         float y0,
                         float x1,
                         float y1,
                         float a,
                         float b,
                         float theta,
                         boolean isMoreThanHalf,
                         boolean isPositiveArc) {

            /* Convert rotation angle from degrees to radians */
        double thetaD = Math.toRadians(theta);
            /* Pre-compute rotation matrix entries */
        double cosTheta = Math.cos(thetaD);
        double sinTheta = Math.sin(thetaD);
            /* Transform (x0, y0) and (x1, y1) into unit space */
            /* using (inverse) rotation, followed by (inverse) scale */
        double x0p = (x0 * cosTheta + y0 * sinTheta) / a;
        double y0p = (-x0 * sinTheta + y0 * cosTheta) / b;
        double x1p = (x1 * cosTheta + y1 * sinTheta) / a;
        double y1p = (-x1 * sinTheta + y1 * cosTheta) / b;

            /* Compute differences and averages */
        double dx = x0p - x1p;
        double dy = y0p - y1p;
        double xm = (x0p + x1p) / 2;
        double ym = (y0p + y1p) / 2;
            /* Solve for intersecting unit circles */
        double dsq = dx * dx + dy * dy;
        if (dsq == 0.0) {
            return; /* Points are coincident */
        }
        double disc = 1.0 / dsq - 1.0 / 4.0;
        if (disc < 0.0) {
            float adjust = (float) (Math.sqrt(dsq) / 1.99999);
            drawArc(x0, y0, x1, y1, a * adjust,
                    b * adjust, theta, isMoreThanHalf, isPositiveArc);
            return; /* Points are too far apart */
        }
        double s = Math.sqrt(disc);
        double sdx = s * dx;
        double sdy = s * dy;
        double cx;
        double cy;
        if (isMoreThanHalf == isPositiveArc) {
            cx = xm - sdy;
            cy = ym + sdx;
        } else {
            cx = xm + sdy;
            cy = ym - sdx;
        }

        double eta0 = Math.atan2((y0p - cy), (x0p - cx));

        double eta1 = Math.atan2((y1p - cy), (x1p - cx));

        double sweep = (eta1 - eta0);
        if (isPositiveArc != (sweep >= 0)) {
            if (sweep > 0) {
                sweep -= 2 * Math.PI;
            } else {
                sweep += 2 * Math.PI;
            }
        }

        cx *= a;
        cy *= b;
        double tcx = cx;
        cx = cx * cosTheta - cy * sinTheta;
        cy = tcx * sinTheta + cy * cosTheta;

        arcToBezier(cx, cy, a, b, x0, y0, thetaD, eta0, sweep);
    }

    /**
     * Converts an arc to cubic Bezier segments and records them in p.
     *
     * @param cx    The x coordinate center of the ellipse
     * @param cy    The y coordinate center of the ellipse
     * @param a     The radius of the ellipse in the horizontal direction
     * @param b     The radius of the ellipse in the vertical direction
     * @param e1x   E(eta1) x coordinate of the starting point of the arc
     * @param e1y   E(eta2) y coordinate of the starting point of the arc
     * @param theta The angle that the ellipse bounding rectangle makes with horizontal plane
     * @param start The start angle of the arc on the ellipse
     * @param sweep The angle (positive or negative) of the sweep of the arc on the ellipse
     */
    private void arcToBezier(double cx,
                             double cy,
                             double a,
                             double b,
                             double e1x,
                             double e1y,
                             double theta,
                             double start,
                             double sweep) {
        // Taken from equations at: http://spaceroots.org/documents/ellipse/node8.html
        // and http://www.spaceroots.org/documents/ellipse/node22.html

        // Maximum of 45 degrees per cubic Bezier segment
        int numSegments = (int) Math.ceil(Math.abs(sweep * 4 / Math.PI));

        double eta1 = start;
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);
        double cosEta1 = Math.cos(eta1);
        double sinEta1 = Math.sin(eta1);
        double ep1x = (-a * cosTheta * sinEta1) - (b * sinTheta * cosEta1);
        double ep1y = (-a * sinTheta * sinEta1) + (b * cosTheta * cosEta1);

        double anglePerSegment = sweep / numSegments;
        for (int i = 0; i < numSegments; i++) {
            double eta2 = eta1 + anglePerSegment;
            double sinEta2 = Math.sin(eta2);
            double cosEta2 = Math.cos(eta2);
            double e2x = cx + (a * cosTheta * cosEta2) - (b * sinTheta * sinEta2);
            double e2y = cy + (a * sinTheta * cosEta2) + (b * cosTheta * sinEta2);
            double ep2x = -a * cosTheta * sinEta2 - b * sinTheta * cosEta2;
            double ep2y = -a * sinTheta * sinEta2 + b * cosTheta * cosEta2;
            double tanDiff2 = Math.tan((eta2 - eta1) / 2);
            double alpha =
                    Math.sin(eta2 - eta1) * (Math.sqrt(4 + (3 * tanDiff2 * tanDiff2)) - 1) / 3;
            double q1x = e1x + alpha * ep1x;
            double q1y = e1y + alpha * ep1y;
            double q2x = e2x - alpha * ep2x;
            double q2y = e2y - alpha * ep2y;
            notifyResult("mPath.cubicTo(" + (float) q1x + "f, " + (float) q1y + "f, " + (float) q2x +
                    "f, " + (float) q2y + "f, " + (float) e2x + "f, " + (float) e2y +"f);");
            eta1 = eta2;
            e1x = e2x;
            e1y = e2y;
            ep1x = ep2x;
            ep1y = ep2y;
        }
    }

    private void drawClipPath() {
        notifyResult("mRenderPath.addPath(mPath, mFinalPathMatrix);");
        notifyResult("canvas.clipPath(mRenderPath, Region.Op.REPLACE);");
    }

    private void drawPath(Path path, float matrixScale) {
        if (path.trimPathStart != 0.0f || path.trimPathEnd != 1.0f) {
            float start = (path.trimPathStart + path.trimPathOffset) % 1.0f;
            float end = (path.trimPathEnd + path.trimPathOffset) % 1.0f;
            notifyResult("if (mPathMeasure == null) {");
            notifyResult(HEAD_SPACE + "mPathMeasure = new PathMeasure();");
            notifyResult("}");
            notifyResult("mPathMeasure.setPath(mPath, false);");
            notifyResult("float len" + mCount + " = mPathMeasure.getLength();");
            notifyResult("float start" + mCount + " = " + start + " * len" + mCount);
            notifyResult("float end" + mCount + " = " + end + " * len" + mCount);
            notifyResult("mPath.reset();");
            notifyResult("if (start" + mCount + " > end" + mCount +") {");
            notifyResult(HEAD_SPACE + "mPathMeasure.getSegment(start" + mCount + ", len" + mCount + ", path, true);");
            notifyResult(HEAD_SPACE + "mPathMeasure.getSegment(0f, end" + mCount + ", path, true);");
            notifyResult("} else {");
            notifyResult(HEAD_SPACE + "mPathMeasure.getSegment(start" + mCount + ", end" + mCount + ", path, true);");
            notifyResult("}");
            notifyResult("mPath.rLineTo(0, 0);");
        }
        notifyResult("mRenderPath.addPath(mPath, mFinalPathMatrix);");
        String fillType = null;
        if ("evenOdd".equals(path.fillType)) {
            fillType = "android.graphics.Path.FillType.EVEN_ODD";
        } else if("nonZero".equals(path.fillType)) {
            fillType = "android.graphics.Path.FillType.WINDING";
        }
        if (fillType != null) {
            notifyResult("mRenderPath.setFillType(" + fillType +");");
        }
        if (path.fillColor != 0) {
            if (!isFillPaintInited) {
                isFillPaintInited = true;
                notifyResult("if (mFillPaint == null) {");
                notifyResult(HEAD_SPACE + "mFillPaint = new Paint();");
                notifyResult(HEAD_SPACE + "mFillPaint.setStyle(Paint.Style.FILL);");
                notifyResult(HEAD_SPACE + "mFillPaint.setAntiAlias(true);");
                notifyResult("}");
            }
            notifyResult("mFillPaint.setColor(applyAlpha(" + path.fillColor +", " + path.fillAlpha + "f));");
            notifyResult("mFillPaint.setColorFilter(filter);");
            notifyResult("canvas.drawPath(mRenderPath, mFillPaint);");
        }
        if (path.strokeColor != 0) {
            if (!isStrokePaintInited) {
                isStrokePaintInited = true;
                notifyResult("if (mStrokePaint == null) {");
                notifyResult(HEAD_SPACE + "mStrokePaint = new Paint();");
                notifyResult(HEAD_SPACE + "mStrokePaint.setStyle(Paint.Style.STROKE);");
                notifyResult(HEAD_SPACE + "mStrokePaint.setAntiAlias(true);");
                notifyResult("}");
            }
            if (path.strokeLineJoin != null) {
                String strokeLineJoin = null;
                if ("bevel".equals(path.strokeLineJoin)) {
                    strokeLineJoin = "Paint.Join.BEVEL";
                } else if ("miter".equals(path.strokeLineJoin)) {
                    strokeLineJoin = "Paint.Join.MITER";
                } else if ("round".equals(path.strokeLineJoin)) {
                    strokeLineJoin = "Paint.Join.ROUND";
                }
                notifyResult("mStrokePaint.setStrokeJoin(" + strokeLineJoin + ");");
            }
            if (path.strokeLineCap != null) {
                String strokeLineCap = null;
                if ("butt".equals(path.strokeLineCap)) {
                    strokeLineCap = "Paint.Cap.BUTT";
                } else if ("round".equals(path.strokeLineCap)) {
                    strokeLineCap = "Paint.Cap.ROUND";
                } else if ("square".equals(path.strokeLineCap)) {
                    strokeLineCap = "Paint.Cap.SQUARE";
                }
                notifyResult("mStrokePaint.setStrokeCap(" + strokeLineCap + ");");
            }
            notifyResult("mStrokePaint.setStrokeMiter(" + path.strokeMiterLimit + "f);");
            notifyResult("mStrokePaint.setColor(applyAlpha(" + path.strokeColor +", " + path.strokeAlpha + "f));");
            notifyResult("mStrokePaint.setColorFilter(filter);");
            notifyResult("mStrokePaint.setStrokeWidth(minScale * " + matrixScale + "f * " + path.strokeWidth + "f);");
            notifyResult("canvas.drawPath(mRenderPath, mStrokePaint);");
        }
    }
}
