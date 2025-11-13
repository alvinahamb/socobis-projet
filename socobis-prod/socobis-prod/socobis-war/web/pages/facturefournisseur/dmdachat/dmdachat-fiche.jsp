<%@ page import="affichage.*" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page import="faturefournisseur.DmdAchatLib" %>

<%
    try{
        String lien = (String) session.getValue("lien");
        DmdAchatLib t = new DmdAchatLib();
        PageConsulte pc = new PageConsulte(t, request, (user.UserEJB) session.getValue("u"));
        t = (DmdAchatLib) pc.getBase();
        String id=pc.getBase().getTuppleID( );
        pc.getChampByName("id").setLibelle("ID");
        pc.getChampByName("daty").setLibelle("Date de la demande d'achat");
        pc.getChampByName("remarque").setLibelle("Remarque");
        pc.getChampByName("fournisseurLib").setLibelle("Fournisseur");
        pc.getChampByName("fournisseur").setVisible(false);
        pc.getChampByName("etat").setVisible(false);
        pc.getChampByName("etatlib").setLibelle("&Eacute;tat");

        pc.setTitre("Fiche de demande d'achat");
        String pageModif = "facturefournisseur/dmdachat/dmdachat-saisie.jsp";
        String pageActuel = "facturefournisseur/dmdachat/dmdachat-fiche.jsp";

        Map<String, String> map = new HashMap<String, String>();
        map.put("inc/dmdachat-details", "");

        String tab = request.getParameter("tab");
        if (tab == null) {
            tab = "inc/dmdachat-details";
        }
        map.put(tab, "active");
        tab = tab + ".jsp";
        DmdAchatLib da=(DmdAchatLib)pc.getBase();
%>
<div class="content-wrapper">
    <h1 class="box-title"><a href=<%= lien + "?but=facturefournisseur/dmdachat/dmdachat-liste.jsp"%> ><i class="fa fa-angle-left"></i></a><% out.println(pc.getTitre()); %></h1>
    <div class="row m-0">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <div class="box-fiche">
                <div class="box">
                    <div class="box-body">
                        <%
                            out.println(pc.getHtml());
                        %>
                        <div class="box-footer">
                            <% if(da.getEtat()<11){ %>
                            <a class="btn btn-primary pull-right" href="<%= lien + "?but=apresTarif.jsp&acte=valider&id=" + id + "&bute=facturefournisseur/dmdachat/dmdachat-fiche.jsp&classe=faturefournisseur.DmdAchat&nomtable=DMDACHAT"%> " style="margin-right: 10px">Valider</a>
                            <a class="btn btn-warning pull-right"  href="<%= lien + "?but="+ pageModif +"&id=" + id +"&acte=update"%>" style="margin-right: 10px">Modifier</a>
                            <% } %>
                            <% if(da.getEtat()>=11){ %>
                            <a class="btn btn-secondary pull-right" href="<%= lien + "?but=bondecommande/bondecommande-saisie.jsp&iddmdachat=" + id %> " style="margin-right: 10px">G&eacute;n&eacute;rer Bon de commande</a>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row m-0">
        <div class="col-md-12 nopadding">
            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <li class="<%=map.get("inc/dmdachat-details")%>"><a href="<%= lien %>?but=<%= pageActuel %>&id=<%= id %>&tab=inc/dmdachat-details">D&eacute;tails</a></li>
                </ul>
                <div class="tab-content">
                    <jsp:include page="<%= tab %>" >
                        <jsp:param name="id" value="<%= id %>" />
                    </jsp:include>
                </div>
            </div>

        </div>
    </div>


</div>
<%
    } catch (Exception e) {
        e.printStackTrace();
    } %>