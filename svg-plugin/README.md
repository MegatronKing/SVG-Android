### 博客：http://blog.csdn.net/megatronkings

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
- [width]       svg生成vector的文件的图片宽度，可以不配置，默认使用原始宽度，对应其android:width属性，单位为dp。
- [height]      svg生成vector的文件的图片高度，可以不配置，默认使用原始高度，对应其android:height属性，单位为dp。


主要task：
- svgAssemble     任务执行，主Task
- svgCleanShape   清除生成的空shape资源文件
- svgCleanJava    清除生成的SVGRenderer Java代码
- svgLoadAppColor 加载色值定义，对应配置参数appColors

----

##版本日志

### V1.1.0
- 新增svg生成vector功能

### V1.1.1
- 修复svg生成vector时，检测dtd文件导致耗时严重的bug

### V1.2.0
- 重构svg生成vector代码
- 支持解析解析关键词颜色定义，比如fill=“red”
- 支持解析svg椭圆ellipse标签
- 支持跳过或校验不合法的svg语法标签或者属性
- 去除不定义尺寸时宽高默认24的设定，自动使用图片原始尺寸

### V1.3.0
- 修改为正式包名

### V1.3.1 (未发布)
- 新增对svgz格式文件的处理
- 新增支持fill-rule、fill-opacity、stroke-opacity三个属性的解析
- 修复在特殊场景下执行svg2vector任务报错的bug


