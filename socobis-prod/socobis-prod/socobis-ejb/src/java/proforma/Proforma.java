package proforma;

import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ClassMere;
import produits.Historique;
import produits.Ingredients;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;
import vente.BonDeCommande;
import historique.MapUtilisateur;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Proforma extends ClassMere{
    private String id,designation,idMagasin,remarque,idOrigine,idClient,idReservation,echeance,reglement, idDevise;
    private Date daty,datyPrevu, dateprevres;
    private int etat,estPrevu;
    private double tva,remise,caution;
    private String lieulocation;

    public String getIdDevise() {
        return idDevise;
    }

    public void setIdDevise(String idDevise) {
        this.idDevise = idDevise;
    }

    public double getCaution() {
        return caution;
    }
    public void setCaution(double caution) {
        this.caution = caution;
    }

    public String getLieulocation() {
        return lieulocation;
    }

    public void setLieulocation(String lieulocation) {
        this.lieulocation = lieulocation;
    }

    public double getRemise() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }

    public Proforma getProforma(Connection c) throws Exception{
        Proforma proforma = (Proforma) this.getById(this.getId(), "PROFORMA", c);
        return proforma;
    }

    public Date getDateprevres() {
        return dateprevres;
    }

    public void setDateprevres(Date dateprevres) {
        this.dateprevres = dateprevres;
    }

    public Proforma()throws Exception{
        this.setNomTable("PROFORMA");
        this.setLiaisonFille("idProforma");
        this.setNomClasseFille("proforma.ProformaDetails");
    }
    @Override
    public String getTuppleID() {
        return this.id;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("PROF", "getSeqProforma");
        this.setId(makePK(c));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(String idMagasin) {
        this.idMagasin = idMagasin;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getIdOrigine() {
        return idOrigine;
    }

    public void setIdOrigine(String idOrigine) {
        this.idOrigine = idOrigine;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
    }

    public String getEcheance() {
        return echeance;
    }

    public void setEcheance(String echeance) {
        this.echeance = echeance;
    }

    public String getReglement() {
        return reglement;
    }

    public void setReglement(String reglement) {
        this.reglement = reglement;
    }

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public Date getDatyPrevu() {
        return datyPrevu;
    }

    public void setDatyPrevu(Date datyPrevu) {
        this.datyPrevu = datyPrevu;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getEstPrevu() {
        return estPrevu;
    }

    public void setEstPrevu(int estPrevu) {
        this.estPrevu = estPrevu;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }
    @Override
    public String getNomClasseFille() {
        return ("proforma.ProformaDetails");
    }

    @Override
    public String getLiaisonFille() {
        return "idProforma"  ;
    }
    public ProformaDetails[] getFilleProforma()throws Exception{
        ProformaDetails profD = new ProformaDetails();
        profD.setIdProforma(this.getId());
        ProformaDetails[] val= (ProformaDetails[])CGenUtil.rechercher(profD, null, null, "");
        return val;
    }
    public ProformaDetailsLib[] getFilleProformaLib()throws Exception{
        ProformaDetailsLib profD = new ProformaDetailsLib();
        profD.setIdProforma(this.getId());
        ProformaDetailsLib[] val= (ProformaDetailsLib[])CGenUtil.rechercher(profD, null, null, "");
        return val;
    }
    public BonDeCommande createBonDeCommande()throws Exception{
        try {
            Proforma proforma = new Proforma();
            proforma.setId(this.getId());
            Proforma[] resultats = (Proforma[]) CGenUtil.rechercher(proforma, null, null, "");
            if(resultats.length > 0) {
                proforma = resultats[0];
                BonDeCommande bd = new BonDeCommande();
                if (proforma.getIdClient() != null) {
                    bd.setIdClient(proforma.getIdClient());
                }
                if (proforma.getIdMagasin() != null) {
                    bd.setIdMagasin(proforma.getIdMagasin());
                }
                if (proforma.getIdDevise() != null) {
                    bd.setIdDevise(proforma.getIdDevise());
                }
                bd.setIdProforma(proforma.getId());
                return bd;
            }else {
                throw new Exception("La proforma n'existe pas");
            }
        }catch(Exception e){
            throw e;
        }
    }


}
