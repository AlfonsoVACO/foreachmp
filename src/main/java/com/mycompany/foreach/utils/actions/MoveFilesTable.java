/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.JSONTemp;
import com.mycompany.foreach.utils.Util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 6_Delta
 */
public class MoveFilesTable {

    private final JSONTemp jsontemp;
    private final List<String> logs;
    private boolean hasxml = false;

    public MoveFilesTable(JSONTemp jsontemp) {
        this.jsontemp = jsontemp;
        this.logs = new ArrayList<>();
        saveXML();
        saveLog();
    }

    private void saveXML() {
        sendArchives(this.jsontemp.getXml().getUrl(),
                this.jsontemp.getXml().getExtension(),
                "\\wM98\\");
        if( hasxml )
            this.logs.add("No hay archivos en esa ruta");
        else{
            this.logs.add("Archivos movidos exitosamente");
            hasxml = false;
        }
    }

    private void saveLog() {
        this.jsontemp.getStats().getUrls().forEach((path) -> {
            String rutaex = path.toString().contains("66")
                    ? "\\wM82\\stats IS66\\"
                    : "\\wM82\\stats IS67\\";
            sendArchives(path, this.jsontemp.getStats().getExtension(), rutaex);
        });
        if( hasxml )
            this.logs.add("No hay archivos en esa ruta");
        else{
            this.logs.add("Archivos movidos exitosamente");
            hasxml = false;
        }
    }

    public List<String> getLogs() {
        return this.logs;
    }

    private void sendArchives(Path path, String extension, String folder) {
        try {
            List<Path> lstpaths = Util.filterContenido(Files.list(path), extension);
            if (!lstpaths.isEmpty()) {
                lstpaths.forEach((patharch) -> {
                    StringBuilder workspace = new StringBuilder();
                    workspace.append(this.jsontemp.getPath().toString())
                            .append(folder)
                            .append(patharch.toFile().getName());
                    this.logs.add("Mover a " + workspace.toString());
                    patharch.toFile().renameTo(Paths.get(workspace.toString()).toFile());
                });
            }else
                hasxml = true;
        } catch (IOException ex) {
            this.logs.add("Error: " + ex.getMessage());
        }
    }
}
