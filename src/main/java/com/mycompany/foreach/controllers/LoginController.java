/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.controllers;

import com.mycompany.foreach.connections.ConnectionLocal;
import com.mycompany.foreach.utils.Constantes;
import com.mycompany.foreach.utils.Consultas;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.Navegacion;
import com.mycompany.foreach.utils.SendInfoToClass;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author 6_Delta
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUser;
    Navegacion nav;
    SendInfoToClass send;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nav = new Navegacion();
        try {
            send = new SendInfoToClass();
            createTables();
        } catch (FileNotFoundException ex) {
            openFile();
        } catch (IOException | ParseException ex) {
            FxDialogs.showException("Error", ex.getMessage(), ex);
            Thread closeprogram = new Thread(() -> {
                exit();
            });
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex1) {
                Logger.getLogger(LoginController.class.getName())
                        .log(Level.SEVERE, null, ex1);
            }
            closeprogram.start();
        }
    }

    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar archivo de configuración JSON");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );
        File archivo = fileChooser.showOpenDialog(null);
        if (archivo != null) {
            try {
                send = new SendInfoToClass(archivo);
                createTables();
            } catch (IOException | ParseException ex) {
                FxDialogs.showException("Error", ex.getMessage(), ex);
            }
        }
    }

    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = (Pane) loader.load(getClass().
                getResourceAsStream(nav.getPrincipal()));
        FXMLController controler = loader.getController();
        Navegacion.setStageController(controler);
        Navegacion.loadStageDaftarListBaju(nav.getConfigjson());
        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().setAll(getClass().getResource(Constantes.STYLES).toExternalForm());
        scene.setFill(new Color(0, 0, 0, 0));
        return scene;
    }

    @FXML
    private void exit(ActionEvent event) throws ClassNotFoundException {
        exit();
    }

    private void exit() {
        System.exit(0);
    }

    @FXML
    private void btnLogin(ActionEvent event) throws IOException {
        if (txtUser.getText() != null) {

            FXMLController.id_usuario = txtUser.getText();
            FXMLController.mwsno = send.getMWS98();
            FXMLController.mwsod = send.getMWS82();
            FXMLController.gralinfo = send.getIntoGral();
            Node node = (Node) event.getSource();
            Stage st = (Stage) node.getScene().getWindow();
            Stage showDECORATED = new Stage(StageStyle.DECORATED);

            showDECORATED.setScene(createScene(loadMainPane()));
            showDECORATED.centerOnScreen();
            showDECORATED.show();
            st.close();

        } else {
            FxDialogs.showInformation(Constantes.TITLE, "Verifica tus datos");
        }
    }

    private void createTables() {
        Connection conn = null;
        try {
            conn = ConnectionLocal.getConnection();
            Statement st = conn.createStatement();
            st.execute(Consultas.CREATE_TABLE_ARCHIVOS);
            System.out.println("Tabla creada");
        } catch (SQLException | ClassNotFoundException ex) {
            if (ex.getMessage().contains("exist")) {
                FxDialogs.showInformation(Constantes.TITLE, "Tablas listas");
            } else {
                FxDialogs.showError(Constantes.TITLE, ex.getMessage());
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException t) {
                FxDialogs.showError(Constantes.TITLE, t.getMessage());
            }
        }
    }
}
