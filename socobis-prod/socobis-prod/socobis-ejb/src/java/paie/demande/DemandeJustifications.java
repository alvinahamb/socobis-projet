/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paie.demande;

import bean.CGenUtil;
import bean.ClassEtat;
import bean.ClassMAPTable;
import bean.TypeObjet;
import paie.conge.Conge;
import paie.conge.MouvementAbsence;
import utilitaire.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import paie.conge.CongeDroit;
import paie.conge.CongeMoins;
import paie.demande.EmployeComplet;
import utils.ConstantePaie;

/**
 *
 * @author Tsiky
 */
public class DemandeJustifications extends ClassEtat {

    String id, idpersonnel, matricule, idtypedemande, titre, nom, prenom, typeabsencelib;
    String horairenormal;
    String heuredepart;
    String heurearrive;
    String idtypeabsence, motif;
    String numero, desce, etatlib;
    Date daty, datedepart, datefin, dateretour;
    String observation, remarque, refuser;
    int rang, rangUser;
    double duree;
    EtatHierarchie etatHierarchie;
    DemandeJustificationVise situationDemandeSuivant;
    String validation, signature;
    double reste_conge;
    String personnel;
    String idremplacents;
    String remplacents;
    String idSup;
    String avenant;
    String annuler;
    double reste_permission;
    double dureefinal;
    String superieur;
    String motifRefu;

     public String getMotifRefu() {
        return motifRefu;
    }

    public void setMotifRefu(String motifRefu) {
        this.motifRefu = motifRefu;
    }

    public DemandeJustifications() {
        setNomTable("demande");
        setEtat(-1);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeabsencelib() {
        return typeabsencelib;
    }

    public void setTypeabsencelib(String typeabsencelib) {
        this.typeabsencelib = typeabsencelib;
    }

    public String getIdremplacents() {
        return idremplacents;
    }

    public void setIdremplacents(String idremplacents) {
        this.idremplacents = idremplacents;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getRemplacents() {
        return remplacents;
    }

    public void setRemplacents(String remplacents) {
        this.remplacents = remplacents;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setSignatures(Map param) {
        String[] signatures = Utilitaire.split(this.getSignature(), ";");
        int i = 1;
        for (String sign : signatures) {
            if (sign.compareToIgnoreCase("-") == 0) {
                param.put("signature" + i, null);
                i++;
                continue;
            }
            param.put("signature" + i, ConstantePaie.imagePath + sign);
            i++;
        }
    }

    public double getReste_conge() {
        return reste_conge;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public void setReste_conge(double reste_conge) {
        this.reste_conge = reste_conge;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws Exception {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) throws Exception {
        this.prenom = prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getIdpersonnel() {
        return idpersonnel;
    }

    public void setIdpersonnel(String idpersonnel) {
        this.idpersonnel = idpersonnel;
    }

    public String getIdtypedemande() {
        return idtypedemande;
    }

    public void setIdtypedemande(String idtypedemande) {
        this.idtypedemande = idtypedemande;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getHorairenormal() {
        return horairenormal;
    }

    public void setHorairenormal(String horairenormal) {
        this.horairenormal = horairenormal;
    }

    public String getHeuredepart() {
        return heuredepart;
    }

    public void setHeuredepart(String heuredepart) {
        this.heuredepart = heuredepart;
    }

    public String getHeurearrive() {
        return heurearrive;
    }

    public void setHeurearrive(String heurearrive) {
        this.heurearrive = heurearrive;
    }

    public String getIdtypeabsence() {
        return idtypeabsence;
    }

    public void setIdtypeabsence(String idtypeabsence) {
        this.idtypeabsence = idtypeabsence;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDesce() {
        return desce;
    }

    public void setDesce(String desce) {
        this.desce = desce;
    }

    public String getEtatlib() {
        if (this.getMode().compareTo("select") == 0) {
            return chaineEtat(getEtat());
        }
        return etatlib;
    }

    public void setEtatlib(String etatlib) {
        if (this.getMode().compareTo("select") == 0) {
            this.etatlib = chaineEtat(getEtat());
            return;
        }
        this.etatlib = etatlib;
    }

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public Date getDatedepart() {
        return datedepart;
    }

    public void setDatedepart(Date datedepart) throws Exception {
//        if(this.getMode().compareToIgnoreCase("modif")==0 && datedepart == null) {
//            throw new Exception("La date de depart ne peut pas etre vide.");
//        }
//        if(this.getMode().compareToIgnoreCase("modif")==0 && datedepart.compareTo(Utilitaire.dateDuJourSql()) < 0) {
//            throw new Exception("Date de depart invalide ! Elle doit etre superieure ou egale a la date du jour.");
//        }
        this.datedepart = datedepart;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) throws Exception {
//        if (this.getMode().equalsIgnoreCase("modif") && datefin == null) {
//            throw new Exception("La date de fin doit être obligatoire !");
//        }
//
//        if (this.getMode().equalsIgnoreCase("modif") && Utilitaire.compareDaty(this.getDatedepart(), datefin) >= 0) {
//            throw new Exception("La date de fin doit etre superieure a la date de depart !");
//        }
        this.datefin = datefin;
    }

    public Date getDateretour() {
        return dateretour;
    }

    public void setDateretour(Date dateretour) {
        this.dateretour = dateretour;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getSuperieur() {
        return superieur;
    }

    public void setSuperieur(String superieur) {
        this.superieur = superieur;
    }

    public EtatHierarchie getEtatHierarchie() {
        return etatHierarchie;
    }

    public void setEtatHierarchie(EtatHierarchie etatHierarchie) {
        this.etatHierarchie = etatHierarchie;
    }

    public DemandeJustificationVise getSituationDemandeSuivant() {
        return situationDemandeSuivant;
    }

    public void setSituationDemandeSuivant(DemandeJustificationVise situationDemandeSuivant) {
        this.situationDemandeSuivant = situationDemandeSuivant;
    }

    @Override
    public String getTuppleID() {
        return id;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }


    @Override
    public void controler(Connection c) throws Exception {
        //Controle solde de conger
        if(this.getIdtypeabsence().equals(ConstantePaie.id_typeabsenceconge)) {
            MouvementAbsence mvt = new MouvementAbsence();
            mvt.setIdPersonnel(this.getIdpersonnel());
            double resteConge = mvt.getSoldeCongePers();
            if(this.getDuree() > resteConge) {
                throw new Exception("Solde conge insuffisant : " + resteConge);
            }
        }

        //controle conger
//        if (this.getIdtypeabsence().equals("TYP000004")) {
//            if (this.getDuree() > this.getReste_conge()) {
//                throw new Exception("Solde cong&eacute; insuffisant:" + this.getReste_conge());
//            }
//        }
//        if (this.getIdtypeabsence().equals(ConstantePaie.id_typeabsencepermission)) {
//            MouvementAbsence mvt = new MouvementAbsence();
//            mvt.setIdPersonnel(this.getIdpersonnel());
//            double restePermission = mvt.getSoldePermissionPers();
//            if(this.getDuree() > restePermission) {
//                throw new Exception("Solde permission insuffisant : " + restePermission);
//            }
//        }

        //motif
        if (this.getMotif().equals("")) {
            if (!this.getIdtypeabsence().equals(ConstantePaie.id_typeabsenceconge)) {
                throw new Exception("Veuillez entrer une motif");
            }
        }

        //controle duree negatif
        System.out.println("this.getDuree() ====>" + this.getDuree());
        if (this.getAvenant().compareToIgnoreCase("") == 0 || this.getAvenant() == null) {
            if (this.getDuree() <= 0) {
                throw new Exception("La duree ne peut pas &egrave;tre inferieur &agrave; 0");
            }
        }

        //controle nbjour par type
        TypeObjet recherche = new TypeObjet();
        recherche.setNomTable("typeabsence");
        TypeObjet[] listeabsence = (TypeObjet[]) CGenUtil.rechercher(recherche, null, null, c, " and id='" + this.getIdtypeabsence() + "'");
        if (this.getDuree() > Double.valueOf(listeabsence[0].getVal())) {
            throw new Exception("Le nombre de jour max pour le" + listeabsence[0].getDesce() + " est de :" + listeabsence[0].getVal());
        }
        if (this.getAvenant() != null && this.getAvenant().compareToIgnoreCase("") != 0) {
//            double duree = this.getDuree() * (-1);
            double duree = this.getDuree();
            this.setDuree(duree);
        }
    }

    public void construirePK(Connection c) throws Exception {
        super.setNomTable("demande");
        this.preparePk("DM", "get_seq_demande");
        this.setId(makePK(c));
    }

    public double getDuree() {
        return duree;
    }

    public void setDuree(double duree) throws Exception {
        if (this.getMode().equals("modif") && (duree <= 0) ) {
            throw new Exception("La duree de la demande est invalide");
        }
        this.duree = duree;
    }

    public void controlerVisaEtatCree(EmployeComplet employe, String u, Connection c) throws Exception {
        try {
            EtatDemande ed = new EtatDemande();
            ed.setNomTable("ETATDEMANDE_LIBCPL");

            EtatDemande[] etatDemandes = (EtatDemande[]) CGenUtil.rechercher(ed, null, null, c, " and idtypedemande='" + this.getIdtypedemande() + "' order by rang asc");
            if (etatDemandes.length == 0) {
                throw new Exception("Etat demande non configur\\351");
            }
            EtatHierarchie[] etatHierarchie = (EtatHierarchie[]) CGenUtil.rechercher(new EtatHierarchie(), null, null, c, " and id='" + etatDemandes[0].getIdEtatHierarchie() + "'");
            if (etatHierarchie.length == 0) {
                throw new Exception("Etat hierarchie non configur\\351");
            }
            if (this.getIdpersonnel() != null && employe.getId() != null && employe.getId().compareTo(this.getIdpersonnel()) != 0) {
                throw new Exception("En attente de validation du demandeur");
            }
            this.setEtatHierarchie(etatHierarchie[0]);
        } catch (Exception e) {
            throw e;
        }
    }

    // @Override
    // public Object validerObject(String u, Connection c) throws Exception{
    // EmployeComplet ec = new EmployeComplet();
    // ec.setNomTable("EMPLOYE_COMPLET");
    // EmployeComplet[]employe = (EmployeComplet[]) CGenUtil.rechercher(ec, null,
    // null, c, " and refuser='"+u+"'");
    // if(employe.length==0)throw new Exception("Employ\\351 introuvable");
    // HistoriqueDemande historique = new HistoriqueDemande(employe[0].getId(),
    // this.getId(), Utilitaire.heureCourante(), Utilitaire.dateDuJourSql());
    // if(this.estRefusee() == true) throw new Exception("Demande d\\351j\\340
    // refus\\351e.");
    // if(employe.length>0 && this.getEtat()==ConstanteEtatPaie.getEtatCreer()){
    // this.controlerVisaEtatCree(employe[0], u, c);
    // if(this.getIdpersonnel()!=null&&employe[0].getId()!=null&&employe[0].getId().compareTo(this.getIdpersonnel())==0){
    // this.updateWithHistorique(historique, employe[0].getEtatvisa(), c);
    // return this;
    // }
    // }else if(employe.length>0 && this.getEtat()>ConstanteEtatPaie.getEtatCreer()){
    // controleAction(employe[0], u, c);
    // this.updateWithHistorique(historique, employe[0].getEtatvisa(), c);
    // }
    // if(this.getEtat()>=11){
    // System.out.println("Validation final par SE (insert conger vers
    // conge_pris):");
    // System.out.println("iddemande => "+this.getId());
    // DemandeJustifications dem = new DemandeJustifications();
    // dem.setNomTable("demande");
    // DemandeJustifications[]dems = (DemandeJustifications[])
    // CGenUtil.rechercher(dem, null, null, c, " and id='"+this.getId()+"'");
    // if(dems[0].getIdtypeabsence().equals("TYP000004")){
    // System.out.println("idpersonnel => "+dems[0].getIdpersonnel());
    // System.out.println("solde => "+dems[0].getDuree());
    // CongeMoins objet = new CongeMoins();
    // objet.setNomTable("conge_moins");
    // objet.construirePK(c);
    // objet.setMois(0);
    // objet.setAnnee(0);
    // objet.setIdpersonnel(dems[0].getIdpersonnel());
    // objet.setConge(dems[0].getDuree());
    // objet.setIddemande(dems[0].getId());
    // objet.setRefdobject(null);
    // objet.insertToTable(c);
    // }
    // }
    // return this;
    // }
    
    public String getIdSup(EmployeComplet[] emps) {
        StringBuilder ids = new StringBuilder();
        for (EmployeComplet emp : emps) {
            if (ids.length() > 0) {
                ids.append(",");
            }
            ids.append("'").append(emp.getIdsup()).append("'");
        }
        return ids.toString();
    }

    public EmployeComplet[] validateur(String idDemandeur, Connection c) throws Exception {
        List<EmployeComplet> validateurList = new ArrayList<>();
        EmployeComplet e = new EmployeComplet();
        e.setNomTable("employe_complet_nou_avecsup");

        EmployeComplet[] validateursup = (EmployeComplet[]) CGenUtil.rechercher(e, null, null, c,
                " and id = '" + idDemandeur + "'");
        EmployeComplet[] emp = (EmployeComplet[]) CGenUtil.rechercher(e, null, null, c,
                " and id in (" + this.getIdSup(validateursup) + ")");

        if (emp != null && emp.length > 0) {
            validateurList.addAll(Arrays.asList(emp));
        }
        return validateurList.toArray(new EmployeComplet[0]);
    }

    public EmployeComplet[] getSuperieurHierarchique(EmployeComplet e, Connection c) throws Exception {
        EmployeComplet ec = new EmployeComplet();
        ec.setNomTable("employe_complet_nou_avecsup");
        EmployeComplet[] superieur = null;
        if (this.getEtat() == ConstanteEtatPaie.getEtatValiderParDemandeur()) {
            superieur = this.validateur(e.getId(), c);
        } else if (this.getEtat() == ConstanteEtatPaie.getEtatValiderParCH()) {
            superieur = (EmployeComplet[]) CGenUtil.rechercher(ec, null, null, c,
                    " and idRole = '" + ConstantePaie.getRoledrcf() + "'");
        } else if (this.getEtat() == ConstanteEtatPaie.getEtatValiderParDE()) {
            superieur = (EmployeComplet[]) CGenUtil.rechercher(ec, null, null, c,
                    " and idRole in ('" + ConstantePaie.getRolese() + "' , '" + ConstantePaie.getRoledg() + "')");
        } else if (this.getEtat() == ConstanteEtatPaie.getEtatValiderParDG()) {
            superieur = (EmployeComplet[]) CGenUtil.rechercher(ec, null, null, c,
                    " and idRole = '" + ConstantePaie.getRoledrcf() + "'");
        }
        return superieur;
    }

    public EmployeComplet[] getSuperieurautorisation(EmployeComplet e, Connection c) throws Exception {
        EmployeComplet ec = new EmployeComplet();
        ec.setNomTable("employe_complet_nou_avecsup");
        EmployeComplet[] superieur = null;
        if (this.getEtat() == ConstanteEtatPaie.getEtatValiderParDemandeur()) {
            superieur = this.validateur(e.getId(), c);
        } else if (this.getEtat() == ConstanteEtatPaie.getEtatValiderParCH()) {
            superieur = (EmployeComplet[]) CGenUtil.rechercher(ec, null, null, c,
                    " and idRole = '" + ConstantePaie.getRoledrcf() + "'");
        }
        return superieur;
    }

    public static int ControlerSE(EmployeComplet emp) throws Exception {
        int i = 1;
        if (emp.getIdRole().equals(ConstantePaie.getRolese()) || emp.getIdRole().equals(ConstantePaie.getRoledg())) {
            i = 0;
        }
        return i;
    }

    public static int ControlerDRCF(EmployeComplet emp) throws Exception {
        int i = 1;
        System.out.println("=================================" + emp.getIdRole());
        if (emp.getIdRole().equals(ConstantePaie.getRoledrcf())) {
            i = 0;
        }
        return i;
    }

    public int ControlerCH(EmployeComplet emp) throws Exception {
        int i = 1;
        if (emp.getIdRole().equals(ConstantePaie.getRolech())) {
            i = 0;
        }
        return i;
    }

    public void Congee(EmployeComplet employe[], Notification notification, String u, Connection c) throws Exception {
        HistoriqueDemande historique = new HistoriqueDemande(employe[0].getId(), this.getId(),
                Utilitaire.heureCourante(), Utilitaire.dateDuJourSql());
        EmployeComplet[] boss = null;
        if (employe.length > 0 && this.getEtat() == ConstanteEtatPaie.getEtatCreer()) {
            this.controlerVisaEtatCree(employe[0], u, c);
            if (this.getIdpersonnel() != null && employe[0].getId() != null
                    && employe[0].getId().compareTo(this.getIdpersonnel()) == 0) {
                this.updateWithHistorique(historique, ConstanteEtatPaie.getEtatValiderParDemandeur(), c);
            }
        }
//        } else if (this.getEtat() < ConstanteEtatPaie.getEtatValiderParDG()
//                && DemandeJustifications.ControlerSE(employe[0]) == 0) {
//            this.updateWithHistorique(historique, ConstanteEtatPaie.getEtatValiderParDG(), c);
//            this.notifvalider(c);
//        } else if (this.getEtat() < ConstanteEtatPaie.getEtatValiderParDE()
//                && DemandeJustifications.ControlerDRCF(employe[0]) == 0) {
//            this.updateWithHistorique(historique, ConstanteEtatPaie.getEtatValiderParDE(), c);
//            // eto le conger no miala
//        }
        else if (this.getEtat() < ConstanteEtatPaie.getEtatValiderParCH()) {
            this.updateWithHistorique(historique, ConstanteEtatPaie.getEtatValiderParCH(), c);
        } else if (this.getEtat() < ConstanteEtatPaie.getEtatValiderParRH()) {
            this.updateWithHistorique(historique, ConstanteEtatPaie.getEtatValiderParRH(), c);
            this.enleverSoldeConge(u, c);
        }

//        boss = this.getSuperieurHierarchique(employe[0], c);
//        if (boss != null) {
//            if (boss.length > 0) {
//                for (int i = 0; i < boss.length; i++) {
//                    notification.creerNotification(Integer.parseInt(boss[i].getRefuser()),
//                            "Demande de Cong&eacute;e",
//                            this.getId(), "paie/demande/demande-absence-fiche.jsp", c);
//                }
//            }
//        }

        sendNotificationToSuperior(notification, c);
    }

    public void autorisation(EmployeComplet employe[], Notification notification, String u, Connection c)
            throws Exception {
        HistoriqueDemande historique = new HistoriqueDemande(employe[0].getId(), this.getId(),
                Utilitaire.heureCourante(), Utilitaire.dateDuJourSql());
        EmployeComplet[] boss = null;
        if (employe.length > 0 && this.getEtat() == ConstanteEtatPaie.getEtatCreer()) {
            this.controlerVisaEtatCree(employe[0], u, c);
            if (this.getIdpersonnel() != null && employe[0].getId() != null
                    && employe[0].getId().compareTo(this.getIdpersonnel()) == 0) {
                this.updateWithHistorique(historique, ConstanteEtatPaie.getEtatValiderParDemandeur(), c);
            }
        } else if (this.getEtat() < ConstanteEtatPaie.getEtatValiderParCH()) {
            this.updateWithHistorique(historique, ConstanteEtatPaie.getEtatValiderParCH(), c);
            this.notifvalider(c);
        } else if (this.getEtat() < ConstanteEtatPaie.getEtatValiderParRH()) {
            this.updateWithHistorique(historique, ConstanteEtatPaie.getEtatValiderParRH(), c);
            this.enleverSoldeConge(u, c);
        }
//        boss = this.getSuperieurautorisation(employe[0], c);
//        if (boss != null) {
//            if (boss.length > 0) {
//                for (int i = 0; i < boss.length; i++) {
//                    notification.creerNotification(Integer.parseInt(boss[i].getRefuser()),
//                            "Demande Autorisation abcence",
//                            this.getId(), "paie/demande/demande-absence-fiche.jsp", c);
//                }
//            }
//        }

        sendNotificationToSuperior(notification, c);
    }

    public void ControlerPermission() throws Exception {
        if (this.getDuree() > this.getReste_permission()) {
            throw new Exception("Dur&eacute;e de permission invalide");
        }
    }

    public void permission(EmployeComplet employe[], Notification notification, String u, Connection c)
            throws Exception {
        HistoriqueDemande historique = new HistoriqueDemande(employe[0].getId(), this.getId(),
                Utilitaire.heureCourante(), Utilitaire.dateDuJourSql());
        EmployeComplet[] boss = null;
        if (employe.length > 0 && this.getEtat() == ConstanteEtatPaie.getEtatCreer()) {
            this.controlerVisaEtatCree(employe[0], u, c);
            if (this.getIdpersonnel() != null && employe[0].getId() != null
                    && employe[0].getId().compareTo(this.getIdpersonnel()) == 0) {
                this.updateWithHistorique(historique, ConstanteEtatPaie.getEtatValiderParDemandeur(), c);
            }
        } else if (this.getEtat() < ConstanteEtatPaie.getEtatValiderParCH()) {
            this.updateWithHistorique(historique, ConstanteEtatPaie.getEtatValiderParCH(), c);
            this.notifvalider(c);
        } else if (this.getEtat() < ConstanteEtatPaie.getEtatValiderParRH()) {
            this.updateWithHistorique(historique, ConstanteEtatPaie.getEtatValiderParRH(), c);
            this.enleverSoldeConge(u, c);
        }
//        boss = this.getSuperieurautorisation(employe[0], c);
//        if (boss != null) {
//            if (boss.length > 0) {
//                for (int i = 0; i < boss.length; i++) {
//                    notification.creerNotification(Integer.parseInt(boss[i].getRefuser()),
//                            "Demande Permission",
//                            this.getId(), "paie/demande/demande-absence-fiche.jsp", c);
//                }
//            }
//        }

        sendNotificationToSuperior(notification, c);
    }

    @Override
    public Object validerObject(String u, Connection c) throws Exception {
        EmployeComplet ec = new EmployeComplet();
        ec.setNomTable("EMPLOYE_COMPLET2");
        ec.setId(this.getIdpersonnel());
        Notification notification = new Notification();
        EmployeComplet[] employe = (EmployeComplet[]) CGenUtil.rechercher(ec, null, null, c, "");
        if (employe.length == 0) {
            throw new Exception("Employ\\351 introuvable");
        }
        if (this.estRefusee() == true) {
            throw new Exception("Demande d\\351j\\340 refus\\351e.");
        }
        if (this.getIdtypeabsence().compareToIgnoreCase(ConstantePaie.id_typeabsenceconge) == 0) {
            this.Congee(employe, notification, u, c);
        } else if (this.getIdtypeabsence().compareToIgnoreCase(ConstantePaie.id_typeautorisationabsence) == 0) {
            this.autorisation(employe, notification, u, c);
        } else if (this.getIdtypeabsence().compareToIgnoreCase(ConstantePaie.id_typeabsencepermission) == 0) {
            this.permission(employe, notification, u, c);
        }

        return this;
    }

    public void enleverSoldeConge(String u, Connection c) {
        try {
            MouvementAbsence mvt = new MouvementAbsence();
            mvt.setIdSource(this.getIdtypeabsence());
            mvt.setIdPersonnel(this.getIdpersonnel());
            mvt.setDateDebut(this.getDatedepart());
            mvt.setMois(Utilitaire.getMois(this.getDatedepart()) - 1);
            mvt.setAnnee(Utilitaire.getAnnee(this.getDatedepart()));
            mvt.setMoins(this.getDuree());

            if (this.getRemarque() == null || this.getRemarque().isEmpty()) {
                mvt.setRemarque("Mouvement abscence du " + this.getDaty() + " de employe: " + this.getIdpersonnel());
            } else {
                mvt.setRemarque(this.getRemarque());
            }

            mvt.setMotif(this.getMotif());
            mvt.setIdRemplacant(this.getIdremplacents());
            mvt.setDateDemande(this.getDaty());
            mvt.construirePK(c);
            mvt.createObject(u, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateWithHistorique(HistoriqueDemande historique, int etatvisa, Connection c) throws Exception {
        try {
            historique.setEtatDemande(etatvisa);
            this.setEtat(etatvisa);
            historique.construirePK(c);
            this.updateToTableWithHisto(historique, c);
        } catch (Exception e) {
            throw e;
        }
    }

    public String chaineEtat(int value) {
        if (value == ConstanteEtatPaie.getEtatCreer()) {
            return "<b style=color:lightskyblue>CR&Eacute;&Eacute;(E)</b>";
        }
        if (value == ConstanteEtatPaie.getEtatValider()) {
            return "<b style=color:green>VIS&Eacute;(E)</b>";
        }
        if (value == ConstanteEtatPaie.getEtatAnnuler()) {
            return "<b style=color:orange>ANNUL&Eacute;(E)</b>";
        }
        if (value == ConstanteEtatPaie.getEtatValiderParDG()) {
            return "<b style=color:lightskyblue>VALID&Eacute;(E) PAR DG</b>";
        }
        if (value == ConstanteEtatPaie.getEtatRefuserParDG()) {
            return "<b style=color:red>REFUS&Eacute;(E) PAR DG</b>";
        }
        if (value == ConstanteEtatPaie.getEtatValiderParDE()) {
            return "<b style=color:lightskyblue>VALID&Eacute;(E) PAR DRCF</b>";
        }
        if (value == ConstanteEtatPaie.getEtatRefuserParDE()) {
            return "<b style=color:red>REFUS&Eacute;(E) PAR DRCF</b>";
        }
        if (value == ConstanteEtatPaie.getEtatValiderParCH()) {
            return "<b style=color:lightskyblue>VALID&Eacute;(E) PAR CH</b>";
        }
        if (value == ConstanteEtatPaie.getEtatRefuserParCH()) {
            return "<b style=color:red>REFUS&Eacute;(E) PAR CH</b>";
        }
        if (value == ConstanteEtatPaie.getEtatValiderParDemandeur()) {
            return "<b style=color:lightskyblue>VALID&Eacute;(E) PAR DEMANDEUR</b>";
        }
        if (value == ConstanteEtatPaie.getEtatRefuserParDemandeur()) {
            return "<b style=color:red>REFUS&Eacute;(E) PAR DEMANDEUR</b>";
        }
        if (value == ConstanteEtatPaie.getEtatValiderParRH()) {
            return "<b style=color:lightskyblue>VALID&Eacute;(E) PAR RH</b>";
        }
        if (value == ConstanteEtatPaie.getEtatRefuserParRH()) {
            return "<b style=color:red>REFUS&Eacute;(E) PAR RH</b>";
        }
        return null;
    }

    @Override
    public String toString() {
        return "DemandeJustification{" + "id=" + id + ", idpersonnel=" + idpersonnel + ", idtypedemande=" + idtypedemande + ", titre=" + titre + ", nom=" + nom + ", prenom=" + prenom + ", typeabsencelib=" + typeabsencelib + ", horairenormal=" + horairenormal + ", heuredepart=" + heuredepart + ", heurearrive=" + heurearrive + ", idtypeabsence=" + idtypeabsence + ", motif=" + motif + ", numero=" + numero + ", desce=" + desce + ", etatlib=" + etatlib + ", daty=" + daty + ", datedepart=" + datedepart + ", dateretour=" + dateretour + ", observation=" + observation + ", remarque=" + remarque + ", duree=" + duree + '}';
    }

    public String getRefuser() {
        return refuser;
    }

    public void setRefuser(String refuser) {
        this.refuser = refuser;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public int getRangUser() {
        return rangUser;
    }

    public void setRangUser(int rangUser) {
        this.rangUser = rangUser;
    }

    public void etatRefuser(EmployeComplet e) throws Exception {
        if (this.ControlerSE(e) == 0) {
            e.setEtatannule(ConstanteEtatPaie.getEtatValiderParDG() - 1);
        } else if (this.ControlerDRCF(e) == 0) {
            e.setEtatannule(ConstanteEtatPaie.getEtatValiderParDE() - 1);
        } else if (this.ControlerCH(e) == 0) {
            e.setEtatannule(ConstanteEtatPaie.getEtatValiderParCH() - 1);
        }
    }

    public String refuser(String u, Connection c) throws Exception {
        boolean ouvert = false;

        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                c.setAutoCommit(false);
                ouvert = true;
            }

            EmployeComplet ec = new EmployeComplet();
            ec.setNomTable("EMPLOYE_COMPLET2");
            EmployeComplet[] employe = (EmployeComplet[]) CGenUtil.rechercher(ec, null, null, c, " and refuser='" + u + "'");
            if (employe.length > 0) {
                DemandeJustifications demande = (DemandeJustifications) this.getById(this.getId(), this.getNomTable(), c);
                HistoriqueDemande historique = new HistoriqueDemande(employe[0].getId(), this.getId(), Utilitaire.heureCourante(), Utilitaire.dateDuJourSql());
                // demande.controleAction(employe[0], u, c);

                // int etatRefuser = getEtatRefuser(employe[0].getIdetathierarchie(), c);
                this.etatRefuser(employe[0]);
                //int etatRefuser = employe[0].getEtatannule();
                
                int etatRefuser = 2;
                if (this.getEtat() < ConstanteEtatPaie.getEtatValiderParDG() && this.ControlerSE(employe[0]) == 0) {
                    etatRefuser = ConstanteEtatPaie.getEtatRefuserParDG();
                } else if (this.getEtat() < ConstanteEtatPaie.getEtatValiderParDE() && this.ControlerDRCF(employe[0]) == 0) {
                    etatRefuser = ConstanteEtatPaie.getEtatRefuserParDE();
                } else if (this.getEtat() < ConstanteEtatPaie.getEtatValiderParCH()) {
                    etatRefuser = ConstanteEtatPaie.getEtatRefuserParCH();
                }
                
                
                historique.setEtatDemande(etatRefuser);
                demande.setEtat(etatRefuser);
                historique.construirePK(c);
                demande.updateToTableWithHisto(historique, c);
            }
            this.notifannuler(c);
            c.commit();
        } catch (Exception ex) {
            if (c != null && ouvert) {
                c.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            if (c != null && ouvert) {
                c.close();
            }
        }

        return this.getId();
    }

    public void notifannuler(Connection c) throws Exception {
        Notification notification = new Notification();
        EmployeComplet e = new EmployeComplet();
        e.setNomTable("EMPLOYE_COMPLET2");
        e.setId(this.getIdpersonnel());
        EmployeComplet[] emp = (EmployeComplet[]) CGenUtil.rechercher(e, null, null, c, "");
        notification.creerNotification(Integer.parseInt(emp[0].getRefuser()),
                "Demande de Cong&eacute;e Annul&eacute;e",
                this.getId(), "paie/demande/demande-absence-fiche.jsp", c);
    }

    public void notifvalider(Connection c) throws Exception {
//        Notification notification = new Notification();
        EmployeComplet e = new EmployeComplet();
        e.setNomTable("EMPLOYE_COMPLET2");
        e.setId(this.getIdpersonnel());
        EmployeComplet[] emp = (EmployeComplet[]) CGenUtil.rechercher(e, null, null, c, "");
//        notification.creerNotification(Integer.parseInt(emp[0].getRefuser()),
//                "Demande de validation",
//                this.getId(), "paie/demande/demande-absence-fiche.jsp", c);
    }

    public boolean estRefusee() throws Exception {
        boolean refusee = false;

        try {
            if (this.getEtat() == ConstanteEtatPaie.getEtatRefuserParCH() || this.getEtat() == ConstanteEtatPaie.getEtatRefuserParDE() || this.getEtat() == ConstanteEtatPaie.getEtatRefuserParDG() || this.getEtat() == ConstanteEtatPaie.getEtatRefuserParRH()) {
                refusee = true;
            }
        } catch (Exception e) {
            throw e;
        }

        return refusee;
    }

    public int getEtatRefuser(String idhierarchie, Connection c) throws Exception {

        try {
            EtatHierarchie etatHierarchie = (EtatHierarchie) (new EtatHierarchie()).getById(idhierarchie, "ETATHIERARCHIE", c);
            return etatHierarchie.getEtatAnnule();
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean containsId(EmployeComplet[] emps, String idToCheck) {
        if (emps == null || idToCheck == null) {
            return false;
        }
        return Arrays.stream(emps)
                .anyMatch(emp -> idToCheck.equals(emp.getId()));
    }

    public boolean estValidateurSuivant2(EmployeComplet employe, Connection c) throws Exception {
        EmployeComplet[] emps = this.validateur(this.getIdpersonnel(), c);
        return this.containsId(emps, employe.getId());
    }

    public boolean aDroitAction(EmployeComplet employe, String refuser, Connection c) throws Exception {
        return estValidateurSuivant2(employe, c);

    }

    public boolean estCloturee(Connection c) throws Exception {
        boolean estCloturee = false;
        if (this.getEtat() == 11) {
            estCloturee = true;
        }
        return estCloturee;
    }

    public boolean estValideeParDemandeur() throws Exception {
        return this.getEtat() >= ConstanteEtatPaie.getEtatValiderParDemandeur();
    }

    public boolean estPasseeParSuperieur(EmployeComplet employe) throws Exception {
        System.out.println(" this.getEtat() ==>"+ this.getEtat());
        System.out.println("employe.getEtatvisa() ===>"+employe.getEtatvisa());
        System.out.println("employe.getEtatannule() ===>"+employe.getEtatannule());
        
        
        
        return this.getEtat() >= employe.getEtatvisa() || this.getEtat() >= employe.getEtatannule();
    }

    public void controleAction(EmployeComplet employe, String u, Connection c) throws Exception {
        try {
            if (estRefusee()) {
                throw new Exception("Demande d\\351j\\340 refus\\351e.");
            }
            if (!estValideeParDemandeur()) {
                throw new Exception("En attente de validation du demandeur");
            }
            if (estCloturee(c)) {
                throw new Exception("Demande cl\\364tur\\351");
            }
            if (estPasseeParSuperieur(employe)) {
                throw new Exception("Demande d\\351j\\340 valid\\351e par votre sup\\351rieur.");
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public boolean estValidateurSuivant(EmployeComplet employe, Connection c) throws Exception {
        try {
            DemandeJustificationVise djv = new DemandeJustificationVise();
            djv.setNomTable("SITUATIONDEMANDE_SUIVANT");
            DemandeJustificationVise[] nextSituation = (DemandeJustificationVise[]) CGenUtil.rechercher(djv, null, null, c, " and id='" + this.getId() + "'");
            if (nextSituation.length > 0 && nextSituation[0].getIdEtatHierarchie().compareTo(employe.getIdetathierarchie()) == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public DemandeJustifications getRempWithId(String id, Connection c) throws Exception {
        try {
            DemandeJustifications ed = new DemandeJustifications();
            ed.setNomTable("demande_libcomplet");
            DemandeJustifications[] dem = (DemandeJustifications[]) CGenUtil.rechercher(ed, null, null, c, " and id='" + id + "'");
            if (dem.length == 0) {
                throw new Exception("Demande non trouver");
            }
            return dem[0];
        } catch (Exception e) {
            throw e;
        }
    }

    public String getIdSup() {
        return idSup;
    }

    public void setIdSup(String idSup) {
        this.idSup = idSup;
    }

    public String getAvenant() {
        return avenant;
    }

    public void setAvenant(String avenant) {
        this.avenant = avenant;
    }
    
    public String getAnnuler() {
        return annuler;
    }

    public void setAnnuler(String annuler) {
        this.annuler = annuler;
    }

    public double getReste_permission() {
        return reste_permission;
    }

    public void setReste_permission(double reste_permission) {
        this.reste_permission = reste_permission;
    }

    public double getDureefinal() {
        return dureefinal;
    }

    public void setDureefinal(double dureefinal) {
        this.dureefinal = dureefinal;
    }

    // lister demande
    public String qweryList(Date datemin, Date datemax, String idP, String etat) {
        String requette = "";
        String et = " and etat = " + etat;
        if (etat == null || etat.compareToIgnoreCase("") == 0) {
            et = "";
        }
        requette = " select " +
                "  idpersonnel,  " +
                "  matricule, " +
                "  idfonction, " +
                "  fonctionlib, " +
                "  idtypedemande, " +
                "  typedemandelib, " +
                "  titre, " +
                "  horairenormal, " +
                "  idtypeabsence, " +
                "  typeabsencelib, " +
                "  nom, " +
                "  prenom, " +
                "  refuser, " +
                "  ranguser, " +
                "  idsup, " +
                "  annuler, " +
                "  SUM(duree)::numeric(32,0) as dureefinal, " +
                "  etat, " +
                "  iddirection, " +
                "  directionlib, " +
                "  etatlib " +
                " from " +
                "  demande_libcomplet  " +
                "  where daty >= TO_Date('" + datemin + "','YYYY/MM/DD') and daty <= TO_Date('" + datemax
                + "','YYYY/MM/DD') " +
                et +
                " and ( idpersonnel = '" + idP + "' or  idSup = '" + idP + "')" +
                " group by " +
                "  idpersonnel, " +
                "  matricule, " +
                "  idfonction, " +
                "  fonctionlib, " +
                "  idtypedemande, " +
                "  typedemandelib, " +
                "  iddirection, " +
                "  directionlib, " +
                "  titre, " +
                "  horairenormal, " +
                "  nom, " +
                "  prenom, " +
                "  idsup, " +
                "  etatlib, " +
                "  refuser, " +
                "  ranguser, " +
                "  idtypeabsence, " +
                "  typeabsencelib, " +
                "  annuler, " +
                "  etat ";
        return requette;
    }

    public DemandeJustifications[] getDemandeJustifications(Date datemin, Date datemax, String idP, String etat)
            throws Exception {
        DemandeJustifications dem = new DemandeJustifications();
        dem.setNomTable("demande_vide");
        String requString = this.qweryList(datemin, datemax, idP, etat);
        System.out.println(requString);
        DemandeJustifications[] demande = (DemandeJustifications[]) CGenUtil.rechercher(dem, requString);
        return demande;
    }

    public void controllerDateFinEtRetour() throws Exception{
        this.datefin = Utilitaire.ajoutJourDate(this.datedepart, (int)Math.ceil(this.duree));
        while(!Utilitaire.estJourOuvrable(this.datefin)){
            this.datefin = Utilitaire.ajoutJourDate(this.datefin,1);
        }
        this.dateretour = this.datefin;
    }

    @Override
    public ClassMAPTable createObject(String user, Connection c) throws Exception {
        this.controllerDateFinEtRetour();
        return super.createObject(user, c);
    }

    @Override
    public int updateToTableWithHisto(String refUser, Connection c) throws Exception {
        this.controllerDateFinEtRetour();
        return super.updateToTableWithHisto(refUser, c);
    }
    public double getAvantResteconge() throws SQLException, Exception{
          MouvementAbsence mvt = new MouvementAbsence();
          mvt.setIdPersonnel(this.getIdpersonnel());
          double resteConge = mvt.getSoldeCongePers();
          return resteConge;
//        Conge conge = new Conge();
//        conge.setNomTable("conge_vide");
//        conge.setDaty(Utilitaire.stringDate(Utilitaire.dateDuJour()));
//        Conge[] value = conge.getCongeByDate(this.getIdpersonnel());
//        return value[0].getCongereste();
    }

    public double getApresResteconge() throws SQLException, Exception{
        double reste = this.getAvantResteconge() - this.getDuree();
        return reste;
    }

    public void sendNotificationToSuperior(Notification notification, Connection c)
    {
        try {
            int refRH = 253;
            int[] refManager = { 233, 327, 342 };
            int refSuperAdmin = 1060;

            // notif RH
            notification.creerNotification(refRH,"Nouvelle Demande", this.getId(), "paie/demande/demande-absence-fiche.jsp", c);

            // notif Manager
            for(int ref: refManager) {
                notification.creerNotification(ref,"Nouvelle Demande", this.getId(), "paie/demande/demande-absence-fiche.jsp", c);
            }

            // notif SuperAdmin
            notification.creerNotification(refSuperAdmin,"Nouvelle Demande", this.getId(), "paie/demande/demande-absence-fiche.jsp", c);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
