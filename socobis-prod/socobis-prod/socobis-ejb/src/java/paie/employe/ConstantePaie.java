/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paie.employe;
import bean.TypeObjet;
/**
 *
 * @author rakotondralambokoto
 */
public class ConstantePaie {
    public static final String cnaps = "CONF0001";
    public static final String ostie = "CONF0002";
    public static final String desce_irsa_bareme = "IRSA";
    public static final String irsa_conf = "CONF0008";
    public static final String abbatement_enfant = "CONF0009";
    
    public static final String id_sal_base = "PR000041";
    public static final String id_cnaps = "PR000101";
    public static final String id_irsa = "PR000141";
    public static final String id_net_a_payer = "PR000210A";
    public static final String id_net_a_payer_virtuel = "PRUNI";
    public static final String id_abattement = "PR000181";
    public static final String id_irsa_nature = "PR000201";
    public static final double plafondAvance=1;
    
    public static final double heuremois=173.33;
    
    public static final int plafond_avcance = 30;

    public static TypeObjet[] getMoisTous(){
        TypeObjet[] mois = new TypeObjet[12];
        mois[0] = new TypeObjet("1","Janvier","");
        mois[1] = new TypeObjet("2","Fevrier","");
        mois[2] = new TypeObjet("3","Mars","");
        mois[3] = new TypeObjet("4","Avril","");
        mois[4] = new TypeObjet("5","Mai","");
        mois[5] = new TypeObjet("6","Juin","");
        mois[6] = new TypeObjet("7","Juillet","");
        mois[7] = new TypeObjet("8","Aout","");
        mois[8] = new TypeObjet("9","Septembre","");
        mois[9] = new TypeObjet("10","Octobre","");
        mois[10] = new TypeObjet("11","Novembre","");
        mois[11] = new TypeObjet("12","Decembre","");
        return mois;
    }
    
}
