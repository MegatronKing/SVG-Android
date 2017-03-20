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