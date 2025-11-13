<%@page import="faturefournisseur.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="user.*" %>
<%@ page import="bean.*" %>
<%@ page import="utilitaire.*" %>
<%@ page import="affichage.*" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%
    UserEJB u = (user.UserEJB)session.getValue("u");
    
%>
<%
    As_BonDeCommandeCpl f = new As_BonDeCommandeCpl();
    f.setNomTable("As_BonDeCommande_MERECPL");
    PageConsulte pc = new PageConsulte(f, request, u);
    pc.setTitre("Fiche d'un bon de commande fournisseur");
    pc.getBase();
    String id=pc.getBase().getTuppleID();
    pc.getChampByName("id").setLibelle("Id");
    pc.getChampByName("daty").setLibelle("date");
    pc.getChampByName("reference").setLibelle("R&eacute;f&eacute;rence");
    pc.getChampByName("remarque").setLibelle("Remarque");
    pc.getChampByName("designation").setLibelle("d&eacute;signation");
    pc.getChampByName("fournisseurlib").setLibelle("Fournisseur");
    pc.getChampByName("modepaiementlib").setLibelle("Mode de paiement");
    pc.getChampByName("modepaiementlib").setLibelle("Mode de paiement");
    pc.getChampByName("idDeviselib").setLibelle("Devise");
    pc.getChampByName("montantTVA").setLibelle("Montant TVA");
    pc.getChampByName("montantHT").setLibelle("Montant HT");
    pc.getChampByName("montantTTC").setLibelle("Montant TTC");
    pc.getChampByName("montantTTCAriary").setLibelle("Montant TTC Ariary");
    pc.getChampByName("etat").setLibelle("&Eacute;tat");
    pc.getChampByName("etatlib").setLibelle("&Eacute;tat");
    pc.getChampByName("idDevise").setVisible(false);
    pc.getChampByName("fournisseur").setVisible(false);
      pc.getChampByName("modepaiement").setVisible(false);
    String pageActuel = "bondecommande/bondecommande-fiche.jsp";

    String lien = (String) session.getValue("lien");
    String pageModif = "bondecommande/bondecommande-saisie.jsp";
    String classe = "faturefournisseur.As_BonDeCommande";
    
    Map<String, String> map = new HashMap<String, String>();
    map.put("inc/bondecommande-liste-detail", "");

    String tab = request.getParameter("tab");
    if (tab == null) {
        tab = "inc/bondecommande-liste-detail";
    }
    map.put(tab, "active");
    tab = tab + ".jsp";
    As_BonDeCommandeCpl bc = (As_BonDeCommandeCpl) pc.getBase();
    int etat = bc.getEtat();
%>

<div class="content-wrapper">
    <h1 class="box-title"><a href=<%= lien + "?but=bondecommande/bondecommande-fiche.jsp"%>> <i class="fa fa-angle-left"></i></a><%=pc.getTitre()%></h1>

    <div class="row m-0">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <div class="box-fiche">
                <div class="box">
                    
                    <div class="box-body">
                        <%
                            out.println(pc.getHtml());
                        %>
                        <br/>
                        <div class="box-footer">
                            <%
                                if( etat < 11 ){ %>
                                    <a class="btn btn-primary pull-right" href="<%= (String) session.getValue("lien") + "?but=apresTarif.jsp&acte=valider&id=" + request.getParameter("id") + "&bute=bondecommande/bondecommande-fiche.jsp&classe=" + classe %> " style="margin-right: 10px">Viser</a>
                                    <a class="btn btn-secondary pull-right"  href="<%= lien + "?but="+ pageModif +"&id=" + id+"&acte=update"%>" style="margin-right: 10px">Modifier</a>

                            <%    }
                            %>
                            <% if(etat >= 11) {%>
                             <a class="btn btn-primary pull-right" href="<%= (String) session.getValue("lien") + "?but=bondelivraison/bondelivraison-saisie.jsp&id=" + request.getParameter("id")%> " style="margin-right: 10px">R&eacute;ceptionner</a>
                             <!-- <a class="btn btn-info pull-right"  href="<%=(String) session.getValue("lien") + "?but=bondecommande/apresFacturer.jsp&id=" + id%>"style="margin: 5px 10px 0 0">Facturer</a> -->
                             <a class="btn btn-secondary pull-right" href="<%= (String) session.getValue("lien") + "?but=facturefournisseur/facturefournisseur-saisie.jsp&idbc="+pc.getChampByName("id").getValeur()%>" style="margin-right: 10px">Facturer</a>

                            <% } %>
                            <a class="btn btn-success pull-right"  href="${pageContext.request.contextPath}/ExportPDF?action=fiche_bc&id=<%=request.getParameter("id")%>" style="margin-right: 10px">Imprimer</a>
                        </div>
                        <br/>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row m-0">
        <div class="col-md-12 nopadding">
            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <!-- a modifier -->
                    <li class="<%=map.get("inc/bondecommande-liste-detail")%>"><a href="<%= lien %>?but=<%= pageActuel %>&id=<%= id %>&tab=inc/bondecommande-liste-detail">Détails</a></li>
                    <li class="<%=map.get("inc/as-bondelivraison")%>"><a href="<%= lien %>?but=<%= pageActuel %>&id=<%= id %>&tab=inc/as-bondelivraison">Détails de Livraison</a></li>
                </ul>
                <div class="tab-content">
                    <jsp:include page="<%= tab %>" >
                        <jsp:param name="idFactureFournisseur" value="<%= id %>" />
                    </jsp:include>
                </div>
            </div>

        </div>
    </div>                    
</div>