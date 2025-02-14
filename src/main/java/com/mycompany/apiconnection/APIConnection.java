/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.apiconnection;

import com.sun.syndication.io.FeedException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rokom
 */
public class APIConnection {

    public static void main(String[] args) {
        CryptoChaunGUI gui = new CryptoChaunGUI();
        RSSReader reader = new RSSReader("https://cointelegraph.com/rss");
        try {
            gui.setNewsEntries(reader.getTitles());
        } catch (IOException | FeedException ex) {
            System.out.println(ex);
        }
        gui.setVisible(true);
    }  
}
