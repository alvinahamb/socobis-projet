package fabrication;

public class HeureSupFabricationCPL extends  HeureSupFabrication{
    String etatlib, idPersonneLib;

    public String getEtatlib() {
        return etatlib;
    }

    public void setEtatlib(String etatlib) {
        this.etatlib = etatlib;
    }

    public HeureSupFabricationCPL(){
        this.setNomTable("heureSupFabrication_cpl");
    }

    public String getIdPersonneLib() {
        return idPersonneLib;
    }

    public void setIdPersonneLib(String idPersonneLib) {
        this.idPersonneLib = idPersonneLib;
    }
}
