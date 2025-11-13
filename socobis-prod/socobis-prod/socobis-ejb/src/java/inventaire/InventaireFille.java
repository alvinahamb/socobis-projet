/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventaire;

import bean.CGenUtil;
import bean.ClassFille;
import bean.ClassMAPTable;
import caisse.EtatCaisse;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import magasin.Magasin;
import produits.Ingredients;
import stock.EtatStock;
import stock.EtatStockParEntree;
import stock.MvtStock;
import stock.MvtStockFille;
import utilitaire.Utilitaire;
import utils.ConstanteStation;

public class InventaireFille extends ClassFille {

    private String id, idInventaire, idProduit, explication,idJauge, mvtsrc;
    public String getMvtsrc() {
        return mvtsrc;
    }

    public void setMvtsrc(String mvtsrc) {
        this.mvtsrc = mvtsrc;
    }

    private double quantiteTheorique, quantite, pu;

    public double getPu() {
        return pu;
    }

    public void setPu(double pu) {
        this.pu = pu;
    }

    public InventaireFille() throws Exception {
        this.setNomTable("InventaireFille");
        this.setLiaisonMere("idInventaire");
        this.setNomClasseMere("inventaire.Inventaire");
    }

    public String getLiaisonMere() {
        return "idInventaire";
    }
    public String getNomClasseMere() {
        return "inventaire.Inventaire";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdInventaire() {
        return idInventaire;
    }

    public void setIdInventaire(String idInventaire) {
        this.idInventaire = idInventaire;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public String getExplication() {
        return explication;
    }

    public void setExplication(String explication) {
        this.explication = explication;
    }

    public double getQuantiteTheorique() {
        return quantiteTheorique;
    }

    public void setQuantiteTheorique(double quantiteTheorique) {
        this.quantiteTheorique = quantiteTheorique;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("IVTFI", "GETSEQinventairefille");
        this.setId(makePK(c));
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
    public void setLiaisonMere(String liaisonMere) {
        super.setLiaisonMere("idInventaire");
    }

    public String getIdJauge() {
        return idJauge;
    }

    public void setIdJauge(String idJauge) {
        this.idJauge = idJauge;
    }

    @Override
    public void controlerUpdate(Connection c) throws Exception {
        super.setNomClasseMere("inventaire.Inventaire");
        super.controlerUpdate(c);
    }

    public Inventaire geInventaire(Connection c) throws Exception {
        if (c == null) {
            throw new Exception("Connection non etablie");
        }
        Inventaire inventaire = new Inventaire();
        inventaire.setId(this.getIdInventaire());
        Inventaire[] inventaires = (Inventaire[]) CGenUtil.rechercher(inventaire, null, null, c, " ");
        if (inventaires.length > 0) {
            return inventaires[0];
        }
        return null;
    }

    protected void  controlerDuplicationInventaire(Connection c) throws Exception {
        if (c == null) {
            throw new Exception("Connection non etablie");
        }
        Inventaire inv=geInventaire(c);
        InventaireFilleCpl invf = new InventaireFilleCpl();
        invf.setNomTable("INVENTAIRE_FILLE_CPL_VALIDE");
        invf.setIdProduit(this.getIdProduit());
        invf.setIdMagasin(inv.getIdMagasin());
        InventaireFilleCpl[] invfcpl = (InventaireFilleCpl[]) CGenUtil.rechercher(invf, null, null, c, " AND daty = TO_DATE('"+inv.getDaty()+"', 'YYYY-MM-DD') ");
        if (invfcpl.length > 0) {
            throw new Exception("Un inventaire pour ce produit a d\u00E9j\u00E0 \u00E9t\u00E9 valid\u00E9e pour la m\u00EAme date");
        }
    }

    protected void  controlerMagasTypeReservoir(Connection c) throws Exception {
        if (c == null) {
            throw new Exception("Connection non etablie");
        }
        Inventaire inv=geInventaire(c);
        Magasin magasin = inv.getMagasin(c);
        if (magasin.getIdTypeMagasin().compareToIgnoreCase(ConstanteStation.idTypeReservoir) == 0) {
            if (magasin.getIdProduit().compareToIgnoreCase(this.getIdProduit()) != 0) {
                throw new Exception("Un ou plusieurs produits filles ne correspondent pas au produit du magasin choisi");
            }
        }
    }

    @Override
    public void controler(Connection c) throws Exception {
        this.controlerDuplicationInventaire(c);
        // this.modifQuantiteTheorique(c);
        super.controler(c);
    }

    protected void calculateQuantiteTheorique(Connection c) throws Exception{
        Inventaire inv=geInventaire(c);
        Date daty=inv.getDaty();
        EtatStock et=new EtatStock();
        Inventaire[] mere=(Inventaire[])CGenUtil.rechercher(new Inventaire(), null, null, c, " and id ='"+idInventaire+"' ");
        if (mere.length!=1) {
            throw new Exception("Inventaire introuvable");
        }
        String query=et.generateQueryCore(daty, daty)+" and inv.idProduit='"+this.idProduit+"' and inv.idmagasin='"+mere[0].getIdMagasin()+"' ";
        // System.out.println("-+ query= "+query);
        EtatStock[] listetat=(EtatStock[]) CGenUtil.rechercher(et, query, c);
        double mt=0;
        if (listetat.length==1) {
            mt=listetat[0].getReste();
        }
        this.setQuantiteTheorique(mt);
    }

    @Override
    public ClassMAPTable createObject(String u, Connection c) throws Exception {
        return super.createObject(u, c);
    }



    public void modifQuantiteTheorique(Connection c) throws Exception{
        EtatStock e = new EtatStock();
        Inventaire mere = (Inventaire) this.findMere("INVENTAIRE", c);
        e.setNomTable("V_ETATSTOCK_PU");
        e.setId(this.getIdProduit());
        e.setIdMagasin(mere.getIdMagasin());
        EtatStock[] etatstock = (EtatStock[]) CGenUtil.rechercher(e,null,null," and pu="+this.getPu());
        if (etatstock==null || etatstock.length==0) {
            this.setQuantiteTheorique(0);
        }else{
            this.setQuantiteTheorique(etatstock[0].getReste());
            this.setMvtsrc(etatstock[0].getMvtsrc());
        }
    }

    public double getEcart() throws Exception{
        double ecart = this.getQuantite() - this.getQuantiteTheorique();
        return ecart;
    }

    public List<MvtStockFille> genererMvtStockFilles(Connection c, MvtStock mere,double ecart) throws Exception{
        List<MvtStockFille> retour = new ArrayList<>();
        EtatStockParEntree ref = new EtatStockParEntree();
        ref.setIdProduit(this.getIdProduit());
        ref.setIdMagasin(mere.getIdMagasin());
        EtatStockParEntree [] etatStocks = (EtatStockParEntree[]) CGenUtil.rechercher(ref,null,null,c," and PU="+this.getPu()+" ORDER BY DATY ASC");
        if (ecart>0) {
            MvtStockFille mvtStockFille = new MvtStockFille();
            mvtStockFille.setIdMvtStock(mere.getId());
            mvtStockFille.setIdProduit(this.getIdProduit());
            mvtStockFille.setEntree(ecart);
            mvtStockFille.setSortie(0);
            mvtStockFille.setDesignation(this.getExplication());
            mvtStockFille.setPu(this.getPu());
            mvtStockFille.setReste(0);
            retour.add(mvtStockFille);
            // manampy id mvtsrc izay id max
        }else if (ecart<0) {
            double qteASortir = Math.abs(ecart);
            System.out.println("Ecart "+qteASortir);
            for (int i = 0; i < etatStocks.length && qteASortir>0; i++) {
                MvtStockFille mvtStockFille = new MvtStockFille();
                mvtStockFille.setIdMvtStock(mere.getId());
                mvtStockFille.setIdProduit(this.getIdProduit());
                if (qteASortir<=etatStocks[i].getReste()){
                    mvtStockFille.setSortie(qteASortir);
                    qteASortir = 0;
                }else {
                    mvtStockFille.setSortie(etatStocks[i].getReste());
                }
                System.out.println(etatStocks[i].getId()+" Sortie "+mvtStockFille.getSortie());
                mvtStockFille.setEntree(0);
                mvtStockFille.setMvtSrc(etatStocks[i].getId());
                mvtStockFille.setDesignation(this.getExplication());
                mvtStockFille.setPu(etatStocks[i].getPu());
                mvtStockFille.setReste(0);
                retour.add(mvtStockFille);
                qteASortir -= etatStocks[i].getReste();
            }
            if (qteASortir>0){
                MvtStockFille mvtStockFille = new MvtStockFille();
                mvtStockFille.setIdMvtStock(mere.getId());
                mvtStockFille.setIdProduit(this.getIdProduit());
                mvtStockFille.setSortie(qteASortir);
                System.out.println(" Sortie "+mvtStockFille.getSortie());

                mvtStockFille.setEntree(0);
                mvtStockFille.setMvtSrc(this.getMvtsrc());
                mvtStockFille.setDesignation(this.getExplication());
                mvtStockFille.setPu(this.getPu());
                mvtStockFille.setReste(0);
                retour.add(mvtStockFille);
            }
        }

        return retour;
    }

    public MvtStockFille genererMvtStockFille(Connection c, MvtStock mere,double ecart) throws Exception{
        MvtStockFille retour = new MvtStockFille();
        retour.setIdMvtStock(mere.getId());
        retour.setIdProduit(this.getIdProduit());
        if (ecart>0) {
            retour.setEntree(ecart);
            retour.setSortie(0);
            // manampy id mvtsrc izay id max
        }else if (ecart<0) {
            retour.setSortie(Math.abs(ecart));
            retour.setEntree(0);
            retour.setMvtSrc(this.getMvtsrc());
        }
        retour.setDesignation(this.getExplication());
        retour.setPu(this.getPu());
        retour.setReste(0);
        return retour;
    }
    private String mvtentree;
    private double reste;
    public String getMvtentree() {
        return mvtentree;
    }

    public void setMvtentree(String mvtentree) {
        this.mvtentree = mvtentree;
    }

    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }
}
