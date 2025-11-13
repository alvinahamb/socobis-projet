
<%@page import="caisse.ReportCaisseCpl"%>
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
    ReportCaisseCpl caisse = new ReportCaisseCpl();
    PageConsulte pc = new PageConsulte(caisse, request, u);
    pc.setTitre("Fiche report caisse");
    caisse=(ReportCaisseCpl) pc.getBase();
    String id=pc.getBase().getTuppleID();
    pc.getChampByName("idCaisseLib").setLibelle("Caisse");
    pc.getChampByName("daty").setLibelle("Date");
    pc.getChampByName("IdCaisse").setVisible(false);
    pc.getChampByName("Etat").setVisible(false);
    pc.getChampByName("MontantTheorique").setVisible(false);
    pc.getChampByName("etatlib").setLibelle("Etat");
    String lien = (String) session.getValue("lien");
    String pageModif = "caisse/report/reportCaisse-saisie.jsp";
    String classe = "caisse.ReportCaisse";
%>

<div class="content-wrapper">
    <h1 class="box-title"><a href=<%= lien + "?but=caisse/report/reportCaisse-liste.jsp"%>> <i class="fa fa-angle-left"></i></a><%=pc.getTitre()%></h1>
    <div class="row m-0">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <div class="box-fiche">
                <div class="box">
                    <div class="box-title with-border">
                    </div>
                    <div class="box-body">
                        <%
                            out.println(pc.getHtml());
                        %>
                        <div class="box-footer">
                            <% if(caisse.getEtat() < ConstanteEtat.getEtatValider()) { %>
                            <a class="btn btn-primary pull-right" href="<%= (String) session.getValue("lien") + "?but=apresTarif.jsp&acte=valider&id=" + request.getParameter("id") + "&bute=caisse/report/reportCaisse-fiche.jsp&classe=" + classe %> " style="margin-right: 10px">Viser</a>
                            <a class="btn btn-secondary pull-right"  href="<%= lien + "?but="+ pageModif +"&id=" + id +"&acte=update"%>" style="margin-right: 10px">Modifier</a>
                            <% } %>
                             <% if(caisse.getEtat()== 11) { %>
                                <a class="pull-left btn btn-danger"  href="<%= (String) session.getValue("lien") + "?but=apresTarif.jsp&acte=annulerVisa&id=" + request.getParameter("id") + "&bute=caisse/report/reportCaisse-fiche.jsp&classe=" + classe %>">Annuler Visa</a>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

