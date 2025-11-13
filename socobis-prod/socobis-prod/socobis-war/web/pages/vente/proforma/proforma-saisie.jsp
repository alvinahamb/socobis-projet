<%--
  Created by IntelliJ IDEA.
  User: safidy
  Date: 05/08/2025
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>

<%@page import="utils.ConstanteSocobis"%>
<%@page import="magasin.Magasin"%>
<%@page import="vente.*"%>
<%@page import="user.*"%>
<%@page import="bean.*" %>
<%@page import="affichage.*"%>
<%@page import="utilitaire.*"%>
<%@ page import="client.Client" %>
<%@ page import="proforma.Proforma" %>
<%@ page import="proforma.ProformaDetails" %>
<%@ page import="proforma.ProformaDetailsLib" %>
<%
    try{
        UserEJB u = null;
        u = (UserEJB) session.getValue("u");
        PageInsertMultiple pi=null;
        Proforma mere = new Proforma();
        ProformaDetailsLib fille = new ProformaDetailsLib();
        fille.setNomTable("PROFORMA_DETAILS_VIDE");
        int nombreLigne = 10;
        pi = new PageInsertMultiple(mere,fille,request, nombreLigne,u);
        Proforma prerempli = null;

        pi.setLien((String) session.getValue("lien"));
        pi.getFormu().getChamp("remarque").setLibelle("Remarque");
        pi.getFormu().getChamp("daty").setLibelle("Date");
        pi.getFormu().getChamp("etat").setVisible(false);
        pi.getFormu().getChamp("idClient").setLibelle("Client");
        pi.getFormu().getChamp("idClient").setPageAppelComplete("client.Client","id","Client");
        pi.getFormu().getChamp("idClient").setPageAppelInsert("client/client-saisie.jsp","idClient;idClientlibelle","id;nom");
        pi.getFormu().getChamp("idClient").setAutre("onchange=\"updateFille(event, 'formId')\"");

        pi.getFormu().getChamp("designation").setLibelle("D&eacute;signation");
        pi.getFormu().getChamp("idMagasin").setLibelle("Point");
        //pi.getFormu().getChamp("idorigine").setLibelle("ID Origine");
        pi.getFormu().getChamp("idorigine").setVisible(false);
        pi.getFormu().getChamp("datyPrevu").setVisible(false);
        pi.getFormu().getChamp("remise").setVisible(false);
        pi.getFormu().getChamp("tva").setVisible(false);
        pi.getFormu().getChamp("echeance").setVisible(false);
        pi.getFormu().getChamp("reglement").setVisible(false);
        pi.getFormu().getChamp("estPrevu").setVisible(false);

//        if(request.getParameter("idDmd")!=null && !request.getParameter("idDmd").isEmpty()){
//            String idDmd = request.getParameter("idDmd");
//            DmdPrix demandePrix = new DmdPrix();
//            prerempli = (Proforma) demandePrix.genererProforma(idDmd, null);
//
//        }
//        ProformaDetails[] detFille = null;
//        if(request.getParameter("id")!=null && !request.getParameter("id").isEmpty()){
//            Proforma pro = new Proforma();
//            pro.setId(request.getParameter("id"));
//            prerempli = (Proforma) pro.getProforma(null);
//            if(prerempli!=null){
//                ProformaDetails [] temp = (ProformaDetails[]) prerempli.getFilleProforma();
//                detFille = new ProformaDetails[temp.length-1];
//                for (int i = 0; i < temp.length; i++) {
//                    if(temp[i].getIdProduit().compareToIgnoreCase(ConstanteLocation.id_produit_caution)!=0){
//                        detFille[i] = temp[i];
//                    }
//                }
//                prerempli.setIdOrigine(request.getParameter("id"));
//            }
//        }

        String idDevis = request.getParameter("idDevis");
       /*if(idDevis != null && !idDevis.isEmpty()){
            Devis devis = new Devis();
            devis.setId(idDevis);
            prerempli = (Proforma) devis.genererProforma(idDevis, null);
        }*/

        Liste[] liste = new Liste[2];
        Magasin m = new Magasin();
        liste[0] = new Liste("idMagasin",m,"val","id");
        //liste[0].setDefaut(ConstanteSocobis.MAGASIN);
        liste[1] = new Liste("idDevise",new caisse.Devise(),"val","id");

        pi.getFormu().changerEnChamp(liste);
        pi.getFormu().getChamp("idMagasin").setAutre("onchange=\"updateFille(event, 'formId')\"");
        pi.getFormu().getChamp("idMagasin").setLibelle("Magasin");
        pi.getFormu().getChamp("idReservation").setVisible(false);

        pi.getFormufle().getChamp("idProduit_0").setLibelle("Produit");
        pi.getFormufle().getChamp("pu_0").setLibelle("PU Brut");
        pi.getFormufle().getChamp("iddemandeprixfille_0").setLibelle("Demande de prix");
        pi.getFormufle().getChamp("compte_0").setLibelle("Compte");
        //pi.getFormufle().getChamp("idDevise_0").setLibelle("Devise");
        pi.getFormufle().getChamp("tva_0").setLibelle("TVA (En %)");
        //pi.getFormufle().getChamp("remise_0").setLibelle("Remise");
        pi.getFormufle().getChamp("designation_0").setLibelle("D&eacute;signation");
        pi.getFormufle().getChamp("qte_0").setLibelle("Quantit&eacute;");
        //pi.getFormufle().getChamp("unite_0").setVisible(false);
        pi.getFormufle().getChamp("unitelib_0").setLibelle("Unit&eacute;");
        pi.getFormufle().getChamp("remise_0").setLibelle("Remise (en %)");
        pi.getFormufle().getChamp("punet_0").setLibelle("PU Net");
        pi.getFormufle().getChamp("montantht_0").setLibelle("Montant HT");
        pi.getFormufle().getChamp("montantttc_0").setLibelle("Montant TTC");
        pi.getFormufle().getChampMulitple("datedebut").setVisible(false);

        pi.getFormufle().getChampMulitple("idDevise").setVisible(false);
        pi.getFormu().getChamp("idDevise").setLibelle("Devise");
        pi.getFormu().getChamp("idDevise").setDefaut(ConstanteSocobis.Devise);

        //pi.getFormufle().getChampMulitple("remise").setVisible(false);
        //affichage.Champ.setPageAppelComplete(pi.getFormufle().getChampFille("idProduit"),"produits.IngredientVente","id","AS_INGREDIENT_VENTE_LIB","prixunitaire;compte_vente;libelle;idunite","pu;compte;designation;unite");
        affichage.Champ.setPageAppelCompleteAWhere(pi.getFormufle().getChampFille("idProduit"),"produits.IngredientVente","id","AS_INGREDIENT_VENTE_LIB","prixunitaire;compte_vente;libelle;idunite;idunitelib","pu;compte;designation;unite;uniteLib"," AND 1>2");
        double tva = 0.0;
        if (request.getParameter("onchanged") != null && request.getParameter("onchanged").equals("true")){
            String idmagasin = request.getParameter("idMagasin");
            if(request.getParameter("idMagasin") != null && !request.getParameter("idMagasin").isEmpty()){
                session.setAttribute("idMagasin", request.getParameter("idMagasin"));
            }
            if(idmagasin == null || idmagasin.isEmpty()){
                idmagasin = (String) session.getAttribute("idMagasin");
            }
            Client c = null;
            if(request.getParameter("idClientlibelle")!=null && request.getParameter("idClientlibelle").split(" - ").length>0){
                String idclient = request.getParameter("idClientlibelle").split(" - ")[0];
                c = (Client)new Client().getById(idclient,"client",null);
                tva = c.getTaxe();
                session.setAttribute("idclient", idclient);
            }else{
                c = (Client)new Client().getById((String) session.getAttribute("idclient"),"client",null);
                tva = c.getTaxe();
            }
            if(c!=null){
                //affichage.Champ.setPageAppelCompleteAWhere(pi.getFormufle().getChampFille("idProduit"),"produits.IngredientVente","id","AS_INGREDIENT_VENTE_LIB","prixunitaire;compte_vente;libelle;idunite;idunitelib","pu;compte;designation;unite;uniteLib"," AND IDTYPECLIENT='"+c.getIdTypeClient()+"' AND IDMAGASIN='"+idmagasin+"'");
                affichage.Champ.setPageAppelCompleteAWhere(pi.getFormufle().getChampFille("idProduit"),"produits.IngredientVente","id","AS_INGREDIENT_VENTE_LIB","prixunitaire;compte_vente;libelle;idunite;idunitelib","pu;compte;designation;unite;uniteLib"," AND IDTYPECLIENT='"+c.getIdTypeClient()+"'");
            }
        }else{
            session.removeAttribute("idMagasin");
            session.removeAttribute("idclient");
        }

        for (int i = 0; i < pi.getNombreLigne(); i++) {
            pi.getFormufle().getChamp("qte_"+i).setAutre("onChange='calculerMontant("+i+")'");
            pi.getFormufle().getChamp("remise_"+i).setAutre("onChange='calculerMontant("+i+")'");
            pi.getFormufle().getChamp("tva_"+i).setAutre("onChange='calculerMontant("+i+")'");
            pi.getFormufle().getChamp("id_"+i).setAutre("readonly");
            //affichage.Champ.setPageAppelComplete(pi.getFormufle().getChampFille("idproduit"),"annexe.ProduitLib","id","PRODUIT_LIB","id;puVente;desce","id;pu;designation");
            pi.getFormufle().getChamp("iddemandeprixfille_"+i).setAutre("readonly");
            //pi.getFormufle().getChamp("unite_"+i).setAutre("readonly");
            pi.getFormufle().getChamp("unitelib_"+i).setAutre("readonly");
            pi.getFormufle().getChamp("compte_"+i).setAutre("readonly");
            pi.getFormufle().getChamp("punet_"+i).setAutre("readonly");
            pi.getFormufle().getChamp("montantht_"+i).setAutre("readonly");
            pi.getFormufle().getChamp("montantttc_"+i).setAutre("readonly");
            pi.getFormufle().getChamp("pu_"+i).setAutre("readonly");
            pi.getFormufle().getChamp("tva_"+i).setDefaut(tva+"");
            //pi.getFormufle().getChamp("iddevise_"+i).setAutre("readonly");
            //pi.getFormufle().getChamp("tva_"+i).setAutre("readonly");
        }

        affichage.Champ.setVisible(pi.getFormufle().getChampFille("id"),false);
        affichage.Champ.setVisible(pi.getFormufle().getChampFille("idDemandePrixFille"),false);
        affichage.Champ.setVisible(pi.getFormufle().getChampFille("idProforma"),false);
        affichage.Champ.setVisible(pi.getFormufle().getChampFille("idOrigine"),false);
        affichage.Champ.setVisible(pi.getFormufle().getChampFille("puAchat"),false);
        affichage.Champ.setVisible(pi.getFormufle().getChampFille("puVente"),false);
        affichage.Champ.setVisible(pi.getFormufle().getChampFille("puRevient"),false);
        affichage.Champ.setVisible(pi.getFormufle().getChampFille("tauxdechange"),false);
        affichage.Champ.setVisible(pi.getFormufle().getChampFille("compte"),false);
        affichage.Champ.setVisible(pi.getFormufle().getChampFille("unite"),false);
        //affichage.Champ.setVisible(pi.getFormufle().getChampFille("tva"),false);
        //affichage.Champ.setVisible(pi.getFormufle().getChampFille("remise"),false);

        String[] colOrdre = {"idProduit","designation","unitelib", "pu", "qte","remise","punet","tva","montantht","montantttc","unite", "iddemandeprixfille", "compte","datedebut"};
        pi.getFormufle().setColOrdre(colOrdre);

        pi.preparerDataFormu();

        //Variables de navigation
        String classeMere = "proforma.Proforma";
        String classeFille = "proforma.ProformaDetails";
        String butApresPost = "vente/proforma/proforma-fiche.jsp";
        String colonneMere = "idProforma";

        String[] ordre = {"daty"};
        pi.getFormu().setOrdre(ordre);

        pi.getFormu().makeHtmlInsertTabIndex();
        pi.getFormufle().makeHtmlInsertTableauIndex();
        String titre = "Saisie d'un Proforma";
        if(request.getParameter("acte")!=null){
            titre = "Modification d'un Proforma";
        } 
%>
<div class="content-wrapper">
    <h1><%= titre %></h1>
    <form id="formId"  class='container' action="<%=pi.getLien()%>?but=apresMultiple.jsp" method="post" >

        <%
            out.println(pi.getFormu().getHtmlInsert());
            %>
        <div class="col-md-12 cardradius">
            <h3 class="fontinter" style="background: white;padding: 16px;margin-top: 10px;border-radius: 16px;" >Total  : <span id="montanttotal">0</span><span id="deviseLibelle"> Ar</span></h3>
        </div>
        <div id="butfillejsp">
        <%
            out.println(pi.getFormufle().getHtmlTableauInsert());
        %>
        </div>
        <input name="acte" type="hidden" id="nature" value="insert">
        <input name="bute" type="hidden" id="bute" value="<%= butApresPost %>">
        <input name="classe" type="hidden" id="classe" value="<%= classeMere %>">
        <input name="classefille" type="hidden" id="classefille" value="<%= classeFille %>">
        <input name="nombreLigne" type="hidden" id="nombreLigne" value="<%= nombreLigne %>">
        <input name="colonneMere" type="hidden" id="colonneMere" value="<%= colonneMere %>">
    </form>

</div>
<script>
    function formatNumber(number) {
        return number.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }
    function calculerMontant(indice) {
        // Récupérer les valeurs des champs
        var pu = parseFloat(document.getElementById('pu_' + indice).value.replace(/\s/g, '')) || 0;
        var qte = parseFloat(document.getElementById('qte_' + indice).value.replace(/\s/g, '')) || 0;
        var remise = parseFloat(document.getElementById('remise_' + indice).value.replace(/\s/g, '')) || 0;
        var tva = parseFloat(document.getElementById('tva_' + indice).value.replace(/\s/g, '')) || 0;

        // Calculer le PU Net (PU après remise)
        var punet = pu - (pu * remise / 100) + ((pu * tva / 100));

        // Calculer le Montant HT
        var montantht = (pu - (pu * remise / 100)) * qte;

        // Calculer le Montant TTC (HT + TVA)
        var montantttc = punet *  qte;

        // Mettre à jour les champs calculés
        document.getElementById('pu_' + indice).value = formatNumber(pu);
        document.getElementById('punet_' + indice).value = formatNumber(punet);
        document.getElementById('montantht_' + indice).value = formatNumber(montantht);
        document.getElementById('montantttc_' + indice).value = formatNumber(montantttc);

        var val = 0;
        $('input[id^="montantttc_"]').each(function() {
            var montant = parseFloat($(this).val().replace(/\s/g, ''));
            if(!isNaN(montant)){
                val += montant;
            }
        });
        $("#montanttotal").html(Intl.NumberFormat('fr-FR', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        }).format(val));
    }
</script>
<%
} catch (Exception e) {
    e.printStackTrace();
%>
<script language="JavaScript">
    alert('<%=e.getMessage()%>');
    history.back();
</script>
<% }%>



