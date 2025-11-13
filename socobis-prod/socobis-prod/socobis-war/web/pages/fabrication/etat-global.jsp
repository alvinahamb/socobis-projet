<%--
  Created by IntelliJ IDEA.
  User: safidy
  Date: 21/08/2025
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>

<%@page import="affichage.PageRecherche"%>
<%@ page import="fabrication.*" %>
<%@ page import="produits.Ingredients" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.sql.Date" %>
<%@ page import="historique.MapUtilisateur" %>
<%@ page import="utils.ConstanteSocobis" %>
<%@ page import="user.*" %>
<%@ page import="bean.*" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="static java.time.DayOfWeek.MONDAY" %>
<%@ page import="static java.time.temporal.TemporalAdjusters.previousOrSame" %>
<%@ page import="static java.time.DayOfWeek.SUNDAY" %>
<%@ page import="static java.time.temporal.TemporalAdjusters.nextOrSame" %>

<% try{
    LocalDate today = LocalDate.now();
    LocalDate monday = today.with(previousOrSame(MONDAY));
    LocalDate sunday = today.with(nextOrSame(SUNDAY));

    OfFilleCpl t = new OfFilleCpl();
    t.setNomTable("OfFilleLibStock");
    t.calculerRevient(null);
    String listeCrt[] = {"libelle", "datybesoin"};
    String listeInt[] = {"datybesoin"};
    UserEJB user = (user.UserEJB) session.getValue("u");
    MapUtilisateur mapUser = user.getUser();
    String libEntete[] = new String[]{"id", "idingredients", "idunite", "libelleexacte","qte"};
    if(mapUser.getIdrole().equals(ConstanteSocobis.CHEFFABR_RANG) || mapUser.getIdrole().equals(ConstanteSocobis.DG_RANG)) {
        libEntete = new String[]{"id", "idingredients", "idunite", "libelleexacte","qte","qtefabrique","qtereste","pv","montantentree","montantsortie","tauxrevient","purevient","montantRevient"};
    }

    PageRecherche pr = new PageRecherche(t, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.getFormu().getChamp("datybesoin1").setLibelle("Date min");
    pr.getFormu().getChamp("datybesoin1").setDefaut(utilitaire.Utilitaire.datetostring(Date.valueOf(monday)));
    pr.getFormu().getChamp("datybesoin2").setLibelle("Date max");
    pr.getFormu().getChamp("datybesoin2").setDefaut(utilitaire.Utilitaire.datetostring(Date.valueOf(sunday)));
    pr.getFormu().getChamp("datybesoin2").setDefaut(utilitaire.Utilitaire.dateDuJour());
    String[] colSomme = {};

    pr.creerObjetPage(libEntete, colSomme);
    OfFille[] listeFille=(OfFille[]) pr.getTableau().getData();
    if(listeFille.length>0) {
        Of o = new Of();
        o.setFille(listeFille);
        o.calculerRevient(null);
    }

    String lienTableau[] = {pr.getLien() + "?but=fabrication/ordre-fabrication-details-fiche.jsp", pr.getLien() + "?but=produits/as-ingredients-fiche.jsp"};
    String colonneLien[] = {"id", "idingredients"};
    String[] attributLien = {"id", "id"};
    String colonneModal[] = {"id","idingredients"};
    pr.getTableau().setAttLien(attributLien);
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setColonneLien(colonneLien);
    pr.getTableau().setAttLien(attributLien);
    pr.getTableau().setModalOnClick(true,colonneModal);


    Map<String,String> lienTab=new HashMap();
    lienTab.put("Fabriquer-idOffille",pr.getLien() + "?but=fabrication/fabrication-saisie.jsp");
    lienTab.put("Fabriquer un par un-idOffille",pr.getLien() + "?but=fabrication/fabrication-saisie.jsp&unParUn=true");
    lienTab.put("Situation globale",pr.getLien() + "?but=fabrication/offille-situation-globale.jsp");
    pr.getTableau().setLienClicDroite(lienTab);

    pr.getTableau().setLienFille("fabrication/inc/liste-fabrication-of.jsp&id=");
    pr.getTableau().transformerDataString();

    String libEnteteAffiche[] = new String[]{"id", "Composants", "Libelle","D&eacute;signation","Qte Ordre"};
    if(mapUser.getIdrole().equals(ConstanteSocobis.CHEFFABR_RANG) || mapUser.getIdrole().equals(ConstanteSocobis.DG_RANG)) {
        libEnteteAffiche = new String[]{"id", "Composants", "Unite","D&eacute;signation","Qte Ordre","Qte Fabriqu&eacute;","Qte reste","Prix de vente","Valeur Fabriqu&eacute;s","D&eacute;pense Fabrication","Taux de revient ( en %)","PU revient","Montant Th&eacute;orique"};
    }
    pr.getTableau().setLibelleAffiche(libEnteteAffiche);
    String enteteRecap[] = {"","","Taux"};
    pr.getTableauRecap().setLibeEntete(enteteRecap);
%>
<script>
    function changerDesignation() {
        document.getElementById("bdlc-liste--form").submit();
    }
</script>

<div class="content-wrapper">
    <section class="content-header">
        <h1>&Eacute;tat global des OF</h1>
    </section>
    <section class="content">
        <form action="<%=pr.getLien()%>?but=<%= pr.getApres() %>" id="bdlc-liste--form" method="post">
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
    </section>
</div>
<%
    }catch(Exception e){

        e.printStackTrace();
    }
%>