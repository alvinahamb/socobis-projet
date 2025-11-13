@echo off
echo ========================================
echo Redéploiement rapide de Socobis
echo ========================================
echo.

REM Vérifier si les conteneurs sont en cours d'exécution
echo [1/5] Verification de l'etat des conteneurs...
docker ps | findstr wildfly_10 >nul
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: Le conteneur wildfly_10 n'est pas en cours d'execution!
    echo Utilisez deploy.bat pour le premier deploiement.
    pause
    exit /b 1
)
echo Conteneurs actifs: OK
echo.

REM Compiler le projet avec Ant dans le conteneur Wildfly
echo [2/5] Compilation du projet avec Ant...
echo Nettoyage de l'ancien build...
docker exec -u root wildfly_10 rm -rf /tmp/socobis-build
echo Copie des sources dans le conteneur (sans .git, .idea, build-file)...
docker cp socobis-ejb wildfly_10:/tmp/socobis-build/socobis-ejb
docker cp socobis-war wildfly_10:/tmp/socobis-build/socobis-war
docker cp build.xml wildfly_10:/tmp/socobis-build/build.xml
echo Changement des permissions...
docker exec -u root wildfly_10 chmod -R 777 /tmp/socobis-build
echo Compilation en cours...
docker exec -u root wildfly_10 bash -c "cd /tmp/socobis-build && ant clean && ant"
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: La compilation a echoue!
    pause
    exit /b 1
)
echo Compilation: OK
echo.

REM Copier le WAR compilé vers Wildfly
echo [3/5] Deploiement du WAR dans Wildfly...
docker exec -u root wildfly_10 bash -c "rm -rf /opt/jboss/wildfly/standalone/deployments/socobis.war*"
docker exec -u root wildfly_10 bash -c "cp -r /tmp/socobis-build/build-file/socobis_war /opt/jboss/wildfly/standalone/deployments/socobis.war"
docker exec -u root wildfly_10 bash -c "touch /opt/jboss/wildfly/standalone/deployments/socobis.war.dodeploy"
echo Deploiement: OK
echo.

REM Attendre un peu pour le déploiement
echo [4/5] Attente du deploiement (20 secondes)...
timeout /t 20 /nobreak
echo.

REM Afficher les logs récents
echo [5/5] Verification du deploiement...
echo ========================================
echo Logs de deploiement:
echo ========================================
docker logs --tail 50 wildfly_10
echo.

echo ========================================
echo Redeploiement termine!
echo ========================================
echo.
echo L'application est disponible sur:
echo   http://localhost:8280/socobis/login
echo.
pause
