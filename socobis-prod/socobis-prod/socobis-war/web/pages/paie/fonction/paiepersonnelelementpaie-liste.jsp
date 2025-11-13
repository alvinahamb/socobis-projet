<%-- 
    Document   : paiepersonnelelementpaie-liste
    Created on : 21 d�c. 2020, 16:26:17
    Author     : Lenovo
--%>

<%@page import="paie.elementpaie.PaiePersonnelElementpaie"%>
<%@page import="affichage.Liste"%>
<%@page import="utilitaire.ConstanteEtat"%>
<%@page import="affichage.PageRecherche"%>

<% 
    try{
    PaiePersonnelElementpaie dr = new PaiePersonnelElementpaie();
    String nomTable = "PAIE_PERS_ELTPAIE_LIB2";

    if (request.getParameter("table") != null && request.getParameter("table").compareToIgnoreCase("") != 0) {
            nomTable = request.getParameter("table");
    }
    dr.setNomTable(nomTable);

    String listeCrt[] = {"id", "matricule","code_rubrique","idcategorie_qualification","idfonction", "remarque", "moisregularisation","anneeregularisation","date_debut","date_fin","idpersonnel","gain","retenue"};
    String listeInt[] = {"gain","retenue"};
    String libEntete[] = {"id","matricule","idpersonnel", "code_rubrique","idcategorie_qualification", "idfonction", "remarque", "moisregularisation","anneeregularisation","date_debut","date_fin","pourcentage","gain","retenue"};
    PageRecherche pr = new PageRecherche(dr, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.getFormu().getChamp("matricule").setLibelle("Matricule");
    pr.getFormu().getChamp("code_rubrique").setLibelle("Rubrique");
    pr.getFormu().getChamp("remarque").setLibelle("Remarque");
    pr.getFormu().getChamp("moisregularisation").setLibelle("Mois");
    pr.getFormu().getChamp("anneeregularisation").setLibelle("Ann&eacute;e");
    pr.getFormu().getChamp("date_debut").setLibelle("Date De D&eacute;but");
    pr.getFormu().getChamp("date_fin").setLibelle("Date De Fin");
    pr.getFormu().getChamp("idpersonnel").setLibelle("Personnel");
    pr.getFormu().getChamp("idcategorie_qualification").setLibelle("Cat&eacute;gorie De Qualification");
    pr.getFormu().getChamp("idfonction").setLibelle("Fonction");
    pr.getFormu().getChamp("gain1").setLibelle("Gain Min");
    pr.getFormu().getChamp("gain2").setLibelle("Gain max");
    pr.getFormu().getChamp("retenue1").setLibelle("Retenue Min");
    pr.getFormu().getChamp("retenue2").setLibelle("Retenue max");   

//    pr.getFormu().getChamp("dateapplication1").setLibelle("Date d'application min");
//    pr.getFormu().getChamp("dateapplication2").setLibelle("Date d'application max");
//    pr.getFormu().getChamp("idlogpers").setLibelle("Personnel");
//    pr.getFormu().getChamp("idtypedebauche").setLibelle("Cause de d&eacute;part");
    pr.setApres("paie/fonction/paiepersonnelelementpaie-liste.jsp");
        
    String[] colSomme = null;
    pr.creerObjetPage(libEntete, colSomme);
%>

<div class="content-wrapper">
    <section class="content-header">
        <h1>Liste des &eacute;l�ments de paie</h1>
    </section>
    <section class="content">
        <form action="<%=pr.getLien()%>?but=paie/fonction/paiepersonnelelementpaie-liste.jsp" method="post" name="incident" id="incident">
            <%
                out.println(pr.getFormu().getHtmlEnsemble());
            %>
        </form>

        <%  String lienTableau[] = {pr.getLien() + "?but=paie/fonction/paiepersonnelelementpaie-fiche.jsp"};
            String colonneLien[] = {"id"};
            pr.getTableau().setLien(lienTableau);
            pr.getTableau().setColonneLien(colonneLien);
            out.println(pr.getTableauRecap().getHtml());%>
        <br>
        <%
            String libEnteteAffiche[] = {"id","Matricule","Personnel", "rubrique","Cat&eacute;gorie De Qualification","Fonction", "remarque", "Mois De R&eacute;gularisation","Ann&eacute;e regularisation","Date De D&eacute;but","Date De Fin","Pourcentage","gain","retenue"};
            pr.getTableau().setLibelleAffiche(libEnteteAffiche);
            out.println(pr.getTableau().getHtml());
            out.println(pr.getBasPage());
        %>
    </section>
</div>
<% } catch(Exception e) { e.printStackTrace();}%>
