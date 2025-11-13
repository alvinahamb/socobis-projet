
<%@page import="faturefournisseur.Fournisseur"%>
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
    Fournisseur caisse = new Fournisseur();
    PageConsulte pc = new PageConsulte(caisse, request, u);
    pc.setTitre("Fiche fournisseur");
    pc.getBase();
    String id=pc.getBase().getTuppleID();
    pc.getChampByName("codePostal").setLibelle("code postal");
    pc.getChampByName("compte").setLibelle("Compte G&eacute;n&eacute;ral");
    pc.getChampByName("compteauxiliaire").setLibelle("Compte Auxiliaire");
    String lien = (String) session.getValue("lien");
    String pageModif = "fournisseur/fournisseur-saisie.jsp";
    String classe = "faturefournisseur.Fournisseur";
%>

<div class="content-wrapper">
    <h1 class="box-title"><a href=<%= lien + "?but=fournisseur/fournisseur-liste.jsp"%>> <i class="fa fa-angle-left"></i></a><%=pc.getTitre()%></h1>
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
                                 <a class="btn btn-secondary pull-right"  href="<%= lien + "?but="+ pageModif +"&id=" + id+"&acte=update"%>" style="margin-right: 10px">Modifier</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

