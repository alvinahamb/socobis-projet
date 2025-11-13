
<%@page import="caisse.MvtCaisseCpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="user.*" %>
<%@ page import="bean.*" %>
<%@ page import="utilitaire.*" %>
<%@ page import="affichage.*" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%
    try {


    UserEJB u = (user.UserEJB)session.getValue("u");

%>
<%
    String lien = (String) session.getValue("lien");
    MvtCaisseCpl caisse = new MvtCaisseCpl();
    PageConsulte pc = new PageConsulte(caisse, request, u);
    pc.setTitre("Fiche de mouvement de caisse");
    pc.getBase();
    String iff=request.getParameter("idFF");
    String id=pc.getBase().getTuppleID();
    pc.getChampByName("id").setLibelle("Id");
    pc.getChampByName("designation").setLibelle("D&eacute;signation");
    pc.getChampByName("idCaisseLib").setLibelle("Caisse");
    pc.getChampByName("idVenteDetail").setVisible(false);
    pc.getChampByName("idVirement").setVisible(false);
    pc.getChampByName("debit").setLibelle("d&eacute;bit");
    pc.getChampByName("credit").setLibelle("cr&eacute;dit");
    pc.getChampByName("etat").setLibelle("&Eacute;tat");
    pc.getChampByName("idCaisse").setVisible(false);
    pc.getChampByName("idOrigine").setLibelle("Origine");
    pc.getChampByName("idVente").setLibelle("Vente");
    pc.getChampByName("daty").setLibelle("Date");
//    pc.getChampByName("idModePaiementLib").setLibelle("Mode de paiement");
//    pc.getChampByName("idModePaiement").setVisible(false);
    pc.getChampByName("idDevise").setLibelle("Devise");
    pc.getChampByName("idPrevision").setLibelle("Pr&eacute;vision");
    pc.getChampByName("idVente").setLien(lien+"?but=vente/vente-fiche.jsp&id", "id=");
    pc.getChampByName("etatLib").setVisible(false);
    pc.getChampByName("idTiers").setVisible(false);
    pc.getChampByName("idOrigine").setVisible(false);
    pc.getChampByName("idop").setVisible(false);
    pc.getChampByName("idtraite").setLibelle("Traite");
    String pageActuel = "caisse/mvt/mvtCaisse-fiche.jsp";
    String classe = "caisse.MvtCaisse";
    Onglet onglet =  new Onglet("page1");
    onglet.setDossier("inc");
    caisse = (MvtCaisseCpl) pc.getBase();
    Map<String, String> map = new HashMap<String, String>();
    String tab = request.getParameter("tab") == null ? "ecriture-details" : request.getParameter("tab");
    map.put("ecriture-details", "");
    System.out.println("TRAITE ==== "+caisse.getIdtraite());
    if(caisse.getIdtraite()!=null){
        pc.getChampByName("idtraite").setLibelle("Traite");
        pc.getChampByName("idtraite").setLien(lien+"?but=facture/traite-fiche.jsp&id", "id=");
    }
    if(caisse.getIdOrigine()!=null && caisse.getIdOrigine().startsWith("VNT")){
        pc.getChampByName("idOrigine").setLien("?but=vente/vente-fiche.jsp","id=");
        map.put("origine-vente-details", "");
        if (tab == null) {
            tab = "origine-vente-details";
        }
    }else if(caisse.getIdOrigine()!=null && caisse.getIdOrigine().startsWith("FCF")){
        pc.getChampByName("idOrigine").setLien("?but=facturefournisseur/facturefournisseur-fiche.jsp","id=");
        map.put("origine-achat-details", "");
        if (tab == null) {
            tab = "origine-achat-details";
        }
    }
    onglet.setDossier("inc");
    map.put(tab, "active");
    tab = "inc/" + tab + ".jsp";
    String pageModif = "";

    if(caisse.getCredit()>0 && caisse.getDebit()<=0){
        pageModif="caisse/mvt/mvtCaisse-saisie-entree.jsp";
    }
        if(caisse.getDebit()>0 && caisse.getCredit()<=0){
        pageModif="caisse/mvt/mvtCaisse-saisie-sortie.jsp";
    }

%>

<div class="content-wrapper">
    <%if(iff!=null){%>
    <h1 class="box-title"><a href=<%= lien + "?but=facturefournisseur/facturefournisseur-fiche.jsp&id="+iff%> ><i class="fa fa-angle-left"></i></a><%=pc.getTitre()%></h1>
    <%}else{%>
    <h1 class="box-title"><a href=<%= lien + "?but=caisse/mvt/mvtCaisse-liste.jsp"%>> <i class="fa fa-angle-left"></i></a><%=pc.getTitre()%></h1>
    <%}%>
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
                            <% if(caisse.getEtat() > 0 && caisse.getEtat() < 8) { %>
                            <a class="btn btn-success pull-right" href="<%= (String) session.getValue("lien") + "?but=apresTarif.jsp&acte=validerMvt&id=" + request.getParameter("id") + "&bute=caisse/mvt/mvtCaisse-fiche.jsp&classe=" + classe %> " style="margin-right: 10px">Valider</a>                            <% }
                            if( caisse.getEtat() != 11 ){ %>
                        <a class="btn btn-secondary pull-right"  href="<%= lien + "?but="+ pageModif +"&id=" + id +"&acte=update"%>" style="margin-right: 10px">Modifier</a>
                            <%   }
                                if(caisse.getEtat() == 11 ){
                            %>
                            <a class="btn btn-tertiary pull-right" href="<%= (String) session.getValue("lien") + "?but=prevision/prevision-non-regle.jsp&idMvtCaisse=" + request.getParameter("id") %>" style="margin-right: 10px">Attacher prevision</a>
                            <%
                                }
                                if( caisse.getEtat() != 11 ){ %>
<%--                            <a class="btn btn-warning pull-right" href="<%= (String) session.getValue("lien") + "?but=caisse/mvt/mvtCaisse-modif.jsp&id=" + request.getParameter("id") %>" style="margin-right: 10px">Modifier</a>--%>
                            <%    }
                            %>
                            <% if(caisse.getEtat() == ConstanteEtat.getEtatProforma()) { %>
                            <a class="btn btn-success pull-right" href="<%= (String) session.getValue("lien") + "?but=caisse/mvt/mvtCaisse-modif.jsp&acte=vc&id=" + request.getParameter("id")  %> " style="margin-right: 10px">Validation Comptable</a>
                            <% } %>

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
                    <% if(caisse.getIdOrigine()!=null && caisse.getIdOrigine().startsWith("VNT")){ %>
                    <li class="<%=map.get("origine-vente-details")%>"><a href="<%= lien%>?but=<%= pageActuel%>&id=<%= id%>&tab=origine-vente-details">D&eacute;tails origine</a></li>
                    <% } %>
                    <% if(caisse.getIdOrigine()!=null && caisse.getIdOrigine().startsWith("FCF")){ %>
                    <li class="<%=map.get("origine-achat-details")%>"><a href="<%= lien%>?but=<%= pageActuel%>&id=<%= id%>&tab=origine-achat-details">D&eacute;tails origine</a></li>
                    <% } %>
                    <% if (caisse.getEtat() >= ConstanteEtat.getEtatValider()) {%>
                    <li class="<%=map.get("ecriture-details")%>"><a href="<%= lien%>?but=<%= pageActuel%>&id=<%= id%>&tab=ecriture-details">&Eacute;criture</a></li>
                    <% }%>
                </ul>
                <div class="tab-content">
                    <jsp:include page="<%= tab%>" >
                        <jsp:param name="idmere" value="<%= id%>" />
                    </jsp:include>
                </div>
            </div>

        </div>
    </div>


</div>
</div>
</div>

<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>