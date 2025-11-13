package stock;


import bean.CGenUtil;
import bean.ClassMAPTable;

import java.sql.Connection;
import java.sql.Date;

import bean.ResultatEtSomme;
import utilitaire.Utilitaire;

public class EtatStockParEntree extends EtatStock {
    protected String idProduit;

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public EtatStockParEntree(){
        this.setNomTable("V_ETATSTOCK_ENTREE");
    }

    public ResultatEtSomme rechercherPage(String[] colInt, String[]valInt, int numPage, String apresWhere, String[]nomColSomme, Connection c, int npp) throws Exception {
        String daty=Utilitaire.dateDuJour();
        if(valInt!=null&&valInt.length>1) {daty=valInt[1].toString();}
        String req = "SELECT\n" +
                "   ve.id,\n" +
                "   ai.id AS IDPRODUIT,\n" +
                "   ai.LIBELLE AS idproduitlib,\n" +
                "   ai.CATEGORIEINGREDIENT,\n" +
                "   c.VAL AS idtypeproduitlib,\n" +
                "   ve.IDMAGASIN,\n" +
                "   m.VAL AS idmagasinlib,\n" +
                "   ve.QUANTITE,\n" +
                "   CAST(ve.ENTREE AS NUMBER(30,2)) AS ENTREE,\n" +
                "   CAST(ve.SORTIE AS NUMBER(30,2)) AS SORTIE,\n" +
                "   CAST(ve.RESTE AS NUMBER(30,2)) AS RESTE,\n" +
                "   ai.UNITE,\n" +
                "   u.VAL AS idunitelib,\n" +
                "   ve.PU,\n" +
                "   ve.DATY\n" +
                "FROM (\n" +
                "   SELECT\n" +
                "       \"ID\",\n" +
                "       \"IDMVTSTOCK\",\n" +
                "       \"IDPRODUIT\",\n" +
                "       \"ENTREE\",\n" +
                "       \"SORTIE\",\n" +
                "       \"IDVENTEDETAIL\",\n" +
                "       \"IDTRANSFERTDETAIL\",\n" +
                "       \"PU\",\n" +
                "       \"MVTSRC\",\n" +
                "       \"DATY\",\n" +
                "       \"RESTE\",\n" +
                "       \"QUANTITE\",\n" +
                "       \"IDMAGASIN\"\n" +
                "   FROM (\n" +
                "       SELECT\n" +
                "           m.ID,\n" +
                "           m.IDMVTSTOCK,\n" +
                "           m.IDPRODUIT,\n" +
                "           m.ENTREE,\n" +
                "           s.SORTIE,\n" +
                "           m.IDVENTEDETAIL,\n" +
                "           m.IDTRANSFERTDETAIL,\n" +
                "           m.PU,\n" +
                "           m.MVTSRC,\n" +
                "           mp.DATY,\n" +
                "           NVL(m.ENTREE, 0) - NVL(s.SORTIE, 0) AS RESTE,\n" +
                "           NVL(m.ENTREE, 0) - NVL(s.SORTIE, 0) AS QUANTITE,\n" +
                "           mp.IDMAGASIN\n" +
                "       FROM MVTSTOCKFILLE m\n" +
                "       LEFT JOIN MVTSTOCK mp ON mp.id = m.IDMVTSTOCK\n" +
                "       LEFT JOIN (\n" +
                "           SELECT m.MVTSRC, SUM(m.SORTIE) AS SORTIE\n" +
                "           FROM MVTSTOCKFILLE m\n" +
                "           LEFT JOIN MVTSTOCK mp ON mp.id = m.IDMVTSTOCK\n" +
                "           WHERE mp.ETAT >= 11\n" +
                "             AND m.MVTSRC IS NOT NULL\n" +
                "             AND mp.DATY <= TO_DATE('" + daty + "', 'DD-MM-YYYY')\n" +
                "           GROUP BY m.MVTSRC\n" +
                "       ) s ON s.MVTSRC = m.id\n" +
                "       WHERE m.ENTREE > 0\n" +
                "         AND mp.ETAT >= 11\n" +
                "         AND mp.DATY <= TO_DATE('" + daty + "', 'DD-MM-YYYY')\n" +
                "   )\n" +
                "   \n" +
                ") ve\n" +
                "LEFT JOIN AS_INGREDIENTS ai ON ai.id = ve.IDPRODUIT\n" +
                "LEFT JOIN CATEGORIEINGREDIENT c ON ai.CATEGORIEINGREDIENT = c.id\n" +
                "LEFT JOIN MAGASINPOINT m ON ve.IDMAGASIN = m.id\n" +
                "LEFT JOIN AS_UNITE u ON ai.UNITE = u.id\n" +
                "WHERE VE.RESTE > 0 and ve.PU > 0\n";
        //VE.RESTE
        ResultatEtSomme rs= CGenUtil.rechercherPage(this,req,numPage,nomColSomme,apresWhere,c,npp);
        return rs;
    }

    public EtatStockParEntree [] genererFicheInventaire(String idMagasin, String idProduit, Connection c) throws Exception {
        EtatStockParEntree search = new EtatStockParEntree();
        search.setNomTable("V_ETATSTOCK_ENTREE");
        if (idMagasin!=null && !idMagasin.isEmpty()){
            search.setIdMagasin(idMagasin);
        }
        if (idProduit!=null && !idProduit.isEmpty()){
            search.setIdMagasin(idProduit);
        }
        EtatStockParEntree [] data = (EtatStockParEntree[]) CGenUtil.rechercher(search,null,null,c,"");
        return data;
    }
}
