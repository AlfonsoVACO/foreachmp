/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.JSONTemp;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.Util;
import com.mycompany.foreach.utils.constantes.Constantes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

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
        compressXML();
        compressLog();
    }

    private void compressXML() {
        compress(this.jsontemp.getXml().getUrl().toString(),
                "\\wM98\\","wM98", this.jsontemp.getXml().getExtension());
        setLogs();
        FxDialogs.showInformation(Constantes.TITLE, "Archivos XML comprimidos");
    }

    private void compressLog() {
        this.jsontemp.getStats().getUrls().forEach((path) -> {
            compress(path.toString(),getRuta(path), getZipName(path), this.jsontemp.getStats().getExtension());
            FxDialogs.showInformation(Constantes.TITLE, "Archivo stats en nodo comprimidos");
        });
        setLogs();
    }

    public void saveXML() {
        sendArchives(this.jsontemp.getXml().getUrl(), this.jsontemp.getXml().getExtension(),
                "\\wM98\\");
        setLogs();
    }

    public void saveLog() {
        this.jsontemp.getStats().getUrls().forEach((path) -> {
            sendArchives(path, this.jsontemp.getStats().getExtension(), getRuta(path));
        });
        setLogs();
    }
    
    public void deleteXML() {
        deleteFiles(this.jsontemp.getXml().getUrl(), this.jsontemp.getXml().getExtension());
        FxDialogs.showInformation(Constantes.TITLE, 
                "Archivos XML eliminados" + Constantes.getCausa());
    }

    public void deleteStats() {
        this.jsontemp.getStats().getUrls().forEach((path) -> {
            deleteFiles(path, this.jsontemp.getStats().getExtension());
        });
        FxDialogs.showInformation(Constantes.TITLE, 
                "Archivos stats eliminados" + Constantes.getCausa());
    }

    private void deleteFiles(Path path, String extension) {
        try {
            List<Path> lstpaths = Util.filterContenido(Files.list(path),extension);
            lstpaths.forEach(it -> it.toFile().deleteOnExit());
        } catch (IOException ex) {
            this.logs.add("Error: " + ex.getMessage());
        }
    }

    private void setLogs() {
        if (hasxml) {
            this.logs.add("No hay archivos en esa ruta");
        } else {
            this.logs.add("Archivos procesados exitosamente");
            hasxml = false;
        }
    }

    private String getRuta(Path path) {
        return path.toString().contains("66") ? "\\wM82\\stats IS66\\"
                : "\\wM82\\stats IS67\\";
    }

    private String getZipName(Path path) {
        return path.toString().contains("66") ? "stats IS66" : "stats IS67";
    }

    public List<String> getLogs() {
        return this.logs;
    }

    private void sendArchives(Path path, String extension, String folder) {
        try {
            List<Path> lstpaths = Util.filterContenido(Files.list(path), extension);
            if (!lstpaths.isEmpty()) {
                lstpaths.forEach((patharch) -> {
                    String wkspace = getWorkspaceFile(folder, patharch);
                    this.logs.add("Mover a " + wkspace);
                    patharch.toFile().renameTo(Paths.get(wkspace).toFile());
                });
            } else {
                hasxml = true;
            }
        } catch (IOException ex) {
            this.logs.add("Error: " + ex.getMessage());
        }
    }

    private void compress(String from, String to, String nombrearc, String toext) {
        try {
            String directorio = getWorkspace(to);
            File carpeta = new File(from);
            if (carpeta.exists()) {
                List<Path> lstpaths = Util.filterContenido(Files.list(carpeta.toPath()), toext);
                ZipOutputStream outputzip = new ZipOutputStream(new FileOutputStream(directorio + nombrearc + ".zip"));
                lstpaths.forEach(it -> {
                    FileInputStream fileinput = null;
                    try {
                        ZipEntry entrada = new ZipEntry(it.toFile().getName());
                        System.out.println(it.toFile().getName());
                        outputzip.putNextEntry(entrada);
                        fileinput = new FileInputStream(from + File.separator + entrada.getName());
                        int leidos;
                        byte[] buffer = new byte[1024];
                        while (0 < (leidos = fileinput.read(buffer))) {
                            outputzip.write(buffer, 0, leidos);
                        }
                        fileinput.close();
                    } catch (FileNotFoundException ex) {
                        this.logs.add("Error: " + ex.getMessage());
                    } catch (IOException ex) {
                        this.logs.add("Error: " + ex.getMessage());
                    }
                });
            } else {
                this.logs.add("La ruta no existe");
            }
        } catch (IOException ex) {
            this.logs.add("Error: " + ex.getMessage());
        }
    }

    private String getWorkspace(String folder) {
        StringBuilder workspace = new StringBuilder();
        return workspace.append(this.jsontemp.getPath().toString())
                .append(folder).toString();
    }
    
    private String getWorkspaceFile(String folder, Path patharch) {
        StringBuilder workspace = new StringBuilder();
        return workspace.append(this.jsontemp.getPath().toString())
                .append(folder)
                .append(patharch.toFile().getName()).toString();
    }
}
