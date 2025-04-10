/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.apiconnection;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author tombr
 */
public class EditorGUI extends javax.swing.JFrame {
    private MongoDBManager mongoManager;
    private APIManager APIManager;
    private GUIManager guiManager;
    private LoginGUI loginGUI;
    
    /**
     * Creates new form EditorGUI
     */
    public EditorGUI() {
        initComponents();
    }
    
    public void setLoginGUI(LoginGUI loginGUI){
        this.loginGUI = loginGUI;
    }
    public void setMongoDBManager(MongoDBManager mongoManager) {
        this.mongoManager = mongoManager;
    }
    
    public void setAPIManager(APIManager APIManager) {
        this.APIManager = APIManager;
    }
    
    public void setGUIManager(GUIManager guiManager) {
        this.guiManager = guiManager;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        editorTA = new javax.swing.JTextArea();
        addBTN = new javax.swing.JButton();
        updateBTN = new javax.swing.JButton();
        deleteBTN = new javax.swing.JButton();
        backBTN = new javax.swing.JButton();
        editorLBL = new javax.swing.JLabel();
        editorSB = new javax.swing.JScrollBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cryptochaun");
        setName("editorFrame"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));

        editorTA.setColumns(20);
        editorTA.setRows(5);
        jScrollPane1.setViewportView(editorTA);

        addBTN.setText("Add");
        addBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBTNActionPerformed(evt);
            }
        });

        updateBTN.setText("Update");
        updateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBTNActionPerformed(evt);
            }
        });

        deleteBTN.setText("Delete");
        deleteBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBTNActionPerformed(evt);
            }
        });

        backBTN.setText("Back");
        backBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBTNActionPerformed(evt);
            }
        });

        editorLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        editorLBL.setForeground(new java.awt.Color(255, 255, 255));
        editorLBL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editorLBL.setText("Editor");

        editorSB.setOrientation(javax.swing.JScrollBar.HORIZONTAL);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editorLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(152, 152, 152))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backBTN)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(addBTN)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(updateBTN)
                                    .addGap(60, 60, 60)
                                    .addComponent(deleteBTN))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(editorSB, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(editorLBL)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editorSB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBTN)
                    .addComponent(updateBTN)
                    .addComponent(deleteBTN))
                .addGap(29, 29, 29)
                .addComponent(backBTN)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBTNActionPerformed
        // TODO add your handling code here:
      JFrame inputFrame = new JFrame();
        inputFrame.setSize(200, 200);
        javax.swing.JLabel currencyLabel = new javax.swing.JLabel();
        javax.swing.JButton confirmButton = new javax.swing.JButton();
        javax.swing.JTextField currencyTF = new javax.swing.JTextField();
        javax.swing.JComboBox currencyCBox = new javax.swing.JComboBox();
        javax.swing.JPanel backgroundJP = new javax.swing.JPanel();
        for (int i = 0; i < APIManager.getJSONLength(); i++) {
            currencyCBox.addItem(APIManager.getObject().query(APIManager.getPointers().get(i)));
        }
        confirmButton.setText("Confirm");
        currencyLabel.setText("Select currency: ");
        inputFrame.add(backgroundJP);
        backgroundJP.add(currencyLabel);
        backgroundJP.add(currencyCBox);
        backgroundJP.add(currencyTF);
        backgroundJP.add(confirmButton);
        currencyTF.setPreferredSize(new Dimension(100, 50));
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBTNActionPerformed(evt, currencyCBox, currencyTF);
                inputFrame.dispose();
            }
        });
        inputFrame.setVisible(true);
    }//GEN-LAST:event_addBTNActionPerformed

    private void updateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBTNActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog("Enter coin name: ");
        String quantity = JOptionPane.showInputDialog("Enter new quantity: ");
        mongoManager.updateRecord(name, quantity);
        refreshDisplay();
    }//GEN-LAST:event_updateBTNActionPerformed

    private void deleteBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBTNActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog("Enter coin name: ");
        mongoManager.deleteRecord(name);
        refreshDisplay();
    }//GEN-LAST:event_deleteBTNActionPerformed

    private void backBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTNActionPerformed
        if (loginGUI != null) {
            loginGUI.saveDatabaseLocally();
        } else {
            System.out.println("loginGUI is null");
        }

        if (guiManager != null) {
            guiManager.loadFrame("summaryFrame");
        } else {
            System.out.println("guiManager is null");
        }
    }//GEN-LAST:event_backBTNActionPerformed

    public void confirmBTNActionPerformed(java.awt.event.ActionEvent evt, javax.swing.JComboBox cBox,
            javax.swing.JTextField tField) {
        mongoManager.createRecord(Float.parseFloat(tField.getText()), cBox.getSelectedItem().toString());
        refreshDisplay();
    }
    
    public void refreshDisplay() {
        editorTA.setText(mongoManager.readRecords(mongoManager.getCollection(), APIManager.getObject(), APIManager.getJSONLength()
            ,APIManager.getPointers(), APIManager.getValues()));
        System.out.println(APIManager.getPointers());
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
            java.util.logging.Logger.getLogger(EditorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditorGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBTN;
    private javax.swing.JButton backBTN;
    private javax.swing.JButton deleteBTN;
    private javax.swing.JLabel editorLBL;
    private javax.swing.JScrollBar editorSB;
    private javax.swing.JTextArea editorTA;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton updateBTN;
    // End of variables declaration//GEN-END:variables
}
