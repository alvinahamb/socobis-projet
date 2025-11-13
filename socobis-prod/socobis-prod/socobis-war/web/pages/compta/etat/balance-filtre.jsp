<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@page import="utilitaire.Utilitaire"%> 
<%@page import="bean.CGenUtil" %> 
<%@page import="bean.TypeObjet" %> 

<%
    String lang = String.valueOf(session.getAttribute("lang"));
    String[] mots = {"Generer etats", "Exercice", "Type de compte", "Type etat", "Mois debut", "Mois fin", "Du compte", "Au compte", "Balance comparative", "Afficher"};
    String[] ret = Utilitaire.transformerLangue(mots, lang);

    TypeObjet typeCompte, lsTypeCompte[];
    typeCompte = new TypeObjet();
    typeCompte.setNomTable("compta_type_compte");
    lsTypeCompte = (TypeObjet[]) CGenUtil.rechercher(typeCompte, null, null, "");
    
    
    String today = Utilitaire.dateDuJour();
    String todayIso = today;
    if (today.contains("/")) {
        String[] parts = today.split("/");
        todayIso = parts[2] + "-" + parts[1] + "-" + parts[0]; 
    }
%>

<script>
    function ecranBalanceCompteGeneral() {
        var date1, date2, plage1, plage2, exercice, typecompte, url, typeetat, balanceCompa, etat;

        typeetat = $('#typeetat').val();
        exercice = $('#exercice').val();
        typecompte = $('#typecompte').val();
        etat = $('#etat').val();
        plage1 = $('#plage1').val();
        plage2 = $('#plage2').val();
        balanceCompa = $('#balanceCompa').val();

        // Si Grand Livre → on prend dateDebut / dateFin
        if (typeetat == '2') {
            date1 = $('#dateDebut').val();
            date2 = $('#dateFin').val();
        } else {
            date1 = $('#mois1').val();
            date2 = $('#mois2').val();
        }

        if (date1 != '' && date2 != '') {
            if (typeetat == '1') { // Balance générale
                if (balanceCompa != null && balanceCompa >= 1900) {
                    url = 'compta/etat/balanceCompa-compta-general-corrige.jsp?moisDebut=' + date1 + '&moisFin=' + date2 + '&debutCompte=' + plage1 + '&finCompte=' + plage2 + '&exercice=' + exercice + '&typeCompte=' + typecompte + '&etat=' + etat + '&balanceCompa=' + balanceCompa;
                } else {
                    url = 'compta/etat/balance-compta-general-corrige.jsp?moisDebut=' + date1 + '&moisFin=' + date2 + '&debutCompte=' + plage1 + '&finCompte=' + plage2 + '&exercice=' + exercice + '&typeCompte=' + typecompte + '&etat=' + etat;
                }
                window.open(url, "", "titulaireresizable=no,scrollbars=yes,location=no,width=1009,height=532,top=0,left=0");
            }

            if (typeetat == '2') { // Grand Livre
                if (plage1 != '' && plage2 != '') {
                    url = 'compta/etat/grand-livre-compte.jsp?dateDebut=' + date1 + '&dateFin=' + date2 + '&compteDebut=' + plage1 + '&compteFin=' + plage2 + '&exercice=' + exercice + '&typeCompte=' + typecompte + '&etat=' + etat;
                    window.open(url, "", "titulaireresizable=no,scrollbars=yes,location=no,width=1009,height=532,top=0,left=0");
                } else {
                    alert('Champ compte manquant.');
                }
            }

            if (typeetat == '3') { // Balance auxiliaire
                url = 'compta/etat/balance-compta-auxiliaire-corrige.jsp?moisDebut=' + date1 + '&moisFin=' + date2 + '&debutCompte=' + plage1 + '&finCompte=' + plage2 + '&exercice=' + exercice + '&typeCompte=' + typecompte + '&etat=' + etat;
                window.open(url, "", "titulaireresizable=no,scrollbars=yes,location=no,width=1009,height=532,top=0,left=0");
            }
        } else {
            alert('Champ date manquant.');
        }
    }

    // Transformation dynamique des champs Mois -> Date
    $(document).ready(function () {
        $("#typeetat").change(function () {
            var val = $(this).val();

            if (val == "2") { // Grand Livre
                $("#blocMois1").html(`
                    <label for="dateDebut">Date début</label>
                    <input type="date" id="dateDebut" name="dateDebut" class="form-control" value="<%=todayIso%>">
                `);

                $("#blocMois2").html(`
                    <label for="dateFin">Date fin</label>
                    <input type="date" id="dateFin" name="dateFin" class="form-control" value="<%=todayIso %>">
                `);

            } else {
                // Restaurer Mois début
                $("#blocMois1").html(`
                    <label for="mois1"><%=ret[4]%></label>
                    <select name="mois1" id="mois1" class="form-control">
                        <option value="1">Janvier</option>
                        <option value="2">Fevrier</option>
                        <option value="3">Mars</option>
                        <option value="4">Avril</option>
                        <option value="5">Mai</option>
                        <option value="6">Juin</option>
                        <option value="7">Juillet</option>
                        <option value="8">Aout</option>
                        <option value="9">Septembre</option>
                        <option value="10">Octobre</option>
                        <option value="11">Novembre</option>
                        <option value="12">Decembre</option>
                    </select>
                `);

                // Restaurer Mois fin
                $("#blocMois2").html(`
                    <label for="mois2"><%=ret[5]%></label>
                    <select name="mois2" id="mois2" class="form-control">
                        <option value="1">Janvier</option>
                        <option value="2">Fevrier</option>
                        <option value="3">Mars</option>
                        <option value="4">Avril</option>
                        <option value="5">Mai</option>
                        <option value="6">Juin</option>
                        <option value="7">Juillet</option>
                        <option value="8">Aout</option>
                        <option value="9">Septembre</option>
                        <option value="10">Octobre</option>
                        <option value="11">Novembre</option>
                        <option value="12">Decembre</option>
                    </select>
                `);
            }
        });
    });
</script>

<div class="content-wrapper">
    <div class="row">
        <div class="row col-md-12">
            <div class="box box-solid">
                <div class="box-header with-border">
                    <h1 class="box-title"><%=ret[0]%></h1>
                </div>
                <div class="box-body">
                    <!-- Exercice -->
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 p-1">
                        <label for="Exercice"><%=ret[1]%></label>
                        <input type="text" id="exercice" name="exercice" class="form-control"
                               value="<%= Utilitaire.getAnneeEnCours()%>">
                    </div>

                    <!-- Type compte -->
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 p-1">
                        <label for="typecompte"><%=ret[2]%></label>
                        <select name="typecompte" id="typecompte" class="form-control">
                            <option value="1">Général</option>
                            <option value="2">Analytique</option>
                        </select>
                    </div>

                    <!-- Type état -->
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 p-1">
                        <label for="typeetat"><%=ret[3]%></label>
                        <select name="typeetat" id="typeetat" class="form-control">
                            <option value="1">Balance Générale</option>
                            <option value="2">Grand Livre</option>
                            <option value="3">Balance Auxiliaire</option>
                        </select>
                    </div>

                    <!-- Etat -->
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 p-1">
                        <label for="etat">Etat</label>
                        <select name="etat" id="etat" class="form-control">
                            <option value="0">Tous</option>
                            <option value="11">Visee</option>
                            <option value="1">Non Visee</option>
                        </select>
                    </div>

                    <!-- Mois début -->
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 p-1" id="blocMois1">
                        <label for="mois1"><%=ret[4]%></label>
                        <select name="mois1" id="mois1" class="form-control">
                            <option value="1">Janvier</option>
                            <option value="2">Fevrier</option>
                            <option value="3">Mars</option>
                            <option value="4">Avril</option>
                            <option value="5">Mai</option>
                            <option value="6">Juin</option>
                            <option value="7">Juillet</option>
                            <option value="8">Aout</option>
                            <option value="9">Septembre</option>
                            <option value="10">Octobre</option>
                            <option value="11">Novembre</option>
                            <option value="12">Decembre</option>
                        </select>
                    </div>

                    <!-- Mois fin -->
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 p-1" id="blocMois2">
                        <label for="mois2"><%=ret[5]%></label>
                        <select name="mois2" id="mois2" class="form-control">
                            <option value="1">Janvier</option>
                            <option value="2">Fevrier</option>
                            <option value="3">Mars</option>
                            <option value="4">Avril</option>
                            <option value="5">Mai</option>
                            <option value="6">Juin</option>
                            <option value="7">Juillet</option>
                            <option value="8">Aout</option>
                            <option value="9">Septembre</option>
                            <option value="10">Octobre</option>
                            <option value="11">Novembre</option>
                            <option value="12">Decembre</option>
                        </select>
                    </div>

                    <!-- Du compte -->
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 p-1">
                        <label for="plage1"><%=ret[6]%></label>
                        <input id="plage1" name="plage1" class="form-control" type="text"/>
                    </div>

                    <!-- Au compte -->
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 p-1">
                        <label for="plage2"><%=ret[7]%></label>
                        <input id="plage2" name="plage2" class="form-control" type="text"/>
                    </div>

                    <!-- Balance comparative -->
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 p-1">
                        <label for="balanceCompa"><%=ret[8]%></label>
                        <input id="balanceCompa" name="balanceCompa" class="form-control" type="number"/>
                    </div>

                    <!-- Bouton -->
                    <div class="form-group p-1 col-lg-12 col-md-12 col-sm-12 d-flex justify-content-end align-items-center filter-option">
                        <a class="btn btn-apj-secondary pull-right" type="submit"
                           onclick="ecranBalanceCompteGeneral()"
                           style="background-color:#fff; color:rgba(28,38,61,255); 
                           box-shadow: rgba(136, 165, 191, 0.48) 6px 2px 16px 0px, 
                           rgba(255, 255, 255, 0.8) -6px -2px 16px 0px;">
                            <%=ret[9]%>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
