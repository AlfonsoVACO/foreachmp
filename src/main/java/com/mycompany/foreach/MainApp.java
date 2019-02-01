package com.mycompany.foreach;

import com.mycompany.foreach.utils.FxDialogs;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    /*@Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/principal.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/styles.css");
        
        stage.setTitle("ForEach");
        stage.setScene(scene);
        stage.show();
    }*/
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApp.class.getResource("/styles/styles.css").toExternalForm());
        stage.setScene(scene);
        scene.setFill(new Color(0, 0, 0, 0));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
