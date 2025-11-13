<%@page import="utilitaire.Utilitaire"%>
<%@ page import="affichage.PageRecherche" %>
<%@ page import="paie.log.LogPersonnelNonValide" %>
<%@ page import="paie.employe.EmployeEltPaie" %>
<%@ page import="bean.AdminGen" %>

<%
    try {
        String id = request.getParameter("id");
        String baselien = (String) session.getValue("lien");
        EmployeEltPaie mapping = new EmployeEltPaie();
        LogPersonnelNonValide log = new LogPersonnelNonValide();
        mapping.setNomTable("DETAILS_ELT_PAIE");

        String listeCrt[] = {};
        String listeInt[] = {};
        String libEntete[] = { "desceRubrique", "gain", "retenue" };
        String[] colSomme = null;

        PageRecherche pr = new PageRecherche(mapping, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
        if(request.getParameter("id") != null){
            log = LogPersonnelNonValide.getLogPersNonValideById(id);
            pr.setAWhere(" and mois="+Utilitaire.getMois(log.getDateapplication()) + " and annee=" +Utilitaire.getAnnee(log.getDateapplication()) + " and id='" + log.getIdlogpers() + "'");
            
        }
        pr.setUtilisateur((user.UserEJB) session.getValue("u"));
        pr.setLien((String) session.getValue("lien"));
        pr.creerObjetPage(libEntete, colSomme);
%>


<div class="box-body">
    <%
        String libEnteteAffiche[] = { "Rubrique", "Gain", "Retenue" };
        String lienTableau[] = {pr.getLien() + "?but=employe/personnelnonvalide-fiche.jsp"};
        String colonneLien[] = {"id"};

        pr.getTableau().setLibelleAffiche(libEnteteAffiche);
        pr.getTableau().setLien(lienTableau);
        pr.getTableau().setColonneLien(colonneLien);


        if(pr.getTableau().getHtml() != null){
            out.println(pr.getTableau().getHtml());
        }

        
        else { %>
            <div style="text-align: center;"><h4>Aucune donnée trouvée</h4></div><%
        }
    %>

    <% if (pr.getListe() != null) { %>
    
    <%
    double gain = AdminGen.calculSommeDouble(pr.getListe(),"gain");
    double retenue = AdminGen.calculSommeDouble(pr.getListe(),"retenue");
    double net = gain-retenue;
    %>
        <div class="w-100" style="display: flex; flex-direction: row-reverse;">
            <table style="width: 20%"class="table">
                <tr>
                    <td><b>Somme Gains</b></td>
                    <td><b><%= utilitaire.Utilitaire.formaterAr(gain) %> Ar</b></td>
                </tr>
                <tr>
                    <td><b>Somme Retenues</b></td>
                    <td><b><%= utilitaire.Utilitaire.formaterAr(retenue) %> Ar</b></td>
                </tr>
                <tr>
                    <td><b>Salaire Net</b></td>
                    <td><b><%= utilitaire.Utilitaire.formaterAr(net) %> Ar</b></td>
                </tr>
    
            </table>
        </div>
    <% } %> 
    
    <form action="<%=baselien%>?but=apresSpecifique.jsp" method="post" name="salaire" id="salaire" data-parsley-validate>
        <input name="idpersonnel" type="hidden" id="nature" value="<%=log.getIdlogpers()%>">
        <input name="acte" type="hidden" id="nature" value="valider_stc">
        <button class="btn btn-primary pull-right" style="margin-right: var(--Bases-4-space-4);" name="Submit" type="submit">Valider STC</button>
    </form>
</div>

<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>