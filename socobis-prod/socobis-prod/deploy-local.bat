@echo off
echo ========================================
echo Deploiement SOCOBIS avec Oracle LOCAL
echo ========================================
echo.

REM Vérifier qu'Oracle local est actif
echo [1/6] Verification du service Oracle local...
sc query OracleServiceDBCOURS | findstr "RUNNING" >nul
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: Le service Oracle OracleServiceDBCOURS n'est pas en cours d'execution!
    echo Demarrez le service Oracle depuis les services Windows
    pause
    exit /b 1
)
echo Service Oracle local: OK
echo.

REM Arrêter les anciens conteneurs
echo [2/6] Arret des anciens conteneurs...
docker stop wildfly_10 2>nul
docker stop oracle_11g 2>nul
docker rm wildfly_10 2>nul
echo Nettoyage: OK
echo.

REM Build de l'image
echo [3/6] Construction de l'image Docker Wildfly...
docker-compose build
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: La construction de l'image a echoue!
    pause
    exit /b 1
)
echo Build: OK
echo.

REM Démarrer le conteneur Wildfly
echo [4/6] Demarrage du conteneur Wildfly...
docker-compose up -d
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: Le demarrage du conteneur a echoue!
    pause
    exit /b 1
)
echo Conteneur demarre: OK
echo.

REM Attendre que Wildfly soit prêt
echo [5/6] Attente du demarrage de Wildfly (30 secondes)...
timeout /t 30 /nobreak >nul
echo.

REM Vérifier la connexion
echo [6/6] Verification de la connexion...
docker logs wildfly_10 2>&1 | findstr "Started server" >nul
if %ERRORLEVEL% EQU 0 (
    echo Wildfly est pret!
) else (
    echo ATTENTION: Wildfly pourrait ne pas etre completement demarre
)
echo.

echo ========================================
echo Deploiement termine!
echo ========================================
echo.
echo Configuration Oracle LOCAL:
echo   Host depuis Docker: host.docker.internal
echo   Port: 1521
echo   SID: ORCL
echo   User: socobis
echo   Password: socobis
echo.
echo Services disponibles:
echo   - Application Socobis: http://localhost:8280/socobis/login
echo   - Console Wildfly: http://localhost:9095
echo.
echo Commandes utiles:
echo   - Voir les logs: docker logs -f wildfly_10
echo   - Redemarrer: docker restart wildfly_10
echo   - Arreter: docker-compose down
echo.
pause
