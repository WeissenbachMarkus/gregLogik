/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregorlogik;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author markus
 */
public class Lager {

    public String name;
    public ArrayList<AnzahlUndItem> bestand;

    public Lager(String name) {
        this.name = name;
        this.bestand = new ArrayList<>();
    }

    public synchronized void einlagern(int anzahl, Item item) {

        //System.out.println("einlagern: "+anzahl+" : "+item);
        for (AnzahlUndItem artikel : this.bestand) {
            if (artikel.item.getName() == item.getName()) {
                artikel.anzahl += anzahl;
                return;
            }
        }

        this.bestand.add(new AnzahlUndItem(item, anzahl));

    }

    public synchronized void auslagern(int anzahl, Item item) {
        // System.out.println("auslagern: "+anzahl+" : "+item);
        for (AnzahlUndItem anzahlUndItem : this.bestand) {
            if (anzahlUndItem.item.getName() == item.getName()) {
                anzahlUndItem.anzahl -= anzahl;

                if (anzahlUndItem.anzahl == 0) {
                    this.bestand.remove(anzahlUndItem);
                }
            }

        }
    }

    public long getItemCount(Item item) {
        try {
            return this.bestand.stream()
                    .filter(e -> e.item.getName() == item.getName())
                    .map(e -> e.anzahl)
                    .findAny()
                    .get();

        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\n"
                + "Items: " + this.bestand
                        .stream()
                        .map(e -> e.item.getName() + " : " + e.anzahl)
                        .collect(Collectors.joining(", "));
    }

    public ArrayList<Item> getItemliste() {
        return new ArrayList<Item>(
                this.bestand.stream()
                        .map(e -> e.item)
                        .collect(Collectors.toList())
        );
    }

    class AnzahlUndItem {

        Item item;
        int anzahl;

        public AnzahlUndItem(Item item, int anzahl) {
            this.item = item;
            this.anzahl = anzahl;
        }

    }

}
