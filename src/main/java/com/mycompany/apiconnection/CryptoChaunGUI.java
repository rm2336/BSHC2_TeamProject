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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.and;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.exclude;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.JFrame;

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
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;

/**
 *
 * @author rokom
 */
public class CryptoChaunGUI extends javax.swing.JFrame {
    private String API_key;
    private String result;
    private boolean isConnected = false;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private MongoClientSettings settings;
    private ArrayList<JSONPointer> pointers = new ArrayList<>();
    private ArrayList<JSONPointer> values = new ArrayList<>();
    private ArrayList<Double> totalValues = new ArrayList<>();
    private JSONObject object;
    private int jsonListSize = 30;
    private List<SyndEntryImpl> newsEntries;
    private int articleIndex = -1;
    /**
     * Creates new form CryptoChaunGUI
     */
    public CryptoChaunGUI() {
        initComponents();
        
            // Load credentials if available
    String[] savedCredentials = CredentialManager.loadCredentials();
    if (savedCredentials != null) {
        usernameTF.setText(savedCredentials[0]);
        passwordPF.setText(savedCredentials[1]);
        clusterTF.setText(savedCredentials[2]);
    }
        
        // read contents of the stats file if it exists
        try {
            File theFile = new File("stats.json");
            if (theFile.exists()) {
                FileReader fr = new FileReader("stats.json"); 
                BufferedReader br = new BufferedReader(fr);
                result = br.readLine();
                br.close();
                System.out.println(result);
                System.out.println("Json has " + jsonListSize + " record(s).");
                object = new JSONObject(result);
                for (int i = 0; i < jsonListSize; i++) {
                    JSONPointer temp = JSONPointer.builder()
                            .append("data")
                            .append(i)
                            .append("name")
                            .build();
                    pointers.add(temp);
                    System.out.println("Adding " + temp.toString());
                    JSONPointer temp2 = JSONPointer.builder()
                            .append("data")
                            .append(i)
                            .append("quote")
                            .append("EUR")
                            .append("price")
                            .build();
                    System.out.println("Adding " + temp2.toString());
                    values.add(temp2);
                }
                // query the JSON file and display the results
                displayTA.setText("");
                for (int i = 0; i < jsonListSize; i++) {
                    displayTA.append(object.query(pointers.get(i)).toString());
                    displayTA.append(" Price: €" + object.query(values.get(i)).toString() + "\n");
                }            
            }       
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void setNewsEntries(List<SyndEntryImpl> list) {
        newsEntries = list;
    }
    
    public String readCollection(MongoCollection<Document> collection) {
        FindIterable<Document> output = collection.find(new Document()).projection(exclude("_id"));
        List<Document> results = new ArrayList<>();
        output.into(results);
        String theData = "";
        double price = 0;
        for (int i = 0; i < results.size(); i++) {
            System.out.println("i = " + i + ": " + results.get(i).getString("currency"));
            theData += "Coin: " + results.get(i).getString("currency") + " Quantity: " +
            results.get(i).getString("quantity") + "\n";
            // query JSON object for price
            if (object != null)
            for (int j = 0; j < jsonListSize; j++) {
                if (object.query(pointers.get(j).toString()).equals(results.get(i).getString("currency"))) {
                    price = Double.valueOf(object.query(values.get(j)).toString()) * Double.valueOf(results.get(i).getString("quantity"));
                    System.out.println("Value: " + object.query(values.get(j).toString()));
                    System.out.println("Quantity: " + results.get(i).getString("quantity"));
                    
                    theData += "Total Value: €" + BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP) + "\n";
                    totalValues.add(price);
                }
            }
        }
        return theData;
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
        clusterTF = new javax.swing.JTextField();
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
        readBTN = new javax.swing.JButton();
        updateBTN = new javax.swing.JButton();
        deleteBTN = new javax.swing.JButton();
        clusterLBL = new javax.swing.JLabel();
        usernameTF = new javax.swing.JTextField();
        newsBTN = new javax.swing.JButton();
        nextArticleBTN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backgroundJP.setBackground(new java.awt.Color(0, 102, 255));

        usernameLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        usernameLBL.setForeground(new java.awt.Color(255, 255, 255));
        usernameLBL.setText("Username:");

        passwordLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        passwordLBL.setForeground(new java.awt.Color(255, 255, 255));
        passwordLBL.setText("Password:");

        clusterTF.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        clusterTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clusterTFActionPerformed(evt);
            }
        });

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

        readBTN.setText("Refresh Collection");
        readBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readBTNActionPerformed(evt);
            }
        });

        updateBTN.setText("Update");
        updateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBTNActionPerformed(evt);
            }
        });

        deleteBTN.setText("Delete Record");
        deleteBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBTNActionPerformed(evt);
            }
        });

        clusterLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        clusterLBL.setForeground(new java.awt.Color(255, 255, 255));
        clusterLBL.setText("Cluster:");

        usernameTF.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        newsBTN.setText("Get News");
        newsBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newsBTNActionPerformed(evt);
            }
        });

        nextArticleBTN.setText(">");
        nextArticleBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextArticleBTNActionPerformed(evt);
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
                            .addComponent(usernameLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, backgroundJPLayout.createSequentialGroup()
                                .addComponent(updateBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))
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
                                .addContainerGap(202, Short.MAX_VALUE))
                            .addGroup(backgroundJPLayout.createSequentialGroup()
                                .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(backgroundJPLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(deleteBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(backgroundJPLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(passwordPF, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                            .addComponent(usernameTF))))
                                .addGap(18, 131, Short.MAX_VALUE)
                                .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(backgroundJPLayout.createSequentialGroup()
                                        .addComponent(newsBTN)
                                        .addGap(18, 18, 18)
                                        .addComponent(nextArticleBTN)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(backgroundJPLayout.createSequentialGroup()
                                        .addComponent(jScrollPane1)
                                        .addGap(35, 35, 35))))))
                    .addGroup(backgroundJPLayout.createSequentialGroup()
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundJPLayout.createSequentialGroup()
                                .addComponent(clusterLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clusterTF, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(progressPB, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(backgroundJPLayout.createSequentialGroup()
                                .addComponent(createBTN)
                                .addGap(18, 18, 18)
                                .addComponent(readBTN)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        backgroundJPLayout.setVerticalGroup(
            backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundJPLayout.createSequentialGroup()
                .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundJPLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newsBTN)
                            .addComponent(nextArticleBTN))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(backgroundJPLayout.createSequentialGroup()
                        .addContainerGap(50, Short.MAX_VALUE)
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(createBTN)
                            .addComponent(readBTN))
                        .addGap(18, 18, 18)
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(updateBTN)
                            .addComponent(deleteBTN))
                        .addGap(35, 35, 35)
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usernameLBL)
                            .addComponent(usernameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(passwordLBL)
                            .addComponent(passwordPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clusterLBL)
                            .addComponent(clusterTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)))
                .addComponent(progressPB, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
        
    // URIBuilder is used to safely construct a URL with query parameters.
        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);
        
    // CloseableHttpClient comes from Apache’s HTTP library.
    // It’s "closeable" to release network resources once done 
    // — avoiding memory leaks.
        // It creates an HTTP client for sending requests.
        CloseableHttpClient client = HttpClients.createDefault();
    // HttpGet sets up an HTTP GET request
    // — used to retrieve data from the API.
        //query.build() finalizes the URI with all query parameters.
        HttpGet request = new HttpGet(query.build());
        
    // Headers tell the API what kind of request this is.
    // Accept HTTP header defines a data type expected in a response sent from the server.
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        // API key for authentication
        request.addHeader("X-CMC_PRO_API_KEY", API_key);
        
        // client.execute(request) sends the HTTP request and waits for the server’s response.
        // The response is stored in CloseableHttpResponse.
        CloseableHttpResponse response = client.execute(request);
        
        try {
            // print status line like 200 = success!
            System.out.println(response.getStatusLine());
            
            //gets the Http respones body where the json data lives
            HttpEntity entity = response.getEntity();
            
                // converts the response body into a String
            // Contains the API’s JSON response as a string
            response_content = EntityUtils.toString(entity);
            
            // Ensures the HTTP entity (response body) is fully consumed and cleaned up.
            // It prevents resource leaks — a crucial step when handling network connections.
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        // The method returns the API response body as a String.
        // This allows the calling method to parse the JSON data.
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
        String clusterName = "";
        
        // Load saved credentials if available
        String[] savedCredentials = CredentialManager.loadCredentials();
        if(savedCredentials != null){
            //load credentials into variables
            user = savedCredentials[0];
            password = savedCredentials[1];
            clusterName = savedCredentials[2];
            
//            //set it to the textfield
//            usernameTF.setText(user);
//            passwordPF.setText(password);
//            clusterTF.setText(clusterName);
        }else{
            try {
            user = URLEncoder.encode(usernameTF.getText(), "UTF-8");
            password = URLEncoder.encode(passwordPF.getText(), "UTF-8");
            clusterName = URLEncoder.encode(clusterTF.getText(), "UTF-8");
            
        } catch (UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null,e);
            progressPB.setValue(0);
        }
    }
        
        progressPB.setValue(25);
        settings = null;

        try {
        String connectionString = "mongodb+srv://" + user + ":" + password + "@" + clusterName + ".mongodb.net/?retryWrites=true&w=majority&appName=myCluster";
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
        if (settings != null) {
            MongoClient mongoClient = MongoClients.create(settings);
            try {
                // Send a ping to confirm a successful connection
                database = mongoClient.getDatabase("crypto_database");
                database.runCommand(new Document("ping", 1));
                collection = database.getCollection("crypto_stats");
                progressPB.setValue(75);
                displayTA.setText(readCollection(collection));
                
                // Save the database backup locally
                saveDatabaseLocally();
                
                progressPB.setValue(100);
                JOptionPane.showMessageDialog(null, "Pinged your deployment. You successfully connected to MongoDB!");
                
                isConnected = true;
                this.mongoClient = mongoClient;
                
                // Save credentials after successful connection
                CredentialManager.saveCredentials(user, password, clusterName);
            } catch (MongoException e) {
                JOptionPane.showMessageDialog(null, e);
                progressPB.setValue(0);
            }
        }
    }//GEN-LAST:event_connectBTNActionPerformed

    public void saveDatabaseLocally() {
        if (collection == null){
            JOptionPane.showMessageDialog(null, "Not connected to MongoDB!");
            return;
        }
      
        // Write to "crypto_backup.json"
        try (FileWriter file = new FileWriter("crypto_backup.json")){
            // retrieves all documents from the MongoDB collection exclude("_id")
            FindIterable<Document> output = collection.find().projection(exclude("_id"));
            List<Document> results = new ArrayList<>();
            output.into(results);
            
            // Initializes a new JSONArray
            JSONArray jsonArray = new JSONArray();
            for (Document doc : results){
                jsonArray.put(new JSONObject(doc.toJson()));
            }
            
            file.write(jsonArray.toString(4));
            file.flush();
            JOptionPane.showMessageDialog(null, "Database backup saved successfully!");
            
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "Error saving database: " + e.getMessage());
        }
    }
    
    
    private void passwordPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordPFActionPerformed

    private void apiBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apiBTNActionPerformed
        // TODO add your handling code here:
        API_key = JOptionPane.showInputDialog(null, "Enter your API key: ");
        if (API_key.equals(""))
            API_key = "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c";
    }//GEN-LAST:event_apiBTNActionPerformed

    private void chartBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chartBTNActionPerformed
        // TODO add your handling code here:
        DefaultPieDataset myPieDataset = new DefaultPieDataset();
        myPieDataset.clear();
        FindIterable<Document> output = collection.find(new Document()).projection(exclude("_id"));
        List<Document> results = new ArrayList<>();
        output.into(results);
        object = new JSONObject(result);
        totalValues.clear();
        // refresh values
        double price = 0;
        if (object != null) {
            for (int i = 0; i < results.size(); i++) {
                for (int j = 0; j < jsonListSize; j++) {
                    if (object.query(pointers.get(j).toString()).equals(results.get(i).getString("currency"))) {
                        price = Double.valueOf(object.query(values.get(j)).toString()) * Double.valueOf(results.get(i).getString("quantity"));
                        totalValues.add(price);
                    }
                }
            }
        }
        for (int i = 0; i < results.size(); i++) {
            myPieDataset.setValue(results.get(i).getString("currency"), BigDecimal.valueOf(totalValues.get(i)).setScale(2, RoundingMode.HALF_EVEN));
            }
        JFreeChart myChart = ChartFactory.createPieChart("My Crypto Portfolio", myPieDataset, rootPaneCheckingEnabled, rootPaneCheckingEnabled, Locale.ENGLISH);
        ChartFrame chartFrame = new ChartFrame("Chart", myChart);
        chartFrame.setVisible(true);
        chartFrame.setSize(400, 500);
    }//GEN-LAST:event_chartBTNActionPerformed

    private void fetchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fetchBTNActionPerformed
        // TODO add your handling code here:
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        // String uri = "https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("start", "1"));
        parameters.add(new BasicNameValuePair("limit", String.valueOf(jsonListSize)));
        parameters.add(new BasicNameValuePair("convert", "EUR"));
        try {
            String result = makeAPICall(uri, parameters);
//            result = "{\"status\":{\"timestamp\":\"2025-02-12T10:21:53.288Z\""
//                    + ",\"error_code\":0,\"error_message\":null,\"elapsed\":16,\"credit_count"
//                    + "\":1,\"notice\":null,\"total_count\":10859},\"data\":[{\"id\":1,\"name"
//                    + "\":\"Bitcoin\",\"symbol\":\"BTC\",\"slug\":\"bitcoin\",\"num_market_pairs"
//                    + "\":11937,\"date_added\":\"2010-07-13T00:00:00.000Z\",\"tags\":[\"mineable"
//                    + "\",\"pow\",\"sha-256\",\"store-of-value\",\"state-channel\",\"coinbase-ventures-portfolio"
//                    + "\",\"three-arrows-capital-portfolio\",\"polychain-capital-portfolio\",\"b"
//                    + "inance-labs-portfolio\",\"blockchain-capital-portfolio\",\"boostvc-portfo"
//                    + "lio\",\"cms-holdings-portfolio\",\"dcg-portfolio\",\"dragonfly-capital-po"
//                    + "rtfolio\",\"electric-capital-portfolio\",\"fabric-ventures-portfolio\",\"fram"
//                    + "ework-ventures-portfolio\",\"galaxy-digital-portfolio\",\"huobi-capi"
//                    + "tal-portfolio\",\"alameda-research-portfolio\",\"a16z-portfolio\",\"1con"
//                    + "firmation-portfolio\",\"winklevoss-capital-portfolio\",\"usv-portf"
//                    + "olio\",\"placeholder-ventures-portfolio\",\"pantera-capital-portfo"
//                    + "lio\",\"multicoin-capital-portfolio\",\"paradigm-portfolio\",\"bitcoin-ecosyste"
//                    + "m\",\"ftx-bankruptcy-estate\",\"2017-2018-alt-season\"],\"max_supply\":210000"
//                    + "00,\"circulating_supply\":19823146,\"total_supply\":19823146,\"infinite_sup"
//                    + "ply\":false,\"platform\":null,\"cmc_rank\":1,\"self_reported_circulating_su"
//                    + "pply\":null,\"self_reported_market_cap\":null,\"tvl_ratio\":null,\"last_up"
//                    + "dated\":\"2025-02-12T10:19:00.000Z\",\"quote\":{\"EUR\":{\"price\":92623.7937"
//                    + "1037334,\"volume_24h\":35589018549.38164,\"volume_change_24h\":10.5158,\"perc"
//                    + "ent_change_1h\":-0.13046824,\"percent_change_24h\":-2.08835051,\"percent_cha"
//                    + "nge_7d\":-1.68261272,\"percent_change_30d\":4.08719941,\"percent_change_60d\":-5.20"
//                    + "268572,\"percent_change_90d\":5.22467304,\"market_cap\":18360949857"
//                    + "94.6128,\"market_cap_dominance\":60.2756,\"fully_diluted_market_cap\":19450996"
//                    + "67917.8447,\"tvl\":null,\"last_updated\":\"2025-02-12T10:19:58."
//                    + "000Z\"}}},{\"id\":1027,\"name\":\"Ethereum\",\"symbol\":\"ETH\",\"slug\":\"ethe"
//                    + "reum\",\"num_market_pairs\":9926,\"date_added\":\"2015-08-07T00:00:00"
//                    + ".000Z\",\"tags\":[\"pos\",\"smart-contracts\",\"ethereum-ecosystem\",\"coinbase-ve"
//                    + "ntures-portfolio\",\"three-arrows-capital-portfolio\",\"polychain-capi"
//                    + "tal-portfolio\",\"heco-ecosystem\",\"binance-labs-portfolio\",\"avalanch"
//                    + "e-ecosystem\",\"solana-ecosystem\",\"blockchain-capital-portfolio\",\"boo"
//                    + "stvc-portfolio\",\"cms-holdings-portfolio\",\"dcg-portfolio\",\"dragon"
//                    + "fly-capital-portfolio\",\"electric-capital-portfolio\",\"fabric-ventur"
//                    + "es-portfolio\",\"framework-ventures-portfolio\",\"hashkey-capital-port"
//                    + "folio\",\"kenetic-capital-portfolio\",\"huobi-capital-portfolio\",\"alamed"
//                    + "a-research-portfolio\",\"a16z-portfolio\",\"1confirmation-portfol"
//                    + "io\",\"winklevoss-capital-portfolio\",\"usv-portfolio\",\"placeholder-vent"
//                    + "ures-portfolio\",\"pantera-capital-portfolio\",\"multicoin-capital-por"
//                    + "tfolio\",\"paradigm-portfolio\",\"tezos-ecosystem\",\"near-protocol-ec"
//                    + "osystem\",\"bnb-chain-ecosystem\",\"velas-ecosystem\",\"ethereum-pow-e"
//                    + "cosystem\",\"osmosis-ecosystem\",\"layer-1\",\"ftx-bankruptcy-est"
//                    + "ate\",\"zksync-era-ecosystem\",\"viction-ecosystem\",\"klaytn-ecosystem\",\"sora-e"
//                    + "cosystem\",\"rsk-rbtc-ecosystem\",\"starknet-ecosystem\"],\"max_supp"
//                    + "ly\":null,\"circulating_supply\":120543070.67367822,\"total_supply\":1205"
//                    + "43070.67367822,\"infinite_supply\":true,\"platform\":null,\"cmc_rank\":2,\"sel"
//                    + "f_reported_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_ra"
//                    + "tio\":null,\"last_updated\":\"2025-02-12T10:20:00.000Z\",\"quote\":{\"EUR\":{\"pr"
//                    + "ice\":2529.899929532977,\"volume_24h\":19417050040.993183,\"volume_change_24h\":10."
//                    + "0753,\"percent_change_1h\":0.23805787,\"percent_change_24h\":-2.96479608,\"perce"
//                    + "nt_change_7d\":-5.30304269,\"percent_change_30d\":-16.42938681,\"percent_chang"
//                    + "e_60d\":-32.56823931,\"percent_change_90d\":-18.22992377,\"market_cap\":3049619"
//                    + "06003.02716,\"market_cap_dominance\":10.0136,\"fully_diluted_market_cap\":3049"
//                    + "61906003.02795,\"tvl\":null,\"last_updated\":\"2025-02-12T10:19:"
//                    + "58.000Z\"}}},{\"id\":825,\"name\":\"Tether USDt\",\"symbol\":\"USDT\",\"slu"
//                    + "g\":\"tether\",\"num_market_pairs\":116303,\"date_added\":\"2015-02-25T0"
//                    + "0:00:00.000Z\",\"tags\":[\"stablecoin\",\"asset-backed-stablecoin\",\"ethere"
//                    + "um-ecosystem\",\"waves-ecosystem\",\"bitcoin-cash-ecosystem\",\"heco-ecosy"
//                    + "stem\",\"algorand-ecosystem\",\"avalanche-ecosystem\",\"solana-ecosys"
//                    + "tem\",\"polygon-ecosystem\",\"fantom-ecosystem\",\"terra-ecosystem\",\"tezo"
//                    + "s-ecosystem\",\"near-protocol-ecosystem\",\"arbitrum-ecosystem\",\"celo-"
//                    + "ecosystem\",\"iotex-ecosystem\",\"zilliqa-ecosystem\",\"harmony-ecosy"
//                    + "stem\",\"moonriver-ecosystem\",\"cronos-ecosystem\",\"injective-ecosystem\",\"bnb-"
//                    + "chain-ecosystem\",\"oasis-ecosystem\",\"moonbeam-ecosystem\",\"usd-stablec"
//                    + "oin\",\"xdc-ecosystem\",\"everscale-ecosystem\",\"velas-ecosystem\",\"doge-ch"
//                    + "ain-ecosystem\",\"ethereum-pow-ecosystem\",\"aptos-ecosystem\",\"sui-ecos"
//                    + "ystem\",\"optimism-ecosystem\",\"canto-ecosystem\",\"osmosis-ecosystem\",\"zksy"
//                    + "nc-era-ecosystem\",\"pulsechain-ecosystem\",\"sei-ecosystem\",\"toncoin-ecosy"
//                    + "stem\",\"fiat-stablecoin\",\"viction-ecosystem\",\"gnosis-chain-ecosystem\",\"klaytn"
//                    + "-ecosystem\",\"okexchain-ecosystem\",\"conflux-ecosystem\",\"kcc-ecosyst"
//                    + "em\",\"tron20-ecosystem\",\"kardiachain-ecosystem\",\"rsk-rbtc-ecosystem\",\"telo"
//                    + "s-ecosystem\",\"boba-network-ecosystem\",\"fusion-network-ecosystem\",\"hoo-sma"
//                    + "rt-chain-ecosystem\",\"secret-ecosystem\",\"aurora-ecosystem\",\"metis-androm"
//                    + "eda-ecosystem\",\"meter-ecosystem\",\"fuse-ecosystem\",\"syscoin-ecosys"
//                    + "tem\",\"milkomeda-ecosystem\",\"bitgert-ecosystem\",\"astar-ecosy"
//                    + "stem\",\"cube-network-ecosystem\",\"thundercore-ecosystem\",\"redlight-cha"
//                    + "in-ecosystem\",\"core-ecosystem\",\"polygon-zkevm-ecosystem\",\"eos-evm-eco"
//                    + "system\",\"starknet-ecosystem\",\"mantle-ecosystem\",\"neon-evm-ecos"
//                    + "ystem\",\"manta-pacific-ecosystem\",\"scroll-ecosystem\",\"x-layer-eco"
//                    + "system\"],\"max_supply\":null,\"circulating_supply\":141953250507.84"
//                    + "326,\"total_supply\":143570568759.4433,\"platform\":{\"id\":1027,\"na"
//                    + "me\":\"Ethereum\",\"symbol\":\"ETH\",\"slug\":\"ethereum\",\"token_add"
//                    + "ress\":\"0xdac17f958d2ee523a2206206994597c13d831ec7\"},\"infinite_sup"
//                    + "ply\":true,\"cmc_rank\":3,\"self_reported_circulating_supply\":null,\"self_re"
//                    + "ported_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2025-02-12T1"
//                    + "0:19:00.000Z\",\"quote\":{\"EUR\":{\"price\":0.9643960927446409,\"volume_24h\":76535"
//                    + "942674.24454,\"volume_change_24h\":3.6044,\"percent_change_1h\":0.03283513,\"perc"
//                    + "ent_change_24h\":0.0097385,\"percent_change_7d\":-0.01100815,\"percent_chang"
//                    + "e_30d\":0.12191477,\"percent_change_60d\":0.0276032,\"percent_change_90d\":-0.0"
//                    + "1096483,\"market_cap\":136899160142.16525,\"market_cap_dominance\":4.4941,\"fu"
//                    + "lly_diluted_market_cap\":138458895544.73346,\"tvl\":null,\"last_updated\":\"2025"
//                    + "-02-12T10:19:58.000Z\"}}},{\"id\":52,\"name\":\"XRP\",\"symbol\":\"XRP\",\"slug\":\"x"
//                    + "rp\",\"num_market_pairs\":1528,\"date_added\":\"2013-08-04T00:00:00.000Z\",\"tag"
//                    + "s\":[\"medium-of-exchange\",\"enterprise-solutions\",\"xrp-ecosystem\",\"arrington-xr"
//                    + "p-capital-portfolio\",\"galaxy-digital-portfolio\",\"a16z-portfolio\",\"pantera-capit"
//                    + "al-portfolio\",\"bnb-chain-ecosystem\",\"ftx-bankruptcy-estate\",\"2017-2018-alt-sea"
//                    + "son\",\"klaytn-ecosystem\",\"made-in-america\"],\"max_supply\":100000000000,\"circu"
//                    + "lating_supply\":57762545657,\"total_supply\":99986504676,\"infinite_supply\":fa"
//                    + "lse,\"platform\":null,\"cmc_rank\":4,\"self_reported_circulating_supply\":null,\"self_rep"
//                    + "orted_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2025-02-12T10:20:0"
//                    + "0.000Z\",\"quote\":{\"EUR\":{\"price\":2.3259854463746072,\"volume_24h\":421"
//                    + "9411365.1087613,\"volume_change_24h\":-2.8114,\"percent_change_1h\":0.108808"
//                    + "92,\"percent_change_24h\":-3.19115417,\"percent_change_7d\":-4.15208144,\"perce"
//                    + "nt_change_30d\":-1.14142498,\"percent_change_60d\":-1.49178823,\"percent_ch"
//                    + "ange_90d\":238.64824356,\"market_cap\":134354840543.73079,\"market_cap_domin"
//                    + "ance\":4.4124,\"fully_diluted_market_cap\":232598544637.45782,\"tvl\":null,\"last"
//                    + "_updated\":\"2025-02-12T10:19:58.000Z\"}}},{\"id\":5426,\"name\":\"Sola"
//                    + "na\",\"symbol\":\"SOL\",\"slug\":\"solana\",\"num_market_pairs\":840,\"date_a"
//                    + "dded\":\"2020-04-10T00:00:00.000Z\",\"tags\":[\"pos\",\"platform\",\"sola"
//                    + "na-ecosystem\",\"cms-holdings-portfolio\",\"kenetic-capital-portfol"
//                    + "io\",\"alameda-research-portfolio\",\"multicoin-capital-portfolio\",\"okx-vent"
//                    + "ures-portfolio\",\"layer-1\",\"ftx-bankruptcy-estate\",\"alleged-sec-secur"
//                    + "ities\",\"cmc-crypto-awards-2024\",\"made-in-america\"],\"max_sup"
//                    + "ply\":null,\"circulating_supply\":488184434.00489986,\"total_supply\":5939261"
//                    + "64.8848802,\"infinite_supply\":true,\"platform\":null,\"cmc_rank\":5,\"self_repor"
//                    + "ted_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_rat"
//                    + "io\":null,\"last_updated\":\"2025-02-12T10:20:00.000Z\",\"quote\":{\"EUR\":{\"price\":189.1401"
//                    + "7185157934,\"volume_24h\":2933773149.2095013,\"volume_change_24h\":-23.3879,\"perce"
//                    + "nt_change_1h\":0.32252199,\"percent_change_24h\":-3.08539593,\"percent_change_7d\":-4.0"
//                    + "9243523,\"percent_change_30d\":9.45783297,\"percent_change_60d\":-12.89289244,\"percent_cha"
//                    + "nge_90d\":-11.28280626,\"market_cap\":92335287742.95274,\"market_cap_dominance\":3.0313,\"fu"
//                    + "lly_diluted_market_cap\":112335296893.47287,\"tvl\":null,\"las"
//                    + "t_updated\":\"2025-02-12T10:19:58.000Z\"}}},{\"id\":1839,\"name\":\"BNB\",\"symb"
//                    + "ol\":\"BNB\",\"slug\":\"bnb\",\"num_market_pairs\":2355,\"date_added\":\"2017-07-25T00:00:00.000Z\",\"tags\":[\"marketplace\",\"centralized-exchange\",\"payments\",\"smart-contracts\",\"ethereum-ecosystem\",\"alameda-research-portfolio\",\"multicoin-capital-portfolio\",\"bnb-chain-ecosystem\",\"layer-1\",\"alleged-sec-securities\",\"celsius-bankruptcy-estate\"],\"max_supply\":null,\"circulating_supply\":142479384,\"total_supply\":142479384,\"infinite_supply\":false,\"platform\":null,\"cmc_rank\":6,\"self_reported_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2025-02-12T10:20:00.000Z\",\"quote\":{\"EUR\":{\"price\":626.2297743984873,\"volume_24h\":1931946397.404982,\"volume_change_24h\":1.0264,\"percent_change_1h\":0.93765914,\"percent_change_24h\":1.40680567,\"percent_change_7d\":13.79168523,\"percent_change_30d\":-3.11255278,\"percent_change_60d\":-9.91335935,\"percent_change_90d\":1.15991383,\"market_cap\":89224832498.75543,\"market_cap_dominance\":2.9292,\"fully_diluted_market_cap\":89224832498.75667,\"tvl\":null,\"last_updated\":\"2025-02-12T10:19:58.000Z\"}}},{\"id\":3408,\"name\":\"USDC\",\"symbol\":\"USDC\",\"slug\":\"usd-coin\",\"num_market_pairs\":25633,\"date_added\":\"2018-10-08T00:00:00.000Z\",\"tags\":[\"medium-of-exchange\",\"stablecoin\",\"asset-backed-stablecoin\",\"ethereum-ecosystem\",\"waves-ecosystem\",\"bitcoin-cash-ecosystem\",\"wanchain-ecosystem\",\"coinbase-ventures-portfolio\",\"algorand-ecosystem\",\"avalanche-ecosystem\",\"solana-ecosystem\",\"hedera-hashgraph-ecosystem\",\"polygon-ecosystem\",\"terra-ecosystem\",\"near-protocol-ecosystem\",\"arbitrum-ecosystem\",\"celo-ecosystem\",\"iotex-ecosystem\",\"harmony-ecosystem\",\"moonriver-ecosystem\",\"cronos-ecosystem\",\"injective-ecosystem\",\"bnb-chain-ecosystem\",\"oasis-ecosystem\",\"moonbeam-ecosystem\",\"usd-stablecoin\",\"everscale-ecosystem\",\"velas-ecosystem\",\"doge-chain-ecosystem\",\"ethereum-pow-ecosystem\",\"aptos-ecosystem\",\"sui-ecosystem\",\"optimism-ecosystem\",\"canto-ecosystem\",\"osmosis-ecosystem\",\"zksync-era-ecosystem\",\"pulsechain-ecosystem\",\"base-ecosystem\",\"sei-ecosystem\",\"multiversx-ecosystem\",\"fiat-stablecoin\",\"viction-ecosystem\",\"gnosis-chain-ecosystem\",\"okexchain-ecosystem\",\"conflux-ecosystem\",\"kcc-ecosystem\",\"tron20-ecosystem\",\"kardiachain-ecosystem\",\"telos-ecosystem\",\"ronin-ecosystem\",\"boba-network-ecosystem\",\"kava-ecosystem\",\"secret-ecosystem\",\"aurora-ecosystem\",\"metis-andromeda-ecosystem\",\"meter-ecosystem\",\"fuse-ecosystem\",\"elastos-ecosystem\",\"syscoin-ecosystem\",\"milkomeda-ecosystem\",\"evmos-ecosystem\",\"bitgert-ecosystem\",\"astar-ecosystem\",\"thundercore-ecosystem\",\"tomb-chain-ecosystem\",\"wemix-ecosystem\",\"sx-network-ecosystem\",\"godwoken-ecosystem\",\"energi-ecosystem\",\"core-ecosystem\",\"polygon-zkevm-ecosystem\",\"starknet-ecosystem\",\"mantle-ecosystem\",\"neon-evm-ecosystem\",\"scroll-ecosystem\",\"shido-network-ecosystem\",\"made-in-america\"],\"max_supply\":null,\"circulating_supply\":56070979185.85878,\"total_supply\":56070979185.85878,\"platform\":{\"id\":1027,\"name\":\"Ethereum\",\"symbol\":\"ETH\",\"slug\":\"ethereum\",\"token_address\":\"0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48\"},\"infinite_supply\":false,\"cmc_rank\":7,\"self_reported_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2025-02-12T10:20:00.000Z\",\"quote\":{\"EUR\":{\"price\":0.9641145042026845,\"volume_24h\":6971833964.324719,\"volume_change_24h\":9.3285,\"percent_change_1h\":-0.01801769,\"percent_change_24h\":-0.00183446,\"percent_change_7d\":0.01199361,\"percent_change_30d\":0.01334672,\"percent_change_60d\":0.01338983,\"percent_change_90d\":0.01725864,\"market_cap\":54058844297.93327,\"market_cap_dominance\":1.7747,\"fully_diluted_market_cap\":54058844297.93179,\"tvl\":null,\"last_updated\":\"2025-02-12T10:19:58.000Z\"}}},{\"id\":74,\"name\":\"Dogecoin\",\"symbol\":\"DOGE\",\"slug\":\"dogecoin\",\"num_market_pairs\":1163,\"date_added\":\"2013-12-15T00:00:00.000Z\",\"tags\":[\"mineable\",\"pow\",\"scrypt\",\"medium-of-exchange\",\"memes\",\"payments\",\"doggone-doggerel\",\"bnb-chain-ecosystem\",\"ftx-bankruptcy-estate\",\"made-in-america\"],\"max_supply\":null,\"circulating_supply\":148013296383.7052,\"total_supply\":148013296383.7052,\"infinite_supply\":true,\"platform\":null,\"cmc_rank\":8,\"self_reported_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2025-02-12T10:20:00.000Z\",\"quote\":{\"EUR\":{\"price\":0.24507519183980525,\"volume_24h\":1335587287.5651886,\"volume_change_24h\":-3.5118,\"percent_change_1h\":0.22769632,\"percent_change_24h\":-4.23245104,\"percent_change_7d\":-4.11584542,\"percent_change_30d\":-21.29420873,\"percent_change_60d\":-37.30146224,\"percent_change_90d\":-37.11602126,\"market_cap\":36274387006.07851,\"market_cap_dominance\":1.1916,\"fully_diluted_market_cap\":36274387006.074905,\"tvl\":null,\"last_updated\":\"2025-02-12T10:19:58.000Z\"}}},{\"id\":2010,\"name\":\"Cardano\",\"symbol\":\"ADA\",\"slug\":\"cardano\",\"num_market_pairs\":1460,\"date_added\":\"2017-10-01T00:00:00.000Z\",\"tags\":[\"dpos\",\"pos\",\"platform\",\"research\",\"smart-contracts\",\"staking\",\"cardano-ecosystem\",\"cardano\",\"bnb-chain-ecosystem\",\"layer-1\",\"alleged-sec-securities\",\"2017-2018-alt-season\",\"made-in-america\"],\"max_supply\":45000000000,\"circulating_supply\":35197202831.96437,\"total_supply\":44995082706.687096,\"infinite_supply\":false,\"platform\":null,\"cmc_rank\":9,\"self_reported_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2025-02-12T10:19:00.000Z\",\"quote\":{\"EUR\":{\"price\":0.7569889050766505,\"volume_24h\":1257253238.6152422,\"volume_change_24h\":-1.4329,\"percent_change_1h\":2.21151995,\"percent_change_24h\":-1.87882082,\"percent_change_7d\":4.42612229,\"percent_change_30d\":-14.58923445,\"percent_change_60d\":-28.91541693,\"percent_change_90d\":35.75679718,\"market_cap\":26643892033.52949,\"market_cap_dominance\":0.8749,\"fully_diluted_market_cap\":34064500728.448284,\"tvl\":null,\"last_updated\":\"2025-02-12T10:19:58.000Z\"}}},{\"id\":1958,\"name\":\"TRON\",\"symbol\":\"TRX\",\"slug\":\"tron\",\"num_market_pairs\":1118,\"date_added\":\"2017-09-13T00:00:00.000Z\",\"tags\":[\"media\",\"payments\",\"ethereum-ecosystem\",\"tron-ecosystem\",\"bnb-chain-ecosystem\",\"layer-1\",\"dwf-labs-portfolio\",\"alleged-sec-securities\",\"2017-2018-alt-season\",\"tron20-ecosystem\"],\"max_supply\":null,\"circulating_supply\":86102469304.41965,\"total_supply\":86102396864.17812,\"infinite_supply\":true,\"platform\":null,\"cmc_rank\":10,\"self_reported_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2025-02-12T10:20:00.000Z\",\"quote\":{\"EUR\":{\"price\":0.23376281097168086,\"volume_24h\":552489879.6168429,\"volume_change_24h\":-8.2162,\"percent_change_1h\":0.55513217,\"percent_change_24h\":-1.24930708,\"percent_change_7d\":7.42604619,\"percent_change_30d\":9.21904849,\"percent_change_60d\":-17.38755587,\"percent_change_90d\":33.13565722,\"market_cap\":20127555256.204002,\"market_cap_dominance\":0.6609,\"fully_diluted_market_cap\":20127538322.373245,\"tvl\":null,\"last_updated\":\"2025-02-12T10:19:58.000Z\"}}}]}";
            System.out.println(result);
            object = new JSONObject(result);
            for (int i = 0; i < jsonListSize; i++) {
                JSONPointer temp = JSONPointer.builder()
                        .append("data")
                        .append(i)
                        .append("name")
                        .build();
                pointers.add(temp);
                JSONPointer temp2 = JSONPointer.builder()
                        .append("data")
                        .append(i)
                        .append("quote")
                        .append("EUR")
                        .append("price")
                        .build();
                values.add(temp2);
            }
            // query the JSON file and display the results
            displayTA.setText("");
            for (int i = 0; i < jsonListSize; i++) {
                displayTA.append(object.query(pointers.get(i)).toString());
                displayTA.append(" Price: €" + object.query(values.get(i)).toString() + "\n");
            }
            // write the results to a file
            try {
                FileWriter fw = new FileWriter("stats.json");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(result);
                bw.newLine();
                bw.write(jsonListSize);
                bw.close();
                displayTA.append("Stats written to file.");
            } catch (IOException e) {
                System.out.println(e);
            }

           System.out.println(result);
        }
        catch(IOException e) {
            System.out.println(e);
        }
        catch (URISyntaxException e) {
            System.out.println(e);
        } 
    }//GEN-LAST:event_fetchBTNActionPerformed

    private void createBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBTNActionPerformed
        // TODO add your handling code here:
        if (isConnected) {
            JFrame inputFrame = new JFrame();
            inputFrame.setSize(200, 200);
            javax.swing.JLabel currencyLabel = new javax.swing.JLabel();
            javax.swing.JButton confirmButton = new javax.swing.JButton();
            javax.swing.JTextField currencyTF = new javax.swing.JTextField();
            javax.swing.JComboBox currencyCBox = new javax.swing.JComboBox();
            javax.swing.JPanel backgroundJP = new javax.swing.JPanel();
            for (int i = 0; i < jsonListSize; i++) {
                currencyCBox.addItem(object.query(pointers.get(i)));
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
        }
    }//GEN-LAST:event_createBTNActionPerformed

    private void confirmBTNActionPerformed(java.awt.event.ActionEvent evt, javax.swing.JComboBox cBox,
            javax.swing.JTextField tField) {
            if (cBox.getSelectedIndex() != -1) {
                String quantity = tField.getText();
                database = mongoClient.getDatabase("crypto_database");
                Document newCurrency = new Document("_id", new ObjectId())
                        .append("currency", cBox.getSelectedItem().toString())
                        .append("quantity", quantity);
                InsertOneResult result = collection.insertOne(newCurrency);
                BsonValue id = result.getInsertedId();
                displayTA.setText("New document id: " + id);
            }     
    }
    private void readBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readBTNActionPerformed
        // TODO add your handling code here:
        displayTA.setText(readCollection(collection));
    }//GEN-LAST:event_readBTNActionPerformed

    private void updateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBTNActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog("Enter coin name: ");
        String quantity = JOptionPane.showInputDialog("Enter new quantity: ");
        Bson match = eq("currency", name);
        Bson update = Updates.set("quantity", quantity);
        UpdateResult result = collection.updateMany(match, update);
        System.out.println("Modified: " + result.getModifiedCount());
    }//GEN-LAST:event_updateBTNActionPerformed

    private void deleteBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBTNActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog("Enter coin name: ");
        Bson match = eq("currency", name);
        DeleteResult result = collection.deleteMany(match);
        System.out.println("Modified: " + result.getDeletedCount());
    }//GEN-LAST:event_deleteBTNActionPerformed

    private void newsBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newsBTNActionPerformed
        // TODO add your handling code here:#
        displayTA.setText("");
        for (int i = 0; i < newsEntries.size(); i++) {
            displayTA.append("- " + newsEntries.get(i).getTitle() + "\n");
            displayTA.append(newsEntries.get(i).getPublishedDate() + "\n\n");
        }
        displayTA.getCaret().moveDot(0);
    }//GEN-LAST:event_newsBTNActionPerformed

    private void nextArticleBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextArticleBTNActionPerformed
        // TODO add your handling code here:
        int tagIndex = 0;
        if (articleIndex < newsEntries.size())
            articleIndex++;
        for (int i = 0; i < newsEntries.get(articleIndex).getDescription().toString().length(); i++) {
            if (newsEntries.get(articleIndex).getDescription().toString().charAt(i) == '<') {
                if (newsEntries.get(articleIndex).getDescription().toString().charAt(i + 1) == 'p')
                    if (newsEntries.get(articleIndex).getDescription().toString().charAt(i + 2) == '>')
                        tagIndex = i;
            }
        }
        String description = newsEntries.get(articleIndex).getDescription().toString().substring(tagIndex);
        description = description.replaceAll("<p>", "");
        description = description.replaceAll("</p>", "");
        displayTA.setText(description);
    }//GEN-LAST:event_nextArticleBTNActionPerformed

    private void clusterTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clusterTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clusterTFActionPerformed

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
    private javax.swing.JLabel clusterLBL;
    private javax.swing.JTextField clusterTF;
    private javax.swing.JButton connectBTN;
    private javax.swing.JButton createBTN;
    private javax.swing.JButton deleteBTN;
    private javax.swing.JTextArea displayTA;
    private javax.swing.JButton exitBTN;
    private javax.swing.JButton fetchBTN;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton newsBTN;
    private javax.swing.JButton nextArticleBTN;
    private javax.swing.JLabel passwordLBL;
    private javax.swing.JPasswordField passwordPF;
    private javax.swing.JProgressBar progressPB;
    private javax.swing.JButton readBTN;
    private javax.swing.JButton updateBTN;
    private javax.swing.JLabel usernameLBL;
    private javax.swing.JTextField usernameTF;
    // End of variables declaration//GEN-END:variables
}
