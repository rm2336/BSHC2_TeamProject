/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apiconnection;

/**
 *
 * @author rokom
 */
public class ChatProtocol {
    private static final int WAITING = 0;
   
    public String processInput(String theInput) {
        String theOutput;
        
        theOutput = "No message";
        if (theInput.startsWith("Message: ")) {
            theOutput = theInput.substring(9);
        }
        else if (theInput.startsWith("Portfolio: "))
            theOutput = theInput.substring(12);
        return theOutput;
    }    
}
