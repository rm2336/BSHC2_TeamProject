/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.apiconnection;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author rokom
 */
public class CryptoChaunGUI extends javax.swing.JFrame {

    /**
     * Creates new form CryptoChaunGUI
     */
    public CryptoChaunGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundJP = new javax.swing.JPanel();
        usernameLBL = new javax.swing.JLabel();
        passwordLBL = new javax.swing.JLabel();
        usernameTF = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        displayTA = new javax.swing.JTextArea();
        connectBTN = new javax.swing.JButton();
        exitBTN = new javax.swing.JButton();
        passwordPF = new javax.swing.JPasswordField();
        progressPB = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backgroundJP.setBackground(new java.awt.Color(0, 102, 255));

        usernameLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        usernameLBL.setForeground(new java.awt.Color(255, 255, 255));
        usernameLBL.setText("Username:");

        passwordLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        passwordLBL.setForeground(new java.awt.Color(255, 255, 255));
        passwordLBL.setText("Password:");

        usernameTF.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        displayTA.setEditable(false);
        displayTA.setColumns(20);
        displayTA.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        displayTA.setLineWrap(true);
        displayTA.setRows(5);
        displayTA.setWrapStyleWord(true);
        displayTA.setAutoscrolls(false);
        jScrollPane1.setViewportView(displayTA);

        connectBTN.setText("Connect");
        connectBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectBTNActionPerformed(evt);
            }
        });

        exitBTN.setText("Exit");
        exitBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBTNActionPerformed(evt);
            }
        });

        passwordPF.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        passwordPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordPFActionPerformed(evt);
            }
        });

        progressPB.setForeground(new java.awt.Color(102, 255, 0));

        javax.swing.GroupLayout backgroundJPLayout = new javax.swing.GroupLayout(backgroundJP);
        backgroundJP.setLayout(backgroundJPLayout);
        backgroundJPLayout.setHorizontalGroup(
            backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundJPLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundJPLayout.createSequentialGroup()
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(connectBTN)
                            .addComponent(passwordLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundJPLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(exitBTN)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(backgroundJPLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(passwordPF, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                    .addComponent(usernameTF))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))))
                    .addGroup(backgroundJPLayout.createSequentialGroup()
                        .addComponent(progressPB, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        backgroundJPLayout.setVerticalGroup(
            backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundJPLayout.createSequentialGroup()
                .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundJPLayout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usernameLBL)
                            .addComponent(usernameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(passwordLBL)
                            .addComponent(passwordPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(progressPB, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundJPLayout.createSequentialGroup()
                        .addContainerGap(22, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)))
                .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectBTN)
                    .addComponent(exitBTN))
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backgroundJP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backgroundJP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBTNActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_exitBTNActionPerformed

    private void connectBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectBTNActionPerformed
        // TODO add your handling code here:
        try {
                URL weatherURL = new URL("https://www.met.ie/Open_Data/json/Outlook.json");
                HttpURLConnection connection = (HttpURLConnection) weatherURL.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                
                int responseCode = connection.getResponseCode();
                System.out.println(responseCode);
                
                StringBuilder infoString = new StringBuilder();
                Scanner scanner = new Scanner(weatherURL.openStream());
                while (scanner.hasNext()) {
                    infoString.append(scanner.nextLine());
                }
                scanner.close();
                
                //System.out.println("Info String: " + infoString);
                
                JSONParser myParse = new JSONParser();
                JSONArray weatherData = new JSONArray();
                Object parsedData = myParse.parse(String.valueOf(infoString));
                weatherData.add(parsedData);
                String forecast;
                String searchTerm = "Overview:";
                System.out.println(searchTerm.length());
                for (int i = 0, matches = 0; i < (infoString.toString().length() - searchTerm.length() - 1); i++) {
                    //System.out.println(infoString.toString().charAt(i));
                    matches = 0;
                    //System.out.println("I: " + i);
                    for (int j = i, k = 0; j < (i + searchTerm.length()); j++, k++) {
                        //System.out.println("J: " + j);
                        if (infoString.toString().charAt(j) == searchTerm.charAt(k)) {
                            matches++;
                            //System.out.println("Matches: " + matches);
                        }
                    }
                    if (matches == searchTerm.length()) {
                        //System.out.println(i);
                        forecast = infoString.toString().substring(i);
                        System.out.println(forecast);
                    }
                }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
        // connect to MongoDB
        String password = "";
        String user = "";
        try {
            user = URLEncoder.encode(usernameTF.getText(), "UTF-8");
            password = URLEncoder.encode(passwordPF.getText(), "UTF-8");
            progressPB.setValue(25);
        } catch (UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null,e);
            progressPB.setValue(0);
        }
        MongoClientSettings settings = null;
        try {
        String connectionString = "mongodb+srv://" + user + ":" + password + "@mycluster.eqvxj.mongodb.net/?retryWrites=true&w=majority&appName=myCluster";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        progressPB.setValue(50);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
            progressPB.setValue(0);
        }
        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("character_list");
                database.runCommand(new Document("ping", 1));
                MongoCollection<Document> collection = database.getCollection("got_characters");
                progressPB.setValue(75);
                Document output = collection.find(eq("name", "Steffon Baratheon")).first();
                displayTA.setText(output.toString());
                progressPB.setValue(100);
                JOptionPane.showMessageDialog(null, "Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                JOptionPane.showMessageDialog(null, e);
                progressPB.setValue(0);
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            progressPB.setValue(0);
        }
    }//GEN-LAST:event_connectBTNActionPerformed

    private void passwordPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordPFActionPerformed

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
            java.util.logging.Logger.getLogger(CryptoChaunGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CryptoChaunGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CryptoChaunGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CryptoChaunGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CryptoChaunGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundJP;
    private javax.swing.JButton connectBTN;
    private javax.swing.JTextArea displayTA;
    private javax.swing.JButton exitBTN;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel passwordLBL;
    private javax.swing.JPasswordField passwordPF;
    private javax.swing.JProgressBar progressPB;
    private javax.swing.JLabel usernameLBL;
    private javax.swing.JTextField usernameTF;
    // End of variables declaration//GEN-END:variables
}
