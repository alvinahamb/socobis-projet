


<%@page import="faturefournisseur.Fournisseur"%>
<%@page import="affichage.PageInsert"%> 
<%@page import="user.UserEJB"%> 
<%@page import="bean.TypeObjet"%> 
<%@page import="affichage.Liste"%>
<%@ page import="mg.cnaps.compta.ConstanteCompta" %>

<%
    try{
    String autreparsley = "data-parslsey-range='[8, 40]' required";
    UserEJB u = (user.UserEJB) session.getValue("u");
    String  mapping = "faturefournisseur.Fournisseur",
            nomtable = "FOURNISSEUR",
            apres = "fournisseur/fournisseur-fiche.jsp",
            titre = "Insertion fournisseur";
    
    Fournisseur  caisse = new Fournisseur();
    PageInsert pi = new PageInsert(caisse, request, u);
    pi.setLien((String) session.getValue("lien"));  
    pi.getFormu().getChamp("nif").setLibelle("NIF");
    pi.getFormu().getChamp("stat").setLibelle("STAT");
    pi.getFormu().getChamp("codePostal").setLibelle("Code Postal");
    pi.getFormu().getChamp("compte").setLibelle("Compte G&eacute;n&eacute;ral");
    pi.getFormu().getChamp("compte").setDefaut(ConstanteCompta.compte_fournisseur);
    pi.getFormu().getChamp("compteauxiliaire").setVisible(false);
    //pi.getFormu().getChamp("compte").setPageAppelComplete("mg.cnaps.compta.ComptaCompte", "compte", "COMPTA_COMPTE");
    pi.getFormu().getChamp("idtypefournisseur").setLibelle("Type de Fournisseur");
    pi.preparerDataFormu();
            if(request.getParameter("acte")!=null){
            titre = "Modification fournisseur";
        }
%>
<div class="content-wrapper">
    <h1> <%=titre%></h1>
    
    <form action="<%=pi.getLien()%>?but=apresTarif.jsp" method="post" name="<%=nomtable%>" id="<%=nomtable%>">
    <%
        pi.getFormu().makeHtmlInsertTabIndex();
        out.println(pi.getFormu().getHtmlInsert());
       out.println(pi.getHtmlAddOnPopup());
    %>
    <input name="acte" type="hidden" id="nature" value="insert">
    <input name="bute" type="hidden" id="bute" value="<%=apres%>">
    <input name="classe" type="hidden" id="classe" value="<%=mapping%>">
    <input name="nomtable" type="hidden" id="nomtable" value="<%=nomtable%>">
    </form>
</div>

<%
} catch (Exception e) {
    e.printStackTrace();
%>
<script language="JavaScript"> alert('<%=e.getMessage()%>');
    history.back();</script>

<% }%>