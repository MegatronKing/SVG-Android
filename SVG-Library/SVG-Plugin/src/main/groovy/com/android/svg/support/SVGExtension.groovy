package com.android.svg.support;

import java.util.Map;

/**
 * <p>svg {
 *      vectorDirs = "${projectDir}\vector-resources1"
 *      shapeDir = "src\main\res\drawables"
 *      javaDir = "src\main\java\com\android\svg\sample\drawables"
 *      packageName="com.android.svg.sample"
 * }</p>
 *
 * @author Megatron King
 * @since 2016-10-12 上午11:07:34
 */
public class SVGExtension {

    public def vectorDirs;
    public def shapeDir;
    public def javaDir;

    public def packageName;
    public def appColors;

    public def uncleanMode;
}