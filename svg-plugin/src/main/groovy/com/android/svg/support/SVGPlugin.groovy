package com.android.svg.support

import com.android.svg.support.task.*
import com.android.svg.support.utils.Holder
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
/**
 * <p>apply plugin: "svg"</p>
 *
 * @author Megatron King
 * @since 2016/10/12 17:11
 */

public class SVGPlugin implements Plugin<Project> {

    private static final String SVG_TASK_GROUP = "svg"

    @Override
    public void apply(Project project) {
        def svgExtension = project.extensions.create("svg", SVGExtension)

        def assemble = project.tasks.create("svgAssemble", SVGAssembleTask)
        assemble.setGroup(SVG_TASK_GROUP)
        def cleanShape = project.tasks.create("svgCleanShape", SVGShapeCleanTask)
        cleanShape.setGroup(SVG_TASK_GROUP)
        def cleanJava = project.tasks.create("svgCleanJava", SVGJavaCleanTask)
        cleanJava.setGroup(SVG_TASK_GROUP)
        def cleanVector = project.tasks.create("svgCleanVector", SVGVectorCleanTask)
        cleanVector.setGroup(SVG_TASK_GROUP)
        def loadAppColor = project.tasks.create("svgLoadAppColor", SVGAppColorLoadTask)
        loadAppColor.setGroup(SVG_TASK_GROUP)

        Task cleanTask = project.tasks.create("svgClean")
        cleanTask.setGroup(SVG_TASK_GROUP)

        def svg2vectorExtensions = project.container(SVG2VectorExtension)
        project.extensions.svg2vector = svg2vectorExtensions

        project.afterEvaluate {
            Holder.SVG_HOLDER.clear()
            if(!svg2vectorExtensions.isEmpty()) {
                def svg2vectorTask = project.tasks.create("svg2vector")
                svg2vectorTask.setGroup(SVG_TASK_GROUP)
                svg2vectorExtensions.each { svg2vectorExtension->
                    def svg2vectorChildTask = project.tasks.create("svg2vector" + firstLettertoUpperCase(svg2vectorExtension.name), SVG2VectorTask)
                    svg2vectorChildTask.setGroup(SVG_TASK_GROUP)
                    svg2vectorChildTask.setExtensionName(svg2vectorExtension.name)
                    svg2vectorTask.dependsOn svg2vectorChildTask
                }
                assemble.dependsOn svg2vectorTask
            }

            cleanTask.dependsOn cleanShape
            cleanTask.dependsOn cleanJava
            cleanTask.dependsOn cleanVector

            if (svgExtension.cleanMode) {
                assemble.dependsOn cleanTask
            }
            assemble.dependsOn loadAppColor
        }
    }

    private def firstLettertoUpperCase(def text) {
        text.getAt(0).toString().toUpperCase() + text.substring(1)
    }
}