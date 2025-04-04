/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.apiconnection;

import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
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
public class ChatGUI extends javax.swing.JFrame {

    private int portNumber;
    private String IPAddress;
    private ChatClient chatClient;
    private ChatServer chatServer;
    private ChatProtocol chatProtocol;
    private String name;
    private Thread serverThread;
    private Thread clientThread;
    private GUIManager guiManager;
    private APIManager apiManager;
    private MongoDBManager mongoManager;
    /**
     * Creates new form ChatGUI
     */
    public ChatGUI() {
        initComponents();
    }
    
    public void setUsername(String name) {
        this.name = name;
    }
    
    public void setGUIManager(GUIManager guiManager) {
        this.guiManager = guiManager;
    }
    
    public void setAPIManager(APIManager apiManager) {
        this.apiManager = apiManager;
    }
    
    public void setMongoDBManager(MongoDBManager mongoManager) {
        this.mongoManager = mongoManager;
    }
    
    public javax.swing.JButton getEndButton() {
        return endBTN;
    }
    
    public void resetChatGUI() {
        messageTA.setText("");
        sendBTN.setEnabled(false);
        connectionRBG.clearSelection();
        String users = ((SummaryGUI)guiManager.getFrame("summaryFrame")).getLeaderboardConnection().readLeaderboard();
        LocalDate date = ((SummaryGUI)guiManager.getFrame("summaryFrame")).getLeaderboardConnection().getLastLoggedInDate("rmatao");
        System.out.println(date);
    }

    public void drawOfflineChart(String recordLine) {
        System.out.println("Drawing: " + recordLine);
        DefaultPieDataset myPieDataset = new DefaultPieDataset();
        myPieDataset.clear();
        // extract data from a single line
        ArrayList<String> coinNames = new ArrayList<>();
        ArrayList<Double> coinPrices = new ArrayList<>();
        // extract coin names
        for (int i = 0; i < recordLine.length() - 6; i++) {
            if (recordLine.charAt(i) == 'C' && recordLine.charAt(i+1) == 'o' && recordLine.charAt(i+2) == 'i'
                    && recordLine.charAt(i+3) == 'n' && recordLine.charAt(i+4) == ':') {
                int k = i+5;
                String feed = "";
                while (recordLine.charAt(k) != '|') {
                    feed += recordLine.charAt(k);
                    k++;
                }
                coinNames.add(feed);
                System.out.println("Adding " + feed);
            }
        }
        // extract coin prices
        for (int i = 0; i < recordLine.length() - 13; i++) {
            if (recordLine.charAt(i) == 'T' && recordLine.charAt(i+1) == 'o' && recordLine.charAt(i+2) == 't'
                    && recordLine.charAt(i+3) == 'a' && recordLine.charAt(i+4) == 'l'
                    && recordLine.charAt(i+5) == ' ' && recordLine.charAt(i+6) == 'V'
                    && recordLine.charAt(i+7) == 'a' && recordLine.charAt(i+8) == 'l'
                    && recordLine.charAt(i+9) == 'u' && recordLine.charAt(i+10) == 'e'
                    && recordLine.charAt(i+11) == ':' && recordLine.charAt(i+12) == ' ') {
                int k = i+13;
                String feed = "";
                while (recordLine.charAt(k) != '|') {
                    feed += recordLine.charAt(k);
                    k++;
                }
                coinPrices.add(Double.valueOf(feed));
                System.out.println("Adding " + feed);
            }
        }
        for (int i = 0; i < coinNames.size(); i++) {
            myPieDataset.setValue(coinNames.get(i), BigDecimal.valueOf(coinPrices.get(i)).setScale(2, RoundingMode.HALF_EVEN));
            }
        JFreeChart myChart = ChartFactory.createPieChart("Crypto Portfolio", myPieDataset, rootPaneCheckingEnabled, rootPaneCheckingEnabled, Locale.ENGLISH);
        ChartFrame chartFrame = new ChartFrame("Chart", myChart);
        // allow the user to save the chart as an image
        javax.swing.JButton saveBTN = new javax.swing.JButton();
        saveBTN.setText("Save");
        saveBTN.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OutputStream out = new FileOutputStream("chart.png");
                    ChartUtilities.writeChartAsPNG(out, myChart, chartFrame.getWidth(), chartFrame.getHeight());
                    JOptionPane.showMessageDialog(null, "Image saved to .png file.");
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
        // add text area
        javax.swing.JTextArea infoTA = new javax.swing.JTextArea();
        chartFrame.add(saveBTN);
        chartFrame.add(infoTA);
        chartFrame.setLayout(null);
        saveBTN.setBounds(chartFrame.getX(), chartFrame.getHeight(), 75, 20);
        chartFrame.setVisible(true);
        chartFrame.setSize(400, 500);
        // calculate sum of all currency values
        double portfolioValue = 0;
        for (int i = 0; i < coinPrices.size(); i++) {
            portfolioValue += coinPrices.get(i);
        }
        infoTA.setText("Total value of portfolio: €" + BigDecimal.valueOf(portfolioValue).setScale(2, RoundingMode.HALF_EVEN));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        connectionRBG = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageTA = new javax.swing.JTextArea();
        messageTF = new javax.swing.JTextField();
        sendBTN = new javax.swing.JButton();
        modeLBL = new javax.swing.JLabel();
        serverRB = new javax.swing.JRadioButton();
        clientRB = new javax.swing.JRadioButton();
        backBTN = new javax.swing.JButton();
        endBTN = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        shareMU = new javax.swing.JMenu();
        portfolioMI = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cryptochaun");
        setName("chatFrame"); // NOI18N

        messageTA.setColumns(20);
        messageTA.setRows(5);
        jScrollPane1.setViewportView(messageTA);

        sendBTN.setText("Send");
        sendBTN.setEnabled(false);
        sendBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendBTNActionPerformed(evt);
            }
        });

        modeLBL.setText("Mode");

        connectionRBG.add(serverRB);
        serverRB.setText("Server");
        serverRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverRBActionPerformed(evt);
            }
        });

        connectionRBG.add(clientRB);
        clientRB.setText("Client");
        clientRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientRBActionPerformed(evt);
            }
        });

        backBTN.setText("Back");
        backBTN.setName("backBTN"); // NOI18N
        backBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBTNActionPerformed(evt);
            }
        });

        endBTN.setText("End Chat");
        endBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endBTNActionPerformed(evt);
            }
        });

        shareMU.setText("Share");

        portfolioMI.setText("Portfolio");
        portfolioMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portfolioMIActionPerformed(evt);
            }
        });
        shareMU.add(portfolioMI);

        jMenuBar1.add(shareMU);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sendBTN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(messageTF, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(38, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(modeLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(serverRB, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clientRB, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(endBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backBTN)
                        .addGap(17, 17, 17))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(messageTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendBTN))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(modeLBL)
                            .addComponent(serverRB)
                            .addComponent(clientRB))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(backBTN)
                            .addComponent(endBTN))
                        .addGap(15, 15, 15))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void serverRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverRBActionPerformed
        // TODO add your handling code here:
        serverThread = new Thread() {
            public void run() {
            portNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter port number: "));
            sendBTN.setEnabled(true);
            chatServer = new ChatServer(portNumber, messageTA, messageTF, sendBTN, name, portfolioMI,
                    mongoManager, apiManager, guiManager);
            }
        };
        serverThread.start();
    }//GEN-LAST:event_serverRBActionPerformed

    private void clientRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientRBActionPerformed
        // TODO add your handling code here:
        clientThread = new Thread() {
            public void run() {
            IPAddress = JOptionPane.showInputDialog("Enter IP address: ");
            portNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter port number: "));
            try {
                sendBTN.setEnabled(true);
                chatClient = new ChatClient(portNumber, IPAddress, messageTA, messageTF, sendBTN, name, portfolioMI,
                mongoManager, apiManager, guiManager);
            } catch (Exception e) {
                System.out.println(e);
                }
            }
        };
        clientThread.start();
    }//GEN-LAST:event_clientRBActionPerformed

    private void sendBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendBTNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sendBTNActionPerformed

    private void backBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTNActionPerformed
        // TODO add your handling code here:
        guiManager.loadFrame("summaryFrame");
    }//GEN-LAST:event_backBTNActionPerformed

    private void portfolioMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portfolioMIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portfolioMIActionPerformed

    private void endBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endBTNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_endBTNActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBTN;
    private javax.swing.JRadioButton clientRB;
    private javax.swing.ButtonGroup connectionRBG;
    private javax.swing.JButton endBTN;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea messageTA;
    private javax.swing.JTextField messageTF;
    private javax.swing.JLabel modeLBL;
    private javax.swing.JMenuItem portfolioMI;
    private javax.swing.JButton sendBTN;
    private javax.swing.JRadioButton serverRB;
    private javax.swing.JMenu shareMU;
    // End of variables declaration//GEN-END:variables
}
