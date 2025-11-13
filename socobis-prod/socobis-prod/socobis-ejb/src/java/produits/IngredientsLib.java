package produits;

public class IngredientsLib extends Ingredients
{
    String idcategorieingredient;
    String idcategorie;
    String compte;
    String uniteLib;
    double taux;
    String  refpostLib;
    String refqualificationLib;
    String composelib,idFamilleLib;

    public String getComposelib() {
        return composelib;
    }

    public void setComposelib(String composelib) {
        this.composelib = composelib;
    }

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }

    public String getUniteLib() {
        return uniteLib;
    }

    public void setUniteLib(String uniteLib) {
        this.uniteLib = uniteLib;
    }

    public String getIdcategorieingredient() {
        return idcategorieingredient;
    }

    public void setIdcategorieingredient(String idcategorieingredient) {
        this.idcategorieingredient = idcategorieingredient;
    }

    public String getIdcategorie() {
        return idcategorie;
    }

    public void setIdcategorie(String idcategorie) {
        this.idcategorie = idcategorie;
    }

    public IngredientsLib() {
        setNomTable("AS_INGREDIENTS_LIB");
    }

    @Override
    public String[] getMotCles() {
        String[] motCles={"id","libelle","unite"};
        return motCles;
    }

    @Override
    public String[] getValMotCles() {
        String[] motCles={"id","libelle","unite"};
        return motCles;
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public String getRefpostLib() {
        return refpostLib;
    }

    public void setRefpostLib(String refpostLib) {
        this.refpostLib = refpostLib;
    }

    public String getRefqualificationLib() {
        return refqualificationLib;
    }

    public void setRefqualificationLib(String refqualificationLib) {
        this.refqualificationLib = refqualificationLib;
    }

    public String getIdFamilleLib() {
        return idFamilleLib;
    }

    public void setIdFamilleLib(String idFamilleLib) {
        this.idFamilleLib = idFamilleLib;
    }
}
