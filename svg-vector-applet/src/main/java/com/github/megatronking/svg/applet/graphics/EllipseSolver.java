package com.github.megatronking.svg.applet.graphics;

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

import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.util.logging.Level;
import java.util.logging.Logger;

class EllipseSolver {
    private static Logger logger = Logger.getLogger(EllipseSolver.class.getSimpleName());

    // Final results:
    private float mMajorAxis = 0;

    private float mMinorAxis = 0;

    private float mRotationDegree = 0;

    private boolean mDirectionChanged;

    // Intermediate results:
    private Point2D.Float mMajorAxisPoint;

    private Point2D.Float mMiddlePoint;

    private Point2D.Float mMinorAxisPoint;

    /**
     * Rotate the Point2D by radians
     *
     * @return the rotated Point2D
     */
    private Point2D.Float rotatePoint2D(Point2D.Float inPoint, float radians) {
        Point2D.Float result = new Point2D.Float();
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
    EllipseSolver(AffineTransform totalTransform,
                         float currentX, float currentY, float rx, float ry, float xAxisRotation,
                         float largeArcFlag, float sweepFlag, float destX, float destY) {
        boolean largeArc = largeArcFlag != 0;
        boolean sweep = sweepFlag != 0;

        // Compute the cx and cy first.
        Point2D.Float originalCenter = computeOriginalCenter(currentX, currentY, rx, ry,
                xAxisRotation, largeArc, sweep, destX, destY);

        // Compute 3 points from original ellipse
        computeControlPoints(rx, ry, xAxisRotation, originalCenter.x, originalCenter.y);

        // Transform 3 points and center point into destination.
        Point2D.Float dstMiddlePoint = (Point2D.Float) totalTransform.transform(mMiddlePoint, null);
        Point2D.Float dstMajorAxisPoint = (Point2D.Float) totalTransform.transform(mMajorAxisPoint, null);
        Point2D.Float dstMinorAxisPoint = (Point2D.Float) totalTransform.transform(mMinorAxisPoint, null);
        Point2D dstCenter = totalTransform.transform(originalCenter, null);
        float dstCenterX = (float) dstCenter.getX();
        float dstCenterY = (float) dstCenter.getY();

        // Compute the relative 3 points:
        float relativeDstMiddleX = dstMiddlePoint.x - dstCenterX;
        float relativeDstMiddleY = dstMiddlePoint.y - dstCenterY;
        float relativeDstMajorAxisPointX = dstMajorAxisPoint.x - dstCenterX;
        float relativeDstMajorAxisPointY = dstMajorAxisPoint.y - dstCenterY;
        float relativeDstMinorAxisPointX = dstMinorAxisPoint.x - dstCenterX;
        float relativeDstMinorAxisPointY = dstMinorAxisPoint.y - dstCenterY;

        // Check if the direction has change!
        mDirectionChanged = computeDirectionChange(mMiddlePoint, mMajorAxisPoint,
                mMinorAxisPoint,
                dstMiddlePoint, dstMajorAxisPoint, dstMinorAxisPoint);

        // From 3 dest points, recompute the a, b and theta.
        if (computeABThetaFromControlPoints(relativeDstMiddleX, relativeDstMiddleY,
                relativeDstMajorAxisPointX,
                relativeDstMajorAxisPointY, relativeDstMinorAxisPointX,
                relativeDstMinorAxisPointY)) {
            logger.log(Level.WARNING,
                    "Early return in the ellipse transformation computation!");
        }
    }

    /**
     * After a random transformation, the controls points may change its direction, left handed <->
     * right handed. In this case, we better flip the flag for the ArcTo command.
     *
     * Here, we use the cross product to figure out the direction of the 3 control points for the
     * src and dst ellipse.
     */
    private boolean computeDirectionChange(final Point2D.Float middlePoint,
                                           final Point2D.Float majorAxisPoint, final Point2D.Float minorAxisPoint,
                                           final Point2D.Float dstMiddlePoint, final Point2D.Float dstMajorAxisPoint,
                                           final Point2D.Float dstMinorAxisPoint) {
        // Compute both Cross Product, then compare the sign.
        float srcCrossProduct = getCrossProduct(middlePoint, majorAxisPoint, minorAxisPoint);
        float dstCrossProduct = getCrossProduct(dstMiddlePoint, dstMajorAxisPoint,
                dstMinorAxisPoint);

        return srcCrossProduct * dstCrossProduct < 0;
    }

    private float getCrossProduct(final Point2D.Float middlePoint,
                                  final Point2D.Float majorAxisPoint, final Point2D.Float minorAxisPoint) {
        float majorMinusMiddleX = majorAxisPoint.x - middlePoint.x;
        float majorMinusMiddleY = majorAxisPoint.y - middlePoint.y;

        float minorMinusMiddleX = minorAxisPoint.x - middlePoint.x;
        float minorMinusMiddleY = minorAxisPoint.y - middlePoint.y;

        return (majorMinusMiddleX * minorMinusMiddleY) - (majorMinusMiddleY
                * minorMinusMiddleX);
    }

    /**
     * @return true if there is something error going on, either due to the ellipse is not setup
     *         correctly, or some computation error happens. This error is ignorable, but the
     *         output ellipse will not be correct.
     */
    private boolean computeABThetaFromControlPoints(float relMiddleX, float relMiddleY,
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
            return true;
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
            return false;
        }
        float doubleThetaInRadians = (float) Math.atan(B / (A - C));
        float thetaInRadians = doubleThetaInRadians / 2;
        if (Math.sin(doubleThetaInRadians) == 0) {
            mMinorAxis = (float) Math.sqrt(1 / C);
            mMajorAxis = (float) Math.sqrt(1 / A);
            mRotationDegree = 0;
            // This is a valid answer, so return false;
            return false;
        }
        float bSqInv = (A + C + B / (float) Math.sin(doubleThetaInRadians)) / 2;
        float aSqInv = A + C - bSqInv;

        if (bSqInv == 0 || aSqInv == 0) {
            return true;
        }
        mMinorAxis = (float) Math.sqrt(1 / bSqInv);
        mMajorAxis = (float) Math.sqrt(1 / aSqInv);

        mRotationDegree = (float) Math.toDegrees(Math.PI / 2 + thetaInRadians);
        return false;
    }

    private void computeControlPoints(float a, float b, float rot, float cx, float cy) {
        mMajorAxisPoint = new Point2D.Float(a, 0);
        mMinorAxisPoint = new Point2D.Float(0, b);

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

        mMiddlePoint = new Point2D.Float(middleR * (float) Math.cos(middleRadians),
                middleR * (float) Math.sin(middleRadians));
        mMiddlePoint = rotatePoint2D(mMiddlePoint, rot);
        mMiddlePoint.x += cx;
        mMiddlePoint.y += cy;
    }

    private Point2D.Float computeOriginalCenter(float x1, float y1, float rx, float ry,
                                                float phi, boolean largeArc, boolean sweep, float x2, float y2) {
        Point2D.Float result = new Point2D.Float();
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

    float getMajorAxis() {
        return mMajorAxis;
    }

    float getMinorAxis() {
        return mMinorAxis;
    }

    float getRotationDegree() {
        return mRotationDegree;
    }

    boolean getDirectionChanged() {
        return mDirectionChanged;
    }
}