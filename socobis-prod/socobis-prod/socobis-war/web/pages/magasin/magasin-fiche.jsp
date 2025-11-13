<%@page import="magasin.Magasin"%>
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
try{
    Magasin unite = new Magasin();
    unite.setNomTable("magasinlib");
    PageConsulte pc = new PageConsulte(unite, request, u);
    pc.setTitre("Fiche Magasin");
    pc.getBase();
    Magasin magasin = (Magasin)pc.getBase();
    String id = magasin.getTuppleID();
    pc.getChampByName("id").setLibelle("Id");
    pc.getChampByName("val").setLibelle("Libell&eacute;");
    pc.getChampByName("desce").setLibelle("Description");
    pc.getChampByName("idPointlib").setLibelle("Point");
    pc.getChampByName("idTypeMagasinlib").setLibelle("Type magasin");
    pc.getChampByName("idProduitlib").setLibelle("Produit");
    String lienModif = "&acte=update";

    pc.getChampByName("idPoint").setVisible(false);
    pc.getChampByName("idTypeMagasin").setVisible(false);
    pc.getChampByName("idProduit").setVisible(false);
    String lien = (String) session.getValue("lien");
    String pageModif = "magasin/magasin-saisie.jsp"+lienModif;
    String classe = "magasin.Magasin";
%>

<div class="content-wrapper">
    <h1 class="box-title"><a href=<%= lien + "?but=magasin/magasin-liste.jsp"%>> <i class="fa fa-angle-left"></i></a><%=pc.getTitre()%></h1>
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
                            <a class="btn btn-secondary pull-right"  href="<%= lien + "?but="+ pageModif +"&id=" + id%>" style="margin-right: 10px">Modifier</a>
                            <a  class="pull-left btn btn-danger" href="<%= lien + "?but=apresTarif.jsp&id=" + id+"&acte=delete&bute=magasin/magasin-liste.jsp&classe="+classe %>">Supprimer</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%
    }catch(Exception e){
        e.printStackTrace();
    }
%>

