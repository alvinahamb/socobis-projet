/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;

import bean.TypeObjet;
import mg.cnaps.compta.ComptaCompte;
import mg.cnaps.compta.ConstanteCompta;
import pertegain.Tiers;
import utilitaire.Utilitaire;

/**
 *
 * @author SAFIDY
 */
public class Client extends Tiers{

    private String telephone;

    private String remarque;

    private double echeance;

    private String idTypeClient;

    private String telfixe;
    private double taxe;
    private String idprovince;
    private String nif;
    private String stat;
    private String carte;
    private Date datecarte;
    private String codeclient;

    public String getCodeclient() {
        return codeclient;
    }

    public void setCodeclient(String codeclient) {
        this.codeclient = codeclient;
    }

    public String getTelfixe() {
        return telfixe;
    }

    public void setTelfixe(String telfixe) {
        this.telfixe = telfixe;
    }

    public double getTaxe() {
        return taxe;
    }

    public void setTaxe(double taxe) {
        this.taxe = taxe;
    }

    public String getIdprovince() {
        return idprovince;
    }

    public void setIdprovince(String idprovince) {
        this.idprovince = idprovince;
    }

    @Override
    public String getNif() {
        return nif;
    }

    @Override
    public void setNif(String nif) {
        this.nif = nif;
    }

    @Override
    public String getStat() {
        return stat;
    }

    @Override
    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getCarte() {
        return carte;
    }

    public void setCarte(String carte) {
        this.carte = carte;
    }

    public Date getDatecarte() {
        return datecarte;
    }

    public void setDatecarte(Date datecarte) {
        this.datecarte = datecarte;
    }

    public String getIdTypeClient() {
        return idTypeClient;
    }

    public void setIdTypeClient(String idTypeClient) {
        this.idTypeClient = idTypeClient;
    }

    public double getEcheance() {
        return echeance;
    }

    public void setEcheance(double echeance) {
        this.echeance = echeance;
    }

    public Client(){
         this.setNomTable("CLIENT");
    }
    

   

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

   

   

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

   
    

    
    
    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CLI", "getSeqClient");
        this.setId(makePK(c));
    }
    
    
    @Override
    public String getValColLibelle() {
        return this.getNom();
    }

    @Override
    public ClassMAPTable createObject(String u, Connection c) throws Exception {
        try {
            int maxSeq = Utilitaire.getMaxSeq("getseqCompteClient", c);
            String nombre = Utilitaire.completerInt(5, maxSeq);
            this.setCompteauxiliaire(nombre);
            this.setCompte(this.getCompte()+nombre);
            ComptaCompte comptaCompte = new ComptaCompte();
            comptaCompte.setCompte(this.getCompte());
            comptaCompte.setLibelle(this.getNom().toUpperCase());
            comptaCompte.setClasse("1");
            comptaCompte.setTypeCompte("4");
            comptaCompte.setIdjournal(ConstanteCompta.journalClient);
            comptaCompte.createObject(u,c);
            return super.createObject(u, c);
        }catch(Exception e){
            throw new Exception("Erreur Creation Client :"+e.getMessage());
        }
    }
}
