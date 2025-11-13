/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import bean.CGenUtil;
import bean.ClassFille;
import utilitaire.UtilDB;

import java.sql.Connection;

/**
 *
 * @author 26134
 */
public class TransfertStockDetails extends ClassFille{
String id,idTransfertStock,idProduit,remarque, idSource;
double quantite, pu;

    public TransfertStockDetails(){
        try {
            this.setLiaisonMere("idTransfertStock");
            this.setNomTable("TRANSFERTSTOCKDETAILS");
            this.setNomClasseMere("stock.TransfertStock");
        } catch (Exception e) {
            System.out.println("error stock.TransfertStockDetails.<init>()");
        }
    }
//
//    @Override
//    public String getNomClasseMere() {
//        return "stock.TransfertStock";
//    }
//
//    @Override
//    public String getLiaisonMere() {
//        return "idTransfertStock";
//    }

    public double getPu() {
        return pu;
    }

    public void setPu(double pu) {
        this.pu = pu;
    }

    public String getIdTransfertStock() {
        return idTransfertStock;
    }

    public void setIdTransfertStock(String idTransfertStock) {
        this.idTransfertStock = idTransfertStock;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public String getIdSource() {
        return idSource;
    }

    public void setIdSource(String idSource) {
        this.idSource = idSource;
    }

    public MvtStockFille createMvtStockFille(boolean isEntree) throws Exception {
        MvtStockFille msf=new MvtStockFille();
        msf.setIdProduit(this.getIdProduit());
        msf.setIdTransfertDetail(this.getId());
        if (isEntree) {
            msf.setEntree(quantite);
        }else{
            msf.setSortie(quantite);
            msf.setMvtSrc(this.idSource);
        }
        msf.setPu(this.getPu());
        return msf;
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("TSD", "GETSEQTRANSFERTSTOCKDETAILS");
        this.setId(makePK(c));
    }
    @Override
    public String getTuppleID() {
        return id;
    }

    @Override
    public String getLiaisonMere() {
        return "idTransfertStock";
    }
    @Override
    public String getAttributIDName() {
        return "id";
    }
    @Override
    public String getNomClasseMere() {
        return "stock.TransfertStock";
    }

    protected void checkQuantiteProduit(Connection c) throws Exception{
        TransfertStock[] tsd=(TransfertStock[]) CGenUtil.rechercher(new TransfertStock(), null, null, c, " and id='"+this.getIdTransfertStock()+"' ");
        if (tsd.length!=1) {
            throw new Exception("transfertStock introuvable");
        }
        EtatStock es = new EtatStock();
        es.setNomTable("V_ETATSTOCK_ING");
        EtatStock[] et=(EtatStock[]) CGenUtil.rechercher(es, null, null, c, " and id='"+this.getIdProduit()+"' and idmagasin='"+tsd[0].getIdMagasinDepart()+"' ");
        if (et.length==0) {
            throw new Exception(String.format("ingredient %s introuvable dans stock", this.getIdProduit()));
        }
        if ( et[0].getReste()< this.getQuantite()) {
            throw new Exception(String.format("ingredient %s insuffisant", this.getIdProduit()));
        }
    }

    protected void checkMVTSource() throws Exception{
        if(this.getIdSource() == null || this.getIdSource().isEmpty()){
            throw new Exception("Vous devez choisir une source!");
        }
    }

    protected void controllerMvtSrc(Connection c) throws Exception{
        boolean estOuvert = false;
        try {
            if (c == null) {
                estOuvert = true;
                c = new UtilDB().GetConn();
            }
            if(this.getIdSource() != null && !this.getIdSource().isEmpty()){
                MvtStockEntreeAvecReste map = new MvtStockEntreeAvecReste();
                MvtStockEntreeAvecReste rep = (MvtStockEntreeAvecReste) map.getById(this.getIdSource(), "V_ETATSTOCK_ENTREE", c);
                if(rep != null) {
                    if(this.getIdProduit().compareToIgnoreCase(rep.getIdProduit()) != 0){
                        throw new Exception("Le produit dans le mouvement source "+ rep.getId()+" ne correspondant pas au produit dans " + this.getIdSource());
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null && estOuvert == true) {
                c.close();
            }
        }
    }

    @Override
    public void controler(Connection c) throws Exception {
        this.checkQuantiteProduit(c);
        this.checkMVTSource();
        this.controllerMvtSrc(c);
    }


}
