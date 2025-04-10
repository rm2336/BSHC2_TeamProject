/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apiconnection;

import java.util.ArrayList;

/**
 *
 * @author rokom
 * This object is passed by reference to every GUI form to facilitate navigation.
 */
public class GUIManager {
    private ArrayList<javax.swing.JFrame> frameList;
    private javax.swing.JFrame currentFrame = null;
    GUIManager() {
        frameList = new ArrayList<>();
    }
    
    public void addFrame(javax.swing.JFrame frame) {
        frameList.add(frame);
    }
    
    public ArrayList<javax.swing.JFrame> getFrameList() {
        return frameList;
    }
    
    public javax.swing.JFrame getFrame(String name) {
        for (javax.swing.JFrame frame : frameList) {
            if (frame.getName().equals(name))
                return frame;
        }
        return null;
    }
    
    public void loadFrame(String nextFrame) {
        // run a loop that searches for the next frame
        for (javax.swing.JFrame frame : frameList) {
            if (frame.getName().equals(nextFrame)) {
                if (currentFrame != null) {
                currentFrame.setVisible(false);
                frame.setLocation(currentFrame.getLocation());
                currentFrame = frame;
                // refresh editor menu if meant to load
                if (frame instanceof EditorGUI)
                    ((EditorGUI) frame).refreshDisplay();
                else if (frame instanceof SummaryGUI)
                    ((SummaryGUI) frame).loadNews();
                }
                else if (frame instanceof LoginGUI)
                    ((LoginGUI) frame).loadSavedCredentials();
                else if (frame instanceof ChatGUI)
                    ((ChatGUI) frame).resetChatGUI();
                currentFrame = frame;
                frame.setVisible(true);
            }
        }      
    }
}
