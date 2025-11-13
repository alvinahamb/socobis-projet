/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paie.edition;

/**
 *
 * @author Sanda
 */
public class PaieEditionmoisanneeLib extends PaieEditionmoisannee{
    private String mois_string;

    public PaieEditionmoisanneeLib() throws Exception{
        super.setNomTable("PAIE_EDITIONMOISANNEE_LIB_2");
    }
    public String getMois_string() {
        return mois_string;
    }

    public void setMois_string(String mois_string) {
        this.mois_string = mois_string;
    }
    
}
