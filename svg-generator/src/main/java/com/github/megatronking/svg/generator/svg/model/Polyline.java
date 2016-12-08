package com.github.megatronking.svg.generator.svg.model;

import com.github.megatronking.svg.generator.svg.utils.PathBuilder;

/**
 * The polyline element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/25 15:11
 */

public class Polyline extends SvgNode {

    public String points;

    @Override
    public void toPath() {
        if (points == null || points.length() == 0) {
            return;
        }
        PathBuilder builder = new PathBuilder();
        String[] split = points.split("[\\s,]+");
        float baseX = Float.parseFloat(split[0]);
        float baseY = Float.parseFloat(split[1]);
        builder.absoluteMoveTo(baseX, baseY);
        for (int j = 2; j < split.length; j += 2) {
            float x = Float.parseFloat(split[j]);
            float y = Float.parseFloat(split[j + 1]);
            builder.relativeLineTo(x - baseX, y - baseY);
            baseX = x;
            baseY = y;
        }
        pathData = builder.toString();
    }

}