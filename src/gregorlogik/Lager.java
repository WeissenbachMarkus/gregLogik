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
public class Lager
{

    //int boxenVorhanden, belegt;
    String name;
    ArrayList<AnzahlUndItem> bestand;

    /*
     mit Lagerkapaziät
     */
    /*
     public Lager(int boxenVorhanden, String name)
     {
     this.boxenVorhanden = boxenVorhanden;
     this.belegt = 0;
     this.name = name;
     this.items = new ArrayList<>();
     }
     */
    public Lager(String name)
    {
        this.name = name;
        this.bestand = new ArrayList<>();
    }

    public void einlagern(int anzahl, Item item)
    {
        
         //System.out.println("einlagern: "+anzahl+" : "+item);

        for (AnzahlUndItem artikel : this.bestand)
        {
            if (artikel.item.getName() == item.getName())
            {
                artikel.anzahl += anzahl;
                return;
            }
        }

        this.bestand.add(new AnzahlUndItem(item, anzahl));

    }

    /*  public boolean einlagern(int anzahl, Item item)
     {
     if (getAnzahlFreiePlaetze() >= anzahl)
     {
     this.belegt += anzahl;
     this.items.add(new AnzahlUndItem(item, anzahl));
     return true;

     } else
     {
     return false;
     }
     }*/
    public void auslagern(int anzahl, Item item)
    {
       // System.out.println("auslagern: "+anzahl+" : "+item);
        for (AnzahlUndItem anzahlUndItem : this.bestand)
        {
            if (anzahlUndItem.item.getName() == item.getName())
            {
                anzahlUndItem.anzahl -= anzahl;

                if (anzahlUndItem.anzahl == 0)
                {
                    this.bestand.remove(anzahlUndItem);
                }
            }

        }
    }

    /*public boolean auslagern(int anzahl, Item item)
     {
     for (AnzahlUndItem anzahlUndItem : this.items)
     {
     if (anzahlUndItem.item.getName() == item.getName())
     {
     anzahlUndItem.anzahl -= anzahl;
     this.belegt -= anzahl;
     if (anzahlUndItem.anzahl == 0)
     {
     this.items.remove(anzahlUndItem);
     }
     return true;
     }

     }

     return false;

     }*/

    /* public int getAnzahlFreiePlaetze()
     {
     return this.boxenVorhanden - this.belegt;
     }*/
    public long getItemCount(Item item)
    {
        try
        {
            return this.bestand.stream()
                    .filter(e -> e.item.getName() == item.getName())
                    .map(e -> e.anzahl)
                    .findAny()
                    .get();

        } catch (Exception e)
        {
            return 0;
        }

    }

    @Override
    public String toString()
    {
        return "Name: " + this.name + "\n"
                + "Items: " + this.bestand
                .stream()
                .map(e -> e.item.getName() + " : " + e.anzahl)
                .collect(Collectors.joining(", "));
    }

    public ArrayList<Item> getItemliste()
    {
        return new ArrayList<Item>(
                this.bestand.stream()
                .map(e -> e.item)
                .collect(Collectors.toList())
        );
    }

    class AnzahlUndItem
    {

        Item item;
        int anzahl;

        public AnzahlUndItem(Item item, int anzahl)
        {
            this.item = item;
            this.anzahl = anzahl;
        }

    }

}
