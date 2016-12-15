# 简介

[![Jcenter Status](https://img.shields.io/badge/download-1.3.0-brightgreen.svg)](http://jcenter.bintray.com/com/github/megatronking)
[![license](http://img.shields.io/badge/license-apache_2.0-red.svg?style=flat)](https://github.com/MegatronKing/SVG-Android/raw/master/LICENSE)

*switch language: [English](README.md)*

SVG-Android是一款支持在Android 4.0+系统上显示SVG图片的开源库。<br>

SVG-Android库可以将svg格式图片自动转化成vector文件(VectorDrawable对应的资源文件)，并且支持将vector文件一键
转化成java图片类文件，这些java图片类可以直接用来绘制在View的画布上。java图片类的绘制渲染速度比使用vector
文件更加快捷和高效，同时可以避免兼容性问题，毕竟5.0以上系统才支持VectorDrawable。同时，SVG-Android支持库还有
很多强大的功能，将svg矢量图的特性发挥到极致，比如给图片着色、缩放大小、改变透明度等等。另外，既支持在java代码中
使用也支持在xml中引用，对开发者来说非常地方便和人性化。

# 演示

![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample1.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample2.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample3.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample4.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample5.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample6.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/animation1.gif)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/animation2.gif)

# 范例

https://github.com/MegatronKing/SVG-Android/raw/master/SVG-Sample.apk

# 特性

- 整体加载速度比png、iconfont和vector要快，主要是编译前预解码可以节省大量时间。
- 相比于png格式图片，具有更好的性能和图片表现力。
- 支持tint, alpha, size, selector等基本图片功能。
- 可以降低apk包体积，相同内容但不同尺寸不同颜色的图片只需要一张即可，大幅提高图片利用率。
- 维护方便，修改图片尺寸、颜色、透明等时，不需要找设计师重新切图，只需要修改大小或者替换色值就可以。
- 支持各种动画效果，包括帧动画和属性动画，甚至支持@animator/xxx方式引用动画。


  \\              |  速度  |   内存  | 兼容性 | scaleType支持 | 清晰度 | 多色彩 | 降低包大小
  ----------------|--------|--------|--------|--------------|-------|--------|-----------
   SVG-Android    | √      | √      | √      | √            | √     | √      | √
   Vector         | ×      | √      | x      | √            | √     | √      | ×
   Png            | √      | ×      | √      | √            | ×     | √      | ×
   Iconfont       | ×      | √      | √      | ×            | √     | ×      | √

# 原理

![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/core.png)

# 使用

## 步骤1：在build.gradle中引入插件并配置参数
```gradle
apply plugin: 'svg'

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.github.megatronking:svg-plugin:1.3.0'
    }
}

svg {
    // 下面四个参数是vector文件生成java渲染类文件的相关配置
    // vector资源文件目录，支持多个目录
    vectorDirs = ["src/main/svg_debug/drawable"]
    // shape资源文件目录
    shapeDir = "src/main/svg_release/drawable"
    // 生成java渲染类的存放包目录
    javaDir = "src/main/java/com/github/megatronking/svg/sample/drawables"
    // 应用包名(必须与R文件包名对应)
    packageName = "com.github.megatronking.svg.sample"

    // 下面参数是svg格式文件生成vector资源文件的配置
    svg2vector {
        // 这里的test可以随意命名，建议使用文件夹名
        test {
            // svg格式文件目录
            svgDir = "${rootDir}/test"
            // 生成vector资源文件的存放目录
            vectorDir = "src/main/svg_debug/drawable"
            // 宽高参数指定生成vector文件的宽高，如果省略将默认使用图片原始宽高
            // 注意此处宽高比要与图片宽高比一致，否则图片会变形
            height = 48
            width = 48
        }
     }
}

```

## 步骤2：在build.gradle中引入svg-support依赖
```gradle

dependencies {
    compile 'com.github.megatronking:svg-support:1.3.0'
}

```

## 步骤3：运行相关task（可以执行下面的命令行，也可以在gradle面板中点击）
```
gradlew svgAssemble
```
这个命令可以将svg文件生成vector资源文件，然后将vector资源文件生成java图片渲染类

```
gradlew svg2vector
```
这个命令可以批量将svg文件生成vector资源文件，即使你不需要java图片渲染类，使用这个任务批量处理svg->vector也是一个不错的选择。

## 步骤4：在Application类中提前初始化SVG加载器
```java

public void onCreate() {
    SVGLoader.load(this)
}

```
# 图片库

SVG-Android提供了一套material-design风格的图片库，是基于google谷歌官方的svg文件生成。在svg-iconlibs目录下面，除了
可以直接使用的vector资源文件外，还有已经生成好的Java图片类文件。如果你的项目缺少UI设计师，那么可以直接通过iconlibs找到大量常用的
图片。


# 文档
- [问题指引](issues_guide.md)
- [机制原理](http://blog.csdn.net/megatronkings/article/details/52454927)
- [接入步骤](http://blog.csdn.net/megatronkings/article/details/52826456)
- [图片处理](http://blog.csdn.net/megatronkings/article/details/52878466)
- [动画使用](http://blog.csdn.net/megatronkings/article/details/53286958)


