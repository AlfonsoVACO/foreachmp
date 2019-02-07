/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import com.mycompany.foreach.controllers.FXMLController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 *
 * @author 6_Delta
 */
public class Navegacion {
    private String log = "/fxml/log.fxml";
    private String info = "/fxml/info.fxml";
    private String configjson = "/fxml/configjson.fxml";
    private String principal = "/fxml/principal.fxml";

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLog() {
        return log;
    }
    
    public String getConfigjson() {
        return configjson;
    }

    public void setConfigjson(String configjson) {
        this.configjson = configjson;
    }

    public void setLog(String log) {
        this.log = log;
    }
    
    public String getPrincipal() {
        return principal;
    }
    
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    
    private static FXMLController principalController;
    
    public static void setStageController (FXMLController controller ) {
        Navegacion.principalController = controller;
    }
    
    public static void loadStageTambahBaju (String fxml) throws IOException{
        principalController.setStageLoadStage((Node) FXMLLoader.load(Navegacion.class.getResource(fxml)));
    }
    
    public static void loadStageDaftarListBaju (String fxml) throws IOException{
        principalController.setStageItemReporte((Node) FXMLLoader.load(Navegacion.class.getResource(fxml)));
    }
}
