<%-- 
    Document   : avoirFC-saisie
    Created on : 2 ao�t 2024, 14:51:01
    Author     : randr
--%>

<%@page import="avoir.AvoirFC"%>
<%@page import="avoir.AvoirFCFille"%>
<%@page import="bean.TypeObjet"%>
<%@page import="user.*"%> 
<%@ page import="bean.*" %>
<%@page import="affichage.*"%>
<%@page import="utilitaire.*"%>
<%@page import="vente.*"%>
<%
    try {
        UserEJB u = null;
        u = (UserEJB) session.getValue("u");
        String titre = "";
        if(request.getParameter("acte") != null && !request.getParameter("acte").isEmpty()){
            titre = "Modification d'une facture d'avoir";
        }else{
            titre="Enregistrement d'une facture d'avoir";
        }
        Vente vente = null;
        VenteDetails[] venteDetails = null;
        if(request.getParameter("idvente")!=null){
            vente = new Vente();
            vente.setId(request.getParameter("idvente"));
            vente = (Vente) CGenUtil.rechercher(vente, null, null, "")[0];
            venteDetails = (VenteDetails[]) CGenUtil.rechercher(new VenteDetails(), null, null, " and IDVENTE = '"+vente.getId()+"'");
        }

        AvoirFC mere = new AvoirFC();
        AvoirFCFille fille = new AvoirFCFille();
        int nombreLigne = 10;
        PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, nombreLigne, u);
        pi.setLien((String) session.getValue("lien"));
        Liste[] liste = new Liste[4];
        liste[0] = new Liste("idMagasin",new magasin.Magasin(),"val","id");
        TypeObjet motif = new TypeObjet();
        motif.setNomTable("motifavoirfc");
        liste[1] = new Liste("idMotif",motif,"val","id");
        TypeObjet cat = new TypeObjet();
        cat.setNomTable("categorieavoirfc");
        liste[2] = new Liste("idCategorie",cat,"val","id");
        TypeObjet typeavoir = new TypeObjet();
        typeavoir.setNomTable("typeavoir");
        liste[3] = new Liste("idtypeavoir",typeavoir,"val","id");
        pi.getFormu().changerEnChamp(liste);
        pi.getFormu().getChamp("idMagasin").setLibelle("Point de vente");
        pi.getFormu().getChamp("designation").setLibelle("D&eacute;signation");
        pi.getFormu().getChamp("remarque").setLibelle("Remarque");
        pi.getFormu().getChamp("daty").setLibelle("Date");
        pi.getFormu().getChamp("idMotif").setLibelle("Motif");
        pi.getFormu().getChamp("idClient").setLibelle("Client");
        pi.getFormu().getChamp("idtypeavoir").setLibelle("Type");
        pi.getFormu().getChamp("idVente").setLibelle("ID Facture client");
        pi.getFormu().getChamp("idCategorie").setLibelle("Cat&eacute;gorie");
        pi.getFormu().getChamp("idClient").setPageAppelComplete("client.Client","id","Client");
        pi.getFormu().getChamp("idClient").setPageAppelInsert("client/client-saisie.jsp","idClient;idClientlibelle","id;nom");
        pi.getFormu().getChamp("idVente").setAutre("readonly");
        pi.getFormu().getChamp("idVente").setDefaut(""+request.getParameter("id"));
        
        pi.getFormu().getChamp("etat").setVisible(false);
        pi.getFormu().getChamp("idOrigine").setVisible(false);
        
        //fille
        affichage.Champ.setPageAppelComplete(pi.getFormufle().getChampFille("idProduit"),"annexe.ProduitLib","id","PRODUIT_LIB_MGA","puVente;puAchat;taux;val","pu;puAchat;tauxDeChange;designation");
        affichage.Champ.setPageAppelInsert(pi.getFormufle().getChampFille("idProduit"),"annexe/produit/produit-saisie.jsp","id;val");
        pi.getFormufle().getChamp("idProduit_0").setLibelle("Produit");
        pi.getFormufle().getChamp("tva_0").setLibelle("TVA");
        pi.getFormufle().getChamp("designation_0").setLibelle("Designation");
        pi.getFormufle().getChamp("remise_0").setLibelle("remise");
        pi.getFormufle().getChamp("idOrigine_0").setLibelle("Origine");
        pi.getFormufle().getChamp("pu_0").setLibelle("Montant");
        pi.getFormufle().getChamp("designation_0").setLibelle("D&eacute;signation");
        pi.getFormufle().getChamp("tauxDeChange_0").setLibelle("Taux de change");
        pi.getFormufle().getChamp("qte_0").setLibelle("Quantit&eacute;");
        String[] ordre={"daty"};
        pi.getFormu().setOrdre(ordre);
        //pi.getFormufle().getChampMulitple("tauxDeChange").setAutre("readonly");
        pi.preparerDataFormu();
        for(int i=0;i<nombreLigne;i++){
           // pi.getFormufle().getChamp("pu_"+i).setAutre("readonly");
            pi.getFormufle().getChamp("qte_"+i).setDefaut("1");
            pi.getFormufle().getChamp("tva_"+i).setDefaut("0");
            pi.getFormufle().getChamp("idDevise_"+i).setDefaut("AR");
        }
        pi.getFormufle().getChampMulitple("id").setVisible(false);
         pi.getFormufle().getChampMulitple("idAvoirFC").setVisible(false);
        pi.getFormufle().getChampMulitple("IdOrigine").setVisible(false);
        pi.getFormufle().getChampMulitple("puAchat").setVisible(false);
        pi.getFormufle().getChampMulitple("idVentedetails").setVisible(false);
        pi.getFormufle().getChampMulitple("puVente").setVisible(false);
        pi.getFormufle().getChampMulitple("idDevise").setVisible(false);
        

        if(vente!=null){
            pi.getFormu().getChamp("designation").setDefaut(vente.getDesignation());
            pi.getFormu().getChamp("idVente").setDefaut(vente.getId());
            System.out.println("ID MADAGASIN="+vente.getIdMagasin());
            if(vente.getIdMagasin()!=null)pi.getFormu().getChamp("idMagasin").setDefaut(String.valueOf(vente.getIdMagasin()));
            pi.getFormu().getChamp("idClient").setDefaut(vente.getIdClient());

            for (int i = 0; i < venteDetails.length; i++) {
                pi.getFormufle().getChamp("idProduit_"+i).setDefaut(venteDetails[i].getIdProduit());
                pi.getFormufle().getChamp("pu_"+i).setDefaut(String.valueOf(venteDetails[i].getPu()));
                pi.getFormufle().getChamp("remise_"+i).setDefaut(String.valueOf(venteDetails[i].getRemise()));
                pi.getFormufle().getChamp("tva_"+i).setDefaut(String.valueOf(venteDetails[i].getTva()));
                pi.getFormufle().getChamp("tauxDeChange_"+i).setDefaut(String.valueOf(venteDetails[i].getTauxDeChange()));
                pi.getFormufle().getChamp("designation_"+i).setDefaut(venteDetails[i].getDesignation());
                pi.getFormufle().getChamp("qte_"+i).setDefaut(String.valueOf(venteDetails[i].getQte()));
            }
        }
        //Variables de navigation
        String classeMere = "avoir.AvoirFC";
        String classeFille = "avoir.AvoirFCFille";
        String butApresPost = "avoir/avoirFC-fiche.jsp";
        String colonneMere = "idAvoirFC";
        //Preparer les affichages
        pi.getFormu().makeHtmlInsertTabIndex();
        pi.getFormufle().makeHtmlInsertTableauIndex();

%>
<div class="content-wrapper">
   <div class="row">
        <div class="col-md-12">
            <div class="box-fiche">
                <div class="box">
                    <div class="box-title with-border">
                        <h1><%= titre %></h1>
                    </div>
                    <div class="box-body">
                        <form class='container' action="<%=pi.getLien()%>?but=apresMultiple.jsp" method="post" >
                            <%

                                out.println(pi.getFormu().getHtmlInsert());
                            %>
                            <h3>Total  : <span id="montanttotal">0</span><span id="deviseLibelle">Ar</span></h3>
                            <%
                                out.println(pi.getFormufle().getHtmlTableauInsert());
                            %>

                            <input name="acte" type="hidden" id="nature" value="insert">
                            <input name="bute" type="hidden" id="bute" value="<%= butApresPost %>">
                            <input name="classe" type="hidden" id="classe" value="<%= classeMere %>">
                            <input name="classefille" type="hidden" id="classefille" value="<%= classeFille %>">
                            <input name="nombreLigne" type="hidden" id="nombreLigne" value="<%= nombreLigne %>">
                            <input name="colonneMere" type="hidden" id="colonneMere" value="<%= colonneMere %>">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%
	} catch (Exception e) {
		e.printStackTrace();
%>
    <script language="JavaScript">
        alert('<%=e.getMessage()%>');
        history.back();
    </script>
<% }%>

