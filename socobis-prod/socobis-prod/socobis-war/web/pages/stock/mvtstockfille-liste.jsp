<%@page import="stock.*"%>
<%@page import="affichage.PageRecherche"%>
<%@page import="magasin.Magasin"%>
<%@page import="affichage.Liste"%>
<%@page import="stock.TypeMvtStock"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@page import="user.UserEJB"%>

<% try{
    UserEJB u = (UserEJB) session.getValue("u");
    Magasin mag = u.getMagasin();
    MvtStockFilleLib stock = new MvtStockFilleLib();
    stock.setNomTable("mvtstockfillelib");

    String listeCrt[] = {"id","idMvtStock","idProduit","idMagasin","idProduitlib","daty", "mvtsrc"};
    String listeInt[] = {"daty"};
    String libEntete[] = {"id","idProduit","idProduitlib","idMagasinLib","idMvtStock","entree","sortie","daty","pu", "montant","mvtsrc"};
    PageRecherche pr = new PageRecherche(stock, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setTitre("Liste des mouvements de stocks fille");
    if(mag != null){
        pr.setAWhere("AND IDMAGASIN='"+mag.getId()+"'");
    }
    Liste[] dropDowns = new Liste[1];
    dropDowns[0] = new Liste( "idMagasin", new Magasin(), "val" , "id" );

    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("stock/mvtstockfille-liste.jsp");
    pr.getFormu().changerEnChamp(dropDowns);
    pr.getFormu().getChamp("id").setLibelle("Id");
    pr.getFormu().getChamp("idProduitlib").setLibelle("D&eacute;signation");
    pr.getFormu().getChamp("idMagasin").setLibelle("Magasin");
    pr.getFormu().getChamp("idProduit").setLibelle("ID Produit");
    pr.getFormu().getChamp("idMvtStock").setLibelle("ID Mouvement de Stock");
    pr.getFormu().getChamp("daty1").setLibelle("Date min");
    pr.getFormu().getChamp("daty1").setDefaut(utilitaire.Utilitaire.dateDuJour());
    pr.getFormu().getChamp("daty2").setLibelle("Date max");
    pr.getFormu().getChamp("mvtsrc").setLibelle("Mouvement source");
    pr.getFormu().getChamp("daty2").setDefaut(utilitaire.Utilitaire.dateDuJour());
    if(mag != null){
        pr.getFormu().getChamp("idMagasin").setAutre("disabled");
        pr.getFormu().getChamp("idMagasin").setDefaut(mag.getId());
    }
    String[] colSomme = {"entree","sortie"};
    pr.creerObjetPage(libEntete, colSomme);
    String[] libelleSomme = {"","Nombre","Somme d'entr&eacute;e","somme de sortie"};
    pr.getTableauRecap().setLibeEntete(libelleSomme);
    //Definition des lienTableau et des colonnes de lien
    String lienTableau[] = {pr.getLien() + "?but=stock/mvtstock-fiche.jsp", pr.getLien() + "?but=produits/as-ingredients-fiche.jsp",pr.getLien() + "?but=stock/mvtstock-fiche.jsp"};
    String colonneLien[] = {"idmvtstock","idProduit", "mvtsrc"};
    String attributLien[] = {"id","id","id"};
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setAttLien(attributLien);
    pr.getTableau().setColonneLien(colonneLien);
    String libEnteteAffiche[] = {"ID","ID Produit","D&eacute;signation","Magasin","ID Mouvement de Stock","Entr&eacute;e","Sortie","Date","PU", "Montant","Mouvement source"};
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
        %>
    </section>
</div>
<%
    }catch(Exception e){

        e.printStackTrace();
    }
%>



