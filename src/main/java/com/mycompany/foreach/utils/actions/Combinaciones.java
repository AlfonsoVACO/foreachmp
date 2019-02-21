/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.daos.DaoDataColumns;
import com.mycompany.foreach.models.Combina;
import com.mycompany.foreach.models.DataColumns;
import com.mycompany.foreach.models.GeneralInfo;
import com.mycompany.foreach.models.JSONTemp;
import com.mycompany.foreach.models.Mws82;
import com.mycompany.foreach.utils.constantes.Constantes;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.Util;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 6_Delta
 */
public class Combinaciones implements Combina {

    private final DataColumns datac;
    private final Mws82 mwsod;
    private final GeneralInfo gralinfo;

    public Combinaciones(DataColumns datac) {
        this.datac = datac;
        this.mwsod = new Mws82();
        this.gralinfo = new GeneralInfo();
    }

    public Combinaciones(DataColumns datac, Mws82 mwsod, GeneralInfo gralinfo) {
        this.datac = datac;
        this.mwsod = mwsod;
        this.gralinfo = gralinfo;
    }

    @Override
    public void loadcmt() {
        if (FxDialogs.showConfirm(Constantes.TITLE,
                "Se ejecutará el proceso de CMT, ¿Desea continuar?",
                FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
            int position = datac.getNombre().lastIndexOf("\\");
            String workspace = datac.getNombre().substring(0, position);
            OperationsCMT operations
                    = new OperationsCMT(mwsod, gralinfo, workspace, datac.getNombre());
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
            FxDialogs.showInformation(Constantes.TITLE, "Proceso CMT terminado");
        }
    }

    @Override
    public void execute() {
        if (FxDialogs.showConfirm(Constantes.TITLE,
                "Se van a comprimir los archivos procesados, ¿Desea continuar?",
                FxDialogs.YES, FxDialogs.NO
        ).equals(FxDialogs.YES)) {
            Temp temporal = new Temp();
            Path path = Paths.get(datac.getNombre());
            if (path.toFile().exists()) {
                JSONTemp objjsom = temporal.readTemp(path.toFile());
                MoveFilesTable zip = new MoveFilesTable(objjsom);
                setMessage(zip);

                String accion = FxDialogs.showConfirm(Constantes.TITLE,
                        "¿Qué desea hacer con los archivos a procesar?",
                        FxDialogs.DELETE, FxDialogs.MOVE, FxDialogs.CANCEL
                );
                executeAction(accion, zip);
            } else {
                FxDialogs.showError(
                        Constantes.TITLE,
                        "El archivo ya no existe");
            }
        }
    }

    @Override
    public void delete() {
        try {
            if (FxDialogs.showConfirm(Constantes.TITLE,
                    "¿Está seguro de eliminar el registro?",
                    FxDialogs.YES, FxDialogs.NO
            ).equals(FxDialogs.YES)) {
                DaoDataColumns.deleteDetail(datac.getId());
                Util.removeFile(datac.getNombre());
                FxDialogs.showInformation(Constantes.TITLE, "Registro eliminado");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            FxDialogs.showException(Constantes.TITLE, "Error al eliminar", ex);
        }
    }

    @Override
    public void removeDB() {
        if (FxDialogs.showConfirm(Constantes.TITLE,
                "¿Está seguro de eliminar el registro?\n"
                + "(Sólo se eliminará de la base de datos, no del path)",
                FxDialogs.YES, FxDialogs.NO
        ).equals(FxDialogs.YES)) {
            try {
                DaoDataColumns.deleteDetail(datac.getId());
                FxDialogs.showInformation(Constantes.TITLE, "Registro eliminado de tabla");
            } catch (SQLException | ClassNotFoundException ex) {
                FxDialogs.showException(Constantes.TITLE, "Error al eliminar", ex);
            }
        }
    }

    private void executeAction(String seleccion, MoveFilesTable accion) {
        switch (seleccion) {
            case FxDialogs.MOVE:
                accion.saveLog();
                accion.saveXML();
                setMessage(accion);
                break;
            case FxDialogs.DELETE:
                accion.deleteXML();
                accion.deleteStats();
                setMessage(accion);
                break;
            default:
                break;
        }
    }

    private void setMessage(MoveFilesTable logs) {
        FxDialogs.showLongMessage(Constantes.TITLE,
                "Lista de logs al procesar",
                logs.getLogs());
    }

}
