<%@page import="user.UserEJB"%>
<%@page import="bean.*"%>
<%@page import="affichage.PageRecherche"%>
<%@ page import="paie.edition.PaieEditionDetails" %>
<%@ page import="affichage.Liste" %>

<% try {
    PaieEditionDetails tiers = new PaieEditionDetails();
    tiers.setNomTable("det_paie_edit_final_pdf_conid");
    String listeCrt[] = {"id","nom" , "poste","moislib","annee","matricule"};
    String listeInt[] = {};
    String libEntete[] ={
            "id","annee","moislib","dateembauche","salaire_de_base","taux","cnaps","imposable",
            "indemnitedepresentationbrut","indemnite_de_transport","irsa","irsanet", "netaprescalcul",
            "reductionpourpersonnesacharge"
    };
    PageRecherche pr = new PageRecherche(tiers, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setTitre("Liste des &eacute;ditions de paie");
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("paie/editions/paieedition-liste.jsp");




    pr.getFormu().getChamp("moislib").setLibelle("Mois");
    pr.getFormu().getChamp("annee").setDefaut((utilitaire.Utilitaire.getAnneeEnCours()+""));
    pr.getFormu().getChamp("annee").setLibelle("Ann&eacutee");
    String[] colSomme = null;
    pr.creerObjetPage(libEntete, colSomme);

    String lienTableau[] = {pr.getLien() + "?but=paie/editions/paieedition-fiche.jsp"};
    String colonneLien[] = {"id"};
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setColonneLien(colonneLien);

    String libEnteteAffiche[] = {
            "id","ann&eacute;e","mois", "date d'embauche", "salaire de base","taux horaire","cnaps","imposables",
            "Indemnit&eacute;s diverses fixes","primes","irsa","irsa NET","NET apr&egrave;s calcul", "Abattements"
    };
    pr.getTableau().setLibelleAffiche(libEnteteAffiche);
   
%>
<div class="content-wrapper">

    <div class="row">
        <section class="content-header">
            <h1><%= pr.getTitre() %></h1>
        </section>

    </div>
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <form action="<%=pr.getLien()%>?but=<%= pr.getApres() %>" method="post" name="incident" id="incident">
                    <%
                        out.println(pr.getFormu().getHtmlEnsemble());
                    %>

                </form>
                <%
                    out.println(pr.getTableauRecap().getHtml());%>
                <br>
                <%
                    out.println(pr.getTableau().getHtml());
                    out.println(pr.getBasPage());
                %>
            </div>
        </div>
    </section>

</div>
<script>
    function changerDesignation() {
        document.incident.submit();
    }
</script>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

