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
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author rokom
 */
public class ChatServer {
    private int portNumber;
    private boolean isClicked = false;
        // TODO code application logic here
    ChatServer(int port, javax.swing.JTextArea output, javax.swing.JTextField msg, javax.swing.JButton button, String username) {
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
            button.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isClicked = true;
                    System.out.println("Button clicked");
                }
            });   
            // Initiate conversation with client
            ChatProtocol kkp = new ChatProtocol();
            outputLine = kkp.processInput(null);
            
            out.println(username + ": " + outputLine);
            
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.equals(""))
                    output.append(inputLine + "\n");
                outputLine = kkp.processInput(inputLine);
                if (isClicked) {
                    outputLine = msg.getText();
                    msg.setText("");
                    System.out.println(username + ": " + outputLine);
                    output.append(username + ": " + outputLine + "\n");
                    isClicked = false;
                    output.getCaret().moveDot(output.getDocument().getLength());
                }
                out.println(username + ": " + outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " +
                    portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
                
    }  
}
