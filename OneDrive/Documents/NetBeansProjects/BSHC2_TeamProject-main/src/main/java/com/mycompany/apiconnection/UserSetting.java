/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.apiconnection;
import java.util.Properties;
import javax.swing.*;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;


/**
 *
 * @author <TeephopAlex MacHugh>
 */
public class UserSetting extends javax.swing.JFrame {

    private GUIManager guiManager;
    private SummaryGUI SummaryGUI;
    
    public UserSetting() {
        initComponents();
        setName("userSettingFrame");
        loadUserSettingsFromFile();
        
        
    }
    
    public void setGUIManager(GUIManager guiManager) {
        this.guiManager = guiManager;
    }
    
    public void setSummaryGUI (SummaryGUI SummaryGUI) {
        this.SummaryGUI = SummaryGUI;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        SaveMongoDBCB = new java.awt.Checkbox();
        SaveAPICB = new java.awt.Checkbox();
        SaveCredentialCB = new java.awt.Checkbox();
        SaveBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("User Setting");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Save MongoDB Database");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Save API Connection");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Save User Credential");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, 20));
        jPanel1.add(SaveMongoDBCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 50, -1, -1));
        jPanel1.add(SaveAPICB, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 80, -1, -1));
        jPanel1.add(SaveCredentialCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 110, -1, -1));

        SaveBtn.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        SaveBtn.setText("SAVE SETTING");
        SaveBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveBtnActionPerformed(evt);
            }
        });
        jPanel1.add(SaveBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 100, -1));

        backBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backBtn.setText("Back");
        backBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        jPanel1.add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 50, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveBtnActionPerformed
         // Read the state of each AWT Checkbox using .getState()
        boolean saveMongoDB = SaveMongoDBCB.getState();
        boolean saveAPI = SaveAPICB.getState();
        boolean saveCredential = SaveCredentialCB.getState();
        
         String userName = "myUser";    
        String password = "myPass";
        String cluster = "myCluster";
        
        // Save everything in one call:
         FileManager.saveConfig(userName, password, cluster, saveMongoDB, saveAPI, saveCredential);
        
        JOptionPane.showMessageDialog(this, "Setting Saved!");
    }//GEN-LAST:event_SaveBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        if (guiManager != null) {
            guiManager.loadFrame("summaryFrame");
        } else {
            System.out.println("guiManager is null");
        }
    }//GEN-LAST:event_backBtnActionPerformed

        private void loadUserSettingsFromFile() {
        Properties props = FileManager.loadConfig();
        boolean saveMongoDB = Boolean.parseBoolean(props.getProperty("saveMongoDB", "false"));
        boolean saveAPI = Boolean.parseBoolean(props.getProperty("saveAPI", "false"));
        boolean saveCredential = Boolean.parseBoolean(props.getProperty("saveCredential", "false"));
        
        SaveMongoDBCB.setState(saveMongoDB);
        SaveAPICB.setState(saveAPI);
        SaveCredentialCB.setState(saveCredential);
    }
        
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
            java.util.logging.Logger.getLogger(UserSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserSetting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Checkbox SaveAPICB;
    private javax.swing.JButton SaveBtn;
    private java.awt.Checkbox SaveCredentialCB;
    private java.awt.Checkbox SaveMongoDBCB;
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
