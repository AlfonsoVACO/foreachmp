/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.GeneralInfo;
import com.mycompany.foreach.models.Mws98;
import com.mycompany.foreach.models.Nodo;
import com.mycompany.foreach.utils.GenerarXLS;
import com.mycompany.foreach.utils.Util;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author 6_Delta
 */
public class XmlToRun {

    private final Mws98 mwsno;
    private final GeneralInfo gralinfo;

    private final Path path;
    private List<Path> archivos = null;
    private List<Path> archivosIS_N1 = null;
    private List<Path> archivosMWS_N1 = null;
    private List<Path> archivosIS_N2 = null;
    private List<Path> archivosMWS_N2 = null;
    private final List<String> logs;
    private final List<String> lstarchfail;
    private final File workspace;

    public XmlToRun(Mws98 mwsno, GeneralInfo gralinfo, File workspace) {
        this.mwsno = mwsno;
        this.workspace = workspace;
        this.gralinfo = gralinfo;
        this.path = mwsno.getPath();
        this.logs = new ArrayList<>();
        this.lstarchfail = new ArrayList<>();
    }

    public List<String> executeXml() throws IOException {
        try {
            if (!Files.exists(this.path)) {
                this.logs.add("El directorio cclog no existe");
                this.path.toFile().mkdir();
                this.logs.add("Creando directorio: cclog");
            }
            this.logs.add("Pah de archivos xml >> " + this.path.toString()
                    + " >> Extención: " + this.mwsno.getExtension());
            try (Stream<Path> contenido = Files.list(this.path)) {
                this.logs.add("Filtrando archivos xml");
                this.archivos = Util.filterContenido(contenido, this.mwsno.getExtension());
            } catch (IOException e) {
                this.logs.add("Error: " + e.getMessage());
            }
            this.archivosIS_N1 = filter("IS", "N1");
            this.archivosIS_N2 = filter("IS", "N2");
            this.archivosMWS_N1 = filter("MWS", "N1");
            this.archivosMWS_N2 = filter("MWS", "N2");
        } catch (Exception e) {
            this.logs.add("Error: " + e.getMessage());
        }
        this.logs.add(
                "IS-N1 >> " + this.archivosIS_N1.size()
                + " IS-N2 >> " + this.archivosIS_N2.size()
                + " MWS-N1 >> " + this.archivosMWS_N1.size()
                + " MWS-N2 >> " + this.archivosMWS_N2.size());

        genera(this.archivosIS_N1, "IS_N1");
        genera(this.archivosIS_N2, "IS_N2");

        genera(this.archivosMWS_N1, "MWS_N1");
        genera(this.archivosMWS_N2, "MWS_N2");
        this.logs.add("XML mws98 generados en c:\\xml");

        return this.logs;
    }

    private void genera(List<Path> pathis, String typels) throws IOException {
        List<Nodo> nodo = new ArrayList<>();
        GenerarXLS generarIS = new GenerarXLS(this.mwsno);
        pathis.forEach((pathisn1) -> {
            ReadXML desXML = new ReadXML();
            try {
                nodo.add(desXML.leer(Files.readAllLines(pathisn1).toString(),
                        new Nodo(), pathisn1.getFileName()));
                if(desXML.getLogs() != null && !"".equals(desXML.getLogs()))
                    this.logs.add(desXML.getLogs());
                if (!desXML.getFilesFail().equals("")) {
                    this.lstarchfail.add(desXML.getFilesFail());
                }
            } catch (IOException e) {
                this.logs.add("Error: " + e.getMessage());
            }
        });
        this.logs.add("Escribir en archivo...");
        generarIS.escribirArchivo(typels, nodo);
        if (!this.lstarchfail.isEmpty()) {
            this.logs.add("Hubo errores al leer archivo...");
            this.logs.add("Creando txt con archivos fallidos");
            Util.makeFileFails(this.lstarchfail, this.gralinfo);
        }

        moveXLStoWS();
    }

    private List<Path> filter(String type, String nodo) {
        List<Path> lstnewPath = new ArrayList<>();
        this.archivos.stream().filter((patharch) -> (patharch.getFileName().toString().contains(type)
                && patharch.getFileName().toString().contains(nodo))).forEachOrdered((patharch) -> {
                    lstnewPath.add(patharch);
        });
        return lstnewPath.isEmpty() ? new ArrayList<>() : lstnewPath;
    }

    private void moveXLStoWS() throws IOException {
        List<Path> archivosxls = Util.filterContenido(
                Files.list(this.mwsno.getToPath()),
                this.mwsno.getFinalextension());
        archivosxls.forEach((pathachr) -> {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append(this.workspace.toPath().toString())
                    .append("\\")
                    .append(this.gralinfo.getExtras().get(1))
                    .append("\\")
                    .append(pathachr.toFile().getName());
            this.logs.add("Mover archivos xls de directorio a " + stringbuilder.toString());
            pathachr.toFile().renameTo(Paths.get(stringbuilder.toString()).toFile());
        });
        this.logs.add("Archivos movidos exitosamente");
    }
}
