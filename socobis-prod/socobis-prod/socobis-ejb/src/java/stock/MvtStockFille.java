/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import bean.ClassFille;
import bean.CGenUtil;
import bean.ClassMAPTable;
import java.sql.Connection;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import produits.Ingredients;
import java.sql.Statement;

import utilitaire.UtilDB;
import utils.ConstanteSocobis;
import utils.ConstanteStation;
import vente.As_BondeLivraisonClientFille;
import vente.As_BondeLivraisonClientFille_Cpl;
import chatbot.AiColDesc;


public class MvtStockFille extends ClassFille{
    @AiColDesc("id du ordre de fabrication fille, commence par OFF")
    private String id;
    private String idMvtStock, idProduit, idVenteDetail, idTransfertDetail,designation,idFab,idOf,categorieIngredient,idOff;
    private double entree, sortie,pu,montant;
    private String mvtSrc;
    private double reste;



    public String getIdFab() {
        return idFab;
    }

    public String getIdOff() {
        return idOff;
    }

    public void setIdOff(String idOff) {
        this.idOff = idOff;
    }

    public String getIdOf() {
        return idOf;
    }

    public void setIdOf(String idOf) {
        this.idOf = idOf;
    }

    public String getCategorieIngredient() {
        return categorieIngredient;
    }

    public void setCategorieIngredient(String categorieIngredient) {
        this.categorieIngredient = categorieIngredient;
    }

    public void setIdFab(String idFab) {
        this.idFab = idFab;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double getPu() {
        return pu;
    }

    public void setPu(double pu) throws Exception {
        if (this.getMode().equals("modif") && pu < 0) {
            throw new Exception("Pu ne peut pas &ecirc;tre inf&eacute;rieur &agrave; 0");
        }
        this.pu = pu;
    }

    @Override
    public boolean isSynchro(){
        return true;
    }
    
    public MvtStockFille() throws Exception{
        setNomTable("MvtStockFille");
        this.setLiaisonMere("idMvtStock");
        this.setNomClasseMere("stock.MvtStock");
    }

    public String getNomClasseMere(){
      return "stock.MvtStock";
    }

    public String getLiaisonMere(){
      return "idMvtStock";
    }

    public MvtStockFille contrer()throws Exception{
        MvtStockFille stockFille = (MvtStockFille)this.dupliquerSansBase();
        if(this.getEntree()>0)
        {
            stockFille.setSortie(this.getEntree());
            stockFille.setEntree(0);
        }
        else if(this.getSortie()>0){
            stockFille.setEntree(this.getSortie());
            stockFille.setSortie(0);
        }
        return stockFille;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Exception{
        this.id = id;
    }

    public String getIdMvtStock() {
        return idMvtStock;
    }

    public void setIdMvtStock(String idMvtStock) {
        this.idMvtStock = idMvtStock;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) throws Exception{
        if(getMode().compareTo("modif")==0 && (idProduit==null || idProduit.isEmpty()==true))throw new Exception("Veuillez entrer un produit");
        this.idProduit = idProduit;
    }

    public String getIdVenteDetail() {
        return idVenteDetail;
    }

    public void setIdVenteDetail(String idVenteDetail) {
        this.idVenteDetail = idVenteDetail;
    }

    public String getIdTransfertDetail() {
        return idTransfertDetail;
    }

    public void setIdTransfertDetail(String idTransfertDetail) {
        this.idTransfertDetail = idTransfertDetail;
    }

    public double getEntree() {
        return entree;
    }

    public void setEntree(double entree) throws Exception {
        if(getMode().compareTo("modif")==0 && entree<0)throw new Exception("Valeur de l'entree invalide");
        this.entree = entree;
    }

    public double getSortie() {
        return sortie;
    }

    public void setSortie(double sortie) throws Exception {
        if(getMode().compareTo("modif")==0 && sortie<0)throw new Exception("Valeur de la sortie invalide");
        this.sortie = sortie;
    }
    public MvtStockFille[] getMvtFromSrc(MvtStockFille mvtF)throws Exception{
        Connection c=null;
        boolean canClose=false;
        try {
            c=new UtilDB().GetConn();
            canClose=true;
            MvtStockFille mvtfille = new MvtStockFille();
            mvtfille.setMvtSrc(mvtF.getId());
            MvtStockFille[] lmvt=(MvtStockFille[]) CGenUtil.rechercher(mvtfille, null, null, c, "");
            return lmvt;
        } catch (Exception e) {
            throw e;
        }
        finally {
            if(canClose){
                c.close();
            }
        }
    }
    public void updatePrice(MvtStockFille[] lmvtFille, MvtStockFille mvtFille, double price)throws Exception{
        Connection c=null;
        boolean canClose=false;
        try {
            c=new UtilDB().GetConn();
            canClose=true;
            lmvtFille = getMvtFromSrc(mvtFille);
            for (int i = 0; i < lmvtFille.length; i++) {
                lmvtFille[i].setPu(price);
                lmvtFille[i].updateToTable(c);
            }
            mvtFille.setPu(price);
            mvtFille.updateToTable(c);
        } catch (Exception e) {
            throw e;
        }
        finally {
            if(canClose){
                c.close();
            }
        }
    }


    
    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("MVTSFI", "GETSEQMVTSTOCKFILLE");
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
        super.setLiaisonMere("idMvtStock");
    }

    @Override
    public void controlerUpdate(Connection c) throws Exception {
        super.setNomClasseMere("stock.MvtStock");
        this.controlerSaisie();
        this.controllerMvtSrc(c);
        super.controlerUpdate(c);
    }

    @Override
    public void controler(Connection c) throws Exception{
        this.controlerSaisie();
        this.controllerMvtSrc(c);
        /**if( this.getSortie() > 0 && this.getEntree() == 0 ){
            EtatStock[] etats = (EtatStock[])CGenUtil.rechercher( new EtatStock(), null,null, c, " and id='" + this.getIdProduit() + "' and idMagasin='"+((MvtStock)this.getMere()).getIdMagasin()+"'");
            EtatStock etat = etats[0];
            if( etat.getReste() <= 0 || etat.getReste() < this.getSortie() ){
                throw new Exception("Veuillez rentrez le produit " + this.getIdProduit() + " : stock insuffisant");
            }
        }*/
        Ingredients ing=this.getIngredient(c);
        if (ing.getTypeStock()==null || ing.getTypeStock()=="") {
            throw new Exception(String.format("Ce produit %s ne peut pas etre generer une sortie en stock",ing.getId()));
        }
        if(this.getSortie()>0 && this.getEntree() == 0)
        {   
            /*EtatStock e = new EtatStock();
            e.setNomTable("V_ETATSTOCK_ING");
            e.setId(this.getIdProduit());
            MvtStock mere = this.getMereMvtStock(c);
            e.setIdMagasin(mere.getIdMagasin());
            EtatStock[] etats=null;// = (EtatStock[])CGenUtil.rechercher(e, null,null, c,"");
            EtatStock etat=null;
            if(etats!=null) etat= etats[0];*/
            /*if( etat.getReste() <= 0 || etat.getReste() < this.getSortie() ){
                throw new Exception("Stock de "+etat.getIdProduitLib()+" insuffisant: "+etat.getReste());
            }*/
            
        }
        //controllerQteLivraison(c);
    }
    public As_BondeLivraisonClientFille_Cpl getBondeLivraisonClientFille(Connection c) throws Exception{
        As_BondeLivraisonClientFille_Cpl blf= new As_BondeLivraisonClientFille_Cpl();
        blf.setIdventedetail(this.getIdVenteDetail());
        As_BondeLivraisonClientFille_Cpl[] As_BondeLivraisonClientFille= (As_BondeLivraisonClientFille_Cpl[]) CGenUtil.rechercher(blf,null,null,c, "");
        if(As_BondeLivraisonClientFille.length>0 || blf!=null){
            return As_BondeLivraisonClientFille[0];
        }
        return null;
    }
    public void controllerQteLivraison(Connection c) throws Exception{
        As_BondeLivraisonClientFille_Cpl blf=getBondeLivraisonClientFille(c);
        if(this.sortie>blf.getQteResteALivrer()){
            throw new Exception( blf.getIdproduitlib()+ " : quantité supérieure au reste à livrer");
            }
    }
    @Override
    public ClassMAPTable createObject(String u, Connection c) throws Exception {
        return super.createObject(u, c);
    }



    public void controlerSaisie() throws Exception{
        if(this.getIdProduit() == null || this.getIdProduit().isEmpty()){
            throw new Exception("Veuillez entrer un produit");
        }
        if(this.getEntree() <= 0 && this.getSortie() <=0){
            throw new Exception("Verifier la quantite sur entree et sortie!");
        }
    }

     public MvtStock getMereMvtStock(Connection c) throws Exception {
        /*if (c == null) {
            throw new Exception("Connection non etablie");
        }*/
        MvtStock m = new MvtStock();
        m.setId(this.getIdMvtStock());
        MvtStock[] listes = (MvtStock[]) CGenUtil.rechercher(m, null, null, c, "");
        if (listes.length > 0) {
            return listes[0];
        }
        return null;
    }

    public Ingredients getIngredient(Connection c) throws Exception {
        Ingredients search = new Ingredients();
        search.setId(this.getIdProduit());
        Ingredients[] result = (Ingredients[]) CGenUtil.rechercher(search,null,null,c,"");
        if (result.length == 0) {
            throw new Exception("Ingredients avec id : " + this.getIdProduit() + " introuvable");
        }
        return result[0];
    }

    public void updateQteIngredient(String u, Connection c)throws Exception{
        Ingredients ing = this.getIngredient(c);
        ing.setReste(ing.getReste()+this.getEntree());
        ing.setReste(ing.getReste()-this.getSortie());
        ing.updateToTableWithHisto(u, c);
    }

    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }

    public String getMvtSrc() {
        return mvtSrc;
    }

    public void setMvtSrc(String mvtSrc) {
        this.mvtSrc = mvtSrc;
    }

    public double getQuantite(Connection c, MvtStock mere) throws Exception {
        EtatStockParEntree search = new EtatStockParEntree();
        search.setId(this.getIdProduit());
        search.setIdMagasin(mere.getIdMagasin());
        if(mere.getIdTypeMvStock().compareTo(ConstanteStation.TYPEMVTSTOCKINVENTAIRE)==0){
            search.setNomTable("V_ETATSTOCK_ING_ALL");
        }else if(this.getMvtSrc() != null && !this.getMvtSrc().isEmpty()){
            search.setNomTable("V_ETATSTOCK_ENTREE");
            search.setId(this.getMvtSrc());
            search.setIdProduit(this.getIdProduit());
        }else{
            search.setNomTable("V_ETATSTOCK_ING");
        }
        EtatStockParEntree[] retour = (EtatStockParEntree[]) CGenUtil.rechercher(search,null,null, c,"");
        if (retour.length == 0) {
            throw new Exception("Mouvement Stock Entree avec id produit : " + this.getIdProduit() + " introuvable");
        }
        return retour[0].getReste();
    }

    public boolean estSuffisant(Connection c, MvtStock mere) throws Exception {
        if (this.getSortie()>this.getQuantite(c, mere)) {
            return false;
        }
        return true;
    }

    public MvtStockFille[] genererNouveauMvt(MvtStockEntreeAvecReste[] stockReste) throws Exception {
        List<MvtStockFille> retourList = new ArrayList<>();
        double quantite = this.getSortie();
            for (int i = 0; i < stockReste.length && quantite>0; i++) {
                double reste = stockReste[i].getQuantite() - quantite;
                if (reste < 0) {
                    reste = 0;
                }
                double sortie= stockReste[i].getQuantite() - reste;
                MvtStockFille mvtFille = new MvtStockFille();
                mvtFille.setIdMvtStock(this.getIdMvtStock());
                mvtFille.setIdProduit(this.getIdProduit());
                mvtFille.setSortie(sortie);
                mvtFille.setMvtSrc(stockReste[i].getId());
                mvtFille.setPu(stockReste[i].getPu());
                quantite = quantite - mvtFille.getSortie();
                retourList.add(mvtFille);
            }
        return retourList.toArray(new MvtStockFille[0]);
    }

    public void updateQteIngredientBatch(String u, Statement st)throws Exception{
        Ingredients ing = this.getIngredient(st.getConnection());
        ing.setReste(ing.getReste()+this.getEntree());
        ing.setReste(ing.getReste()-this.getSortie());
        ing.updateToTableWithHistoBatch(u, st);
    }

    public void controllerMvtSrc(Connection c)throws Exception{
        boolean estOuvert = false;
        try {
            if (c == null) {
                estOuvert = true;
                c = new UtilDB().GetConn();
            }
            if(this.getMvtSrc() != null && !this.getMvtSrc().isEmpty()){
                MvtStockEntreeAvecReste map = new MvtStockEntreeAvecReste();
                MvtStockEntreeAvecReste rep = (MvtStockEntreeAvecReste) map.getById(this.getMvtSrc(), "V_ETATSTOCK_ENTREE", c);
                if(rep != null) {
                    if(this.getIdProduit().compareToIgnoreCase(rep.getIdProduit()) != 0){
                        throw new Exception("Le produit dans le mouvement source "+ rep.getId()+" ne correspondant pas au produit dans " + this.getMvtSrc());
                    }
                }
            }else{
                MvtStock mere = this.getMereMvtStock(c);
                if(mere!=null && mere.getIdTypeMvStock().compareTo(ConstanteSocobis.TYPE_MVT_SORTIE) == 0){
                    Ingredients ingredients = this.getIngredient(c);
                    if(ingredients!=null && !ingredients.getCategorieIngredient().equalsIgnoreCase(ConstanteSocobis.CATEGORIE_CONSOMMABLE)){
                        throw new Exception("Le mouvement source ne peut pas \u00EAtre vide");
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


    public static Map<String, Double> getDataChartCheese() throws Exception {
        Map<String, Double> dataChart = new HashMap<String, Double>();
        String req = "select sum(ENTREE) as entree, sum(SORTIE) as sortie from MVTSTOCKFILLELIB where IDPRODUIT = 'ING000T0108'";
        MvtStockFille[] etat = (MvtStockFille[]) CGenUtil.rechercher(new MvtStockFille(), req);
        dataChart.put("Entree", (double) etat[0].getEntree());
        dataChart.put("Sortie", (double) etat[0].getSortie());
        return dataChart;
    }

    public static Map<String, Double> getDataChartBar() throws Exception {
        Map<String, Double> dataChart = new LinkedHashMap<>();
        String req = "SELECT *\n" +
                "FROM (\n" +
                "    SELECT IDPRODUIT, IDPRODUITLIB, SUM(SORTIE) AS sortie\n" +
                "    FROM MVTSTOCKFILLELIB\n" +
                "    GROUP BY IDPRODUIT, IDPRODUITLIB\n" +
                ")\n" +
                "WHERE ROWNUM <= 50";
        MvtStockFilleLib[] etat = (MvtStockFilleLib[]) CGenUtil.rechercher(new MvtStockFilleLib(), req);
        for (MvtStockFilleLib charge : etat) {
            dataChart.put(charge.getIdProduitlib(), charge.getSortie());
        }

        return dataChart;
    }
}
