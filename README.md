# SVG-Android
support svg images for android 4.0+
___

##1、SVG-Android为何而生
android从5.0开始支持SVG图片，但是相比于常用的PNG位图存在着解码耗时长和渲染性能差等问题。而且在兼容性方面，虽然提供了
support-vector-drawable兼容库，但是对于开发者而言API并不友好。<br>
所以SVG-Android应运而生，完美支持4.0+，使用方式和PNG完全一样!<br>
同时在性能方面做了优化，虽然性能方面依然稍逊PNG位图一筹，但是考虑到SVG的图片的呈现效果和扩展，这点是可以接受的。

##2、SVG-Android性能比较
相比于PNG位图，SVG-Android在decode阶段性能远远优于PNG位图，但是draw渲染阶段相对逊色了不少，后期会着力于优化这一块。
相比于Vector，android官方的SVG解决方案，SVG-Android在decode阶段的性能遥遥领先，draw阶段也稍稍领先。
总体来说，SVG-Android性能比PNG位图略低0.2-0.5倍，比Vector提高了2-3倍。
另外，对图片效果的呈现，SVG-Android比PNG好很多，完全不会因为尺寸拉伸而失真。
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


