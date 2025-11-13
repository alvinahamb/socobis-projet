package demande;
public class DemandeTransfertCpl extends DemandeTransfert {
    private String idMagasinDepartLib,idMagasinArriveLib,etatlib;
    public DemandeTransfertCpl() throws Exception{
        this.setNomTable("demandetransfertcpl");
    }

    public String getIdMagasinDepartLib() {
        return idMagasinDepartLib;
    }

    public void setIdMagasinDepartLib(String idMagasinDepartLib) {
        this.idMagasinDepartLib = idMagasinDepartLib;
    }

    public String getIdMagasinArriveLib() {
        return idMagasinArriveLib;
    }

    public void setIdMagasinArriveLib(String idMagasinArriveLib) {
        this.idMagasinArriveLib = idMagasinArriveLib;
    }

    public String getEtatlib() {
        return etatlib;
    }

    public void setEtatlib(String etatlib) {
        this.etatlib = etatlib;
    }
}