package faturefournisseur;

import bean.ClassMere;
import produits.Ingredients;
import utilitaire.UtilDB;

import java.sql.Connection;
import java.sql.Date;

public class DmdAchat extends ClassMere {
    private String id;
    private Date daty;
    private String fournisseur;
    private String remarque;

    public DmdAchat() throws Exception {
        this.setNomTable("DMDACHAT");
        this.setLiaisonFille("idmere");
        this.setNomClasseFille("faturefournisseur.DmdAchatFille");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date date) {
        this.daty = date;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public FactureFournisseurDetails[] getFactureFournisseurDetails(Connection c) throws Exception {
        int indice = 0;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                indice = 1;
            }
            DmdAchatFille[] dmdAchatFille = (DmdAchatFille[])this.getFille("DMDACHATFILLE",c,"");
            FactureFournisseurDetails[] factureFoDetails = new  FactureFournisseurDetails[dmdAchatFille.length];

            for (int i = 0; i < dmdAchatFille.length; i++) {
                Ingredients ing = (Ingredients)new Ingredients().getById(dmdAchatFille[i].getIdproduit(),"AS_INGREDIENTS",c);
                factureFoDetails[i] = new FactureFournisseurDetails();
                factureFoDetails[i].setIdProduit(dmdAchatFille[i].getIdproduit());
                factureFoDetails[i].setPu(dmdAchatFille[i].getPu());
                factureFoDetails[i].setQte(dmdAchatFille[i].getQuantite());
                factureFoDetails[i].setTva(dmdAchatFille[i].getTva());
                factureFoDetails[i].setIdDevise("AR");
                factureFoDetails[i].setCompte(ing.getCompte_achat());
            }
            return factureFoDetails;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }finally{
            if(indice == 1 && c!=null)c.close();
        }
    }

    public As_BonDeCommande_Fille[] getBonDeCommandeDetails(Connection c) throws Exception {
        int indice = 0;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                indice = 1;
            }
            DmdAchatFille[] dmdAchatFille = (DmdAchatFille[])this.getFille("DMDACHATFILLE",c,"");
            As_BonDeCommande_Fille[] factureFoDetails = new  As_BonDeCommande_Fille[dmdAchatFille.length];

            for (int i = 0; i < dmdAchatFille.length; i++) {
//                Ingredients ing = (Ingredients)new Ingredients().getById(dmdAchatFille[i].getIdproduit(),"AS_INGREDIENTS",c);
                factureFoDetails[i] = new As_BonDeCommande_Fille();
                factureFoDetails[i].setProduit(dmdAchatFille[i].getIdproduit());
                factureFoDetails[i].setPu(dmdAchatFille[i].getPu());
                factureFoDetails[i].setQuantite(dmdAchatFille[i].getQuantite());
                System.out.println("qte = " + dmdAchatFille[i].getQuantite());
                factureFoDetails[i].setTva(dmdAchatFille[i].getTva());
                factureFoDetails[i].setIdDevise("AR");
            }
            return factureFoDetails;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }finally{
            if(indice == 1 && c!=null)c.close();
        }
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("DMDA", "GETSEQDMDACHAT");
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
}
