/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.JSONTemp;
import com.mycompany.foreach.utils.constantes.Constantes;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.Util;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author 6_Delta
 */
public class Temp {
    private String stats = "\"stats\":{", xml = "\"xml\":{";
    private String date = "\"date\":", path = "\"path\":";
    private final Date fecha;
    private final List<String> logs;
    private final JSONTemp jsonTemp;
    
    public Temp(){
        this.logs = new ArrayList<>();
        this.jsonTemp = new JSONTemp();
        this.fecha = new Date();
    }
    
    public Temp(String cngstats, String cngxml, Date fecha, String pathp) {
        this.logs = new ArrayList<>();
        this.jsonTemp = new JSONTemp();
        stats += cngstats;
        xml += cngxml;
        date += "\""+fecha+"\"";
        this.fecha = fecha;
        this.path = pathp;
    }

    public String createTemp() throws IOException {
        long time = fecha.getTime();
        Util.makeFileNameds(
                Arrays.asList(
                        "{",
                        stats + "},",
                        xml + "},",
                        date+",",
                        "\"path\":\""+path.replace("\\", "\\\\")+"\"","}"),
                Paths.get(path),
                "fileTemp-"+time,
                ".json");
        return Paths.get(path).toString()+"\\fileTemp-"+time+".json";
    }
    
    public JSONTemp readTemp(File archivo) {
        try{
            JSONParser parser = new JSONParser();
            Object objJSON = parser.parse(new FileReader(archivo));
            JSONObject objeto = (JSONObject) objJSON;
            
            getObjStats( (JSONObject) objeto.get("stats") );
            getObjXml( (JSONObject) objeto.get("xml") );            
            this.jsonTemp.setDate( (String) objeto.get("date") );
            this.jsonTemp.setPath( Paths.get( (String) objeto.get("path") ) );
        }catch(ParseException | IOException ex){
            FxDialogs.showException(Constantes.TITLE, ex.getMessage(), ex);
        }
        return this.jsonTemp;
    }
    
    private void getObjStats(JSONObject objstats){
        JSONArray urls = (JSONArray) objstats.get("urls");
        List<Path> lstPath = new ArrayList<>();
        for(Object itemurl : urls){
            lstPath.add( Paths.get(itemurl.toString()) );
        }
        String extension = (String) objstats.get("extension");
        this.jsonTemp.setStats(new JSONTemp.Stats(extension, lstPath) );
    }
    
    private void getObjXml(JSONObject objstats){
        String url = (String) objstats.get("url");
        String extension = (String) objstats.get("extension");
        this.jsonTemp.setXml(new JSONTemp.Xml(extension, Paths.get(url)) );
    }
    
    public List<String> getLogs(){
        return this.logs;
    }
}
