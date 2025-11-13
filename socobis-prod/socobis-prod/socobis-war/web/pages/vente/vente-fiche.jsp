<%@page import="vente.VenteLib"%>
<%@ page import="user.*" %>
<%@ page import="bean.*" %>
<%@ page import="utilitaire.*" %>
<%@ page import="constante.ConstanteEtat" %>
<%@ page import="affichage.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@page import="fichier.AttacherFichier"%>
<%@page import="configuration.*"%>
<%@page import="uploadbean.*"%>

<%
    try {
        //Information sur les navigations via la page
        String lien = (String) session.getValue("lien");
        String pageModif = "vente/vente-modif.jsp";
        String classe = "vente.Vente";
        String pageActuel = "vente/vente-fiche.jsp";

        //Information sur la fiche
        VenteLib dp = new VenteLib();
        PageConsulte pc = new PageConsulte(dp, request, (user.UserEJB) session.getValue("u"));
        dp = (VenteLib) pc.getBase();
        request.setAttribute("vente", dp);
        String id = request.getParameter("id");
        pc.getChampByName("id").setLibelle("Id");
        pc.getChampByName("designation").setLibelle("D&eacute;signation");
        pc.getChampByName("remarque").setLibelle("Remarque");
        pc.getChampByName("modelivraisonlib").setLibelle("Mode de livraison");
        pc.getChampByName("fraislivraison").setLibelle("Frais de livraison");
        pc.getChampByName("modelivraison").setVisible(false);
        pc.getChampByName("daty").setLibelle("Date");
        pc.getChampByName("etat").setVisible(false);
        pc.getChampByName("idMagasin").setVisible(false);
        pc.getChampByName("etatlib").setLibelle("&Eacute;tat");
        pc.getChampByName("idDevise").setLibelle("Devise");
        pc.getChampByName("idMagasinLib").setLibelle("Point de vente");
        pc.getChampByName("provincelib").setLibelle("Province");
        pc.getChampByName("idClient").setVisible(false);
        pc.getChampByName("idClientLib").setLibelle("Client");
        pc.getChampByName("montanttotal").setLibelle("Montant HT");
        pc.getChampByName("montanttva").setLibelle("Montant TVA");
        pc.getChampByName("montantttc").setLibelle("Montant TTC");
        pc.getChampByName("montantTtcAr").setVisible(false);
        pc.getChampByName("Montantpaye").setLibelle("Montant Pay&eacute;");
        pc.getChampByName("Montantreste").setLibelle("Reste &agrave; payer");
        pc.getChampByName("Tauxdechange").setLibelle("Taux de change");
        pc.getChampByName("montantRevient").setVisible(false);
        pc.getChampByName("idprovince").setVisible(false);
        pc.getChampByName("datyprevu").setLibelle("Ech&eacute;ance de paiement");
        pc.getChampByName("margeBrute").setVisible(false);
        pc.getChampByName("montant").setVisible(false);
        pc.getChampByName("modepaiement").setVisible(false);
        pc.getChampByName("referencefact").setVisible(false);
        pc.getChampByName("idOrigine").setLibelle("Origine");
        pc.getChampByName("modepaiementlib").setLibelle("Mode de paiement");
        pc.getChampByName("montantremise").setLibelle("Montant Remise");
        pc.getChampByName("poids").setLibelle("Poids (Kg)");
        pc.getChampByName("referencefacture").setLibelle("R&eacutef&eacute;rence de la facture");
        pc.getChampByName("numerofacture").setLibelle("Num&eacute;ro de la facture");

        pc.getChampByName("idOrigine").setLien(lien + "?but=vente/bondecommande/bondecommande-fiche.jsp", "id=");

        pc.setTitre("D&eacute;tails de la facture client");
        Onglet onglet = new Onglet("page1");
        onglet.setDossier("inc");
        Map<String, String> map = new HashMap<String, String>();
        map.put("vente-details", "");
        map.put("encaissement-vise-liste", "");
        map.put("livraison-detail", "");
        map.put("liste-prevision", "");
        if(dp.getEtat() >= ConstanteEtat.getEtatValider()) {
            map.put("mvtcaisse-details", "");
            map.put("ecriture-detail", "");
            map.put("avoirfc-details", "");
            map.put("paiementFacture-details.jsp", "");
        }
        String tab = request.getParameter("tab");
        if (tab == null) {
            tab = "vente-details";
        }
        map.put("avoir-details", "");
        map.put(tab, "active");
        tab = "inc/" + tab + ".jsp";
        AttacherFichier[] fichiers = UploadService.getUploadFile(request.getParameter("id"));
        configuration.CynthiaConf.load();
        String cdn = configuration.CynthiaConf.properties.getProperty("cdnReadUri");
        String projectName = pc.getChampByName("designation").getValeur()
                    .replace("'","_")                
                    .replace("/","_")
                    .replace("-","_")
                    .replace(":", "_")
                    .replace("*", "_")
                    .replace(" ", "_");
        String dossierTemp = "vente/files/"+projectName;
        String dossier = dossierTemp;

%>
<div class="content-wrapper">
    <h1 class="box-title"><a href="<%=(String) session.getValue("lien")%>?but=vente/vente-liste.jsp"><i class="fa fa-angle-left"></i></a><%=pc.getTitre()%></h1>
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
                            <% if (dp.getEtat() < ConstanteEtat.getEtatValider()) {%>
                            <a class="btn btn-primary pull-right" href="<%= (String) session.getValue("lien") + "?but=apresTarif.jsp&acte=valider&id=" + request.getParameter("id") + "&bute=vente/vente-fiche.jsp&classe=" + classe%> " style="margin-right: 10px">Viser</a>
                            <a class="btn btn-secondary pull-right" href="<%= lien + "?but=" + pageModif + "&id=" + id%>" style="margin-right: 10px">Modifier</a>
                            <a class="pull-left btn btn-danger" href="<%= lien + "?but=apresTarif.jsp&id=" + id + "&acte=annuler&bute=vente/vente-fiche.jsp&classe=" + classe%>">Annuler</a>
                            <% }%>
                            <% if (dp.getEtat() >= ConstanteEtat.getEtatValider()) {%>
                            <a class="btn btn-secondary pull-right" href="<%= (String) session.getValue("lien") + "?but=pertegain/pertegainimprevue-saisie.jsp&idorigine=" + id%> " style="margin-right: 10px">G&eacute;n&eacute;rer Perte/Gain</a>
                            <% }%>
                            <% if (dp.getEtat() >= ConstanteEtat.getEtatValider()) {%>
                                <a class="btn btn-secondary pull-right" href="<%= (String) session.getValue("lien") + "?but=caisse/mvt/mvtCaisse-saisie-entree-fc.jsp&idOrigine=" + request.getParameter("id") + "&montant="+dp.getMontantreste()+"&devise=" + dp.getIdDevise()+"&tiers="+dp.getIdClient()+"&taux="+dp.getTauxdechange() %> " style="margin-right: 10px">Encaisser</a>
                            <% }%>
                            <% if (dp.getEtat() >= ConstanteEtat.getEtatValider()) {%>
                            <!--a class="btn btn-secondary pull-right" href="<%= (String) session.getValue("lien") + "?but=bondelivraison-client/apresLivraisonFacture.jsp&id=" + request.getParameter("id") + "&bute=vente/encaissement-modif.jsp&classe=" + classe%> " style="margin-right: 10px">Livrer</a-->
                            <a class="btn btn-secondary pull-right" href="<%= (String) session.getValue("lien") + "?but=bondelivraison-client/bondelivraison-client-saisie.jsp&idfc_client=" + request.getParameter("id") + "&classe=" + classe%> " style="margin-right: 10px">Livrer</a>
                            <% }%>
                            <% if (dp.getEtat() > 11) {%>
                                <a class="btn btn-tertiary pull-right" href="<%= (String) session.getValue("lien") + "?but=vente/bondelivraison-client/facturer-livraison.jsp&idVente=" + request.getParameter("id")%>" style="margin-right: 10px">Attacher BL</a>
                            <% } %>
                             <% if (dp.getEtat() >= ConstanteEtat.getEtatValider()) {%>
                                <a class="btn btn-primary pull-right" href="<%= (String) session.getValue("lien") + "?but=avoir/avoirFC-saisie.jsp&idvente="+dp.getId()+"&classe=" + classe%> " style="margin-right: 10px">G&eacute;n&eacute;rer avoir</a>
                                <a class="btn btn-secondary pull-right" href="<%= (String) session.getValue("lien") + "?but=vente/paiement-facture-par-avoir.jsp&idvente="+dp.getId()%> " style="margin-right: 10px">Payer par Ristourne/Avoir</a>
                                <a class="btn btn-secondary pull-right" href="<%= (String) session.getValue("lien") + "?but=vente/paiement-facture-par-traite.jsp&idvente="+dp.getId()%> " style="margin-right: 10px">Payer par une Traite</a>
                                    <%--<a class="btn btn-primary pull-right" href="<%= (String) session.getValue("lien") + "?but=vente/planPaiement-saisie.jsp&idvt="+id+"&classe=vente.Vente&table="+dp.getNomTable()+"&bute="+pageActuel%>" style="margin-right: 10px">Plan de Paiement</a>--%>
                                                                <% }%>
                                                            <a class="btn btn-tertiary pull-right"  href="${pageContext.request.contextPath}/ExportPDF?action=fiche_vente_nouveau&id=<%=request.getParameter("id")%>" style="margin-right: 10px">Imprimer</a>
                                                            <a class="btn btn-tertiary pull-right"  href="<%= (String) session.getValue("lien") + "?but=facture/reference-facture-saisie.jsp&id="+id%>" style="margin-right: 10px">Ajouter r&eacute;f&eacute;rence Facture</a>


                                                                <a class="btn btn-tertiary pull-right" href="<%= (String) session.getValue("lien") + "?but=pageupload.jsp&id=" + request.getParameter("id") + "&dossier=" + dossier + "&nomtable=ATTACHER_FICHIER&procedure=GETSEQ_ATTACHER_FICHIER&bute=" + pageActuel + "&id=" + request.getParameter("id") + "&nomprj="+ pc.getChampByName("designation").getValeur() %>" style="margin-right: 10px;/*! display: block; *//*! margin: 5px auto; *//*! width: 111px; *//*! max-width: 111px; */">Attacher Fichier</a>
                                                                <br>        
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
                                                        <li class="<%=map.get("vente-details")%>"><a href="<%= lien%>?but=<%= pageActuel%>&id=<%= id%>&tab=vente-details">D&eacute;tail(s)</a></li>
                                                        <li class="<%=map.get("ecriture-detail")%>"><a href="<%= lien%>?but=<%= pageActuel%>&id=<%= id%>&tab=ecriture-detail">&Eacute;critures</a></li>
                                                            <li class="<%=map.get("livraison-details")%>"><a href="<%= lien%>?but=<%= pageActuel%>&id=<%= id%>&tab=livraison-details">Livraison D&eacute;tails</a></li>
                                                            <li class="<%=map.get("liste-prevision")%>"><a href="<%= lien%>?but=<%= pageActuel%>&id=<%=id%>&tab=liste-prevision">Plan de paiement</a></li>
                                                            <% if (dp.getEtat() >= ConstanteEtat.getEtatValider()) {%>
                                                        
                                                        <li class="<%=map.get("pertegainimprevue-details")%>"><a href="<%= lien%>?but=<%= pageActuel%>&id=<%= id%>&tab=pertegainimprevue-details">Gain(s) ou perte(s)</a></li>
                                                        <li class="<%=map.get("encaissement-vise-liste")%>"><a href="<%= lien%>?but=<%= pageActuel%>&id=<%= id%>&tab=encaissement-vise-liste">Paiement(s)</a></li>
                                                        <li class="<%=map.get("avoirfc-details")%>"><a href="<%= lien%>?but=<%= pageActuel%>&id=<%= id%>&tab=avoirfc-details">Avoir(s)</a></li>
                                                        <li class="<%=map.get("paiementFacture-details")%>"><a href="<%= lien%>?but=<%= pageActuel%>&id=<%= id%>&tab=paiementFacture-details">Liaison paiement(s)</a></li>   
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

                                        <div class="row m-0">
                                    <%--<div class="col-md-1"></div>--%>
                                <div class="col-md-12 bottom-vente-fiche nopadding">
                                                <div class="box">
                                                    <h2 class="box-title" style="margin-left: 10px;">Les fichiers attach&eacute;s</h2>
                                                    <div class="box-body" style="padding: 0 20px 20px 20px;">
                                                        <table class="table table-striped table-bordered table-condensed tree" style="color: #4e4e4e;">
                                                            <thead>
                                                                <tr>
                                                                    <th class='contenuetable'></th>
                                                                    <th class='contenuetable'>Libell&eacute;</th>
                                                                    <th class='contenuetable'>Fichier</th>
                                                                    <th class='contenuetable'>Date d`upload</th>
                                                                    <th class='contenuetable'>T&eacute;l&eacute;charger</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%if (fichiers == null || fichiers.length == 0) { %>
                                                                <tr>
                                                                    <td colspan="3" style="text-align: center;"><strong>Aucun fichier</strong></td>
                                                                </tr>
                                                                <%} else {
                                                                    for (AttacherFichier fichier : fichiers) {%> 
                                                                <tr class="treegrid-1 treegrid-expanded">
                                                                    <td><span class="treegrid-expander glyphicon glyphicon-minus"></span></td>
                                                                    <td><%=fichier.getChemin()%></td>
                                                                    <td><%=Utilitaire.champNull(fichier.getLibelle())%></td>
                                                                    <td><%=fichier.getDaty()%></td>
                                                                    <td>
                                    <!--<form action="../UploadDownloadFileServlet" method="get">
                                        <input type="submit" value="download">
                                        <input type="hidden" name="fileName" value="<%=cdn + dossier + "/" + fichier.getChemin()%>">
                                    </form>-->
                                    <a href="../FileManager2?parent=<%= "/"+dossier + "/" +fichier.getChemin()  %>" class="btn btn-success" >T&eacute;l&eacute;charger</a>
                                    </td>
                                </tr>
                                <%}
                                    }%>

                            </tbody>

                        </table>
                    </div>
                </div>
            </div>
        </div>          
</div>
<style>
    .bottom-vente-fiche {
        padding: 0 30px 0 30px; !important;
    }
</style>

<%
    } catch (Exception e) {
        e.printStackTrace();
    }%>
