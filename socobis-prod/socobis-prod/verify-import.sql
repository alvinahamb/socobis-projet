-- Script de vérification de l'import
SET PAGESIZE 50
SET LINESIZE 120

PROMPT ==========================================
PROMPT Verification de l import dans SOCOBIS
PROMPT ==========================================

PROMPT
PROMPT [1] Nombre de tables:
SELECT COUNT(*) as nb_tables FROM user_tables;

PROMPT
PROMPT [2] Tables principales:
SELECT table_name FROM user_tables WHERE ROWNUM <= 20 ORDER BY table_name;

PROMPT
PROMPT [3] Nombre de vues:
SELECT COUNT(*) as nb_vues FROM user_views;

PROMPT
PROMPT [4] Objets invalides:
SELECT object_type, COUNT(*) as count FROM user_objects WHERE status = 'INVALID' GROUP BY object_type;

PROMPT
PROMPT [5] Verification table UTILISATEUR (CRITIQUE):
SELECT COUNT(*) as existe FROM user_tables WHERE table_name = 'UTILISATEUR';

PROMPT
PROMPT [6] Verification tables critiques:
SELECT 
    CASE WHEN COUNT(*) > 0 THEN 'EXISTE' ELSE 'MANQUANTE' END as statut,
    'UTILISATEUR' as table_name
FROM user_tables WHERE table_name = 'UTILISATEUR'
UNION ALL
SELECT 
    CASE WHEN COUNT(*) > 0 THEN 'EXISTE' ELSE 'MANQUANTE' END,
    'ROLES'
FROM user_tables WHERE table_name = 'ROLES'
UNION ALL
SELECT 
    CASE WHEN COUNT(*) > 0 THEN 'EXISTE' ELSE 'MANQUANTE' END,
    'ANNULATIONUTILISATEUR'
FROM user_tables WHERE table_name = 'ANNULATIONUTILISATEUR';

