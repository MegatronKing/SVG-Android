@ ECHO OFF

if  "%JAVA_HOME%" == "" (goto setJava) else (goto convert)
:setJava
@ ECHO 请安装Java JDK并设置环境变量
goto end

:convert
java -jar svg2vector-applet.jar
:end