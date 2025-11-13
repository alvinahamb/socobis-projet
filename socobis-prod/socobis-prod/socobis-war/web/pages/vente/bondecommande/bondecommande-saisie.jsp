<%-- 
    Document   : bondecommande-saisie
    Created on : 17 juil. 2024, 16:27:57
    Author     : micha
--%>


<%@page import="bean.CGenUtil"%>
<%@page import="affichage.PageUpdateMultiple"%>
<%@page import="vente.*"%>
<%@page import="bean.UnionIntraTable"%>
<%@page import="user.UserEJB"%>
<%@page import="affichage.PageInsertMultiple"%>
<%@page import="java.util.Calendar"%>
<%@page import="affichage.Liste"%>
<%@page import="bean.TypeObjet"%>
<%@page import="affichage.PageInsert"%>
<%@page import="utilitaire.Utilitaire"%>
<%@page import="faturefournisseur.ModePaiement"%>
<%@page import="annexe.Unite"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="proforma.ProformaLib" %>
<%@ page import="proforma.Proforma" %>
<%@ page import="proforma.ProformaDetails" %>
<%@ page import="proforma.ProformaDetailsLib" %>
<%@ page import="client.Client" %>
<%
    try{
    UserEJB u = null;
    u = (UserEJB) session.getValue("u");
    BonDeCommande mere = new BonDeCommande();
    BonDeCommandeFIlleCpl fille = new BonDeCommandeFIlleCpl();
    fille.setNomTable("BONDECOMMANDE_CLIENT_FILLE_V");
    int nombreLigne = 10;
    PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, nombreLigne, u);
    pi.setLien((String) session.getValue("lien"));
    pi.getFormu().getChamp("dateBesoin").setLibelle("Date de besoin");
    pi.getFormu().getChamp("daty").setLibelle("Date");
    pi.getFormu().getChamp("remarque").setLibelle("Remarque");
    pi.getFormu().getChamp("designation").setLibelle("D&eacute;signation");
    pi.getFormu().getChamp("idClient").setLibelle("Client");
    pi.getFormu().getChamp("idClient").setAutre("onchange=\"updateFille(event, 'formId')\"");
    pi.getFormu().getChamp("reference").setLibelle("R&eacute;f&eacute;rence");
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("idDevise").setVisible(false);
    pi.getFormu().getChamp("idDevise").setDefaut("AR");
    pi.getFormu().getChamp("idProforma").setAutre("readonly");
    pi.getFormu().getChamp("idProforma").setLibelle("ID Proforma");
    pi.getFormu().getChamp("modelivraison").setLibelle("Mode de livraison");

    Liste[] liste = new Liste[3];
    ModePaiement mp = new ModePaiement();
    liste[0] = new Liste("modepaiement",mp,"val","id");
    //liste[1] = new Liste("idDevise",new caisse.Devise(),"val","id");
    //liste[1].setDefaut("AR");
    //liste[1].setLibelle("Devise");
    //liste[1].setLibelle("Devise");
    liste[1] = new Liste("idMagasin",new magasin.Magasin(),"val","id");
    liste[1].setLibelle("Magasin");

    Liste listemode = new Liste("modelivraison");

    String [] affVal = new String[2];
    String [] aff = new String[2];
    aff = new String[]{"LIVRAISON","RECUPERATION"};
    affVal = new String[]{"1","2"};

    listemode.ajouterValeur(affVal,aff);
    liste[2] = listemode;

    pi.getFormu().changerEnChamp(liste);

    String [] ordre = {"daty","designation","remarque","reference","idDevise","idMagasin","idClient","modepaiement"};
    pi.getFormu().setOrdre(ordre);
    pi.getFormu().getChamp("idMagasin").setAutre("onchange=\"updateFille(event, 'formId')\"");
    pi.getFormu().getChamp("modepaiement").setLibelle("Mode de paiement");
    pi.getFormu().getChamp("modelivraison").setLibelle("Mode de livraison");
    //pi.getFormu().getChamp("fournisseur").setPageAppel("choix/fournisseur/fournisseur-choix.jsp","fournisseur;fournisseurlibelle");
    pi.getFormu().getChamp("idClient").setPageAppelComplete("client.Client","id","Client");
    pi.getFormu().getChamp("idClient").setPageAppelInsert("client/client-saisie.jsp","idClient;idClientLibelle","id;nom");
    affichage.Champ.setPageAppelCompleteAWhere(pi.getFormufle().getChampFille("produit"),"produits.IngredientVente","id","AS_INGREDIENT_VENTE_LIB","prixunitaire;compte_vente;libelle;idunite;idunitelib","pu;compte;designation;unite;uniteLib"," AND 1>2");

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
            //if(idmagasin!=null && c!=null){
            //    affichage.Champ.setPageAppelCompleteAWhere(pi.getFormufle().getChampFille("produit"),"produits.IngredientVente","id","AS_INGREDIENT_VENTE_LIB","prixunitaire;compte_vente;libelle;idunite;idunitelib","pu;compte;designation;unite;uniteLib"," AND IDTYPECLIENT='"+c.getIdTypeClient()+"' AND IDMAGASIN='"+idmagasin+"'");
            //}
            if(c!=null){
                //affichage.Champ.setPageAppelCompleteAWhere(pi.getFormufle().getChampFille("idProduit"),"produits.IngredientVente","id","AS_INGREDIENT_VENTE_LIB","prixunitaire;compte_vente;libelle;idunite;idunitelib","pu;compte;designation;unite;uniteLib"," AND IDTYPECLIENT='"+c.getIdTypeClient()+"' AND IDMAGASIN='"+idmagasin+"'");
                affichage.Champ.setPageAppelCompleteAWhere(pi.getFormufle().getChampFille("idProduit"),"produits.IngredientVente","id","AS_INGREDIENT_VENTE_LIB","prixunitaire;compte_vente;libelle;idunite;idunitelib","pu;compte;designation;unite;uniteLib"," AND IDTYPECLIENT='"+c.getIdTypeClient()+"'");
            }
        }else{
            session.removeAttribute("idMagasin");
            session.removeAttribute("idclient");
        }

    //Nommage et visibilite 
        
    for (int i = 0; i < nombreLigne; i++) {
        pi.getFormufle().getChamp("quantite_"+i).setAutre("onChange='calculerMontant("+i+")'");
        pi.getFormufle().getChamp("remise_"+i).setAutre("onChange='calculerMontant("+i+")'");
        pi.getFormufle().getChamp("tva_"+i).setAutre("onChange='calculerMontant("+i+")'");
        pi.getFormufle().getChamp("produit_" + i).setLibelle("Produit");
        pi.getFormufle().getChamp("idDevise_"+i).setDefaut("AR");
        pi.getFormufle().getChamp("unite_"+i).setAutre("readonly");
        pi.getFormufle().getChamp("unitelib_"+i).setAutre("readonly");
        pi.getFormufle().getChamp("tva_"+i).setDefaut(tva+"");
        pi.getFormufle().getChamp("punet_"+i).setAutre("readonly");
        pi.getFormufle().getChamp("montantht_"+i).setAutre("readonly");
        pi.getFormufle().getChamp("montantttc_"+i).setAutre("readonly");
        pi.getFormufle().getChamp("pu_"+i).setAutre("readonly");
    }

    //affichage.Champ.setPageAppelComplete(pi.getFormufle().getChampMulitple("produit").getListeChamp(), "produits.IngredientsLib", "id", "as_ingredients_lib", "pv;tva;unite","pu;tva;unite");



    pi.getFormufle().getChamp("quantite_0").setLibelle("Quantit&eacute;");
    pi.getFormufle().getChamp("designation_0").setLibelle("D&eacute;signation");
    pi.getFormufle().getChamp("pu_0").setLibelle("Prix Unitaire");
    pi.getFormufle().getChamp("montant_0").setLibelle("Montant");
    pi.getFormufle().getChamp("tva_0").setLibelle("TVA (En %)");
    pi.getFormufle().getChamp("remise_0").setLibelle("Remise (En %)");
    pi.getFormufle().getChamp("idDevise_0").setLibelle("Devise");
    pi.getFormufle().getChamp("punet_0").setLibelle("PU Net");
    pi.getFormufle().getChamp("montantht_0").setLibelle("Montant HT");
    pi.getFormufle().getChamp("montantttc_0").setLibelle("Montant TTC");
    affichage.Champ.setVisible(pi.getFormufle().getChampFille("idDevise"), false);
    affichage.Champ.setVisible(pi.getFormufle().getChampFille("idbc"),false);
    affichage.Champ.setVisible(pi.getFormufle().getChampFille("id"),false);
    affichage.Champ.setVisible(pi.getFormufle().getChampFille("montant"),false);
    affichage.Champ.setVisible(pi.getFormufle().getChampFille("unite"),false);
//    pi.getFormufle().getChampMulitple("unite").setVisible(false);
    //pi.getFormufle().getChamp("unite_0").setLibelle("Unit&eacute;");
    pi.getFormufle().getChamp("unitelib_0").setLibelle("Unit&eacute;");

    BonDeCommande ocr = (BonDeCommande) session.getAttribute("ocr");
    if(ocr!=null){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = ocr.getDaty().toLocalDate();
        String formattedDate = localDate.format(formatter);
        pi.getFormu().getChamp("designation").setDefaut(ocr.getDesignation());
        pi.getFormu().getChamp("daty").setDefaut(formattedDate);
        if(ocr.getIdClient()!=null){
            pi.getFormu().getChamp("idClient").setDefaut(ocr.getIdClient());
        }
        if(ocr.getIdMagasin()!=null){
            pi.getFormu().getChamp("idMagasin").setDefaut(ocr.getIdMagasin());
        }
        pi.setDefautFille(ocr.getFille());
    }
    session.removeAttribute("ocr");

        String idProforma = request.getParameter("idProforma");
        if (idProforma != null && !idProforma.trim().isEmpty()) {
            pi.getFormu().getChamp("idProforma").setDefaut(request.getParameter("idProforma"));
            //pi.getFormu().getChamp("idMagasin").setAutre("disabled");
            Proforma proforma = new Proforma();
            proforma.setId(idProforma);
            BonDeCommande bc = proforma.createBonDeCommande();
            ProformaDetailsLib[] details = proforma.getFilleProformaLib();
            if (details != null && details.length > 0) {
                BonDeCommandeFIlleCpl[] lignes = new BonDeCommandeFIlleCpl[details.length];
                for (int i = 0; i < details.length; i++) {
                    ProformaDetailsLib detail = details[i];
                    System.err.println("=========getMontantht====+++>"+detail.getMontantht());
                    System.err.println("========getPunet=====+++>"+detail.getPunet());
                    lignes[i] = detail.createBonDeCommandeFilleLib();
                }
                pi.setDefautFille(lignes);
            }
            pi.getFormu().setDefaut(bc);
            pi.getFormu().getChamp("idMagasin").setDefaut(bc.getIdMagasin());
        }

      String[] colOrdre = {"id","produit","designation","unitelib","pu","quantite","remise","punet","tva","montantht","montantttc","idDevise","unite"};
      pi.getFormufle().setColOrdre(colOrdre);


    //Variables de navigation
    String classeMere = "vente.BonDeCommande";
    String classeFille = "vente.BonDeCommandeFille";
    String butApresPost = "vente/bondecommande/bondecommande-fiche.jsp";
    String colonneMere = "idbc";
    //Preparer les affichages
     pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
    pi.getFormufle().makeHtmlInsertTableauIndex();
       
%>
<div class="content-wrapper">
    <!-- A modifier -->
    <h1>Saisie d&rsquo;un bon de commande client</h1>
    <!--  -->
    <form id="formId" class='container' action="<%=pi.getLien()%>?but=apresMultiple.jsp" method="post" >
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
    document.addEventListener('DOMContentLoaded', function() {
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
        const deviseSelect = document.getElementById('idDevise');
        deviseSelect.addEventListener('change', function() {
            const deviseSelectionne = this.value;
            for (let i = 0; i <= 10; i++) {
                const champDevise = document.getElementById('idDevise_' + i);
                if (champDevise) {
                    champDevise.value = deviseSelectionne;
                    if (champDevise.tagName === 'SELECT') {
                        for (let j = 0; j < champDevise.options.length; j++) {
                            if (champDevise.options[j].value === deviseSelectionne) {
                                champDevise.selectedIndex = j;
                                break;
                            }
                        }
                    }
                    const event = new Event('change');
                    champDevise.dispatchEvent(event);
                }
            }
        });
    });
</script>
<script>
    function formatNumber(number) {
        return number.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }
    function calculerMontant(indice) {
        // Récupérer les valeurs des champs
        var pu = parseFloat(document.getElementById('pu_' + indice).value.replace(/\s/g, '')) || 0;
        var qte = parseFloat(document.getElementById('quantite_' + indice).value.replace(/\s/g, '')) || 0;
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

