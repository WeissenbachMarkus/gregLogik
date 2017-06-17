/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregorlogik;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author markus
 */
public class ThreadItemWatcher {

    private ArrayList<LagerItemAnzahlLager> auftraege;
    private ArrayList<Item> itemList;
    private int count;
    private final Lagerverwaltung lagerverwalung;

    public ThreadItemWatcher(ArrayList<Item> list, Lagerverwaltung lv) {
        this.auftraege = new ArrayList<>();
        this.itemList = list;
        this.count=0;
        this.lagerverwalung=lv;
    }

    public void callbackSetAuftraege(ArrayList<LagerItemAnzahlLager> info) {
        this.auftraege.addAll(info);
        this.count++;
        
        System.out.println("STOP " +count +"  "+itemList.size());
    }

    public ArrayList<LagerItemAnzahlLager> start()  {
      ArrayList<Thread> threadsList= new ArrayList<>();
        
        for (int i = 0; i < this.itemList.size(); i++) {
            
            System.out.println("Created Threads "+(i+1));
            Thread thread=new Thread(new ThreadIItem(this.itemList.get(i), this.lagerverwalung,this));
        threadsList.add(thread);
           thread.start();       
        }
        
        for (Thread thread : threadsList) {
            
             try {
               thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadItemWatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
     return this.auftraege;
    }
}
