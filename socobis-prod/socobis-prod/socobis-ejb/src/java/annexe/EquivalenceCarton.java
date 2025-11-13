package annexe;

import bean.ClassMAPTable;
import utilitaire.UtilDB;

import java.sql.Connection;

public class EquivalenceCarton extends ClassMAPTable {
    String id, idPetris, idCarton;
    double nbcarton;

    public EquivalenceCarton() {
        this.setNomTable("equivalencecarton");
    }

    public String getTuppleID() {
        return id;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("EQCRT", "getSeqequivalencecarton");
        this.setId(makePK(c));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPetris() {
        return idPetris;
    }

    public void setIdPetris(String idPetris) {
        this.idPetris = idPetris;
    }

    public String getIdCarton() {
        return idCarton;
    }

    public void setIdCarton(String idCarton) {
        this.idCarton = idCarton;
    }

    public double getNbcarton() {
        return nbcarton;
    }

    public void setNbcarton(double nbcarton) {
        this.nbcarton = nbcarton;
    }
}
