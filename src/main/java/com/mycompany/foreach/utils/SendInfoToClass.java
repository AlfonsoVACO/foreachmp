/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import com.mycompany.foreach.models.Archives;
import com.mycompany.foreach.models.GeneralInfo;
import com.mycompany.foreach.models.Mws82;
import com.mycompany.foreach.models.Mws98;
import com.mycompany.foreach.models.MwsArchive;
import com.mycompany.foreach.models.MwsPrimary;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author 6_Delta
 */
public final class SendInfoToClass {

    private Mws98 mwsno ;
    private Mws82 mwsod;
    private GeneralInfo gral;
    private File archivo;
    private boolean notfound = false;

    public SendInfoToClass() throws IOException, FileNotFoundException, ParseException {
        getJson();
    }
    
    public SendInfoToClass(File archivo) throws IOException, FileNotFoundException, ParseException {
        this.archivo = archivo;
        this.notfound = true;
        getJson();
    }

    public Mws82 getMWS82() {
        return this.mwsod;
    }

    public Mws98 getMWS98() {
        return this.mwsno;
    }
    
    public GeneralInfo getIntoGral(){
        return this.gral;
    }

    private File getFilePathJSON() {
        return notfound ? this.archivo : Paths.get(Constantes.PATH_JSON).toFile();
    }

    private void getJson(){
        try {
            JSONParser parser = new JSONParser();
            Object objJSON = parser.parse(new FileReader(getFilePathJSON()));
            JSONObject objeto = (JSONObject) objJSON;
            this.mwsno = getMws98((JSONObject) objeto.get("mws98"));
            this.mwsod = getMws82((JSONObject) objeto.get("mws82"));
            this.gral = getGeneralInfo((JSONObject) objeto.get("createDir"));
        } catch (FileNotFoundException ex) {
            FxDialogs.showException("Error", "Archivo no encontrado", ex);
        } catch (IOException | ParseException ex) {
            FxDialogs.showException("Error", ex.getMessage(), ex);
            System.exit(0);
        }
    }
    
    private GeneralInfo getGeneralInfo(JSONObject gralinfo){
        JSONArray extras = (JSONArray) gralinfo.get("extras");
        return new GeneralInfo( (String) gralinfo.get("inDir"), extras);
    }

    private Mws98 getMws98(JSONObject mws98) {
        Mws98 mws = new Mws98();
        mws.setExtension((String) mws98.get("extension"));
        mws.setFinalextension((String) mws98.get("finalextension"));
        mws.setIp((String) mws98.get("ip"));
        mws.setPath(Paths.get((String) mws98.get("path")));
        mws.setToPath(Paths.get((String) mws98.get("toPath")));
        return mws;
    }

    private Mws82 getMws82(JSONObject mws82) {
        Mws82 mws = new Mws82();

        JSONArray ips = (JSONArray) mws82.get("ips");
        JSONArray toPath = (JSONArray) mws82.get("toPath");
        JSONArray commands = (JSONArray) mws82.get("commands");

        mws.setIps(ips);
        mws.setPath(Paths.get((String) mws82.get("path")));
        mws.setExtension((String) mws82.get("extension"));
        mws.setToPath(toPath);
        mws.setCommands(commands);
        JSONObject objArchives = (JSONObject) mws82.get("archives");
        mws.setArchives(getArchives(objArchives));
        return mws;
    }

    private Archives getArchives(JSONObject objArchives) {
        Archives archives = new Archives();
        archives.setFilter( (String) objArchives.get("filter"));
        archives.setOnRegex( (String) objArchives.get("onRegex"));
        archives.setToRegex( (String) objArchives.get("toRegex"));
        
        JSONObject is = (JSONObject)  objArchives.get("IS");
        JSONObject mws = (JSONObject)  objArchives.get("MWS");        
        archives.setMwsis( Arrays.asList( getIS(is), getMWS(mws) ) );
        return archives;
    }
    
    private MwsPrimary getIS(JSONObject is){
        JSONArray heads = (JSONArray) is.get("heads");
        JSONArray alias = (JSONArray) is.get("alias");
        JSONArray display = (JSONArray) is.get("display");
        
        MwsPrimary mwsp = new MwsPrimary();
        mwsp.setHeads(heads);
        mwsp.setAlias(alias);
        mwsp.setDisplay(display);
        mwsp.setType("IS");
        
        return mwsp;
    }
    
    private MwsPrimary getMWS(JSONObject mws){
        JSONArray heads = (JSONArray) mws.get("heads");
        JSONArray alias = (JSONArray) mws.get("alias");
        JSONObject memory = (JSONObject) mws.get("memory");
        JSONObject cpu = (JSONObject) mws.get("cpu");
        JSONObject threads = (JSONObject) mws.get("threads");
        
        JSONArray headsarrMemory = (JSONArray) memory.get("heads");
        JSONArray staticarMemory = (JSONArray) memory.get("static");
        
        JSONArray headsarrCpu = (JSONArray) cpu.get("heads");
        JSONArray staticarCpu = (JSONArray) cpu.get("static");
        
        JSONArray headsarrThreads = (JSONArray) threads.get("heads");
        JSONArray staticarThreads = (JSONArray) threads.get("static");
        
        MwsPrimary mwsp = new MwsPrimary();
        mwsp.setHeads(heads);
        mwsp.setAlias(alias);
        mwsp.setType("MWS");
        mwsp.setMemory(new MwsArchive(headsarrMemory, staticarMemory));
        mwsp.setCpu(new MwsArchive(headsarrCpu, staticarCpu));
        mwsp.setThreads(new MwsArchive(headsarrThreads, staticarThreads));
        return mwsp;
    }
}
