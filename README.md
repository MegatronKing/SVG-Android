# SVG-Android
support svg images for android 2.3+
___

### Blog：http://blog.csdn.net/megatronkings

___

![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample1.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample2.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample3.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample4.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample5.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample6.png)

### Sample Download: https://github.com/MegatronKing/SVG-Android/raw/master/SVG-Sample.apk

___

#How to Use

## add plugin dependencies in app build.gradle
```gradle

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.github.megatronking:svg-plugin:1.0.0'
    }
}

svg {
    // vector resources
    vectorDirs = ["src/main/svg_debug/drawable"]
    // shape resources
    shapeDir = "src/main/svg_release/drawable"
    // java classpath
    javaDir = "src/main/java/com/github/megatron/svg/sample/drawables"
    // app(module) package name
    packageName = "com.github.megatron.svg.sample"
}

```

## add app dependencies in app build.gradle
```gradle

dependencies {
    compile 'com.github.megatronking:svg-support:1.0.0'
}

```

## with command Line

```

    gradlew svgAssemble


```

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



