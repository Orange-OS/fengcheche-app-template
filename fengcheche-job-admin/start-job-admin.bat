@echo off
setlocal

REM Switch to the parent Maven project directory.
cd /d "%~dp0\.."

REM XXL-JOB Admin is standalone and does not register to Nacos.
if "%SERVER_PORT%"=="" set "SERVER_PORT=8088"
if "%XXL_JOB_DB_URL%"=="" set "XXL_JOB_DB_URL=jdbc:mysql://192.168.176.7:13306/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai"
if "%XXL_JOB_DB_USERNAME%"=="" set "XXL_JOB_DB_USERNAME=root"
if "%XXL_JOB_DB_PASSWORD%"=="" set "XXL_JOB_DB_PASSWORD=root"
if "%FENGCHECHE_SYSTEM_AUTH_BASE_URL%"=="" set "FENGCHECHE_SYSTEM_AUTH_BASE_URL=http://127.0.0.1:9000/api/system/auth"
if "%FENGCHECHE_XXL_JOB_REQUIRED_ROLE%"=="" set "FENGCHECHE_XXL_JOB_REQUIRED_ROLE=admin"

echo Starting fengcheche-job-admin ...
echo URL: http://127.0.0.1:%SERVER_PORT%/xxl-job-admin
echo System Auth: %FENGCHECHE_SYSTEM_AUTH_BASE_URL%
echo.

call mvn -pl fengcheche-job-admin spring-boot:run

pause
endlocal
