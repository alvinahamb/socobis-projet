/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vente;

/**
 *
 * @author Angela
 */
public class VenteLib extends Vente{
    private String idMagasinLib,idOrigine;
    private String etatLib;
    private double montanttotal;
    private String idDevise;
    private String idClientLib;
    private double montantpaye;
    private double montantreste;
    private double montantttc;
    double montantTtcAr;
    protected double avoir, montantremise,montant,poids;
    private String referencefacture,modepaiementlib;
    private int mois, annee;
    private String modelivraisonlib, idprovince, provincelib, livraison;
    private double frais;
    private double colis;

    public String getLivraison() {
        return livraison;
    }

    public void setLivraison(String livraison) {
        this.livraison = livraison;
    }

    public double getFrais() {
        return frais;
    }

    public void setFrais(double frais) {
        this.frais = frais;
    }

    public String getModelivraisonlib() {
        return modelivraisonlib;
    }

    public void setModelivraisonlib(String modelivraisonlib) {
        this.modelivraisonlib = modelivraisonlib;
    }

    public String getModepaiementlib() {
        return modepaiementlib;
    }

    public void setModepaiementlib(String modepaiementlib) {
        this.modepaiementlib = modepaiementlib;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double getMontantremise() {
        return montantremise;
    }

    public void setMontantremise(double montantremise) {
        this.montantremise = montantremise;
    }

    public String getIdOrigine() {
        return idOrigine;
    }

    public void setIdOrigine(String idOrigine) {
        this.idOrigine = idOrigine;
    }
    

    public double getAvoir() {
        return avoir;
    }

    public void setAvoir(double avoir) {
        this.avoir = avoir;
    }

    public String getDesignation() {
        return designation;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public String getIdprovince() {
        return idprovince;
    }

    public void setIdprovince(String idprovince) {
        this.idprovince = idprovince;
    }

    public String getProvincelib() {
        return provincelib;
    }

    public void setProvincelib(String provincelib) {
        this.provincelib = provincelib;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public double getMontantTtcAr() {
        return montantTtcAr;
    }

    public void setMontantTtcAr(double montantTtcAr) {
        this.montantTtcAr = montantTtcAr;
    }

    public double getMontantttc() {
        return montantttc;
    }

    public void setMontantttc(double montantttc) {
        this.montantttc = montantttc;
    }
        

    public void setMontantpaye(double montantpaye) {
        this.montantpaye = montantpaye;
    }

    public double getMontantpaye() {
        return montantpaye;
    }

    public void setMontantreste(double montantreste) {
        this.montantreste = montantreste;
    }

    public double getMontantreste() {
        return montantreste;
    }

    public String getIdClientLib() {
        return idClientLib;
    }

    public void setIdClientLib(String idClientLib) {
        this.idClientLib = idClientLib;
    }

    public String getIdDevise() {
        return idDevise;
    }

    public void setIdDevise(String idDevise) {
        this.idDevise = idDevise;
    }

    public double getMontanttotal() {
        return montanttotal;
    }

    public void setMontanttotal(double montanttotal) {
        this.montanttotal = montanttotal;
    }

    public VenteLib() {
        this.setNomTable("VENTE_CPL");
    }

    public String getIdMagasinLib() {
        return idMagasinLib;
    }

    public void setIdMagasinLib(String idMagasinLib) {
        this.idMagasinLib = idMagasinLib;
    }

    public String getEtatLib() {
        return etatLib;
    }

    public String getChaineEtat(){
        return chaineEtat(this.getEtat());
    }

    public void setEtatLib(String etatLib) {
        this.etatLib = etatLib;
    }

    public String getReferencefacture() {
        return referencefacture;
    }

    public void setReferencefacture(String referencefacture) {
        this.referencefacture = referencefacture;
    }
    public double getColis() {
        return colis;
    }

    public void setColis(double colis) {
        this.colis = colis;
    }
}
