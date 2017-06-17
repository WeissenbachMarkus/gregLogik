/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregorlogik;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author markus
 */
public class ThreadIItem implements Runnable {

    private final Item item;
    private final Lagerverwaltung lv;
    private final ThreadItemWatcher threadWatcher;


    public ThreadIItem(Item item, Lagerverwaltung lv, ThreadItemWatcher wt) {
        this.item = item;
        this.lv = lv;
        this.threadWatcher = wt;
    }

    @Override
    public void run() {

        ArrayList<LagerItemAnzahlLager> auftraege = new ArrayList<>();
        boolean ausgeglichen = false;

        while (!ausgeglichen) {
            Lager meisteItems = lv.getLagerMitDenMeistenGegenstaendenDieserArt(item);
            Lager wenigstenItems = lv.getLagerMitDenWenigstenGegenstaendenDieserArt(item);

            long anzahlWenigste = wenigstenItems.getItemCount(item);
            long anzahlMeiste = meisteItems.getItemCount(item);

            if (anzahlWenigste + 1 == anzahlMeiste
                    || anzahlWenigste == anzahlMeiste
                    || anzahlMeiste <= 1) {

                this.threadWatcher.callbackSetAuftraege(auftraege);
                ausgeglichen=true;
                return;

            } else {

                System.out.println("HERE");

                meisteItems.auslagern(1, item);
                wenigstenItems.einlagern(1, item);

                if (lv.vorhanden(auftraege, meisteItems, item, wenigstenItems)) {
                    lv.getEinzelnenAuftrag(auftraege, meisteItems, item, wenigstenItems).inkrement();
                } else {
                    auftraege.add(new LagerItemAnzahlLager(meisteItems, item, 1, wenigstenItems));
                }

            }
        }
    }

}
