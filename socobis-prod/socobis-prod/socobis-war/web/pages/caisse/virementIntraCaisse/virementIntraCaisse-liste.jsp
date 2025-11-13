<%@page import="caisse.VirementIntraCaisseCpl"%>
<%@ page import="user.*" %>
<%@ page import="bean.*" %>
<%@ page import="utilitaire.*" %>
<%@ page import="affichage.*" %>
<%@page import="affichage.PageRecherche"%>

<% try{ 
    VirementIntraCaisseCpl t = new VirementIntraCaisseCpl();
    t.setNomTable("VirementIntraCaisseCpl");
    String etat = request.getParameter("etat");
    if(etat == null) etat = "";
    t.setNomTable( t.getNomTable().concat(etat) );
    String listeCrt[] = {"id","designation","daty","idCaisseDepartLib","idCaisseArriveLib","montant"};
    String listeInt[] = {"daty"};
    String libEntete[] = {"id","designation","daty","idCaisseDepartLib","idCaisseArriveLib","montant", "etatLib"};
    PageRecherche pr = new PageRecherche(t, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setTitre("virements intra-caisse");
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("caisse/virementIntraCaisse/virementIntraCaisse-liste.jsp");
    pr.getFormu().getChamp("daty1").setLibelle("Date min");
    pr.getFormu().getChamp("designation").setLibelle("d&eacute;signation");
    pr.getFormu().getChamp("daty1").setDefaut(utilitaire.Utilitaire.dateDuJour());
    pr.getFormu().getChamp("daty2").setLibelle("Date max");
    pr.getFormu().getChamp("daty2").setDefaut(utilitaire.Utilitaire.dateDuJour());
    pr.getFormu().getChamp("idCaisseDepartLib").setLibelle("Caisse de depart");
    pr.getFormu().getChamp("idCaisseArriveLib").setLibelle("Caisse d'arrive");
    String[] colSomme = null;
    pr.creerObjetPage(libEntete, colSomme);
    //Definition des lienTableau et des colonnes de lien
    String lienTableau[] = {pr.getLien() + "?but=caisse/virementIntraCaisse/virementIntraCaisse-fiche.jsp"};
    String colonneLien[] = {"id"};
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setColonneLien(colonneLien);
    //Definition des libelles � afficher
    String libEnteteAffiche[] = {"id","d&eacute;signation","daty","Caisse Depart","Caisse Arrive","montant", "&Eacute;tat"};
    pr.getTableau().setLibelleAffiche(libEnteteAffiche);
    String[] etatAffiche = {"Tous","Cr&eacute;&eacute;","Valid&eacute;","Annul&eacute;"};
    String[] etatPasse = {"","_annule","_cree","_valider"};
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

        <div class="row m-0">
            <div class="col-md-12 nopadding">
                <form action="<%= pr.getLien() %>?but=<%= pr.getApres() %>" method="post">
                    <div class="d-flex mb-5" style="align-items: end;gap: var(--Bases-4-space-2);justify-content: center">
                        <div class="form-input">
                            <label for="etat" class="box-header with-border">
                                Voir l'etat
                            </label>
                            <select name="etat" class="form-control">
                                <%
                                    for( int i = 0; i < etatAffiche.length; i++ ){ %>
                                        <option value="<%= etatPasse[i] %>"> <%= etatAffiche[i] %> </option>
                                <%    }
                                %>
                            </select>
                            <br>
                        </div>
                        <input type="submit" value="Consultez" class="btn btn-primary " style="min-height: 24px;height: 34px;" />

                    </div>
                </form>
            </div>
            <div class="col-md-12 nopadding">
                <%
                    out.println(pr.getTableauRecap().getHtml());
                %>
            </div>    
        </div>

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



