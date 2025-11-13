@echo off
setlocal enabledelayedexpansion
echo ========================================
echo Import de la base de donnees Oracle LOCALE
echo ========================================
echo.

REM Vérifier qu'Oracle est actif localement
echo [1/4] Verification du service Oracle local...
@REM sc query OracleServiceDBCOURS | findstr "RUNNING" >nul
sc query OracleServiceDBCOURS | findstr "RUNNING" >nul
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: Le service Oracle OracleServiceDBCOURS n'est pas en cours d'execution!
    echo Demarrez le service Oracle depuis les services Windows
    pause
    exit /b 1
)
echo Service Oracle actif: OK
echo.

REM L'utilisateur SOCOBIS existe déjà, on l'utilise directement
echo [2/4] Utilisation de l'utilisateur SOCOBIS existant...
echo User: socobis / Password: socobis
echo.

REM Importer le dump
echo [3/4] Import du fichier DMP (cela peut prendre plusieurs minutes)...

REM Convertir le chemin long en chemin DOS court (format 8.3) pour éviter les espaces
set "DMP_LONG=C:\Users\erant\Documents\S5\Archlog\socobis\socobis\socobis_20251107\socobis_20251107.dmp"
for %%A in ("!DMP_LONG!") do set "DMP_SHORT=%%~sA"
echo Chemin court: !DMP_SHORT!
echo.

REM Import avec le chemin DOS court (sans espaces)
@REM imp userid=socobis/socobis@DBCOURSXDB file=!DMP_SHORT! full=y ignore=y
imp userid=socobis/socobis file=!DMP_SHORT! full=y ignore=y
if %ERRORLEVEL% NEQ 0 (
    echo ATTENTION: L'import a rencontre des erreurs (mais peut avoir reussi partiellement)
    echo Verifiez les logs ci-dessus
)
echo.

echo [4/4] Verification de l'import...
@REM sqlplus -S socobis/socobis@DBCOURSXDB @verify-import.sql
sqlplus -S socobis/socobis @verify-import.sql
echo.

echo ========================================
echo Import termine!
echo ========================================
echo.
echo Informations de connexion Oracle LOCAL:
echo   Hote: localhost
echo   Port: 1521
echo   SID: DBCOURS
echo   User: socobis
echo   Password: socobis
echo.
echo Pour verifier l'import:
echo   sqlplus socobis/socobis@DBCOURS
echo   SQL^> SELECT COUNT(*) FROM user_tables;
echo.
pause
