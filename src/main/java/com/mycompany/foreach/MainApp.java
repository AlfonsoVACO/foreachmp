package com.mycompany.foreach;

import com.mycompany.foreach.connections.ConnectionLocal;
import com.mycompany.foreach.controllers.FXMLController;
import com.mycompany.foreach.models.Navegacion;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.SendInfoToClass;
import com.mycompany.foreach.utils.constantes.Constantes;
import com.mycompany.foreach.utils.constantes.Consultas;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    Navegacion nav;
    SendInfoToClass send;

    @Override
    public void start(Stage stage) throws Exception {
        initialize();
        
        FXMLController.id_usuario = "User";
        FXMLController.mwsno = send.getMWS98();
        FXMLController.mwsod = send.getMWS82();
        FXMLController.gralinfo = send.getIntoGral();
        Stage showDECORATED = new Stage(StageStyle.DECORATED);
        showDECORATED.setResizable(false);
        showDECORATED.setScene(createScene(loadMainPane()));
        showDECORATED.centerOnScreen();
        showDECORATED.show();
        //st.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initialize() {
        nav = new Navegacion();
        send = new SendInfoToClass();
        if (send.isFail()) {
            try {
                send.getJson();
                createTables();
            } catch (IOException ex) {
                FxDialogs.showException(Constantes.TITLE, ex.getMessage(), ex);
            }
        } else {
            openFile();
        }
    }

    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = (Pane) loader.load(getClass().
                getResourceAsStream(nav.getPrincipal()));
        FXMLController controler = loader.getController();
        Navegacion.setStageController(controler);
        Navegacion.loadStageItemReporte(nav.getConfigjson());
        Navegacion.loadStageFindList(nav.getFindstring());
        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().setAll(getClass().getResource(Constantes.STYLES).toExternalForm());
        scene.setFill(new Color(0, 0, 0, 0));
        return scene;
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
                send.getJson();
                createTables();
            } catch (IOException ex) {
                FxDialogs.showException(Constantes.TITLE, ex.getMessage(), ex);
            }
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
