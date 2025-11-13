<%@page import="vente.BonDeCommandeCpl"%>
<%@page import="affichage.PageRecherche"%>
<%@page import="magasin.Magasin"%>
<%@page import="affichage.Liste"%>
<%@page import="faturefournisseur.ModePaiement"%>



<% try{ 
    BonDeCommandeCpl bdc = new BonDeCommandeCpl();
    String nomTable = "BONDECOMMANDE_CLIENT_CPL";
        if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("") != 0) {
            nomTable = request.getParameter("etat");
        }
       bdc.setNomTable(nomTable);
    String listeCrt[] = {"id","remarque","designation","reference","modepaiement","daty","idclientlib"};
    String listeInt[] = {"daty"};
    String libEntete[] = {"id","daty","idclientlib","designation","reference","modepaiementlib","remarque","nbfacture","etatlib"};
    PageRecherche pr = new PageRecherche(bdc, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setTitre("Liste des bons de commande client");
     
    // Changer en Liste
    // Initialisation Liste

    Liste[] liste = new Liste[1];
    ModePaiement mp = new ModePaiement();
    liste[0] = new Liste("modepaiement",mp,"val","id");
    pr.getFormu().changerEnChamp(liste);

    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("vente/bondecommande-liste.jsp");
   
    pr.getFormu().getChamp("id").setLibelle("Id");
    pr.getFormu().getChamp("remarque").setLibelle("Remarque");
    pr.getFormu().getChamp("designation").setLibelle("D&eacute;signation");
    pr.getFormu().getChamp("reference").setLibelle("r&eacute;f&eacute;rence");
     pr.getFormu().getChamp("modepaiement").setLibelle("Mode de  paiement");
    pr.getFormu().getChamp("daty1").setLibelle("Date min");
    pr.getFormu().getChamp("idclientlib").setLibelle("Client");
    pr.getFormu().getChamp("daty1").setDefaut(utilitaire.Utilitaire.dateDuJour());
    pr.getFormu().getChamp("daty2").setLibelle("Date max");
    pr.getFormu().getChamp("daty2").setDefaut(utilitaire.Utilitaire.dateDuJour());
    //  pr.getFormu().getChamp("daty").setLibelle("daty");

    if(request.getParameter("id")!=null && request.getParameter("id").compareToIgnoreCase("")!=0) {
        pr.getFormu().getChamp("id").setDefaut(request.getParameter("id"));
    }
    if(request.getParameter("etaty")!=null && request.getParameter("etaty").compareToIgnoreCase("")!=0) {
        pr.setAWhere(" and etat>=" + request.getParameter("etaty"));
    }


    String[] colSomme = null;
    pr.creerObjetPage(libEntete, colSomme);
    //Definition des lienTableau et des colonnes de lien
    String lienTableau[] = {pr.getLien() + "?but=vente/bondecommande/bondecommande-fiche.jsp"};    
    String colonneLien[] = {"id"};
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setColonneLien(colonneLien);
    pr.getTableau().setLienFille("vente/bondecommande/inc/bondecommande-liste-detail.jsp&id=");
    String libEnteteAffiche[] = {"Id","Date","Client","D&eacute;signation","r&eacute;f&eacute;rence","Mode de paiement","Remarque","Nombre de Facture","&Eacute;tat"};
    pr.getTableau().setLibelleAffiche(libEnteteAffiche);
    String[] etatVal = {"BONDECOMMANDE_CLIENT_CPL","BONDECOMMANDE_CLIENT_CPL_C", "BONDECOMMANDE_CLIENT_CPL_V", "BONDECOMMANDE_CLIENT_CPL_A"};
    String[] etatAff = {"Tous","Cr&eacute;&eacute;(s)","Valid&eacute;(s)","Annul&eacute;(s)"};
%>
<script>
    function changerDesignation() {
        document.getElementById("bdc-liste--form").submit();
    }
</script>
<div class="content-wrapper">
    <section class="content-header">
        <h1><%= pr.getTitre() %></h1>
    </section>
    <section class="content">
        <form action="<%=pr.getLien()%>?but=<%= pr.getApres() %>" id="bdc-liste--form" method="post">
            <%
                out.println(pr.getFormu().getHtmlEnsemble());
            %>
            <div class="row col-md-12">
                <div class="col-md-4"></div>
              <div class="col-md-offset-5">
                <div class="form-group">
                    &Eacute;tat :
                    <select name="etat" class="champ-select" id="etat" onchange="changerDesignation()" >
                        <% for (int i = 0; i < etatVal.length; i++) {%>
                        <option value="<%=etatVal[i]%>" <%= etatVal[i].equalsIgnoreCase(bdc.getNomTable()) ? "selected " : ""%>>
                            <%=etatAff[i]%>
                        </option>
                        <%}%>
                    </select>
                </div>
            </div>

                <div class="col-md-4"></div>
            </div>
        </form>
        <%
            out.println(pr.getTableauRecap().getHtml());%>
        <br>
        <%
            out.println(pr.getTableau().getHtml());
        %>
    </section>
    
</div>
    <%
    }catch(Exception e){

        e.printStackTrace();
    }
%>
