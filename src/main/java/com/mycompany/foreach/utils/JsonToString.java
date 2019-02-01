/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author 6_Delta
 */
public final class JsonToString {
    
    private final String jsonString;
    private final String path;
    
    public JsonToString(String path){
        this.path = path;
        jsonString = getJSONForPathSimple();
    }
    
    private File getFilePathJSON() {
        return Paths.get(this.path).toFile();
    }
   
    public String getJSONForPathSimple() {
        File archivo = getFilePathJSON();
        StringBuilder builder = new StringBuilder();
        try (BufferedReader buffer = new BufferedReader(new FileReader(archivo))) {
            boolean loop = false;
            while (!loop) {
                String linea = buffer.readLine();
                if (linea == null) 
                    loop = true;
                else 
                    builder.append(linea.trim());
            }
        } catch (IOException ex) {
            
        }
        return builder.toString();
    }
    
    public String getJSONForPathComplete() {
        File archivo = getFilePathJSON();
        StringBuilder builder = new StringBuilder();
        try (BufferedReader buffer = new BufferedReader(new FileReader(archivo))) {
            boolean loop = false;
            while (!loop) {
                String linea = buffer.readLine();
                if (linea == null) 
                    loop = true;
                else 
                    builder.append(linea);
            }
        } catch (IOException ex) {
            
        }
        return builder.toString();
    }
}
