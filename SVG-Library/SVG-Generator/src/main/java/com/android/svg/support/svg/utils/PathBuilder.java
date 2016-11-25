package com.android.svg.support.svg.utils;

/**
 * Build a string for Svg file's path data.
 *
 * @author Megatron King
 * @since 2016/11/23 14:32
 */

public class PathBuilder {

    private StringBuilder mPathData = new StringBuilder();

    private String booleanToString(boolean flag) {
        return flag ? "1" : "0";
    }

    public PathBuilder absoluteMoveTo(float x, float y) {
        mPathData.append("M").append(x).append(",").append(y);
        return this;
    }

    public PathBuilder relativeMoveTo(float x, float y) {
        mPathData.append("m").append(x).append(",").append(y);
        return this;
    }

    public PathBuilder absoluteLineTo(float x, float y) {
        mPathData.append("L").append(x).append(",").append(y);
        return this;
    }

    public PathBuilder relativeLineTo(float x, float y) {
        mPathData.append("l").append(x).append(",").append(y);
        return this;
    }

    public PathBuilder absoluteVerticalTo(float v) {
        mPathData.append("V").append(v);
        return this;
    }

    public PathBuilder relativeVerticalTo(float v) {
        mPathData.append("v").append(v);
        return this;
    }

    public PathBuilder absoluteHorizontalTo(float h) {
        mPathData.append("H").append(h);
        return this;
    }

    public PathBuilder relativeHorizontalTo(float h) {
        mPathData.append("h").append(h);
        return this;
    }

    public PathBuilder absoluteArcTo(float rx, float ry, boolean rotation, boolean largeArc, boolean sweep, float x, float y) {
        mPathData.append("A").append(rx).append(",").append(ry).append(",").append(booleanToString(rotation)).append(",").append(
                booleanToString(largeArc)).append(",").append(booleanToString(sweep)).append(",").append(x).append(",").append(y);
        return this;
    }

    public PathBuilder relativeArcTo(float rx, float ry, boolean rotation, boolean largeArc, boolean sweep, float x, float y) {
        mPathData.append("a").append(rx).append(",").append(ry).append(",").append(booleanToString(rotation)).append(",").append(
                booleanToString(largeArc)).append(",").append(booleanToString(sweep)).append(",").append(x).append(",").append(y);
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
