<%--
  Created by IntelliJ IDEA.
  User: Tsinjoniaina
  Date: 25/07/2025
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@page import="stock.TransfertStockCpl"%>
<%@page import="affichage.PageRecherche"%>
<%@page import="affichage.Liste"%>
<%@page import="magasin.Magasin"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="user.UserEJB" %>
<% try{
    TransfertStockCpl stock = new TransfertStockCpl();

    String listeCrt[] = {"id","designation","idMagasinDepart","idMagasinArrive","daty"};
    String listeInt[] = {"daty"};
    String libEntete[] = {"id","designation","idMagasinDepartlib","idMagasinArrivelib","daty","etatlib"};
    PageRecherche pr = new PageRecherche(stock, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setTitre("Liste des transferts Envoy&eacute;s");

    // Initialisation Liste
    Liste[] listes = new Liste[1];
    Magasin m = new Magasin();
    m.setNomTable("magasinpoint");
    listes[0] = new Liste("idMagasinArrive", m, "val", "id");

    UserEJB userEJB = (user.UserEJB) session.getValue("u");
    pr.setUtilisateur(userEJB);
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("stock/transfertstock/transfertstock-depart-liste.jsp");
    pr.getFormu().changerEnChamp( listes );
    pr.getFormu().getChamp("id").setLibelle("Id");
    pr.getFormu().getChamp("designation").setLibelle("D&eacute;signation");
    pr.getFormu().getChamp("idMagasinDepart").setVisible(false);
    pr.getFormu().getChamp("idMagasinArrive").setLibelle("Magasin d'arriver");
    pr.getFormu().getChamp("daty1").setLibelle("Date min");
    pr.getFormu().getChamp("daty1").setDefaut(utilitaire.Utilitaire.dateDuJour());
    pr.getFormu().getChamp("daty2").setLibelle("Date max");
    pr.getFormu().getChamp("daty2").setDefaut(utilitaire.Utilitaire.dateDuJour());

    Magasin magasin = userEJB.getMagasin();
    if(magasin!=null){
        pr.setAWhere(" and idMagasinDepart = '"+magasin.getId()+"'");
    }

    String[] colSomme = null;
    pr.creerObjetPage(libEntete, colSomme);

    Map<String,String> lienTab=new HashMap();
    lienTab.put("modifier",pr.getLien() + "?but=stock/transfertstock/transfertstock-saisie.jsp&acte=update");
    pr.getTableau().setLienClicDroite(lienTab);

    //Definition des lienTableau et des colonnes de lien
    String lienTableau[] = {pr.getLien() + "?but=stock/transfertstock/transfertstock-fiche.jsp"};
    String colonneLien[] = {"id"};
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setColonneLien(colonneLien);
    String libEnteteAffiche[] = {"id","D&eacute;signation","Magasin de d&eacute;part","Magasin d'arriver","Date","&Eacute;tat"};
    pr.getTableau().setLibelleAffiche(libEnteteAffiche);
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
