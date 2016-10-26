# SVG-Android
support svg images for android 2.3+

### 博客：http://blog.csdn.net/megatronkings


##SVG-Generator

负责解析SVG或者Vector文件，计算Path路径并自动生成SVGRenderer Java代码，同时会生成空内容的shape
资源文件（用于创建资源ID），另外会创建SVGLoader，用于预先加载所有SVGRenderer资源。<br>

为了兼容Android官方的Vector文件以及利用其相关特性，SVG-Generator支持将SVG格式文件自动生成Vector文件。


##SVG-Plugin

Gradle插件，方便开发者一键化处理SVG图片到SVGRenderer代码的自动生成。

支持功能如下：

- 直接处理SVG格式文件，并生成vector资源文件，并支持多个目录及多种配置
- 将vector资源文件生成SVG-Android框架支持的SVGRenderer等相关文件，支持多个目录


配置参数如下：
```gradle
svg {
    vectorDirs = ["src/main/svg_debug/drawable"]
    shapeDir = "src/main/svg_release/drawable"
    javaDir = "src/main/java/com/github/megatron/svg/sample/drawables"
    packageName = "com.github.megatron.svg.sample"
    appColors = ['black':0xFF000000, 'white':0xFFFFFFFF]

    svg2vector {
        svg_a {
            svgDir = "${rootDir}/svg_a"
            vectorDir = "src/main/svg_debug/drawable"
            width = 48
            height = 48
        }
        svg_b {
            ...
        }
     }
}
```
config：
- [vectorDirs]  vectors资源目录，支持多个目录，debug包可作为资源打进apk，但release包不可
- [shapeDir]    shape资源目录，需要打到apk包内
- [javaDir]     生成SVGRenderer Java代码的目录
- [packageName] 应用包名，用于R文件的引用
- [appColors]   定义色值，如果vector文件中有@color/xxx的引用，需要配置
- [svg2vector]  svg格式文件生成vector文件的相关配置

svg2vector：
- [svg_a]       命名无特殊意义，可随意，建议使用svg图片文件目录名称，可以配置多个。
- [svgDir]      svg图片文件目录路径
- [vectorDir]   svg生成vector的文件目录，必须包含在vectorDirs数组中
- [width]       svg生成vector的文件的图片宽度，可以不配置，默认为24，对应其android:width属性，单位为dp。
- [height]      svg生成vector的文件的图片高度，可以不配置，默认为24，对应其android:height属性，单位为dp。


主要task：
- svgAssemble     任务执行，主Task
- svgCleanShape   清除生成的空shape资源文件
- svgCleanJava    清除生成的SVGRenderer Java代码
- svgLoadAppColor 加载色值定义，对应配置参数appColors


##SVG-Support
SVG渲染支持库和组件库，提供对SVG功能的支持，包括tint、alpha、selector、size等。<br>
虽然SVG-Android支持直接使用View组件显示图片，但是一些SVG特性需要扩展组件才能支持。<br>
组件支持：
- SVGView
- SVGImageView
- SVGTextView
- SVGButton
- SVGImageButton
- SVGEditText


##Enjoy SVG-Android！

--------

    Copyright (C) 2016, Megatron King

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



