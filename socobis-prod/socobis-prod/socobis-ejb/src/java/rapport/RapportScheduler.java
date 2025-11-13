package rapport;

import utilitaire.Utilitaire;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.sql.Date;

@Singleton
@Startup
public class RapportScheduler {

    @Schedule(hour = "17", minute = "30", second = "0", persistent = false)
    public void envoyerCRQuotidien() {
        try {
            Date daty = Utilitaire.dateDuJourSql();

            System.out.println("Début d'envoi automatique de CR : " + daty);

            Rapport rapport = new Rapport();
            rapport.sendMails(daty);

            System.out.println("Envoi terminé pour : " + daty);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
