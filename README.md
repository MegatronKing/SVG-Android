# SVG-Android
support svg images for android 2.3+
___

### 参考博客：http://blog.csdn.net/megatronkings

___

![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample1.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample2.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample3.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample4.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample5.png)
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/sample6.png)

### 范例下载：https://github.com/MegatronKing/SVG-Android/raw/master/SVG-Sample.apk

___

##一、SVG-Android为何而生
Android从5.0开始支持SVG图片，也就是VectorDarwable，但是相比于常用的PNG位图却存在着诸多问题。
###1、性能方面
正常情况下，Vector的性能损耗是PNG的3倍左右，主要表现在解析xml和计算path两个阶段，如此大的性能差距，导致很少有公司会采用。
###2、兼容性
由于android 5.0以上才出现VectorDarwable，为了兼容低版本，google推出了support-vector-drawable兼容库，但是实际效果并不好。首先，support-vector-drawable兼容库的最低版本是23.2.0，同时依赖于support-v4，如果support-v4版本过低，无法引入兼容库。其次，如果要在layout中直接使用vector，必须引入support-v7包，对于不使用v7包的应用来说，只能舍弃vector了。
###3、程序包体积
尽管Vector文件相比于PNG图片，体积占用比较小，但是为了兼容低版本，打包时编译工具会将vector文件生成对应的PNG图片一并打包到apk中，这非常容易导致包体积膨胀。
###4、实用性
虽然兼容库能兼容到低版本，但是在API使用方面就不那么容易了，我们很难直接在xml中直接使用，比如src="@drawable/svg"，这大大制约了开发效率。<br><br>
针对以上几种缺点，SVG-Android应运而生，完美支持2.3+!

___

##二、SVG-Android性能比较

### SVG-Android VS PNG 
SVG-Android在decode阶段性能远远优于PNG位图，但是draw渲染阶段相对逊色了不少，后期会着力于优化这一块。
### SVG-Android VS Vector
SVG-Android在decode阶段的性能遥遥领先，耗时大约在100-200us，比Vector高出至少10倍<br>
SVG-Android在draw阶段也稍稍领先,大概节约了250us<br>
### SVG-Android VS IconFont
IconFont必须提前加载字体库，这其实是一个相当耗时的操作，500个字符大约在4000us左右，随着图片增加，字体库肯定会膨胀，总耗时实际更多。
虽然，在渲染阶段耗时极少，但是整体耗时依然很高。从内存方面来看，加载字体库的内存消耗在native层，不太好监测，但估计应该也不小。

总体来说，SVG-Android性能方面比PNG位图略低0.2-0.5倍，比Vector提高了2-3倍。但是对于对图片效果的呈现，SVG-Android比PNG好很多，完全不会因为尺寸拉伸而失真。<br>

下图是100次加载的测试数据，单位us，很明显SVG-Android总体效果还是有优势的。<br><br>
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/performance-test.png)

___

##三、SVG-Android功能比较

### SVG-Android VS PNG&Vector
展现效果：SVG-Android图片完美实现了矢量图的效果，无论放大缩小，图片的展现效果永远不会失真。<br>
多图复用：对于相同内容颜色不同的图片，SVG-Android只需要一份，可以随时改变图片颜色和透明。

### SVG-Android VS IconFont
SVG-Android可以直接绑定ImageView，支持scaleType、alpha、tint等功能。<br>
SVG-Android使用在布局文件中的时候，可以直接在右侧预览效果，这一点IconFont不具有。<br>
SVG-Android使用SVGColorImageView控件同样可以完美支持selector。<br>
使用PNG&Vector的项目接入SVG-Android非常快速，而接入IconFont会非常耗时。

___

##四、SVG-Android实现原理

###1、预解析
从对Vector的性能测试数据来看，大部分耗时都在解析xml和绘制渲染两个阶段。为了提高性能，SVG-Android的做法是将部分耗时操作由运行时转移到编译前，也就是预解析。同时，由于svg文件的fillData的数据在Android中表现为Path，这部分计算量也是可以预先计算好的。<br><br>
所以，SVG-Generator库会将Vector文件提前解析生成用于直接渲染的SVGRenderer类，另外fillData的每个指令数也会预先计算好，直接生成Java Path代码，SVGDrawable只要通过SVGRenderer就能画出svg图形了。<br><br>
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/generate-codes.png)

###2、无感知
为了提高开发效率，我们希望开发者在使用SVG图片的时候能够和使用常规的PNG一样，可以在layout文件中直接使用@drawable/xxx，或者java代码中使用R.drawable.xxx。为了解决这个问题，我们采用偷天换日的方式，使用SVG-Generator生成一张空的shape文件，放入到drawable-anydpi中，同时会将对应的SVGDrawableConstantState预先注入到Resources的sPreloadedDrawables缓存中，拦截掉所有对shape的获取请求。<br><br>
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/generate-codes.png)

___

##五、SVG-Android如何接入

###1、SVG图片转换成Vector文件
由于Android只支持部分规范的SVG文件，所以我们还是按照官方的思路，先生成合法的Vector文件，这样还有个好处就是可以引用dimen和color，方面以后统一修改尺寸和颜色。<br>

SVG图片转换成Vector文件有很多种方式。<br>

方式一：使用svg2android网站转换 http://inloop.github.io/svg2android/ <br>

方式二：使用Android Studio 右键 -> New -> Vector Asset -> Local SVG File

###2、SVG-Generator解析Vector自动生成代码
首先，在SVG-Generator模块的Config类中配置好参数：包括应用包名、生成SVGRenderer代码包名、以及Vector中引用的dimen和color。<br><br>
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/step1.png)


其次，在SVG-Generator模块的build.gradle文件中配置三个参数：存放Vector文件的目录，生成空shape文件目录、生成Java渲染代码目录。<br><br>
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/step2.png)


接着，运行SVG-Generator的task run。可以在gradle的task列表中点击，也可以运行命令：gradle task run <br><br>
最后，我们可以看到在指定的目录下生成了SVGRenderer类,类名就是图片名。同时，在drawable-anydpi生成了对应的空shape文件。

###3、应用程序中引入SVG-Support包并装载SVGLoader
在应用程序的Application自定义类中，装载SVGLoader（上一步自动生成），只要一行代码！<br><br>
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/step3.png)

___

##六、Enjoy SVG-Android！

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



