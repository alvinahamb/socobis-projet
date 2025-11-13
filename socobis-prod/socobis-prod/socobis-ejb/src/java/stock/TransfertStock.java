/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ClassMere;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.Date;

import historique.MapUtilisateur;
import utils.ConstanteSocobis;
import utils.ConstanteStation;

/**
 *
 * @author 26134
 */
public class TransfertStock extends ClassMere{
    String id,designation,idMagasinDepart,idMagasinArrive, idOf, idFabrication;
    Date daty;

    public TransfertStock() throws Exception {
        this.setNomTable("TransfertStock");
        this.setLiaisonFille("idTransfertStock");
        this.setNomClasseFille("stock.TransfertStockDetails");
    }

    public String getIdFabrication() {
        return idFabrication;
    }

    public void setIdFabrication(String idFabrication) {
        this.idFabrication = idFabrication;
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

    public String getIdMagasinDepart() {
        return idMagasinDepart;
    }

    public void setIdMagasinDepart(String idMagasinDepart) {
        this.idMagasinDepart = idMagasinDepart; 
    }

    public String getIdMagasinArrive() {
        return idMagasinArrive;
    }

    public void setIdMagasinArrive(String idMagasinArrive) {
        this.idMagasinArrive = idMagasinArrive;
    }

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public String getIdOf() {
        return idOf;
    }

    public void setIdOf(String idOf) {
        this.idOf = idOf;
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("TS", "getseqTransfertStock");
        this.setId(makePK(c));
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
    public String getLiaisonFille() {
        return "idTransfertStock";
    }
    @Override
    public String getNomClasseFille() {
        return "stock.TransfertStockDetails";
    }
    
    public TransfertStockDetails[] getTransfertStockDetails(Connection c) throws Exception{
        TransfertStockDetails[] tsd=(TransfertStockDetails[]) CGenUtil.rechercher(new TransfertStockDetails(), null, null, c, " and idTransfertStock='"+this.getId()+"' ");
        return tsd;
    }
    
    protected MvtStock createMvtStock(boolean isEntree) throws Exception{
        MvtStock md=new MvtStock();
        md.setDaty(this.getDaty());
        md.setIdTransfert(this.getId());
        if (isEntree) {    
            md.setIdMagasin(this.getIdMagasinArrive());
            md.setIdTypeMvStock(ConstanteStation.TYPEMVTSTOCKENTREE);
            md.setDesignation("transfert "+this.getDesignation()+" (entree)");
        }else{
            md.setIdMagasin(this.getIdMagasinDepart());
            md.setIdTypeMvStock(ConstanteStation.TYPEMVTSTOCKSORTIE);
            md.setDesignation("transfert "+this.getDesignation()+" (sortie)");
        }
        return md;
    }
    protected MvtStockFille[] createMvtStockFilles(boolean isEntree,Connection c) throws Exception{
        TransfertStockDetails[] tsd=this.getTransfertStockDetails(c);
        MvtStockFille[] mvtf=new MvtStockFille[tsd.length];
        for (int i = 0; i < tsd.length; i++) {
            mvtf[i] = tsd[i].createMvtStockFille(isEntree);
        }
        return mvtf;
    }

    @Override
    public void controler(Connection c) throws Exception {
        if (idMagasinDepart == null || idMagasinArrive == null ||
                idMagasinDepart.trim().isEmpty() || idMagasinArrive.trim().isEmpty()) {
            throw new Exception("Les identifiants des magasins ne doivent pas être null ou vides.");
        }

        if (idMagasinDepart.equals(idMagasinArrive)) {
            throw new Exception("Le magasin de départ doit être différent du magasin d’arrivée.");
        }
    }

    protected MvtStock createMvtStockEntree(String u, Connection c) throws Exception{
        MvtStock me=this.createMvtStock(true);
        me.setFille(this.createMvtStockFilles(true, c));
        me.createObject(u, c);
        //me.saveMvtStockFille(u, c);
//        me.validerObject(u, c);
        return me;
    }
    
    protected MvtStock createMvtStockSortie(String u, Connection c) throws Exception{
        MvtStock ms=this.createMvtStock(false);
        ms.setFille(this.createMvtStockFilles(false, c));
        ms.createObject(u, c);
        //ms.saveMvtStockFille(u, c);
        ms.validerObject(u, c);
        return ms;
    }
    
    @Override
    public Object validerObject(String u, Connection c) throws Exception {
        this.createMvtStockEntree(u, c);
        this.createMvtStockSortie(u, c);
        return super.validerObject(u, c);
    }

    @Override
    public ClassMAPTable createObject(MapUtilisateur u, Connection c)throws Exception{
        if(u.getIdrole().compareTo(ConstanteSocobis.MAGCENTRAL_RANG) != 0){
            throw new Exception("Vous n'avez pas le droit de creer un transfert de stock!");
        }
        return super.createObject(u,c);
    }

    @Override
    public Object validerObject(MapUtilisateur u, Connection c) throws Exception{
        return super.validerObject(u,c);
    }
    public Object superValider(String u, Connection c)throws Exception {
        return super.validerObject(u, c);
    }
    public Object superValider(MapUtilisateur u, Connection c)throws Exception {
        return super.validerObject(u, c);
    }


    public ClassMAPTable superCreateObject(MapUtilisateur u, Connection c)throws Exception {
        return super.createObject(u,c);
    }

    public ClassMAPTable superCreateObject(String u, Connection c)throws Exception {
        return super.createObject(u,c);
    }

}
