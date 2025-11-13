/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socobis.export;


import net.sf.jasperreports.engine.JRException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utilitaire.Utilitaire;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import vente.*;
import java.util.*;
import bean.*;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import mg.cnaps.compta.ComptaEtatGrandLivreGenerator;
import mg.cnaps.compta.ComptaCompte;
import mg.cnaps.compta.ComptaSousEcriture;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

@WebServlet(name = "ExportExcel", urlPatterns = {"/ExportExcel"})
public class ExportExcel extends HttpServlet {

  

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, Exception, IOException, JRException {
        String action = request.getParameter("action");
         if(action.equalsIgnoreCase("vente_liste")){
            vente_liste(request, response);
         }
        if(action.equalsIgnoreCase("grand_livre")){
            grand_livre(request, response);
        }
      
    }
  
    private void vente_liste(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, Exception {
        Connection c = null;
        Map param = new HashMap();
        List dataSource = new ArrayList();
    
        // Récupération des paramètres
        String id = request.getParameter("id");
        String designation = request.getParameter("designation");
        String idClientLib = request.getParameter("idClientLib");
        String daty1 = request.getParameter("daty1");
        String daty2 = request.getParameter("daty2");
        String awhere = "";
    
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

        String[] libEntete = {
            "ID", "DESIGNATION", "CLIENT", "DEVISE", "DATE",
            "MONTANT TTC", "MONTANT REVIENT", "MARGE BRUTE",
            "MONTANT PAYE", "MONTANT RESTE", "ETAT"
        };
    

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ventes");
    

        CellStyle headerStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        headerStyle.setFont(boldFont);
        headerStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);
    
  
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderTop(CellStyle.BORDER_THIN);
        dataStyle.setBorderBottom(CellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(CellStyle.BORDER_THIN);
        dataStyle.setBorderRight(CellStyle.BORDER_THIN);
    
 
        CellStyle totalStyle = workbook.createCellStyle();
        totalStyle.setFont(boldFont);
        totalStyle.setBorderTop(CellStyle.BORDER_THIN);
        totalStyle.setBorderBottom(CellStyle.BORDER_THIN);
        totalStyle.setBorderLeft(CellStyle.BORDER_THIN);
        totalStyle.setBorderRight(CellStyle.BORDER_THIN);
    
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < libEntete.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(libEntete[i]);
            cell.setCellStyle(headerStyle);
        }
    
     
        int rowNum = 1;
        for (VenteLib vente : enc_mere) {
            Row row = sheet.createRow(rowNum++);
    
            Cell c0 = row.createCell(0); c0.setCellValue(vente.getId()); c0.setCellStyle(dataStyle);
            Cell c1 = row.createCell(1); c1.setCellValue(vente.getDesignation()); c1.setCellStyle(dataStyle);
            Cell c2 = row.createCell(2); c2.setCellValue(vente.getIdClientLib()); c2.setCellStyle(dataStyle);
            Cell c3 = row.createCell(3); c3.setCellValue(vente.getIdDevise()); c3.setCellStyle(dataStyle);
            Cell c4 = row.createCell(4); c4.setCellValue(vente.getDaty() != null ? vente.getDaty().toString() : ""); c4.setCellStyle(dataStyle);
            Cell c5 = row.createCell(5); c5.setCellValue(vente.getMontantttc()); c5.setCellStyle(dataStyle);
            Cell c6 = row.createCell(6); c6.setCellValue(vente.getMontantRevient()); c6.setCellStyle(dataStyle);
            Cell c7 = row.createCell(7); c7.setCellValue(vente.getMargeBrute()); c7.setCellStyle(dataStyle);
            Cell c8 = row.createCell(8); c8.setCellValue(vente.getMontantpaye()); c8.setCellStyle(dataStyle);
            Cell c9 = row.createCell(9); c9.setCellValue(vente.getMontantreste()); c9.setCellStyle(dataStyle);
            Cell c10 = row.createCell(10); c10.setCellValue(vente.getEtatLib()); c10.setCellStyle(dataStyle);
        }
    

        double totalmontantttc = AdminGen.calculSommeDouble(enc_mere, "montantttc");
        double totalmontantRevient = AdminGen.calculSommeDouble(enc_mere, "montantRevient");
        double totalmargeBrute = AdminGen.calculSommeDouble(enc_mere, "margeBrute");
        double totalmontantpaye = AdminGen.calculSommeDouble(enc_mere, "montantpaye");
        double totalmontantreste = AdminGen.calculSommeDouble(enc_mere, "montantreste");
    
        // Ligne Total
        Row totalRow = sheet.createRow(rowNum++);
        Cell totalLabelCell = totalRow.createCell(4);
        totalLabelCell.setCellValue("Total");
        totalLabelCell.setCellStyle(totalStyle);
    
        Cell totalTTC = totalRow.createCell(5);
        totalTTC.setCellValue(totalmontantttc);
        totalTTC.setCellStyle(totalStyle);
    
        Cell totalRevient = totalRow.createCell(6);
        totalRevient.setCellValue(totalmontantRevient);
        totalRevient.setCellStyle(totalStyle);
    
        Cell totalMarge = totalRow.createCell(7);
        totalMarge.setCellValue(totalmargeBrute);
        totalMarge.setCellStyle(totalStyle);
    
        Cell totalPaye = totalRow.createCell(8);
        totalPaye.setCellValue(totalmontantpaye);
        totalPaye.setCellStyle(totalStyle);
    
        Cell totalReste = totalRow.createCell(9);
        totalReste.setCellValue(totalmontantreste);
        totalReste.setCellStyle(totalStyle);
    

        for (int i = 0; i < libEntete.length; i++) {
            sheet.autoSizeColumn(i);
        }
    

        String fileName = "liste_ventes.xlsx";
    

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.flush();
        out.close();
    }


    private void grand_livre(HttpServletRequest request, HttpServletResponse response)
            throws IOException, JRException, Exception {

        String date1 = request.getParameter("date1");
        String date2 = request.getParameter("date2");
        String plage1     = request.getParameter("plage1");
        String plage2     = request.getParameter("plage2");
        String typecompte = request.getParameter("typecompte");
        String exercice   = request.getParameter("exercice");
        String etat       = request.getParameter("etat");


        ComptaEtatGrandLivreGenerator grandLivre = new ComptaEtatGrandLivreGenerator();
        if (exercice != null && exercice.matches("\\d+")) {
            grandLivre.setExercice(Integer.parseInt(exercice));
        }
        grandLivre.setTypeCompte(typecompte);
        grandLivre.setDateDebut(date1);
        grandLivre.setDateFin(date2);
        grandLivre.setCompteDebut(plage1);
        grandLivre.setCompteFin(plage2);
        grandLivre.fillComptesWithMouvementAndReport(null);

        String[] libEntete = {
            "Compte", "Id Mvt", "Date", "Jrn/Folio",
            "Contrep.", "Libelle oper.", "Debit", "Credit",
            "Lettre", "Compte analytique/Générale", "Etat"
        };
        final int lastCol = libEntete.length - 1;


        SXSSFWorkbook workbook = new SXSSFWorkbook(100); 
        Sheet sheet = workbook.createSheet("Grand Livre");


        CellStyle headerStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        headerStyle.setFont(boldFont);
        headerStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderTop(CellStyle.BORDER_THIN);
        dataStyle.setBorderBottom(CellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(CellStyle.BORDER_THIN);
        dataStyle.setBorderRight(CellStyle.BORDER_THIN);


        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.cloneStyleFrom(dataStyle);
        Font boldDataFont = workbook.createFont();
        boldDataFont.setBold(true);
        boldStyle.setFont(boldDataFont);


        Row titreRow = sheet.createRow(0);
        Cell titreCell = titreRow.createCell(0);
        titreCell.setCellValue(
            "Grand livre du compte " + (grandLivre.getCompteDebut() == null ? "" : grandLivre.getCompteDebut())
            + " au " + (grandLivre.getCompteFin() == null ? "" : grandLivre.getCompteFin())
            + " | Période: " + (grandLivre.getDateDebut() == null ? "" : grandLivre.getDateDebut())
            + " → " + (grandLivre.getDateFin() == null ? "" : grandLivre.getDateFin())
        );
        titreCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, lastCol));


        Row headerRow = sheet.createRow(1);
        for (int i = 0; i < libEntete.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(libEntete[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 2;

        for (Map.Entry<String, ComptaCompte> entry : grandLivre.getComptes().entrySet()) {
            ComptaCompte cc = entry.getValue();
            java.util.List<ComptaSousEcriture> mouvements = cc.getMouvements();

            if (cc.getReportDebit() != 0 || cc.getReportCredit() != 0
                || (mouvements != null && !mouvements.isEmpty())) {

                // Bandeau COMPTE :
                Row compteRow = sheet.createRow(rowNum++);
                Cell compteCell = compteRow.createCell(0);
                compteCell.setCellValue("COMPTE : " + (cc.getCompte() == null ? "" : cc.getCompte()));
                compteCell.setCellStyle(headerStyle);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(rowNum - 1, rowNum - 1, 0, lastCol));


                Row cumulAvantRow = sheet.createRow(rowNum++);

                Cell cumulAvantLabel = cumulAvantRow.createCell(5);
                cumulAvantLabel.setCellValue("ANCIEN CUMUL...");
                cumulAvantLabel.setCellStyle(boldStyle);

                Cell cumulAvantDebit = cumulAvantRow.createCell(6);
                cumulAvantDebit.setCellValue(Utilitaire.formaterAr(cc.getReportDebit()));
                cumulAvantDebit.setCellStyle(boldStyle);
                Cell cumulAvantCredit = cumulAvantRow.createCell(7);
                cumulAvantCredit.setCellValue(Utilitaire.formaterAr(cc.getReportCredit()));
                cumulAvantCredit.setCellStyle(boldStyle);

                for (int c = 0; c <= 4; c++) {
                    Cell empty = cumulAvantRow.createCell(c);
                    empty.setCellStyle(boldStyle);
                }

                for (int c = 8; c <= lastCol; c++) {
                    Cell empty = cumulAvantRow.createCell(c);
                    empty.setCellStyle(boldStyle);
                }


                if (mouvements != null) {
                    for (ComptaSousEcriture mvt : mouvements) {
                        Row row = sheet.createRow(rowNum++);

                        Cell c0 = row.createCell(0); c0.setCellValue(nullSafe(cc.getCompte()));                    c0.setCellStyle(dataStyle);
                        Cell c1 = row.createCell(1); c1.setCellValue(nullSafe(mvt.getIdMere()));                   c1.setCellStyle(dataStyle);
                        Cell c2 = row.createCell(2); c2.setCellValue(Utilitaire.datetostring(mvt.getDaty()));      c2.setCellStyle(dataStyle);
                        Cell c3 = row.createCell(3); c3.setCellValue(nullSafe(mvt.getJournal()) + "/" + nullSafe(mvt.getFolio())); c3.setCellStyle(dataStyle);
                        Cell c4 = row.createCell(4); c4.setCellValue("");                                          c4.setCellStyle(dataStyle); 
                        Cell c5 = row.createCell(5); c5.setCellValue(nullSafe(mvt.getRemarque()));                c5.setCellStyle(dataStyle);
                        Cell c6 = row.createCell(6); c6.setCellValue(Utilitaire.formaterAr(mvt.getDebit()));                             c6.setCellStyle(dataStyle);
                        Cell c7 = row.createCell(7); c7.setCellValue(Utilitaire.formaterAr(mvt.getCredit()));                            c7.setCellStyle(dataStyle);
                        Cell c8 = row.createCell(8); c8.setCellValue(nullSafe(mvt.getLettrage()));                c8.setCellStyle(dataStyle);
                        Cell c9 = row.createCell(9); c9.setCellValue(nullSafe(mvt.getReference_engagement()));    c9.setCellStyle(dataStyle);
                        Cell c10= row.createCell(10);c10.setCellValue(mvt.getEtat());                             c10.setCellStyle(dataStyle);
                    }
                }

                Row totalRow = sheet.createRow(rowNum++);
                for (int c = 0; c <= 4; c++) {
                    Cell empty = totalRow.createCell(c);
                    empty.setCellStyle(boldStyle);
                }
                Cell totalLabel = totalRow.createCell(5);
                totalLabel.setCellValue("TOTAL");
                totalLabel.setCellStyle(boldStyle);

                Cell totalDebit = totalRow.createCell(6);
                totalDebit.setCellValue(Utilitaire.formaterAr(cc.getTotalDebit()));
                totalDebit.setCellStyle(boldStyle);

                Cell totalCredit = totalRow.createCell(7);
                totalCredit.setCellValue(Utilitaire.formaterAr(cc.getTotalCredit()));
                totalCredit.setCellStyle(boldStyle);

                for (int c = 8; c <= lastCol; c++) {
                    Cell empty = totalRow.createCell(c);
                    empty.setCellStyle(boldStyle);
                }


                Row cumulApresRow = sheet.createRow(rowNum++);
                for (int c = 0; c <= 4; c++) {
                    Cell empty = cumulApresRow.createCell(c);
                    empty.setCellStyle(boldStyle);
                }
                Cell cumulLabel = cumulApresRow.createCell(5);
                cumulLabel.setCellValue("CUMUL");
                cumulLabel.setCellStyle(boldStyle);

                Cell cumulDebit = cumulApresRow.createCell(6);
                cumulDebit.setCellValue(Utilitaire.formaterAr(cc.getReportDebit()));
                cumulDebit.setCellStyle(boldStyle);

                Cell cumulCredit = cumulApresRow.createCell(7);
                cumulCredit.setCellValue(Utilitaire.formaterAr(cc.getReportCredit()));
                cumulCredit.setCellStyle(boldStyle);

                for (int c = 8; c <= lastCol; c++) {
                    Cell empty = cumulApresRow.createCell(c);
                    empty.setCellStyle(boldStyle);
                }


                Row soldeRow = sheet.createRow(rowNum++);
                for (int c = 0; c <= 4; c++) {
                    Cell empty = soldeRow.createCell(c);
                    empty.setCellStyle(boldStyle);
                }
                Cell soldeLabel = soldeRow.createCell(5);
                soldeLabel.setCellValue("SOLDE");
                soldeLabel.setCellStyle(boldStyle);

                Cell soldeDebit = soldeRow.createCell(6);
                soldeDebit.setCellValue(Utilitaire.formaterAr(cc.getSoldeDebit()));
                soldeDebit.setCellStyle(boldStyle);

                Cell soldeCredit = soldeRow.createCell(7);
                soldeCredit.setCellValue(Utilitaire.formaterAr(cc.getSoldeCredit()));
                soldeCredit.setCellStyle(boldStyle);

                for (int c = 8; c <= lastCol; c++) {
                    Cell empty = soldeRow.createCell(c);
                    empty.setCellStyle(boldStyle);
                }
            }
        }

        Row totalGeneralRow = sheet.createRow(rowNum++);
        for (int c = 0; c <= 4; c++) {
            Cell empty = totalGeneralRow.createCell(c);
            empty.setCellStyle(boldStyle);
        }
        Cell totalGeneralLabel = totalGeneralRow.createCell(5);
        totalGeneralLabel.setCellValue("TOTAL");
        totalGeneralLabel.setCellStyle(boldStyle);

        Cell totalGeneralCredit = totalGeneralRow.createCell(6);
        totalGeneralCredit.setCellValue(Utilitaire.formaterAr(grandLivre.getTotalCredit()));
        totalGeneralCredit.setCellStyle(boldStyle);

        Cell totalGeneralDebit = totalGeneralRow.createCell(7);
        totalGeneralDebit.setCellValue(Utilitaire.formaterAr(grandLivre.getTotalDebit()));
        totalGeneralDebit.setCellStyle(boldStyle);

        for (int c = 8; c <= lastCol; c++) {
            Cell empty = totalGeneralRow.createCell(c);
            empty.setCellStyle(boldStyle);
        }



        int[] widths = {5000, 4200, 4200, 5200, 3200, 12000, 4200, 4200, 4200, 5200, 3200};
        for (int i = 0; i < libEntete.length; i++) {
            sheet.setColumnWidth(i, widths[i]);
        }


        String fileName = "grand_livre.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

        response.setHeader("Content-Disposition",
            "attachment; filename=\"" + fileName + "\"; filename*=UTF-8''" + encodedFileName);
        
        try (ServletOutputStream out = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out)) {
            workbook.write(bos);
            bos.flush();
        }
        workbook.dispose();
    }


    private String nullSafe(Object o) {
        return (o == null) ? "" : String.valueOf(o);
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
            Logger.getLogger(ExportExcel.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ExportExcel.class.getName()).log(Level.SEVERE, null, ex);
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
    }
   
   
     


    
   
}
