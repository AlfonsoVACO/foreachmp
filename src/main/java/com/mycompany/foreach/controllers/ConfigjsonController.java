/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.controllers;

import com.mycompany.foreach.utils.Constantes;
import com.mycompany.foreach.utils.FilesOperations;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.JsonToString;
import com.mycompany.foreach.utils.Navegacion;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author 6_Delta
 */
public class ConfigjsonController implements Initializable {
    @FXML private TextArea codeeditor;
    Navegacion nav;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nav = new Navegacion();
        JsonToString json = new JsonToString(Constantes.PATH_JSON);
        codeeditor.setText(json.getJSONForPathComplete());
    }

    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(nav.getPrincipal() ));
        FXMLController principal = loader.getController();
        Navegacion.setStageController(principal);
        return mainPane;

    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().setAll(getClass().getResource(Constantes.STYLES).toExternalForm());
        return scene;
    }
    
    @FXML
    private void savearch(ActionEvent event) throws IOException{
        if(Paths.get(Constantes.PATH_JSON).toFile().exists()){
            File archivo = Paths.get(Constantes.PATH_JSON).toFile();
            FilesOperations.writeFileJSON(archivo, codeeditor.getText());
        }else
            FxDialogs.showWarning(Constantes.TITLE, 
                    "No se puede realizar acción porque no se cargó JSON raíz");
    }

}
