package com.android.svg.support;

public class SVG2VectorExtension  {

    public def name;

    public def svgDir;
    public def vectorDir;

    // default 24dip
    public def width = 24;
    public def height = 24;

    public SVG2VectorExtension(def name) {
        this.name = name;
    }

}
