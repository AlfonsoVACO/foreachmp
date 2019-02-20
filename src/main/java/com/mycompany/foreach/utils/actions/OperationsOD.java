/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.Mws82;
import com.mycompany.foreach.utils.Console;
import com.mycompany.foreach.utils.constantes.Constantes;
import com.mycompany.foreach.utils.FxDialogs;
import com.mycompany.foreach.utils.Util;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author 6_Delta
 */
public class OperationsOD {

    private final Mws82 mwsod;
    private final File workspace;
    private final Path workspacewmod;
    private final List<String> logs;
    private final List<File> archivostxt;
    private final String nomenclatura;

    public OperationsOD(Mws82 mwsod, File workspace, String nomenclatura) throws IOException {
        this.mwsod = mwsod;
        this.workspace = workspace;
        this.workspacewmod = Paths.get(workspace + "\\wM82\\");
        this.nomenclatura = nomenclatura;
        this.logs = new ArrayList<>();
        this.archivostxt = new ArrayList<>();
        createDirIStats();
    }

    private void createDirIStats() throws IOException {
        this.logs.add("Iniciando el proceso de 8.2");
        this.mwsod.getToPath().forEach((directory) -> {
            File dirstat = Paths.get(workspacewmod.toString() + "\\" + directory).toFile();
            if (!dirstat.exists()) {
                dirstat.mkdir();
                this.logs.add("Creando directorio: " + directory);
            }
        });
        executeCommand();
    }

    private void executeCommand() throws IOException {
        for (String paths : this.mwsod.getToPath()) {
            String nombrearch = paths.contains("66") ? "IS_66" : "IS_67";
            Path dircclog = Paths.get(this.mwsod.getPath().toString() + "\\" + paths);
            String archive = dircclog.toString() + "\\" + nombrearch + "-" + this.nomenclatura + ".txt";
            String nombrearchivoactual = paths.contains("66")
                    ? Constantes.NAME_BATSE
                    : Constantes.NAME_BATSI;
            createArchives(dircclog, archive, nombrearchivoactual);
            executeBat(nombrearchivoactual);
            FxDialogs.showInformation(Constantes.TITLE,
                    "Espere a que la pantalla negra termine\n"
                    + "(Si no aparece, click en aceptar)");
            moviendoTxt(new File(archive), paths + "\\"
                    + nombrearch + "-" + this.nomenclatura + ".txt");
        }
    }

    private void moviendoTxt(File from, String subcarpetaarch) {
        Path to = Paths.get(this.workspacewmod.toString() + "\\" + subcarpetaarch);
        this.logs.add("Moviendo archivo txt " + from.toString() + " >> " + to.toString());
        from.renameTo(to.toFile());
        this.archivostxt.add(to.toFile());
        this.logs.add("Archivo movido correctamente");
    }

    private void createArchives(Path path, String tosave, String archive) throws IOException {
        Util.makeFileNameds(
                Arrays.asList("@echo off",
                        "type \"" + path.toString() + "\\*.log\" > \"" + tosave + "\"",
                        "pause",
                        "exit"),
                this.workspacewmod, archive, ".bat");
    }

    private void executeBat(String type) {
        Console.executeFromFile(this.workspacewmod.toFile(), new String[]{
            "cmd",
            "/c",
            type
        });
        if (!Console.getLog().equals("")) {
            this.logs.add(Console.getLog());
        } else {
            this.logs.add("Comando ejecutado correctamente");
        }
    }

    public List<File> getArchivostxt() {
        return this.archivostxt;
    }

    public List<String> getLogs() {
        return this.logs;
    }
    
    public List<String> getWorksPaths() {
        List<String> lista = new ArrayList<>();
        this.mwsod.getToPath().stream()
                .map((paths) -> Paths.get(this.mwsod.getPath().toString() + "\\" + paths))
                .forEachOrdered((dircclog) -> {
            lista.add(dircclog.toString());
        });
        return lista;
    }
}
