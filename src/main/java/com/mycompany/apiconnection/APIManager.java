/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apiconnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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
import org.json.JSONObject;
import org.json.JSONPointer;

/**
 *
 * @author rokom
 * This class handles fetch API requests and processes the resulting JSON data
 */
public class APIManager {
    private String APIKey = "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c"; //sandbox key
    private JSONObject object;
    private ArrayList<JSONPointer> pointers;
    private ArrayList<JSONPointer> values;
    private int JSONLength = 30;
    
    APIManager() {
        
    }
    
    private String makeAPICall(String uri, List<NameValuePair> parameters)
        throws URISyntaxException, IOException {
        String response_content = "";
        
    // URIBuilder is used to safely construct a URL with query parameters.
        URIBuilder query = new URIBuilder(uri);
        
        if (parameters != null)
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
        request.addHeader("X-CMC_PRO_API_KEY", APIKey);
        
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
    
    public void fetchAPI() {       
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        // String uri = "https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("start", "1"));
        parameters.add(new BasicNameValuePair("limit", String.valueOf(JSONLength)));
        parameters.add(new BasicNameValuePair("convert", "EUR"));
        try {
            String result = makeAPICall(uri, parameters);
            System.out.println(result);
            object = new JSONObject(result);
            for (int i = 0; i < JSONLength; i++) {
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
        }
        catch(IOException e) {
            System.out.println(e);
        }
        catch (URISyntaxException e) {
            System.out.println(e);
        } 
    }
    
    public boolean validateAPIKey() {
        String uri = "https://pro-api.coinmarketcap.com/v1/key/info";
        try {
            String result = makeAPICall(uri, null);
            System.out.println(result);
            if (result.contains("This API Key is invalid."))
                return false;
            else
                return true;
        } catch (URISyntaxException | IOException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public String getPrices() {
        String result = "";
        for (int i = 0; i < JSONLength; i++) {
                result += object.query(pointers.get(i)).toString();
                result += ": €" + object.query(values.get(i)).toString() + "\n";
            }
        return result;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public JSONObject getObject() {
        return object;
    }

    public ArrayList<JSONPointer> getPointers() {
        return pointers;
    }

    public ArrayList<JSONPointer> getValues() {
        return values;
    }
    
    public int getJSONLength() {
        return JSONLength;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }

    public void setPointers(ArrayList<JSONPointer> pointers) {
        this.pointers = pointers;
    }

    public void setValues(ArrayList<JSONPointer> values) {
        this.values = values;
    }

    public void setJSONLength(int JSONLength) {
        this.JSONLength = JSONLength;
    }
    
}
