package com.github.megatronking.svg.generator.svg.model;

import com.github.megatronking.svg.generator.svg.utils.PathBuilder;

/**
 * The ellipse element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/24 20:04
 */

public class Ellipse extends SvgNode {

    public float cx;
    public float cy;
    public float rx;
    public float ry;

    @Override
    public void toPath() {
        if (Float.isNaN(cx) || Float.isNaN(cx) || rx <= 0 || ry <= 0) {
            return;
        }
        // "M cx cy m -rx, 0 a rx,ry 0 1,1 (rx * 2),0 a rx,ry 0 1,1 -(rx * 2),0"
        PathBuilder builder = new PathBuilder();
        builder.absoluteMoveTo(cx, cy);
        builder.relativeMoveTo(-rx, 0);
        builder.relativeArcTo(rx, ry, false, true, true, 2 * rx, 0);
        builder.relativeArcTo(rx, ry, false, true, true, -2 * rx, 0);
        pathData =  builder.toString();
    }

}