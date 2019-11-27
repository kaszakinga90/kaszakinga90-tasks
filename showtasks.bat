call runcrud.bat

if "%ERRORLEVEL%" == "0" goto browser
echo.
echo script has errors - breaking work
goto fail

:browser
start chrome "http://localhost:8080/crud/v1/task/getTasks"
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Browser cannot open.
goto fail

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.