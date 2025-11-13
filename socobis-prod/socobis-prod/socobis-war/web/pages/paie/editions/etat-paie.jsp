<%@page import="paie.log.LogPersonnelFin"%>
<%@page import="bean.TypeObjet"%>
<%@page import="affichage.Liste"%>
<%@page import="affichage.PageRecherche"%>
<%@ page import="paie.edition.EtatPaie" %>

<%
    try{
        EtatPaie dr = new EtatPaie();
        String nomTable = "SITUATION_SALAIRE_EDITION";

        dr.setNomTable(nomTable);
        String listeCrt[] = { "personnel", "matricule", "annee", "mois" };
        String listeInt[] = {};
        String libEntete[] = { "id", "personnel", "fonction", "matricule", "heureMois", "heureLegal", "heurePresence", "tauxHoraire", "salaire", "salaireMois", "heureSup", "rappel", "retenuAbsc", "prime","congePayer", "preavis", "indamDivFixe", "salaireBrute", "cnaps", "ostie", "netImposable", "netImposableArr", "irsa", "nbEnfant", "irsaNet", "avanceSalaire", "avanceExcep", "allocation", "netPayer", "netPayerArr", "mois", "annee" };
        PageRecherche pr = new PageRecherche(dr, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
        pr.setUtilisateur((user.UserEJB) session.getValue("u"));
        pr.setLien((String) session.getValue("lien"));

        affichage.Champ[] liste = new affichage.Champ[1];
        Liste mois = new Liste("mois");
        mois.makeListeMois();
        liste[0] = mois;

        pr.setApres("paie/editions/etat-paie.jsp");
        String[] colSomme = { "salaire", "netImposable", "netPayer" };

        pr.creerObjetPage(libEntete, colSomme);
        String lienTableau[] = {pr.getLien() + "?but=paie/employe/personnel-fiche-portrait.jsp"};
        String colonneLien[] = {"id"};
        pr.getTableau().setLien(lienTableau);
        pr.getTableau().setColonneLien(colonneLien);
        String libEnteteAffiche[] = {
                "ID", "Personnel", "Fonction", "Matricule", "Heures du mois", "Heures l&eacute;gales", "Heure de pr&eacute;sence",
                "Taux horaire", "Salaire", "Salaire du mois", "Heures suppl&eacute;mentaires", "Rappel", "Retenue absence",
                "Prime","Cong&eacute; pay&eacute;", "Pr&eacute;avis", "Indemnite diverse fixe", "Salaire brut", "CNAPS", "OSTIE",
                "Net imposable", "Net imposable arrondi", "IRSA", "Nombre d'enfants", "IRSA net",
                "Avance sur salaire", "Avance exceptionnelle", "Allocation", "Net à payer", "Net à payer arrondi", "Mois", "Ann&eacute;e"
        };
        pr.getTableau().setLibelleAffiche(libEnteteAffiche);

        String enteteRecap[] = {"", "Nombre", "Somme des Salaires", "Somme des Salaires Net Imposables", "Somme des salaires Net A Payer"};
        pr.getTableauRecap().setLibeEntete(enteteRecap);
%>

<script>
    function changerDesignation() {
        document.getElementById("personnel").submit();
    }
</script>
<div class="content-wrapper">
    <section class="content-header">
        <h1>&Eacute;tat de paie</h1>
    </section>
    <section class="content">
        <form action="<%=pr.getLien()%>?but=paie/editions/etat-paie.jsp" method="post" name="personnel" id="personnel">
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
<%}catch(Exception e){
    e.printStackTrace();
}%>

