/*
 * Copyright (C) 2017, Megatron King
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.megatronking.svg.plugin

import com.github.megatronking.svg.plugin.task.*
import com.github.megatronking.svg.plugin.utils.Holder
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

            // get package name from android plugin
            def androidPlugin = project.android;
            if (androidPlugin  != null && svgExtension != null && svgExtension.packageName == null) {
                svgExtension.packageName = androidPlugin.defaultConfig.applicationId
            }

            // add vector and shape dirs to sourceSets
            if (androidPlugin != null && svgExtension != null && svgExtension.autoSourceSet != null) {
                def shapeDir = svgExtension.shapeDir
                def vectorDirs = svgExtension.vectorDirs  != null ? svgExtension.vectorDirs : []
                if (svg2vectorExtensions != null) {
                    svg2vectorExtensions.each { svg2vectorConfiguration->
                        if (svg2vectorConfiguration.vectorDir != null && !vectorDirs.contains(svg2vectorConfiguration.vectorDir)) {
                            vectorDirs.add(svg2vectorConfiguration.vectorDir)
                        }
                    }
                }
                androidPlugin.sourceSets.each { sourceSet->
                    if (sourceSet.name.equals('debug')) {
                        vectorDirs.each {
                            it = splitResDir(project, it)
                            def hasDir = false
                            for (dir in sourceSet.res.srcDirs) {
                                if (dir.absolutePath.equals(it)) {
                                    hasDir = true
                                    break
                                }
                            }
                            if (!hasDir) {
                                println "add debug res dir to sourceSet : ${it}"
                                sourceSet.res.srcDir(new File(it))
                            }
                        }
                    }
                    if (shapeDir != null && sourceSet.name.equals('release')) {
                        shapeDir = splitResDir(project, shapeDir)
                        def hasDir = false
                        for (dir in sourceSet.res.srcDirs) {
                            if (dir.absolutePath.equals(shapeDir)) {
                                hasDir = true
                                break
                            }
                        }
                        if (!hasDir) {
                            println "add release res dir to sourceSet : ${shapeDir}"
                            sourceSet.res.srcDir(new File(shapeDir))
                        }
                    }
                }
            }

            // resolve dependencies
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

    private def splitResDir(Project project, def dir) {
        dir = project.file(dir).absolutePath
        dir = dir.split("drawable")[0]
        dir.subSequence(0, dir.length() - 1)
    }
}