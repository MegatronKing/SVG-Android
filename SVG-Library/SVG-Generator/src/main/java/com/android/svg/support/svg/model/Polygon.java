package com.android.svg.support.svg.model;

/**
 * The polygon element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/23 9:47
 */

public class Polygon extends SvgNode {

    public String points;

    @Override
    public void toPath() {
        if (points == null || points.length() == 0) {
            return;
        }
        com.android.svg.support.svg.utils.PathBuilder builder = new com.android.svg.support.svg.utils.PathBuilder();
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
        builder.relativeClose();
        pathData = builder.toString();
    }

}
