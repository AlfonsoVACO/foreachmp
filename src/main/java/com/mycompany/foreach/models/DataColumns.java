/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.models;

import com.mycompany.foreach.daos.DaoDataColumns;
import com.mycompany.foreach.utils.Constantes;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.Util;
import com.mycompany.foreach.utils.actions.MoveFilesTable;
import com.mycompany.foreach.utils.actions.OperationsCMT;
import com.mycompany.foreach.utils.actions.Temp;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.scene.control.Button;

/**
 *
 * @author 6_Delta
 */
public class DataColumns {

    private StringProperty nombre, fecha;
    private IntegerProperty id;
    private final Button execute, loadcmt, delete;

    private Mws82 mwsod;
    private GeneralInfo gralinfo;

    public DataColumns(final int id, final String nombre, String fecha,
            final Button execute, final Button loadcmt, final Button delete,
            final Mws82 mwsod, final GeneralInfo gralinfo) {

        this.nombre = new SimpleStringProperty(nombre);
        this.fecha = new SimpleStringProperty(fecha);
        this.id = new SimpleIntegerProperty(id);
        this.execute = execute;
        this.loadcmt = loadcmt;
        this.delete = delete;
        this.mwsod = mwsod;
        this.gralinfo = gralinfo;

        delete.setDisable(true);

        loadcmt.setOnMouseReleased((Event event) -> {
            int position = nombre.lastIndexOf("\\");
            String workspace = nombre.substring(0, position);
            OperationsCMT operations = 
                    new OperationsCMT(mwsod, gralinfo, workspace, nombre);
            List<String> logs = operations.getLogs();
            if (!logs.isEmpty()) {
                try {
                    FxDialogs.showError(Constantes.TITLE,
                            "Ocurrieron errores durante el proceso");
                    Path path = Paths.get(workspace + "\\wM82\\");
                    Util.makeFileNameds(logs, path, "Errores-mws", ".log");
                } catch (IOException ex) {
                    FxDialogs.showException(Constantes.TITLE, ex.getMessage(), ex);
                }
            }
            delete.setDisable(false);
        });

        execute.setOnMouseReleased((Event arg0) -> {
            Temp temporal = new Temp();
            Path path = Paths.get(nombre);
            if (path.toFile().exists()) {
                JSONTemp objjsom = temporal.readTemp(path.toFile());
                MoveFilesTable move = new MoveFilesTable(objjsom);

                FxDialogs.showLongMessage(
                        Constantes.TITLE,
                        "Lista de logs al procesar",
                        move.getLogs());
            } else {
                FxDialogs.showError(
                        Constantes.TITLE,
                        "El archivo ya no existe");
            }
        });

        delete.setOnMouseReleased((Event arg0) -> {
                try {
                if (FxDialogs.showConfirm(Constantes.TITLE,
                        "Está seguro de eliminar el registro",
                        FxDialogs.YES, FxDialogs.NO
                ).equals(FxDialogs.YES)) {

                    DaoDataColumns.deleteDetail(id);
                    delete.setDisable(true);
                    execute.setDisable(true);
                    FxDialogs.showInformation("Información",
                            "El registro se eliminará de la tabla \n"
                            + "al generar otro reporte o reiniciar la app");
                    Util.removeFile(nombre);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                FxDialogs.showException(Constantes.TITLE, "Error al eliminar", ex);
            }
        });
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getFecha() {
        return fecha.get();
    }

    public void setFecha(String fecha) {
        this.fecha = new SimpleStringProperty(fecha);
    }

    public Button getExecute() {
        return execute;
    }

    public Button getDelete() {
        return delete;
    }

    public Button getLoadcmt() {
        return loadcmt;
    }

    public Mws82 getMwsod() {
        return mwsod;
    }

    public void setMwsod(Mws82 mwsod) {
        this.mwsod = mwsod;
    }

    public GeneralInfo getGralinfo() {
        return gralinfo;
    }

    public void setGralinfo(GeneralInfo gralinfo) {
        this.gralinfo = gralinfo;
    }

}
