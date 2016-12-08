### 博客：http://blog.csdn.net/megatronkings

##SVG-Generator

负责解析SVG或者Vector文件，计算Path路径并自动生成SVGRenderer Java代码，同时会生成空内容的shape
资源文件（用于创建资源ID），另外会创建SVGLoader，用于预先加载所有SVGRenderer资源。<br>

为了兼容Android官方的Vector文件以及利用其相关特性，SVG-Generator支持将SVG格式文件自动生成Vector文件。


