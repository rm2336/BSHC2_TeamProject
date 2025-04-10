package com.mycompany.apiconnection;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class FileManager {
    private static final String CONFIG_FILE = "app_config.properties";

    // NEW: File to store API key and fetched data
    private static final String API_DATA_FILE = "api_data.properties";

    // Save both credentials and settings in one properties file.
    public static void saveConfig(String userName, String password, String cluster,
                                  boolean saveMongoDB, boolean saveAPI,
                                  boolean saveCredential) {
        Properties properties = new Properties();

        // Store credentials only if the user chose to save them
        if (saveCredential) {
            properties.setProperty("userName", userName);
            properties.setProperty("password", password);
            properties.setProperty("cluster", cluster);
        }

        // Store settings flags (these always get stored)
        properties.setProperty("saveMongoDB", String.valueOf(saveMongoDB));
        properties.setProperty("saveAPI", String.valueOf(saveAPI));
        properties.setProperty("saveCredential", String.valueOf(saveCredential));

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            properties.store(writer, "Application Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the entire configuration.
    public static Properties loadConfig() {
        Properties properties = new Properties();
        File file = new File(CONFIG_FILE);

        if (!file.exists()) {
            return properties;
        }
        try (FileReader reader = new FileReader(file)) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    // ------------------------------
    // ✅ NEW METHODS for API Settings
    // ------------------------------

    public static void saveAPIData(String apiKey, String apiData) {
        Properties properties = new Properties();
        properties.setProperty("apiKey", apiKey);
        properties.setProperty("apiData", apiData);

        try (FileWriter writer = new FileWriter(API_DATA_FILE)) {
            properties.store(writer, "API Key and Fetched Data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties loadAPIData() {
        Properties properties = new Properties();
        File file = new File(API_DATA_FILE);

        if (!file.exists()) {
            return properties;
        }
        try (FileReader reader = new FileReader(file)) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
