@ECHO off

set WEBSERVER_PATH="target"

call cd %WEBSERVER_PATH%
call java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar webapp-1.0-SNAPSHOT.jar
pause