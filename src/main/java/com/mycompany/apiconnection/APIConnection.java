/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.apiconnection;

import com.sun.syndication.io.FeedException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 *
 * @author rokom
 */
public class APIConnection {
    public static void main(String[] args) {
        // instantiate interface classes
        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            System.out.println(e);
        }

        MongoDBManager mongoDB = new MongoDBManager();
        APIManager api = new APIManager();
        CredentialManager credentialManager = new CredentialManager();
        GUIManager manager = new GUIManager();

        CryptoChaunGUI gui = new CryptoChaunGUI();
        gui.setMongoDBManager(mongoDB);
        gui.setAPIManager(api);

        // Instantiate the EditorGUI and LoginGUI
        EditorGUI editor = new EditorGUI();
        editor.setMongoDBManager(mongoDB);
        editor.setAPIManager(api);
        editor.setGUIManager(manager);

        LoginGUI login = new LoginGUI();
        login.setCredentialManager(credentialManager);
        login.setMongoManager(mongoDB);
        login.setGUIManager(manager);

        // Set the LoginGUI instance in EditorGUI
        editor.setLoginGUI(login);  // Ensure loginGUI is set in EditorGUI

        SummaryGUI summary = new SummaryGUI();
        summary.setAPIManager(api);
        summary.setGUIManager(manager);
        summary.setMongoManager(mongoDB);

        TutorialGUI tutorial = new TutorialGUI();
        tutorial.setGUIManager(manager);

        ChatGUI chat = new ChatGUI();
        chat.setGUIManager(manager);

        RegistrationGUI register = new RegistrationGUI();
        register.setGUIManager(manager);

        VerificationGUI verification = new VerificationGUI();
        verification.setGUIManager(manager);

        // add frames to the GUI manager
        manager.addFrame(gui);
        manager.addFrame(editor);
        manager.addFrame(login);
        manager.addFrame(summary);
        manager.addFrame(tutorial);
        manager.addFrame(chat);
        manager.addFrame(register);
        manager.addFrame(verification);

        // Create and add UserSetting frame
        UserSetting userSetting = new UserSetting();
        userSetting.setGUIManager(manager);
        manager.addFrame(userSetting);

        for (int i = 0; i < manager.getFrameList().size(); i++) {
            System.out.println(manager.getFrameList().get(i).toString());
        }

        RSSReader reader = new RSSReader("https://cointelegraph.com/rss");
        try {
            gui.setNewsEntries(reader.getTitles());
            summary.setNewsEntries(reader.getTitles());
        } catch (IOException | FeedException ex) {
            System.out.println(ex);
        }

        manager.loadFrame("loginFrame");

        String testInput = "a";
        Scanner myScanner = new Scanner(System.in);
        while (!testInput.equals("")) {
            testInput = myScanner.nextLine();
            manager.loadFrame(testInput);
        }
    }
}
