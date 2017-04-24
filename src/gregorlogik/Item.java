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
public class Item
{

    public String ean, artikelnummer, name, beschreibung, verkaufspreis;

    public Item(String ean, String artikelnummer, String name, String beschreibung, String verkaufspreis)
    {
        this.name = name;
        this.ean = ean;
        this.artikelnummer = artikelnummer;
        this.beschreibung = beschreibung;
        this.verkaufspreis = verkaufspreis;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "Name: "+this.name+ " Beschreibung: "+this.beschreibung+ " EAN:"+this.ean;
    }

}
