package com.mycompany.apiconnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author <TeephopAlex MacHugh>
 */
public class CredentialManager {
    private static final String FILE_PATH = "credentials.txt";
    
    // Save credentials using FileWriter
    public static void saveCredentials(String userName, String password, String cluster){
        try(FileWriter writer = new FileWriter(FILE_PATH, false)){
            writer.write(userName + "\n");
            writer.write(password + "\n");
            writer.write(cluster + "\n");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    // Load the credentials from the file
    public static String[] loadCredentials(){
        File file = new File(FILE_PATH);
        if(!file.exists()) {
            return null;
        }
        
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String userName = reader.readLine();
            String password = reader.readLine();
            String cluster = reader.readLine();
            
            return new String[]{userName, password, cluster};
        }catch(IOException e){
            return null;
        }
    }
    
    // this method will delete the credentials file if the saveCredential in the setting set to false
    public static void deleteCredentials(){
        File file = new File(FILE_PATH);
        if(file.exists()){
            file.delete();
        }
    }
}

