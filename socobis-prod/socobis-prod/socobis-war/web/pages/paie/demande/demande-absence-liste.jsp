 <%@page import="utilitaire.ConstanteEtatPaie"%>
<%@page import="utils.ConstantePaie"%>
<%@page import="paie.demande.DemandeJustifications"%>
<%@page import="paie.demande.*"%>
<%@page import="affichage.*"%>
<%@page import="user.UserEJB"%>
<%@page import="bean.*" %>
 <%@ page import="historique.MapUtilisateur" %>

 <% try{
    DemandeJustifications t = new DemandeJustifications();
    t.setNomTable("demande_libcomplet");
    String listeCrt[] = {"datedepart","datefin","idtypeabsence", "nom", "prenom","idPersonnel","matricule"};
    String listeInt[] = {"daty","datedepart","datefin"};
    String libEntete[] = {"id","idPersonnel","matricule","nom","prenom","motif","typeabsencelib","duree","daty","datedepart","datefin","dateretour","etatlib","annuler"};
    PageRecherche pr = new PageRecherche(t, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("paie/demande/demande-absence-liste.jsp");
    affichage.Champ[] liste = new affichage.Champ[1];
    TypeObjet tp = new TypeObjet();
    tp.setNomTable("typeabsence");
    Liste l = new Liste("idtypeabsence", tp, "desce", "id");    
    liste[0] = l;
    pr.getFormu().changerEnChamp(liste);
    pr.getFormu().getChamp("idtypeabsence").setLibelle("Type d'absence");
    pr.getFormu().getChamp("matricule").setLibelle("Matricule");
    pr.getFormu().getChamp("nom").setLibelle("Nom");
    pr.getFormu().getChamp("prenom").setLibelle("Pr&eacute;nom");    
    pr.getFormu().getChamp("datedepart1").setLibelle("Date d&eacute;part min");
    pr.getFormu().getChamp("datedepart2").setLibelle("Date d&eacute;part  max");
    pr.getFormu().getChamp("datefin1").setLibelle("Date retour min");
    pr.getFormu().getChamp("datefin2").setLibelle("Date retour max");
    pr.getFormu().getChamp("idPersonnel").setLibelle("ID Personnel");

    EmployeComplet pers = new EmployeComplet();
    EmployeComplet e = pers.getEmployeByRefUser(pr.getUtilisateur().getUser().getRefuser()+"");
    String idP = e.getId();
//    String aWhere = " and idtypedemande like '"+ConstantePaie.idDemandeAbsence+"' or ( idSup = '"+idP+"') order by daty desc";
    String aWhere = " ";
    pr.setAWhere(aWhere);
    if(request.getParameter("etat")!=null&&request.getParameter("etat").compareTo("")!=0&&request.getParameter("etat").compareTo("null")!=0){
        pr.setAWhere(pr.getAWhere()+" and etat="+request.getParameter("etat"));
    }
     UserEJB ue = (UserEJB) session.getValue("u");
     EmployeComplet employeComplet = new EmployeComplet();
     MapUtilisateur mapUser = ue.getUser();

     if(mapUser.getIdrole().compareToIgnoreCase("agent")==0) {
         EmployeComplet emp = employeComplet.getEmployeByRefUser(mapUser.getTuppleID());
         pr.getFormu().getChamp("idPersonnel").setDefaut(emp.getId());
     }
    String[] colSomme = null;
    pr.creerObjetPage(libEntete, colSomme);
%>
<script>
    function changerDesignation() {
        document.personnel.submit();
    }

</script>
<div class="content-wrapper">
    <section class="content-header">
        <h1>Liste de demande d'absence</h1>
    </section>
    <section class="content">
        <form action="<%=pr.getLien()%>?but=paie/demande/demande-absence-liste.jsp" method="post" name="personnel" id="personnel">
            <%
                out.println(pr.getFormu().getHtmlEnsemble());
            %>
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <div class="form-group">
                    <label>Etat : </label>
                    <select name="etat" class="champ form-control" id="etat" onchange="changerDesignation()" style="display: inline-block; width: 250px;">
                        <option value="" <% if (request.getParameter("etat") == null || request.getParameter("etat").compareToIgnoreCase("") == 0) {
                                out.print(" selected");
                            }%>>Tous</option>
                        <option value="<%=ConstanteEtatPaie.getEtatCreer()%>" <%if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase(ConstanteEtatPaie.getEtatCreer()+"") == 0) {
                                out.print("selected");
                            }%>>Cr&eacute;e</option>
                        <option value="<%=ConstanteEtatPaie.getEtatValiderParDemandeur()%>" <%if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase(ConstanteEtatPaie.getEtatValiderParDemandeur()+"") == 0) {
                                out.print("selected");
                            }%>>Valider par demandeur</option> 
                        <option value="<%=ConstanteEtatPaie.getEtatValiderParRH()%>" <%if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase(ConstanteEtatPaie.getEtatValiderParRH()+"") == 0) {
                                out.print("selected");
                            }%>>Valider par RH</option>
                        <option value="<%=ConstanteEtatPaie.getEtatValiderParCH()%>" <%if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase(ConstanteEtatPaie.getEtatValiderParCH()+"") == 0) {
                                out.print("selected");
                            }%>>Valider par CH</option>
                        <option value="<%=ConstanteEtatPaie.getEtatValiderParDE()%>" <%if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase(ConstanteEtatPaie.getEtatValiderParDE()+"") == 0) {
                                out.print("selected");
                            }%>>Valider par DE</option>
                        <option value="<%=ConstanteEtatPaie.getEtatValiderParDG()%>" <%if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase(ConstanteEtatPaie.getEtatValiderParDG()+"") == 0) {
                                out.print("selected");
                            }%>>Valider par DG</option>
                    </select>
                </div>
            </div>
            <div class="col-md-4"></div>
        </form>
        <%
            String lienTableau[] = {pr.getLien() + "?but=paie/demande" + "/demande-absence-fiche.jsp",pr.getLien() + "?but=paie/employe" + "/personnel-fiche-portrait.jsp"};
            String colonneLien[] = {"id","idPersonnel"};
            pr.getTableau().setLien(lienTableau);
            pr.getTableau().setColonneLien(colonneLien);
            out.println(pr.getTableauRecap().getHtml());%>
        <br>
        <%
            String libEnteteAffiche[] = {"Id","Id Personnel", "Matricule","Nom", "Pr&eacute;nom", "Motif", "Type absence", "dur&eacute;e", "Date de saisie", "Date d&eacute;but","Date fin","Date retour" , "Etat","Annul&eacute;"};
            pr.getTableau().setLibelleAffiche(libEnteteAffiche);
            out.println(pr.getTableau().getHtml());
            out.println(pr.getBasPage());
        %>
    </section>
</div>
<%
    } catch (Exception e) {
        e.printStackTrace(); 
    }
%>
