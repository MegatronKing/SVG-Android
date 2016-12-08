package com.github.megatronking.svg.generator.vector.model;

/**
 * The path element model in the vector xml.
 *
 * @author Megatron King
 * @since 2016/8/31 20:42
 */
public class Path {

    public Path(Group parentGroup) {
        this.parentGroup = parentGroup;
    }

    public String name;
    public String pathData;

    public int fillColor;
    public float fillAlpha;
    public String strokeLineCap;
    public String strokeLineJoin;
    public float strokeMiterLimit;
    public int strokeColor;
    public float strokeAlpha;
    public float strokeWidth;
    public float trimPathEnd;
    public float trimPathOffset;
    public float trimPathStart;

    public Group parentGroup;
}
