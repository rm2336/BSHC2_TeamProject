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
    boolean isClicked = false;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;
    /**
     * @param args the command line arguments
     */
    ChatClient(int port, String host, javax.swing.JTextArea output, javax.swing.JTextField msg, javax.swing.JButton button, String username) throws IOException {
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
            while ((fromServer = in.readLine()) != null) {
                if (fromServer.equals("Bye."))
                    break;
                
                fromUser = "";

                if (!fromServer.contains("No message")) {
                    System.out.println(fromServer);
                    output.append(fromServer  + "\n");
                }
                if (isClicked) {
                    fromUser = msg.getText();
                    msg.setText("");
                    System.out.println(username + ": " + fromUser);
                    output.append(username + ": " + fromUser + "\n");
                    isClicked = false;
                    output.getCaret().moveDot(output.getDocument().getLength());
                }
                
                if (!fromUser.equals(""))
                    out.println(username + ": " + fromUser);
                else
                    out.println(fromUser);
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

