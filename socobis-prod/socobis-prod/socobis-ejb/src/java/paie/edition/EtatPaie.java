package paie.edition;

import bean.CGenUtil;
import bean.ClassMAPTable;

import java.sql.Connection;

public class EtatPaie extends ClassMAPTable {

    private String id;
    private String personnel;
    private String fonction;
    private String matricule;
    private double heureMois;
    private double heureLegal;
    private double heurePresence;
    private double tauxHoraire;
    private double salaire;
    private double salaireMois;
    private double heureSup;
    private double rappel;
    private double retenuAbsc;
    private double prime;
    private double congePayer;
    private double preavis;
    private double indamDivFixe;
    private double salaireBrute;
    private double cnaps;
    private double ostie;
    private double netImposable;
    private double netImposableArr;
    private double irsa;
    private int nbEnfant;
    private double irsaNet;
    private double avanceSalaire;
    private double avanceExcep;
    private double allocation;
    private double netPayer;
    private double netPayerArr;
    private int annee;
    private int mois;

    public EtatPaie() {
        this.setNomTable("SITUATION_SALAIRE_EDITION");
    }

    public EtatPaie[] getAllEtatPaies() throws Exception {
        EtatPaie[] all = (EtatPaie[]) CGenUtil.rechercher(this, null, null, " ");
        return all;
    }

    @Override
    public String getTuppleID() {
        return id;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public double getHeureMois() {
        return heureMois;
    }

    public void setHeureMois(double heureMois) {
        this.heureMois = heureMois;
    }

    public double getHeureLegal() {
        return heureLegal;
    }

    public void setHeureLegal(double heureLegal) {
        this.heureLegal = heureLegal;
    }

    public double getHeurePresence() {
        return heurePresence;
    }

    public void setHeurePresence(double heurePresence) {
        this.heurePresence = heurePresence;
    }

    public double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public double getSalaireMois() {
        return salaireMois;
    }

    public void setSalaireMois(double salaireMois) {
        this.salaireMois = salaireMois;
    }

    public double getHeureSup() {
        return heureSup;
    }

    public void setHeureSup(double heureSup) {
        this.heureSup = heureSup;
    }

    public double getRappel() {
        return rappel;
    }

    public void setRappel(double rappel) {
        this.rappel = rappel;
    }

    public double getRetenuAbsc() {
        return retenuAbsc;
    }

    public void setRetenuAbsc(double retenuAbsc) {
        this.retenuAbsc = retenuAbsc;
    }

    public double getPrime() {
        return prime;
    }

    public void setPrime(double prime) {
        this.prime = prime;
    }

    public double getCongePayer() {
        return congePayer;
    }

    public void setCongePayer(double congePayer) {
        this.congePayer = congePayer;
    }

    public double getPreavis() {
        return preavis;
    }

    public void setPreavis(double preavis) {
        this.preavis = preavis;
    }

    public double getIndamDivFixe() {
        return indamDivFixe;
    }

    public void setIndamDivFixe(double indamDivFixe) {
        this.indamDivFixe = indamDivFixe;
    }

    public double getSalaireBrute() {
        return salaireBrute;
    }

    public void setSalaireBrute(double salaireBrute) {
        this.salaireBrute = salaireBrute;
    }

    public double getCnaps() {
        return cnaps;
    }

    public void setCnaps(double cnaps) {
        this.cnaps = cnaps;
    }

    public double getOstie() {
        return ostie;
    }

    public void setOstie(double ostie) {
        this.ostie = ostie;
    }

    public double getNetImposable() {
        return netImposable;
    }

    public void setNetImposable(double netImposable) {
        this.netImposable = netImposable;
    }

    public double getNetImposableArr() {
        return netImposableArr;
    }

    public void setNetImposableArr(double netImposableArr) {
        this.netImposableArr = netImposableArr;
    }

    public double getIrsa() {
        return irsa;
    }

    public void setIrsa(double irsa) {
        this.irsa = irsa;
    }

    public int getNbEnfant() {
        return nbEnfant;
    }

    public void setNbEnfant(int nbEnfant) {
        this.nbEnfant = nbEnfant;
    }

    public double getIrsaNet() {
        return irsaNet;
    }

    public void setIrsaNet(double irsaNet) {
        this.irsaNet = irsaNet;
    }

    public double getAvanceSalaire() {
        return avanceSalaire;
    }

    public void setAvanceSalaire(double avanceSalaire) {
        this.avanceSalaire = avanceSalaire;
    }

    public double getAvanceExcep() {
        return avanceExcep;
    }

    public void setAvanceExcep(double avanceExcep) {
        this.avanceExcep = avanceExcep;
    }

    public double getAllocation() {
        return allocation;
    }

    public void setAllocation(double allocation) {
        this.allocation = allocation;
    }

    public double getNetPayer() {
        return netPayer;
    }

    public void setNetPayer(double netPayer) {
        this.netPayer = netPayer;
    }

    public double getNetPayerArr() {
        return netPayerArr;
    }

    public void setNetPayerArr(double netPayerArr) {
        this.netPayerArr = netPayerArr;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }
}