### Q1：执行svgAssemble或者svg2vector后，只生成了配置的文件夹，未生成指定文件
可能是build.gradle里面插件配置参数不正确，确认下存放svg图片文件的目录和svgDir参数配置目录是否一致。

### Q2：代码中给图片设置tint、alpha等属性，导致其它页面图片都发生变化
这是一个经典的Drawable问题，给Drawable图片设置属性前要先调用mutate()方法。