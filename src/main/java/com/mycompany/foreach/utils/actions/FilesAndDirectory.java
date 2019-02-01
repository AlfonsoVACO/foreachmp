/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.GeneralInfo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 6_Delta
 */
public class FilesAndDirectory {

    
    private final GeneralInfo gralinfo;
    private File workspace;
    private String nomenclatura;
    private final List<String> logs;

    public FilesAndDirectory(GeneralInfo gralinfo) {
        this.gralinfo = gralinfo;
        this.logs = new ArrayList<>();
    }
    
    public String getNomenclatura(){
        return this.nomenclatura;
    }

    private void setNomenclatura(String mesGenera, String anio) {
        this.nomenclatura = mesGenera.substring(0, 3) + "-" + anio;//anio.substring(1,anio.length());
        this.logs.add("Definiendo nomenclatura");
    }

    public List<String> generateDirectory(String mesGenera, int anio) throws IOException {
        setNomenclatura(mesGenera, String.valueOf(anio));

        String directory = this.gralinfo.getInDir() + "\\" + mesGenera + "-" + anio;
        this.workspace = Paths.get(directory).toFile();
        if (!this.workspace.exists()) {
            this.workspace.mkdir();
            this.logs.add("Creando directorio master");
            if (!this.workspace.exists()) {
                this.logs.add(">>> Hubo un error al crear directorios...");
            }
        }

        this.gralinfo.getExtras().forEach((extra) -> {
            File extramws = Paths.get(this.workspace + "\\" + extra).toFile();
            if (!extramws.exists()) {
                extramws.mkdir();
                this.logs.add("Creando directorio: " + extra);
            }
        });
        return this.logs;
    }
    
    public File getWorkspace(){
        return this.workspace;
    }

}