/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faturefournisseur;

import bean.ClassMAPTable;
import java.sql.Connection;

import mg.cnaps.compta.ComptaCompte;
import mg.cnaps.compta.ConstanteCompta;
import pertegain.Tiers;
import utilitaire.Utilitaire;

/**
 *
 * @author nouta
 */
public class Fournisseur extends Tiers{
    protected String contact,codePostal, idTypeFournisseur;

    public Fournisseur() {
        super.setNomTable("FOURNISSEUR");
    }

    public String getIdTypeFournisseur() {
        return idTypeFournisseur;
    }

    public void setIdTypeFournisseur(String idTypeFournisseur) {
        this.idTypeFournisseur = idTypeFournisseur;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

     @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("FRN", "GETSEQFOURNISSEUR");
        this.setId(makePK(c));
    }
 
    @Override
    public String getValColLibelle() {
        return this.getNom();
    }

    @Override
    public ClassMAPTable createObject(String u, Connection c) throws Exception {
        try {
            int maxSeq = Utilitaire.getMaxSeq("getseqCompteFournisseur", c);
            String nombre = Utilitaire.completerInt(5, maxSeq);
            this.setCompteauxiliaire(nombre);
            this.setCompte(this.getCompte()+nombre);
            ComptaCompte comptaCompte = new ComptaCompte();
            comptaCompte.setCompte(this.getCompte());
            comptaCompte.setLibelle(this.getNom().toUpperCase());
            comptaCompte.setClasse("1");
            comptaCompte.setTypeCompte("4");
            comptaCompte.setIdjournal(ConstanteCompta.journalFournisseur);
            comptaCompte.createObject(u,c);
            return super.createObject(u, c);
        }catch(Exception e){
            throw new Exception("Erreur Creation Fournisseur :"+e.getMessage());
        }
    }
  
}
