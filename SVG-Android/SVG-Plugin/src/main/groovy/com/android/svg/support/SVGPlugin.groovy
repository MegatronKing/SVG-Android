package com.android.svg.support

import com.android.svg.support.task.SVGAppColorLoadTask
import com.android.svg.support.task.SVGAssembleTask
import com.android.svg.support.task.SVGJavaCleanTask
import com.android.svg.support.task.SVGShapeCleanTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * <p>apply plugin: "svg"</p>
 *
 * @author Megatron King
 * @since 2016/10/12 17:11
 */

public class SVGPlugin implements Plugin<Project> {

    private static final String SVG_TASK_GROUP = "svg";

    @Override
    public void apply(Project project) {
        project.extensions.create("svg", SVGExtension);
        def assemble = project.tasks.create("svgAssemble", SVGAssembleTask);
        assemble.setGroup(SVG_TASK_GROUP)
        def cleanShape = project.tasks.create("svgCleanShape", SVGShapeCleanTask);
        cleanShape.setGroup(SVG_TASK_GROUP)
        def cleanJava = project.tasks.create("svgCleanJava", SVGJavaCleanTask);
        cleanJava.setGroup(SVG_TASK_GROUP)
        def loadAppColor = project.tasks.create("svgLoadAppColor", SVGAppColorLoadTask);
        loadAppColor.setGroup(SVG_TASK_GROUP)

        assemble.dependsOn cleanShape
        assemble.dependsOn cleanJava
        assemble.dependsOn loadAppColor
    }
}