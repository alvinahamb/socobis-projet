package utils;

import annexe.ProduitLib;
import chatbot.ClassIA;
import fabrication.FabricationFilleCpl2;
import fabrication.OfFilleCpl;
import faturefournisseur.FactureFournisseurDetailsCpl;
import prevision.PrevisionComplet;
import stock.MvtStockFilleTheorique;
import stock.RapprochementOF;
import vente.BonDeCommande;
import vente.BonDeCommandeFIlleCpl;
import vente.VenteDetailsLib;

public class ConstanteAsync {
    public static Class<? extends ClassIA>[] iaClasses = new Class[]{VenteDetailsLib.class, FactureFournisseurDetailsCpl.class, FabricationFilleCpl2.class, PrevisionComplet.class, ProduitLib.class, OfFilleCpl.class, RapprochementOF.class};
    public static final String API_KEY = "AIzaSyBp79rk0qe1FEPYeKPx6TuORYABQrV2c4I";
    public static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;
    public static final String ADK_URL = "http://localhost:8000";
    public static final String AI_CONTEXT = "C'est une application d'une entreprise de confiserie et biscuiterie, donc tout ce qui est achat, depenses, ventes, bon de commande, fabrication, ordre de fabrication (OF), prevision, etc etc";
    public static final String AI_DEFINITIONS = "";

    public static final String[] mailRapport={"noreplyasync@gmail.com","lhcp apld qdgt tjqp"};

    public static final String roleChefProduction = "cheffab";
    public static final String roleDg = "dg";

    public static String[] getMailRapport(){
        return mailRapport;
    }

    public static final String[] couleurs = {

            "#FF5733", "#33FF57", "#3357FF", "#F1C40F", "#8E44AD", "#1ABC9C", "#E74C3C", "#2ECC71", "#3498DB", "#9B59B6",

            "#34495E", "#16A085", "#27AE60", "#2980B9", "#D35400", "#C0392B", "#BDC3C7", "#7F8C8D", "#FFB6C1", "#00CED1",

            "#FFD700", "#7FFF00", "#DC143C", "#4B0082", "#FF8C00", "#20B2AA", "#FF69B4", "#FF6347", "#40E0D0", "#6A5ACD",

            "#00FA9A", "#CD5C5C", "#9370DB", "#48D1CC", "#F08080", "#E9967A", "#8FBC8F", "#4169E1", "#800000", "#191970",

            "#FFA07A", "#FF4500", "#ADFF2F", "#00BFFF", "#DAA520", "#B22222", "#00FF7F", "#D8BFD8", "#008080", "#BDB76B",

            "#FF00FF", "#6B8E23", "#FF1493", "#8B0000", "#BC8F8F", "#00FFFF", "#696969", "#8B008B", "#FF7F50", "#A0522D",

            "#DB7093", "#556B2F", "#9932CC", "#B0E0E6", "#DDA0DD", "#A52A2A", "#2F4F4F", "#708090", "#FA8072", "#F5DEB3",

            "#DC143C", "#E0FFFF", "#F4A460", "#483D8B", "#6495ED", "#FFDEAD", "#3CB371", "#4682B4", "#C71585", "#F0E68C",

            "#9ACD32", "#D2691E", "#7B68EE", "#ADD8E6", "#BA55D3"

    };
    public static final String CAISSE_DEFAUT = "CAIS001";
}
