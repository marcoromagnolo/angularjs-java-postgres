@ECHO off

set WEBCLIENT_PATH="."

echo Using BUILD_PATH: %WEBCLIENT_PATH%

cd %WEBCLIENT_PATH%
call grunt serve
pause
