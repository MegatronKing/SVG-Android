### 博客：http://blog.csdn.net/megatronkings

##SVG-Support

SVG渲染支持库和组件库，提供对SVG功能的支持，包括tint、alpha、selector、size等。<br>
虽然SVG-Android支持直接使用View组件显示图片，但是一些SVG特性需要扩展组件才能支持。<br>
组件支持：
- SVGView
- SVGImageView
- SVGTextView
- SVGButton
- SVGImageButton
- SVGEditText

----

##版本日志

### V1.1.0

- 新增对动画的支持和扩展
- 修复SVG扩展类一些兼容性bug

### V1.3.0
- 修改为正式包名

### V1.3.1
- 新增支持对PathType(即svg的fill-rule)的处理
- 新增支持autoMirror属性
- 删除不必要的依赖配置，主要是support-v4包
- 升级targetSdkVersion到24

### V1.3.2
- 修复SVGDrawable关于canvas的一个bug
