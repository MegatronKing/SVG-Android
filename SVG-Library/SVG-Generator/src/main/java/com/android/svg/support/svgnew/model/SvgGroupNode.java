package com.android.svg.support.svgnew.model;

/**
 * Represent a SVG file's group element.
 *
 * @author Megatron King
 * @since 2016/11/23 16:36
 */

public abstract class SvgGroupNode extends SvgNode {

    /**
     *   a c e
     * ( b d f )
     *   0 0 1
     */
    public float[] matrix;

}
