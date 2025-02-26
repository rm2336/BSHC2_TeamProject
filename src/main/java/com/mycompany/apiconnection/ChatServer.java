/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apiconnection;

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
        // TODO code application logic here
    ChatServer(int port) {
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
            
            // Initiate conversation with client
            ChatProtocol kkp = new ChatProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);
            
            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
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
