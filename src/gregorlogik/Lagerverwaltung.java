/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregorlogik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author markus
 */
public class Lagerverwaltung
{

    ArrayList<Lager> lagerListe;
    ArrayList<Item> itemList;

    public Lagerverwaltung()
    {
        lagerListe = new ArrayList<>();
        this.itemList = new ArrayList<>();
    }

    public void lagerHinzufuegen(String name)
    {
        this.lagerListe.add(new Lager(name));
    }


    public ArrayList<LagerItemAnzahlLager> gleicheItemBestandAusAlt()
    {
        ArrayList<LagerItemAnzahlLager> auftraege = new ArrayList<>();

        while (itemList.size() != 0)
        {
            System.out.println("Rechnet");

            for (Iterator<Item> iterator = this.itemList.iterator(); iterator.hasNext();)
            {
                Item item = iterator.next();

                Lager meisteItems = getLagerMitDenMeistenGegenstaendenDieserArt(item);
                Lager wenigstenItems = getLagerMitDenWenigstenGegenstaendenDieserArt(item);

                long anzahlWenigste = wenigstenItems.getItemCount(item);
                long anzahlMeiste = meisteItems.getItemCount(item);

                /*  System.out.println("meiste: " + meisteItems.name + " item: " + item + " anzahl: " + anzahlMeiste);
                 System.out.println("wenigsten: " + wenigstenItems.name + " item: " + item + " anzahl: " + anzahlWenigste);*/
                if (anzahlWenigste + 1 == anzahlMeiste
                        || anzahlWenigste == anzahlMeiste
                        || anzahlMeiste <= 1)
                {

                    iterator.remove();

                } else
                {
                    meisteItems.auslagern(1, item);
                    wenigstenItems.einlagern(1, item);

                    if (vorhanden(auftraege, meisteItems, item, wenigstenItems))
                    {
                        getEinzelnenAuftrag(auftraege, meisteItems, item, wenigstenItems).inkrement();
                    } else
                    {
                        auftraege.add(new LagerItemAnzahlLager(meisteItems, item, 1, wenigstenItems));
                    }

                }

            }

        }

        return auftraege;

    }
    
    public ArrayList<LagerItemAnzahlLager> gleicheItemBestandAus()
    { 
        return  new ThreadItemWatcher(itemList,this).start();

    }

   
    public Lager getLagerMitDenMeistenGegenstaendenDieserArt(Item item)
    {
        return this.lagerListe
                .stream()
                .reduce((Lager e, Lager e2) -> e.getItemCount(item) < e2.getItemCount(item) ? e2 : e)
                .get();
    }

    public Lager getLagerMitDenWenigstenGegenstaendenDieserArt(Item item)
    {
        return this.lagerListe
                .stream()
                .reduce((Lager e, Lager e2) -> e.getItemCount(item) > e2.getItemCount(item) ? e2 : e)
                .get();

    }

 
    public void ausgabe()
    {
        for (Lager lager : this.lagerListe)
        {
            System.out.println(lager);

        }
    }

    public boolean vorhanden(ArrayList<LagerItemAnzahlLager> liste, Lager von, Item item, Lager nach)
    {
        return liste
                .stream()
                .anyMatch(e -> e.item == item && e.von == von && e.nach == nach);
    }

    public LagerItemAnzahlLager getEinzelnenAuftrag(ArrayList<LagerItemAnzahlLager> einzelneAuftraege, Lager von, Item item, Lager nach)
    {

        return einzelneAuftraege
                .stream()
                .filter(e -> e.item == item && e.von == von && e.nach == nach)
                .findFirst()
                .get();
    }

}
