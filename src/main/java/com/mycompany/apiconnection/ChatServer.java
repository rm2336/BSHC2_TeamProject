/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apiconnection;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author rokom
 */
public class ChatServer {
    private int portNumber;
    private boolean isClicked = false;
    private boolean sharedPortfolio = false;
    private boolean terminateThread = false;
    
        // TODO code application logic here
    ChatServer(int port, javax.swing.JTextArea output, javax.swing.JTextField msg, javax.swing.JButton button, String username,
            javax.swing.JMenuItem sharePortfolio, MongoDBManager mongoManager, APIManager apiManager, GUIManager guiManager) {
        portNumber = port;
        System.out.println("Running server...");
        try (
            ServerSocket serverSocket = 
                    new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
            String inputLine, outputLine;
            // add listener to the button
            button.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isClicked = true;
                    System.out.println("Button clicked.");
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
            // Initiate conversation with client
            ChatProtocol kkp = new ChatProtocol();
            outputLine = "Connection established.";
            
            out.println("Message: " + username + ": " + outputLine);
            
            while ((inputLine = in.readLine()) != null && !terminateThread) {
                if (!inputLine.equals("0")) {
                    if (inputLine.startsWith("Message: ")) 
                        output.append(inputLine.substring(9) + "\n");
                    else if (inputLine.startsWith("Portfolio: ")) {
                        ((ChatGUI)guiManager.getFrame("chatFrame")).drawOfflineChart(inputLine);
                        output.append(inputLine.substring(11) + "\n");
                    }
                }
                //outputLine = kkp.processInput(inputLine);
                outputLine = "No message";
                if (isClicked) {
                    outputLine = msg.getText();
                    msg.setText("");
                    System.out.println(username + ": " + outputLine);
                    output.append(username + ": " + outputLine + "\n");
                    isClicked = false;
                    output.getCaret().moveDot(output.getDocument().getLength());
                }
                else if (sharedPortfolio) {
                    outputLine = (mongoManager.getPortfolioLine(mongoManager.getCollection(),
                            apiManager.getObject(), apiManager.getJSONLength(),
                            apiManager.getPointers(), apiManager.getValues()));
                    output.append("Sharing portfolio...\n");
                }
                if (!outputLine.equals("No message")) {
                    if (sharedPortfolio) {
                        out.println("Portfolio: " + username + ": " + outputLine);
                        sharedPortfolio = false;
                    }
                    else
                        out.println("Message: " + username + ": " + outputLine);
                }
                else
                    out.println("0");
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " +
                    portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
           
    }
}
