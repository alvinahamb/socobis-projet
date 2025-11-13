

<%@page import="faturefournisseur.As_BonDeLivraison_Fille_Cpl"%>
<%@ page import="affichage.*" %>

<%
    try {
        String lien = (String) session.getValue("lien");
        As_BonDeLivraison_Fille_Cpl t = new As_BonDeLivraison_Fille_Cpl();
        t.setNomTable("rapprochement_bf_fournisseur");
        String listeCrt[] = {};
        String listeInt[] = {};
        String libEntete[] = {"id","produit", "produitlib", "quantite", "qteEntree","resteAEntrer", "unitelib"};

        PageRecherche pr = new PageRecherche(t, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
        pr.setUtilisateur((user.UserEJB) session.getValue("u"));
        pr.setLien(lien);
        String id =request.getParameter("id");
        if(id != null) {
            pr.setAWhere(" and numbl='"+id+"'");
        }
        String[] colSomme = null;
        pr.setNpp(10);
        pr.creerObjetPage(libEntete, colSomme);
%>

<div class="box-body">
    <%
        String libEnteteAffiche[] = {"Id", "ID Produit", "Produit", "Quantit&eacute;", "Quantit&eacute; &agrave; entrer", "Reste &agrave; entrer", "Unit&eacute;"};
        pr.getTableau().setLibelleAffiche(libEnteteAffiche);
        if (pr.getTableau().getHtml() != null) {
            out.println(pr.getTableau().getHtml());
        } else {
    %><center><h4>Aucune donne trouvee</h4></center><%
    }


%>
    <div class="box-footer">

    </div>
</div>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }%>

