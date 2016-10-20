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

/**
 * Build a string for Svg file's path data.
 */
class PathBuilder {
    private StringBuilder mPathData = new StringBuilder();

    private String booleanToString(boolean flag) {
        return flag ? "1" : "0";
    }

    public PathBuilder absoluteMoveTo(float x, float y) {
        mPathData.append("M" + x + "," + y);
        return this;
    }

    public PathBuilder relativeMoveTo(float x, float y) {
        mPathData.append("m" + x + "," + y);
        return this;
    }

    public PathBuilder absoluteLineTo(float x, float y) {
        mPathData.append("L" + x + "," + y);
        return this;
    }

    public PathBuilder relativeLineTo(float x, float y) {
        mPathData.append("l" + x + "," + y);
        return this;
    }

    public PathBuilder absoluteVerticalTo(float v) {
        mPathData.append("V" + v);
        return this;
    }

    public PathBuilder relativeVerticalTo(float v) {
        mPathData.append("v" + v);
        return this;
    }

    public PathBuilder absoluteHorizontalTo(float h) {
        mPathData.append("H" + h);
        return this;
    }

    public PathBuilder relativeHorizontalTo(float h) {
        mPathData.append("h" + h);
        return this;
    }

    public PathBuilder absoluteArcTo(float rx, float ry, boolean rotation,
                                     boolean largeArc, boolean sweep, float x, float y) {
        mPathData.append("A" + rx + "," + ry + "," + booleanToString(rotation) + "," +
                booleanToString(largeArc) + "," + booleanToString(sweep) + "," + x + "," + y);
        return this;
    }

    public PathBuilder relativeArcTo(float rx, float ry, boolean rotation,
                                     boolean largeArc, boolean sweep, float x, float y) {
        mPathData.append("a" + rx + "," + ry + "," + booleanToString(rotation) + "," +
                booleanToString(largeArc) + "," + booleanToString(sweep) + "," + x + "," + y);
        return this;
    }

    public PathBuilder absoluteClose() {
        mPathData.append("Z");
        return this;
    }

    public PathBuilder relativeClose() {
        mPathData.append("z");
        return this;
    }

    public String toString() {
        return mPathData.toString();
    }
}