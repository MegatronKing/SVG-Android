# SVG-Android

[![Jcenter Status](https://img.shields.io/badge/download-1.3.7-brightgreen.svg)](https://bintray.com/megatronking/maven)
[![license](http://img.shields.io/badge/license-apache_2.0-red.svg?style=flat)](https://github.com/MegatronKing/SVG-Android/raw/master/LICENSE)

*switch language: [简体中文](README.zh-cn.md).*

support svg images for android 4.0+ <br>

The library provides a generator to convert vector or svg images to java classes.
We can use those java classes to render images directly when drawn, it would be 
much faster than vectors, and it avoids compatibility issues. On the other hand,
the support library has some powerful functions such as tinting, ajusting size, 
changing alpha both in java codes or xml files.

### Blog：http://blog.csdn.net/megatronkings
#### Wechat：King20091305035

###

![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample1.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample2.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample3.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample4.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample5.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample6.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/animation1.gif)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/animation2.gif)

### Sample Download: https://github.com/MegatronKing/SVG-Android/raw/master/SVG-Sample.apk

# About
SVG-Android is a support library for showing svg images in android 4.0+ .
- faster than png, vector and iconfont, the decoding stage costs less time.
- better performance and expression than png even using a huge image.
- support tint, alpha, size, selector whether in java code or xml code.
- shrink the apk size, it can reduce the image size obviously.
- support drawable animtions with some assigned animators.


  |\\            | speed  | memory | compatible | scaleType | expression | multi-colors | shrink |
  ---------------|--------|--------|------------|-----------|------------|--------------|--------
  |SVG-Android   | √      | √      | √          | √         | √          | √            | √      |
  |Vector        | ×      | √      | x          | √         | √          | √            | ×      |
  |Png           | √      | ×      | √          | √         | ×          | √            | ×      |
  |Iconfont      | ×      | √      | √          | ×         | √          | ×            | √      |

# How it works

![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/core.png)

# How to Use

## add plugin dependencies in app build.gradle
```gradle
apply plugin: 'svg'

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.github.megatronking:svg-plugin:1.3.7'
    }
}

svg {
    // vector resources
    vectorDirs = ["src/main/svg_debug/drawable"]
    // shape resources
    shapeDir = "src/main/svg_release/drawable"
    // java classpath
    javaDir = "src/main/java/com/github/megatronking/svg/sample/drawables"

    svg2vector {
        test {
            svgDir = "${rootDir}/test"
            vectorDir = "src/main/svg_debug/drawable"
            height = 48
            width = 48
        }
     }
}

```

## add dependencies in app build.gradle
```gradle

dependencies {
    compile 'com.github.megatronking:svg-support:1.3.1'
}

```

## with command Line
```
gradlew svgAssemble
```
With this command, we convert svg to vector and generate java classes from vector.<br>
After that, we will see some generated classes in dir 'src/main/java/com/github/megatronking/svg/sample/drawables'.

```
gradlew svg2vector
```
With this command, a batch of svgs will be converted to vector files. No matter whether you need java render classes, just run this task, it is more convenient than converted them one by one.
[See svg support doc](support_doc.md)

## install SVGLoader in Application class
```java

public void onCreate() {
    SVGLoader.load(this)
}

```

## use svg image
```
<ImageView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/ic_android_red"/>
```

or

```
Drawable drawable = new SVGDrawable(new ic_android_red(context));
imageView.setImageDrawable(drawable);
```


--------

    Copyright (C) 2017, Megatron King

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



