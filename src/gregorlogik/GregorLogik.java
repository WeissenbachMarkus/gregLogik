/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregorlogik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


/**
 *
 * @author markus
 */
public class GregorLogik implements Runnable
{

    private String path;
    private NewJFrame frame;
    private int number;

    private static void lagerHinzufuegen(ArrayList<String[]> csvFile, Lagerverwaltung verwaltung)
    {
        Arrays.asList(csvFile.get(0)).forEach(e
                ->
                {
                    if (e.charAt(0) == 'F')
                    {
                        verwaltung.lagerHinzufuegen(e);
                    }
                }
        );

        csvFile.remove(0);

    }

    @Override
    public void run()
    {

        ReadFile readFile = new ReadFile(this.path);
        ArrayList<String[]> csvFile = readFile.read();

        Lagerverwaltung verwaltung = new Lagerverwaltung();
        lagerHinzufuegen(csvFile, verwaltung);
        einlagernDerItems(csvFile, verwaltung);

        ArrayList<LagerItemAnzahlLager> einzelAuftraege = verwaltung.gleicheItemBestandAus();

        // einzelAuftraege.sort((e, e2) -> e.von.toString().compareTo(e2.von.toString()));
        einzelAuftraege.sort((e, e2) -> e.compareTo(e2));

        pushResult(einzelAuftraege.stream()
             .filter(e-> e.anzahl>=this.number)
                .map(e -> e.toString())
                .collect(Collectors.joining("\n")));
    }

    public void setPath(String path, NewJFrame frame)
    {
        this.path = path;
        this.frame = frame;
    }

    public void pushResult(String result)
    {
        this.frame.setResult(result);
    }

    private static void einlagernDerItems(ArrayList<String[]> csvFile, Lagerverwaltung verwaltung)
    {
        csvFile.forEach(e
                ->
                {
                    Item item = new Item(e[0], e[1], e[2], e[3], e[4]);

                    verwaltung.itemList.add(item);

                    for (int i = 5; i < e.length - 1; i++)
                    {
                        int anzahl = Integer.parseInt(e[i].trim());
                        if (anzahl > 0)
                        {
                            verwaltung.lagerListe.get(i - 5).einlagern(anzahl, item);
                        }
                    }

                }
        );
    }

    void setNumber(String text)
    {
        try
        {
            this.number = Integer.parseInt(text);
        } catch (NumberFormatException e)
        {
            this.number=0;
        }
    }
}
