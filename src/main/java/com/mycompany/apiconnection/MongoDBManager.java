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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.exclude;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import java.awt.Dimension;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.json.JSONPointer;

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
    public void connect(String username, String password, String cluster, String databaseName, String collectionName, boolean notify) {
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
               
                if (notify)
                    JOptionPane.showMessageDialog(null, "Pinged your deployment. You successfully connected to MongoDB!");
                
                isConnected = true;
                
            } catch (MongoException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    // CRUD methods are defined below
    
    public void createRecord(float quantity, String recordName) {
        // update record if the coin already exists
        if (isConnected) {
            // find quantity
            String currentQuantity = "";
            List<Document> results = new ArrayList<>();
            FindIterable<Document> output = collection.find(new Document()).projection(exclude("_id"));
            output.into(results);
            Bson match = eq("currency", recordName);
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getString("currency").equals(recordName))
                    currentQuantity = results.get(i).getString("quantity");
            }
            if (currentQuantity.equals(""))
                currentQuantity = "0";
            float finalQuantity = quantity + Float.parseFloat(currentQuantity);
            Bson update = Updates.set("quantity", String.valueOf(finalQuantity));
            UpdateResult result = collection.updateMany(match, update);
            System.out.println("Modified: " + result.getModifiedCount());
            if (result.getModifiedCount() == 0) {
                Document newCurrency = new Document("_id", new ObjectId())
                        .append("currency", recordName)
                        .append("quantity", String.valueOf(finalQuantity));
                InsertOneResult finalResult = collection.insertOne(newCurrency);
            }
        }     
    }
    
    public String readRecords(MongoCollection<Document> collection, JSONObject object, int objectLength, ArrayList<JSONPointer> pointers, ArrayList<JSONPointer> values) {
        ArrayList<Double> totalValues = new ArrayList<>();
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
            for (int j = 0; j < objectLength; j++) {
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
    
    public void updateRecord(String name, String quantity) {                                          
        // TODO add your handling code here:
        Bson match = eq("currency", name);
        Bson update = Updates.set("quantity", quantity);
        UpdateResult result = collection.updateMany(match, update);
        System.out.println("Modified: " + result.getModifiedCount());
    }   
    
    public void deleteRecord(String name) {
        Bson match = eq("currency", name);
        DeleteResult result = collection.deleteMany(match);
        System.out.println("Modified: " + result.getDeletedCount());       
    }
    
    // add the user's timestamp to the collection if not recorded,
    // otherwise update it
    public void updateLeaderboard(String username) {
        FindIterable<Document> output = collection.find(new Document()).projection(exclude("_id"));
        List<Document> results = new ArrayList<>();
        output.into(results);
        Bson match = eq("username", username);
        Bson update = Updates.set("last logged in", Calendar.getInstance().getTime().toString());
        UpdateResult result = collection.updateOne(match, update);
        if (result.getModifiedCount() == 0) {
            // create new record
            Document newTimestamp = new Document("_id", new ObjectId())
                    .append("username", username)
                    .append("last logged in", Calendar.getInstance().getTime().toString());
            collection.insertOne(newTimestamp);
        }
    }
    
    public String readLeaderboard() {
        String result = "";
        FindIterable<Document> output = collection.find(new Document()).projection(exclude("_id"));
        List<Document> results = new ArrayList<>();
        output.into(results);
        for (int i = 0; i < results.size(); i++) {
            result += results.get(i).getString("username");
            result += " - Last seen on " + results.get(i).getString("last logged in") + "\n";
        }
        return result;
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
