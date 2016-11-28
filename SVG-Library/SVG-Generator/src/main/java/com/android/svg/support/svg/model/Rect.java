package com.android.svg.support.svg.model;

import com.android.svg.support.svg.utils.PathBuilder;

/**
 * The rect element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/22 19:01
 */

public class Rect extends SvgNode {

    public float x;
    public float y;
    public float width;
    public float height;

    @Override
    public void toPath() {
        if (Float.isNaN(x) || Float.isNaN(y)) {
            return;
        }
        // "M x, y h width v height h -width z"
        PathBuilder builder = new PathBuilder();
        builder.absoluteMoveTo(x, y);
        builder.relativeHorizontalTo(width);
        builder.relativeVerticalTo(height);
        builder.relativeHorizontalTo(-width);
        builder.relativeClose();
        pathData = builder.toString();
    }
}
