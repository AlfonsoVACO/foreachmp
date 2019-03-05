/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import java.awt.AWTException;
import java.awt.Robot;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author 6_Delta
 */
public class FxDialogs {

    public static final String YES = "Si";
    public static final String NO = "No";
    public static final String OK = "OK";
    public static final String CANCEL = "Cancelar";
    public static final String DELETE = "Eliminar";
    public static final String MOVE = "Mover";

    public static void showInformation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Información");
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Alerta");
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showException(String title, String message, Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Excepción");
        alert.setHeaderText(title);
        alert.setContentText(message);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Detalles:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public static String showTextInput(String title, String message, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Entrada");
        dialog.setHeaderText(title);
        dialog.setContentText(message);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    public static boolean[] getRadioDes(String title, String message, RadioButton... options) {
        //ChoiceDialog choice = new ChoiceDialog();
        //choice.getItems().addAll(options);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Control");
        alert.setHeaderText(title);
        alert.getDialogPane().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                event.consume();
                try {
                    Robot r = new Robot();
                    r.keyPress(java.awt.event.KeyEvent.VK_SPACE);
                    r.keyRelease(java.awt.event.KeyEvent.VK_SPACE);
                } catch (AWTException e) {
                }
            }
        });
        
        for (RadioButton buttons : options) {
            buttons.setWrapText(true);
            buttons.setMaxWidth(Double.MAX_VALUE);
            buttons.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(buttons, Priority.ALWAYS);
            GridPane.setHgrow(buttons, Priority.ALWAYS);
        }
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);

        for (int i = 0; i < options.length; i++) {
            expContent.add(options[i], 0, i);
        }
        alert.getDialogPane().setContent(expContent);//.setExpandableContent(expContent);
        alert.getButtonTypes().setAll(Arrays.asList(new ButtonType(OK), new ButtonType(CANCEL)));
        Optional<ButtonType> result = alert.showAndWait();
        
        boolean[] itemsel = new boolean[options.length];
        int i = 0;
        for (RadioButton buttons : options) {
            itemsel[i] = buttons.isSelected();
            i++;
        }
        
        return !result.isPresent() ? new boolean[]{} : itemsel;

    }

    public static String showConfirm(String title, String message, String... options) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Elije una opción");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.getDialogPane().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                event.consume();
                try {
                    Robot r = new Robot();
                    r.keyPress(java.awt.event.KeyEvent.VK_SPACE);
                    r.keyRelease(java.awt.event.KeyEvent.VK_SPACE);
                } catch (AWTException e) {
                }
            }
        });
        if (options == null || options.length == 0) {
            options = new String[]{OK, CANCEL};
        }

        List<ButtonType> buttons = new ArrayList<>();
        for (String option : options) {
            buttons.add(new ButtonType(option));
        }

        alert.getButtonTypes().setAll(buttons);

        Optional<ButtonType> result = alert.showAndWait();
        return !result.isPresent() ? CANCEL : result.get().getText();
    }

    public static void showLongMessage(String title, String message, List<String> cadenax) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Información");
        alert.setHeaderText(title);

        StringBuilder stringb = new StringBuilder();
        cadenax.forEach((linea) -> {
            stringb.append(linea).append("\n");
        });

        Label label = new Label("Detalles:");

        TextArea textArea = new TextArea(stringb.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();

        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
