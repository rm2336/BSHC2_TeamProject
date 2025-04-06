/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.apiconnection;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.exclude;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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
import org.jfree.chart.ChartUtilities;
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
    private MongoDBManager mongoManager;
    private APIManager APIManager;
    private ArrayList<JSONPointer> pointers = new ArrayList<>();
    private ArrayList<JSONPointer> values = new ArrayList<>();
    private ArrayList<Double> totalValues = new ArrayList<>();
    private JSONObject object;
    private int jsonListSize = 30;
    private List<SyndEntryImpl> newsEntries;
    private int articleIndex = -1;
    private CredentialManager credentialManager;
    /**
     * Creates new form CryptoChaunGUI
     */
    public CryptoChaunGUI() {
        initComponents();
        chatBTN.setVisible(false);
        
            // Load credentials if available
    String[] savedCredentials = credentialManager.loadCredentials();
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
    
    public void setMongoDBManager(MongoDBManager manager) {
        mongoManager = manager;
    }
    
    public void setAPIManager(APIManager api) {
        APIManager = api;
        // set API Manager variables
        APIManager.setJSONLength(jsonListSize);
        APIManager.setObject(object);
        APIManager.setPointers(pointers);
        APIManager.setValues(values);
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
        chatBTN = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        prevArticleBTN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("cryptochaunFrame"); // NOI18N

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

        chatBTN.setText("Chat");
        chatBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatBTNActionPerformed(evt);
            }
        });

        jButton1.setText("?");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        prevArticleBTN.setText("<");
        prevArticleBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevArticleBTNActionPerformed(evt);
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
                                .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(backgroundJPLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(deleteBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(backgroundJPLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(passwordPF, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                            .addComponent(usernameTF))))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(backgroundJPLayout.createSequentialGroup()
                                        .addComponent(newsBTN)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(prevArticleBTN)
                                        .addGap(18, 18, 18)
                                        .addComponent(nextArticleBTN)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(backgroundJPLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(backgroundJPLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(exitBTN)
                                .addGap(41, 41, 41)
                                .addComponent(apiBTN)
                                .addGap(35, 35, 35)
                                .addComponent(fetchBTN)
                                .addGap(34, 34, 34)
                                .addComponent(chartBTN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chatBTN)
                                .addGap(27, 27, 27))))
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
                        .addComponent(jButton1)
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(backgroundJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newsBTN)
                            .addComponent(nextArticleBTN)
                            .addComponent(prevArticleBTN))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(backgroundJPLayout.createSequentialGroup()
                        .addContainerGap(47, Short.MAX_VALUE)
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
                    .addComponent(chartBTN)
                    .addComponent(chatBTN))
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backgroundJP, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        String[] savedCredentials = credentialManager.loadCredentials();
        if(savedCredentials != null) {
            //load credentials into variables
            user = savedCredentials[0];
            password = savedCredentials[1];
            clusterName = savedCredentials[2];
        }
        
        // Attempt to connect
        mongoManager.connect(user, password, clusterName, "crypto_database", "crypto_stats", true);
        
        // Save the database backup locally
        saveDatabaseLocally();
                
        // Save credentials after successful connection
        credentialManager.saveCredentials(user, password, clusterName);
                
        // make chat button visible
        chatBTN.setVisible(true);
        
    }//GEN-LAST:event_connectBTNActionPerformed

    public void saveDatabaseLocally() {
        if (mongoManager.getCollection() == null){
            JOptionPane.showMessageDialog(null, "Not connected to MongoDB!");
            return;
        }
      
        // Write to "crypto_backup.json"
        try (FileWriter file = new FileWriter("crypto_backup.json")){
            // retrieves all documents from the MongoDB collection exclude("_id")
            FindIterable<Document> output = mongoManager.getCollection().find().projection(exclude("_id"));
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
        APIManager.setAPIKey(API_key);
        
        if (API_key.equals(""))
            API_key = "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c";
    }//GEN-LAST:event_apiBTNActionPerformed

    private void chartBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chartBTNActionPerformed
        // TODO add your handling code here:
        DefaultPieDataset myPieDataset = new DefaultPieDataset();
        myPieDataset.clear();
        FindIterable<Document> output = mongoManager.getCollection().find(new Document()).projection(exclude("_id"));
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
        chartFrame.add(saveBTN);
        chartFrame.setLayout(null);
        saveBTN.setBounds(chartFrame.getX(), chartFrame.getHeight(), 75, 20);
        chartFrame.setVisible(true);
        chartFrame.setSize(400, 500);
        // calculate sum of all currency values
        double portfolioValue = 0;
        for (int i = 0; i < totalValues.size(); i++) {
            portfolioValue += totalValues.get(i);
        }
        displayTA.setText("Total value of portfolio: €" + BigDecimal.valueOf(portfolioValue).setScale(2, RoundingMode.HALF_EVEN));
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
                displayTA.append(": €" + object.query(values.get(i)).toString() + "\n");
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
        if (mongoManager.isConnected()) {
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
                //database = mongoClient.getDatabase("crypto_database");
                Document newCurrency = new Document("_id", new ObjectId())
                        .append("currency", cBox.getSelectedItem().toString())
                        .append("quantity", quantity);
                InsertOneResult result = mongoManager.getCollection().insertOne(newCurrency);
                BsonValue id = result.getInsertedId();
                displayTA.setText("New document id: " + id);
            }     
    }
    private void readBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readBTNActionPerformed
        // TODO add your handling code here:
        displayTA.setText(mongoManager.readRecords(mongoManager.getCollection(), APIManager.getObject(), APIManager.getJSONLength(), APIManager.getPointers(), APIManager.getValues()));
    }//GEN-LAST:event_readBTNActionPerformed

    private void updateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBTNActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog("Enter coin name: ");
        String quantity = JOptionPane.showInputDialog("Enter new quantity: ");
        Bson match = eq("currency", name);
        Bson update = Updates.set("quantity", quantity);
        UpdateResult result = mongoManager.getCollection().updateMany(match, update);
        System.out.println("Modified: " + result.getModifiedCount());
    }//GEN-LAST:event_updateBTNActionPerformed

    private void deleteBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBTNActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog("Enter coin name: ");
        Bson match = eq("currency", name);
        DeleteResult result = mongoManager.getCollection().deleteMany(match);
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
        if (articleIndex < newsEntries.size() - 1)
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

    private void chatBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatBTNActionPerformed
        // TODO add your handling code here:
        ChatGUI chatScreen = new ChatGUI();
        chatScreen.setUsername(usernameTF.getText());
        chatScreen.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_chatBTNActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        TutorialGUI tutorialScreen = new TutorialGUI();
        tutorialScreen.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void prevArticleBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevArticleBTNActionPerformed
        // TODO add your handling code here:
        int tagIndex = 0;
        if (articleIndex > 0)
            articleIndex--;
        else {
            articleIndex = 0;
        }
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
    }//GEN-LAST:event_prevArticleBTNActionPerformed

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
    private javax.swing.JButton chatBTN;
    private javax.swing.JLabel clusterLBL;
    private javax.swing.JTextField clusterTF;
    private javax.swing.JButton connectBTN;
    private javax.swing.JButton createBTN;
    private javax.swing.JButton deleteBTN;
    private javax.swing.JTextArea displayTA;
    private javax.swing.JButton exitBTN;
    private javax.swing.JButton fetchBTN;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton newsBTN;
    private javax.swing.JButton nextArticleBTN;
    private javax.swing.JLabel passwordLBL;
    private javax.swing.JPasswordField passwordPF;
    private javax.swing.JButton prevArticleBTN;
    private javax.swing.JProgressBar progressPB;
    private javax.swing.JButton readBTN;
    private javax.swing.JButton updateBTN;
    private javax.swing.JLabel usernameLBL;
    private javax.swing.JTextField usernameTF;
    // End of variables declaration//GEN-END:variables
}
