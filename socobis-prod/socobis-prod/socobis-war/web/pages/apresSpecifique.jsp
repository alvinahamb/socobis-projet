<%--
    Document   : apresSpecifique
    Created on : 28 janv. 2021, 14:12:07
    Author     : Axel
--%>

<%@page import="paie.edition.EditionCalcul"%>
<%@page import="bean.ClassMAPTable"%>
<%@page import="user.UserEJB"%>
<%@ page import="user.*" %>
<%@ page import="utilitaire.*" %>
<%@ page import="bean.*" %>
<%@ page import="affichage.*" %>
<%
    String acte = request.getParameter("acte");
    UserEJB u = (user.UserEJB) session.getValue("u");
    String lien = (String) session.getValue("lien");
    String bute = request.getParameter("bute");

    try {
        if (acte.compareToIgnoreCase("genener_paie") == 0) {
            int mois = Integer.parseInt(request.getParameter("mois"));
            int annee = Integer.parseInt(request.getParameter("annee"));
            String rattachement = request.getParameter("rattachement")!=null?request.getParameter("rattachement"): "DIR00001";
            String idpersonnel = request.getParameter("idpersonnel")!=null ? request.getParameter("idpersonnel") : null;
            String type_edition =request.getParameter("type_edition");
            String id  = EditionCalcul.genererEditionPLSQLTahina(mois, annee, rattachement, "1060",type_edition,idpersonnel);

            String idedition="";
            String table="PAIE_EDITIONMOISANNEE_lib";
            String pages = "editionmoisannee-fiche.jsp";
            String[] parts = id.split("-");
            idedition =parts[0];
            bute = "paie/editionmoisannee/"+pages+"&id="+idedition+"&tab="+table;
        }
        if (acte.compareToIgnoreCase("genener_stc") == 0) {
            int mois = Integer.parseInt(request.getParameter("mois"));
            int annee = Integer.parseInt(request.getParameter("annee"));
            String rattachement = request.getParameter("rattachement")!=null?request.getParameter("rattachement"): "DIR00001";
            String idpersonnel = request.getParameter("idpersonnel")!=null ? request.getParameter("idpersonnel") : null;
            String type_edition =request.getParameter("type_edition");
            bute = request.getParameter("bute");
            EditionCalcul.genererEditionStc(mois, annee, rattachement, "1060",type_edition,idpersonnel);
        }
        if (acte.compareToIgnoreCase("valider_stc") == 0) {
            String idpersonnel = request.getParameter("idpersonnel")!=null ? request.getParameter("idpersonnel") : null;
            String idedition = EditionCalcul.validerStc(idpersonnel,"1060",null);
            String table="PAIE_EDITIONMOISANNEESTC_LIB_2";
            String pages = "editionmoisanneestc-fiche.jsp";

            bute = "paie/editionmoisannee/"+pages+"&id="+idedition+"&tab="+table;
        }
%>
<script language="JavaScript">
    document.location.replace("<%=lien%>?but=<%=bute%>");
</script>
<%
} catch (Exception e) {
    e.printStackTrace();
%>
<script>
    alert('<%=e.getMessage()%>');
    history.back();
</script>
<%
        return;
    }
%>