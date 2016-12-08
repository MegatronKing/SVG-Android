package com.github.megatronking.svg.generator.svg.model;

import com.github.megatronking.svg.generator.svg.utils.PathBuilder;

/**
 * The circle element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/22 19:28
 */

public class Circle extends SvgNode {

    public float cx;
    public float cy;
    public float r;

    @Override
    public void toPath() {
        if (Float.isNaN(cx) || Float.isNaN(cx) || r <= 0) {
            return;
        }
        // "M cx cy m -r, 0 a r,r 0 1,1 (r * 2),0 a r,r 0 1,1 -(r * 2),0"
        PathBuilder builder = new PathBuilder();
        builder.absoluteMoveTo(cx, cy);
        builder.relativeMoveTo(-r, 0);
        builder.relativeArcTo(r, r, false, true, true, 2 * r, 0);
        builder.relativeArcTo(r, r, false, true, true, -2 * r, 0);
        pathData =  builder.toString();
    }

}
