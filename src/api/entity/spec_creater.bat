@echo off
cls

for /d %%D in (*) do (
	copy NUL %%~D.service.spec.ts
	move %%~D.service.spec.ts ./%%~D/
)

REM ng generate service ./%%~ni/%%~ni --nospec --no-interactive
REM move change.ts ./change/
