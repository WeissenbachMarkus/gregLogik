/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregorlogik;

/**
 *
 * @author markus
 */
public class LagerItemAnzahlLager implements Comparable<LagerItemAnzahlLager> {
    
        Lager von, nach;
        Item item;
        int anzahl;

        public LagerItemAnzahlLager(Lager von, Item item, int anzahl, Lager nach)
        {
            this.von = von;
            this.nach = nach;
            this.item = item;
            this.anzahl = anzahl;
        }

        public void inkrement()
        {
            this.anzahl++;
        }

        @Override
        public String toString()
        {
            return "Von: " + this.von.name + "  Name: " + this.item.name+"  Beschreibung: "+this.item.beschreibung+"  EAN: "+this.item.ean + "  Anzahl: " + this.anzahl + "  Nach: " + this.nach.name;
        }

        @Override
        public int compareTo(LagerItemAnzahlLager t)
        {
            int comparison = this.item.getName().compareTo(t.item.getName());
            if (comparison == 0)
            {
                if (this.anzahl > t.anzahl)
                {
                    return -1;
                } else if (this.anzahl == t.anzahl)
                {
                    return 0;
                } else
                {
                    return 1;
                }
            } else
            {
                return comparison;
            }

        }
}
