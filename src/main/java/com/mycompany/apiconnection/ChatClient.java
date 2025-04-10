/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apiconnection;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author rokom
 */
public class ChatClient {
    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author rokom
 */
    private int portNumber;
    private String hostName;
    private String fromUser;
    private String fromServer;
    private boolean terminateThread = false;
    private boolean isClicked = false;
    private boolean sharedPortfolio = false;
    private boolean readFirstLine = false;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;
    
    /**
     * @param args the command line arguments
     */
    ChatClient(int port, String host, javax.swing.JTextArea output, javax.swing.JTextField msg, javax.swing.JButton button, String username,
            javax.swing.JMenuItem sharePortfolio, MongoDBManager mongoManager, APIManager apiManager, GUIManager guiManager) throws IOException {
        // TODO code application logic here
        portNumber = port;
        hostName = host;
        try (
            Socket echoSocket = new Socket(hostName, portNumber);
        ) {
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            button.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isClicked = true;
                    System.out.println("Button clicked");
                }
            });
            // add listener to the share portfolio menu item
            sharePortfolio.addActionListener((ActionEvent e) -> {
                sharedPortfolio = true;
                System.out.println("Share portfolio button clicked.");
            });
            // add listener to the end chat button
            ((ChatGUI)guiManager.getFrame("chatFrame")).getEndButton().addActionListener((ActionEvent e) -> {
                terminateThread = true;
                ((ChatGUI)guiManager.getFrame("chatFrame")).resetChatGUI();
            });
            while ((fromServer = in.readLine()) != null && !terminateThread) {
                
                fromUser = "";

                if (fromServer.startsWith("Message: ")) {
                    System.out.println(fromServer.substring(9));
                    output.append(fromServer.substring(9) + "\n");
                }
                else if (fromServer.startsWith("Portfolio: ")) {
                    ((ChatGUI)guiManager.getFrame("chatFrame")).drawOfflineChart(fromServer);
                    output.append(fromServer.substring(11) + "\n");
                }
                if (isClicked) {
                    fromUser = msg.getText();
                    msg.setText("");
                    System.out.println(username + ": " + fromUser);
                    output.append(username + ": " + fromUser + "\n");
                    isClicked = false;
                    output.getCaret().moveDot(output.getDocument().getLength());
                }
                else if (sharedPortfolio) {
                    fromUser = (mongoManager.getPortfolioLine(mongoManager.getCollection(),
                            apiManager.getObject(), apiManager.getJSONLength(),
                            apiManager.getPointers(), apiManager.getValues()));
                    System.out.println("JSON Length: " + apiManager.getJSONLength());
                    System.out.println("Records:" + mongoManager.readRecords(mongoManager.getCollection(),
                            apiManager.getObject(), apiManager.getJSONLength(),
                            apiManager.getPointers(), apiManager.getValues()));
                    output.append("Sharing portfolio...\n");
                }
                if (!fromUser.equals("")) {
                    if (sharedPortfolio) {
                    sharedPortfolio = false;
                    out.println("Portfolio: " + username + ": " + fromUser);
                    }
                    else
                        out.println("Message: " + username + ": " + fromUser);
                }
                else
                    out.println("0");
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostName);
            System.exit(1);       
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    } 
    
    public void sendMessage(String msg) {
        fromUser = msg;
        out.println(msg);
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getFromServer() {
        return fromServer;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
}

