/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventaire;

import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ClassMere;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import magasin.Magasin;
import stock.MvtStock;
import stock.MvtStockFille;
import stock.*;
import utilitaire.Utilitaire;
import utils.ConstanteStation;

public class Inventaire extends ClassMere{
    private String id, designation, idMagasin, remarque;
    private Date daty;
    private String idCategorie;

    public Inventaire(){
        this.setNomTable("inventaire");
        setLiaisonFille("idInventaire");
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

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
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
    public void construirePK(Connection c) throws Exception {
        this.preparePk("IVT", "GETSEQinventaire");
        this.setId(makePK(c));
    }



    public Magasin getMagasin(Connection c) throws Exception {
        if (c ==null)
        {
            throw new Exception ("Connection non etablie");
        }
        Magasin magasin = new Magasin();
        magasin.setId(this.getIdMagasin());
        Magasin[] magasins = (Magasin[])CGenUtil.rechercher(magasin, null, null, c, " ");
        if (magasins.length > 0) {
            return magasins[0];
        }
        return null;
    }

    public InventaireFille generateInventaireFilleZero () throws Exception{
        InventaireFille invF=new InventaireFille();
        invF.setQuantite(0);
        invF.setQuantiteTheorique(0);
        invF.setIdInventaire(this.getId());
        invF.setExplication("inventaire 0");
        return invF;
    }

    public InventaireFille[] getInventaireFille(Connection c) throws Exception{
        InventaireFille search = new InventaireFille();
        search.setIdInventaire(this.getId());
        search.setNomTable("INVENTAIREFILLECOMPLET");
        return (InventaireFille[]) CGenUtil.rechercher(search,null,null,c,"");
    }

    public InventaireFilleComplet[] getInventaireFilleComplet(Connection c) throws Exception{
        InventaireFilleComplet search = new InventaireFilleComplet();
        search.setIdInventaire(this.getId());
        return (InventaireFilleComplet[]) CGenUtil.rechercher(search,null,null,c,"");
    }

    public void creerMouvementGenerer(String u,Connection c) throws Exception{
        //Statement st =null;
        try {
        MvtStock m = new MvtStock();
        m.setDaty(daty);
        m.setDesignation("Mouvement pour ecart de l inventaire: "+this.getId());
        m.setIdMagasin(this.getIdMagasin());
        m.setIdTypeMvStock(ConstanteStation.TYPEMVTSTOCKINVENTAIRE);
        m.setIdobjet(this.getId());
        InventaireFille[] invf = getInventaireFille(c);
        List<MvtStockFille> mvtf = new ArrayList<>();
        for (int i = 0; i < invf.length; i++) {
            invf[i].setQuantiteTheorique(invf[i].getReste());
            invf[i].setMvtsrc(invf[i].getMvtentree());
            InventaireFille inv = (InventaireFille) invf[i];
            inv.setNomTable("INVENTAIREFILLE");
            double ecart = invf[i].getEcart();
            if (ecart!=0) {
                mvtf.addAll(invf[i].genererMvtStockFilles(c, m, ecart));
                //st=c.createStatement();
                inv.updateToTableWithHisto(u,c);
                //inv.updateToTableWithHistoBatch(u,st);
            }
        }
         //st.executeBatch();
        if (mvtf.size()==0) {
            return;
        }
        MvtStockFille[] fille = mvtf.toArray(new MvtStockFille[mvtf.size()]);
        m.setFille(fille);
        m.createObject(u, c);
        m.validerObject(u, c);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }/*finally {
             if(st!=null) st.close();
        }*/
    }

    @Override
    public Object validerObject(String u, Connection c) throws Exception {
        creerMouvementGenerer(u, c);
        super.validerObject(u, c);
        return this;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Inventaire genererInventaire (EtatStockParEntree [] etatStocks) throws Exception{
        Inventaire inventaire = null;
        if (etatStocks.length>0) {
            inventaire = new Inventaire();
            inventaire.setIdMagasin(etatStocks[0].getIdMagasin());
            inventaire.setDaty(Utilitaire.dateDuJourSql());
            inventaire.setDesignation("Inventaire : "+inventaire.getDaty());
            List<InventaireFille> list = new ArrayList<>();
            for (EtatStockParEntree etatStock : etatStocks) {
                InventaireFille invF = new InventaireFille();
                invF.setIdProduit(etatStock.getIdProduit());
                invF.setQuantite(etatStock.getQuantite());
                invF.setQuantiteTheorique(etatStock.getReste());
                invF.setPu(etatStock.getPu());
                list.add(invF);
            }
            inventaire.setFille(list.toArray(new InventaireFille[]{}));
        }
        return inventaire;
    }

    public void annulerMvtStock(Connection c, String u) throws Exception{
        MvtStock m = new MvtStock();
        m.setIdTypeMvStock(ConstanteStation.TYPEMVTSTOCKINVENTAIRE);
        m.setIdMagasin(this.getIdMagasin());
        m.setDesignation(this.getId());
        MvtStock[] mvtStocks = (MvtStock[]) CGenUtil.rechercher(m, null, null, c, "");
        for(int i=0; i< mvtStocks.length; i++){
            mvtStocks[i].annuler(u, c);
        }
    }

    public void annulerVisaMvtStock(Connection c, String u) throws Exception{
        MvtStock m = new MvtStock();
        m.setIdTypeMvStock(ConstanteStation.TYPEMVTSTOCKINVENTAIRE);
        m.setIdMagasin(this.getIdMagasin());
        m.setDesignation(this.getId());
        MvtStock[] mvtStocks = (MvtStock[]) CGenUtil.rechercher(m, null, null, c, "");
        for(int i=0; i< mvtStocks.length; i++){
            mvtStocks[i].annulerVisa(u, c);
            mvtStocks[i].annuler(u, c);
        }
    }

    @Override
    public int annuler(String u, Connection c) throws Exception {
        annulerMvtStock(c, u);
        return super.annuler(u, c);
    }

    @Override
    public void annulerVisa(String u, Connection c) throws Exception {
        annulerVisaMvtStock(c, u);
        super.annulerVisa(u, c);
        super.annuler(u, c);
    }
}
