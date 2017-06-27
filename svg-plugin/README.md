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
- [packageName] 应用包名，用于R文件的引用，如果不配置默认使用applicationId
- [appColors]   定义色值，如果vector文件中有@color/xxx的引用，需要配置
- [svg2vector]  svg格式文件生成vector文件的相关配置
- [autoSourceSet]  是否自动添加vector和shape资源文件目录到SourceSet，默认true
- [generateLoader] 是否自动生成SVGLoader文件，默认true

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
- svg2vector      执行svg转换vector

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

### V1.3.2
- 新增对svgz格式文件的处理
- 新增支持fill-rule、fill-opacity、stroke-opacity三个属性的解析
- 修复在特殊场景下执行svg2vector任务报错的bug

### V1.3.3
- 新增可以不需要配置packageName，默认使用applicationId (此功能有bug，必须配置packageName)
- 自动添加vector和shape目录到sourceSet，如果想禁用此功能可以使用配置autoSourceSet=false

### V1.3.4
- 紧急修复1.3.3版本自动配置packageName的bug

### V1.3.5
- 修复嵌套或者复杂矩阵变换导致图片不显示的bug
- 修复存在width、height属性的svg文件生成vector资源文件图片尺寸失真的bug
- 支持预定义标签defs中使用style标签
- 支持style标签部分css样式属性

### V1.3.6
- 新增对use、symbol标签的支持
- 新增解析部分style标签css样式属性
- 修复使用rgb(r,g,b)方式定义颜色时解析出错的bug
- 修复一系列style标签相关bug
- 修复矩阵变换相关bug
- 优化部分代码

### V1.3.7
- 修复较大矢量图生成的render类方法超出编译器限制的bug

### V1.3.8
- 修复使用appColors属性编译错误的bug
