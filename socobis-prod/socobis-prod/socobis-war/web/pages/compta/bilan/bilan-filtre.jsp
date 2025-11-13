<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="utilitaire.Utilitaire"%>
<%@page import="bean.CGenUtil" %>
<%@page import="bean.TypeObjet" %>
<%
    String lang = String.valueOf(session.getAttribute("lang"));
    //String[] mots = {"Journal", "Mois", "Annee","Afficher"};
    //String[] ret = Utilitaire.transformerLangue(mots, lang);

    TypeObjet journal, liste[];
    journal = new TypeObjet();
    journal.setNomTable("COMPTA_JOURNAL");
    liste = (TypeObjet[]) CGenUtil.rechercher(journal, null, null, "");
%>
<script>

    function ecranBilan() {
        var annee,typeetat;
        typeetat = $('#typeetat').val();
        annee = $('#annee').val();
        if(annee!='' && annee>0){
            if (typeetat=='1'){
                url = 'compta/bilan/bilanActif.jsp?typeetat=' + typeetat +'&annee=' + annee;
                window.open(url, "", "titulaireresizable=no,scrollbars=yes,location=no,width=1009,height=532,top=0,left=0");
            }
            else if(typeetat=='2'){
                url = 'compta/bilan/bilanPassif.jsp?typeetat=' + typeetat +'&annee=' + annee;
                window.open(url, "", "titulaireresizable=no,scrollbars=yes,location=no,width=1009,height=532,top=0,left=0");
            }
            else if(typeetat=='3'){
                url = 'compta/bilan/compteResultat.jsp?typeetat=' + typeetat +'&annee=' + annee;
                window.open(url, "", "titulaireresizable=no,scrollbars=yes,location=no,width=1009,height=532,top=0,left=0");
            }

        } else {
            alert('Champ date manquant.');
        }
    }

</script>
<div class="content-wrapper" style="padding: 15px">
    <div class="row">
        <div class="row col-md-12">
            <div class="box box-solid">
                <div class="box-header with-border">
                    <h1 class="box-title">&Eacute;tats Financiers
                    </h1>
                </div>
                <div class="box-body">
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 p-1">
                        <label for="typeetat">Type &Eacute;tat
                        </label>
                        <select name="typeetat" id="typeetat" class="form-control">b
                            <option value="1">Bilan Actif</option>
                            <option value="2">Bilan Capitaux Propres et Passif</option>
                            <option value="3">Compte de R&eacute;sultat</option>
                        </select>
                    </div>
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 p-1">
                        <label for="annee">Ann&eacute;e
                        </label>
                        <input type="text" id="annee" name="annee" class="form-control"
                               value="<%= Utilitaire.getAnneeEnCours()%>">
                    </div>
                    <div class="form-group p-1 col-lg-12 col-md-12 col-sm-12 d-flex justify-content-end align-items-center filter-option " >
                        <a class="btn btn-apj-secondary pull-right" type="submit"
                           onclick="ecranBilan()" style="background-color:#fff; color:rgba(28,38,61,255); box-shadow: rgba(136, 165, 191, 0.48) 6px 2px 16px 0px, rgba(255, 255, 255, 0.8) -6px -2px 16px 0px;">Afficher
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>