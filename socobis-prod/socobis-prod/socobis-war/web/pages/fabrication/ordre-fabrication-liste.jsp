<%--
  Created by IntelliJ IDEA.
  User: tokiniaina_judicael
  Date: 2025-04-01
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>

<%@page import="affichage.PageRecherche"%>
<%@ page import="fabrication.Of" %>
<%@ page import="java.util.Map" %> 
<%@ page import="java.util.HashMap" %>
<%@ page import="utilitaire.Utilitaire" %>

<% try{
    Of t = new Of();
    t.setNomTable("OFABLIB");
    String listeCrt[] = {"id", "lancepar", "cible", "remarque", "libelle", "besoin", "daty"};
    String listeInt[] = {"daty","besoin"};
    String libEntete[] = {"id", "daty", "besoin", "libelle", "lancepar", "cible", "remarque"};
    PageRecherche pr = new PageRecherche(t, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setTitre("Liste des ordres de fabrication");
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("fabrication/ordre-fabrication-liste.jsp");
    pr.getFormu().getChamp("daty1").setLibelle("Date min");
    pr.getFormu().getChamp("daty2").setLibelle("Date max");
    pr.getFormu().getChamp("besoin1").setLibelle("Date de Besoin min");
    pr.getFormu().getChamp("besoin2").setLibelle("Date de Besoin max");
    pr.getFormu().getChamp("lancepar").setLibelle("Lanc&eacute; par");
    pr.getFormu().getChamp("libelle").setLibelle("libell&eacute;");
    String[] colSomme = null;
    pr.getFormu().getChamp("daty1").setDefaut(Utilitaire.getDebutSemaineString());
    pr.getFormu().getChamp("daty2").setDefaut(Utilitaire.getFinSemaineString());
    pr.getFormu().getChamp("besoin1").setDefaut(Utilitaire.getDebutSemaineString());
    pr.getFormu().getChamp("besoin2").setDefaut(Utilitaire.getFinSemaineString());

    if(request.getParameter("id")!=null && request.getParameter("id").compareToIgnoreCase("")!=0) {
        pr.getFormu().getChamp("id").setDefaut(request.getParameter("id"));
    }
    if(request.getParameter("etaty")!=null && request.getParameter("etaty").compareToIgnoreCase("")!=0) {
        pr.setAWhere(" and etat>=" + request.getParameter("etaty"));
    }
    
    pr.creerObjetPage(libEntete, colSomme);
    Map<String,String> lienTab=new HashMap();
    lienTab.put("modifier",pr.getLien() + "?but=fabrication/ordre-fabrication-modif.jsp");  
    pr.getTableau().setLienClicDroite(lienTab);

    //Definition des lienTableau et des colonnes de lien
    String lienTableau[] = {pr.getLien() + "?but=fabrication/ordre-fabrication-fiche.jsp"};
    String colonneLien[] = {"id"}; // Colonne contenant un lien, passé comme paramètre dans l'URL
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setColonneLien(colonneLien);
    //Remplacer le nom de l'attribut passé dans l'URL, par exemple passer 'idObjet' au lieu de 'id' du colonneLien
    String[] attributLien = {"id"};
    pr.getTableau().setAttLien(attributLien);

    //Definition des libelles à afficher
    String libEnteteAffiche[] = {"ID", "Date","Date de besoin","D&eacute;signation","Lanc&eacute; par", "Cible", "Remarque"};
    pr.getTableau().setLibelleAffiche(libEnteteAffiche);
    pr.getTableau().setLienFille("fabrication/inc/ordre-fabrication-details.jsp&id=");
%>

<div class="content-wrapper">
    <section class="content-header">
        <h1><%= pr.getTitre() %></h1>
    </section>
    <section class="content">
        <form action="<%=pr.getLien()%>?but=<%= pr.getApres() %>" method="post">
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



