@echo off
setlocal

REM Switch to the parent Maven project directory.
cd /d "%~dp0\.."

if "%SERVER_PORT%"=="" set "SERVER_PORT=8091"
if "%NACOS_SERVER_ADDR%"=="" set "NACOS_SERVER_ADDR=192.168.176.7:8848"
if "%FENGCHECHE_INTERNAL_TOKEN%"=="" set "FENGCHECHE_INTERNAL_TOKEN=fengcheche-internal-token"
if "%XXL_JOB_ADMIN_ADDRESSES%"=="" set "XXL_JOB_ADMIN_ADDRESSES=http://127.0.0.1:8088/xxl-job-admin"
if "%XXL_JOB_EXECUTOR_APPNAME%"=="" set "XXL_JOB_EXECUTOR_APPNAME=fengcheche-job-executor"
if "%XXL_JOB_EXECUTOR_PORT%"=="" set "XXL_JOB_EXECUTOR_PORT=9999"

echo Starting fengcheche-job-executor-service ...
echo URL: http://127.0.0.1:%SERVER_PORT%/fengcheche-job-executor
echo Nacos: %NACOS_SERVER_ADDR%
echo XXL-JOB Admin: %XXL_JOB_ADMIN_ADDRESSES%
echo Executor AppName: %XXL_JOB_EXECUTOR_APPNAME%
echo Executor Port: %XXL_JOB_EXECUTOR_PORT%
echo.

echo Preparing fengcheche-common ...
call mvn -pl fengcheche-common -am -DskipTests install
if errorlevel 1 goto fail

call mvn -pl fengcheche-job-executor-service spring-boot:run
goto end

:fail
echo Failed to prepare shared modules.
pause
endlocal
exit /b 1

:end
pause
endlocal
