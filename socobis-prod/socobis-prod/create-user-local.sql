-- Script pour créer l'utilisateur SOCOBIS dans Oracle local avec TOUS les privilèges
-- Exécuter avec: sqlplus system/[votre_mot_de_passe]@ORCL @create-user-local.sql

-- Supprimer l'utilisateur s'il existe déjà
DROP USER socobis CASCADE;

-- Créer l'utilisateur3
CREATE USER socobis IDENTIFIED BY socobis 
  DEFAULT TABLESPACE users 
  TEMPORARY TABLESPACE temp 
  QUOTA UNLIMITED ON users;

-- Accorder TOUS les privilèges système
GRANT ALL PRIVILEGES TO socobis;

-- Accorder le rôle DBA (inclut presque tous les privilèges)
GRANT DBA TO socobis WITH ADMIN OPTION;

-- Accorder les rôles standards
GRANT CONNECT, RESOURCE TO socobis WITH ADMIN OPTION;

-- Privilèges spécifiques pour les objets
GRANT CREATE SESSION TO socobis;
GRANT CREATE TABLE TO socobis;
GRANT CREATE VIEW TO socobis;
GRANT CREATE SEQUENCE TO socobis;
GRANT CREATE PROCEDURE TO socobis;
GRANT CREATE TRIGGER TO socobis;
GRANT CREATE TYPE TO socobis;
GRANT CREATE SYNONYM TO socobis;
GRANT CREATE DATABASE LINK TO socobis;
GRANT CREATE MATERIALIZED VIEW TO socobis;
GRANT CREATE JOB TO socobis;
GRANT CREATE ANY TABLE TO socobis;
GRANT CREATE ANY VIEW TO socobis;
GRANT CREATE ANY SEQUENCE TO socobis;
GRANT CREATE ANY PROCEDURE TO socobis;
GRANT CREATE ANY TRIGGER TO socobis;
GRANT CREATE ANY TYPE TO socobis;
GRANT CREATE ANY SYNONYM TO socobis;

-- Privilèges de modification
GRANT ALTER ANY TABLE TO socobis;
-- erreur ici
GRANT ALTER ANY VIEW TO socobis;
-- erreur ici
GRANT ALTER ANY SEQUENCE TO socobis;
GRANT ALTER ANY PROCEDURE TO socobis;
GRANT ALTER ANY TRIGGER TO socobis;
-- Privilèges de suppression
GRANT DROP ANY TABLE TO socobis;
GRANT DROP ANY VIEW TO socobis;
GRANT DROP ANY SEQUENCE TO socobis;
GRANT DROP ANY PROCEDURE TO socobis;
GRANT DROP ANY TRIGGER TO socobis;
-- Privilèges de sélection/insertion/mise à jour/suppression
GRANT SELECT ANY TABLE TO socobis;
GRANT INSERT ANY TABLE TO socobis;
GRANT UPDATE ANY TABLE TO socobis;
GRANT DELETE ANY TABLE TO socobis;
-- Privilèges d'exécution
GRANT EXECUTE ANY PROCEDURE TO socobis;
GRANT EXECUTE ANY TYPE TO socobis;

-- Privilèges de gestion
GRANT ALTER SYSTEM TO socobis;
GRANT ALTER SESSION TO socobis;
GRANT ANALYZE ANY TO socobis;
GRANT AUDIT ANY TO socobis;
GRANT COMMENT ANY TABLE TO socobis;
GRANT GRANT ANY PRIVILEGE TO socobis;
GRANT GRANT ANY ROLE TO socobis;

-- Privilèges sur les tablespaces
GRANT UNLIMITED TABLESPACE TO socobis;

-- Vérifier
SELECT username, account_status FROM dba_users WHERE username = 'SOCOBIS';
SELECT * FROM dba_sys_privs WHERE grantee = 'SOCOBIS' ORDER BY privilege;

