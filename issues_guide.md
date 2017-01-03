### Q1：引入此库后会不会增加APK包体积
不会，只会减小不会增加。首先，真正打包到APK中的依赖只有SVG-Support库，这个库非常轻巧，只有十几个类且不大。
其次，虽然每张svg图片都会被Generator生成对应的一个java渲染类和一个无内容的shape资源文件，但是java类编译成
字节码文件再压到dex后，体积会非常小，相比于正常1-5k的PNG图片会小很多，而且不需要做任何dpi的适配。
再者，SVG-Android库可以大幅度增加图片利用率，不同尺寸，不同颜色、不同透明度，不同旋转角度等，只需要一张基本色彩
的SVG图片，我们可以在代码里面控制其颜色、尺寸、透明度、旋转角度等并且保证不失真。

### Q2：执行svgAssemble或者svg2vector后，只生成了配置的文件夹，未生成指定文件
可能是build.gradle里面插件配置参数不正确，确认下存放svg图片文件的目录和svgDir参数配置目录是否一致（尤其注意projectDir和rootDir的区别）。

### Q3：代码中给图片设置tint、alpha等属性，导致其它页面图片都发生变化
这是一个经典的Drawable问题，给Drawable图片设置属性前要先调用mutate()方法。

### Q4：执行svgAssemble后，SVGLoader中资源文件引用报错或者xml中无法引用@drawable/xxx图片
检查build.gradle中的sourceSets配置，需要在debug配置的res.srcDirs添加vector资源目录，这样可以方便开发时预览图片，
而release正式包配置则需要添加shape资源目录（注意，release不能添加vector资源目录，否则apk包中会多出很多冗余无用的
资源文件导致体积增大）。但从svg-plugin:1.3.3版本开始，不再需要手动配置sourceSets，插件会默认将vector资源目录和shape资源目录
添加到sourceSets中，不过如果你不希望插件自动添加，可以配置autoSourceSet=false来禁用此功能。