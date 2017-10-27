@echo off
d:
cd %~dp0
setlocal enabledelayedexpansion
set JAVA=%JAVA_HOME%\bin\java
set OPTS= -jar
set lib=%~dp0..\lib

set CP=%CONFIG%;
set MAIN=cc.openstring.oss.statistic.main.Application
  
for /f %%i in ('dir /b %lib%\*.jar^|sort') do (
   set CP=!CP!%lib%\%%i;
)

java -Djava.library.path=%lib% -classpath %CP%  %MAIN% %*

