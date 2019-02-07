/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author 6_Delta
 */
public class SendInfoCMT {

    private final StatsJson stats;
    private final XmlJson xml;
    private Date date;
    private Path path;
    private final Path urljson;

    public SendInfoCMT(Path urljson) {
        this.urljson = urljson;
        this.stats = new StatsJson();
        this.xml = new XmlJson();
        getJson();
    }

    public StatsJson getStats() {
        return this.stats;
    }

    public XmlJson getXml() {
        return this.xml;
    }

    public Date getDate() {
        return this.date;
    }

    public Path getPath() {
        return this.path;
    }

    private File getFilePathJSON() {
        return urljson.toFile();
    }

    private void getJson() {
        if (getFilePathJSON().exists()) {
            try {
                JSONParser parser = new JSONParser();
                Object objJSON = parser.parse(new FileReader(getFilePathJSON()));
                JSONObject objeto = (JSONObject) objJSON;
                String pathjson = (String) objeto.get("path");

                String datejson = (String) objeto.get("date");
                this.date = getDate(datejson);
                this.path = Paths.get(pathjson);

                JSONObject xmljson = (JSONObject) objeto.get("xml");
                JSONObject statsjson = (JSONObject) objeto.get("stats");
                getXML(xmljson);
                getStats(statsjson);
            } catch (IOException | ParseException ex) {
                FxDialogs.showException(Constantes.TITLE, "Error al construir json", ex);
            }
        }else{
            FxDialogs.showError(Constantes.TITLE, "El archivo no existe");
        }
    }

    private void getXML(JSONObject xmljson) {
        this.xml.setExtension((String) xmljson.get("extension"));
        this.xml.setUrl((String) xmljson.get("url"));
    }

    private void getStats(JSONObject statsjson) {
        this.stats.setExtension((String) statsjson.get("extension"));
        this.stats.setUrls((JSONArray) statsjson.get("urls"));
    }

    private Date getDate(String datejson) {
        Date dater = null;
        try {
            SimpleDateFormat simplefor = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            dater = simplefor.parse(datejson);
        } catch (java.text.ParseException ex) {
            FxDialogs.showException(Constantes.TITLE, "Error al generar fecha", ex);
        }
        return dater != null ? dater : new Date();
    }

    public class StatsJson {

        private String extension;
        private List<String> urls;

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }

    }

    public class XmlJson {

        private String extension;
        private String url;

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
