
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socobis.export;


import bean.AdminGen;
import bean.CGenUtil;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import proforma.ProformaDetailsLib;
import proforma.ProformaLib;
import reporting.ReportingCdn;
import stock.MvtStockFilleTheorique;
import web.mg.cnaps.servlet.etat.UtilitaireImpression;
import encaissement.*;
import fabrication.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import org.xhtmlrenderer.css.style.derived.StringValue;
import prelevement.PrelevementPompiste;
import utils.ConstanteVente;
import vente.*;
import faturefournisseur.*;
import client.*;
import utilitaire.*;
/**
 *
 * @author Admin
 */
@WebServlet(name = "ExportPDF", urlPatterns = {"/ExportPDF"})
public class ExportPDF extends HttpServlet {
    String nomJasper = "";
    ReportingCdn.Fonctionnalite fonctionnalite = ReportingCdn.Fonctionnalite.RECETTE;
    
    public String getReportPath() throws IOException {
        return getServletContext().getRealPath(File.separator + "report" + File.separator + getNomJasper() + ".jasper");
    }
    public String getNomJasper() {
        return nomJasper;
    }

    public void setNomJasper(String nomJasper) {
        this.nomJasper = nomJasper;
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("fiche_encaissement")) impressionEncaissement(request, response);
        if (action.equalsIgnoreCase("fiche_encaissement_pompiste")) impressionEncaissementPompist(request, response);
        if (action.equalsIgnoreCase("fiche_vente")) fiche_vente(request, response);
        if (action.equalsIgnoreCase("fiche_bc")) fiche_bc(request, response);
        if (action.equalsIgnoreCase("fiche_bl")) fiche_bl(request, response);
        if (action.equalsIgnoreCase("fiche_ordre_fabrication")) fiche_ordre_fabrication(request, response);
        if (action.equalsIgnoreCase("fiche_vente_nouveau")) fiche_vente_nouveau(request, response);
        if (action.equalsIgnoreCase("situation_globale")) situation_globale(request, response);
        if (action.equalsIgnoreCase("offille_situation_globale")) offille_situation_globale(request, response);
        if (action.equalsIgnoreCase("vente_liste")) vente_liste(request, response);
        if (action.equalsIgnoreCase("vente_liste_mere_fille")) vente_liste_mere_fille(request, response);
        if (action.equalsIgnoreCase("proforma")) proforma(request, response);
    }
    private void fiche_bc(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
         String id = request.getParameter("id");
        As_BonDeCommandeCpl v = new As_BonDeCommandeCpl();
        v.setNomTable("As_BonDeCommande_MERECPL");
        As_BonDeCommandeCpl[] enc_mere = (As_BonDeCommandeCpl[]) CGenUtil.rechercher(v, null, null, null,
                " AND ID = '" + id + "'");
        
        if (enc_mere.length > 0) {
          param.put("designation", enc_mere[0].getDesignation());
          param.put("ref", enc_mere[0].getReference());
          param.put("daty", enc_mere[0].getDaty());
          param.put("remarque", enc_mere[0].getRemarque());
          param.put("fournisseur", enc_mere[0].getFournisseurlib());
          param.put("modeP", enc_mere[0].getModepaiementlib());
          param.put("num", id);
          param.put("iddevise", enc_mere[0].getIdDevise());
          param.put("montantHT", enc_mere[0].getMontantHT());
          param.put("montantTVA", enc_mere[0].getMontantTVA());
          param.put("montantTTC", enc_mere[0].getMontantTTC());
          param.put("devise", enc_mere[0].getIdDeviselib());
        }
       
        As_BonDeCommande_Fille_CPL vf = new As_BonDeCommande_Fille_CPL();
        vf.setNomTable("AS_BONDECOMMANDE_CPL");
        As_BonDeCommande_Fille_CPL[] v_fille = (As_BonDeCommande_Fille_CPL[]) CGenUtil.rechercher(vf, null, null, null,
                " AND idbc = '" + id + "'");
        dataSource.addAll(Arrays.asList(v_fille));
        setNomJasper("BonDeCommande");
        UtilitaireImpression.imprimer(request, response, getNomJasper(), param, dataSource, getReportPath());
    }
    
    private void fiche_bl(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
         String id = request.getParameter("id");
        As_BondeLivraisonClient_Cpl v = new As_BondeLivraisonClient_Cpl();
        v.setNomTable("AS_BONDELIVRAISON_CLIENT_CPL");
        As_BondeLivraisonClient_Cpl[] enc_mere = (As_BondeLivraisonClient_Cpl[]) CGenUtil.rechercher(v, null, null, null,
                " AND id = '" + id + "'");
        if (enc_mere.length > 0) {
          param.put("designation", enc_mere[0].getDesignation());
          param.put("daty", enc_mere[0].getDaty());
          param.put("remarque", enc_mere[0].getRemarque());
          param.put("magasin", enc_mere[0].getMagasin());
          param.put("client", enc_mere[0].getIdclientlib());
          param.put("num", id);

        }
        As_BondeLivraisonClientFille_Cpl vf = new As_BondeLivraisonClientFille_Cpl();
        vf.setNomTable("AS_BONLIVRFILLE_CLIENT_CPL");
        As_BondeLivraisonClientFille_Cpl[] v_fille = (As_BondeLivraisonClientFille_Cpl[]) CGenUtil.rechercher(vf, null, null, null,
                " AND numbl = '" + id + "'");
        dataSource.addAll(Arrays.asList(v_fille));
        setNomJasper("BonDeLivraison");
        UtilitaireImpression.imprimer(request, response, getNomJasper(), param, dataSource, getReportPath());
    }
    private void fiche_vente(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
         String id = request.getParameter("id");
        VenteLib v = new VenteLib();
        VenteLib[] enc_mere = (VenteLib[]) CGenUtil.rechercher(v, null, null, null,
                " AND ID = '" + id + "'");
        if (enc_mere.length > 0) {
          param.put("designation", enc_mere[0].getDesignation());
          param.put("magasin", enc_mere[0].getIdMagasinLib());
          param.put("daty", enc_mere[0].getDaty());
          param.put("remarque", enc_mere[0].getRemarque());
          param.put("devise", enc_mere[0].getIdDevise());
          param.put("numFact", id);
        }
       
        VenteDetailsLib vf = new VenteDetailsLib();
        vf.setNomTable("VENTE_DETAILS_CPL");
        VenteDetailsLib[] v_fille = (VenteDetailsLib[]) CGenUtil.rechercher(vf, null, null, null,
                " AND idVente = '" + id + "'");
        dataSource.addAll(Arrays.asList(v_fille));
         String devise = v_fille[0].getIdDevise();
        double montantHT = AdminGen.calculSommeDouble(v_fille,"montantHTLocal");
        double montantTVA = AdminGen.calculSommeDouble(v_fille,"montantTvaLocal");
        double montantTTC = AdminGen.calculSommeDouble(v_fille,"montantTTCLocal");
        
        param.put("montantHT", montantHT);
        param.put("montantTVA", montantTVA);
        param.put("montantTTC", montantTTC);
        param.put("devise", devise);
        
        setNomJasper("factureclient");
        UtilitaireImpression.imprimer(request, response, getNomJasper(), param, dataSource, getReportPath());
    }
    private void impressionEncaissement(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
         String id = request.getParameter("id");
        EncaissementLib eM = new EncaissementLib();
        eM.setNomTable("ENCAISSEMENT_LIB");
        EncaissementLib[] enc_mere = (EncaissementLib[]) CGenUtil.rechercher(eM, null, null, null,
                " AND ID = '" + id + "'");
        if (enc_mere.length > 0) {
          param.put("carburants", Utilitaire.formaterAr(enc_mere[0].getVenteCarburant()));
          param.put("lubrifiants", Utilitaire.formaterAr(enc_mere[0].getVenteLubrifiant()));
          param.put("totalrecette", Utilitaire.formaterAr(enc_mere[0].getTotalRecette()));
          param.put("depense", Utilitaire.formaterAr(enc_mere[0].getDepense()));
          param.put("montantecart", Utilitaire.formaterAr(enc_mere[0].getEcart()));
          param.put("versement", Utilitaire.formaterAr(enc_mere[0].getTotalVersement()));
         
        }
        EncaissementDetailsLib eF = new EncaissementDetailsLib();
        eF.setNomTable("Encaissement_Details_Lib");
        EncaissementDetailsLib[] enc_fille = (EncaissementDetailsLib[]) CGenUtil.rechercher(eF, null, null, null,
                " AND idEncaissement = '" + id + "'");
        dataSource.addAll(Arrays.asList(enc_fille));
        setNomJasper("encaissement");
        UtilitaireImpression.imprimer(request, response, getNomJasper(), param, dataSource, getReportPath());
    }

     private void impressionEncaissementPompist (HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
         String id = request.getParameter("id");
        EncaissementFichePdf eM = new EncaissementFichePdf();
       
        EncaissementFichePdf[] enc_mere = (EncaissementFichePdf[]) CGenUtil.rechercher(eM, null, null, null,
                " AND ID = '" + id + "'");

      
        EncaissementReport er=new EncaissementReport();
        er.setId(id);
        er.init(c);

        if (enc_mere.length > 0) {
          param.put("date", enc_mere[0].getDaty());
          param.put("nom", enc_mere[0].getIdPompisteLib());
          param.put("ecart", enc_mere[0].getEcart());
          param.put("versement", enc_mere[0].getTotalVersement());
          param.put("espece", enc_mere[0].getTotalEspece());
          param.put("om", enc_mere[0].getTotalOrangeMoney());
        }
       
        dataSource.add(er);


        setNomJasper("encaissementPompiste");
        UtilitaireImpression.imprimer(request, response, getNomJasper(), param, dataSource, getReportPath());
    }
    private void fiche_ordre_fabrication(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
       //List dataSource = new ArrayList();
        String id = request.getParameter("id");
        Of t = new Of();
        t.setId(id);
        Of[] of_mere = (Of[]) CGenUtil.rechercher(t, null, null, null,
        " AND ID = '" + id + "'");
        if (of_mere.length > 0) {
          param.put("daty", of_mere[0].getDaty());
          param.put("id", of_mere[0].getId());
        }
      
        MvtStockFilleTheorique mvtst = new MvtStockFilleTheorique();
        HashMap<String, Vector> map = mvtst.getRapprochementParCategorie(id, null, null);
        List dataSource = new ArrayList();
        for (Vector v : map.values()) {
            for (Object obj : v) {
                if (obj instanceof MvtStockFilleTheorique) {
                    dataSource.add((MvtStockFilleTheorique) obj);
                }
            }
        }
        setNomJasper("fiche_ordre_fabrication");
        UtilitaireImpression.imprimer(request, response, getNomJasper(), param, dataSource, getReportPath());
    }
    private void fiche_vente_nouveau(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
         String id = request.getParameter("id");
        VenteLib v = new VenteLib();
        VenteLib[] enc_mere = (VenteLib[]) CGenUtil.rechercher(v, null, null, null,
                " AND ID = '" + id + "'");
        param.put("tel", ConstanteVente.tel);
        param.put("fax", ConstanteVente.fax);
        param.put("tel1", ConstanteVente.tel1);
        param.put("tel2", ConstanteVente.tel2);
        param.put("email", ConstanteVente.email);
        param.put("bni_cl", ConstanteVente.bni_cl);
        param.put("boa", ConstanteVente.boa);
        param.put("bmoi", ConstanteVente.bmoi);
        param.put("bfv_sg", ConstanteVente.bfv_sg);
        param.put("cf", ConstanteVente.cf);
        param.put("stat", ConstanteVente.stat);
        param.put("nif", ConstanteVente.nif);
        param.put("datyJour", Utilitaire.dateDuJour());
        if (enc_mere.length > 0) {
            ClientLib cl = new ClientLib();
            cl.setNomTable("CLIENTLIB");
            ClientLib client = (ClientLib) cl.getById(enc_mere[0].getIdClient(),"CLIENTLIB" , null);
            if (enc_mere.length > 0) {
                param.put("idclient",client.getId());
                param.put("nomClient",client.getNom());
                param.put("adresseClient",client.getAdresse());
                param.put("lieuClient",client.getProvinceLib());
                param.put("telClient",client.getTelephone());
                param.put("nifclient",client.getNif());
                param.put("statclient",client.getStat());
                param.put("cp",client.getCarte());
                param.put("quit","");
                param.put("datyclient",client.getDatecarte());
            } 
            param.put("numfact",enc_mere[0].getNumerofacture());
            param.put("datyfact",enc_mere[0].getDaty());
            param.put("totalcolis",enc_mere[0].getColis());
            param.put("totalpoids",enc_mere[0].getPoids());
            param.put("modepaiement",enc_mere[0].getModepaiementlib());
            param.put("reference",enc_mere[0].getIdOrigine());
            param.put("livraison",enc_mere[0].getLivraison());
            param.put("transport",enc_mere[0].getFrais());
        }
       
        VenteDetailsLib vf = new VenteDetailsLib();
        vf.setNomTable("VENTE_DETAILS_POIDS");
        VenteDetailsLib[] v_fille = (VenteDetailsLib[]) CGenUtil.rechercher(vf, null, null, null,
                " AND idVente = '" + id + "'");
        dataSource.addAll(Arrays.asList(v_fille));
        double montantHT = AdminGen.calculSommeDouble(v_fille,"montantHTLocal");
        double montantTVA = AdminGen.calculSommeDouble(v_fille,"montantTvaLocal");
        double montantTTC = AdminGen.calculSommeDouble(v_fille,"montantTTCLocal");
        double frais = AdminGen.calculSommeDouble(v_fille,"frais");
        param.put("montantNetLettre",ChiffreLettre.convertRealToString(montantTTC));
        param.put("totalbrut",montantHT);
        param.put("participation_transport",frais);
        param.put("totalHt",montantHT);
        param.put("total_taxes",montantTVA);
        param.put("montantttc",montantTTC);
        //param.put("escompte","");
        param.put("net_payer",montantTTC);
        param.put("net_payer_fmg",montantTTC*5);
        setNomJasper("facture-vente");
        UtilitaireImpression.imprimer(request, response, getNomJasper(), param, dataSource, getReportPath());
    }
    private void situation_globale(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
        String id = request.getParameter("id");
        Of t = new Of();
        t.setId(id);
        Of[] of_mere = (Of[]) CGenUtil.rechercher(t, null, null, null,
        " AND ID = '" + id + "'");
        if (of_mere.length > 0) {
          param.put("daty", of_mere[0].getDaty());
          param.put("id", of_mere[0].getId());
        }

        OfEtatGlobal ofGlobal = new OfEtatGlobal(id);
        Of of = ofGlobal.getOf();
        HashMap<String, Vector> mapRapprochement = ofGlobal.getRapprochement();
        MvtStockFilleTheorique[] rapprochementGlobal = ofGlobal.getRapprochementGlobal();
        OfFilleCpl[] details = ofGlobal.getDetails();

        List<MvtStockFilleTheorique> rapprochement = new ArrayList();
        for (Vector v : mapRapprochement.values()) {
            for (Object obj : v) {
                if (obj instanceof MvtStockFilleTheorique) {
                    rapprochement.add((MvtStockFilleTheorique) obj);
                }
            }
        }

        MvtStockFilleTheorique.calculerPourcentage(rapprochementGlobal);
        List<MvtStockFilleTheorique> listerapproche_globales = new ArrayList<>();
        if (rapprochementGlobal != null) {
            Collections.addAll(listerapproche_globales, rapprochementGlobal);
        }
        
        List<OfFilleCpl> listeDetails = new ArrayList<>();
        if (details != null) {
            Collections.addAll(listeDetails, details);
        }
        System.out.println("details = "+details.length);
        param.put("rapprochement", rapprochement);
        param.put("listeDetails", listeDetails);
        param.put("listerapproche_globales", listerapproche_globales);
        setNomJasper("situation_globale");
        UtilitaireImpression.imprimer(request, response, getNomJasper(), param, dataSource, getReportPath());
    }

    private void offille_situation_globale(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
        String id = request.getParameter("id");

        OffilleEtatGlobal etatGlobal = new OffilleEtatGlobal(id);
        OfFilleCpl offille = etatGlobal.getOffille();
        String idMere = offille.getIdMere();

        Of[] of_mere = (Of[]) CGenUtil.rechercher(new Of(), null, null, null,
                " AND ID = '" + idMere + "'");
        if (of_mere.length > 0) {
            param.put("daty", of_mere[0].getDaty());
            param.put("id", of_mere[0].getId());
            param.put("idfille", id);
        }

        param.put("article","Carton de 18 CB(PF)");
        param.put("qte", offille.getQte());
        param.put("qteFabrique", offille.getQteFabrique());
        param.put("qteReste", offille.getQteReste());
        param.put("puRevient", offille.getPuRevient());
        param.put("pv", offille.getPv());
        param.put("montantRevient", Utilitaire.enleverExponentielleDouble(offille.getMontantRevient()));
        param.put("montantEntree", Utilitaire.enleverExponentielleDouble(offille.getMontantentree()));
        param.put("montantSortie", Utilitaire.enleverExponentielleDouble(offille.getMontantsortie()));
        param.put("tauxRevient", Utilitaire.enleverExponentielleDouble(offille.getTauxRevient()));

        HashMap<String, Vector> mapRapprochement = etatGlobal.getRapprochement();
        MvtStockFilleTheorique[] rapprochementGlobal = etatGlobal.getRapprochementGlobal();

        List<MvtStockFilleTheorique> rapprochement = new ArrayList();
        for (Vector v : mapRapprochement.values()) {
            for (Object obj : v) {
                if (obj instanceof MvtStockFilleTheorique) {
                    rapprochement.add((MvtStockFilleTheorique) obj);
                }
            }
        }

        MvtStockFilleTheorique.calculerPourcentage(rapprochementGlobal);
        List<MvtStockFilleTheorique> listerapproche_globales = new ArrayList<>();
        if (rapprochementGlobal != null) {
            Collections.addAll(listerapproche_globales, rapprochementGlobal);
        }

        param.put("rapprochement", rapprochement);
        param.put("listerapproche_globales", listerapproche_globales);
        setNomJasper("offille_situation_globale");
        UtilitaireImpression.imprimer(request, response, getNomJasper(), param, dataSource, getReportPath());
    }
    private void vente_liste(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
        String id = request.getParameter("id");
        String designation = request.getParameter("designation");
        String idClientLib = request.getParameter("idClientLib");
        String daty1 = request.getParameter("daty1");
        String daty2 = request.getParameter("daty2");
        String awhere="";
        VenteLib v = new VenteLib();
        v.setNomTable("VENTE_CPL");
        v.setId(id);
        v.setDesignation(designation);
        v.setIdClientLib(idClientLib);
        if ((daty1 != null && !daty1.trim().isEmpty()) || (daty2 != null && !daty2.trim().isEmpty())) {
            if (daty1 != null && !daty1.trim().isEmpty()) {
                awhere += " and daty >= to_date('" + daty1 + "', 'dd/mm/yyyy') ";
                param.put("datymin", daty1);
            }
            if (daty2 != null && !daty2.trim().isEmpty()) {
                awhere += " and daty <= to_date('" + daty2 + "', 'dd/mm/yyyy') ";
                param.put("datymax", daty2);
            }
        }
        VenteLib[] enc_mere = (VenteLib[]) CGenUtil.rechercher(v, null, null, null,awhere);
        //double montantHT = AdminGen.calculSommeDouble(enc_mere,"montantHT");
        dataSource.addAll(Arrays.asList(enc_mere));
        setNomJasper("facture_vente_mere");
        UtilitaireImpression.imprimer(request, response, getNomJasper(), param, dataSource, getReportPath());
    }
    private void vente_liste_mere_fille(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
        String id = request.getParameter("id");
        String designation = request.getParameter("designation");
        String idClientLib = request.getParameter("idClientLib");
        String daty1 = request.getParameter("daty1");
        String daty2 = request.getParameter("daty2");
        String awhere="";
        VenteLib v = new VenteLib();
        v.setNomTable("VENTE_CPL");
        v.setId(id);
        v.setDesignation(designation);
        v.setIdClientLib(idClientLib);
        if ((daty1 != null && !daty1.trim().isEmpty()) || (daty2 != null && !daty2.trim().isEmpty())) {
            if (daty1 != null && !daty1.trim().isEmpty()) {
                awhere += " and daty >= to_date('" + daty1 + "', 'dd/mm/yyyy') ";
                param.put("datymin", daty1);
            }
            if (daty2 != null && !daty2.trim().isEmpty()) {
                awhere += " and daty <= to_date('" + daty2 + "', 'dd/mm/yyyy') ";
                param.put("datymax", daty2);
            }
        }
        VenteLib[] enc_mere = (VenteLib[]) CGenUtil.rechercher(v, null, null, null, awhere);
        Map<String, List<VenteDetailsLib>> listedetailsMap = new HashMap<>();
        for (VenteLib venteMere : enc_mere) {
            VenteDetailsLib vf = new VenteDetailsLib();
            vf.setNomTable("VENTE_DETAILS_CPL");
            vf.setIdVente(venteMere.getId());
            VenteDetailsLib[] enc_fille = (VenteDetailsLib[]) CGenUtil.rechercher(vf, null, null, null, "");
            if (enc_fille != null) {
                listedetailsMap.put(venteMere.getId(), Arrays.asList(enc_fille));
            } else {
                listedetailsMap.put(venteMere.getId(), new ArrayList<>());
            }
        }
        param.put("listedetails", listedetailsMap);
        List<VenteLib> dataSourceMere = Arrays.asList(enc_mere);
        setNomJasper("facture_vente_mere_fille");
        UtilitaireImpression.imprimer(request, response, getNomJasper(), param, dataSourceMere, getReportPath());

    }

    private void proforma(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception{
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
        String id = request.getParameter("id");
        ProformaLib v = new ProformaLib();
        v.setNomTable("PROFORMA_CPL");
        ProformaLib[] enc_mere = (ProformaLib[]) CGenUtil.rechercher(v, null, null, null,
                " AND ID = '" + id + "'");
        param.put("tel", ConstanteVente.tel);
        param.put("fax", ConstanteVente.fax);
        param.put("tel1", ConstanteVente.tel1);
        param.put("tel2", ConstanteVente.tel2);
        param.put("email", ConstanteVente.email);
        param.put("bni_cl", ConstanteVente.bni_cl);
        param.put("boa", ConstanteVente.boa);
        param.put("bmoi", ConstanteVente.bmoi);
        param.put("bfv_sg", ConstanteVente.bfv_sg);
        param.put("cf", ConstanteVente.cf);
        param.put("stat", ConstanteVente.stat);
        param.put("nif", ConstanteVente.nif);
        param.put("datyJour", Utilitaire.dateDuJour());
        if (enc_mere.length > 0) {
             ClientLib cl = new ClientLib();
            cl.setNomTable("CLIENTLIB");
            ClientLib client = (ClientLib) cl.getById(enc_mere[0].getIdClient(),"CLIENTLIB" , null);
            if (enc_mere.length > 0) {
                param.put("idclient",client.getId());
                param.put("nomClient",client.getNom());
                param.put("adresseClient",client.getAdresse());
                param.put("lieuClient",client.getProvinceLib());
                param.put("telClient",client.getTelephone());
                param.put("statclient",client.getStat());
                param.put("nifclient",client.getNif());
                param.put("cp",client.getCarte());
                param.put("quit","");
                param.put("datyclient",client.getDatecarte());
            }
            param.put("numfact",enc_mere[0].getId());
            param.put("datyfact",enc_mere[0].getDaty());
            //param.put("totalcolis","");
            //param.put("totalpoids","");
            param.put("modepaiement","");
            param.put("reference","");
            param.put("livraison","");
            param.put("transport","");
        }

        ProformaDetailsLib vf = new ProformaDetailsLib();
        ProformaDetailsLib[] v_fille = (ProformaDetailsLib[]) CGenUtil.rechercher(vf, null, null, null,
                " and idProforma='" + id + "'");
        dataSource.addAll(Arrays.asList(v_fille));
        double montantHT = AdminGen.calculSommeDouble(v_fille,"montanttotal");
        double montantTVA = AdminGen.calculSommeDouble(v_fille,"montanttva");
        double montantTTC = AdminGen.calculSommeDouble(v_fille,"montantttc");
        //double frais = AdminGen.calculSommeDouble(v_fille,"frais");
        param.put("montantNetLettre",ChiffreLettre.convertRealToString(montantTTC));
        param.put("totalbrut",montantHT);
        //param.put("participation_transport","frais");
        param.put("totalHt",montantHT);
        param.put("total_taxes",montantTVA);
        param.put("montantttc",montantTTC);
        //param.put("escompte","");
        param.put("net_payer",montantTTC);
        param.put("net_payer_fmg",montantTTC*5);
        setNomJasper("proforma");
        UtilitaireImpression.imprimer(request, response, getNomJasper()+"-"+id, param, dataSource, getReportPath());
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ExportPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ExportPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
