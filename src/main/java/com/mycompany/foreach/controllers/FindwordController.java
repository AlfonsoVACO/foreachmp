/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.controllers;

import com.mycompany.foreach.utils.Console;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.actions.Walking;
import com.mycompany.foreach.utils.constantes.Constantes;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

/**
 * FXML Controller class
 *
 * @author 6_Delta
 */
public class FindwordController implements Initializable {

    @FXML
    private ListView<String> paths, extensiones;
    @FXML
    private TextField path, strinput;
    private ObservableList<String> itemspath;
    private ObservableList<String> itemsextends;
    private boolean[] radios = new boolean[3];

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.itemsextends = FXCollections.observableArrayList();
    }

    @FXML
    private void onRigthClick(MouseEvent event) {
        if (event.getButton().compareTo(MouseButton.SECONDARY) == 0) {
            paths.setCellFactory(it -> {
                ListCell<String> cell = new ListCell<>();
                ContextMenu context = new ContextMenu();
                MenuItem itemmenu = new MenuItem();
                itemmenu.textProperty().bind(Bindings.format("Abrir \"%s\"", cell.textProperty()));
                itemmenu.setOnAction(ev -> {
                    String value = cell.getItem();
                    if(!value.isEmpty()){
                        Console.execute("cmd", "/c", "\""+value+"\"");
                        if (!Console.getLog().equals("")) {
                            FxDialogs.showError(Constantes.TITLE, Console.getLog());
                        }
                    }
                });
                context.getItems().addAll(itemmenu);
                cell.textProperty().bind(cell.itemProperty());
                cell.emptyProperty().addListener((obs, wasemty, isnoempty) -> {
                    if (isnoempty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(context);
                    }
                });
                return cell;
            });
        }
    }

    @FXML
    private void examinar(ActionEvent event) {
        DirectoryChooser directory = new DirectoryChooser();
        directory.setTitle("Agregar path");
        File archivo = directory.showDialog(null);
        if (archivo != null) {
            this.path.setText(archivo.getPath());
        }
    }

    @FXML
    private void buscar(ActionEvent event) throws IOException {
        this.paths.setItems(FXCollections.observableArrayList());
        String palabra = this.strinput.getText();
        this.itemspath = FXCollections.observableArrayList();
        String url = this.path.getText();

        if (!"".equals(url)) {
            if (this.itemsextends.isEmpty()) {
                FxDialogs.showError(Constantes.TITLE, "No has agregado las extenciones a buscar");
            } else if ("".equals(palabra)) {
                FxDialogs.showError(Constantes.TITLE, "Olvidaste agregar el texto a buscar");
            } else {
                Walking lector = new Walking();
                List<String> loistaarchivos = new ArrayList<>();
                boolean extep = this.itemsextends.filtered(it -> ("mp3".equals(it))
                        || ("mp4".equals(it))
                        || ("jpg".equals(it))
                        || ("png".equals(it))
                        || ("pdf".equals(it))).isEmpty();
                if (extep) {
                    if(!notTrue()) this.radios[0] = true;
                    loistaarchivos = lector.execute(palabra, this.itemsextends, Paths.get(url), this.radios);
                    if (loistaarchivos.isEmpty()) {
                        FxDialogs.showInformation(Constantes.TITLE, "Ups! ningún archivo encontrado");
                    } else {
                        loistaarchivos.forEach(it -> this.itemspath.add(it));
                        this.paths.setItems(this.itemspath);
                    }
                } else {
                    FxDialogs.showWarning(Constantes.TITLE, "Ups! hay extenciones que no se pueden leer");
                }
            }
        } else {
            FxDialogs.showWarning(Constantes.TITLE, "No has seleccionado una ruta");
        }
    }

    @FXML
    private void addExt(ActionEvent event) {
        String extension = FxDialogs.showTextInput("Agrega extensión",
                "Ingresa la extencióna a agregar", "");
        itemsextends.add(extension);
        extensiones.setItems(itemsextends);
    }

    @FXML
    private void clean(ActionEvent event) {
        this.itemsextends.clear();
        this.itemspath.clear();
        this.extensiones.setItems(FXCollections.observableArrayList());
    }
    
    @FXML
    private void avanzado(ActionEvent event){
        this.radios = FxDialogs
                .getRadioDes(Constantes.TITLE, "Opciones avanzadas", 
                        new RadioButton("Mayusculas y minusculas"),
                        new RadioButton("Solo minusculas"),
                        new RadioButton("Solo mayusculas"));
    }
    
    private boolean notTrue(){
        for(boolean item: this.radios){
            if(item) return true;
        }
        return false;
    }

}
