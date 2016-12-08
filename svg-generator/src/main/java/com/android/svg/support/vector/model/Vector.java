package com.android.svg.support.vector.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The vector element (root element) model in the vector xml.
 *
 * @author Megatron King
 * @since 2016/8/31 20:42
 */

public class Vector {

    public String name;

    public boolean autoMirrored;

    public float alpha;

    public String tintMode;

    public int tint;

    public String width;
    public String height;

    public float viewportWidth;
    public float viewportHeight;

    public List<Object> children = new ArrayList<>();

}
