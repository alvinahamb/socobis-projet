<%@page import="paie.log.LogPersonnelFin"%>
<%@page import="bean.TypeObjet"%>
<%@page import="affichage.Liste"%>
<%@page import="affichage.PageRecherche"%>
<%@ page import="user.UserEJB" %>
<%@ page import="historique.MapUtilisateur" %>
<%@ page import="paie.demande.EmployeComplet" %>

<%
    try{
    LogPersonnelFin dr = new LogPersonnelFin();
    String nomTable = "LOG_PERSONNEL_AVECCONGE";
    if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("") != 0) {
        nomTable = request.getParameter("etat");
    }
    UserEJB ue = (UserEJB) session.getValue("u");
    EmployeComplet pers = new EmployeComplet();

    MapUtilisateur map = ue.getUser();

    dr.setNomTable(nomTable);
    String listeCrt[] = {"id", "matricule", "nom", "prenom", "numero_cin", "direction", "service", "temporaire"};
    String listeInt[] = {};
    String libEntete[] = {"id", "matricule", "nom", "prenom", "sexe", "numero_cin", "direction", "service", "temporaire","droit","prie","resteconge"};
    PageRecherche pr = new PageRecherche(dr, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));

    Liste[] liste = new Liste[3];
    TypeObjet m0 = new TypeObjet();
    m0.setNomTable("log_direction");
    liste[0] = new Liste("direction", m0, "val", "val");

    TypeObjet m1 = new TypeObjet();
    m1.setNomTable("log_service_crt");
    liste[1] = new Liste("service", m1, "val", "val");

    Liste tem = new Liste("temporaire");
    String[] aff = {"Tous", "Cadre", "Permanent", "Temporaire"};
    String[] val = {"%", "Cadre", "Permanent", "Temporaire"};
    tem.ajouterValeur(val, aff);
    liste[2] = tem;

    pr.getFormu().changerEnChamp(liste);

    pr.setApres("paie/employe/infopersonnel-liste.jsp");
    String[] colSomme = {"droit"};
    pr.getFormu().getChamp("id").setLibelle("ID");
//    manager sy admin ihany no mahita ny liste employes rehetra sinon raha agent izy dia ny tenany ihany no hitany
    if(map.getIdrole().compareToIgnoreCase("agent")==0) {
        EmployeComplet e = pers.getEmployeByRefUser(map.getTuppleID());
        pr.getFormu().getChamp("id").setDefaut(e.getId());
    }
    pr.getFormu().getChamp("nom").setLibelle("Nom");
    pr.getFormu().getChamp("prenom").setLibelle("Pr&eacute;nom(s)");
    pr.getFormu().getChamp("numero_cin").setLibelle("Carte d'identit&eacute; (CIN)");
    pr.getFormu().getChamp("matricule").setLibelle("Matricule");
    pr.getFormu().getChamp("temporaire").setLibelle("Type de Contrat");
    pr.getFormu().getChamp("direction").setLibelle("Lieu d'affectation");
    pr.getFormu().getChamp("service").setLibelle("Rattachement");
    pr.creerObjetPage(libEntete, colSomme);
    String enteteRecap[] = {"","Nombre","Somme de droit de cong&eacute"};
    pr.getTableauRecap().setLibeEntete(enteteRecap);
    String lienTableau[] = {pr.getLien() + "?but=paie/employe/personnel-fiche-portrait.jsp"};
    String colonneLien[] = {"id"};
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setColonneLien(colonneLien);
    String libEnteteAffiche[] = {"ID", "Matricule", "Nom", "Pr&eacute;nom(s)", "Sexe", "CIN", "Lieu d'affectation", "Rattachement", "Type de contrat","Droit de cong&eacute;","Cong&eacute; entam&eacute;","cong&eacute restant"};
    pr.getTableau().setLibelleAffiche(libEnteteAffiche);
    String[] etatVal = {"LOG_PERSONNEL_AVECCONGE","log_personnel_fin_valide", "LOG_PERSONNEL_FIN_A_VALIDER", "log_personne_non_valide_vue"};
    String[] etatAff = {"Tous","Valid&eacute;e"," A valider","Non valide"};
%>

<script>
    function changerDesignation() {
        document.getElementById("personnel").submit();
    }
</script>
<div class="content-wrapper">
    <section class="content-header">
        <h1>Liste des employ&eacute;s</h1>
    </section>
    <section class="content">
        <form action="<%=pr.getLien()%>?but=paie/employe/infopersonnel-liste.jsp" method="post" name="personnel" id="personnel">
            <%
                out.println(pr.getFormu().getHtmlEnsemble());
            %>
            <div class="row col-md-12">
                <div class="col-md-4"></div>
              <div class="col-md-offset-5">
                <div class="form-group">
                    <select name="etat" class="champ-select" id="etat" onchange="changerDesignation()" >
                        <% for (int i = 0; i < etatVal.length; i++) {%>
                        <option value="<%=etatVal[i]%>" <%= etatVal[i].equalsIgnoreCase(dr.getNomTable()) ? "selected " : ""%>>
                            <%=etatAff[i]%>
                        </option>
                        <%}%>
                    </select>
                </div>
            </div>

                <div class="col-md-4"></div>
            </div>

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
<%}catch(Exception e){
    e.printStackTrace();
}%>
