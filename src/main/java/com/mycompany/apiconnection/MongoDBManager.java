/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.swing.JOptionPane;
import org.bson.Document;

/**
 *
 * @author rokom
 * the MongoDB manager class handles the database connection throughout the app
 */
public class MongoDBManager {
    private MongoClientSettings settings;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    
    private boolean isConnected = false;
    
    MongoDBManager() {
        
    }
    public void connect(String username, String password, String cluster, String databaseName, String collectionName) {
        // connect to MongoDB
        
        try {
            username = URLEncoder.encode(username, "UTF-8");
            password = URLEncoder.encode(password, "UTF-8");
            cluster = URLEncoder.encode(cluster, "UTF-8");
            
        } catch (UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null,e);
        }
        settings = null;

        try {
            String connectionString = "mongodb+srv://" + username + ":" + password + "@" + cluster + ".mongodb.net/?retryWrites=true&w=majority&appName=myCluster";
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();
            settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
        }
        
        // Create a new client and connect to the server
        if (settings != null) {
            mongoClient = MongoClients.create(settings);
            try {
                // Send a ping to confirm a successful connection
                database = mongoClient.getDatabase(databaseName);
                database.runCommand(new Document("ping", 1));
                collection = database.getCollection(collectionName);
               
                JOptionPane.showMessageDialog(null, "Pinged your deployment. You successfully connected to MongoDB!");
                
                isConnected = true;
                
            } catch (MongoException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    public boolean isConnected() {
        return isConnected;
    }

    public MongoClientSettings getSettings() {
        return settings;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }
    
}
