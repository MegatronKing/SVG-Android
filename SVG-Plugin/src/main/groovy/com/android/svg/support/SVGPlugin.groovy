package com.android.svg.support;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * <p>apply plugin: 'svg'</p>
 *
 * @author Megatron King
 * @since 2016/10/12 17:11
 */

public class SVGPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.extensions.create("svg", SVGExtension);
    }
}