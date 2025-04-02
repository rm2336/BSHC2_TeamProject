/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.apiconnection;

import brevo.*;
import okhttp3.*;
import brevo.auth.*;
import brevoModel.*;
import brevoApi.AccountApi;
import brevoApi.TransactionalEmailsApi;

import java.io.File;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author rokom
 */
public class RegistrationGUI extends javax.swing.JFrame {

    private GUIManager guiManager;
    private MongoDBManager mongoManager;
    
    /**
     * Creates new form RegistrationGUI
     */
    public RegistrationGUI() {
        initComponents();
        mongoManager = new MongoDBManager();
        mongoManager.connect("adminUser", "upPGU?7fZ+5d@4k", "mycluster.eqvxj", "authentication_database", "credentials", false);
    }
    
    public MongoDBManager getAuthenticator() {
        return mongoManager;
    }
    
    public void setGUIManager(GUIManager manager) {
        guiManager = manager;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        backBTN = new javax.swing.JButton();
        confirmBTN = new javax.swing.JButton();
        usernameLBL = new javax.swing.JLabel();
        passwordLBL = new javax.swing.JLabel();
        usernameTF = new javax.swing.JTextField();
        emailLBL = new javax.swing.JLabel();
        emailTF = new javax.swing.JTextField();
        passwordPF = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CryptoChaun");
        setName("registrationFrame"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        backBTN.setText("Back");

        confirmBTN.setText("Confirm");
        confirmBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBTNActionPerformed(evt);
            }
        });

        usernameLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        usernameLBL.setForeground(new java.awt.Color(255, 255, 255));
        usernameLBL.setText("Username");

        passwordLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        passwordLBL.setForeground(new java.awt.Color(255, 255, 255));
        passwordLBL.setText("Password");

        emailLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        emailLBL.setForeground(new java.awt.Color(255, 255, 255));
        emailLBL.setText("Email Address");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(emailLBL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordLBL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameLBL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 279, Short.MAX_VALUE)
                        .addComponent(confirmBTN)
                        .addGap(53, 53, 53))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usernameTF)
                            .addComponent(emailTF, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(passwordPF))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLBL)
                    .addComponent(usernameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLBL)
                    .addComponent(passwordPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailLBL)
                    .addComponent(emailTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backBTN)
                    .addComponent(confirmBTN))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBTNActionPerformed
        // TODO add your handling code here:
        // send email
        // code based on: https://github.com/getbrevo/brevo-java?tab=readme-ov-file#installation
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        
        // authenticate API key
        ApiKeyAuth apiKey = (ApiKeyAuth)defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(((LoginGUI)guiManager.getFrame("loginFrame")).getBrevoKey());
        //apiKey.setApiKeyPrefix("Token");
        
        
        ApiKeyAuth partnerKey = (ApiKeyAuth)defaultClient.getAuthentication("partner-key");
        partnerKey.setApiKey("r2WgvMtfhanDNT3m");
        
        // check if API key has been entered
        if (mongoManager.accountExists(usernameTF.getText(), emailTF.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Cannot proceed with registration. The specified username may"
                    + "already be taken or the entered email address might have an existing account linked to it.");
        }
        else
            try {         
                //AllRouteExamples obj = new AllRouteExamples();
                TransactionalEmailsApi api = new TransactionalEmailsApi();
                SendSmtpEmailSender sender = new SendSmtpEmailSender();
                sender.setEmail("rmatano5914@gmail.com");
                sender.setName("CryptoChaun Admin Team");
                List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
                SendSmtpEmailTo to = new SendSmtpEmailTo();
                toList.add(to);
                to.setName(usernameTF.getText());
                to.setEmail(emailTF.getText());

                SendSmtpEmailReplyTo replyTo = new SendSmtpEmailReplyTo();
                replyTo.setEmail("rmatano5914@gmail.com");
                replyTo.setName("Roko Matanovic");

                Properties headers = new Properties();
                headers.setProperty("CrpytoChaun", "001");

                Properties params = new Properties();
                params.setProperty("user", usernameTF.getText());
                params.setProperty("subject", "Confirmation");

                // generate verification code
                int firstDigit = (int)(Math.random() * 10);
                int secondDigit = (int)(Math.random() * 10);
                int thirdDigit = (int)(Math.random() * 10);
                int fourthDigit = (int)(Math.random() * 10);
                int[] verificationCode = new int[4];
                verificationCode[0] = firstDigit;
                verificationCode[1] = secondDigit;
                verificationCode[2] = thirdDigit;
                verificationCode[3] = fourthDigit;

                params.setProperty("firstDigit", String.valueOf(firstDigit));
                params.setProperty("secondDigit", String.valueOf(secondDigit));
                params.setProperty("thirdDigit", String.valueOf(thirdDigit));
                params.setProperty("fourthDigit", String.valueOf(fourthDigit));

                SendSmtpEmail email = new SendSmtpEmail();
                email.setSender(sender);
                email.setTo(toList);
                email.setReplyTo(replyTo);
                email.setHtmlContent("<html><body><h1>Hi {{params.user}}, </h1><p>Thanks for creating a CryptoChaun account!</p>"
                        + "<p>Please find your verification code below:</p>" + "<h1>{{params.firstDigit}}{{params.secondDigit}}{{params.thirdDigit}}{{params.fourthDigit}}</h1>"
                        + "<p>Best,</p><p>Roko</p></body></html>");
                email.setSubject("My {{params.subject}}");
                email.setHeaders(headers);
                email.setParams(params);

                CreateSmtpEmail response = api.sendTransacEmail(email);
                System.out.println(response.toString());
                guiManager.loadFrame("verificationFrame");
                ((VerificationGUI)guiManager.getFrame("verificationFrame")).setVerificationCode(verificationCode);
                ((VerificationGUI)guiManager.getFrame("verificationFrame")).setMongoManager(mongoManager);
                ((VerificationGUI)guiManager.getFrame("verificationFrame")).setCredentials(usernameTF.getText(), String.valueOf(passwordPF.getPassword()), emailTF.getText());
                ((VerificationGUI)guiManager.getFrame("verificationFrame")).modifyMessage();
                usernameTF.setText("");
                passwordPF.setText("");
                emailTF.setText("");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                System.out.println(e.getCause());
                JOptionPane.showMessageDialog(rootPane, "Could not send email. API key may be invalid or the connection has dropped.");
            }

    }//GEN-LAST:event_confirmBTNActionPerformed

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
            java.util.logging.Logger.getLogger(RegistrationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrationGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBTN;
    private javax.swing.JButton confirmBTN;
    private javax.swing.JLabel emailLBL;
    private javax.swing.JTextField emailTF;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel passwordLBL;
    private javax.swing.JPasswordField passwordPF;
    private javax.swing.JLabel usernameLBL;
    private javax.swing.JTextField usernameTF;
    // End of variables declaration//GEN-END:variables
}
