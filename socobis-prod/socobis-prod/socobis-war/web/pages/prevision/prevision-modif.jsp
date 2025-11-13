<%@page import="prevision.*"%>
<%@page import="affichage.*"%>
<%@page import="user.*"%>

<%
    try{

        UserEJB user = (UserEJB) session.getValue("u");
        Prevision prevision = new Prevision();
        String id = request.getParameter("id");
        PageUpdate pageUpdate = new PageUpdate( prevision, request, user );
        pageUpdate.setTitre("Modification de la pr&eacute;vision");
        String lien = (String) session.getValue("lien");
        pageUpdate.setLien(lien);

        Liste[] listes = new Liste[1];
        listes[0] = new Liste("idDevise", new caisse.Devise(), "val", "id");
        pageUpdate.getFormu().changerEnChamp(listes);
        pageUpdate.getFormu().getChamp("compte").setLibelle("Compte de regroupement");
        pageUpdate.getFormu().getChamp("debit").setLibelle("d&eacute;pense");
        pageUpdate.getFormu().getChamp("daty").setLibelle("date");
        pageUpdate.getFormu().getChamp("designation").setLibelle("d&eacute;signation");
        pageUpdate.getFormu().getChamp("credit").setLibelle("recette");
        pageUpdate.getFormu().getChamp("etat").setVisible(false);
        pageUpdate.getFormu().getChamp("idCaisse").setVisible(false);
        pageUpdate.getFormu().getChamp("idVenteDetail").setVisible(false);
        pageUpdate.getFormu().getChamp("idVirement").setVisible(false);
        pageUpdate.getFormu().getChamp("idDevise").setLibelle("Devise");
        pageUpdate.getFormu().getChamp("idOp").setVisible(false);
        pageUpdate.getFormu().getChamp("idTiers").setLibelle("Tiers");
        pageUpdate.getFormu().getChamp("idTiers").setPageAppelComplete("client.Client","id","Client");
        pageUpdate.getFormu().getChamp("idOrigine").setVisible(false);
        pageUpdate.getFormu().getChamp("idFacture").setVisible(false);
        String[] champOrdre={"daty","id","designation","debit","credit","iddevise","taux","idTiers","compte","idCaisse","idVenteDetail","idVirement","idOp","idOrigine","idFacture","etat"};
        pageUpdate.getFormu().setOrdre(champOrdre);
        pageUpdate.preparerDataFormu();
        String classe = "prevision.Prevision";
        String bute = "prevision/prevision-fiche.jsp";
        String nomTable = "PREVISION";

%>


<div class="content-wrapper">
    <div class="row">
    <div class="col-md-3"></div>
        <div class="col-md-6">
            <div class="box-fiche">
                <div class="box">
                    <div class="box-title with-border">
                        <h1 class="box-title"><a href=<%= lien + "?but=prevision/prevision-fiche.jsp&id="+id%> <i class="fa fa-arrow-circle-left"></i></a><%= pageUpdate.getTitre() %></h1>
                    </div>
                    <form action="<%= lien %>?but=apresTarif.jsp&id=<%= id %>" method="post">
                        <%
                            out.println(pageUpdate.getFormu().getHtmlInsert());
                        %>
                        <div class="row">
                            <div class="col-md-11">
                                <button class="btn btn-primary pull-right" name="Submit2" type="submit">Valider</button>
                            </div>
                            <br><br>
                        </div>
                        <input name="acte" type="hidden" id="acte" value="update">
                        <input name="bute" type="hidden" id="bute" value="<%= bute %>">
                        <input name="classe" type="hidden" id="classe" value="<%= classe %>">
                        <input name="rajoutLien" type="hidden" id="rajoutLien" value="id-<%= id %>" >
                        <input name="nomtable" type="hidden" id="nomtable" value="<%= nomTable %>">
                    </form>
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