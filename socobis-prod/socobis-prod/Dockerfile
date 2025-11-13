# # Utiliser une image Alpine Linux comme base
# FROM alpine:latest

# # Installer OpenJDK 8 et autres dépendances
# RUN apk update && apk add --no-cache \
#     openjdk8-jre \
#     wget \
#     unzip

# # Définir JAVA_HOME pour OpenJDK 8
# ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
# ENV PATH $JAVA_HOME/bin:$PATH

# # Télécharger et installer WildFly
# RUN wget https://download.jboss.org/wildfly/10.1.0.Final/wildfly-10.1.0.Final.zip \
#     && unzip wildfly-10.1.0.Final.zip -d /opt/ \
#     && mv /opt/wildfly-10.1.0.Final /opt/wildfly \
#     && rm wildfly-10.1.0.Final.zip

# # Définir WILDFLY_HOME
# ENV WILDFLY_HOME /opt/wildfly

# # Copier votre application WAR dans le dossier de déploiement de WildFly
# COPY ./deployments/socobis.war $WILDFLY_HOME/standalone/deployments/socobis.war

# RUN touch $WILDFLY_HOME/standalone/deployments/socobis.war.dodeploy

# # Exposer les ports nécessaires
# EXPOSE 8080 9990

# # Démarrer WildFly
# CMD ["/opt/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]


# Étape 1 : Compilation avec Ant
# FROM eclipse-temurin:8-jdk AS builder
# WORKDIR /app

# # Copier tout le code source et le build.xml
# COPY . /app

# # Installer Ant
# RUN apt-get update && apt-get install -y ant

# # Compiler le projet pour produire le WAR
# RUN ant -f build.xml

# # Étape 2 : Serveur Wildfly
# FROM jboss/wildfly:10.1.0.Final
# WORKDIR /opt/jboss/wildfly

# # Ajouter un utilisateur admin
# RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

# # Copier le WAR compilé depuis l'étape 1
# COPY --from=builder /app/build-file/socobis_war /opt/jboss/wildfly/standalone/deployments/socobis.war

# # Exposer les ports : 8080 (app) et 9990 (admin console)
# EXPOSE 8080 9990

# # Démarrer Wildfly avec la console d'administration accessible de l'extérieur
# CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

# Étape 1 : Construction avec Ant
# Étape 1 : Construction avec Ant
FROM eclipse-temurin:8-jdk AS builder
WORKDIR /app

# Copier tout le projet
COPY . /app

# Installer Ant
RUN apt-get update && apt-get install -y ant && apt-get clean

# Lancer le build complet avec Ant (il déploie dans /app/build-file/socobis_war)
RUN ant -f build.xml deploy

# Étape 2 : Serveur WildFly
FROM jboss/wildfly:10.1.0.Final
WORKDIR /opt/jboss/wildfly

# Ajouter un utilisateur admin
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

# Copier le dossier "exploded war" directement dans les déploiements WildFly
COPY --from=builder /app/build-file/socobis_war /opt/jboss/wildfly/standalone/deployments/socobis.war

# Ajouter le marqueur de déploiement
RUN touch /opt/jboss/wildfly/standalone/deployments/socobis.war.dodeploy

# Exposer les ports : 8080 (appli) et 9990 (console admin)
EXPOSE 8080 9990

# Démarrer WildFly (avec console externe)
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]


