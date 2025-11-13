/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;


public class MvtStockFilleLib extends MvtStockFille{
    private String idProduitlib, idVenteDetaillib, idTransfertDetaillib , libelleexacte, idMagasinLib, idMagasin, mvtsrc;
    private String libelle;
    private String daty;
    public MvtStockFilleLib() throws Exception{
        setNomTable("mvtstockfillelib");
    }

    public String getMvtsrc() {
        return mvtsrc;
    }

    public void setMvtsrc(String mvtsrc) {
        this.mvtsrc = mvtsrc;
    }

    public String getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(String idMagasin) {
        this.idMagasin = idMagasin;
    }

    public String getIdMagasinLib() {
        return idMagasinLib;
    }

    public void setIdMagasinLib(String idMagasinLib) {
        this.idMagasinLib = idMagasinLib;
    }

    public String getDaty() {
        return daty;
    }

    public void setDaty(String daty) {
        this.daty = daty;
    }

    public String getIdProduitlib() {
        return idProduitlib;
    }

    public void setIdProduitlib(String idProduitlib) {
        this.idProduitlib = idProduitlib;
    }

    public String getIdVenteDetaillib() {
        return idVenteDetaillib;
    }

    public void setIdVenteDetaillib(String idVenteDetaillib) {
        this.idVenteDetaillib = idVenteDetaillib;
    }
    
    public String getIdTransfertDetaillib() {
        return idTransfertDetaillib;
    }

    public void setIdTransfertDetaillib(String idTransfertDetaillib) {
        this.idTransfertDetaillib = idTransfertDetaillib;
    }

    public String getLibelleexacte() {
        return libelleexacte;
    }

    public void setLibelleexacte(String libelleexacte) {
        this.libelleexacte = libelleexacte;
    }


    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String[] getMotCles() {
        String[] motCles={"id","libelleexacte","pu","daty"};
        return motCles;
    }

    @Override
    public String[] getValMotCles() {
        String[] valMotCles={"id","libelleexacte","daty"};
        return valMotCles;
    }
}
