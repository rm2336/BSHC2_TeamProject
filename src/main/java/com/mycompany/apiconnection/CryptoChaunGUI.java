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
import com.mongodb.client.result.InsertOneResult;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.JOptionPane;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author rokom
 */
public class CryptoChaunGUI extends javax.swing.JFrame {
    private String API_key;
    private boolean isConnected = false;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private MongoClientSettings settings;
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
        apiBTN = new javax.swing.JButton();
        fetchBTN = new javax.swing.JButton();
        chartBTN = new javax.swing.JButton();
        createBTN = new javax.swing.JButton();

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

        apiBTN.setText("Enter API Key");
        apiBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apiBTNActionPerformed(evt);
            }
        });

        fetchBTN.setText("Fetch API");
        fetchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fetchBTNActionPerformed(evt);
            }
        });

        chartBTN.setText("Chart");
        chartBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chartBTNActionPerformed(evt);
            }
        });

        createBTN.setText("Insert Record");
        createBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBTNActionPerformed(evt);
            }
        });

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
                                .addGap(41, 41, 41)
                                .addComponent(apiBTN)
                                .addGap(35, 35, 35)
                                .addComponent(fetchBTN)
                                .addGap(34, 34, 34)
                                .addComponent(chartBTN)
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
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(progressPB, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(createBTN))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        backgroundJPLayout.setVerticalGroup(
            backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundJPLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundJPLayout.createSequentialGroup()
                        .addComponent(createBTN)
                        .addGap(76, 76, 76)
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)))
                .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectBTN)
                    .addComponent(exitBTN)
                    .addComponent(apiBTN)
                    .addComponent(fetchBTN)
                    .addComponent(chartBTN))
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

    private String makeAPICall(String uri, List<NameValuePair> parameters)
        throws URISyntaxException, IOException {
        String response_content = "";
        
        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);
        
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());
        
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", API_key);
        
        CloseableHttpResponse response = client.execute(request);
        
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        
        return response_content;
    }
    private void exitBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBTNActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_exitBTNActionPerformed

    private void connectBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectBTNActionPerformed
        // TODO add your handling code here:
        
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
        settings = null;
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
        MongoClient mongoClient = MongoClients.create(settings);
            try {
                // Send a ping to confirm a successful connection
                database = mongoClient.getDatabase("crypto_database");
                database.runCommand(new Document("ping", 1));
                collection = database.getCollection("crypto_stats");
                progressPB.setValue(75);
                Document output = collection.find(eq("currency", "BitCoin")).first();
                displayTA.setText(output.toString());
                progressPB.setValue(100);
                JOptionPane.showMessageDialog(null, "Pinged your deployment. You successfully connected to MongoDB!");
                isConnected = true;
                this.mongoClient = mongoClient;
            } catch (MongoException e) {
                JOptionPane.showMessageDialog(null, e);
                progressPB.setValue(0);
            }
    }//GEN-LAST:event_connectBTNActionPerformed

    private void passwordPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordPFActionPerformed

    private void apiBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apiBTNActionPerformed
        // TODO add your handling code here:
        API_key = JOptionPane.showInputDialog(null, "Enter your API key: ");
        String uri = "https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("start", "1"));
        parameters.add(new BasicNameValuePair("limit", "5000"));
        parameters.add(new BasicNameValuePair("convert", "USD"));
        try {
            String result = makeAPICall(uri, parameters);
            System.out.println(result);
        }
        catch(IOException e) {
            System.out.println(e);
        }
        catch (URISyntaxException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_apiBTNActionPerformed

    private void chartBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chartBTNActionPerformed
        // TODO add your handling code here:
        DefaultPieDataset myPieDataset = new DefaultPieDataset();
        myPieDataset.setValue("One", usernameTF.getText().length());
        myPieDataset.setValue("Two", passwordPF.getPassword().toString().length());
        JFreeChart myChart = ChartFactory.createPieChart("Pie Chart", myPieDataset, rootPaneCheckingEnabled, rootPaneCheckingEnabled, Locale.ENGLISH);
        ChartFrame chartFrame = new ChartFrame("Pie Chart", myChart);
        chartFrame.setVisible(true);
        chartFrame.setSize(400, 500);
    }//GEN-LAST:event_chartBTNActionPerformed

    private void fetchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fetchBTNActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_fetchBTNActionPerformed

    private void createBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBTNActionPerformed
        // TODO add your handling code here:
        if (isConnected) {
            String currency = JOptionPane.showInputDialog(null, "Enter currency name: ");
            String value = JOptionPane.showInputDialog(null, "Enter currency value: ");
            database = mongoClient.getDatabase("crypto_database");
            Document newCurrency = new Document("_id", new ObjectId())
                    .append("currency", currency)
                    .append("value", value);
            InsertOneResult result = collection.insertOne(newCurrency);
            BsonValue id = result.getInsertedId();
            displayTA.setText("New document id: " + id);
        }
    }//GEN-LAST:event_createBTNActionPerformed

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
    private javax.swing.JButton apiBTN;
    private javax.swing.JPanel backgroundJP;
    private javax.swing.JButton chartBTN;
    private javax.swing.JButton connectBTN;
    private javax.swing.JButton createBTN;
    private javax.swing.JTextArea displayTA;
    private javax.swing.JButton exitBTN;
    private javax.swing.JButton fetchBTN;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel passwordLBL;
    private javax.swing.JPasswordField passwordPF;
    private javax.swing.JProgressBar progressPB;
    private javax.swing.JLabel usernameLBL;
    private javax.swing.JTextField usernameTF;
    // End of variables declaration//GEN-END:variables
}
