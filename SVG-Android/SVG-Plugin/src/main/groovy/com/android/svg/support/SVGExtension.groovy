package com.android.svg.support

/**
 * <p>svg {
 *      vectorDirs = "${projectDir}\vector-resources1"
 *      shapeDir = "${projectDir}\src\main\res\drawables"
 *      javaDir = "${projectDir}\src\main\java\com\android\svg\sample\drawables"
 *      packageName="com.android.svg.sample"
 * }</p>
 *
 * @author Megatron King
 * @since 2016-10-12 上午11:07:34
 */
public class SVGExtension {

    def vectorDirs;
    def shapeDir;
    def javaDir;

    def packageName;
    def appColors;

    @Override
    String toString() {
        return "[vectorDirs] $vectorDirs \n [shapeDir] $shapeDir \n [javaDir] $javaDir"
    }
}