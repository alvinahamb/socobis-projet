<%--
  Created by IntelliJ IDEA.
  User: ny-haritina
  Date: 04/06/2025
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page import="affichage.Liste"%>
<%@page import="bean.TypeObjet"%>
<%@page import="affichage.PageRecherche"%>
<%@ page import="paie.avancement.PaieAvancementLibelle" %>
<%@ page import="paie.edition.PaieFonction" %>

<%
    try {
        PaieAvancementLibelle lv = new PaieAvancementLibelle();
        if (request.getParameter("etat") != null && !request.getParameter("etat").equals("")) {
            lv.setNomTable(request.getParameter("etat"));
        } else {
            lv.setNomTable("PAIE_AVANCEMENT_LIBELLE2");
        }
        String listeCrt[] = {"id", "id_logpers", "matricule", "idfonction",
                "direction", "service", "datedecision", "date_application"};
        String listeInt[] = {"datedecision", "date_application"};
        String libEntete[] = {"id", "matricule", "id_logpers", "direction", "service","motif","fonctionLib", "datedecision", "date_application"};
        PageRecherche pr = new PageRecherche(lv, request, listeCrt, listeInt, 3, libEntete, 8);

        affichage.Champ[] liste = new affichage.Champ[3];

        TypeObjet tp = new TypeObjet();
        tp.setNomTable("LOG_DIRECTION");
        Liste l = new Liste("direction", tp, "VAL", "VAL");
        liste[0] = l;

        TypeObjet tp1 = new TypeObjet();
        tp1.setNomTable("LOG_SERVICE_CRT");
        Liste l2 = new Liste("service", tp1, "VAL", "VAL");
        liste[1] = l2;

        PaieFonction fonc = new PaieFonction();
        fonc.setNomTable("PAIE_FONCTION");
        Liste fonction = new Liste("idfonction", fonc, "desce", "id");
        liste[2] = fonction;

//        String[] lib = {"Tous", "Cadre", "Permanent", "Temporaire"};
//        String[] val = {"%", "Cadre", "Permanent", "Temporaire"};
//        Liste statut = new Liste("statut");
//        statut.makeListeString(lib, val);
//        liste[2] = statut;

        pr.getFormu().changerEnChamp(liste);

        pr.setUtilisateur((user.UserEJB) session.getValue("u"));
        pr.setLien((String) session.getValue("lien"));
        pr.getFormu().getChamp("id_logpers").setLibelle("Nom et Pr&eacute;noms");
        pr.getFormu().getChamp("idfonction").setLibelle("Fonction");
        pr.getFormu().getChamp("direction").setLibelle("R&eacute;gion");
        pr.getFormu().getChamp("service").setLibelle("Section");
        pr.getFormu().getChamp("datedecision1").setLibelle("Date de d&eacute;cision min");
        pr.getFormu().getChamp("datedecision2").setLibelle("Date de d&eacute;cision max");
        pr.getFormu().getChamp("date_application1").setLibelle("Date d'application min");
        pr.getFormu().getChamp("date_application2").setLibelle("Date d'application max");
        pr.getFormu().getChamp("service").setLibelle("Section");
        pr.setApres("paie/avancement/avancement-liste.jsp");
        String[] colSomme = null;
        pr.creerObjetPage(libEntete, colSomme);
%>

<script>
    function changerDesignation() {
        document.prestation.submit();
    }
</script>

<div class="content-wrapper">
    <section class="content-header">
        <h1>Liste des mouvements</h1>
    </section>
    <section class="content">
        <form action="<%=pr.getLien()%>?but=paie/avancement/avancement-liste.jsp" method="post" name="prestation" id="prestation">
            <% out.println(pr.getFormu().getHtmlEnsemble());%>
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <div class="form-group">
                    <label>Etat : </label>
                    <select name="etat" class="champ form-control" id="etat" onchange="changerDesignation()" style="display: inline-block; width: 250px;">
                        <option value="PAIE_AVANCEMENT_LIBELLE2" <% if (request.getParameter("etat") == null || request.getParameter("etat").compareToIgnoreCase("") == 0) {
                            out.print(" selected");
                        } %>>Tous </option>
                        <option value="PAIE_AVANCEMENT_LIBVALIDE" <%if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("PAIE_AVANCEMENT_LIBVALIDE") == 0) {
                            out.print("selected");
                        }%>>Valide</option>
                        <option value="PAIE_AVANCEMENT_LIBNONVALIDE" <%if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("PAIE_AVANCEMENT_LIBNONVALIDE") == 0) {
                            out.print("selected");
                        }%>>Non valide</option>
                    </select>
                </div>
            </div>
            <div class="col-md-4"></div>
        </form>
        <%  String lienTableau[] = {pr.getLien() + "?but=paie/avancement/avancement-fiche.jsp"};
            String colonneLien[] = {"id"};
            pr.getTableau().setLien(lienTableau);
            pr.getTableau().setColonneLien(colonneLien);
            out.println(pr.getTableauRecap().getHtml());%>
        <br>
        <%
            String libEnteteAffiche[] = {"id", "Matricule", "Nom et pr&eacute;noms", "R&eacute;gion", "Section","Motif", "Fonction", "Date de d&eacute;cision", "Date d'application"};
            pr.getTableau().setLibelleAffiche(libEnteteAffiche);
            out.println(pr.getTableau().getHtml());
            out.println(pr.getBasPage());
        %>
    </section>
</div>

<%
} catch (Exception e) {
    e.printStackTrace();
%>

<script language="JavaScript">
    alert('<%=e.getMessage()%>');
    history.back();</script>
<% } %>
