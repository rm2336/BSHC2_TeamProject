/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.apiconnection;

import com.sun.syndication.io.FeedException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rokom
 */
public class APIConnection {
    public static void main(String[] args) {
        // instantiate interface classes
        MongoDBManager mongoDB = new MongoDBManager();
        APIManager api = new APIManager();
        
        GUIManager manager = new GUIManager();
        
        CryptoChaunGUI gui = new CryptoChaunGUI();
        gui.setMongoDBManager(mongoDB);
        gui.setAPIManager(api);
        
        EditorGUI editor = new EditorGUI();
        editor.setMongoDBManager(mongoDB);
        editor.setAPIManager(api);
        
        LoginGUI login = new LoginGUI();
        SummaryGUI summary = new SummaryGUI();
        
        // add frames to the GUI manager
        manager.addFrame(gui);
        manager.addFrame(editor);
        manager.addFrame(login);
        manager.addFrame(summary);
        
        RSSReader reader = new RSSReader("https://cointelegraph.com/rss");
        try {
            gui.setNewsEntries(reader.getTitles());
        } catch (IOException | FeedException ex) {
            System.out.println(ex);
        }
        
        manager.loadFrame("cryptochaunFrame");
        String testInput = "a";
        Scanner myScanner = new Scanner(System.in);
        while (!testInput.equals("")) {
            testInput = myScanner.nextLine();
            manager.loadFrame(testInput);
        }
    }  
}
