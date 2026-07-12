@echo off
setlocal

REM Switch to the parent Maven project directory.
cd /d "%~dp0\.."

if "%SERVER_PORT%"=="" set "SERVER_PORT=9000"
if "%NACOS_SERVER_ADDR%"=="" set "NACOS_SERVER_ADDR=192.168.176.7:8848"

echo Starting fengcheche-gateway ...
echo URL: http://127.0.0.1:%SERVER_PORT%
echo Nacos: %NACOS_SERVER_ADDR%
echo.

call mvn -pl fengcheche-gateway spring-boot:run

pause
endlocal
