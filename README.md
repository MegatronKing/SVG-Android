# SVG-Android
support svg images for android 4.0+
___

##一、SVG-Android为何而生
android从5.0开始支持SVG图片，也就是VectorDarwable，但是相比于常用的PNG位图却存在着诸多问题。
###1、性能方面
正常情况下，Vector的性能损耗是PNG的3倍左右，主要表现在解析xml和计算path两个阶段，所以几乎很少有应用会采用。
###2、兼容性
由于5.0才出现VectorDarwable，为了兼容低版本，google推出了support-vector-drawable兼容库。但是实际效果并不好。首先，support-vector-drawable兼容库的最低版本是23.2.0，同时依赖于support-v4，如果support-v4版本过低，无法引入兼容库。
###3、程序包体积
尽管vector文件相比于PNG图片，体积占用比较小，但是为了兼容低版本，打包时编译工具会将vector文件生成对应的PNG图片一并打包到apk中，这非常容易导致包体积膨胀。
###4、实用性
虽然兼容库能兼容到低版本，但是在API使用方面就不那么容易了，我们很难直接在xml中直接使用，比如src="@drawable/svg"，这大大制约了开发效率。<br><br>
针对以上几种缺点，SVG-Android应运而生，完美支持4.0+!

##2、SVG-Android性能比较
相比于PNG位图，SVG-Android在decode阶段性能远远优于PNG位图，但是draw渲染阶段相对逊色了不少，后期会着力于优化这一块。<br>
相比于Vector，android官方的SVG解决方案，SVG-Android在decode阶段的性能遥遥领先，draw阶段也稍稍领先。<br>
总体来说，SVG-Android性能比PNG位图略低0.2-0.5倍，比Vector提高了2-3倍。<br>
另外，对图片效果的呈现，SVG-Android比PNG好很多，完全不会因为尺寸拉伸而失真。<br>
![](https://github.com/MegatronKing/SVG-Android/blob/master/screenshots/performance-test.png)

##3、SVG-Android原理
SVG-Android框架会提前将svg图片转化成的vector xml文件生成SVG的渲染器(Java Code)，同时计算好渲染路径和参数。
这样在draw就不需要解码计算和渲染计算（非常耗时的操作）。同时，SVG-Android会将生成的渲染器类注入到Resources中，
这样可以拦截所有的通过@drawable/xxx获取Drawable的请求，返回SVGDrawable对象，而在SVGDrawable对象内部包含了SVG的渲染器，
从而实现了开发者友好，可以在layout或者selector等中使用svg图片。

##4、SVG-Android如何接入

首先，将svg格式文件生成vector文件(通过svg2android或者Android Studio皆可)
然后，运行SVG-Generator的task run (指定上一步的vector文件目录和Java Code生成目录)
接着，在应用的Application的onCreate中加入SVGLoader.load(this);

##5、Enjoy SVG-Android！


