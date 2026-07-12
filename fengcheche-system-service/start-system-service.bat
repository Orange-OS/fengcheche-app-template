@echo off
setlocal

REM Switch to the parent Maven project directory.
cd /d "%~dp0\.."

if "%SERVER_PORT%"=="" set "SERVER_PORT=8080"
if "%NACOS_SERVER_ADDR%"=="" set "NACOS_SERVER_ADDR=192.168.176.7:8848"
if "%FENGCHECHE_INTERNAL_TOKEN%"=="" set "FENGCHECHE_INTERNAL_TOKEN=fengcheche-internal-token"

echo Starting fengcheche-system-service ...
echo URL: http://127.0.0.1:%SERVER_PORT%/fengcheche
echo Nacos: %NACOS_SERVER_ADDR%
echo.

echo Preparing fengcheche-common ...
call mvn -pl fengcheche-common -am -DskipTests install
if errorlevel 1 goto fail

call mvn -pl fengcheche-system-service spring-boot:run
goto end

:fail
echo Failed to prepare shared modules.
pause
endlocal
exit /b 1

:end
pause
endlocal
