/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import bean.CGenUtil;
import bean.ClassEtat;
import bean.ClassMere;
import fabrication.Fabrication;
import fabrication.FabricationFille;
import fabrication.OfFille;
import inventaire.Inventaire;
import inventaire.InventaireFille;
import inventaire.InventaireFilleCpl;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import magasin.Magasin;
import produits.Ingredients;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;
import utils.ConstanteSocobis;
import java.sql.Statement;
import utils.ConstanteStation;

public class MvtStock extends ClassMere {

    private String id, designation, idMagasin, idVente, idTransfert, idTypeMvStock,idPoint,idobjet,fabPrecedent;
    private Date daty;

    @Override
    public boolean isSynchro(){
        return true;
    }
    
    public MvtStock() throws Exception {
        this.setNomTable("MVTSTOCK");
	this.setNomClasseFille("stock.MvtStockFille");
	this.setLiaisonFille("idMvtStock");
	 
    }

    public String getFabPrecedent() {
        return fabPrecedent;
    }

    public void setFabPrecedent(String fabPrecedent) {
        this.fabPrecedent = fabPrecedent;
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

    public String getIdVente() {
        return idVente;
    }

    public void setIdVente(String idVente) {
        this.idVente = idVente;
    }

    public String getIdTransfert() {
        return idTransfert;
    }

    public void setIdTransfert(String idTransfert) {
        this.idTransfert = idTransfert;
    }

    public String getIdTypeMvStock() {
        return idTypeMvStock;
    }

    public void setIdTypeMvStock(String idTypeMvStock) {
        this.idTypeMvStock = idTypeMvStock;
    }

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public String getIdPoint() {
        return idPoint;
    }

    public void setIdPoint(String idPoint) {
        this.idPoint = idPoint;
    }

    public String getIdobjet() {
        return idobjet;
    }

    public void setIdobjet(String idobjet) {
        this.idobjet = idobjet;
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
        this.preparePk("MVTST", "GETSEQMVTSTOCK");
        this.setId(makePK(c));
    }

    protected void controlerMvt(Connection c) throws Exception {
        if (this.getIdMagasin()== null || this.getIdMagasin().compareToIgnoreCase("") == 0) {
            throw new Exception("Champ magasin obligatoire");
        }
    }

    public Magasin getMagasin(Connection c) throws Exception {
        if (c == null) {
            throw new Exception("Connection non etablie");
        }
        Magasin magasin = new Magasin();
        magasin.setId(this.getIdMagasin());
        Magasin[] magasins = (Magasin[]) CGenUtil.rechercher(magasin, null, null, c, " ");
        if (magasins.length > 0) {
            return magasins[0];
        }
        return null;
    }

    public MvtStockFille[] getMvtStockFilles(Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                estOuvert = true;
                c = new UtilDB().GetConn();
            }
            MvtStockFille msf = new MvtStockFille();
            msf.setIdMvtStock(this.getId());
            MvtStockFille[] msfs = (MvtStockFille[]) CGenUtil.rechercher(msf, null, null, c, " ");
            if (msfs.length > 0) {
                return msfs;
            }
            return null;
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null && estOuvert == true) {
                c.close();
            }
        }
    }

    public void createInventaireZero(String u, Connection c) throws Exception {
    if (this.getIdTypeMvStock() == ConstanteStation.TYPEMVTSTOCKENTREE) {
        MvtStockFille[] msfs = getMvtStockFilles(c);
        this.setFille(msfs);
        if (msfs != null && msfs.length > 0) {
            for (MvtStockFille mvf : msfs) {
                InventaireFilleCpl invFCpl = new InventaireFilleCpl();
                invFCpl.setIdMagasin(this.getIdMagasin());
                invFCpl.setIdProduit(mvf.getIdProduit());
                InventaireFilleCpl[] invFCpls = invFCpl.getInventaireFilles(c);
                if (invFCpls == null) {
                    Magasin m = getMagasin(c);
                    Inventaire inv = (Inventaire) m.generateInventaireMere().createObject(u, c);
                    InventaireFille invF = inv.generateInventaireFilleZero();
                    invF.setIdProduit(mvf.getIdProduit());
                    invF.createObject(u, c);
                    inv.validerObject(u, c);
                }
            }
        }
    }
}
    public OfFille[] getOfFilles(String nt,Connection c) throws Exception {
        String nomTable="fabricationOfFille";
        if(nt!=null&&nt.compareToIgnoreCase("")!=0) nomTable=nt;
        OfFille crt=new OfFille();
        crt.setNomTable(nomTable);
        crt.setIdFab(this.getIdobjet());
        return (OfFille[]) CGenUtil.rechercher(crt, null, null, c, "");
    }
    public FabricationFille[] getFabricationFilles(String nt,Connection c) throws Exception {
            String nomTable="FABRICATIONFILLE";
            if(nt!=null&&nt.compareToIgnoreCase("")!=0) nomTable=nt;
            FabricationFille crt=new FabricationFille();
            crt.setNomTable(nomTable);
            crt.setIdMere(this.getIdobjet());
            return (FabricationFille[]) CGenUtil.rechercher(crt, null, null, c, "");
    }
    public MvtStockFille[] getMvtStockPrecedent(String nt,Connection c) throws Exception {
           String nomTable="MVTSTOCKFille";
           if(nt!=null&&nt.compareToIgnoreCase("")!=0) nomTable=nt;
           MvtStockFille crt=new MvtStockFille();
           return null;
    }
    public static MvtStockFille[] getMvtStockEntree(MvtStockFille[] msfs) throws Exception {
            ArrayList<MvtStockFille> msfList = new ArrayList<MvtStockFille>();
            for (MvtStockFille msf : msfs) {
                if(msf.getEntree()>0) msfList.add(msf);
            }
            return msfList.toArray(new MvtStockFille[msfList.size()]);
    }
    public static MvtStockFille getMvtStockEntreeCorrespondantFabFille(MvtStockFille[] msfs,FabricationFille[] listeFab) throws Exception {
        MvtStockFille[] mvEntree=getMvtStockEntree(msfs);
        for (MvtStockFille msf : mvEntree) {
            for (FabricationFille fab : listeFab) {
                if(msf.getIdProduit().compareToIgnoreCase(fab.getIdIngredients())==0) return msf;
            }
        }
        return null;
    }

    /*@Override
    public Object validerObject(String u, Connection c) throws Exception {
        MvtStockFille[]fille=this.getMvtStockFilles(c);
        if (this.getIdTypeMvStock()!=null && this.getIdTypeMvStock().compareToIgnoreCase(ConstanteSocobis.TYPE_MVT_SORTIE)==0) {
            checkResteStock(c,fille, this);
            createGenerateMvt(u,c,fille);
        }
        for (int i = 0; i < fille.length; i++) {
            fille[i].updateQteIngredient(u, c);
        }
        super.validerObject(u, c);
        // createInventaireZero(u, c);
        if(this.getFabPrecedent()==null||this.getFabPrecedent().compareToIgnoreCase("")==0) return this;
        Fabrication precedent=new Fabrication();
        precedent.setId(this.getFabPrecedent());
        FabricationFille[] ofFille=(FabricationFille[])precedent.getFille(null, c,"");
        if(ofFille==null||ofFille==null) return this;
        Fabrication fab=new Fabrication();
        fab.setId(this.getFabPrecedent());
        //MvtStockFille[] mvtFilleFab=fab.getMvtStockFille(null,c);
        //if(MvtStock.getMvtStockEntreeCorrespondantFabFille(mvtFilleFab,ofFille)!=null) return this;
        for(int i=0;i<fille.length;i++){
            if(fille[i].getSortie()>0)
            {
                if(fille[i].getIdProduit().compareToIgnoreCase(ofFille[0].getIdIngredients())==0)
                {
                    MvtStock mvt=(MvtStock) this.dupliquerSansBase();
                    mvt.construirePK(c);
                    mvt.setIdobjet(this.getFabPrecedent());
                    mvt.setFabPrecedent(null);
                    mvt.setIdTypeMvStock(ConstanteStation.TYPEMVTSTOCKENTREE);
                    MvtStockFille nouveau=fille[i].contrer();
                    nouveau.setIdMvtStock(mvt.getId());
                    nouveau.construirePK(c);
                    MvtStockFille[]ln={nouveau};
                    mvt.setFille(ln);
                    mvt.createObject(u, c);
                    mvt.validerObject(u, c);
                }
            }
        }
        return this;
    }*/

    @Override
    public Object validerObject(String u, Connection c) throws Exception {
        Statement st =null;
        try {
            MvtStockFille[] fille = (MvtStockFille[]) this.getFille();
            if (fille == null) fille = this.getMvtStockFilles(c);
            if (this.getIdTypeMvStock() != null && this.getIdTypeMvStock().compareToIgnoreCase(ConstanteSocobis.TYPE_MVT_SORTIE) == 0) {
                checkResteStock(c, fille, this);
                // createGenerateMvt(u, c, fille);
            }
            st=c.createStatement();
            for (int i = 0; i < fille.length; i++) {
                fille[i].updateQteIngredientBatch(u, st);
            }
            st.executeBatch();
            super.validerObject(u, c);
            // createInventaireZero(u, c);
            if (this.getFabPrecedent() == null || this.getFabPrecedent().compareToIgnoreCase("") == 0) return this;
            Fabrication precedent = new Fabrication();
            precedent.setId(this.getFabPrecedent());
            FabricationFille[] ofFille = (FabricationFille[]) precedent.getFille(null, c, "");
            if (ofFille == null || ofFille == null) return this;
            Fabrication fab = new Fabrication();
            fab.setId(this.getFabPrecedent());
            //MvtStockFille[] mvtFilleFab=fab.getMvtStockFille(null,c);
            //if(MvtStock.getMvtStockEntreeCorrespondantFabFille(mvtFilleFab,ofFille)!=null) return this;
            for (int i = 0; i < fille.length; i++) {
                if (fille[i].getSortie() > 0) {
                    if (fille[i].getIdProduit().compareToIgnoreCase(ofFille[0].getIdIngredients()) == 0) {
                        MvtStock mvt = (MvtStock) this.dupliquerSansBase();
                        mvt.construirePK(c);
                        mvt.setIdobjet(this.getFabPrecedent());
                        mvt.setFabPrecedent(null);
                        mvt.setIdTypeMvStock(ConstanteStation.TYPEMVTSTOCKENTREE);
                        MvtStockFille nouveau = fille[i].contrer();
                        nouveau.setIdMvtStock(mvt.getId());
                        nouveau.construirePK(c);
                        MvtStockFille[] ln = {nouveau};
                        mvt.setFille(ln);
                        mvt.createObject(u, c);
                        mvt.validerObject(u, c);
                    }
                }
            }
            return this;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally {
            if(st!=null) st.close();
        }
    }

    public void saveMvtStockFille(String u, Connection c) throws Exception {
        MvtStockFille[] mvtf = (MvtStockFille[]) this.getFille();
        for (int i = 0; i < mvtf.length; i++) {
            mvtf[i].setId(null);
            mvtf[i].setIdMvtStock(this.getId());
            mvtf[i].createObject(u, c);
        }
    }

    @Override
    public void controler(Connection c) throws Exception {
        super.controler(c);
        this.controlerMvt(c);
    }

    public void checkResteStock(Connection c, MvtStockFille[] fille, MvtStock mere) throws Exception {
        for (MvtStockFille mvtFille : fille) {
            if (mvtFille.estSuffisant(c, mere)==false) {
                throw new Exception(String.format("quantite en stock insuffisante pour le produit %s dans le magasin", mvtFille.getIdProduit()));
            }
        }
    }

    
    public void createGenerateMvt(String refuser, Connection c, MvtStockFille [] mvf) throws Exception{
        List<MvtStockFille[]> mvtstockfillenew= new ArrayList<MvtStockFille[]>();
        for (int i = 0; i < mvf.length; i++) {
            String apres = "";
            apres += "AND IDPRODUIT = '"+mvf[i].getIdProduit()+"' AND IDMAGASIN='"+this.getIdMagasin()+"'";
            Ingredients ing = mvf[i].getIngredient(c);
            if (ing.getTypeStock().compareToIgnoreCase("FIFO")==0) {
                apres+=" order by daty asc,id asc "; 
            }else if (ing.getTypeStock().compareToIgnoreCase("LIFO")==0){
                apres += " order by daty desc,id desc ";
            } else{
                return;
            }
            MvtStockEntreeAvecReste[] liste=this.getMvtSelonType(c, apres);
            mvtstockfillenew.add(mvf[i].genererNouveauMvt(liste));
        }
        this.deleteObjectAncien(c,mvf);
        for (MvtStockFille[] mvtStockFilles : mvtstockfillenew) {
            this.createNewSortie(refuser, mvtStockFilles, c);
        }
    }

    public MvtStockEntreeAvecReste[] getMvtSelonType(Connection c,String apres) throws Exception {
        MvtStockEntreeAvecReste search = new MvtStockEntreeAvecReste();
        search.setIdMagasin(this.getIdMagasin());
        return  (MvtStockEntreeAvecReste[]) CGenUtil.rechercher(search,null, null, apres);
    }
    
    public void deleteObjectAncien(Connection c,MvtStockFille [] mvt) throws Exception {
        for (MvtStockFille fille : mvt) {
            fille.deleteToTable(c);
        }
    }

    public void createNewSortie(String refUser, MvtStockFille[] listeMvtFille, Connection c) throws Exception {
        for (MvtStockFille fille : listeMvtFille) {
            fille.createObject(refUser,c);
        }
    }

}
