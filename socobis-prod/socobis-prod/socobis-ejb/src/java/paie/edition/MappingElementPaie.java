package paie.edition;

import java.util.List;

import paie.conge.CongeDroitCPL;
import paie.pointage.PointageHeureSup;
import utils.ConstantePaie;

public class MappingElementPaie {

    private double salaireDeBase;
    private double salaireDuMoisArrondi;
    private double irsa;
    private double cnaps;
    private double ostie;
    private double prime;
    private double totalRetenues;
    private double totalGains;
    private double indemnite;
    private double netAPayerArrondi;
    private double allocation;
    private double heureReels;
    private double congePaye;
    private double totalHeureSup;
    private double congeDroit;
    private double avanceExceptionnelle;
    private double avanceSalaire;
    private double avanceTotale;
    private double avanceGain;
    private double valHeureSup;
    private double abattement;
    private double totalHS;
    private double hsNuit;
    private double hsDim;
    private double hsFerie;
    public MappingElementPaie()
    { }

    public static MappingElementPaie getValeurElementDePaie(List<PaieEditionEltpaie> listePaieEditionElementPaie) {
        try {
            MappingElementPaie mapping = new MappingElementPaie();
            mapping.setHeureReels(listePaieEditionElementPaie.get(0).getHeureNormal());

            if (!listePaieEditionElementPaie.isEmpty() && listePaieEditionElementPaie.get(0).getIdpersonnel() != null) {
                String idPersonnel = listePaieEditionElementPaie.get(0).getIdpersonnel();
                PointageHeureSup pointageHeureSup = PointageHeureSup.getByIdPersonnel(idPersonnel);
                mapping.setTotalHeureSup(pointageHeureSup.getTotal_heure_sup());

                CongeDroitCPL congeDroitCPL = CongeDroitCPL.getCongeDroitPersonnel(idPersonnel);
                mapping.setCongeDroit(congeDroitCPL.getConge());
            }

            for (PaieEditionEltpaie element : listePaieEditionElementPaie) {
                if (element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idSalaireBasePaie)) {
                    mapping.setSalaireDeBase(element.getDroits());
                } else if (element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idIrsaPaie)) {
                    mapping.setIrsa(element.getRetenues());
                } else if (element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idCnapsPaie)) {
                    mapping.setCnaps(element.getRetenues());
                } else if (element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idIndemnitePaie)) {
                    mapping.setIndemnite(element.getDroits());
                } else if (element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idAllocationPaie)) {
                    mapping.setAllocation(element.getDroits());
                } else if (element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idOstiePaie)) {
                    mapping.setOstie(element.getRetenues());
                } else if (element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idPrimePaie)) {
                    mapping.setPrime(element.getDroits());
                } else if (element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idNetAPayerArrondis)) {
                    mapping.setNetAPayerArrondi(element.getDroits());
                } else if (element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idTotalGainsPaie)) {
                    mapping.setTotalGains(element.getDroits());
                } else if (element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idTotalRetenuesPaie)) {
                    mapping.setTotalRetenues(element.getRetenues());
                }

                // conge
                else if (element.getIdelementpaie() != null && element.getIdelementpaie().equals("PR000061")) {
                    mapping.setCongePaye(element.getDroits());
                }

                else if (element.getIdelementpaie() != null && element.getIdelementpaie().equals("PR0000115")) {
                    mapping.setAvanceGain(element.getDroits());
                }

//                avance
                else if(element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idAvanceExceptionnelle)) {
                    mapping.setAvanceExceptionnelle(element.getRetenues());
                }
                else if(element.getIdelementpaie() != null && element.getIdelementpaie().equals(ConstantePaie.idAvanceSurSalaire)) {
                    mapping.setAvanceSalaire(element.getRetenues());
                }
                else if(element.getIdelementpaie() != null && element.getIdelementpaie().equals("PR0000229")) {
                    mapping.setValHeureSup(element.getDroits());
                }
                else if(element.getIdelementpaie() != null && element.getIdelementpaie().equals("PR000181")) {
                    mapping.setAbattement(element.getDroits());
                }

                else if(element.getIdelementpaie() != null && element.getIdelementpaie().equals("PR0000221")) {
                    mapping.setHsNuit(element.getDroits());
                }

                else if(element.getIdelementpaie() != null && element.getIdelementpaie().equals("PR0000222")) {
                    System.out.println("ferie +++++ " + element.getDroits());
                    mapping.setHsFerie(element.getDroits());
                }
                else if(element.getIdelementpaie() != null && element.getIdelementpaie().equals("PR0000223")) {
                    mapping.setHsDim(element.getDroits());
                }


                mapping.setTotalHS(mapping.getHsNuit() + mapping.getHsFerie() + mapping.getHsDim() + mapping.getValHeureSup());
                mapping.setAvanceTotale(mapping.getAvanceExceptionnelle() + mapping.getAvanceSalaire());
            }
            return mapping;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getCategorieQualif(String fonction) {
        if (fonction.equals("Chauffeur") || fonction.equals("CHAUFFEUR")) {
            return "3A-GP2";
        } else if (fonction.equals("TECHNICIEN DE SURFACE") || fonction.equals("Technicien de Surface")) {
            return "1A-GP2";
        } else if (
                fonction.equals("Chargée Admin & Financier") ||
                        fonction.equals("CHARGÉE ADMIN & FINANCIER") ||
                        fonction.equals("CHARGER ADMIN & FINANCIER") ||
                        fonction.equals("Manager Qualité") ||
                        fonction.equals("MANAGER QUALITÉ") ||
                        fonction.equals("MANAGER QUALITE") ||
                        fonction.equals("Manager Plateau Junior") ||
                        fonction.equals("MANAGER PLATEAU JUNIOR") ||
                        fonction.equals("Superviseur") ||
                        fonction.equals("SUPERVISEUR")
        ) {
            return "HC";
        } else if (
                fonction.equals("Agent Call") ||
                        fonction.equals("Agent BPO") ||
                        fonction.equals("AGENT BPO") ||
                        fonction.equals("Télévendeur") ||
                        fonction.equals("TÉLÉVENDEUR") ||
                        fonction.equals("TELEVENDEUR")
        ) {
            return "4B-GP3";
        } else if (fonction.equals("Assistant Polyvalente") || fonction.equals("ASSISTANT POLYVALENTE") || fonction.equalsIgnoreCase("Agent CALL")) {
            return "4A-GP3";
        } else {
            return "HC";
        }
    }


    public double getCongeDroit() {
        return congeDroit;
    }

    public void setCongeDroit(double congeDroit) {
        this.congeDroit = congeDroit;
    }

    public double getTotalHeureSup() {
        return totalHeureSup;
    }

    public void setTotalHeureSup(double totalHeureSup) {
        this.totalHeureSup = totalHeureSup;
    }

    public double getSalaireDuMoisArrondi() {
        return salaireDuMoisArrondi;
    }

    public void setSalaireDuMoisArrondi(double salaireDuMoisArrondi) {
        this.salaireDuMoisArrondi = salaireDuMoisArrondi;
    }

    public double getCongePaye() {
        return congePaye;
    }

    public void setCongePaye(double congePaye) {
        this.congePaye = congePaye;
    }

    public double getOstie() {
        return ostie;
    }

    public void setOstie(double ostie) {
        this.ostie = ostie;
    }

    public double getSalaireDeBase() {
        return salaireDeBase;
    }

    public void setSalaireDeBase(double salaireDeBase) {
        this.salaireDeBase = salaireDeBase;
    }

    public double getIrsa() {
        return irsa;
    }

    public void setIrsa(double irsa) {
        this.irsa = irsa;
    }

    public double getCnaps() {
        return cnaps;
    }

    public void setCnaps(double cnaps) {
        this.cnaps = cnaps;
    }

    public double getPrime() {
        return prime;
    }

    public void setPrime(double prime) {
        this.prime = prime;
    }

    public double getTotalRetenues() {
        return totalRetenues;
    }

    public void setTotalRetenues(double totalRetenues) {
        this.totalRetenues = totalRetenues;
    }

    public double getTotalGains() {
        return totalGains;
    }

    public void setTotalGains(double totalGains) {
        this.totalGains = totalGains;
    }

    public double getSalaierDuMoisArrondi() {
        return salaireDuMoisArrondi;
    }

    public void setSalaierDuMoisArrondi(double salaireDuMoisArrondi) {
        this.salaireDuMoisArrondi = salaireDuMoisArrondi;
    }

    public double getIndemnite() {
        return indemnite;
    }

    public void setIndemnite(double indemnite) {
        this.indemnite = indemnite;
    }

    public double getNetAPayerArrondi() {
        return netAPayerArrondi;
    }

    public void setNetAPayerArrondi(double netAPayerArrondi) {
        this.netAPayerArrondi = netAPayerArrondi;
    }

    public double getAllocation() {
        return allocation;
    }

    public void setAllocation(double allocation) {
        this.allocation = allocation;
    }

    public void setHeureReels(double heureReels) {
        this.heureReels = heureReels;
    }

    public double getHeureReels() {
        return this.heureReels;
    }

    @Override
    public String toString() {
        return "MappingElementPaie {" +
            "salaireDeBase=" + salaireDeBase +
            ", salaireDuMoisArrondi=" + salaireDuMoisArrondi +
            ", irsa=" + irsa +
            ", cnaps=" + cnaps +
            ", ostie=" + ostie +
            ", prime=" + prime +
            ", totalRetenues=" + totalRetenues +
            ", totalGains=" + totalGains +
            ", indemnite=" + indemnite +
            ", netAPayerArrondi=" + netAPayerArrondi +
            ", allocation=" + allocation +
            ", heureReels=" + heureReels +
            '}';
    }

    public double getAvanceExceptionnelle() {
        return avanceExceptionnelle;
    }

    public void setAvanceExceptionnelle(double avanceExceptionnelle) {
        this.avanceExceptionnelle = avanceExceptionnelle;
    }

    public double getAvanceSalaire() {
        return avanceSalaire;
    }

    public void setAvanceSalaire(double avanceSalaire) {
        this.avanceSalaire = avanceSalaire;
    }

    public double getAvanceTotale() {
        return avanceTotale;
    }

    public void setAvanceTotale(double avanceTotale) {
        this.avanceTotale = avanceTotale;
    }

    public double getAvanceGain() {
        return avanceGain;
    }

    public void setAvanceGain(double avanceGain) {
        this.avanceGain = avanceGain;
    }

    public double getValHeureSup() {
        return valHeureSup;
    }

    public void setValHeureSup(double valHeureSup) {
        this.valHeureSup = valHeureSup;
    }

    public double getAbattement() {
        return abattement;
    }

    public void setAbattement(double abattement) {
        this.abattement = abattement;
    }

    public double getTotalHS() {
        return totalHS;
    }

    public void setTotalHS(double totalHS) {
        this.totalHS = totalHS;
    }

    public double getHsNuit() {
        return hsNuit;
    }

    public void setHsNuit(double hsNuit) {
        this.hsNuit = hsNuit;
    }

    public double getHsDim() {
        return hsDim;
    }

    public void setHsDim(double hsDim) {
        this.hsDim = hsDim;
    }

    public double getHsFerie() {
        return hsFerie;
    }

    public void setHsFerie(double hsFerie) {
        this.hsFerie = hsFerie;
    }
}