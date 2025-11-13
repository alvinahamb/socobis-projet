package caisse;
import java.sql.Date;

import bean.ClassFille;
import facture.tr.Traite;
public class MouvementCaisseFille extends ClassFille {
    private String id,designation,idCaisse,idVenteDetail,idVirement,idOp,idOrigine, idtraite,idTiers;
    protected String idDevise;
    private double debit,credit,taux;
    private Date daty;
    private String idPrevision;
    private String compte;
    private String idModePaiement;
    private int etatversement;
    private Traite traite;
    private String reference;
    private String idmvtcaissemere;
    public MouvementCaisseFille() throws Exception {
        this.setNomTable("mouvementcaisse");
        setLiaisonMere("idmvtcaissemere");
        setNomClasseMere("caisse.MouvementCaisseMere");
    }
     @Override
    public String getNomClasseMere()
    {
        return "caisse.MouvementCaisseMere";
    }
    public String getLiaisonMere() {
        return "idmvtcaissemere";
    }
    @Override
    public String getAttributIDName() {
        return "id";
    }
    @Override
    public String getTuppleID() {
        return id;
    }
     @Override
    public void construirePK(java.sql.Connection c) throws Exception {  
        this.preparePk("MVTF", "GETSEQMOUVEMENTCAISSE");
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
    public String getIdCaisse() {
        return idCaisse;
    }
    public void setIdCaisse(String idCaisse) {
        this.idCaisse = idCaisse;
    }
    public String getIdVenteDetail() {
        return idVenteDetail;
    }
    public void setIdVenteDetail(String idVenteDetail) {
        this.idVenteDetail = idVenteDetail;
    }
    public String getIdVirement() {
        return idVirement;
    }
    public void setIdVirement(String idVirement) {
        this.idVirement = idVirement;
    }
    public String getIdOp() {
        return idOp;
    }
    public void setIdOp(String idOp) {
        this.idOp = idOp;
    }
    public String getIdOrigine() {
        return idOrigine;
    }
    public void setIdOrigine(String idOrigine) {
        this.idOrigine = idOrigine;
    }
    public String getIdtraite() {
        return idtraite;
    }
    public void setIdtraite(String idtraite) {
        this.idtraite = idtraite;
    }
    public String getIdTiers() {
        return idTiers;
    }
    public void setIdTiers(String idTiers) {
        this.idTiers = idTiers;
    }
    public String getIdDevise() {
        return idDevise;
    }
    public void setIdDevise(String idDevise) {
        this.idDevise = idDevise;
    }
    public double getDebit() {
        return debit;
    }
    public void setDebit(double debit) {
        this.debit = debit;
    }
    public double getCredit() {
        return credit;
    }
    public void setCredit(double credit) {
        this.credit = credit;
    }
    public double getTaux() {
        return taux;
    }
    public void setTaux(double taux) {
        this.taux = taux;
    }
    public Date getDaty() {
        return daty;
    }
    public void setDaty(Date daty) {
        this.daty = daty;
    }
    public String getIdPrevision() {
        return idPrevision;
    }
    public void setIdPrevision(String idPrevision) {
        this.idPrevision = idPrevision;
    }
    public String getCompte() {
        return compte;
    }
    public void setCompte(String compte) {
        this.compte = compte;
    }
    public String getIdModePaiement() {
        return idModePaiement;
    }
    public void setIdModePaiement(String idModePaiement) {
        this.idModePaiement = idModePaiement;
    }
    public int getEtatversement() {
        return etatversement;
    }
    public void setEtatversement(int etatversement) {
        this.etatversement = etatversement;
    }
    public Traite getTraite() {
        return traite;
    }
    public void setTraite(Traite traite) {
        this.traite = traite;
    }
    public String getIdmvtcaissemere() {
        return idmvtcaissemere;
    }
    public void setIdmvtcaissemere(String idmvtcaissemere) {
        this.idmvtcaissemere = idmvtcaissemere;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }

}
