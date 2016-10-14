# SVG-Android
support svg images for android 2.3+
___

### 博客：http://blog.csdn.net/megatronkings

___

##SVG-Generator

负责解析Vector文件，计算Path路径并自动生成SVGRenderer Java代码，同时会生成空内容的shape
资源文件（用于创建资源ID），另外会创建SVGLoader，用于预先加载所有SVGRenderer资源。<br>

另外，由于Android只支持部分规范的SVG文件，所以我们还是按照官方的思路，必须先生成合法的Vector文件，
这样还有个好处就是可以引用dimen和color，方面以后统一修改尺寸和颜色。<br>
SVG图片转换成Vector文件有两种方式：<br>
1、使用svg2android网站转换 http://inloop.github.io/svg2android/ <br>
2、使用Android Studio 右键 -> New -> Vector Asset -> Local SVG File<br>

___


##SVG-Plugin

Gradle插件，支持SVG-Generator调用，配置参数如下：
```gradle
svg {
    vectorDirs = ["src/main/svg_debug/drawable"]
    shapeDir = "src/main/svg_release/drawable"
    javaDir = "src/main/java/com/github/megatron/svg/sample/drawables"
    packageName = "com.github.megatron.svg.sample"
    appColors = ['black':0xFF000000, 'white':0xFFFFFFFF]
}
```
config：
- vectorDirs  vectors资源目录，支持多个目录，debug包可作为资源打进apk，但release包不可
- shapeDir    shape资源目录，需要打到apk包内
- javaDir     生成SVGRenderer Java代码的目录
- packageName 应用包名，用于R文件的引用
- appColors   定义色值，如果vector文件中有@color/xxx的引用，需要配置

task：
- svgAssemble     任务执行，主Task
- svgCleanShape   清除生成的空shape资源文件
- svgCleanJava    清除生成的SVGRenderer Java代码
- svgLoadAppColor 加载色值定义，对应配置参数appColors


##SVG-Support
SVG渲染支持库和组件库，提供对SVG功能的支持，包括tint、alpha、selector、size等。<br>

组件支持：
- SVGView 替代View
- SVGImageView 替代ImageView
- SVGTextView 替代TextView
- SVGButton 替代Button
- SVGImageButton 替代ImageButton
- SVGEditText 替代EditText


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



