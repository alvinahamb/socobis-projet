package caisse;
import java.sql.Connection;

import bean.ClassMere;

public class MouvementCaisseMere extends ClassMere{
    String id,designation,idmodepaiement,idtiers,idorigine,idtierslib,etatlib;
    double credit,debit;
    java.sql.Date daty;
    public MouvementCaisseMere() throws Exception {
        this.setNomTable("mouvementcaissemere");
        setLiaisonFille("idmvtcaissemere");
        setNomClasseFille("caisse.MouvementCaisseFille");
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("MVTM", "GETSEQMOUVEMENTCAISSEMERE");
        this.setId(makePK(c));
    }
    @Override
    public String getTuppleID() {
        return id;
    }

    public  String getNomClasseFille()
    {
        return "caisse.MouvementCaisseFille";
    }
    public String getLiaisonFille() {
        return "idmvtcaissemere";
    }
    @Override
    public String getAttributIDName() {
        return "id";
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getIdmodepaiement() {
        return idmodepaiement;
    }
    public void setIdmodepaiement(String idmodepaiement) {
        this.idmodepaiement = idmodepaiement;
    }
    public String getIdtiers() {
        return idtiers;
    }
    public void setIdtiers(String idtiers) {
        this.idtiers = idtiers;
    }
    public String getIdorigine() {
        return idorigine;
    }
    public void setIdorigine(String idorigine) {
        this.idorigine = idorigine;
    }
    public String getIdtierslib() {
        return idtierslib;
    }
    public void setIdtierslib(String idtierslib) {
        this.idtierslib = idtierslib;
    }
    public String getEtatlib() {
        return etatlib;
    }
    public void setEtatlib(String etatlib) {
        this.etatlib = etatlib;
    }
    public double getCredit() {
        return credit;
    }
    public void setCredit(double credit) {
        this.credit = credit;
    }
    public double getDebit() {
        return debit;
    }
    public void setDebit(double debit) {
        this.debit = debit;
    }
    public java.sql.Date getDaty() {
        return daty;
    }
    public void setDaty(java.sql.Date daty) {
        this.daty = daty;
    }
    
}
