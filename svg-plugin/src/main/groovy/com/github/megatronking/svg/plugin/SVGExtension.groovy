package com.github.megatronking.svg.plugin
/**
 * <p>svg {
 *      vectorDirs = "${projectDir}\vector-resources1"
 *      shapeDir = "src\main\res\drawables"
 *      javaDir = "src\main\java\com\github\megatronking\svg\sample\drawables"
 *      packageName="com.github.megatronking.svg.sample"
 * }</p>
 *
 * @author Megatron King
 * @since 2016-10-12 上午11:07:34
 */
public class SVGExtension {

    public def vectorDirs = [];
    public def shapeDir;
    public def javaDir;

    public def packageName;
    public def appColors;

    public def cleanMode;

    public def debugMode;

    public def autoSourceSet = true;

    public def generateLoader = true;
}