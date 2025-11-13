<%-- 
    Document   : client-quit-liste
    Created on : 31 oct. 2022, 12:14:22
    Author     : NAEPHA
--%>

<%@page import="java.sql.Date"%>
<%@page import="utilitaire.Utilitaire"%>
<%@page import="facture.tr.Traite"%>
<%@page import="affichage.PageRecherche"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    try {
        Traite base = new Traite();
        base.setNomTable("TRAITEMTTRESTE");
		
		if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("") != 0) {
            base.setNomTable(request.getParameter("etat"));
        }

        String listeCrt[] = {"id","tiers", "banque", "daty", "dateEcheance","reference"};
        String listeInt[] = {"daty"};
        String libEntete[] = {"id","tiers", "banque","reference", "daty", "dateEcheance","montant","montantreste","etatversementlib"};

        PageRecherche pr = new PageRecherche(base, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
        pr.setUtilisateur((user.UserEJB) session.getValue("u"));
        pr.setLien((String) session.getValue("lien"));
        
        pr.getFormu().getChamp("id").setLibelle("ID");
        pr.getFormu().getChamp("tiers").setLibelle("Tiers");
        pr.getFormu().getChamp("reference").setLibelle("R&eacute;f&eacute;rence");
        pr.getFormu().getChamp("banque").setLibelle("Banque");
        pr.getFormu().getChamp("daty1").setLibelle("Date min");
        pr.getFormu().getChamp("daty2").setLibelle("Date max");
        pr.getFormu().getChamp("dateEcheance").setLibelle("Date d'&eacute;ch&eacute;ance");
        pr.setOrdre(" order by tiers,dateEcheance asc");

        
        pr.setApres("facture/traite-liste.jsp");
        
        String[] colSomme = null;
        pr.creerObjetPage(libEntete, colSomme);
%>
<script>
    function changerDesignation() {
        document.liste.submit();
    }
</script>
<div class="content-wrapper">
    <section class="content-header">
        <h1>Liste des traites</h1>
    </section>
    <section class="content">
        <form action='<%=pr.getLien() + "?but=facture/traite-liste.jsp" %>' method="post" name="liste" id="liste">
            <%
                out.println(pr.getFormu().getHtmlEnsemble());
            %>
			<div class="row col-md-12">
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <label for="etat">&Eacute;tat:</label>
                    <select name="etat" class="champ" id="etat" onchange="changerDesignation()" >
                        <% if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("TRAITEMTTRESTE") == 0) {%>    
                        <option value="TRAITEMTTRESTE" selected>Tous</option>
                        <% } else { %>
                        <option value="TRAITEMTTRESTE" >Tous</option>
                        <% } %>
						
                        <% if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("TRAITE_LIBC") == 0) {%>
                        <option value="TRAITE_LIBC" selected>Créé</option>
                        <% } else { %>
                        <option value="TRAITE_LIBC">Créé</option>
                        <% } %>

                        <% if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("TRAITE_LIBV") == 0) {%>
                        <option value="TRAITE_LIBV" selected>Visé</option>
                        <% } else { %>
                        <option value="TRAITE_LIBV">Visé</option>
                        <% } %>
						
						<% if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("TRAITE_LIBESC") == 0) {%>
                        <option value="TRAITE_LIBESC" selected>Escompté</option>
                        <% } else { %>
                        <option value="TRAITE_LIBESC">Escompté</option>
                        <% } %>
						
						<% if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("TRAITE_LIBVER") == 0) {%>
                        <option value="TRAITE_LIBVER" selected>Versé</option>
                        <% } else { %>
                        <option value="TRAITE_LIBVER">Versé</option>
                        <% } %>

                        <% if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("TRAITE_LIBNONENC") == 0) {%>
                        <option value="TRAITE_LIBNONENC" selected>Non encaissé</option>
                        <% } else { %>
                        <option value="TRAITE_LIBNONENC">Non encaissé</option>
                        <% } %>

                        <% if (request.getParameter("etat") != null && request.getParameter("etat").compareToIgnoreCase("TRAITE_LIBENC") == 0) {%>
                        <option value="TRAITE_LIBENC" selected>Encaissé</option>
                        <% } else { %>
                        <option value="TRAITE_LIBENC">Encaissé</option>
                        <% } %>

                    </select>
                </div>
                <div class="col-md-4"></div>
            </div>
        </form>
               <%
            String lienTableau[] = {pr.getLien() + "?but=facture/traite-fiche.jsp"};
            String colonneLien[] = {"id"};
            pr.getTableau().setLien(lienTableau);
            pr.getTableau().setColonneLien(colonneLien);
            out.println(pr.getTableauRecap().getHtml());%>
        <br/>
        <%
            String libelleAffiche[] = {"Id","Tiers", "Banque","r&eacute;f&eacute;rence","date","Date d'&eacute;ch&eacute;ance","Montant", "Montant Disponible","&Eacute;tat de versement"};
            pr.getTableau().setLibelleAffiche(libelleAffiche);
            out.println(pr.getTableau().getHtml());
            out.println(pr.getBasPage());
        %>
    </section>
</div>
<% } catch (Exception e) {
        e.printStackTrace();
    }%>

