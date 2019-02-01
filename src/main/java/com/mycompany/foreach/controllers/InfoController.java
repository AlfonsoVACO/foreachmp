/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.controllers;

import com.mycompany.foreach.models.DataColumns;
import com.mycompany.foreach.utils.JsonToString;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.web.HTMLEditor;

/**
 * FXML Controller class
 *
 * @author 6_Delta
 */
public class InfoController implements Initializable {

    @FXML
    private Label lblid, lbldate;
    @FXML
    private Hyperlink linkurl;
    @FXML
    private HTMLEditor jsonh;

    public static DataColumns columnas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblid.setText(columnas.getId().toString());
        lbldate.setText(columnas.getFecha());
        linkurl.setText(columnas.getNombre());
        if (Paths.get(columnas.getNombre()).toFile().exists()) {
            JsonToString json = new JsonToString(columnas.getNombre());
            jsonh.setHtmlText(json.getJSONForPathComplete());
        }else
            jsonh.setHtmlText("El archivo ya no existe");
    }

}
