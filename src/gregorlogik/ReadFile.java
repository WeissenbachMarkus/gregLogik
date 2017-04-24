/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregorlogik;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author markus
 */
public class ReadFile
{

    String csvFile;
    BufferedReader br;
    String csvSplitBy;

    public ReadFile(String csvFile)
    {

        this.csvFile = csvFile;
        this.csvSplitBy = "\t";

    }

    public ArrayList<String[]> read()
    {

        ArrayList<String[]> csvFile = new ArrayList<>();

        try
        {
            this.br = new BufferedReader(new FileReader(this.csvFile));

            String line = "";
            while ((line = this.br.readLine()) != null)
            {

                String[] itemsAndStock = line.split(this.csvSplitBy);

                csvFile.add(itemsAndStock);
            }

        } catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return csvFile;
    }

}
